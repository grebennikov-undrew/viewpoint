package com.grebennikovas.viewpoint;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "ViewPoint Api",
                description = "Simple BI System", version = "0.0.1",
                contact = @Contact(
                        name = "Grebennikov Andrey",
                        email = "grebennikovas@yahoo.com",
                        url = "https://github.com/grebennikov-undrew/viewpoint"
                )
        )
)
public class OpenApiConfig {
}
