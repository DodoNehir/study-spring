package com.example.projectvoucher.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import java.time.LocalDateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI openApiInfo() {
    return new OpenAPI()
        .info(new Info()
            .title("Project Voucher API")
            .description("Project Voucher API")
            .version(LocalDateTime.now().toString()));
  }

}
