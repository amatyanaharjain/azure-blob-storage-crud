package com.naharamatya.blob.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naharamatya.blob.model.Storage;
import com.naharamatya.blob.service.impl.AzureBlobStorageImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AzureBlobStorageController {

	@Autowired
	private AzureBlobStorageImpl storageImpl;
	
	@PostMapping("/create-container")
	public void createContainer() {
		storageImpl.createContainer();
	}
	
	@DeleteMapping("/delete-container")
	public void deleteContainer() {
		storageImpl.deleteContainer();
	}

	@PostMapping("/write-blob")
	public void writeBlob() {
		Storage storage = storageImpl.getStorage("cust","cust1.txt","Hello World");
		String writeStr = storageImpl.write(storage);
		log.info(writeStr);
	}
	
	@PutMapping("/update-blob")
	public void updateBlob() {
		Storage storage = storageImpl.getStorage("cust","cust1.txt","Updated Text");
		String writeStr = storageImpl.update(storage);
		log.info(writeStr);
	}
	
	@DeleteMapping("/delete-blob")
	public void deleteBlob() {
		Storage storage = storageImpl.getStorage("cust","cust1.txt","");
		storageImpl.delete(storage);
	}
	
	@GetMapping("/list-blob")
	public void listBlob() {
		String path = "cust/";
		List<String> files = storageImpl.getAllBlobs(path);
		System.out.println(files);
	}
	
	@GetMapping("/read-blob")
	public void readBlob() {
		Storage storage = storageImpl.getStorage("cust","cust1.txt","");
		byte[] readContent = storageImpl.read(storage);
		System.out.println(readContent);
	}
	
}
