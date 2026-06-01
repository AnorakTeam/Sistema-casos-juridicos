# Backend - Estadísticas y reportes

> Documento validado contra el código fuente actualizado del sistema. La documentación describe únicamente comportamiento implementado en backend.


## 1. Propósito del módulo

El módulo de estadísticas consolida información operativa del consultorio jurídico para generar reportes por semestre, rangos de fechas y perfiles específicos. Su implementación se basa en consultas agregadas sobre entidades reales del sistema.

El backend expone estadísticas mediante `EstadisticasController` y delega el procesamiento a servicios especializados.

---

## 2. Estructura del módulo

Clases principales:

| Clase | Responsabilidad |
|---|---|
| `EstadisticasController` | Expone endpoints REST bajo `/api/estadisticas` |
| `EstadisticasService` | Fachada del módulo |
| `EstadisticasQueryService` | Estadísticas por semestre |
| `EstadisticasRangoQueryService` | Estadísticas por rango de fechas |
| `EstadisticasPerfilQueryService` | Estadísticas por estudiante, asesor o monitor |
| `EstadisticasMapperService` | Conversión de agregaciones a DTOs |
| `EstadisticasPdfService` | Generación de reporte PDF |
| `EstadisticasSemestreDTO` | DTO principal de respuesta |
| `SemestreDTO` | DTO de semestres disponibles |
| `ConteoDTO` | DTO de agregación nombre-cantidad |

---

## 3. DTO principal

`EstadisticasSemestreDTO` contiene:

| Campo | Descripción |
|---|---|
| `año` | Año del periodo |
| `semestre` | Semestre consultado |
| `periodoInicio` | Fecha inicial del periodo |
| `periodoFin` | Fecha final del periodo |
| `consultasFinalizadas` | Total de consultas finalizadas |
| `consultasPendientes` | Total de consultas pendientes |
| `totalConsultas` | Total de consultas del periodo |
| `consultasPorEstado` | Conteos agrupados por estado |
| `consultasPorArea` | Conteos agrupados por área |
| `consultasPorTipoViolencia` | Conteos por tipo de violencia |
| `totalPersonasAtendidas` | Total de personas atendidas |
| `personasPorGenero` | Distribución por género |
| `personasPorEstrato` | Distribución por estrato |
| `personasPorZona` | Distribución por zona |
| `personasPorGrupoEtnico` | Distribución por grupo étnico |
| `personasPorMunicipio` | Distribución por municipio |
| `personasPorCondicion` | Distribución por condición |
| `procesosPorEstado` | Procesos agrupados por estado |
| `totalConciliaciones` | Total de conciliaciones |
| `conciliacionesPorEstado` | Conciliaciones agrupadas por estado |
| `totalSeguimientos` | Total de seguimientos |
| `seguimientosPorEstado` | Seguimientos agrupados por estado |
| `totalEstudiantesActivos` | Total de estudiantes activos |
| `totalEstudiantesHabilitadosConciliacion` | Estudiantes habilitados para conciliación |

---

## 4. Semestres disponibles

El sistema lista semestres disponibles a partir de información operativa registrada. El DTO `SemestreDTO` expone:

- año;
- semestre;
- etiqueta;
- periodo de inicio;
- periodo de fin.

---

## 5. Estadísticas por semestre

La consulta por semestre recibe año y semestre. El backend calcula el rango del periodo y retorna indicadores consolidados.

Ejemplo de endpoint:

```http
GET /api/estadisticas/2026/semestre/1
```

Este endpoint requiere `VER_REPORTES`.

---

## 6. Estadísticas por rango de fechas

El backend permite generar reportes entre `fechaInicio` y `fechaFin`.

Ejemplo:

```http
GET /api/estadisticas/reporte?fechaInicio=2026-01-01&fechaFin=2026-06-30
```

Las fechas se reciben con formato ISO `yyyy-MM-dd`.

---

## 7. Estadísticas por perfil

El sistema implementa estadísticas filtradas por:

- estudiante;
- asesor;
- monitor.

Endpoints:

```http
GET /api/estadisticas/{año}/semestre/{semestre}/estudiante/{id}
GET /api/estadisticas/{año}/semestre/{semestre}/asesor/{id}
GET /api/estadisticas/{año}/semestre/{semestre}/monitor/{id}
```

Estos endpoints requieren `VER_CONSULTAS`.

---

## 8. Reportes PDF

El módulo permite descargar PDF para:

- estadísticas por semestre;
- estadísticas por rango de fechas.

La respuesta se construye como `ResponseEntity<byte[]>` con encabezados para descarga de archivo PDF.

Endpoints:

```http
GET /api/estadisticas/{año}/semestre/{semestre}/pdf
GET /api/estadisticas/reporte/pdf?fechaInicio=2026-01-01&fechaFin=2026-06-30
```

---

## 9. Relación con entidades operativas

Las estadísticas se derivan de los módulos:

- consultas;
- personas;
- procesos;
- conciliaciones;
- seguimientos;
- estudiantes.

La actualización de fechas operativas de consultas, especialmente `lastUpdatedAt`, permite que los reportes reflejen actividad consolidada por periodos.

---

## 10. Seguridad

El controller usa permisos:

- `VER_REPORTES` para estadísticas globales y PDFs;
- `VER_CONSULTAS` para semestres disponibles y reportes por perfil.

Esto diferencia reportes institucionales de consultas asociadas a perfiles operativos.
