package fr.dta;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import fr.dta.book.User;
import fr.dta.entities.Book;
import fr.dta.service.BookService;


@Sql("classpath:test-user-data.sql")
public class BserlT extends IntegrationTest{
	
	@Autowired
	BookService bookService;
	
	@Test
	@WithMockUser(authorities = "TEST")
	public void testCreate() throws Exception {
		Book b = new Book();
		b.setLogin("test");

		this.mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				.content(jsonHelper.serialize(b))).andExpect(status().isCreated());
		this.mockMvc.perform(get("/api/users")).andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$", hasSize(3))).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	public void testCreatePreconditionFail() throws Exception {
		Book b = new Book();
		b.setLogin(null);
		b.setPassword(null);

		this.mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				.content(jsonHelper.serialize(b)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(jsonPath("$[*].field", containsInAnyOrder("password", "login")))
		.andExpect(jsonPath("$[0].objectName").value("user"))
		.andExpect(jsonPath("$[*].code", containsInAnyOrder("NotBlank", "NotBlank")))
		.andExpect(status().isPreconditionFailed());

	}
	
	@Test
	@WithMockUser
	public void testUpdate() throws Exception {
		Book user = bookService.findById(1l);
		Assert.assertEquals("admin@iocean.fr", book.getLogin());

		book.setLogin("test@iocean.fr");
		this.mockMvc
				.perform(put("/api/users/{id}", 1).contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
						.content(jsonHelper.serialize(book)))
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
