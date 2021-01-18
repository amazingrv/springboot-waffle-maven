package com.amazingrv.springwaffle.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.amazingrv.springwaffle.dto.PersonDTO;
import com.amazingrv.springwaffle.entity.PersonEntity;
import com.amazingrv.springwaffle.mapper.PersonMapper;
import com.amazingrv.springwaffle.repo.PersonRepository;
import com.amazingrv.springwaffle.service.PersonService;

/**
 * Basic CRUD operation implements for person
 *
 * @author rjat3
 */
@Service
public class PersonServiceImpl implements PersonService {
	private final PersonRepository repository;
	private final PersonMapper mapper;

	public PersonServiceImpl(PersonRepository repository, PersonMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	/**
	 * Method to insert person into db
	 *
	 * @param person person details
	 * @return id for the created entry
	 */
	@Override
	public String createPerson(PersonDTO person) {
		final PersonEntity personEntity = repository.save(mapper.toPersonEntity(person));
		return personEntity.getUid();
	}

	/**
	 * Method to remove a person from db
	 *
	 * @param id primary key for person
	 * @return id of deleted entry
	 */
	@Override
	public String deletePerson(String id) {
		final Optional<PersonEntity> personEntity = repository.findById(id);
		final PersonEntity entity = personEntity.orElseThrow(EntityNotFoundException::new);
		repository.delete(entity);
		return id;
	}

	/**
	 * Method to fetch persons from db
	 *
	 * @param pageable pagination information
	 * @return list of person details
	 */
	@Override
	public List<PersonDTO> getAllPersons(Pageable pageable) {
		final List<PersonEntity> persons = repository.findAll(pageable).getContent();
		return persons.stream().filter(Objects::nonNull).map(mapper::toPersonDTO).collect(Collectors.toList());
	}

	/**
	 * Method to fetch person from db
	 *
	 * @param id primary key value
	 * @return person details
	 */
	@Override
	public PersonDTO getPerson(String id) {
		final Optional<PersonEntity> personEntity = repository.findById(id);
		final PersonEntity entity = personEntity.orElseThrow(EntityNotFoundException::new);
		return mapper.toPersonDTO(entity);
	}

	/**
	 * Method to update a person in db
	 *
	 * @param person person details to update
	 * @return id for updated entry
	 */
	@Override
	public String updatePerson(PersonDTO person) {
		final Optional<PersonEntity> personEntity = repository.findById(person.getUid());
		final PersonEntity entity = personEntity.orElseThrow(EntityNotFoundException::new);
		repository.save(entity);
		return entity.getUid();
	}
}
