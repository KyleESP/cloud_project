package com.grouped.cloudserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grouped.cloudserver.controllers.UserController;
import com.grouped.cloudserver.models.User;
import com.grouped.cloudserver.repositories.services.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CloudserverApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserRepository userRepository;

	@Test
	void shouldGetUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/user/{id}", 1)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2));
	}

	@Test
	void shouldGetUsers() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/user")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.users").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.users[*].id").isNotEmpty());
	}

	@Test
	void shouldCreateUser() throws Exception {
		User user = new User("Guy", "Fernand", "16/12/1997");

		mockMvc.perform(MockMvcRequestBuilders
				.post("/user")
				.content(new ObjectMapper().writeValueAsString(user))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
	}

	@Test
	void shouldUpdateUser() throws Exception {
		User user = new User("Georges", "Gonzalez", "16/12/1996");

		mockMvc.perform(MockMvcRequestBuilders
				.put("/user/{id}", 1)
				.content(objectMapper.writeValueAsString(user))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent())
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Georges"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Gonzalez"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.birthDay").value("16/12/1996"));
	}

	@Test
	void shouldUpdateAllUser() throws Exception {
		Optional<User> userEntity = userRepository.findById("1");
		assert(userEntity.isPresent());

		User user = userEntity.get();
		user.setFirstName("Jacques");

		Optional<User> userEntity2 = userRepository.findById("2");
		assert(userEntity2.isPresent());

		User user2 = userEntity2.get();
		user.setFirstName("Fréféric");

		List<User> listUser = new ArrayList<>();
		listUser.add(user);
		listUser.add(user2);

		mockMvc.perform(put("/user")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(listUser)))
				.andExpect(status().isOk());

		userEntity = userRepository.findById("1");
		assert(userEntity.isPresent());
		assertThat(userEntity.get().getFirstName()).isEqualTo("Jacques");

		userEntity2 = userRepository.findById("2");
		assert(userEntity2.isPresent());
		assertThat(userEntity2.get().getFirstName()).isEqualTo("Frédéric");
	}

	@Test
	void shouldDeleteUser() throws Exception {
		mockMvc.perform(delete("/user")
				.param("id", "1"))
				.andExpect(status().isOk());

		Optional<User> userEntity = userRepository.findById("1");
		assert(userEntity.isEmpty());
	}

	@Test
	void shouldDeleteAllUser() throws Exception {
		mockMvc.perform(delete("/user"))
				.andExpect(status().isOk());

		List<User> userEntity = userRepository.findAll();
		assert(userEntity.isEmpty());
	}
}
