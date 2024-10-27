package br.com.dayvid.apirestdocker.integrationtests.controller.withyaml;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Date;
import br.com.dayvid.apirestdocker.configs.TestConfigs;
import br.com.dayvid.apirestdocker.integrationtests.controller.withyaml.mapper.YMLMapper;
import br.com.dayvid.apirestdocker.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.dayvid.apirestdocker.integrationtests.vo.AccountCredentialsVO;
import br.com.dayvid.apirestdocker.integrationtests.vo.BookVO;
import br.com.dayvid.apirestdocker.integrationtests.vo.PagedModels.PagedModelBook;
import br.com.dayvid.apirestdocker.integrationtests.vo.TokenVO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class BookControllerYamlTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;

    private static YMLMapper objectMapper;

    private static BookVO book;

    @BeforeAll
    public static void setup() {
        objectMapper = new YMLMapper();
        book = new BookVO();
    }

    @Test
    @Order(1)
    public void authorization() {
        AccountCredentialsVO user = new AccountCredentialsVO();
        user.setUsername("leandro");
        user.setPassword("admin123");

        var token =
                given()
                        .config(
                                RestAssuredConfig
                                        .config()
                                        .encoderConfig(EncoderConfig.encoderConfig()
                                                .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
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

        specification =
                new RequestSpecBuilder()
                        .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + token)
                        .setBasePath("/api/book/v1")
                        .setPort(TestConfigs.SERVER_PORT)
                        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                        .build();
    }

    @Test
    @Order(2)
    public void testCreate() throws JsonMappingException, JsonProcessingException {

        mockBook();

        book = given()
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .body(book, objectMapper)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(BookVO.class, objectMapper);

        assertNotNull(book.getId());
        assertNotNull(book.getTitle());
        assertNotNull(book.getAuthor());
        assertNotNull(book.getPrice());
        assertTrue(book.getId() > 0);
        assertEquals("Docker Deep Dive", book.getTitle());
        assertEquals("Nigel Poulton", book.getAuthor());
        assertEquals(55.99, book.getPrice());
    }

    @Test
    @Order(3)
    public void testUpdate() throws JsonMappingException, JsonProcessingException {

        book.setTitle("Docker Deep Dive - Updated");

        BookVO bookUpdated = given()
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .body(book, objectMapper)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(BookVO.class, objectMapper);

        assertNotNull(bookUpdated.getId());
        assertNotNull(bookUpdated.getTitle());
        assertNotNull(bookUpdated.getAuthor());
        assertNotNull(bookUpdated.getPrice());
        assertEquals(bookUpdated.getId(), book.getId());
        assertEquals("Docker Deep Dive - Updated", bookUpdated.getTitle());
        assertEquals("Nigel Poulton", bookUpdated.getAuthor());
        assertEquals(55.99, bookUpdated.getPrice());
    }

    @Test
    @Order(4)
    public void testFindById() throws JsonMappingException, JsonProcessingException {
        var foundBook = given()
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .pathParam("id", book.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(BookVO.class, objectMapper);

        assertNotNull(foundBook.getId());
        assertNotNull(foundBook.getTitle());
        assertNotNull(foundBook.getAuthor());
        assertNotNull(foundBook.getPrice());
        assertEquals(foundBook.getId(), book.getId());
        assertEquals("Docker Deep Dive - Updated", foundBook.getTitle());
        assertEquals("Nigel Poulton", foundBook.getAuthor());
        assertEquals(55.99, foundBook.getPrice());
    }

    @Test
    @Order(5)
    public void testDelete() {
        given()
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .pathParam("id", book.getId())
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
                .queryParams("page", 0, "size", 12, "direction", "asc")
                    .when()
                .get()
                    .then()
                        .statusCode(200)
                        .extract()
                        .body()
                            .as(PagedModelBook.class, objectMapper);

        var content = wrapper.getContent();

        BookVO foundBookOne = content.get(0);

        assertNotNull(foundBookOne.getId());
        assertNotNull(foundBookOne.getTitle());
        assertNotNull(foundBookOne.getAuthor());
        assertNotNull(foundBookOne.getPrice());
        assertTrue(foundBookOne.getId() > 0);
        assertEquals("Big Data: como extrair volume, variedade, velocidade e valor da avalanche de informação cotidiana", foundBookOne.getTitle());
        assertEquals("Viktor Mayer-Schonberger e Kenneth Kukier", foundBookOne.getAuthor());
        assertEquals(54.0, foundBookOne.getPrice());

        BookVO foundBookFive = content.get(4);

        assertNotNull(foundBookFive.getId());
        assertNotNull(foundBookFive.getTitle());
        assertNotNull(foundBookFive.getAuthor());
        assertNotNull(foundBookFive.getPrice());
        assertTrue(foundBookFive.getId() > 0);
        assertEquals("Domain Driven Design", foundBookFive.getTitle());
        assertEquals("Eric Evans", foundBookFive.getAuthor());
        assertEquals(92.0, foundBookFive.getPrice());
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
                .queryParams("page", 0, "size", 12, "direction", "asc")
                    .when()
                    .get()
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .asString();

        String content = unthreatedContent.replace("\n", "").replace("\r", "").replaceAll("\\s+", " ");

        System.out.println(content);

        assertTrue(content.contains("rel: \"self\" href: \"http://localhost:8888/api/book/v1/3\""));
        assertTrue(content.contains("rel: \"self\" href: \"http://localhost:8888/api/book/v1/12\""));
        assertTrue(content.contains("rel: \"self\" href: \"http://localhost:8888/api/book/v1/5\""));

        assertTrue(content.contains("rel: \"first\" href: \"http://localhost:8888/api/book/v1?direction=asc&page=0&size=12&sort=title,asc\""));
        assertTrue(content.contains("rel: \"self\" href: \"http://localhost:8888/api/book/v1?page=0&size=12&direction=asc\""));
        assertTrue(content.contains("rel: \"next\" href: \"http://localhost:8888/api/book/v1?direction=asc&page=1&size=12&sort=title,asc\""));
        assertTrue(content.contains("rel: \"last\" href: \"http://localhost:8888/api/book/v1?direction=asc&page=1&size=12&sort=title,asc\""));

        assertTrue(content.contains("page: size: 12 totalElements: 15 totalPages: 2 number: 0"));

    }

    private void mockBook() {
        book.setTitle("Docker Deep Dive");
        book.setAuthor("Nigel Poulton");
        book.setPrice(Double.valueOf(55.99));
        book.setLaunch_date(new Date());
    }
}
