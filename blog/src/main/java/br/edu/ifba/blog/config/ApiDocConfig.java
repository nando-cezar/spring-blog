package br.edu.ifba.blog.config;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@RestController
@OpenAPIDefinition(
    info = @Info(
        title = "Blog API Demo",
        version = "0.0",
        description = "Documentation for our Blog API Application",
        license = @License(name = "MIT License", url = "http://foo.bar"),
        contact = @Contact(
            url = "https://www.linkedin.com/in/lu%C3%ADs-fernando-cezar-dos-santos-6a329b19b/", 
            email = "luisfernando_cezar@hotmail.com"
        )
    )
)
public class ApiDocConfig {
    
}
