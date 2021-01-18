package com.amazingrv.springwaffle.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO class for person
 *
 * @author rjat3
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PersonDTO {
	@NotBlank(message = "Id is mandatory")
	private String uid;

	@NotBlank(message = "Name is mandatory")
	private String firstName;
}
