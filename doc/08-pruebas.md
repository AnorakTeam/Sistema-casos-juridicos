# Pruebas

## 1. Propósito

Este documento describe los casos de prueba definidos para validar el comportamiento del sistema.

Las pruebas verifican el cumplimiento de:

- endpoints de la API,
- reglas de negocio,
- validaciones del sistema,
- integración entre frontend y backend.

---

## 2. Enfoque

Las pruebas se organizan en tres niveles:

- backend (API),
- frontend (interfaz),
- integración (flujo completo).

---

## 3. Criterios de prueba

Cada caso de prueba valida:

- operación ejecutada,
- condición inicial,
- resultado esperado.

Las reglas evaluadas están definidas en:

- `validaciones-y-reglas.md`
- `api.md`

---

## 4. Pruebas de backend

## 4.1 Personas

### Creación

- Crear persona válida.
- Crear persona con solo teléfono.
- Crear persona con solo correo.
- Intentar crear sin contacto.
- Intentar crear con documento duplicado.
- Crear menor de edad con acudiente válido.
- Intentar crear menor sin acudiente.

### Consulta

- Listar personas.
- Consultar por id válido.
- Consultar por id inexistente.

### Actualización

- Actualizar datos válidos.
- Intentar actualizar sin contacto.
- Intentar duplicar documento.
- Intentar actualizar inexistente.

### Eliminación

- Eliminar persona existente.
- Intentar eliminar inexistente.

---

## 4.2 Áreas

### Creación

- Crear área válida.
- Intentar crear duplicada.
- Intentar crear con nombre vacío.

### Consulta

- Listar áreas.
- Consultar por id válido.
- Consultar por id inexistente.

### Actualización

- Actualizar nombre válido.
- Intentar duplicar nombre.
- Intentar actualizar inexistente.

### Eliminación

- Eliminar área sin dependencias.
- Intentar eliminar con temas asociados.

---

## 4.3 Temas

### Creación

- Crear tema válido.
- Crear en diferentes áreas.
- Intentar duplicado en misma área.
- Intentar con área inexistente.

### Consulta

- Listar temas.
- Filtrar por área.
- Consultar por id.

### Actualización

- Actualizar nombre.
- Cambiar de área.
- Intentar duplicado.
- Intentar inexistente.

### Eliminación

- Eliminar válido.
- Intentar eliminar con tipos.

---

## 4.4 Tipos

### Creación

- Crear tipo válido.
- Crear en diferentes temas.
- Intentar duplicado.
- Intentar con tema inexistente.

### Consulta

- Listar tipos.
- Filtrar por tema.
- Consultar por id.

### Actualización

- Actualizar válido.
- Cambiar de tema.
- Intentar duplicado.

### Eliminación

- Eliminar válido.
- Intentar inexistente.

---

(AQUI COLOCAR LAS PRUEBAS A LOS DEMAS CRUDS AL MISMO ESTILO)

---

## 5. Evidencias

Para cada módulo se deben registrar evidencias de:

- operación exitosa,
- error de validación,
- error de negocio,
- error por inexistencia,
- error por dependencia.

---

(AUI HAY UNA IDEA PERO MODIFIQUENLO COMO CONSIDEREN)
## 6. Pruebas de frontend

Se validará:

- comportamiento de formularios,
- validaciones visuales,
- mensajes al usuario,
- navegación y estado de componentes.

---

(SE TIENE QUE ACORDAR ENTRE EL QUE HIZO FRONTEND Y BACKEND)
## 7. Pruebas de integración

Se validará:

- flujo completo frontend → backend,
- persistencia de datos,
- manejo de errores en interfaz,
- consistencia entre lo enviado y lo almacenado.

---

## 8. Alcance

Este documento registra los casos de prueba definidos.  
Las reglas que se validan se documentan en archivos independientes.