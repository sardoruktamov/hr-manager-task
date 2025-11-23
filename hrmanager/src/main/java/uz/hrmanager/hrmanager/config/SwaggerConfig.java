package uz.hrmanager.hrmanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("HR-Manager-System")
                        .description("HR tizimini boshqarish uchun API")
                        .contact(new Contact()
                                .name("O'ktamov Sardorbek")
                                .url("https://t.me/uktamov91")
                                .email("sardorbek.uktamov.1@mail.ru")
                        )
                        .version("1.0.0")
                );
    }
}
