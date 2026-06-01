# Frontend - Módulo de estadísticas

## 1. Propósito del módulo

El módulo de estadísticas permite visualizar información agregada del consultorio jurídico a partir de los datos operativos del backend. La interfaz ofrece una pantalla principal para reportes globales y también integra estadísticas del semestre actual en el panel de inicio.

Componentes principales:

```text
src/components/forms/estadisticas/EstadisticasForm.jsx
src/components/forms/inicio/InicioForm.jsx
```

## 2. Rutas relacionadas

| Ruta | Componente | Propósito |
|---|---|---|
| `/estadisticas` | `EstadisticasForm` | Consulta de estadísticas globales por semestre o rango de fechas y descarga de PDF. |
| `/inicio` | `InicioForm` | Panel de inicio con tarjetas, gráficos y listas operativas según perfil. |

La ruta `/estadisticas` exige permiso de reportes. La ruta `/inicio` utiliza el usuario autenticado para mostrar información operativa correspondiente a su perfil.

## 3. Archivos relacionados

```text
src/app/(dashboard)/estadisticas/page.js
src/app/(dashboard)/inicio/page.js
src/components/forms/estadisticas/EstadisticasForm.jsx
src/components/forms/inicio/InicioForm.jsx
src/lib/config.js
src/lib/permission.js
src/lib/authz.js
```

## 4. Permisos usados

| Permiso | Uso frontend |
|---|---|
| `Ver reportes` | Permite acceder a `/estadisticas` y consultar estadísticas globales. |
| `Ver consultas` | Permite cargar estadísticas por perfil en el panel de inicio cuando aplica. |

La navegación lateral muestra `Estadísticas` cuando el usuario tiene permiso `Ver reportes`.

## 5. Componente `EstadisticasForm`

`EstadisticasForm` implementa la vista principal de reportes. Al iniciar:

```text
GET /api/auth/me
GET /api/estadisticas/semestres
```

Si el usuario no tiene permiso `Ver reportes`, la pantalla redirige a `/inicio`.

## 6. Modos de consulta en la pantalla de estadísticas

La pantalla permite trabajar en dos modos:

| Modo | Endpoint principal | Uso |
|---|---|---|
| Semestre | `GET /api/estadisticas/{año}/semestre/{semestre}` | Consulta datos agregados de un semestre seleccionado. |
| Rango libre | `GET /api/estadisticas/reporte?fechaInicio=YYYY-MM-DD&fechaFin=YYYY-MM-DD` | Consulta datos agregados entre dos fechas. |

El usuario puede descargar PDF para ambos modos.

## 7. Endpoints consumidos por `EstadisticasForm`

### 7.1 Semestres disponibles

```text
GET /api/estadisticas/semestres
```

Alimenta el selector de periodo.

### 7.2 Estadísticas por semestre

```text
GET /api/estadisticas/{año}/semestre/{semestre}
```

Carga conteos y distribuciones del periodo seleccionado.

### 7.3 Estadísticas por rango de fechas

```text
GET /api/estadisticas/reporte?fechaInicio=YYYY-MM-DD&fechaFin=YYYY-MM-DD
```

Permite generar un reporte con fechas personalizadas.

### 7.4 PDF por semestre

```text
GET /api/estadisticas/{año}/semestre/{semestre}/pdf
```

La interfaz descarga el blob recibido y crea un enlace temporal para el archivo.

### 7.5 PDF por rango

```text
GET /api/estadisticas/reporte/pdf?fechaInicio=YYYY-MM-DD&fechaFin=YYYY-MM-DD
```

Genera el PDF correspondiente al rango seleccionado.

## 8. Descarga de PDF

La función de descarga construye la URL según el modo activo. Después:

```text
1. Ejecuta fetch con credentials: include.
2. Verifica respuesta HTTP.
3. Convierte la respuesta en Blob.
4. Crea un objeto URL temporal.
5. Simula clic de descarga.
6. Libera el objeto URL.
```

Los nombres de archivo se generan según el periodo consultado.

## 9. Componente `InicioForm`

`InicioForm` presenta un panel inicial por rol. Calcula el semestre actual desde el navegador y carga estadísticas según el perfil del usuario.

Para usuarios administrativos o con `Ver reportes`, usa:

```text
GET /api/estadisticas/{año}/semestre/{semestre}
```

Para perfiles operativos usa endpoints específicos:

```text
GET /api/estadisticas/{año}/semestre/{semestre}/asesor/{perfilId}
GET /api/estadisticas/{año}/semestre/{semestre}/monitor/{perfilId}
GET /api/estadisticas/{año}/semestre/{semestre}/estudiante/{perfilId}
```

El `perfilId` proviene de la respuesta de `/api/auth/me`.

## 10. Información operativa del panel de inicio

Además de estadísticas, `InicioForm` carga listas operativas:

```text
GET /api/consultas
GET /api/seguimientos/consulta/{consultaId}/visibles-estudiante
GET /api/seguimientos/respuestas/pendientes
```

El objetivo de estas listas es mostrar al usuario información útil de trabajo: consultas pendientes, tareas visibles para estudiante y respuestas pendientes de calificación.

## 11. Visualización

El frontend usa componentes internos SVG y tarjetas para representar:

- total de consultas;
- consultas finalizadas;
- consultas pendientes;
- porcentaje de avance;
- distribución por área;
- listas operativas por perfil.

La visualización se construye en el cliente a partir de los DTOs entregados por el backend.

## 12. Manejo de errores

El componente maneja errores de conexión, errores de permisos y errores de disponibilidad de PDF. Cuando una consulta no puede cargarse, se muestra mensaje sin comprometer el resto de la interfaz.

## 13. Relación con backend

La estructura y significado de los datos estadísticos se documenta en:

```text
doc/backend/estadisticas.md
doc/api/estadisticas.md
doc/reglas/estadisticas.md
```

El frontend no calcula estadísticas de negocio principales; consume los agregados que entrega el backend y los presenta visualmente.
