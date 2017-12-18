package fr.iocean.dta.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import fr.iocean.dta.IntegrationTest;
import fr.iocean.dta.user.model.User;
import fr.iocean.dta.user.service.UserService;

@Sql("classpath:test-user-data.sql")
public class UserIT extends IntegrationTest {

	@Autowired
	UserService userService;

	@Test
	@WithMockUser(authorities = "TEST")
	public void testCreate() throws Exception {
		User u = new User("test");
		u.setLogin("test");

		this.mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				.content(jsonHelper.serialize(u))).andExpect(status().isCreated());
		this.mockMvc.perform(get("/api/users")).andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$", hasSize(3))).andExpect(status().isOk());

	}

	@Test
	@WithMockUser
	public void testCreatePreconditionFail() throws Exception {
		User u = new User("test");
		u.setLogin(null);
		u.setPassword(null);

		this.mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				.content(jsonHelper.serialize(u)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(jsonPath("$[*].field", containsInAnyOrder("password", "login")))
		.andExpect(jsonPath("$[0].objectName").value("user"))
		.andExpect(jsonPath("$[*].code", containsInAnyOrder("NotBlank", "NotBlank")))
		.andExpect(status().isPreconditionFailed());

	}
	
	@Test
	@WithMockUser
	public void testUpdate() throws Exception {
		User user = userService.findById(1l);
		Assert.assertEquals("admin@iocean.fr", user.getLogin());

		user.setLogin("test@iocean.fr");
		this.mockMvc
				.perform(put("/api/users/{id}", 1).contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
						.content(jsonHelper.serialize(user)))
				.andExpect(jsonPath("$.login").value("test@iocean.fr")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "TEST")
	public void testGetUser() throws Exception {
		this.mockMvc.perform(get("/api/users/{id}", 1)).andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.login").value("admin@iocean.fr"))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "TEST")
	public void testGetNotFoundUser() throws Exception {
		this.mockMvc.perform(get("/api/users/{id}", 55)).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isNotFound());
	}
	
	@Test
	@WithMockUser(authorities = "TEST")
	public void testGetAllUsers() throws Exception {
		this.mockMvc.perform(get("/api/users")).andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$", hasSize(2))).andExpect(status().isOk());
	}

}
