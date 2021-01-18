package com.amazingrv.springwaffle.rest;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazingrv.springwaffle.constants.Constants;
import com.amazingrv.springwaffle.constants.Routes;
import com.amazingrv.springwaffle.dto.PersonDTO;
import com.amazingrv.springwaffle.rest.util.ResponseUtils;
import com.amazingrv.springwaffle.service.PersonService;

/**
 * @author rjat3
 */
@RestController
@RequestMapping(Routes.API_PERSON)
public class PersonController {
	private final PersonService service;

	public PersonController(PersonService service) {
		this.service = service;
	}

	/**
	 * End point to delete person from db based on id
	 *
	 * @param id key for person to delete
	 * @return response
	 */
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deletePersonFromDB(@PathVariable String id) {
		try {
			final String userId = service.deletePerson(id);
			return ResponseUtils.getResponse(Constants.USER_ID, userId);
		} catch (final EntityNotFoundException ex) {
			return ResponseEntity.notFound().build();
		} catch (final Exception ex) {
			return ResponseUtils.getErrorResponse(ex);
		}
	}

	/**
	 * End point to update person details in db based on details received
	 *
	 * @param person person details
	 * @return response
	 */
	@PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> editPersonInDB(@RequestBody PersonDTO person) {
		try {
			final String userId = service.updatePerson(person);
			return ResponseUtils.getResponse(Constants.USER_ID, userId);
		} catch (final EntityNotFoundException ex) {
			return ResponseEntity.notFound().build();
		} catch (final Exception ex) {
			return ResponseUtils.getErrorResponse(ex);
		}
	}

	/**
	 * End point to fetch all persons from db
	 *
	 * @param pageable pagination details
	 * @return response
	 */
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllPersons(@PageableDefault(page = 0, size = 10) Pageable pageable) {
		try {
			final List<PersonDTO> persons = service.getAllPersons(pageable);
			return ResponseUtils.getResponse(Constants.PERSONS, persons);
		} catch (final Exception ex) {
			return ResponseUtils.getErrorResponse(ex);
		}
	}

	/**
	 * End point to fetch a persons from db based on id
	 *
	 * @param id key for person to fetch
	 * @return response
	 */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getPerson(@PathVariable("id") String id) {
		try {
			final PersonDTO person = service.getPerson(id);
			return ResponseUtils.getResponse(Constants.PERSON, person);
		} catch (final EntityNotFoundException ex) {
			return ResponseEntity.notFound().build();
		} catch (final Exception ex) {
			return ResponseUtils.getErrorResponse(ex);
		}
	}

	/**
	 * End point to insert a person in db based on details received
	 *
	 * @param person person details
	 * @return response
	 */
	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> savePersonToDB(@Valid @RequestBody PersonDTO person) {
		try {
			final String userId = service.createPerson(person);
			return ResponseUtils.getResponse(Constants.USER_ID, userId);
		} catch (final Exception ex) {
			return ResponseUtils.getErrorResponse(ex);
		}
	}
}
