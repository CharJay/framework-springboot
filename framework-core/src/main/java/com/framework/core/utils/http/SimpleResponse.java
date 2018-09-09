package com.framework.core.utils.http;

public class SimpleResponse {

	private ResponseWrap response;

	private String content;

	public SimpleResponse(ResponseWrap response) {
		super();
		this.response = response;
	}

	public boolean isSuccessful() {
		return response.isSuccessful();
	}

	public boolean isMoved(){
		return response.getStatusCode() == 301 || response.getStatusCode() == 302;
	}
	
	public ResponseWrap getResponse() {
		return response;
	}

	public void setResponse(ResponseWrap response) {
		this.response = response;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
