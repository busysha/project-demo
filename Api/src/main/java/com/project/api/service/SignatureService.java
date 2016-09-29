package com.project.api.service;

import java.util.Map;


public interface SignatureService {
	
	boolean isPassSignature(String sign, Map<String, String> params);

}
