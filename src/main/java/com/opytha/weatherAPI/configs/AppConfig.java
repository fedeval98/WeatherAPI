package com.opytha.weatherAPI.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addResponses("NotModifiedResponse",
                                new ApiResponse()
                                        .description("El contenido no ha sido modificado, se reutiliza el eTag.")
                                        .content(new Content()
                                                .addMediaType("application/json",
                                                        new MediaType()
                                                                .schema(new Schema().type("string"))
                                                                .example("ETag: \"your-etag-value\"")
                                                )
                                        )
                        )
                        .addResponses("TooManyRequestsResponse",
                                new ApiResponse()
                                        .description("Se ha superado el límite de 100 peticiones por hora.")
                                        .content(new Content()
                                                .addMediaType("application/json",
                                                        new MediaType()
                                                                .schema(new Schema().type("string"))
                                                                .example("{\"error\": \"Se ha superado el límite de 100 peticiones por hora.\"}")
                                                )
                                        )
                        )
                )
                .info(new Info()
                        .title("WeatherAPI")
                        .version("1.0")
                        .description("Esta API proporciona información sobre el clima, consumiendo datos de la API de OpenWeather. " +
                                "El objetivo es ofrecer datos climáticos de manera eficiente, implementando un sistema de cache para reducir la cantidad de llamadas a la API de OpenWeather, utilizando ETags para manejar la validación de los datos almacenados en cache y un limitador de peticiones de 100 por hora. " +
                                "Tambien se utiliza Swagger para realizar una documentacion de los endpoints y su respuesta."));
    }
}
