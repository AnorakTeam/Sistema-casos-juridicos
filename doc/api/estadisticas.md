# API - Estadísticas

> Documento validado contra el código fuente actualizado del sistema. La documentación describe únicamente comportamiento implementado en backend.


## 1. Ruta base

```http
/api/estadisticas
```

La API de estadísticas expone reportes consolidados en JSON y PDF.

---

## 2. Endpoints

| Método | Endpoint | Descripción | Permiso |
|---|---|---|---|
| GET | `/api/estadisticas/semestres` | Lista semestres disponibles | `VER_REPORTES` o `VER_CONSULTAS` |
| GET | `/api/estadisticas/{año}/semestre/{semestre}` | Estadísticas por semestre | `VER_REPORTES` |
| GET | `/api/estadisticas/{año}/semestre/{semestre}/pdf` | PDF por semestre | `VER_REPORTES` |
| GET | `/api/estadisticas/reporte?fechaInicio={fecha}&fechaFin={fecha}` | Estadísticas por rango | `VER_REPORTES` |
| GET | `/api/estadisticas/reporte/pdf?fechaInicio={fecha}&fechaFin={fecha}` | PDF por rango | `VER_REPORTES` |
| GET | `/api/estadisticas/{año}/semestre/{semestre}/estudiante/{id}` | Estadísticas por estudiante | `VER_CONSULTAS` |
| GET | `/api/estadisticas/{año}/semestre/{semestre}/asesor/{id}` | Estadísticas por asesor | `VER_CONSULTAS` |
| GET | `/api/estadisticas/{año}/semestre/{semestre}/monitor/{id}` | Estadísticas por monitor | `VER_CONSULTAS` |

---

## 3. Formato de fechas

Los endpoints de rango reciben:

```http
fechaInicio=yyyy-MM-dd
fechaFin=yyyy-MM-dd
```

Ejemplo:

```http
GET /api/estadisticas/reporte?fechaInicio=2026-01-01&fechaFin=2026-06-30
```

---

## 4. Respuesta de semestre

```json
{
  "año": 2026,
  "semestre": 1,
  "periodoInicio": "2026-01-01",
  "periodoFin": "2026-06-30",
  "consultasFinalizadas": 12,
  "consultasPendientes": 4,
  "totalConsultas": 16,
  "consultasPorEstado": [
    { "nombre": "CERRADO", "cantidad": 12 }
  ],
  "consultasPorArea": [
    { "nombre": "Civil", "cantidad": 8 }
  ],
  "procesosPorEstado": [
    { "nombre": "PENDIENTE", "cantidad": 3 }
  ],
  "totalConciliaciones": 5,
  "totalSeguimientos": 10,
  "totalEstudiantesActivos": 20,
  "totalEstudiantesHabilitadosConciliacion": 6
}
```

---

## 5. Respuesta de semestres disponibles

```json
[
  {
    "año": 2026,
    "semestre": 1,
    "etiqueta": "2026-1",
    "periodoInicio": "2026-01-01",
    "periodoFin": "2026-06-30"
  }
]
```

---

## 6. Descarga PDF

Los endpoints PDF retornan bytes con encabezados de archivo. El nombre del archivo se construye en el backend según el tipo de reporte.

Ejemplos:

```http
GET /api/estadisticas/2026/semestre/1/pdf
GET /api/estadisticas/reporte/pdf?fechaInicio=2026-01-01&fechaFin=2026-06-30
```

---

## 7. Consideraciones de consumo

- Las estadísticas globales usan `VER_REPORTES`.
- Las estadísticas por perfil usan `VER_CONSULTAS`.
- Las respuestas JSON usan `EstadisticasSemestreDTO`.
- Los conteos agregados usan `ConteoDTO`.
- Los reportes PDF usan `application/pdf` en la respuesta.
