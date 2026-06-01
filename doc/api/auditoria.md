# API - Auditoría

> Documento validado contra el código fuente actualizado del sistema. La documentación describe únicamente comportamiento implementado en backend.


## 1. Ruta base

```http
/api/audit
```

La API de auditoría permite consultar registros auditables generados por acciones del sistema.

---

## 2. Endpoint disponible

| Método | Endpoint | Descripción | Permiso |
|---|---|---|---|
| GET | `/api/audit` | Consulta paginada de auditoría | `ACCEDER_ADMINISTRACION` |

---

## 3. Parámetros

| Parámetro | Tipo | Requerido | Descripción |
|---|---|---|---|
| `page` | entero | no | Página solicitada, por defecto `0` |
| `size` | entero | no | Tamaño de página, por defecto `20` |
| `username` | texto | no | Filtro por usuario |
| `sortBy` | texto | no | Campo de ordenamiento, por defecto `timestamp` |
| `sortDir` | texto | no | Dirección de ordenamiento, por defecto `desc` |

Ejemplo:

```http
GET /api/audit?page=0&size=20&username=admin&sortBy=timestamp&sortDir=desc
```

---

## 4. Respuesta

La respuesta es un `Page<AuditLogDTO>`.

Ejemplo de elemento:

```json
{
  "id": 1,
  "username": "admin",
  "action": "CREAR_CONSULTA",
  "entityName": "Consulta",
  "entityId": "10",
  "timestamp": "2026-05-31T10:00:00",
  "details": "..."
}
```

---

## 5. Acciones auditables

Las acciones se generan desde métodos anotados con `@Auditable` en servicios de escritura. El controller de auditoría no crea registros manualmente; solo expone la consulta.
