package com.naharamatya.blob.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

@Configuration
public class AzureBlobStorageConfiguration {

	@Value("${azure.storage.container.name}")
	private String containerName;
	
	@Value("${azure.storage.connection.string}")
	private String connectionString;
	
	@Bean
	public BlobServiceClient getBlobServiceClient() {
		BlobServiceClient serviceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
		return serviceClient;
	} 
	
	@Bean
	public BlobContainerClient getBlobContainerClient() {
		BlobContainerClient containerClient = getBlobServiceClient().getBlobContainerClient(containerName);
		return containerClient;
	}
	
	
}
