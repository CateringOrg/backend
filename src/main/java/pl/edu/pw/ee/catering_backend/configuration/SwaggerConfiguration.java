package pl.edu.pw.ee.catering_backend.configuration;


import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        scheme = "bearer",
        name = "Authorization",
        in = SecuritySchemeIn.HEADER,
        bearerFormat = "JWT",
        type = SecuritySchemeType.HTTP
)
@RequiredArgsConstructor
@Slf4j
public class SwaggerConfiguration {

  @Bean
  public OpenAPI apiOpenApi() {
    OpenAPI api = new OpenAPI();
    api.setInfo(new Info().title("Catering Backend API").version("1.0.0"));
    List<Server> serverList = new ArrayList<>();
    serverList.add(new Server().url("http://localhost:8080")
        .description("predefined address"));
    api.setServers(serverList);
    api.setSecurity(List.of(new SecurityRequirement().addList("Authorization")));
    return api;
  }

  @Bean
  public GroupedOpenApi cateringCompaniesApi() {
    return GroupedOpenApi.builder()
            .group("catering-companies")
            .displayName("Catering Companies")
            .pathsToMatch("/catering-companies/**")
            .build();
  }

  @Bean
  public GroupedOpenApi offersApi() {
    return GroupedOpenApi.builder()
            .group("offers")
            .displayName("Offers")
            .pathsToMatch("/offers/**")
            .build();
  }

  @Bean
  public GroupedOpenApi cartApi() {
    return GroupedOpenApi.builder()
            .group("cart")
            .displayName("Cart")
            .pathsToMatch("/cart/**")
            .build();
  }

  @Bean
  public GroupedOpenApi authApi() {
    return GroupedOpenApi.builder()
        .group("auth")
        .displayName("auth")
        .pathsToMatch("/auth/**")
        .build();
  }

  @Bean
  public GroupedOpenApi orderApi() {
    return GroupedOpenApi.builder()
            .group("order")
            .displayName("Order")
            .pathsToMatch("/orders/**")
            .build();
  }
}
