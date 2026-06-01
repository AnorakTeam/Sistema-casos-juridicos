# Módulo: Personas

## Propósito

Permite registrar y gestionar las personas que consultan el consultorio jurídico. El formulario de registro captura datos de identificación, identidad, contacto, vivienda, economía, acudiente (para menores) y relación con el servicio.

Ver `doc/api/personas.md` para la especificación completa del backend.

## Pantallas y rutas

| Ruta | Componente principal | Descripción |
|---|---|---|
| `/recepcion` | `PersonaForm` (nuevo) | Formulario de registro de persona nueva |
| `/personas` | `PersonasForm` + `PersonaForm` (editar) | Listado y edición de personas |

## Componentes

```text
src/components/forms/persona/PersonaForm.jsx
src/components/forms/persona/PersonasForm.jsx
```

## Permisos

| Permiso | Uso |
|---|---|
| `Acceder recepción` | Acceder a la pantalla de registro. |
| `Acceder personas` | Acceder al listado de personas. |
| `Crear personas` | Usar el formulario de registro. |
| `Ver personas` | Cargar el listado de personas. |
| `Editar personas` | Mostrar botones de edición. |
| `Cambiar estado personas` | Mostrar botones de desactivar/reactivar. |

## Endpoints consumidos

### Listado

```text
GET /api/personas
GET /api/personas/activos
```

### Detalle

```text
GET /api/personas/{id}
```

### Crear

```text
POST /api/personas
```

### Editar

```text
PUT /api/personas/{id}
```

### Cambiar estado

```text
PATCH /api/personas/{id}/desactivar
PATCH /api/personas/{id}/reactivar
```

### Catálogos para el formulario

```text
GET /api/tipos-documento/activos
GET /api/tipos-persona
GET /api/nacionalidades
GET /api/condiciones
GET /api/ocupaciones
GET /api/empresas
GET /api/departamentos
GET /api/municipios/departamento/{departamentoId}   ← cascade por departamento
GET /api/barrios/municipio/{municipioId}            ← cascade por municipio
```

## Formulario por pasos (wizard)

`PersonaForm` divide el registro en pasos. El paso "Acudiente" solo aparece si la persona es menor de edad según la fecha de nacimiento.

| Paso | Sección | Campos clave |
|---|---|---|
| 0 | Identificación | Tipo persona, tipo documento, número, fechas, nombres, apellidos |
| 1 | Identidad | Pronombre, sexo, género, orientación sexual, grupo étnico, discapacidad |
| 2 | Contacto | Teléfono, correo (al menos uno obligatorio) |
| 3 | Vivienda | Departamento → municipio → barrio (cascade), dirección, estrato, zona |
| 4 | Economía | Ocupación, empresa, salario, personas a cargo, servicios básicos |
| 5 | Acudiente | Solo si menor de edad: nombre, relación, teléfono, correo, dirección |
| 6 | Servicio | Cómo se enteró, relación con la universidad |

### Validaciones clave

- Al pulsar "Siguiente" desde el paso "Identificación", se validan los campos obligatorios del paso con `trigger()` antes de avanzar.
- Al enviar, se verifica que exista al menos teléfono o correo (validación cruzada). Si faltan ambos, se muestra toast y se navega al paso "Contacto".
- Estrato: mínimo 0, máximo 7, validado en react-hook-form y en el atributo HTML.
- Personas a cargo: mínimo 0, máximo 10, validado en react-hook-form y en HTML.

### Cascade de ubicación

Al seleccionar departamento → se cargan municipios de ese departamento.
Al seleccionar municipio → se cargan barrios de ese municipio.
Al cambiar departamento → se resetean municipio y barrio.
Al cambiar municipio → se resetea barrio.
