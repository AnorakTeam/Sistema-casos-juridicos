# Decisión técnica - Módulo de conciliación

## Contexto

El módulo de conciliación permite generar conciliaciones desde consultas jurídicas, asignar responsables, programar reuniones, notificar destinatarios y finalizar el trámite con soporte documental.

El módulo se integra con consulta, perfiles, permisos, archivos, reuniones, notificaciones y cierre de casos.

## Decisiones principales

1. La conciliación nace desde una consulta.
2. La consulta es fuente de persona principal, partes y contrapartes.
3. El estado de conciliación se administra en tabla.
4. El backend valida estados por código técnico.
5. La solicitud PDF se guarda al crear conciliación.
6. La finalización exige acta PDF.
7. El estudiante puede consultar recursos relacionados, pero no gestiona conciliaciones.
8. El conciliador opera conciliaciones asignadas.
9. El cierre de consulta se bloquea si hay conciliación pendiente.
10. `activo` no reemplaza el estado funcional.
11. La reunión vigente se administra en entidad propia.
12. Programación y reprogramación generan historial.
13. Finalizar o desactivar conciliación cancela notificaciones pendientes.

## Conciliación nace desde consulta

La conciliación se crea desde:

```text
/api/conciliaciones/consulta/{consultaId}
```

Justificación:

- hereda contexto jurídico;
- evita duplicar datos de partes y contrapartes;
- conserva trazabilidad con la consulta origen;
- permite validar alcance y estado de la consulta.

## Consulta como fuente de partes y contrapartes

La conciliación no almacena partes propias. El detalle toma la información desde la consulta.

Esto permite mantener consistencia entre consulta y conciliación.

## Tabla `estado_conciliacion`

Se usa tabla para estados de conciliación:

```text
estado_conciliacion
```

Campos:

- `codigo`;
- `nombre`;
- `activo`;
- `orden`.

Justificación:

- el frontend puede mostrar nombres visibles;
- el backend valida códigos técnicos;
- el orden puede controlarse desde datos;
- se conserva independencia entre texto visible y regla de negocio.

## Códigos técnicos

```text
EN_ESPERA
ESPERANDO_REUNION
REUNION_PROGRAMADA
COMPLETO_CONCILIADO
COMPLETO_NO_CONCILIADO
```

El backend normaliza los códigos recibidos para evitar diferencias por mayúsculas, espacios o guiones.

## Separación entre cambio de estado y finalización

El cambio de estado operativo usa endpoint de estado. La finalización con resultado final usa endpoint específico con acta.

Criterio:

- estados finales no se aplican como simple cambio de estado;
- la finalización exige acta;
- el acta se guarda antes de dejar la conciliación finalizada;
- la fecha de finalización se registra en la entidad.

## Reunión vigente

La reunión vigente se representa en:

```text
ReunionConciliacion
```

Campos principales:

- conciliación;
- fecha de reunión;
- sede;
- observaciones;
- fecha de creación;
- fecha de actualización.

La reunión vigente permite que el flujo de conciliación tenga una fecha operativa clara sin depender del campo heredado `fecha_conciliacion`.

## Historial de reunión

La programación y reprogramación se registran en:

```text
ReunionConciliacionHistorial
```

Esto permite conservar:

- evento realizado;
- fecha anterior y nueva;
- sede anterior y nueva;
- observaciones anteriores y nuevas;
- usuario que realizó el cambio;
- fecha del evento.

## Notificaciones de reunión

Las notificaciones se representan en:

```text
ReunionConciliacionNotificacion
```

El flujo contempla:

- notificaciones inmediatas por programación;
- notificaciones inmediatas por reprogramación;
- recordatorios;
- alertas administrativas cuando no se encuentran destinatarios con correo o cuando ocurre error.

## Cancelación de notificaciones pendientes

Al finalizar o desactivar conciliación, el backend cancela notificaciones pendientes para evitar recordatorios de trámites que ya no deben continuar operativamente.

La cancelación se aplica a notificaciones no enviadas y activas, conservando historial de las enviadas.

## Relación con cierre de consulta

El cierre de consulta se bloquea si existen conciliaciones activas pendientes.

Estados pendientes:

```text
EN_ESPERA
ESPERANDO_REUNION
REUNION_PROGRAMADA
```

Estados finales:

```text
COMPLETO_CONCILIADO
COMPLETO_NO_CONCILIADO
```

## Impacto en frontend

El frontend debe:

- enviar solicitud PDF al crear;
- usar el endpoint de finalización con acta;
- programar o reprogramar reunión desde el flujo correspondiente;
- mostrar estado visible y código técnico;
- manejar errores del backend como validaciones de negocio.

## Criterios de mantenimiento

Si cambia conciliación, revisar:

- controllers de conciliación;
- services de conciliación;
- validators;
- estado_conciliacion;
- reuniones;
- notificaciones;
- documentación API;
- reglas de negocio;
- frontend de conciliaciones y reuniones.
