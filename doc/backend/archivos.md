# Backend - Archivos y almacenamiento documental

> Documento validado contra el código fuente actualizado del sistema. La documentación describe únicamente comportamiento implementado en backend.


## 1. Propósito

El backend incluye un módulo de almacenamiento de archivos para cargar, listar y descargar documentos. Este módulo también es usado por conciliación para almacenar solicitud y acta.

---

## 2. Componentes principales

| Componente | Responsabilidad |
|---|---|
| `FileUploadController` | Expone endpoints bajo `/api/files` |
| `FileStorageService` | Gestiona guardado, descarga, listado y validación de rutas |
| `FileNotFoundException` | Excepción específica de archivo no encontrado |
| `ConciliacionDocumentoService` | Guarda solicitud y acta de conciliación |

---

## 3. Endpoints de archivos

El controller expone:

- carga individual;
- carga múltiple;
- descarga por ruta;
- listado de archivos;
- listado de directorios.

Todos los endpoints requieren usuario autenticado.

---

## 4. Carga individual

Endpoint:

```http
POST /api/files/upload
```

Parámetros:

| Parámetro | Tipo | Descripción |
|---|---|---|
| `file` | `MultipartFile` | Archivo a cargar |
| `path` | texto opcional | Subdirectorio destino |

La respuesta incluye información del archivo almacenado.

---

## 5. Carga múltiple

Endpoint:

```http
POST /api/files/upload-multiple
```

Parámetros:

| Parámetro | Tipo | Descripción |
|---|---|---|
| `files` | `MultipartFile[]` | Archivos a cargar |
| `path` | texto opcional | Subdirectorio destino |

La respuesta es una lista con resultado por archivo.

---

## 6. Descarga

Endpoint:

```http
GET /api/files/download/**
```

El path posterior a `/download/` representa la ruta relativa del archivo solicitado.

---

## 7. Listado de archivos y directorios

Endpoints:

```http
GET /api/files/list
GET /api/files/list/{subDir}
GET /api/files/directories
```

Estos endpoints permiten consultar nombres de archivos y directorios disponibles bajo la raíz configurada del almacenamiento.

---

## 8. Seguridad documental

`FileStorageService` valida rutas relativas para evitar accesos fuera del directorio permitido. La configuración del almacenamiento se define por propiedades del backend.

---

## 9. Uso en conciliación

El módulo de conciliación usa almacenamiento documental para:

- guardar solicitud de conciliación;
- guardar acta de finalización;
- conservar rutas en la entidad `Conciliacion`.

La finalización de conciliación exige acta y registra su ruta antes de devolver la respuesta.
