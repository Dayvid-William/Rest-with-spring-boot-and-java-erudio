package br.com.dayvid.apirestdocker.serialization.converter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

public class YamlJackson2HttpMesageConverter  extends AbstractJackson2HttpMessageConverter {

    public YamlJackson2HttpMesageConverter() {
        super (new YAMLMapper() // instancia um novo conversor de yaml
                .setSerializationInclusion(JsonInclude.Include.NON_NULL), // ignora os atributos nulos
                MediaType.parseMediaType("application/x-yaml") // converte para yaml
                );
    }
}
