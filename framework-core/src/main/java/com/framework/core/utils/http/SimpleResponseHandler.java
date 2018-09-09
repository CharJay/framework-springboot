package com.framework.core.utils.http;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class SimpleResponseHandler implements ResponseHandler<SimpleResponse> {

	private final org.slf4j.Logger LOGGER;

	private boolean fetchContent = true;

	public SimpleResponseHandler(org.slf4j.Logger LOGGER, boolean fetchContent) {
		this.LOGGER = LOGGER;
		this.fetchContent = fetchContent;
	}

	@Override
	public boolean complete(ResponseWrap response) throws IOException {
		return true;
	}

	@Override
	public SimpleResponse success(ResponseWrap response) throws IOException {
		LOGGER.debug("req success. code={},url={}", response.getStatusCode(), response.getUri(), response.getUri());
		SimpleResponse resp = new SimpleResponse(response);
		if (fetchContent && response.getContent() != null) {
			RequestWrap request = response.getRequest();
			String content = IOUtils.toString((InputStream) response.getContent(), request.getRemoteCharset());
			resp.setContent(content);
		}
		return resp;
	}

	@Override
	public SimpleResponse error(ResponseWrap response) throws IOException {
		if(response.getStatusCode() == 301 || response.getStatusCode() == 302){
			LOGGER.debug("req moved. code={},url={}", response.getStatusCode(), response.getUri());
		}else{
			LOGGER.error("req error. code={},url={},error={}", response.getStatusCode(), response.getUri(), response.getErrorResponseAsString());
		}
		SimpleResponse resp = new SimpleResponse(response);
		resp.setContent(response.getErrorResponseAsString());
		return resp;
	}

}
