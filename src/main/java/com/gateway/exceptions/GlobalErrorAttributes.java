package com.gateway.exceptions;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
	
	private HttpStatus status = HttpStatus.BAD_REQUEST;
	
	public GlobalErrorAttributes() {
		super(false);
	}
	
	@Override
	public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
		return assembleError(request);
	}

	private Map<String, Object> assembleError(ServerRequest request) {
		Map<String, Object> errorAttributes =  new LinkedHashMap<String, Object>();
		Throwable error = getError(request);
		if(error instanceof ServerException) {
			errorAttributes.put("code", ((ServerException) error).getCode().getCode());
			errorAttributes.put("data", error.getMessage());
		
		} else {
			errorAttributes.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
            errorAttributes.put("data", "GATEWAY ERROR");
		}
		return errorAttributes;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}