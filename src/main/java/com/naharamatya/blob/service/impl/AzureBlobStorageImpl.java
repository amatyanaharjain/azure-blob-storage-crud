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
import com.azure.storage.blob.models.BlobStorageException;
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
		try {
			String path = getPath(storage);
			BlobClient client = blobContainerClient.getBlobClient(path);

			client.upload(storage.getInputStream(), false);
			return path;
		} catch (BlobStorageException e) {
			return e.getMessage();
		} catch (RuntimeException e) {
			return e.getMessage();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@Override
	public String update(Storage storage) {
		try {
			String path = getPath(storage);
			BlobClient client = blobContainerClient.getBlobClient(path);

			client.upload(storage.getInputStream(), true);
			return path;
		} catch (BlobStorageException e) {
			return e.getMessage();
		} catch (RuntimeException e) {
			return e.getMessage();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@Override
	public byte[] read(Storage storage) {
		try {
			String path = getPath(storage);
			BlobClient client = blobContainerClient.getBlobClient(path);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			client.download(outputStream);
			final byte[] bytes = outputStream.toByteArray();
			return bytes;
		} catch (BlobStorageException e) {
			System.out.println(e.getMessage());
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<String> getAllBlobs(String path) {
		try {
			PagedIterable<BlobItem> blobList = blobContainerClient.listBlobsByHierarchy(path);
			List<String> blobNamesList = new ArrayList<>();
			for (BlobItem blob : blobList) {

				blobNamesList.add(blob.getName());
			}

			return blobNamesList;
		} catch (BlobStorageException e) {
			System.out.println(e.getMessage());
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public void delete(Storage storage) {
		try {
			String path = getPath(storage);
			BlobClient client = blobContainerClient.getBlobClient(path);
			client.delete();
			log.info("Blob is deleted successfully");
		} catch (BlobStorageException e) {
			System.out.println(e.getMessage());
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void createContainer() {
		try {
			blobServiceClient.createBlobContainer(containerName);
			log.info("Container " + containerName + " created.");
		} catch (BlobStorageException e) {
			System.out.println(e.getMessage());
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void deleteContainer() {
		try {
			blobServiceClient.deleteBlobContainer(containerName);
			log.info("Container " + containerName + " deleted.");
		} catch (BlobStorageException e) {
			System.out.println(e.getMessage());
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
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
		if (StringUtils.isNotBlank(data)) {
			storage.setInputStream(new ByteArrayInputStream(data.getBytes()));
		}
		return storage;
	}

}
