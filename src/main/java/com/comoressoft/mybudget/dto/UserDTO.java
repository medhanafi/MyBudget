package com.comoressoft.mybudget.dto;

public class UserDTO {

	private Long id;
	private String username;
	private boolean accountExpired;

	private boolean accountLocked;

	private boolean credentialsExpired;
	private boolean enabled;

	private FamilyDTO family;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAccountExpired() {
		return accountExpired;
	}

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public boolean isAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public FamilyDTO getFamily() {
		return family;
	}

	public void setFamily(FamilyDTO family) {
		this.family = family;
	}
	
	

}
