package com.amazingrv.springwaffle.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.amazingrv.springwaffle.dto.PersonDTO;

/**
 * @author rjat3
 */
public interface PersonService {

	String createPerson(PersonDTO person);

	String deletePerson(String id);

	List<PersonDTO> getAllPersons(Pageable pageable);

	PersonDTO getPerson(String id);

	String updatePerson(PersonDTO person);
}
