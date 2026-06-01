# Decisión técnica - Estrategias de perfiles

## Contexto

El sistema permite que un `UsuarioSistema` esté asociado a distintos tipos de perfil real: asesor, estudiante, monitor, administrativo o conciliador.

El cambio y resolución de perfil requieren lógica diferente según el tipo de perfil. Para evitar servicios con condicionales extensos y para mantener el sistema extensible, se usa el patrón Strategy.

## Decisión

El sistema usa estrategias para tres responsabilidades relacionadas con perfiles:

1. crear o actualizar el perfil destino en un cambio de perfil;
2. desactivar el perfil anterior;
3. resolver el perfil activo del usuario autenticado.

## Estrategia de cambio de perfil

Interfaz:

```text
PerfilCambioHandler
```

Registry:

```text
PerfilCambioHandlerRegistry
```

Handlers concretos:

```text
CambiarAAsesorHandler
CambiarAEstudianteHandler
CambiarAMonitorHandler
CambiarAAdministrativoHandler
CambiarAConciliadorHandler
```

Uso:

- `UsuarioCambioPerfilService` recibe la solicitud de cambio;
- resuelve el handler según el tipo de perfil destino;
- delega la creación o actualización del perfil correspondiente;
- registra historial de cambio.

## Estrategia de estado de perfil anterior

Interfaz:

```text
PerfilEstadoHandler
```

Registry:

```text
PerfilEstadoHandlerRegistry
```

Handlers concretos:

```text
AsesorPerfilEstadoHandler
EstudiantePerfilEstadoHandler
MonitorPerfilEstadoHandler
AdministrativoPerfilEstadoHandler
ConciliadorPerfilEstadoHandler
```

Uso:

- `PerfilEstadoService` no decide con `switch`;
- delega al handler registrado para el tipo de perfil actual;
- los handlers de asesor, estudiante y monitor validan consultas operativas antes de desactivar;
- el cambio de perfil respeta las reglas de integridad operativa.

## Estrategia de resolución de perfil activo

Interfaz:

```text
PerfilUsuarioActivoResolver
```

Registry:

```text
PerfilUsuarioActivoResolverRegistry
```

Resolvers concretos:

```text
AsesorPerfilUsuarioActivoResolver
EstudiantePerfilUsuarioActivoResolver
MonitorPerfilUsuarioActivoResolver
AdministrativoPerfilUsuarioActivoResolver
ConciliadorPerfilUsuarioActivoResolver
```

Uso:

- `PerfilUsuarioResolverService` obtiene `UsuarioSistema.tipoPerfilActual`;
- solicita al registry el resolver correspondiente;
- el resolver consulta el repositorio del perfil específico;
- si el perfil no existe o está inactivo, se lanza regla de negocio.

## Justificación

El patrón Strategy permite:

- cumplir principio abierto/cerrado;
- evitar condicionales por tipo de perfil en servicios orquestadores;
- encapsular reglas específicas por perfil;
- facilitar agregar un nuevo tipo de perfil si el dominio lo requiere;
- mantener pruebas unitarias pequeñas y enfocadas.

## Relación con UsuarioSistema

El usuario del sistema conserva:

```text
tipo_perfil_actual
rol_id
activo
```

El perfil real conserva datos funcionales. Al desactivar o reactivar perfiles desde servicios de comando, el sistema sincroniza el estado del perfil con `UsuarioSistema` mediante:

```text
UsuarioSistemaPerfilEstadoService
```

## Relación con consultas vivas

La estrategia de estado del perfil anterior respeta reglas de negocio:

- asesor con consultas operativas no se desactiva;
- estudiante con consultas operativas no se desactiva;
- monitor con consultas operativas no se desactiva.

Esto mantiene coherencia entre cambio de perfil y responsabilidad operativa.

## Pruebas relacionadas

El código incluye pruebas unitarias para validar:

- delegación de `PerfilEstadoService` hacia handler;
- delegación de `PerfilUsuarioResolverService` hacia resolver;
- registry de handlers de estado;
- registry de resolvers de perfil activo;
- sincronización entre perfil y usuario del sistema.

## Criterios de mantenimiento

Al agregar un nuevo tipo de perfil:

1. agregar enum en `TipoPerfilUsuario`;
2. crear handler de cambio de perfil;
3. crear handler de estado de perfil;
4. crear resolver de perfil activo;
5. registrar repositorio y reglas de acceso;
6. agregar pruebas unitarias;
7. actualizar documentación de backend, API, reglas y decisiones.
