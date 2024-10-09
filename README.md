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
- **api/pollution/cityname** -> Devuelve la polucion del aire de la ciudad buscada. ejemplo: /api/pollution/London

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

## Plan futuro
- Mejorar el sistema de cache.
- Agregar endpoints para nuevas funcionalidades como alertas meteorológicas.
- Mejorar la seguridad con OAuth 2.0.
- Crear un front utilizando VUE.

## Contribuciones
Las contribuciones son bienvenidas. Si deseas contribuir, por favor abre un Pull Request o crea un Issue.

## Licencia
Este proyecto está licenciado bajo la MIT License.
