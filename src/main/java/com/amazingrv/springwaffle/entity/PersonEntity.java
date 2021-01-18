package com.amazingrv.springwaffle.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class for Person in DB
 *
 * @author rjat3
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntity {
	@Id
	private String uid;
	private String firstName;
}
