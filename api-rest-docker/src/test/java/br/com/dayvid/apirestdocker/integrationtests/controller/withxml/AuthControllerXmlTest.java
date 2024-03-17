package br.com.dayvid.apirestdocker.integrationtests.controller.withxml;

import br.com.dayvid.apirestdocker.configs.TestConfigs;
import br.com.dayvid.apirestdocker.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.dayvid.apirestdocker.integrationtests.vo.AccountCredentialsVO;
import br.com.dayvid.apirestdocker.integrationtests.vo.TokenVO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonMappingException;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerXmlTest extends AbstractIntegrationTest {

    private static TokenVO tokenVO;

    @Test
    @Order(1)
    public void testSignin() throws JsonMappingException, JsonProcessingException {

        AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");
        /*StringWriter writer = new StringWriter();
        JAXB.marshal(user, writer);
        String xmlContent = writer.toString();*/

        tokenVO = given()
                .basePath("/auth/signin")
                    .port(TestConfigs.SERVER_PORT)
                    .contentType(TestConfigs.CONTENT_TYPE_XML)
                .body(user)
                    .when()
                .post()
                    .then()
                        .statusCode(200)
                            .extract()
                            .body()
                                .as(TokenVO.class); //Só pode ser salvo, pois, tem todos os atributos iguais o da aplicação se não deveria ser salvo como String.

        assertNotNull(tokenVO);
    }

    @Test
    @Order(2)
    public void testRefresh() throws JsonMappingException, JsonProcessingException{

        AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

        var newTokenVO = given()
                .basePath("/auth/refresh")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_XML)
                    .pathParam("username", tokenVO.getUsername())
                .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.getRefreshToken())
                    .when()
                .put("{username}")
                    .then()
                        .statusCode(200)
                        .extract()
                        .body()
                            .as(TokenVO.class); //Só pode ser salvo, pois, tem todos os atributos iguais o da aplicação se não deveria ser salvo como String.

        assertNotNull(newTokenVO.getAccessToken());
        assertNotNull(newTokenVO.getRefreshToken());
    }
}
