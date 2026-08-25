# api-stock

Microservicio de catálogo e inventario construido con Java 17 y Spring Boot, siguiendo
arquitectura hexagonal. Gestiona marcas, categorías, artículos y el abastecimiento de
existencias.

Forma parte del reto **Emazon**, una tienda virtual dividida en microservicios
independientes, desarrollado durante el **Bootcamp Power Up de Pragma** (2024).

## Arquitectura

Puertos y adaptadores, con el dominio aislado de la infraestructura:

```
domain/     modelos, reglas de negocio, casos de uso y puertos
            (brand, category, product, page, role)
app/        handlers de aplicación, DTOs y mappers de MapStruct
infra/      adaptadores de entrada (controladores REST) y de salida
            (persistencia JPA), seguridad, manejo de excepciones y OpenAPI
```

El dominio no conoce Spring ni JPA. Los casos de uso dependen de interfaces que la capa
de infraestructura implementa.

## Endpoints

| Método | Ruta | Rol requerido | Descripción |
|---|---|---|---|
| `POST` | `/brand/create` | `ADMIN` | Crear marca |
| `GET` | `/brand/fetch` | autenticado | Listar marcas, paginado y ordenable |
| `POST` | `/category/create` | `ADMIN` | Crear categoría |
| `GET` | `/category/fetch` | autenticado | Listar categorías, paginado y ordenable |
| `POST` | `/product/create` | `ADMIN` | Crear artículo con sus categorías |
| `GET` | `/product/fetch` | autenticado | Listar artículos, paginado y ordenable |
| `POST` | `/product/increase` | `WAREHOUSE_ASSISTANT` | Aumentar existencias |

La autorización se resuelve con `@PreAuthorize` sobre cada método, validando el rol
contenido en el JWT que emite [`api-user`](https://github.com/Herreran903/api-user).

Documentación interactiva en `/swagger-ui.html` una vez levantado el servicio.

## Reglas de negocio implementadas

- Nombres de marca y categoría únicos, con límites de longitud en nombre y descripción
- Cada artículo debe tener entre 1 y 3 categorías, sin repetir
- Listados paginados con orden ascendente o descendente por nombre, marca o categoría
- Validación a nivel de campo con Bean Validation
- Manejo centralizado de excepciones con respuestas de error consistentes

## Stack

- **Java 17**, **Spring Boot 3.3**
- Spring Web, Spring Data JPA, Spring Security, Spring Validation
- **MySQL** como motor de persistencia
- **MapStruct 1.5.5** para el mapeo entre entidades y DTOs
- **JJWT 0.12.6** para la validación de tokens
- **springdoc-openapi 2.6.0** para la documentación
- **JUnit 5** y **Mockito** para pruebas

## Pruebas

18 clases de prueba que cubren modelos de dominio, casos de uso, handlers de aplicación
y controladores.

```bash
./gradlew test
```

## Ejecución local

Requiere Java 17 y una instancia de MySQL.

```bash
git clone https://github.com/Herreran903/api-stock.git
cd api-stock
./gradlew bootRun
```

Configura la conexión a la base de datos y la clave de firma del JWT en
`src/main/resources/application.properties` antes de arrancar. La clave debe coincidir
con la que usa `api-user` para emitir los tokens.

## Servicios relacionados

- [`api-user`](https://github.com/Herreran903/api-user) — autenticación y gestión de usuarios
