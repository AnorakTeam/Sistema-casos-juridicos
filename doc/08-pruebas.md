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

Las pruebas de frontend se ejecutaron sobre los formularios actualmente implementados en la interfaz. En esta etapa, la validación se concentró en comportamiento visual, obligatoriedad de campos, mensajes al usuario y estabilidad del formulario tras respuestas exitosas o fallidas del backend.

## 6.1 Persona

### Renderizado

- Se verificó que el formulario muestra los campos básicos de identificación.
- Se verificó que el formulario muestra los campos de contacto.
- Se verificó que el formulario muestra los campos de vivienda.
- Se verificó que el formulario muestra los campos económicos.
- Se verificó que el formulario muestra los campos del acudiente.
- Se verificó que los campos de fecha pueden diligenciarse en formato correcto.
- Se verificó que los campos booleanos se presentan mediante controles adecuados.

### Validaciones visibles

- Se verificó que el formulario advierte cuando faltan nombres.
- Se verificó que el formulario advierte cuando faltan apellidos.
- Se verificó que el formulario advierte cuando falta número de documento.
- Se verificó que el formulario advierte cuando faltan fechas obligatorias.
- Se identificó como hallazgo que el frontend no valida el formato básico del correo cuando se diligencia.
- Se identificó como hallazgo que el frontend no valida valores negativos en campos numéricos; esta restricción se cumple únicamente desde backend.
- Se identificó como hallazgo que el frontend no informa de forma preventiva que debe existir al menos teléfono o correo.
- Se identificó que no se implementó un control específico para impedir el uso de `"No informa"` en el campo correo desde la interfaz.

### Comportamiento posterior al envío

- Se verificó que el frontend muestra confirmación cuando el backend responde exitosamente.
- Se identificó como hallazgo que el formulario no se limpia ni reinicia automáticamente después del registro exitoso, aunque existe un botón manual para limpiar campos.

### Comportamiento en menores de edad

- Se verificó que el formulario permite diligenciar los datos del acudiente.
- Se identificó como hallazgo que la sección del acudiente permanece visible en todo momento, cuando debería mostrarse o activarse solo cuando la fecha de nacimiento corresponda a una persona menor de edad.

## 6.2 Área

### Renderizado

- Se verificó que el formulario de área muestra el campo `nombre`.
- Se verificó que el usuario puede escribir correctamente en el campo. 

### Validaciones visibles

- Se verificó que el formulario no permite enviar el campo vacío o muestra error al intentar el envío.
- Se identificó como hallazgo que la obligatoriedad del campo no se comunica visualmente antes del envío.
- Se verificó que el frontend restringe entradas inválidas por longitud.
- Se identificó como hallazgo que el frontend no procesa el objeto `detalles` devuelto por el backend, por lo que los mensajes de error son genéricos y no muestran el detalle específico.

### Estado del formulario

- Se identificó como hallazgo que, después de un error de validación, el formulario puede permanecer en un estado incorrecto y seguir enviando JSON vacíos hasta recargar la página.
- Se identificó como hallazgo que, después de guardar correctamente, el formulario no limpia ni actualiza automáticamente el campo.

## 6.3 Tema

### Renderizado

- Se verificó que el formulario muestra el campo `nombre`.
- Se verificó que el formulario muestra el selector de área.
- Se verificó que el selector de área carga datos reales desde backend. 

### Validaciones visibles

- Se verificó que el frontend impide el envío cuando el nombre está vacío.
- Se identificó como hallazgo que no existe una indicación visual clara de obligatoriedad del campo.
- Se identificó como hallazgo que el frontend no valida la selección del área antes del envío; la restricción queda delegada completamente al backend.
- Se identificó como hallazgo que los errores mostrados son genéricos porque no se procesa el objeto `detalles` de la respuesta. 

### Comportamiento posterior al envío

- Se verificó que el formulario puede registrar temas válidos.
- Se identificó como hallazgo que, en algunos casos, el frontend muestra primero un error de conexión con backend y luego un mensaje de éxito, aunque el registro sí queda guardado. 

## 6.4 Tipo

### Renderizado

- Se verificó que el formulario muestra el campo `nombre`.
- Se verificó que el formulario muestra el selector de tema.
- Se verificó que el selector de tema carga datos reales desde backend.

### Validaciones visibles

- Se verificó que el frontend impide el envío cuando el nombre está vacío.
- Se identificó como hallazgo que no existe una indicación visual clara de obligatoriedad.
- Se identificó como hallazgo que el frontend no valida la selección del tema antes del envío; la restricción efectiva la aplica el backend.
- Se identificó como hallazgo que, cuando el backend devuelve errores, el formulario puede volverse inestable o empezar a enviar JSON vacíos hasta que la página se recarga.

### Manejo de errores

- Se verificó que el frontend muestra mensajes cuando el backend devuelve error.
- Se identificó como hallazgo que los mensajes son genéricos y no reflejan el detalle específico del error.
- Se identificó como hallazgo que, tras ciertos errores, la interfaz puede quedar bloqueada o inestable. 

---

## 7. Pruebas de integración

Las pruebas de integración se ejecutaron sobre la comunicación entre formularios del frontend y endpoints del backend, verificando estructura del request, respuestas devueltas, persistencia de datos y manejo de errores. En los ítems sin observaciones adicionales se consideró cumplimiento satisfactorio; los casos con anotaciones se interpretaron como hallazgos o cumplimiento parcial.
## 7.1 Integración de Persona

### Registro de persona mayor de edad

- Se verificó que el frontend envía correctamente solicitudes a `/api/personas`.
- Se verificó que el request contiene los nombres correctos de los campos.
- Se verificó que las fechas se envían en formato compatible con `LocalDate`.
- Se verificó que una persona válida mayor de edad con teléfono y correo se registra correctamente.
- Se verificó que una persona válida mayor de edad solo con teléfono se registra correctamente.
- Se verificó que una persona válida mayor de edad solo con correo se registra correctamente.
- Se verificó que los registros exitosos quedan persistidos en backend.

### Registro de persona menor de edad

- Se verificó que una persona menor de edad con acudiente válido se registra correctamente.
- Se verificó que el backend rechaza correctamente los casos de menor sin nombre del acudiente.
- Se verificó que el backend rechaza correctamente los casos de menor sin relación del acudiente.
- Se verificó que el backend rechaza correctamente los casos de menor sin teléfono ni correo del acudiente.

### Manejo de errores

- Se verificó que el backend rechaza la creación de una persona sin teléfono y sin correo.
- Se verificó que el backend rechaza la creación con número de documento duplicado.
- Se verificó que el frontend muestra los mensajes devueltos por backend sin romper la interfaz.
- Se identificó como hallazgo que el frontend no anticipa varias de estas validaciones, por lo que la experiencia depende en gran parte de la respuesta del backend.

## 7.2 Integración de Área

### Registro y persistencia

- Se verificó que el frontend ejecuta solicitudes `POST /api/areas`.
- Se verificó que el body enviado contiene el JSON esperado.
- Se verificó que las áreas creadas pueden confirmarse posteriormente en backend mediante consultas o revisión en H2. 

### Manejo de errores

- Se verificó que el backend rechaza duplicados y datos inválidos.
- Se verificó que el frontend no se rompe cuando el backend responde error por validación.
- Se identificó como hallazgo que el frontend no procesa el objeto `detalles`, por lo que no muestra mensajes específicos.
- Se identificó como hallazgo que, tras ciertos errores, el formulario puede quedar enviando JSON vacíos hasta recargar la página.

### Estado posterior al registro

- Se identificó como hallazgo que el formulario guarda correctamente, pero no limpia ni actualiza el campo después del guardado.

## 7.3 Integración de Tema

### Registro y asociación

- Se verificó que el frontend ejecuta solicitudes `POST /api/temas`.
- Se verificó que el JSON enviado utiliza `areaId`.
- Se verificó que el tema creado queda asociado al área correcta.
- Se verificó la persistencia posterior mediante `GET /api/temas` y `GET /api/temas/area/{areaId}`.

### Manejo de errores

- Se verificó que el backend rechaza temas con área inexistente.
- Se verificó que el backend rechaza duplicados en una misma área.
- Se identificó como hallazgo que el frontend no valida previamente la selección del área.
- Se identificó como hallazgo que los errores mostrados son demasiado genéricos.
- Se identificó como hallazgo que, aunque el registro puede guardarse con éxito, el frontend en ocasiones muestra primero un mensaje de error de conexión antes de confirmar la operación.

## 7.4 Integración de Tipo

### Registro y asociación

- Se verificó que el frontend ejecuta solicitudes `POST /api/tipos`.
- Se verificó que el JSON enviado utiliza `temaId`.
- Se verificó que el tipo creado queda asociado al tema correcto.
- Se verificó la persistencia posterior mediante `GET /api/tipos` y `GET /api/tipos/tema/{temaId}`.

### Manejo de errores

- Se verificó que el backend rechaza tipos con tema inexistente.
- Se verificó que el backend rechaza duplicados en un mismo tema.
- Se identificó como hallazgo que el frontend no valida previamente la selección del tema.
- Se identificó como hallazgo que, cuando el backend responde con error, el frontend puede quedar inestable o bloquearse.
- Se identificó como hallazgo que después de algunos errores el formulario empieza a enviar JSON vacíos hasta recargar la página.

## 7.5 Flujo encadenado Área → Tema → Tipo

- Se verificó que el formulario de tema consume áreas reales obtenidas desde backend.
- Se verificó que el formulario de tipo consume temas reales obtenidos desde backend.
- Se verificó parcialmente la secuencia área → tema → tipo desde la consistencia de datos.
- No se validó un flujo completo de navegación entre formularios desde la interfaz, debido a que el frontend actual no implementa un recorrido integrado entre módulos ni persistencia permanente en la base de datos del entorno de pruebas. 

## 7.6 Hallazgos transversales de integración

- El backend cumple adecuadamente la validación y persistencia de los datos enviados.
- El frontend presenta debilidades en validación previa de campos obligatorios y restricciones contextuales.
- El frontend no procesa el objeto `detalles` de las respuestas de error, por lo que los mensajes mostrados son genéricos.
- Después de ciertos errores, algunos formularios quedan en estados inestables y requieren recarga manual para volver a operar correctamente. 

---

## 8. Alcance

Este documento registra los casos de prueba definidos.  
Las reglas que se validan se documentan en archivos independientes.