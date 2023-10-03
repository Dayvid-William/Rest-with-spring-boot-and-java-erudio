package br.com.dayvid.apirestdocker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Diz ao spring boot que ele precisa ler essa classe ao iniciar como uma classe de configurações sobre a aplicação
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // https://www.baeldung.com/spring-mvc-content-negotiation-json-xml
        // Via EXTENSION. http://localhost:8080/api/person/v1.xml DEPRECATED on SpringBoot 2.6

        // Via QUERY PARAM. http://localhost:8080/api/person/v1?mediaType=xml
		/*
		configurer.favorParameter(true)
			.parameterName("mediaType").ignoreAcceptHeader(true)
			.useRegisteredExtensionsOnly(false)
			.defaultContentType(MediaType.APPLICATION_JSON)
				.mediaType("json", MediaType.APPLICATION_JSON)
				.mediaType("xml", MediaType.APPLICATION_XML);
		*/

        // Via HEADER PARAM. http://localhost:8080/api/person/v1

        configurer.favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                    .mediaType("Json", MediaType.APPLICATION_JSON)
                    .mediaType("xml", MediaType.APPLICATION_XML);
    }
}
