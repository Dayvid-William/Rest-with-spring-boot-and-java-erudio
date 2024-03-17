package br.com.dayvid.apirestdocker.integrationtests.controller.withjson;

import br.com.dayvid.apirestdocker.configs.TestConfigs;
import br.com.dayvid.apirestdocker.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.dayvid.apirestdocker.integrationtests.vo.AccountCredentialsVO;
import br.com.dayvid.apirestdocker.integrationtests.vo.PersonVO;
import br.com.dayvid.apirestdocker.integrationtests.vo.TokenVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;

	private static PersonVO person;

	@BeforeAll
	public static void setup(){
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		person = new PersonVO();
	}


	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

		var accessToken = given()
				.basePath("/auth/signin")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(user)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenVO.class) //Só pode ser salvo, pois, tem todos os atributos iguais o da aplicação se não deveria ser salvo como String.
							.getAccessToken();

		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}

	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockPerson();

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(person)
					.when()
				.post()
					.then()
						.statusCode(200)
						.extract()
						.body()
							.asString();

		PersonVO persitedPerson = objectMapper.readValue(content, PersonVO.class);
		person = persitedPerson;

		assertNotNull(persitedPerson);

		assertNotNull(persitedPerson.getId());
		assertNotNull(persitedPerson.getFirstName());
		assertNotNull(persitedPerson.getLastName());
		assertNotNull(persitedPerson.getAddress());
		assertNotNull(persitedPerson.getGender());

		assertTrue(persitedPerson.getId() > 0);

		assertEquals("Nelson", persitedPerson.getFirstName());
		assertEquals("Piquet", persitedPerson.getLastName());
		assertEquals("Brasília - DF - Brasil", persitedPerson.getAddress());
		assertEquals("Male", persitedPerson.getGender());
	}

	@Test
	@Order(2)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		person.setLastName("Piquet Souto Maior");

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(person)
					.when()
				.post()
					.then()
						.statusCode(200)
						.extract()
						.body()
							.asString();

		PersonVO persitedPerson = objectMapper.readValue(content, PersonVO.class);
		person = persitedPerson;

		assertNotNull(persitedPerson);

		assertNotNull(persitedPerson.getId());
		assertNotNull(persitedPerson.getFirstName());
		assertNotNull(persitedPerson.getLastName());
		assertNotNull(persitedPerson.getAddress());
		assertNotNull(persitedPerson.getGender());

		assertEquals(person.getId(), persitedPerson.getId());

		assertEquals("Nelson", persitedPerson.getFirstName());
		assertEquals("Piquet Souto Maior", persitedPerson.getLastName());
		assertEquals("Brasília - DF - Brasil", persitedPerson.getAddress());
		assertEquals("Male", persitedPerson.getGender());
	}

	@Test
	@Order(3)
	public void testFindById() throws JsonProcessingException {
		mockPerson();

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIM, TestConfigs.ORIGIN_ERUDIO)
					.pathParam("id", person.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();

		PersonVO persitedPerson = objectMapper.readValue(content, PersonVO.class);
		person = persitedPerson;

		assertNotNull(persitedPerson);

		assertNotNull(persitedPerson.getId());
		assertNotNull(persitedPerson.getFirstName());
		assertNotNull(persitedPerson.getLastName());
		assertNotNull(persitedPerson.getAddress());
		assertNotNull(persitedPerson.getGender());

		assertEquals(person.getId(), persitedPerson.getId());

		assertEquals("Nelson", persitedPerson.getFirstName());
		assertEquals("Piquet Souto Maior", persitedPerson.getLastName());
		assertEquals("Brasília - DF - Brasil", persitedPerson.getAddress());
		assertEquals("Male", persitedPerson.getGender());
	}

	private void mockPerson() {
		person.setFirstName("Nelson");
		person.setLastName("Piquet");
		person.setAddress("Brasília - DF - Brasil");
		person.setGender("Male");
	}

}
