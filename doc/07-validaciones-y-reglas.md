# Validaciones y reglas

## 1. Propósito

Este documento describe las validaciones y reglas del sistema.  
Define las condiciones que deben cumplir los datos y cómo deben comportarse las funcionalidades tanto en backend como en frontend.

---

## 2. Criterios generales

Las restricciones del sistema se dividen en dos niveles:

- **Validaciones de entrada**: formato, obligatoriedad, longitud y tipo de dato.
- **Reglas de negocio**: lógica funcional, dependencias entre datos y restricciones del sistema.

El backend garantiza el cumplimiento final.  
El frontend debe anticiparlas para mejorar la experiencia del usuario.

---

## 3. Validaciones estructurales

Se aplican antes de ejecutar la lógica del sistema.

Incluyen:

- obligatoriedad de campos,
- control de longitud máxima,
- validación de formato (ej. correo),
- valores numéricos no negativos.

---

## 4. Reglas por módulo

## 4.1 Área

- El nombre es obligatorio.
- Máximo 50 caracteres.
- No se permiten nombres duplicados (sin importar mayúsculas/minúsculas).
- No se permite actualizar con el mismo nombre.
- No se puede eliminar si tiene temas asociados.

---

## 4.2 Tema

- El nombre es obligatorio.
- Máximo 80 caracteres.
- El área es obligatoria.
- Debe existir el área asociada.
- No se permiten temas duplicados dentro de una misma área.
- No se permite actualizar si no hay cambios reales.
- No se puede eliminar si tiene tipos asociados.

---

## 4.3 Tipo

- El nombre es obligatorio.
- Máximo 80 caracteres.
- El tema es obligatorio.
- Debe existir el tema asociado.
- No se permiten tipos duplicados dentro del mismo tema.
- No se permite actualizar si no hay cambios reales.

---

## 4.4 Persona

- El número de documento es obligatorio.
- No se permiten documentos duplicados.
- Debe existir al menos un medio de contacto: teléfono o correo.
- Si es menor de edad, se deben validar datos del acudiente.
- Los valores vacíos o "No informa" no siempre se consideran datos válidos.

---

(REGLAS POR CAPA ENTIDAD QUE SE MANEJE, COLOCAR DE LAS QUE FALTAN)

---

## 5. Reglas específicas de persona

### Contacto mínimo

Debe existir al menos uno:

- teléfono,
- correo.

---

### Menores de edad

Si la persona es menor de 18 años:

- el nombre del acudiente es obligatorio,
- la relación del acudiente es obligatoria,
- debe existir teléfono o correo del acudiente.

---

### Normalización de datos

Antes de guardar:

- se eliminan espacios innecesarios,
- valores vacíos se consideran no informados,
- "No informa" se maneja como valor especial según contexto.

---

(AQUI SE COLOCAN MAS REGLAS QUE VAYAN SURGIENDO ...)

---

(AQUI HAY IDEAS DE QUE COLOCAR, PERO ADAPTENLO COMO CONSIDEREN)
## 6. Aplicación en frontend

El frontend debe reflejar estas reglas mediante:

- validación de formularios,
- control de campos obligatorios,
- restricciones de longitud,
- validación de formato,
- bloqueo de envío cuando falten datos mínimos.

---

## 7. Formularios

### Área

- exigir nombre,
- limitar longitud,
- mostrar error si el nombre ya existe.

### Tema

- exigir nombre y área,
- depender de selección de área,
- mostrar errores de duplicidad.

### Tipo

- exigir nombre y tema,
- depender de selección de tema,
- respetar jerarquía área → tema → tipo.

### Persona

- exigir datos básicos,
- validar contacto mínimo,
- activar sección de acudiente si es menor de edad,
- validar datos del acudiente solo en ese caso,
- controlar formatos y valores numéricos.

---

## 8. Mensajes al usuario

El frontend debe mostrar mensajes claros basados en respuestas del backend, especialmente en casos de:

- duplicados,
- datos faltantes,
- errores de validación,
- restricciones de eliminación.

---

## 9. Reglas compartidas

Estas reglas aplican a todo el sistema:

- evitar duplicados,
- garantizar relaciones válidas,
- exigir datos mínimos,
- impedir operaciones inconsistentes,
- mantener coherencia entre frontend y backend.

---

## 10. Alcance

Este documento describe las validaciones y reglas actuales del sistema.  
Debe actualizarse cuando cambie la lógica funcional.