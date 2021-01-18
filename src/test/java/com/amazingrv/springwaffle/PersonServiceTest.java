package com.amazingrv.springwaffle;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import com.amazingrv.springwaffle.entity.PersonEntity;
import com.amazingrv.springwaffle.mapper.PersonMapperImpl;
import com.amazingrv.springwaffle.repo.PersonRepository;
import com.amazingrv.springwaffle.service.PersonService;
import com.amazingrv.springwaffle.service.impl.PersonServiceImpl;

/**
 * @author rjat3
 */
@SpringBootTest
@ContextConfiguration(classes = { PersonServiceImpl.class, PersonMapperImpl.class })
class PersonServiceTest {
	@MockBean
	private PersonRepository repository;

	@Autowired
	private PersonService service;

	@Test
	void shouldReturnDTOWhenFoundInDB() {
		final var entity = new PersonEntity("123", "ABC");
		Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(entity));
		final var result = service.getPerson("123");
		Assertions.assertThat(result).isNotNull();
		Mockito.verify(repository).findById("123");
	}

}
