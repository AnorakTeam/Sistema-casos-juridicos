# Reglas de negocio - Seguimientos

## Regla 1. Todo seguimiento nuevo inicia pendiente

El backend crea todo seguimiento nuevo con estado `PENDIENTE` y `activo=true`.

## Regla 2. La descripción es obligatoria

La descripción se normaliza y no puede superar 200 caracteres.

## Regla 3. Fecha de entrega válida

La fecha de entrega, si se informa, no puede ser anterior a la fecha actual.

## Regla 4. Días de notificación válidos

`diasNotificacion` no puede ser negativo. Si se informa, debe existir `fechaEntrega`.

## Regla 5. Notificación al estudiante exige estudiante activo

Si `notificarEstudiante=true`, la consulta debe tener estudiante asignado y activo. Esta regla se valida en backend y protege la coherencia de tareas visibles para estudiantes.

## Regla 6. La consulta no cambia en edición

La consulta asociada define el contexto del seguimiento. Por eso no se permite cambiar `consultaId` al actualizar un seguimiento existente.

## Regla 7. Solo seguimientos pendientes son editables

El backend solo permite editar seguimientos en estado `PENDIENTE`.

## Regla 8. Las notificaciones se sincronizan después de guardar

Las notificaciones dependen del id del seguimiento. Por eso se crean o sincronizan luego de persistir el seguimiento.

## Regla 9. Al cancelar, completar o eliminar se cancelan notificaciones pendientes

Las notificaciones enviadas permanecen como historial. Las pendientes pueden desactivarse para evitar envíos no correspondientes.

## Regla 10. Respuesta rechazada exige observación

Una respuesta marcada como `RECHAZADA` debe incluir observación de revisión. La observación permite trazabilidad académica y operativa.

## Regla 11. Respuesta aprobada puede tener observación opcional

La aprobación puede incluir comentario, pero no lo exige.

## Regla 12. Respuestas pendientes bloquean cierre de consulta

Una consulta no puede cerrarse si tiene respuestas de seguimiento pendientes.

## Regla 13. Notificaciones pendientes bloquean cierre de consulta

Una consulta no puede cerrarse si existen notificaciones activas no enviadas asociadas a seguimientos activos.

## Regla 14. Alertas disciplinarias tienen permiso específico

La consulta de alertas disciplinarias requiere `VER_ALERTAS_DISCIPLINARIAS`.

## Reglas respaldadas por pruebas

Las pruebas unitarias cubren:

- notificación al estudiante con consulta y estudiante activo;
- rechazo de notificación a estudiante sin estudiante asignado;
- rechazo de estudiante inactivo;
- observación obligatoria al rechazar respuesta;
- decisión inválida pendiente;
- longitud máxima de observación;
- bloqueo de cierre de consulta por seguimientos, respuestas y notificaciones pendientes.
