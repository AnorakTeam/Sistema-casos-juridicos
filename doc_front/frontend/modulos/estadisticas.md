# Módulo: Estadísticas

## Propósito

Muestra estadísticas globales del consultorio jurídico para el administrador, y estadísticas filtradas por perfil en el panel de inicio de cada usuario.

Ver `doc/api/` para los endpoints del backend (módulo de estadísticas).

## Pantallas y rutas

| Ruta | Componente | Descripción |
|---|---|---|
| `/estadisticas` | `EstadisticasForm` | Dashboard completo solo para admin (permiso `Ver reportes`) |
| `/inicio` | `InicioForm` | Panel de control con estadísticas y listas operativas por rol |

## Componentes

```text
src/components/forms/estadisticas/EstadisticasForm.jsx
src/components/forms/inicio/InicioForm.jsx
```

## Permisos

| Permiso | Uso |
|---|---|
| `Ver reportes` | Acceder a `/estadisticas` y ver estadísticas globales. |
| `Ver consultas` | Ver estadísticas filtradas por perfil en `/inicio`. |

## Endpoints consumidos

### Semestres disponibles

```text
GET /api/estadisticas/semestres
```

Devuelve la lista de semestres desde 2024 hasta el semestre actual. Se usa para poblar el selector de semestre.

### Estadísticas globales por semestre (admin)

```text
GET /api/estadisticas/{año}/semestre/{semestre}
```

Permiso requerido: `Ver reportes`.

### Estadísticas globales por rango libre (admin)

```text
GET /api/estadisticas/reporte?fechaInicio=YYYY-MM-DD&fechaFin=YYYY-MM-DD
```

Permiso requerido: `Ver reportes`.

Cuando es rango libre, los campos `año` y `semestre` llegan `null` en la respuesta. El frontend usa `periodoInicio` y `periodoFin` para mostrar el rango.

### PDF por semestre

```text
GET /api/estadisticas/{año}/semestre/{semestre}/pdf
```

### PDF por rango libre

```text
GET /api/estadisticas/reporte/pdf?fechaInicio=YYYY-MM-DD&fechaFin=YYYY-MM-DD
```

### Estadísticas por perfil (inicio/dashboard)

```text
GET /api/estadisticas/{año}/semestre/{semestre}/asesor/{perfilId}
GET /api/estadisticas/{año}/semestre/{semestre}/monitor/{perfilId}
GET /api/estadisticas/{año}/semestre/{semestre}/estudiante/{perfilId}
```

El `perfilId` se obtiene del campo `perfilId` del objeto de usuario en `/api/auth/me`.

## Cálculo del semestre actual en JavaScript

```javascript
const hoy = new Date();
const año = hoy.getFullYear();
const semestre = hoy.getMonth() >= 6 ? 2 : 1;
```

## Selección de endpoint en el inicio por rol

```javascript
if (tienePermiso(user, PERMISOS.VER_REPORTES) || esAdministrativo(user)) {
  url = `/api/estadisticas/${año}/semestre/${semestre}`;
} else if (esAsesor(user)) {
  url = `/api/estadisticas/${año}/semestre/${semestre}/asesor/${user.perfilId}`;
} else if (esMonitor(user)) {
  url = `/api/estadisticas/${año}/semestre/${semestre}/monitor/${user.perfilId}`;
} else if (esEstudiante(user)) {
  url = `/api/estadisticas/${año}/semestre/${semestre}/estudiante/${user.perfilId}`;
}
```

## Página de estadísticas (solo admin)

`EstadisticasForm` tiene dos modos de consulta seleccionables mediante tabs:

- **Por semestre**: selector de semestre + botón de actualizar.
- **Rango personalizado**: inputs `fechaInicio` y `fechaFin` tipo date + botón "Consultar".

### Validaciones del rango personalizado

- `fechaInicio` no puede ser posterior a `fechaFin`.
- Fecha mínima: 2024-01-01.
- Fecha máxima: hoy.

### Visualización

Las estadísticas se presentan como tarjetas clicables. Al hacer clic en una tarjeta se expande un panel de detalle con gráficos específicos para esa categoría. Solo puede haber un panel abierto a la vez.

| Tarjeta | Campo | Detalle al hacer clic |
|---|---|---|
| Consultas | `totalConsultas` | Donut finalizadas/pendientes, por estado, tipo violencia, bar chart áreas |
| Personas atendidas | `totalPersonasAtendidas` | Por género, estrato, zona, grupo étnico, condición, municipio |
| Conciliaciones | `totalConciliaciones` | Conciliaciones por estado |
| Seguimientos | `totalSeguimientos` | Seguimientos por estado, procesos por estado |
| Estudiantes activos | `totalEstudiantesActivos` | Total activos + habilitados para conciliación |

### Exportar PDF

El botón "Exportar PDF" detecta el modo activo (semestre o rango) y llama al endpoint correspondiente. El archivo se descarga directamente desde el blob de la respuesta.

## Panel de inicio por rol (InicioForm)

El inicio muestra estadísticas del semestre actual y listas operativas según el rol:

| Rol | Estadísticas | Listas adicionales |
|---|---|---|
| Admin | Globales + bar chart de áreas | Consultas pendientes (10) + respuestas por calificar (10) |
| Asesor / Monitor | Por perfil (sin áreas) | Consultas pendientes (10) + respuestas por calificar (10) |
| Estudiante | Por perfil (sin áreas) | Consultas pendientes (10) + tareas pendientes (10) |

### Listas operativas en el inicio

**Consultas pendientes**: `GET /api/consultas` → filtra por estado activo/pendiente → primeras 10.

**Tareas pendientes (estudiante)**: Para el estudiante no existe un endpoint de seguimientos plano. El flujo correcto es:
1. `GET /api/consultas` → obtiene las consultas del estudiante.
2. Por cada consulta (máximo 5): `GET /api/seguimientos/consulta/{id}/visibles-estudiante`.
3. Filtra los seguimientos con estado `PENDIENTE` → primeras 10.

**Respuestas por calificar (asesor/admin)**: `GET /api/seguimientos/respuestas/pendientes` → primeras 10.
