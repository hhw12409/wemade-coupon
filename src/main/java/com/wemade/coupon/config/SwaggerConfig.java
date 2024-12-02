package com.wemade.coupon.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
            .info(new Info()
                    .title("Coupon API")
                    .description("쿠폰 서비스")
                    .version("v1.0.0"));
  }
}
