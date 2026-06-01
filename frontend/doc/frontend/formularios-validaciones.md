# Formularios y validaciones

El frontend usa `react-hook-form` como librería de formularios en todos los módulos que lo requieren.

## Patrón de formulario estándar

Cada formulario principal sigue este patrón:

1. Verificar sesión y permisos al montar el componente.
2. Cargar catálogos y datos necesarios.
3. Mostrar el formulario con campos validados.
4. Al enviar, construir el payload y llamar al backend.
5. Mostrar toast de éxito o error según la respuesta.

```javascript
// Patrón básico con react-hook-form
const { register, handleSubmit, reset, formState: { errors } } = useForm({
  defaultValues: { nombre: "" },
});

const onSubmit = async (data) => {
  const res = await fetch(`${API_URL_BASE}/areas`, {
    method: "POST",
    credentials: "include",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });
  // manejo de respuesta...
};
```

## Componentes de formulario reutilizables

Ubicados en `src/components/forms/parts/`.

### FormInput

Campo de texto integrado con react-hook-form. Muestra el mensaje de error automáticamente.

```javascript
<FormInput
  name="nombre"
  label="Nombre del área"
  register={register}
  errors={errors}
  rules={{ required: "El nombre es obligatorio" }}
/>
```

### FormSelect

Campo select con lista de opciones `{ value, label }`.

```javascript
<FormSelect
  name="sedeId"
  label="Sede"
  options={sedes}
  register={register}
  errors={errors}
  rules={{ required: REQUIRED, valueAsNumber: true }}
/>
```

### FormCheckbox

Campo checkbox.

```javascript
<FormCheckbox name="activo" label="Activo" register={register} errors={errors} />
```

### FormFileUpload

Campo de carga de archivos con validación de tipo MIME y tamaño.

```javascript
<FormFileUpload
  name="archivos"
  label="Documentos a subir"
  multiple={true}
  setValue={setValue}
  value={archivos}
  errors={errors}
  tiposPermitidos={["application/pdf", "image/jpeg"]}  // opcional
  maxTamanoByte={10 * 1024 * 1024}                     // opcional, 10 MB por defecto
/>
```

Tipos permitidos por defecto: PDF, JPEG, PNG, WebP, Word y Excel. Tamaño máximo por defecto: 10 MB. Los archivos que no cumplen se rechazan con un toast antes de agregarse a la lista.

## Reglas de validación reutilizables

`src/lib/form-validation.js` exporta reglas comunes:

```javascript
import { requiredEmailRule, optionalEmailRule, nonNegativeNumberRule, maxNumberRule } from "@/lib/form-validation";

// Email obligatorio con formato válido
rules={requiredEmailRule()}

// Email opcional que valida formato solo si tiene valor
rules={optionalEmailRule()}

// Número no negativo
rules={{ required: REQUIRED, ...nonNegativeNumberRule() }}

// Número con máximo
rules={{ ...maxNumberRule(7, "El estrato máximo es 7") }}
```

## Formulario de persona por pasos (wizard)

`PersonaForm` implementa un formulario de múltiples pasos para el registro de personas.

### Pasos

| Paso | Sección | Campos principales |
|---|---|---|
| 0 | Identificación | Tipo persona, tipo documento, número, fechas, nombres, apellidos |
| 1 | Identidad | Pronombre, sexo, género, orientación sexual, etnia |
| 2 | Contacto | Teléfono, correo (al menos uno obligatorio) |
| 3 | Vivienda | Departamento, municipio, barrio, dirección, estrato, zona |
| 4 | Economía | Ocupación, empresa, salario, personas a cargo |
| 5 | Acudiente | Solo si la persona es menor de edad |
| 6 | Servicio | Cómo se enteró, relación con la universidad |

### Validación al avanzar de paso

Al pulsar "Siguiente", se llama `trigger(campos)` de react-hook-form para validar los campos del paso actual. Si alguno falla, no se avanza.

### Validación cruzada de contacto

Al enviar, se verifica que exista al menos teléfono o correo. Si ambos están vacíos, se muestra un toast y se navega al paso "Contacto".

## Formulario de nueva consulta jurídica

`NuevaConsultaForm` gestiona el estado del formulario con `useState` en lugar de react-hook-form, debido a la complejidad de los selectores de personas con modales.

### Validación antes de enviar

`validarFormularioConsulta()` verifica explícitamente antes del fetch:
- Fecha, estado, trámite, sede, área, tema, tipo.
- **Parte principal**: verificación explícita de `form.personaId` para evitar enviar `NaN` al backend.
- Descripción, hechos, pretensiones, concepto jurídico.

### Límites de caracteres en campos de texto libre

| Campo | maxLength |
|---|---|
| Descripción | 2000 |
| Hechos | 2000 |
| Pretensiones | 2000 |
| Concepto jurídico | 2000 |
| Observaciones | 500 |

### Conversión segura de IDs

Los IDs de personas y catálogos se convierten con `numberOrNull()` antes de enviar, lo que devuelve `null` si el valor está vacío o no es un número válido, evitando enviar `NaN` al backend.

## Formulario de permisos de roles

`RolePermissionsForm` gestiona qué páginas puede ver un rol y actualiza los permisos asociados automáticamente.

### Algoritmo de diff de permisos

Al guardar, el formulario calcula:
- `agregar`: permisos en el objetivo que no tiene el rol actualmente.
- `quitar`: permisos gestionados por este form que el rol tiene pero no están en el objetivo.

El cálculo usa `objetivo = unión de permisos de todas las páginas seleccionadas`. Si la página B comparte el permiso `Ver catálogos` con la página A, y se desmarca A pero B sigue seleccionada, el permiso no se quita porque sigue en el objetivo.

### Protección de acceso propio

El formulario no permite quitarle al propio rol del usuario el acceso a la página de Administración, mostrando un mensaje de error en la UI antes de aplicar el cambio.

## Cargue masivo de estudiantes

`ImportarEstudiantesForm` permite subir un archivo Excel con el formato requerido por el endpoint `POST /api/estudiantes/importar`.

El botón "Descargar plantilla" genera un CSV con los encabezados correctos y una fila de ejemplo, descargado directamente desde el navegador sin necesidad de un endpoint del backend.

La validación del archivo en el frontend verifica únicamente que sea `.xlsx` o `.xls`. Las validaciones de negocio (duplicados, campos faltantes) las ejecuta el backend fila por fila.

## Componentes de UI reutilizables

### ConfirmActionDialog

Diálogo de confirmación usado antes de acciones destructivas o irreversibles (desactivar, archivar, etc.).

```javascript
import { ConfirmActionDialog } from "@/components/ui/ConfirmActionDialog";

<ConfirmActionDialog
  open={Boolean(confirmDialog)}
  title="Desactivar área"
  description="¿Deseas desactivar esta área?"
  confirmText="Desactivar"
  cancelText="Cancelar"
  loading={confirmLoading}
  variant="destructive"     // opcional, controla el color del botón de confirmación
  onClose={() => setConfirmDialog(null)}
  onConfirm={confirmarDesactivar}
/>
```

### Pagination

Control de paginación con selector de tamaño de página. Se usa en todos los listados paginados.

```javascript
import Pagination from "@/components/ui/Pagination";
import { DEFAULT_PAGE_SIZE_OPTIONS, getTotalPages, paginateItems } from "@/lib/list-utils";

<Pagination
  currentPage={paginaActual}
  totalPages={totalPaginas}
  onPageChange={setPaginaActual}
  pageSize={registrosPorPagina}
  onPageSizeChange={(value) => { setRegistrosPorPagina(value); setPaginaActual(1); }}
  pageSizeOptions={DEFAULT_PAGE_SIZE_OPTIONS}   // [5, 10, 20, 50]
  totalItems={items.length}
/>
```

Las utilidades `getTotalPages` y `paginateItems` de `src/lib/list-utils.js` calculan el total de páginas y extraen el subconjunto de items de la página actual.

## Qué NO hace la validación frontend

- No cambia el estado de recursos en el backend directamente.
- No construye rutas internas del backend.
- No asume que el usuario tiene permisos porque pasó la validación visual.
- No reemplaza la validación backend de reglas de negocio.

La validación backend es siempre la fuente de verdad.
