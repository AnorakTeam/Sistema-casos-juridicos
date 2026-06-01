# Reglas de negocio - Conciliaciones

## Regla 1. La conciliación nace desde una consulta

Toda conciliación se crea asociada a una consulta existente. La consulta no puede estar cerrada ni archivada.

## Regla 2. Una consulta no debe tener más de una conciliación activa no finalizada

El backend bloquea la creación de una conciliación nueva si la consulta ya tiene una conciliación activa en estado no finalizado.

## Regla 3. Estados no finalizados

Se consideran estados no finalizados:

- `EN_ESPERA`
- `ESPERANDO_REUNION`
- `REUNION_PROGRAMADA`

## Regla 4. Estados finalizados

Se consideran estados finalizados:

- `COMPLETO_CONCILIADO`
- `COMPLETO_NO_CONCILIADO`

## Regla 5. Finalización solo con acta

La conciliación no puede pasar a estado final por el endpoint general de cambio de estado. Debe usarse el endpoint de finalización con acta.

## Regla 6. El estado `EN_ESPERA` se calcula automáticamente

El backend no permite asignar manualmente `EN_ESPERA` porque depende de la existencia de responsables mínimos.

## Regla 7. Programar reunión exige responsables

Para programar reunión se requiere estudiante y conciliador asignados.

## Regla 8. Programar reunión exige fecha futura y sede activa

La fecha de reunión es obligatoria y debe ser futura. La sede debe existir y estar activa.

## Regla 9. Reprogramar cancela pendientes anteriores

Al reprogramar una reunión se cancelan notificaciones pendientes anteriores y se crean nuevas notificaciones.

## Regla 10. Finalizar cancela notificaciones pendientes

Cuando se finaliza la conciliación, las notificaciones pendientes de reunión se desactivan. Las enviadas se conservan como historial.

## Regla 11. Desactivar cancela notificaciones pendientes

Cuando una conciliación se desactiva, las notificaciones pendientes también se cancelan.

## Regla 12. Conciliaciones pendientes bloquean cierre de consulta

Una consulta no puede cerrarse si tiene conciliaciones activas no finalizadas.

## Regla 13. Estudiante debe estar habilitado para conciliación

La asignación exige estudiante activo y con bandera de conciliación habilitada.

## Regla 14. Conciliador debe estar activo

La asignación exige conciliador activo.

## Regla 15. Las operaciones respetan permisos y alcance

La lectura, gestión, programación, reprogramación y conclusión de conciliaciones están separadas por permisos específicos. El alcance se resuelve en servicios de acceso del módulo.
