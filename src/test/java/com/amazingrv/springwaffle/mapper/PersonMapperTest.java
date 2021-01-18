package com.amazingrv.springwaffle.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.amazingrv.springwaffle.dto.PersonDTO;
import com.amazingrv.springwaffle.entity.PersonEntity;

/**
 * @author rjat3
 */
@SpringBootTest(classes = PersonMapperImpl.class)
class PersonMapperTest {
	private static PersonEntity initialEntity;

	private static PersonDTO initialDTO;
	private static PersonDTO expectedDTO;
	private static PersonEntity expectedEntity;

	@BeforeAll
	static void setup() {
		PersonMapperTest.initialEntity = new PersonEntity("123", "ABC");
		PersonMapperTest.expectedDTO = new PersonDTO("123", "ABC");

		PersonMapperTest.initialDTO = new PersonDTO("234", "XYZ");
		PersonMapperTest.expectedEntity = new PersonEntity("234", "XYZ");
	}

	@Autowired
	private PersonMapper mapper;

	@Test
	void shouldReturnDTOWhenProvidedEntity() {
		final PersonDTO actualDTO = mapper.toPersonDTO(PersonMapperTest.initialEntity);
		Assertions.assertThat(actualDTO).isNotNull().isEqualTo(PersonMapperTest.expectedDTO);
	}

	@Test
	void shouldReturnEntityWhenProvidedDTO() {
		final PersonEntity actualEntity = mapper.toPersonEntity(PersonMapperTest.initialDTO);
		Assertions.assertThat(actualEntity).isNotNull().isEqualTo(PersonMapperTest.expectedEntity);
	}
}
