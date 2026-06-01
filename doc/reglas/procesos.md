# Reglas de negocio - Procesos

## Principio general

Un proceso representa una actuación formal vinculada a una consulta jurídica. Su ciclo funcional se administra con `EstadoProceso` y su permanencia histórica se administra con la marca `activo`.

## Regla 1. El proceso nace pendiente

Todo proceso creado por el backend inicia en estado `PENDIENTE`. El cliente no controla el estado inicial.

## Regla 2. Radicado opcional en estado pendiente

Mientras el proceso está en `PENDIENTE`, `numeroRadicado` puede ser nulo. Esta regla permite registrar gestión preliminar antes de contar con radicado formal.

## Regla 3. Radicado obligatorio en estados finales

Los estados finales son:

- `SENTENCIA_FAVORABLE`
- `SENTENCIA_DESFAVORABLE`
- `DESISTIMIENTO`
- `RECHAZO`
- `PRESCRIPCION`

Para cualquiera de estos estados, el backend exige número de radicado.

## Regla 4. Longitud del radicado

Cuando se informa radicado, debe tener exactamente 23 caracteres.

## Regla 5. Unicidad del radicado

Si se informa radicado, debe ser único frente a otros procesos. En actualización se excluye el proceso actual para permitir conservar el mismo radicado.

## Regla 6. La consulta no cambia en edición

La consulta asociada define el alcance del proceso. Por eso, al actualizar un proceso existente no se permite modificar `consultaId`.

## Regla 7. La consulta debe permitir operación

No se realizan operaciones de creación, edición o cambio de estado sobre procesos cuya consulta asociada no permita operación operativa.

## Regla 8. Especialidad coherente con órgano de control

Si se informa especialidad, debe pertenecer al órgano de control seleccionado. Si se informa especialidad sin órgano de control, la operación se rechaza.

## Regla 9. Estado funcional y activo son conceptos separados

`estado` representa el resultado funcional del proceso.

`activo` representa la disponibilidad lógica del registro.

La eliminación por endpoint no borra físicamente el proceso; lo desactiva.

## Regla 10. Procesos pendientes bloquean cierre de consulta

Una consulta no puede cerrarse si tiene procesos activos en estado `PENDIENTE`. Esta regla se valida desde el servicio de estado de consulta.

## Regla 11. Gestión restringida por permisos

Las operaciones de escritura requieren `GESTIONAR_PROCESOS`. La lectura requiere `VER_PROCESOS` o `GESTIONAR_PROCESOS`.

## Regla 12. El alcance se hereda desde la consulta

El proceso no define por sí solo el alcance del usuario. El acceso se determina con base en la consulta asociada.

## Reglas respaldadas por pruebas

`ProcesoValidatorTest` valida:

- radicado nulo permitido en pendiente;
- texto vacío interpretado como radicado nulo en pendiente;
- rechazo de estado final sin radicado;
- rechazo de radicado con longitud inválida;
- aceptación de radicado válido.
