# Frontend

## Visión general

El frontend es una aplicación **Next.js 16** basada en el nuevo app router y construida con **React 19**. Usa un layout global con soporte de tema claro/oscuro mediante `next-themes` y componentes de interfaz de usuario generados por `shadcn`, `radix-ui`, `tailwindcss` y `sonner`.

## Estructura principal

- `frontend/src/app/page.js`
  - Página de inicio de sesión.
  - Contiene el formulario `LoginForm` y el selector de tema.
- `frontend/src/app/inicio/page.js`
  - Dashboard de bienvenida con la barra lateral.
  - Parece ser la primera pantalla después del login.
- `frontend/src/app/nuevaconsulta/page.js`
  - Página para registrar nueva información relacionada con el sistema.
  - Muestra formularios: `PersonaForm`, `AreaForm`, `TemaForm`, `TipoForm`.
- `frontend/src/app/consultasjuridicas/page.js`
  - Página de consultas jurídicas.
  - Presenta el componente `ConsultasJuridicasForm` con búsqueda local.
- `frontend/src/components/auth/LoginForm.jsx`
  - Formulario de login que guarda el correo en `localStorage` y redirige a `/inicio`.
- `frontend/src/hooks/useApiForm.js`
  - Hook reutilizable para enviar datos a la API y mostrar toasts de éxito/error.
- `frontend/src/components/forms/` 
  - Formularios para los distintos recursos del backend: `Persona`, `Área`, `Tema`, `Tipo`.

## Rutas y navegación

Rutas implementadas actualmente:

- `/` -> login
- `/inicio` -> dashboard principal
- `/nuevaconsulta` -> formulario de creación de datos
- `/consultasjuridicas` -> búsqueda/listado de consultas

La aplicación usa una barra lateral (`AppSidebar`) en varias páginas de contenido. Los menus son generados dinámicamente en cada página mediante arrays de secciones.

## Integración con backend

Los formularios usan `useApiForm` para enviar peticiones `POST` JSON al backend.

Endpoints actuales usados en el código:

- `http://localhost:8080/api/personas`
- `http://localhost:8080/api/areas`
- `http://localhost:8080/api/temas`
- `http://localhost:8080/api/tipos`

### Comportamiento de `useApiForm`

- Envía `POST` con `Content-Type: application/json`.
- Si la respuesta es exitosa, muestra un toast de éxito.
- Si hay un error de backend, muestra un toast con la respuesta.
- Si falla la conexión, muestra un toast de error de conexión.

## Formularios clave

### PersonaForm

- Usa `react-hook-form`.
- Incluye campos de datos personales, documento, género, orientación sexual, escolaridad, ubicación y servicios públicos.
- Tiene validación básica para campos obligatorios.
- Envía los datos a `/api/personas`.

### AreaForm

- Registra áreas con un solo campo `nombre`.
- Envía datos a `/api/areas`.

### TemaForm

- Registra temas asociados a un área.
- Carga dinámicamente las áreas desde `/api/areas`.
- Envía datos a `/api/temas`.

### TipoForm

- Registra tipos relacionados con un tema.
- Carga dinámicamente los temas desde `/api/temas`.
- Envía datos a `/api/tipos`.

### ConsultasJuridicasForm

- Actualmente usa datos de ejemplo en memoria (`sampleConsultas`).
- Implementa búsqueda local sobre ID, consulta y fecha.
- No hay todavía integración real con la API para consultas jurídicas.

## Diseño y UX

- Soporte de modo oscuro con `next-themes`.
- Uso de componentes `Button`, `Input`, `Select`, `Card`, `Sidebar`, y `Toaster`.
- Baraja estilos con `tailwindcss` y variables CSS personalizadas en `globals.css`.
- La página de login tiene un layout de dos columnas para pantallas grandes.

## Dependencias importantes

- `next` 16.2.3
- `react` 19.2.4
- `react-dom` 19.2.4
- `react-hook-form`
- `next-themes`
- `lucide-react`
- `shadcn`
- `radix-ui`
- `sonner`
- `tailwindcss` 4
- `@biomejs/biome` para lint/format

## Observaciones y recomendaciones

- El login no tiene autenticación real; solo guarda el email en `localStorage` y redirige a `/inicio`. Esto fué pactado como parte del primer sprint, próxima integración de autenticación será desarrollada y se deja de esta manera para conveniencia del desarrollo.
- `ConsultasJuridicasForm` es un prototipo de búsqueda y debe reemplazarse por una consulta a la API.
- Existen archivos como `frontend/src/app/App.jsx`, `frontend/src/app/NuevaConsulta.jsx` y `frontend/src/app/NuevoUsuario.jsx` que parecen ser código legacy o no utilizados por el enrutador actual del app router.
- `frontend/README.md` todavía contiene el texto generado por `create-next-app`; conviene actualizarlo para documentar el setup real.
- Se recomienda externalizar los endpoints de API a variables de entorno (`NEXT_PUBLIC_API_URL`), evitando direcciones hardcodeadas.
- El `metadata` en `frontend/src/app/layout.js` debe ajustarse para reflejar el nombre y la descripción del sistema.

## Ejecución

Desde `frontend`:

```bash
npm install
npm run dev
```

Comandos útiles:

- `npm run dev` -> iniciar servidor de desarrollo
- `npm run build` -> construir la app
- `npm run lint` -> ejecutar biome
- `npm run format` -> formatear código
