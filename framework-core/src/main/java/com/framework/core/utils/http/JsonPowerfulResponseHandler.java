package com.framework.core.utils.http;

import java.io.IOException;
import java.io.InputStream;

import com.framework.core.utils.json.CustomJsonUtils;
import org.apache.commons.io.IOUtils;

public abstract class JsonPowerfulResponseHandler<T> implements ResponseHandler<T> {

	private Class<T> dataType;
	private Class<?>[] elementTypes;

	public JsonPowerfulResponseHandler(Class<T> dataType, Class<?>... elementTypes) {
		this.dataType = dataType;
		this.elementTypes = elementTypes;
	}

	@Override
	public T success(ResponseWrap response) throws IOException {
		RequestWrap request = response.getRequest();
		String content = IOUtils.toString((InputStream) response.getContent(), request.getRemoteCharset());
		return CustomJsonUtils.getInstance().readValue(content, dataType, elementTypes);
	}

	/**
	 * 
	 * do nothing
	 * 
	 */
	@Override
	public T error(ResponseWrap response) throws IOException {
		return null;
	}

	/**
	 * 
	 * return true
	 * 
	 */
	@Override
	public boolean complete(ResponseWrap response) throws IOException {
		return true;
	}

}