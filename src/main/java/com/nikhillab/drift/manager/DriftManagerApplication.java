package com.nikhillab.drift.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nikhillab.drift.manager.service.SnapshotResourceService;

@SpringBootApplication
public class DriftManagerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DriftManagerApplication.class, args);

	}

	@Autowired
	SnapshotResourceService snapshotResourceService;

	@Override
	public void run(String... args) throws Exception {
		// resourceManager.createSnapShot(182l, "ec2:security-group");
		// System.out.println("SnapShot Created Successfully");
		// System.out.println(snapshotResourceService.getResources(65l));
		
	}

}
