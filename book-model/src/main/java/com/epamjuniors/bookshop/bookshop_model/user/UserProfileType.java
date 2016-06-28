/*
 * Enumeration describing all possible user profile types
 */

package com.epamjuniors.bookshop.bookshop_model.user;

import java.io.Serializable;

public enum UserProfileType implements Serializable {
	USER("USER"),
	DBA("DBA"),
	ADMIN("ADMIN");

	String userProfileType;

	private UserProfileType(String userProfileType) {
		this.userProfileType = userProfileType;
	}

	public String getUserProfileType() {
		return userProfileType;
	}

}