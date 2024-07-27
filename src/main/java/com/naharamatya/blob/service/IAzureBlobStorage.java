package com.naharamatya.blob.service;

import java.util.List;

import com.naharamatya.blob.model.Storage;

public interface IAzureBlobStorage {

	public String write(Storage storage );
	
	public String update(Storage storage);
	
	public byte[] read(Storage storage);
	
	public void delete(Storage storage);
	
	public void createContainer();
	
	public void deleteContainer();

	public List<String> getAllBlobs(String path);
}
