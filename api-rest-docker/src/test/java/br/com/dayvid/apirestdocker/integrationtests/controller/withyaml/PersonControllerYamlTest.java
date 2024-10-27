package br.com.dayvid.apirestdocker.integrationtests.controller.withyaml;

import br.com.dayvid.apirestdocker.configs.TestConfigs;
import br.com.dayvid.apirestdocker.integrationtests.controller.withyaml.mapper.YMLMapper;
import br.com.dayvid.apirestdocker.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.dayvid.apirestdocker.integrationtests.vo.AccountCredentialsVO;
import br.com.dayvid.apirestdocker.integrationtests.vo.PagedModels.PagedModelPerson;
import br.com.dayvid.apirestdocker.integrationtests.vo.PersonVO;
import br.com.dayvid.apirestdocker.integrationtests.vo.TokenVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerYamlTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static YMLMapper objectMapper;

    private static PersonVO person;

    @BeforeAll
    public static void setup() {
        objectMapper = new YMLMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        person = new PersonVO();
    }

    @Test
    @Order(0)
    public void authorization() throws JsonMappingException, JsonProcessingException {

        AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

        var accessToken = given()
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(
                                                TestConfigs.CONTENT_TYPE_YML,
                                                ContentType.TEXT)))
                .basePath("/auth/signin")
                    .port(TestConfigs.SERVER_PORT)
                    .contentType(TestConfigs.CONTENT_TYPE_YML)
                    .accept(TestConfigs.CONTENT_TYPE_YML)
                .body(user, objectMapper)
                    .when()
                .post()
                    .then()
                        .statusCode(200)
                        .extract()
                        .body()
                            .as(TokenVO.class, objectMapper)
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

        var persistedPerson = given().spec(specification)
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(
                                                TestConfigs.CONTENT_TYPE_YML,
                                                ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .body(person, objectMapper)
                    .when()
                .post()
                    .then()
                        .statusCode(200)
                        .extract()
                        .body()
                            .as(PersonVO.class, objectMapper);


        assertNotNull(persistedPerson);

        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirstName());
        assertNotNull(persistedPerson.getLastName());
        assertNotNull(persistedPerson.getAddress());
        assertNotNull(persistedPerson.getGender());
        assertTrue(persistedPerson.getEnabled());

        assertTrue(persistedPerson.getId() > 0);

        assertEquals("Nelson", persistedPerson.getFirstName());
        assertEquals("Piquet", persistedPerson.getLastName());
        assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(2)
    public void testUpdate() throws JsonMappingException, JsonProcessingException {
        person.setLastName("Piquet Souto Maior");

        var persistedPerson = given().spec(specification)
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(
                                                TestConfigs.CONTENT_TYPE_YML,
                                                ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .body(person, objectMapper)
                    .when()
                .post()
                    .then()
                        .statusCode(200)
                        .extract()
                        .body()
                            .as(PersonVO.class, objectMapper);

        person = persistedPerson;

        assertNotNull(persistedPerson);

        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirstName());
        assertNotNull(persistedPerson.getLastName());
        assertNotNull(persistedPerson.getAddress());
        assertNotNull(persistedPerson.getGender());
        assertTrue(persistedPerson.getEnabled());

        assertEquals(person.getId(), persistedPerson.getId());

        assertEquals("Nelson", persistedPerson.getFirstName());
        assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
        assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(3)
    public void testDisablePersonById() throws JsonMappingException, JsonProcessingException {

        var persistedPerson = given().spec(specification)
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(
                                                TestConfigs.CONTENT_TYPE_YML,
                                                ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .pathParam("id", person.getId())
                .when()
                    .patch("{id}")
                .then()
                    .statusCode(200)
                        .extract()
                        .body()
                            .as(PersonVO.class, objectMapper);

        person = persistedPerson;

        assertNotNull(persistedPerson);

        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirstName());
        assertNotNull(persistedPerson.getLastName());
        assertNotNull(persistedPerson.getAddress());
        assertNotNull(persistedPerson.getGender());
        assertFalse(persistedPerson.getEnabled());

        assertEquals(person.getId(), persistedPerson.getId());

        assertEquals("Nelson", persistedPerson.getFirstName());
        assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
        assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(4)
    public void testFindById() throws JsonMappingException, JsonProcessingException {
        mockPerson();

        var persistedPerson = given().spec(specification)
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(
                                                TestConfigs.CONTENT_TYPE_YML,
                                                ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                    .pathParam("id", person.getId())
                    .when()
                .get("{id}")
                    .then()
                        .statusCode(200)
                        .extract()
                        .body()
                            .as(PersonVO.class, objectMapper);

        person = persistedPerson;

        assertNotNull(persistedPerson);

        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirstName());
        assertNotNull(persistedPerson.getLastName());
        assertNotNull(persistedPerson.getAddress());
        assertNotNull(persistedPerson.getGender());
        assertFalse(persistedPerson.getEnabled());

        assertEquals(person.getId(), persistedPerson.getId());

        assertEquals("Nelson", persistedPerson.getFirstName());
        assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
        assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());
    }
    @Test
    @Order(5)
    public void testDelete() throws JsonMappingException, JsonProcessingException {

        given().spec(specification)
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(
                                                TestConfigs.CONTENT_TYPE_YML,
                                                ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                    .pathParam("id", person.getId())
                    .when()
                .delete("{id}")
                    .then()
                        .statusCode(204);
    }

    @Test
    @Order(6)
    public void testFindAll() throws JsonMappingException, JsonProcessingException {

        var wrapper = given().spec(specification)
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(
                                                TestConfigs.CONTENT_TYPE_YML,
                                                ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .queryParams("page", 3, "size", 10, "direction", "asc")
                    .when()
                .get()
                    .then()
                        .statusCode(200)
                        .extract()
                        .body()
                            .as(PagedModelPerson.class, objectMapper);

        var people = wrapper.getContent();

        PersonVO foundPersonOne = people.get(0);

        assertNotNull(foundPersonOne.getId());
        assertNotNull(foundPersonOne.getFirstName());
        assertNotNull(foundPersonOne.getLastName());
        assertNotNull(foundPersonOne.getAddress());
        assertNotNull(foundPersonOne.getGender());
        assertTrue(foundPersonOne.getEnabled());

        assertEquals(901, foundPersonOne.getId());

        assertEquals("Alida", foundPersonOne.getFirstName());
        assertEquals("Dunford", foundPersonOne.getLastName());
        assertEquals("8416 Loeprich Park", foundPersonOne.getAddress());
        assertEquals("Female", foundPersonOne.getGender());

        PersonVO foundPersonSix = people.get(5);

        assertNotNull(foundPersonSix.getId());
        assertNotNull(foundPersonSix.getFirstName());
        assertNotNull(foundPersonSix.getLastName());
        assertNotNull(foundPersonSix.getAddress());
        assertNotNull(foundPersonSix.getGender());
        assertFalse(foundPersonSix.getEnabled());

        assertEquals(133, foundPersonSix.getId());

        assertEquals("Amabel", foundPersonSix.getFirstName());
        assertEquals("Tollady", foundPersonSix.getLastName());
        assertEquals("03677 Helena Junction", foundPersonSix.getAddress());
        assertEquals("Female", foundPersonSix.getGender());
    }

    @Test
    @Order(7)
    public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {

        RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        given().spec(specificationWithoutToken)
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(
                                                TestConfigs.CONTENT_TYPE_YML,
                                                ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                    .when()
                .get()
                    .then()
                        .statusCode(403);
    }

    @Test
    @Order(8)
    public void testFindByName() throws JsonMappingException, JsonProcessingException {

        var wrapper = given().spec(specification)
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(
                                                TestConfigs.CONTENT_TYPE_YML,
                                                ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .pathParam("firstName", "ayr")
                .queryParams("page", 0, "size", 6, "direction", "asc")
                    .when()
                    .get("findPersonByName/{firstName}")
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                        .as(PagedModelPerson.class, objectMapper);

        var people = wrapper.getContent();

        PersonVO foundPersonOne = people.get(0);

        assertNotNull(foundPersonOne.getId());
        assertNotNull(foundPersonOne.getFirstName());
        assertNotNull(foundPersonOne.getLastName());
        assertNotNull(foundPersonOne.getAddress());
        assertNotNull(foundPersonOne.getGender());
        assertTrue(foundPersonOne.getEnabled());

        assertEquals(1, foundPersonOne.getId());

        assertEquals("Ayrton", foundPersonOne.getFirstName());
        assertEquals("Senna", foundPersonOne.getLastName());
        assertEquals("São Paulo", foundPersonOne.getAddress());
        assertEquals("Male", foundPersonOne.getGender());
    }

    @Test
    @Order(9)
    public void testHATEOS() throws JsonMappingException, JsonProcessingException {

        var unthreatedContent = given().spec(specification)
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(
                                                TestConfigs.CONTENT_TYPE_YML,
                                                ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .queryParams("page", 3, "size", 10, "direction", "asc")
                    .when()
                    .get()
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                        .asString();

        String content = unthreatedContent.replace("\n", "").replace("\r", "").replaceAll("\\s+", " ");

        assertTrue(content.contains("rel: \"self\" href: \"http://localhost:8888/api/person/v1/419\""));
        assertTrue(content.contains("rel: \"self\" href: \"http://localhost:8888/api/person/v1/499\""));
        assertTrue(content.contains("rel: \"self\" href: \"http://localhost:8888/api/person/v1/389\""));

        assertTrue(content.contains("rel: \"first\" href: \"http://localhost:8888/api/person/v1?direction=asc&page=0&size=10&sort=firstName,asc\""));
        assertTrue(content.contains("rel: \"prev\" href: \"http://localhost:8888/api/person/v1?direction=asc&page=2&size=10&sort=firstName,asc\""));
        assertTrue(content.contains("rel: \"self\" href: \"http://localhost:8888/api/person/v1?page=3&size=10&direction=asc\""));
        assertTrue(content.contains("rel: \"next\" href: \"http://localhost:8888/api/person/v1?direction=asc&page=4&size=10&sort=firstName,asc\""));
        assertTrue(content.contains("rel: \"last\" href: \"http://localhost:8888/api/person/v1?direction=asc&page=100&size=10&sort=firstName,asc\""));

        assertTrue(content.contains("page: size: 10 totalElements: 1008 totalPages: 101 number: 3"));

    }

    private void mockPerson() {
        person.setFirstName("Nelson");
        person.setLastName("Piquet");
        person.setAddress("Brasília - DF - Brasil");
        person.setGender("Male");
        person.setEnabled(true);
    }
}