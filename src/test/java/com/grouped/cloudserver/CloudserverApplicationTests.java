package com.grouped.cloudserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grouped.cloudserver.models.Position;
import com.grouped.cloudserver.models.User;
import com.grouped.cloudserver.repositories.services.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
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
	void shouldCreateUser() throws Exception {
		int nbUsers = userRepository.findAll().size();
		User user = new User("Jacques", "Fernand", "16/12/1997", new Position(8.65, 8.54));

		mockMvc.perform(MockMvcRequestBuilders
				.post("/user")
				.content(new ObjectMapper().writeValueAsString(user))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

		int newNbUsers = userRepository.findAll().size();
		
		assert(newNbUsers - nbUsers == 1);
	}

	@Test
	void shouldGetUser() throws Exception {
		List<User> users = userRepository.findAll();
		User user;
		if (users.isEmpty()) {
			userRepository.save(new User("Jacques", "Fernand", "16/12/1997", new Position(8.65, 8.54)));
			user = userRepository.findAll().get(0);
		} else {
			user = users.get(0);
		}

		mockMvc.perform(MockMvcRequestBuilders
				.get("/user/{id}", user.getId())
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.getId()));
	}

	@Test
	void shouldGetUsersWithPagination() throws Exception {
		List<User> users = userRepository.findAll();

		if (users.isEmpty()) {
			userRepository.save(new User("Jacques", "Fernand", "16/12/1997", new Position(8.65, 8.54)));
			users = userRepository.findAll();
		}

		users = users.subList(0, Math.min(100, users.size()));
		List<String> ids = new ArrayList<>();

		int N = 0;
		for (int i = 0; i < users.size(); i += 100) {
			users = users.subList(i, Math.min(i + 100, users.size()));

			for (User u : users) {
				ids.add(u.getId());
			}

			mockMvc.perform(MockMvcRequestBuilders
					.get("/user")
					.param("page", Integer.toString(N))
					.accept(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
					.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").value(ids));

			ids.clear();
			N++;
		}
	}

	@Test
	void shouldGetUsers() throws Exception {
		List<User> users = userRepository.findAll();
		List<String> ids = new ArrayList<>();
		for (User u : users) {
			ids.add(u.getId());
		}

		mockMvc.perform(MockMvcRequestBuilders
				.get("/user")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").value(ids));
	}

	@Test
	void shouldUpdateUser() throws Exception {
		List<User> users = userRepository.findAll();
		User user;
		if (users.isEmpty()) {
			userRepository.save(new User("Jacques", "Fernand", "16/12/1997", new Position(8.65, 8.54)));
			user = userRepository.findAll().get(0);
		} else {
			user = users.get(0);
		}
		
		String newFirstName = "Robert";
		user.setFirstName(newFirstName);

		mockMvc.perform(MockMvcRequestBuilders
				.put("/user/{id}", user.getId())
				.content(objectMapper.writeValueAsString(user))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		Optional<User> userObj = userRepository.findById(user.getId());
		assert(userObj.isPresent());
		assert(userObj.get().getFirstName().equals(newFirstName));
	}

	@Test
	void shouldUpdateAllUser() throws Exception {
		List<User> listUsers = new ArrayList<>();
		listUsers.add(new User("Hugues", "Fernando", "16/09/1995", new Position(5.45, 8.25)));
		listUsers.add(new User("Jules", "Bernard", "11/09/1995", new Position(4.45, 51.25)));

		mockMvc.perform(MockMvcRequestBuilders
				.put("/user")
				.content(objectMapper.writeValueAsString(listUsers))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Hugues"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("Jules"));

		List<User> users = userRepository.findAll();
		List<String> firstNames = new ArrayList<>();
		for (User user : users) {
			firstNames.add(user.getFirstName());
		}
		for (User user : listUsers) {
			assert(firstNames.contains(user.getFirstName()));
		}
	}

	@Test
	void shouldDeleteUser() throws Exception {
		List<User> users = userRepository.findAll();
		User user;
		if (users.isEmpty()) {
			userRepository.save(new User("Jacques", "Fernand", "16/12/1997", new Position(8.65, 8.54)));
			user = userRepository.findAll().get(0);
		} else {
			user = users.get(0);
		}

		mockMvc.perform(MockMvcRequestBuilders
				.delete("/user/{id}", user.getId()))
				.andExpect(status().isOk());

		Optional<User> userObj = userRepository.findById(user.getId());
		assert(userObj.isEmpty());
	}

	@Test
	void shouldDeleteAllUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/user"))
				.andExpect(status().isOk());

		List<User> users = userRepository.findAll();
		assert(users.isEmpty());
	}
}
