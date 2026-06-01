# Backend - Módulo de procesos

## Propósito del módulo

El módulo de procesos administra la información procesal asociada a una consulta jurídica. Su función es registrar el trámite formal que surge a partir de una consulta y mantener separado el ciclo de vida del proceso frente al ciclo de vida de la consulta. En el código fuente actual, el proceso se modela como una entidad operativa vinculada a una consulta, a un departamento, a un órgano de control y, de manera opcional, a una especialidad.

La implementación vigente permite que un proceso exista inicialmente en estado `PENDIENTE` sin número de radicado. Esta decisión refleja que en la operación real puede existir una gestión procesal preliminar antes de contar con radicado formal. Cuando el proceso pasa a un estado final, el backend exige el número de radicado y valida su formato.

## Fuentes de código revisadas

La documentación de este módulo se basa en las siguientes clases y paquetes del backend actual:

| Tipo | Archivos principales |
|---|---|
| Controller | `ProcesoController` |
| Servicio fachada | `ProcesoService` |
| Escritura | `ProcesoCommandService` |
| Lectura | `ProcesoQueryService` |
| Validación | `ProcesoValidator` |
| Mapeo | `ProcesoMapper` |
| Acceso y alcance | `ProcesoAccessService` |
| DTO | `ProcesoDTO` |
| Entidades | `Proceso`, `EstadoProceso`, `OrganoControl`, `Especialidad` |
| Repositorios | `ProcesoRepository`, `OrganoControlRepository`, `EspecialidadRepository`, `ConsultaRepository`, `DepartamentoRepository` |
| Catálogos relacionados | `OrganoControlController`, `EspecialidadController` |
| Pruebas | `ProcesoValidatorTest` |

## Ubicación dentro de la arquitectura

El módulo sigue el patrón de capas usado por el backend:

1. `ProcesoController` expone el contrato HTTP bajo `/api/procesos`.
2. `ProcesoService` centraliza el acceso público al caso de uso.
3. `ProcesoCommandService` maneja creación, actualización, cambio de estado y eliminación lógica.
4. `ProcesoQueryService` maneja consultas de lectura.
5. `ProcesoValidator` concentra reglas de negocio del módulo.
6. `ProcesoAccessService` valida permisos y alcance del usuario autenticado.
7. `ProcesoMapper` convierte entre entidad y DTO.
8. `ProcesoRepository` resuelve persistencia y consultas derivadas.

La separación permite mantener las reglas funcionales fuera del controller y evita que la capa HTTP contenga lógica de negocio.

## Entidad principal

La entidad `Proceso` representa un registro procesal asociado a una consulta. De acuerdo con el modelo actual, contiene principalmente:

| Campo | Descripción |
|---|---|
| `id` | Identificador interno del proceso. |
| `numeroRadicado` | Número de radicado. Puede ser nulo mientras el estado sea `PENDIENTE`. |
| `departamento` | Departamento asociado al proceso. |
| `consulta` | Consulta jurídica a la cual pertenece. |
| `organoControl` | Órgano de control relacionado con el trámite. |
| `especialidad` | Especialidad del órgano de control, si aplica. |
| `estado` | Estado funcional del proceso. |
| `activo` | Marca de eliminación lógica. |

La relación con `Consulta` es esencial porque el proceso no tiene alcance independiente: su visibilidad y capacidad de operación dependen de la consulta asociada.

## DTO de proceso

El contrato de entrada y salida usa `ProcesoDTO`:

| Campo | Tipo | Regla vigente |
|---|---|---|
| `id` | `Long` | No debe enviarse en creación. |
| `numeroRadicado` | `String` | Opcional en `PENDIENTE`; obligatorio en estados finales. |
| `departamentoId` | `Long` | Obligatorio. |
| `consultaId` | `Long` | Obligatorio. |
| `especialidadId` | `Long` | Opcional. |
| `organoControlId` | `Long` | Opcional, pero requerido si se informa especialidad. |
| `estado` | `EstadoProceso` | Controlado por endpoint específico para cambio de estado. |
| `activo` | `Boolean` | Controlado por endpoint específico para activación/desactivación. |

## Estados funcionales

El enum `EstadoProceso` define los estados vigentes:

| Estado | Tipo | Descripción funcional |
|---|---|---|
| `PENDIENTE` | Inicial / operativo | Estado por defecto al crear un proceso. Permite no tener radicado. |
| `SENTENCIA_FAVORABLE` | Final | Resultado favorable. Exige radicado. |
| `SENTENCIA_DESFAVORABLE` | Final | Resultado desfavorable. Exige radicado. |
| `DESISTIMIENTO` | Final | El trámite termina por desistimiento. Exige radicado. |
| `RECHAZO` | Final | El trámite termina por rechazo. Exige radicado. |
| `PRESCRIPCION` | Final | El trámite termina por prescripción. Exige radicado. |

La clase `EstadoProceso` incluye el método `esFinal()`, que considera final todo estado distinto de `PENDIENTE`.

## Regla de radicado

La regla actual se encuentra en `ProcesoValidator.normalizarNumeroRadicadoParaEstado`:

- Si el proceso está en `PENDIENTE`, el número de radicado puede ser nulo.
- Si el estado es final, el número de radicado es obligatorio.
- Si se informa radicado, se normaliza como texto.
- El radicado debe tener exactamente 23 caracteres.
- El radicado informado debe ser único.

Esta regla se aplica tanto al crear como al actualizar datos y al cambiar estado del proceso.

## Creación de proceso

El flujo de creación en `ProcesoCommandService.crear` es:

1. Validar que el DTO sea válido para creación.
2. Normalizar el radicado considerando que el proceso nuevo inicia como `PENDIENTE`.
3. Validar permiso y alcance mediante `ProcesoAccessService.validarPuedeCrearProceso`.
4. Validar unicidad del radicado si se informó.
5. Preparar datos con consulta, departamento, órgano de control y especialidad.
6. Crear entidad `Proceso`.
7. Asignar estado `PENDIENTE`.
8. Asignar `activo=true`.
9. Persistir y retornar DTO.

El estado inicial no depende de un valor enviado por el cliente. El backend conserva el control del ciclo funcional.

## Actualización de proceso

La actualización permite modificar datos generales del proceso, pero no permite cambiar la consulta asociada. La consulta define el alcance operativo del proceso y por eso se valida con `validarNoCambieConsulta`.

El flujo general es:

1. Validar permiso y alcance para editar.
2. Buscar proceso activo por id.
3. Validar que la consulta asociada permita operación operativa.
4. Validar que el DTO no cambie el id.
5. Validar que no cambie la consulta.
6. Normalizar radicado según el estado actual del proceso.
7. Validar unicidad de radicado si cambió.
8. Validar coherencia entre especialidad y órgano de control.
9. Verificar que existan cambios reales.
10. Guardar datos.

## Cambio de estado funcional

El estado funcional cambia mediante:

`PATCH /api/procesos/{id}/estado?estado=...`

El flujo aplica:

1. Permiso `GESTIONAR_PROCESOS`.
2. Proceso activo existente.
3. Consulta asociada en estado operativo.
4. Estado nuevo obligatorio y distinto al actual.
5. Radicado validado según estado nuevo.
6. Guardado del nuevo estado.

Si se intenta pasar a un estado final sin radicado, el backend rechaza la operación.

## Activación y desactivación lógica

La marca `activo` se maneja por separado del estado funcional:

`PATCH /api/procesos/{id}/activo?activo=false`

La eliminación por `DELETE /api/procesos/{id}` funciona como desactivación lógica. Esto evita pérdida de trazabilidad del proceso y conserva integridad histórica con la consulta.

## Órganos de control y especialidades

El módulo de procesos se apoya en catálogos específicos:

- `OrganoControl`: entidad que representa el órgano o instancia competente.
- `Especialidad`: entidad relacionada con un órgano de control.

La validación `validarEspecialidadPerteneceAlOrgano` garantiza que, cuando se informe una especialidad, esta pertenezca al órgano seleccionado. Si se envía especialidad sin órgano de control, el backend rechaza la operación.

## Alcance y permisos

El controller protege los endpoints con permisos:

| Operación | Permisos |
|---|---|
| Listar y obtener | `VER_PROCESOS` o `GESTIONAR_PROCESOS` |
| Crear | `GESTIONAR_PROCESOS` |
| Actualizar | `GESTIONAR_PROCESOS` |
| Cambiar estado funcional | `GESTIONAR_PROCESOS` |
| Cambiar activo | `GESTIONAR_PROCESOS` |
| Eliminar lógicamente | `GESTIONAR_PROCESOS` |

El alcance operativo se valida desde `ProcesoAccessService` y se deriva de la consulta asociada. Esto mantiene coherencia entre permisos de proceso y permisos de consulta.

## Relación con cierre de consulta

El proceso participa en las reglas de cierre de consulta. `ConsultaEstadoService` consulta si existen procesos activos en estado `PENDIENTE` para bloquear el cierre de una consulta. Esto mantiene coherencia entre el estado de la consulta y sus procesos asociados.

## Relación con estadísticas

`ProcesoRepository` incluye métodos de agregación por estado y por área. El módulo de estadísticas utiliza esta información para consolidar resultados procesales en reportes por semestre, rango y perfil.

## Pruebas unitarias relacionadas

`ProcesoValidatorTest` cubre reglas esenciales:

- radicado nulo permitido en `PENDIENTE`;
- texto vacío tratado como radicado nulo en `PENDIENTE`;
- rechazo de estado final sin radicado;
- rechazo de longitud inválida;
- aceptación de radicado válido.

Estas pruebas respaldan la regla principal del módulo.

## Resumen funcional

El módulo de procesos implementa un flujo seguro y trazable para registrar procesos judiciales o administrativos derivados de consultas jurídicas. La regla central consiste en permitir gestión preliminar sin radicado mientras el proceso esté pendiente y exigir radicado cuando el trámite obtiene un resultado final. La separación entre `estado` y `activo` permite conservar historial sin borrar información operativa.
