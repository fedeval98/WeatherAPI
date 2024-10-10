# WeatherAPI

## Descripción
Esta API proporciona información sobre el clima, consumiendo datos de la API de OpenWeather. El objetivo es ofrecer datos climáticos de manera eficiente, implementando un sistema de **cache** para reducir la cantidad de llamadas a la API de OpenWeather, utilizando **ETags** para manejar la validación de los datos almacenados en cache y un limitador de peticiones de 100 por hora. Tambien se utiliza Swagger para realizar una documentacion de los endpoints y su respuesta.

## Tecnologías

- **Lenguaje:** Java 23
- **Framework:** Spring Boot 3.3.4
- **Project Manager:** Maven

## Dependencias

- **Spring Web:** Para manejar las peticiones HTTP y crear la API REST.
- **Spring Security:** Para la autenticación y autorización.
- **Spring Data JPA:** Para la integración con bases de datos utilizando JPA.
- **PostgreSQL:** Base de datos para almacenar los datos de cache y otros recursos.
- **JWT (JSON Web Token):** Para la autenticación y manejo de sesiones seguras.
- **Lombok:** Para reducir el código boilerplate (getters, setters, constructores, etc).
- **Swagger:** Para documentar los endpoints y sus respuestas.

## API externa

La API consume datos de OpenWeather:

- **URL Base:** [OpenWeather API](https://openweathermap.org/api)
- Se implementará un sistema de cache para optimizar las llamadas, almacenando la información y validándola con **ETags**.

## Instalación

1. Clona este repositorio:
    ```bash
    git clone https://github.com/fedeval98/WeatherAPI.git
    ```

2. Dirígete al directorio del proyecto:
    ```bash
    cd weather-api
    ```

3. Compila el proyecto con Maven:
    ```bash
    mvn clean install
    ```

4. Configura las variables de entorno o modifica el archivo `application.properties` para conectarte a tu base de datos PostgreSQL.

## Uso

- La API permite consultar el clima de diferentes ubicaciones. Los datos se almacenan en caché, y se usan ETags para validar la frescura de los mismos antes de hacer nuevas llamadas a la API de OpenWeather.

### Endpoints

- **/api/weather/cityname** -> Devuelve el clima actual de la ciudad buscada. ejemplo: /api/weather/London
- **/api/forecast/cityname** -> Devuelve el clima de los proximos 5 dias de la ciudad buscada. ejemplo: /api/forecast/London
- **/api/pollution/cityname** -> Devuelve la polucion del aire de la ciudad buscada. ejemplo: /api/pollution/London
- **/swagger-ui/index.html** -> Accede a la documentacion de Swagger.

## Configuración

### Base de datos

Asegúrate de tener una instancia de PostgreSQL en ejecución. Configura la conexión en `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/weather_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

## API Key de OpenWeather

Registra tu API Key en OpenWeather y agrégala en tu `application.properties`:
- Crea una variable de entorno en el sistema o utiliza un archivo .env para guardar la API_KEY.
```
openweather.api.key=${API_KEY}
```
- Recorda que tu API_KEY es una variable secreta, no la incluyas en tu repositorio.

## Seguridad
Este proyecto usa JWT para la autenticación. Para generar y validar tokens, configura los parámetros de seguridad en tu archivo de propiedades.

## Seguridad
Este proyecto utiliza JWT (JSON Web Token) para la autenticación y autorización de los usuarios. A continuación, se describe cómo se generan y manejan los tokens en la aplicación:

### Flujo de Autenticación

1. **Login**: Cuando un usuario se autentica en la API mediante el endpoint `/api/login`, se envían las credenciales (email y contraseña). Si las credenciales son válidas, se genera un token JWT.

   - **Generación del Token**:
      - Se utiliza la clase `JwtService` para crear el token. Esta clase tiene un método `generateToken(UserDetails userDetails)` que toma como parámetro el objeto `UserDetails` del usuario autenticado.
      - El token contiene la información del usuario y su rol, y tiene una duración de una hora.

2. **Uso del Token**:
   - El token se debe incluir en el encabezado de autorización de las solicitudes a los endpoints protegidos de la API, utilizando el esquema "Bearer". Por ejemplo:
     ```
     Authorization: Bearer <token>
     ```

3. **Validación del Token**:
   - La clase `JwtRequestFilter` intercepta las solicitudes entrantes y verifica la validez del token JWT. Si el token es válido y no ha expirado, se extrae el nombre de usuario del token y se establece la autenticación en el contexto de seguridad de Spring.

4. **Manejo de Errores**:
   - Si el token es inválido o ha expirado, el acceso a los endpoints protegidos será denegado. Se define un `CustomAuthenticationEntryPoint` para manejar los errores de autenticación y enviar respuestas adecuadas al cliente.

5. **Roles y Permisos**:
   - Los roles de los usuarios se gestionan en la clase `UserDetailService`, que asigna roles a los usuarios en función de los datos de la base de datos. Al generar el token, también se incluye el rol en los reclamos del token.

### Ejemplo de Generación de Token
Al realizar una autenticación exitosa, se devuelve una respuesta JSON que incluye el token:

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
### Configuración de Seguridad
Asegúrate de que la configuración de seguridad en SecurityConfig permita el acceso al endpoint /api/login sin autenticación, mientras que otros endpoints requieren un token válido para acceder.

## [Plan futuro](https://shadow-parka-4f4.notion.site/11b2ea608eb280dcb383e455f6923516?v=11b2ea608eb2815b9721000c816d7509)
- Mejorar el sistema de cache.
- Agregar endpoints para nuevas funcionalidades como alertas meteorológicas.
- Mejorar la seguridad con OAuth 2.0.
- Crear un front utilizando VUE.

## Contribuciones
Las contribuciones son bienvenidas. Si deseas contribuir, por favor abre un Pull Request o crea un Issue.

## Licencia
Este proyecto está licenciado bajo la MIT License.
