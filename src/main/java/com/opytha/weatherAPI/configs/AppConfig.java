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
                                        .description("Content not modified. Re-utilizing E-Tag.")
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
                                        .description("Limit of 100 requests per hour has been surpassed.")
                                        .content(new Content()
                                                .addMediaType("application/json",
                                                        new MediaType()
                                                                .schema(new Schema().type("string"))
                                                                .example("{\"error\": \"Limit of 100 requests per hour has been surpassed.\"}")
                                                )
                                        )
                        )
                )
                .info(new Info()
                        .title("WeatherAPI")
                        .version("1.0")
                        .description("This API provides weather information by consuming data from the OpenWeather API. " +
                                "The goal is to offer weather data efficiently by implementing a caching system to reduce the number of calls to the OpenWeather API, using ETags to handle the validation of cached data, and a rate limiter of 100 requests per hour. " +
                                "Swagger is also used to document the endpoints and their responses.")
                );
    }
}
