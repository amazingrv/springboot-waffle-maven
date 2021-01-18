package com.amazingrv.springwaffle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.amazingrv.springwaffle.entity.PersonEntity;
import com.amazingrv.springwaffle.repo.PersonRepository;

/**
 * @author rjat3
 */
@SpringBootTest
@AutoConfigureMockMvc
class SpringWaffleIntegrationApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private PersonRepository repository;

	@BeforeEach
	public void setup() {
		final PersonEntity personEntity = new PersonEntity();
		personEntity.setFirstName("TestABC");
		personEntity.setUid("123");
		repository.save(personEntity);
	}

	@WithMockUser()
	@Test
	void shouldReturnNotFound() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/person/9999").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@WithMockUser()
	@Test
	void shouldReturnPersonAllUsers() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/person/all").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content()
						.json("{\"persons\":[{\"uid\":\"123\",\"firstName\":\"TestABC\"}]}"));
	}

	@WithMockUser()
	@Test
	void shouldReturnPersonFound() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/person/123").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content()
						.json("{\"person\":{\"uid\":\"123\",\"firstName\":\"TestABC\"}}"));
	}
}
