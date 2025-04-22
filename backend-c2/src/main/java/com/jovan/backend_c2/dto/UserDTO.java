package com.jovan.backend_c2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class UserDTO {

	
	private String firstName;
    private String lastName;
    private String email;
    private String password;
	
}
