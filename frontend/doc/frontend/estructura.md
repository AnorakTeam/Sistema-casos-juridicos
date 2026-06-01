# Estructura del frontend

## Árbol de carpetas

```text
frontend/
├── src/
│   ├── app/                              # Rutas de Next.js (App Router)
│   │   ├── layout.js                     # Layout raíz: tema, fuentes, metadata
│   │   ├── page.js                       # Página raíz: redirige a /inicio o muestra login
│   │   ├── recuperar-password/
│   │   │   └── page.js                   # Formulario de solicitud de recuperación
│   │   ├── restablecer-password/
│   │   │   └── page.js                   # Formulario de restablecimiento con token
│   │   └── (dashboard)/                  # Grupo de rutas protegidas con layout compartido
│   │       ├── layout.js                 # Layout del dashboard: sidebar, header, tema
│   │       ├── inicio/page.js            # Panel de control con estadísticas por rol
│   │       ├── recepcion/page.js         # Registro de personas nuevas
│   │       ├── personas/page.js          # Listado y gestión de personas
│   │       ├── nuevaconsulta/page.js     # Formulario de nueva consulta jurídica
│   │       ├── consultasjuridicas/page.js # Listado de consultas jurídicas
│   │       ├── tareas/page.js            # Seguimientos y tareas
│   │       ├── nuevoproceso/page.js      # Formulario de nuevo proceso judicial
│   │       ├── procesos/page.js          # Listado de procesos judiciales
│   │       ├── conciliaciones/page.js    # Módulo de conciliaciones y reuniones
│   │       ├── estudiantes/page.js       # Gestión de estudiantes
│   │       ├── asesoresymonitores/page.js # Gestión de asesores y monitores
│   │       ├── roles/page.js             # Creación de usuarios del sistema
│   │       ├── admin/page.js             # Catálogos, permisos de roles y auditoría
│   │       ├── estadisticas/page.js      # Estadísticas globales (solo admin)
│   │       └── eliminacion/page.js       # Desactivación y reactivación de registros
│   │
│   ├── components/
│   │   ├── app-sidebar.jsx               # Barra lateral de navegación con usuario y logout
│   │   ├── Calendar.js                   # Componente de calendario de seguimientos
│   │   ├── CalendarModal.jsx             # Modal del calendario accesible desde el header
│   │   ├── theme-provider.jsx            # Proveedor de tema claro/oscuro
│   │   │
│   │   ├── auth/
│   │   │   ├── LoginForm.jsx             # Formulario de inicio de sesión
│   │   │   ├── RecuperarPasswordForm.jsx # Formulario de solicitud de recuperación
│   │   │   └── RestablecerPasswordForm.jsx # Formulario de restablecimiento con token
│   │   │
│   │   ├── navigation/
│   │   │   └── PermissionSidebar.jsx     # Sidebar con ítems filtrados por permisos reales
│   │   │
│   │   ├── forms/
│   │   │   ├── EliminacionForm.jsx       # Desactivación/reactivación de registros
│   │   │   │
│   │   │   ├── AdminUsuarios/
│   │   │   │   ├── AuditLogsTable.jsx    # Tabla de logs de auditoría
│   │   │   │   ├── CambiarRolUsuarioForm.jsx # Cambio de rol de usuario
│   │   │   │   ├── RolePermissionsForm.jsx   # Asignación de permisos a roles
│   │   │   │   └── UsuarioSistemaForm.jsx    # Creación de usuarios del sistema
│   │   │   │
│   │   │   ├── catalogos/
│   │   │   │   ├── AreaForm.jsx          # CRUD de áreas jurídicas
│   │   │   │   ├── TemaForm.jsx          # CRUD de temas por área
│   │   │   │   └── TipoForm.jsx          # CRUD de tipos por tema
│   │   │   │
│   │   │   ├── conciliacion/
│   │   │   │   ├── ConciliacionesForm.jsx      # Listado y gestión de conciliaciones
│   │   │   │   └── ReunionesConciliacionForm.jsx # Programación y reprogramación de reuniones
│   │   │   │
│   │   │   ├── consulta/
│   │   │   │   ├── ConsultasJuridicasForm.jsx  # Listado y gestión de consultas
│   │   │   │   ├── NuevaConsultaForm.jsx       # Creación de consulta jurídica
│   │   │   │   └── SeguimientosForm.jsx        # Seguimientos y respuestas
│   │   │   │
│   │   │   ├── estadisticas/
│   │   │   │   └── EstadisticasForm.jsx  # Dashboard de estadísticas del semestre
│   │   │   │
│   │   │   ├── inicio/
│   │   │   │   └── InicioForm.jsx        # Panel de control con estadísticas y listas operativas
│   │   │   │
│   │   │   ├── parts/                    # Componentes reutilizables de formulario
│   │   │   │   ├── ArchivoForm.jsx       # Carga de archivo individual
│   │   │   │   ├── ArchivosConsultaForm.jsx # Carga de documentos adicionales de consulta
│   │   │   │   ├── FormCheckbox.jsx      # Campo checkbox integrado con react-hook-form
│   │   │   │   ├── FormFileUpload.jsx    # Campo de carga de archivos con validación
│   │   │   │   ├── FormInput.jsx         # Campo de texto integrado con react-hook-form
│   │   │   │   ├── FormMultiSelect.jsx   # Selección múltiple integrada
│   │   │   │   ├── FormSelect.jsx        # Campo select integrado con react-hook-form
│   │   │   │   └── PersonaMultiSelect.jsx # Selector múltiple de personas
│   │   │   │
│   │   │   ├── persona/
│   │   │   │   ├── PersonaForm.jsx       # Registro/edición de persona (formulario por pasos)
│   │   │   │   └── PersonasForm.jsx      # Listado de personas con búsqueda y paginación
│   │   │   │
│   │   │   ├── procesos/
│   │   │   │   ├── NuevoProcesosForm.jsx # Creación de proceso judicial
│   │   │   │   └── ProcesosForm.jsx      # Listado de procesos judiciales
│   │   │   │
│   │   │   └── usuarios/
│   │   │       ├── AsesoresYMonitoresForm.jsx  # Gestión de asesores y monitores
│   │   │       ├── ConciliadorForm.jsx         # Registro de conciliadores
│   │   │       ├── EstudiantesForm.jsx         # Gestión de estudiantes
│   │   │       └── ImportarEstudiantesForm.jsx # Cargue masivo de estudiantes desde Excel
│   │   │
│   │   └── ui/                           # Componentes base de shadcn/ui + dos componentes custom
│   │       ├── ConfirmActionDialog.jsx   # Diálogo de confirmación reutilizable (custom)
│   │       ├── Pagination.jsx            # Paginación con selector de tamaño de página (custom)
│   │       └── ...                       # Button, Card, Dialog, Select, Tabs, etc. (shadcn/ui)
│   │
│   ├── hooks/
│   │   ├── useApiForm.js                 # Hook para envíos de formulario al backend
│   │   └── use-mobile.js                 # Detecta si el viewport es móvil
│   │
│   └── lib/
│       ├── api.js                        # Helpers para leer y normalizar respuestas HTTP
│       ├── apiClient.js                  # Cliente HTTP centralizado (wrapper de fetch)
│       ├── authz.js                      # Funciones de autorización (tienePermiso, esAsesor…)
│       ├── config.js                     # URLs del backend desde variables de entorno
│       ├── form-validation.js            # Reglas reutilizables para react-hook-form
│       ├── list-utils.js                 # Ordenamiento y paginación de listas
│       ├── permission.js                 # Constantes de permisos del sistema
│       └── utils.js                      # Helper cn() para combinar clases Tailwind
│
├── public/
│   └── logo.png                          # Logotipo del consultorio jurídico
│
├── .env.example                          # Plantilla de variables de entorno
├── next.config.mjs                       # Configuración de Next.js
├── tailwind.config.js                    # Configuración de Tailwind CSS
└── package.json
```

## Responsabilidades por carpeta

| Carpeta | Responsabilidad |
|---|---|
| `src/app/` | Rutas, páginas y layouts de Next.js. Cada `page.js` importa y renderiza el form correspondiente. No contiene lógica de negocio. |
| `src/app/(dashboard)/` | Rutas protegidas con sidebar y header. El layout verifica visualmente que el usuario esté autenticado. La verificación real se hace en cada form. |
| `src/components/auth/` | Formularios de autenticación: login, recuperación y restablecimiento. |
| `src/components/navigation/` | Sidebar con ítems filtrados dinámicamente por permisos del usuario autenticado. |
| `src/components/forms/` | Un directorio por módulo. Cada form es responsable de verificar sesión, permisos, cargar datos y comunicarse con el backend. |
| `src/components/forms/parts/` | Componentes de formulario reutilizables integrados con react-hook-form. No contienen lógica de negocio. |
| `src/components/ui/` | Componentes base de shadcn/ui (no modificar) más `ConfirmActionDialog` y `Pagination` que son componentes custom del proyecto. |
| `src/hooks/` | Hooks de React compartidos entre formularios. |
| `src/lib/` | Utilidades puras sin estado: configuración, autorización, validación, helpers HTTP. |

## Convenciones de nombres

| Tipo | Convención | Ejemplo |
|---|---|---|
| Páginas | `page.js` en carpeta con nombre de ruta | `app/(dashboard)/consultas/page.js` |
| Layouts | `layout.js` | `app/(dashboard)/layout.js` |
| Formularios | `NombreMóduloForm.jsx` | `NuevaConsultaForm.jsx` |
| Partes de formulario | `FormNombre.jsx` | `FormInput.jsx`, `FormFileUpload.jsx` |
| Hooks | `useNombre.js` | `useApiForm.js` |
| Utilidades | nombre descriptivo en camelCase | `authz.js`, `list-utils.js` |
| Constantes | `MAYUSCULAS_CON_GUION` en archivo `.js` | `PERMISOS.VER_CONSULTAS` |

## Carpetas que no deben contener lógica de negocio

- `src/components/ui/`: solo presentación.
- `src/app/*/page.js`: solo importar y renderizar el form.
- `src/lib/utils.js`: solo el helper `cn()`.
