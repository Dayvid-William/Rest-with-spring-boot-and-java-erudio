package br.com.dayvid.apirestdocker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean //Faz com que a instancia retornada pelo metodo se torne um objeto gerenciado pelo spring
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RESTful API with java 20 and spring boot 3.1.2")
                        .version("v1")
                        .description("Some description about you API")
                        .termsOfService("https://\637b8cd0bd894450460dd0a0--adorable-sable-425b3d.netlify.app")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("")));
    }
}
