# Configuración y seguridad de entorno

## Propósito

Este documento describe la configuración observada en el código fuente para ejecutar el backend y el frontend del sistema. No incluye valores reales de credenciales, tokens, llaves ni secretos.

## Backend

El backend se configura desde:

```text
backend/app/src/main/resources/application.properties
```

La aplicación usa variables de entorno para los datos sensibles y para los parámetros dependientes del ambiente.

### Variables principales del backend

| Variable | Uso en el código |
|---|---|
| `PORT` | Puerto HTTP del backend. Valor por defecto: `8080`. |
| `DB_URL` | URL de conexión JDBC a PostgreSQL. |
| `DB_USERNAME` | Usuario de base de datos. |
| `DB_PASSWORD` | Contraseña de base de datos. |
| `DB_DDL_AUTO` | Estrategia de Hibernate para esquema. Valor por defecto en código: `update`. |
| `DB_SHOW_SQL` | Activa o desactiva salida de SQL. Valor por defecto en código: `true`. |
| `JWT_SECRET` | Secreto usado para firmar/verificar JWT. |
| `AUTH_COOKIE_SECURE` | Define si la cookie de autenticación se marca como segura. Valor por defecto: `true`. |
| `AUTH_COOKIE_SAME_SITE` | Política SameSite de la cookie. Valor por defecto: `None`. |
| `BREVO_API_KEY` | Llave del proveedor de correo. |
| `MAIL_FROM_EMAIL` | Correo remitente del sistema. |
| `FRONTEND_URL` | URL usada para construir enlaces de restablecimiento de contraseña. |
| `UPLOAD_DIR` | Directorio base para carga de archivos. Valor por defecto: `uploads`. |

### Base de datos

El backend usa PostgreSQL mediante Spring Data JPA. La configuración incluye:

- driver PostgreSQL;
- dialecto `PostgreSQLDialect`;
- esquema por defecto `DB_consultorioJuridico`;
- creación de namespaces habilitada para Hibernate.

### JWT

El secreto JWT se lee desde `JWT_SECRET`. El tiempo de expiración configurado en código es:

```text
app.jwt.expiration-ms=3600000
```

Esto equivale a una hora.

### Cookie de autenticación

El backend crea una cookie llamada:

```text
access_token
```

Características observadas:

- `HttpOnly`;
- `Secure` configurable con `AUTH_COOKIE_SECURE`;
- `SameSite` configurable con `AUTH_COOKIE_SAME_SITE`;
- ruta `/`;
- duración máxima de una hora en login;
- expiración inmediata en logout.

### CORS

CORS se configura en:

```text
config/cors/CorsConfig.java
config/cors/CorsProperties.java
```

La configuración permite cambiar orígenes, métodos, headers, credenciales y tiempo de cache mediante propiedades `app.cors.*`. El código incluye valores por defecto para desarrollo local y despliegues Vercel.

### Correo

El sistema usa configuración de correo para:

- recuperación de contraseña;
- notificaciones de seguimiento;
- notificaciones de reuniones de conciliación.

Los valores sensibles se leen desde variables y no deben registrarse en documentación con valores reales.

### Archivos

El servicio de archivos usa la propiedad:

```text
file.upload-dir
```

Si no se define `UPLOAD_DIR`, el valor por defecto es `uploads`.

## Frontend

La configuración de URLs se centraliza en:

```text
frontend/src/lib/config.js
```

Variables públicas del frontend:

| Variable | Uso |
|---|---|
| `NEXT_PUBLIC_API_URL_BASE` | URL base principal de la API. |
| `NEXT_PUBLIC_API_URL` | Alternativa para URL base de la API. |
| `NEXT_PUBLIC_FILE_STORAGE_API_URL_BASE` | URL base para operaciones de archivos. |

El frontend normaliza la URL para asegurar que tenga esquema HTTP/HTTPS, que no termine en `/` y que termine en `/api`.

## Cliente HTTP frontend

El archivo `src/lib/apiClient.js` centraliza peticiones HTTP y usa:

```text
credentials: "include"
```

Esto permite enviar la cookie de sesión al backend en peticiones autenticadas.

## Manejo de errores frontend

El archivo `src/lib/api.js` contiene utilidades para:

- leer cuerpo de respuesta;
- extraer título de error;
- extraer detalles de validación;
- construir descripciones legibles para interfaz.

## Consideraciones de seguridad documental

La documentación técnica debe describir nombres de variables, finalidad y flujo. No debe incluir:

- contraseñas reales;
- tokens reales;
- `JWT_SECRET` real;
- API keys reales;
- cadenas JDBC con credenciales reales;
- correos personales usados como prueba;
- cookies o tokens capturados.
