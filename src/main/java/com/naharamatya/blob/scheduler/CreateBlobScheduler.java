package com.naharamatya.blob.scheduler;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.naharamatya.blob.model.Storage;
import com.naharamatya.blob.service.impl.AzureBlobStorageImpl;

@Component
public class CreateBlobScheduler {
	
	@Autowired
	private AzureBlobStorageImpl storageImpl;
	
	@Scheduled(initialDelay = 10000, fixedDelay = 10000)
	public void createBlob() {
		System.out.println(LocalDateTime.now());
		
		Storage storage = storageImpl.getStorage("folder1/folder2",LocalDateTime.now()+".txt",LocalDateTime.now().toString());
		String writeStr = storageImpl.write(storage);
		
	}

}
