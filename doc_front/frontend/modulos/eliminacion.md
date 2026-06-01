# Módulo: Eliminación y reactivación

## Propósito

Permite desactivar y reactivar registros del sistema desde una pantalla centralizada: personas, usuarios, estudiantes, consultas y archivar/desarchivar consultas.

## Pantalla y ruta

| Ruta | Componente | Descripción |
|---|---|---|
| `/eliminacion` | `EliminacionForm` | Gestión centralizada de estado activo de registros |

## Componente

```text
src/components/forms/EliminacionForm.jsx
```

## Permisos

| Permiso | Uso |
|---|---|
| `Acceder eliminación` | Mostrar el ítem en el menú lateral y acceder a la pantalla. |
| `Cambiar estado personas` | Desactivar/reactivar personas. |
| `Cambiar estado usuarios` | Desactivar/reactivar usuarios del sistema. |
| `Cambiar estado estudiantes` | Desactivar estudiantes. |
| `Cambiar estado consultas` | Cambiar estado de consultas. |
| `Archivar consultas` | Archivar y desarchivar consultas. |

## Endpoints consumidos

Los endpoints de esta pantalla corresponden a los de cada módulo:

```text
PATCH /api/personas/{id}/desactivar
PATCH /api/personas/{id}/reactivar
PATCH /api/usuarios/{id}/activo?activo=false
PATCH /api/usuarios/{id}/activo?activo=true
PATCH /api/estudiantes/{id}/activo?activo=false
PATCH /api/consultas/{id}/archivar
PATCH /api/consultas/{id}/desarchivar
```

La pantalla verifica los permisos del usuario antes de mostrar cada sección. Si el usuario no tiene el permiso para una categoría, esa sección no aparece.
