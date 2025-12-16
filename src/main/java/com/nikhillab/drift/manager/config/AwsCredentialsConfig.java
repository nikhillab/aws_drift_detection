package com.nikhillab.drift.manager.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nikhillab.drift.manager.service.aws.AWSResourceExplorer2Service;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.GetCallerIdentityResponse;

@Configuration
public class AwsCredentialsConfig {

    @Bean
    public AwsCredentialsProvider awsCredentialsProvider() {
        return ProfileCredentialsProvider.builder().profileName("test").build();

    }

    private Map<String, String> getCallerIdentity(AwsCredentialsProvider awsCredentialsProvider) {
        StsClient stsClient = StsClient.builder().credentialsProvider(awsCredentialsProvider)
                .region(Region.US_EAST_1)
                .build();

        GetCallerIdentityResponse identity = stsClient.getCallerIdentity();

        return Map.of(
                "AccountId", identity.account(),
                "UserId", identity.userId());
    }

    @Bean
    public AWSResourceExplorer2Service awsResourceExplorer2Service(AwsCredentialsProvider awsCredentialsProvider,
            @Value("${aws.view.arn}") String awsViewArn) {

        var callerIdentity = getCallerIdentity(awsCredentialsProvider);
        return new AWSResourceExplorer2Service(awsCredentialsProvider, Region.US_EAST_1,
                awsViewArn.replace("{{accountID}}", callerIdentity.get("AccountId")));
    }

}