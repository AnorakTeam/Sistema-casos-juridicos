# Backend - Auditoría

> Documento validado contra el código fuente actualizado del sistema. La documentación describe únicamente comportamiento implementado en backend.


## 1. Propósito

El módulo de auditoría registra acciones relevantes ejecutadas sobre entidades del sistema. Permite conservar trazabilidad técnica de operaciones administrativas y funcionales.

---

## 2. Componentes principales

| Componente | Responsabilidad |
|---|---|
| `@Auditable` | Anotación usada para marcar métodos auditables |
| `AuditAspect` | Intercepta métodos anotados y construye registros |
| `AuditLog` | Entidad JPA del registro de auditoría |
| `AuditLogDTO` | DTO de respuesta |
| `AuditLogRepository` | Repositorio de auditoría |
| `AuditLogService` | Consulta paginada y filtrada |
| `AuditLogController` | Endpoint REST bajo `/api/audit` |

---

## 3. Entidad AuditLog

`AuditLog` conserva datos como:

- usuario;
- acción;
- nombre de entidad;
- identificador de entidad;
- fecha y hora;
- detalles.

El DTO `AuditLogDTO` expone:

- `id`;
- `username`;
- `action`;
- `entityName`;
- `entityId`;
- `timestamp`;
- `details`.

---

## 4. Uso de @Auditable

Los métodos de escritura de módulos principales usan la anotación `@Auditable`, indicando acción y entidad. Ejemplos de acciones implementadas:

- creación de consulta;
- actualización de consulta;
- cambio de estado;
- creación de proceso;
- actualización de perfiles;
- cambio de perfil;
- finalización de conciliación;
- eliminación lógica.

---

## 5. Consulta de auditoría

El controller expone consulta paginada:

```http
GET /api/audit?page=0&size=20&username=usuario&sortBy=timestamp&sortDir=desc
```

Parámetros:

| Parámetro | Descripción | Valor por defecto |
|---|---|---|
| `page` | Página solicitada | `0` |
| `size` | Tamaño de página | `20` |
| `username` | Filtro opcional por usuario | no requerido |
| `sortBy` | Campo de ordenamiento | `timestamp` |
| `sortDir` | Dirección | `desc` |

---

## 6. Seguridad

La consulta de auditoría requiere permiso:

```text
ACCEDER_ADMINISTRACION
```

---

## 7. Protección de registros

El paquete incluye un script de base de datos asociado a auditoría inmutable:

```text
backend/db/triggers/audit_immutable.sql
```

Este recurso refuerza el carácter histórico de los registros de auditoría a nivel de base de datos cuando se aplica en el ambiente correspondiente.
