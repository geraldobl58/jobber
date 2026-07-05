package org.geraldoluiz.jobber.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Jobber API")
                        .description("API para gerenciamento de vagas, candidatos e empresas")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Geraldo Luiz")
                                .url("https://github.com/geraldoluiz"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}

