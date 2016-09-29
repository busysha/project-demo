package com.project.api.service;

public interface AuthorizeService {

	public boolean checkUserTicket(String userId, String ticket);
	
	public void updateUserTicket(String ticketKey, String ticket);
	
	
	
}
