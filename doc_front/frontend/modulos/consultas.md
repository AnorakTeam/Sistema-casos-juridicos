# Módulo: Consultas jurídicas

## Propósito

Permite crear, consultar, editar y gestionar el estado de consultas jurídicas. Incluye asignación de responsables, partes y contrapartes, y archivos adjuntos.

Ver `doc/api/consultas.md` y `doc/reglas/consultas.md` para la especificación completa del backend.

## Pantallas y rutas

| Ruta | Componente principal | Descripción |
|---|---|---|
| `/nuevaconsulta` | `NuevaConsultaForm` | Formulario de creación de consulta |
| `/consultasjuridicas` | `ConsultasJuridicasForm` | Listado y gestión de consultas |

## Componentes

```text
src/components/forms/consulta/NuevaConsultaForm.jsx
src/components/forms/consulta/ConsultasJuridicasForm.jsx
src/components/forms/parts/ArchivosConsultaForm.jsx
```

## Permisos

| Permiso | Uso |
|---|---|
| `Acceder nueva consulta` | Mostrar el ítem en el menú lateral. |
| `Acceder consultas jurídicas` | Mostrar el ítem en el menú lateral. |
| `Crear consultas` | Acceder al formulario de nueva consulta. |
| `Ver consultas` | Cargar el listado de consultas. |
| `Editar consultas` | Mostrar botones de edición. |
| `Cambiar estado consultas` | Mostrar acciones de cambio de estado. |
| `Archivar consultas` | Mostrar botón de archivar. |
| `Asignar responsables consulta` | Mostrar selectores de asesor, monitor y estudiante. |

## Endpoints consumidos

### Listado de consultas

```text
GET /api/consultas
GET /api/consultas?search=texto&estado=ACTIVO&areaId=1
```

### Detalle de consulta

```text
GET /api/consultas/{id}
```

### Crear consulta

```text
POST /api/consultas
```

### Editar consulta

```text
PUT /api/consultas/{id}
```

### Cambiar estado

```text
PATCH /api/consultas/{id}/estado?estado=CODIGO
```

### Archivar

```text
PATCH /api/consultas/{id}/archivar
```

### Desarchivar

```text
PATCH /api/consultas/{id}/desarchivar
```

### Catálogos necesarios para el formulario

```text
GET /api/personas/activos
GET /api/sedes
GET /api/areas
GET /api/temas/area/{areaId}      ← se carga al seleccionar área
GET /api/tipos/tema/{temaId}      ← se carga al seleccionar tema
GET /api/asesores/activos         ← solo si tiene permiso ASIGNAR_RESPONSABLES_CONSULTA
GET /api/monitores/activos        ← solo si tiene permiso ASIGNAR_RESPONSABLES_CONSULTA
GET /api/estudiantes/activos      ← solo si tiene permiso ASIGNAR_RESPONSABLES_CONSULTA
```

### Archivos adjuntos

```text
GET  /files/list/{consultaId}                          ← lista archivos existentes
GET  /files/download/{consultaId}/{fileName}           ← descarga archivo
POST /files/upload-multiple   multipart/form-data      ← sube archivos tras crear consulta
```

Los endpoints de archivos usan `FILE_STORAGE_API_URL_BASE`.

## Formulario de nueva consulta

El formulario usa `useState` en lugar de `react-hook-form` por la complejidad de los selectores de personas con modales.

### Campos obligatorios

fecha, estado, trámite, sede, área, tema, tipo, parte principal, descripción, hechos, pretensiones, concepto jurídico.

### Validación antes de enviar

`validarFormularioConsulta()` verifica todos los campos obligatorios antes del `fetch`. El campo `personaId` se verifica explícitamente para evitar enviar `NaN` al backend.

### Conversión segura de IDs

```javascript
personaId: numberOrNull(form.personaId)   // devuelve null si está vacío, no NaN
sedeId:    numberOrNull(form.sedeId)
areaId:    numberOrNull(form.areaId)
```

### Límites de texto

| Campo | maxLength |
|---|---|
| Descripción | 2000 |
| Hechos | 2000 |
| Pretensiones | 2000 |
| Concepto jurídico | 2000 |
| Observaciones | 500 |

### Archivos adjuntos

Los archivos se suben en una segunda petición después de crear la consulta exitosamente. Si la subida falla, se muestra un `toast.warning` indicando que la consulta se creó pero los archivos no se pudieron subir.

## Selección de personas (modales)

La selección de parte principal, partes adicionales y contrapartes se hace mediante modales de búsqueda:

- **Parte principal**: selección única. El modal filtra personas que no estén ya en partes adicionales ni contrapartes.
- **Partes adicionales**: selección múltiple. Filtra personas que no sean la parte principal ni contrapartes.
- **Contrapartes**: selección múltiple. Filtra personas que no sean la parte principal ni partes adicionales.

## Cascade de catálogos

El formulario carga temas y tipos de forma encadenada:
- Al seleccionar un área → se cargan los temas de esa área.
- Al seleccionar un tema → se cargan los tipos de ese tema.
- Si cambia el área → se resetea el tema y el tipo seleccionados.
- Si cambia el tema → se resetea el tipo seleccionado.
