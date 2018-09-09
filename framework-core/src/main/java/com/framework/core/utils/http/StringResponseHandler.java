package com.framework.core.utils.http;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public abstract class StringResponseHandler implements ResponseHandler<String> {


	@Override
	public String success(ResponseWrap response) throws IOException {
		RequestWrap request = response.getRequest();
		String content = IOUtils.toString((InputStream) response.getContent(), request.getRemoteCharset());
		return content;
	}

	/**
	 * 
	 * do nothing
	 * 
	 */
	@Override
	public String error(ResponseWrap response) throws IOException {
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