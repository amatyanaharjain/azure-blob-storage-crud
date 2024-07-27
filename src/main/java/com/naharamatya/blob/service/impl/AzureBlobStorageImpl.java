package com.naharamatya.blob.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobItem;
import com.naharamatya.blob.model.Storage;
import com.naharamatya.blob.service.IAzureBlobStorage;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AzureBlobStorageImpl implements IAzureBlobStorage {

	@Autowired
	private BlobServiceClient blobServiceClient;

	@Autowired
	private BlobContainerClient blobContainerClient;

	@Value("${azure.storage.container.name}")
	private String containerName;

	@Override
	public String write(Storage storage) {
		String path = getPath(storage);
		BlobClient client = blobContainerClient.getBlobClient(path);
		
		client.upload(storage.getInputStream(), false);
		return path;
	}

	@Override
	public String update(Storage storage) {
		String path = getPath(storage);
		BlobClient client = blobContainerClient.getBlobClient(path);
		
		client.upload(storage.getInputStream(), true);
		return path;
	}

	@Override
	public byte[] read(Storage storage) {
		String path = getPath(storage);
		BlobClient client = blobContainerClient.getBlobClient(path);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		client.download(outputStream);
		final byte[] bytes = outputStream.toByteArray();
		return bytes;
	}

	@Override
	public List<String> getAllBlobs(String path) {
		PagedIterable<BlobItem> blobList = blobContainerClient.listBlobsByHierarchy(path);
		List<String> blobNamesList = new ArrayList<>();
		for (BlobItem blob : blobList) {

			blobNamesList.add(blob.getName());
		}

		return blobNamesList;
	}

	@Override
	public void delete(Storage storage) {
		String path = getPath(storage);
		BlobClient client = blobContainerClient.getBlobClient(path);
		client.delete();
		log.info("Blob is deleted successfully");
	}

	@Override
	public void createContainer() {
		blobServiceClient.createBlobContainer(containerName);
		log.info("Container " + containerName + " created.");
	}

	@Override
	public void deleteContainer() {
		blobServiceClient.deleteBlobContainer(containerName);
		log.info("Container " + containerName + " deleted.");
	}

	private String getPath(Storage storage) {

		if (StringUtils.isNotBlank(storage.getPath()) && StringUtils.isNotBlank(storage.getFileName())) {
			return storage.getPath() + "/" + storage.getFileName();
		}
		return null;
	}
	
	public Storage getStorage(String path, String fileName, String data) {
		Storage storage = new Storage();
		storage.setPath(path);
		storage.setFileName(fileName);
		if(StringUtils.isNotBlank(data)) {
			storage.setInputStream(new ByteArrayInputStream(data.getBytes()));
		}
		return storage;
	}

}
