package net.wmfs.coalesce.aa.access.service;

import java.io.IOException;

public interface JsonMapperService {
	
	// serializing an object to json
	String toJson(Object instance) throws IOException;

	// deserializing json to object
	<T> T fromJson(String json, Class<T> clazz) throws IOException;
}
