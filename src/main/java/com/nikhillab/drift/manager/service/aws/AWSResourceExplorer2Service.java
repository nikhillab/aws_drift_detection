package com.nikhillab.drift.manager.service.aws;

import java.util.List;

import com.nikhillab.drift.manager.entity.AWSResourceDetails;
import com.nikhillab.drift.manager.entity.AWSResourceDetailsProperty;
import com.nikhillab.drift.manager.entity.AWSSupportedResourceType;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.resourceexplorer2.ResourceExplorer2Client;
import software.amazon.awssdk.services.resourceexplorer2.model.ListResourcesRequest;
import software.amazon.awssdk.services.resourceexplorer2.model.ListSupportedResourceTypesRequest;
import software.amazon.awssdk.services.resourceexplorer2.model.SearchFilter;

public class AWSResourceExplorer2Service {

    private final ResourceExplorer2Client resourceExplorer2Client;
    private final String viewArn;

    public AWSResourceExplorer2Service(AwsCredentialsProvider credentialsProvider, Region region, String viewArn) {
        this.resourceExplorer2Client = ResourceExplorer2Client.builder()
                .credentialsProvider(credentialsProvider).region(region)
                .build();
        this.viewArn = viewArn;
    }

    public List<AWSSupportedResourceType> getSupportedResourceTypes() {
        var listSupportedResourceTypesRequest = ListSupportedResourceTypesRequest.builder()
                .maxResults(200)
                .build();

        return resourceExplorer2Client.listSupportedResourceTypesPaginator(listSupportedResourceTypesRequest)
                .stream()
                .flatMap(listSupportedResourceTypesResponce -> listSupportedResourceTypesResponce.resourceTypes()
                        .stream())
                .map(resourceType -> new AWSSupportedResourceType(resourceType.service(), resourceType.resourceType()))
                .toList();

    }

    public List<AWSResourceDetails> getResources(String resourceType) {
        var listResourcesRequest = ListResourcesRequest.builder()
                .viewArn(viewArn)
                .filters(SearchFilter.builder().filterString("resourcetype:" + resourceType).build())
                .maxResults(100)
                .build();

        return resourceExplorer2Client.listResourcesPaginator(listResourcesRequest)
                .stream()
                .flatMap(listResourcesResponce -> listResourcesResponce.resources().stream())
                .map(resource -> {
                    var awsResourceDetailsProperty = resource.properties()
                            .stream()
                            .map(property -> new AWSResourceDetailsProperty(
                                    property.name(),
                                    property.lastReportedAt(),
                                    property.data().toString()))
                            .toList();

                    return new AWSResourceDetails(
                            resource.arn(),
                            resource.owningAccountId(),
                            resource.region(),
                            resource.resourceType(),
                            resource.service(),
                            resource.lastReportedAt(),
                            awsResourceDetailsProperty);
                }).toList();

    }

}
