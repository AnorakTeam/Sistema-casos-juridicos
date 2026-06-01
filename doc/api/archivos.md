# API - Archivos

> Documento validado contra el código fuente actualizado del sistema. La documentación describe únicamente comportamiento implementado en backend.


## 1. Ruta base

```http
/api/files
```

Los endpoints requieren autenticación.

---

## 2. Cargar archivo

```http
POST /api/files/upload
```

Tipo de contenido:

```http
multipart/form-data
```

Parámetros:

| Nombre | Tipo | Requerido | Descripción |
|---|---|---|---|
| `file` | archivo | sí | Archivo a cargar |
| `path` | texto | no | Subdirectorio destino |

Respuesta esperada:

```json
{
  "fileName": "documento.pdf",
  "filePath": "ruta/documento.pdf",
  "success": true
}
```

---

## 3. Cargar múltiples archivos

```http
POST /api/files/upload-multiple
```

Parámetros:

| Nombre | Tipo | Requerido | Descripción |
|---|---|---|---|
| `files` | arreglo de archivos | sí | Archivos a cargar |
| `path` | texto | no | Subdirectorio destino |

Respuesta: lista de resultados por archivo.

---

## 4. Descargar archivo

```http
GET /api/files/download/{ruta-relativa}
```

El backend retorna un `Resource` con encabezados de descarga.

---

## 5. Listar archivos

```http
GET /api/files/list
GET /api/files/list/{subDir}
```

Retorna una lista de nombres de archivos.

---

## 6. Listar directorios

```http
GET /api/files/directories
```

Retorna una lista de directorios disponibles.

---

## 7. Consideraciones

- Las rutas se manejan como rutas relativas al almacenamiento configurado.
- El controller responde con códigos HTTP de error cuando no encuentra recursos o cuando ocurre un error interno de almacenamiento.
- Conciliación usa este módulo como soporte documental para solicitud y acta.
