# Entorno de ejecución

Este proyecto se ejecuta actualmente en un entorno local. No se ha definido aún una plataforma de despliegue formal ni un pipeline de despliegue automatizado.

## Ejecución local

La forma principal de ejecutar el sistema es en local, ya sea con Docker Compose o levantando cada módulo individualmente.

### Con Docker Compose

Se requiere tener Docker instalado y el complemento de Docker Compose habilitado.

Verificar instalación:

```bash
docker --version
docker compose --version
```

Desde la raíz del repositorio, ejecutar:

```bash
docker compose up --build
```

> En Linux puede ser necesario ejecutar con `sudo` si el usuario no pertenece al grupo `docker`.

### Ejecución individual por módulo

Si no se usa Docker, los módulos pueden iniciarse de forma separada.

#### Backend

El backend utiliza Maven.

Verificar instalación de Maven:

```bash
mvn --version
```

Entrar al directorio del backend correspondiente donde está el `pom.xml` y compilar:

```bash
mvn compile
```

Luego iniciar con:

```bash
./mvnw spring-boot:run
```

#### Frontend

El frontend está construido con Next.js.

Dentro del directorio `frontend`, instalar dependencias:

```bash
npm i
```

Y ejecutar el entorno de desarrollo:

```bash
npm run dev
```

## Estado actual de despliegue

- El proyecto está enfocado a ejecución local.
- No hay una plataforma de despliegue definida en este momento.
- No se ha documentado un ambiente de staging, producción ni una estrategia de despliegue.