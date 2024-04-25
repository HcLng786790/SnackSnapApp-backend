package com.huuduc.snacksnap.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * Class config OpenApi(Swagger)
 *
 *  http://locahost:8080/swagger-ui/index.html
 * @author huuduc
 */
@OpenAPIDefinition(
        info = @Info(
                title = "SnackSnap App",
                version = "1.0.0",
                description = "Api SnackSnap App"
        ),
        servers = {
                @Server(url = "http://localhost:8080",description = "Local Development Server")
        }
)
@SecurityScheme(
        name = "bearer-key",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class OpenApiConfig {
}
