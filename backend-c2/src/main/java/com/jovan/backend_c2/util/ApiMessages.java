package com.jovan.backend_c2.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ApiMessages {

	
	
	USER_NOT_FOUND_BY_ACCOUNT("User not found for the given account number: %s"),
	TOKEN_ALREADY_EXISTS_ERROR("Token already exists"),
	TOKEN_NOT_FOUND_ERROR("Token not found"),
    TOKEN_EMPTY_ERROR("Token is empty"),
    TOKEN_EXPIRED_ERROR("Token has expired"),
    TOKEN_INVALID_ERROR("Token is invalid"),
    TOKEN_ISSUED_SUCCESS("{ \"token\": \"%s\" }"),
    TOKEN_MALFORMED_ERROR("Token is malformed"),
    TOKEN_SIGNATURE_INVALID_ERROR("Token signature is invalid"),
    TOKEN_UNSUPPORTED_ERROR("Token is not supported"),
    PASSWORD_CONTAINS_WHITESPACE_ERROR("Password cannot contain any whitespace characters"),
    PASSWORD_EMPTY_ERROR("Password cannot be empty"),
    PASSWORD_INVALID_ERROR("Invalid password"),
    PASSWORD_REQUIREMENTS_ERROR("Password must contain at least %s"),
    PASSWORD_RESET_FAILURE("Failed to reset password"),
    PASSWORD_RESET_SUCCESS("{\"message\": \"Password reset successfully\"}"),
    PASSWORD_RESET_TOKEN_ISSUED("{\"passwordResetToken\": \"%s\"}"),
    PASSWORD_TOO_LONG_ERROR("Password must be less than 128 characters long"),
    PASSWORD_TOO_SHORT_ERROR("Password must be at least 8 characters long"),
    USER_ADDRESS_EMPTY_ERROR("Address cannot be empty"),
    USER_COUNTRY_CODE_EMPTY_ERROR("Country code cannot be empty"),
    USER_COUNTRY_CODE_INVALID_ERROR("Invalid country code: %s"),
    USER_DETAILS_EMPTY_ERROR("User details cannot be empty"),
    USER_EMAIL_ADDRESS_INVALID_ERROR("Invalid email: %s"),
    USER_EMAIL_ALREADY_EXISTS_ERROR("Email already exists"),
    USER_EMAIL_EMPTY_ERROR("Email cannot be empty"),
    USER_LOGOUT_SUCCESS("User logged out successfully %s"),
    USER_NAME_EMPTY_ERROR("Name cannot be empty"),
    USER_NOT_FOUND_BY_EMAIL("User not found for the given email: %s"),
    USER_NOT_FOUND_BY_IDENTIFIER("User not found for the given identifier: %s"),
    USER_PHONE_NUMBER_ALREADY_EXISTS_ERROR("Phone number already exists"),
    USER_PHONE_NUMBER_EMPTY_ERROR("Phone number cannot be empty"),
    USER_PHONE_NUMBER_INVALID_ERROR("Invalid phone number: %s for country code: %s"),
    USER_REGISTRATION_SUCCESS("User registered successfully"),
    USER_UPDATE_SUCCESS("User updated successfully");
	
	@Getter
	private final String message;
}
