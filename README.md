# WeatherAPI

## Description
This API provides weather information by consuming data from the OpenWeather API. The goal is to offer weather data efficiently by implementing a caching system to reduce the number of calls to the OpenWeather API, using **ETags** to handle the validation of cached data, and a rate limiter of 100 requests per hour. Swagger is also used to document the endpoints and their responses.

## Technologies

- **Language:** Java 23
- **Framework:** Spring Boot 3.3.4
- **Project Manager:** Maven

## Dependencies

- **Spring Web:** To handle HTTP requests and create the REST API.
- **Spring Security:** For authentication and authorization.
- **Spring Data JPA:** For integration with databases using JPA.
- **PostgreSQL:** Database to store cached data and other resources.
- **JWT (JSON Web Token):** For secure authentication and session management.
- **Lombok:** To reduce boilerplate code (getters, setters, constructors, etc.).
- **Swagger:** To document the endpoints and their responses.

## External API

The API consumes data from OpenWeather:

- **Base URL:** [OpenWeather API](https://openweathermap.org/api)
- A caching system will be implemented to optimize calls by storing the information and validating it with **ETags**.

## Installation

1. Clone this repository:
    ```bash
    git clone https://github.com/fedeval98/WeatherAPI.git
    ```

2. Navigate to the project directory:
    ```bash
    cd weather-api
    ```

3. Build the project with Maven:
    ```bash
    mvn clean install
    ```

4. Configure environment variables or modify the `application.properties` file to connect to your PostgreSQL database.

## Usage

- The API allows querying the weather for different locations. Data is cached, and ETags are used to validate freshness before making new calls to the OpenWeather API.

### Endpoints

- **/api/weather/cityname** -> Returns the current weather for the specified city. Example: /api/weather/London
- **/api/forecast/cityname** -> Returns the weather forecast for the next 5 days for the specified city. Example: /api/forecast/London
- **/api/pollution/cityname** -> Returns the air pollution data for the specified city. Example: /api/pollution/London
- **/swagger-ui/index.html** -> Access the Swagger documentation.
- **/api/register** -> Endpoint for user creation by sending a JSON body to the server.

### IMPORTANT 

Use tools like Postman to make HTTP requests to the server, as there is currently no user interface available.  

To register, send a raw JSON body to `/api/register`:
```
{
  "firstName": "Name",
  "lastName": "Lastname",
  "email": "your@mail",
  "password": "password"
}
```

To log in, send a raw JSON body to `/api/login`:
```
{
  "email": "your@mail",
  "password": "password"
}
```

**Consider checking `/swagger-ui/index.html` to explore all available requests.**

## Configuration

### Database

Make sure you have a running instance of PostgreSQL. Configure the connection in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/weather_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## OpenWeather API Key

Register your API Key with OpenWeather and add it to your `application.properties`:
- Create an environment variable on your system or use a .env file to store the API_KEY.
```
openweather.api.key=${API_KEY}
```
- Remember that your API_KEY is a secret variable; do not include it in your repository.

## Security
This project uses JWT for authentication. To generate and validate tokens, configure the security parameters in your properties file.

## Security
This project uses JWT (JSON Web Token) for user authentication and authorization. The following describes how tokens are generated and handled in the application:

### Authentication Flow

1. **Login**: When a user authenticates with the API via the `/api/login` endpoint, their credentials (email and password) are sent. If the credentials are valid, a JWT token is generated.

   - **Token Generation**:
      - The `JwtService` class is used to create the token. This class has a method `generateToken(UserDetails userDetails)` that takes the authenticated `UserDetails` object as a parameter.
      - The token contains user information and their role, and it has a duration of one hour.

2. **Token Usage**:
   - The token must be included in the authorization header of requests to protected API endpoints using the "Bearer" scheme. For example:
     ```
     Authorization: Bearer <token>
     ```

3. **Token Validation**:
   - The `JwtRequestFilter` class intercepts incoming requests and verifies the validity of the JWT token. If the token is valid and has not expired, the username is extracted from the token, and the authentication is set in the Spring security context.

4. **Error Handling**:
   - If the token is invalid or has expired, access to the protected endpoints will be denied. A `CustomAuthenticationEntryPoint` is defined to handle authentication errors and send appropriate responses to the client.

5. **Roles and Permissions**:
   - User roles are managed in the `UserDetailService` class, which assigns roles to users based on data from the database. When generating the token, the role is also included in the token claims.

### Example of Token Generation
Upon successful authentication, a JSON response is returned that includes the token:

```java
@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtService.generateToken(userDetails);
        
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
```

### Security Configuration
Ensure that the security configuration in `SecurityConfig` allows access to the `/api/login` endpoint without authentication, while other endpoints require a valid token to access.

## [Future Plan](https://shadow-parka-4f4.notion.site/11b2ea608eb280dcb383e455f6923516?v=11b2ea608eb2815b9721000c816d7509)
- Improve the caching system.
- Add endpoints for new features such as weather alerts.
- Enhance security with OAuth 2.0.
- Create a front end using VUE.

## Contributions
Contributions are welcome. If you would like to contribute, please open a Pull Request or create an Issue.

## License
This project is licensed under the MIT License.
