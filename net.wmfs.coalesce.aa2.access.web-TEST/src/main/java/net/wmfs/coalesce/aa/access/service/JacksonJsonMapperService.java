package net.wmfs.coalesce.aa.access.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class JacksonJsonMapperService implements JsonMapperService {

	private final ObjectMapper mapper;

	JacksonJsonMapperService(final ObjectMapper mapper) {
		super();
		this.mapper = checkNotNull(mapper);
	}

	@Override
	@SneakyThrows
	public String toJson(Object instance) {
		return mapper.writeValueAsString(instance);
	}

	@Override
	public <T> T fromJson(String json, Class<T> clazz) throws IOException {
		return mapper.readValue(json, clazz);
	}
}
