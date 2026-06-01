# Reglas de negocio - Estadísticas y reportes

> Documento validado contra el código fuente actualizado del sistema. La documentación describe únicamente comportamiento implementado en backend.


## 1. Regla general

Las estadísticas se calculan a partir de información operativa persistida en el sistema. No se ingresan manualmente; se derivan de consultas, personas, procesos, seguimientos, conciliaciones y estudiantes.

---

## 2. Periodos

El sistema maneja estadísticas por:

- semestre académico;
- rango libre de fechas;
- perfil operativo dentro de un semestre.

Los semestres disponibles se exponen mediante DTOs con año, semestre, etiqueta y fechas del periodo.

---

## 3. Indicadores de consultas

El reporte incluye:

- consultas finalizadas;
- consultas pendientes;
- total de consultas;
- consultas por estado;
- consultas por área;
- consultas por tipo de violencia.

---

## 4. Indicadores de personas

El reporte incluye conteos de personas por:

- género;
- estrato;
- zona;
- grupo étnico;
- municipio;
- condición.

---

## 5. Indicadores jurídicos y académicos

El reporte incluye:

- procesos por estado;
- total de conciliaciones;
- conciliaciones por estado;
- total de seguimientos;
- seguimientos por estado;
- estudiantes activos;
- estudiantes habilitados para conciliación.

---

## 6. Reglas de acceso

- Los reportes institucionales requieren `VER_REPORTES`.
- La consulta de semestres puede realizarse con `VER_REPORTES` o `VER_CONSULTAS`.
- Las estadísticas por estudiante, asesor y monitor requieren `VER_CONSULTAS`.

---

## 7. Reportes PDF

El sistema genera documentos PDF con las estadísticas consultadas. Los PDFs se generan en backend mediante `EstadisticasPdfService` y se retornan como bytes en la respuesta HTTP.
