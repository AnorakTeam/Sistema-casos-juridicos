# Modelo de datos

## 1. Propósito

Este documento describe el modelo de datos implementado actualmente en el backend del sistema. Su objetivo es presentar las entidades persistentes, sus atributos principales, las relaciones entre ellas y las decisiones de modelado adoptadas durante el desarrollo.

El modelo actual cubre dos (CAMBIAR CUANDO SE HAGA LO QUE FALTA) grupos principales de información:

- la **gestión de personas**, orientada al registro de datos personales, de contacto, vivienda y caracterización;
- la **parametrización jurídica**, orientada a la clasificación jerárquica por área, tema y tipo.
- (AQUI LOS GRUPOS QUE FALTAN)

## 2. Entidades implementadas

## 2.1 Persona

### Descripción

La entidad `Persona` centraliza la información requerida para registrar una persona dentro del sistema. Su estructura agrupa datos de identificación, contacto, ubicación, condiciones socioeconómicas, datos del acudiente e información relacionada con el acceso al servicio.

### Propósito

Esta entidad funciona como base para la gestión de personas dentro del sistema y evita distribuir la información en múltiples estructuras separadas. Su diseño permite concentrar en una sola tabla la información necesaria para caracterizar a una persona de forma integral.

### Atributos principales

#### Identificación y datos básicos
- `id`
- `tipoUsuario`
- `tipoDocumento`
- `numeroDocumento`
- `fechaExpedicion`
- `ciudadExpedicion`
- `nombres`
- `apellidos`
- `nombreIdentitario`
- `pronombre`
- `sexo`
- `genero`
- `orientacionSexual`
- `fechaNacimiento`

#### Contacto
- `telefono`
- `correo`

#### Información social y caracterización
- `nacionalidad`
- `estadoCivil`
- `escolaridad`
- `grupoEtnico`
- `condicionActual`
- `sabeLeerEscribir`
- `discapacidad`
- `caracterizacionPcd`
- `necesitaAjustePcd`

#### Información de vivienda
- `departamento`
- `municipio`
- `barrio`
- `direccion`
- `comuna`
- `localidad`
- `estrato`
- `tipoVivienda`
- `zona`
- `tenencia`
- `numeroPersonasACargo`
- `ingresosAdicionales`
- `energiaElectrica`
- `acueducto`
- `alcantarillado`

#### Información económica
- `ocupacion`
- `empresa`
- `salario`
- `cargo`
- `direccionEmpresa`
- `telefonoEmpresa`

#### Datos del acudiente
- `nombreCompletoAcudiente`
- `relacionAcudiente`
- `telefonoAcudiente`
- `correoAcudiente`
- `direccionAcudiente`

#### Información del servicio
- `comoSeEntero`
- `relacionConUniversidad`

### Decisiones de modelado

- Se utiliza `numeroDocumento` como dato único para prevenir registros duplicados.
- Se emplean tipos `LocalDate`, `Boolean` e `Integer` de acuerdo con la naturaleza del dato.
- Los campos `telefono` y `correo` no se restringen rígidamente como obligatorios a nivel estructural, debido a que la validación real se resuelve mediante reglas de negocio.
- Los datos del acudiente se consideran condicionales, ya que dependen del contexto de edad de la persona registrada.

---

## 2.2 Area

### Descripción

La entidad `Area` representa el primer nivel de parametrización jurídica del sistema. Su propósito es definir las categorías generales bajo las cuales se organizan los temas y, posteriormente, los tipos. 

### Atributos principales
- `id`
- `nombre`

### Decisiones de modelado

- `nombre` es obligatorio.
- `nombre` es único.
- `nombre` tiene longitud máxima de 50 caracteres. 
- Se incluye una relación con `Tema`.
- La colección de temas no se expone directamente en las respuestas JSON, evitando serialización innecesaria.

---

## 2.3 Tema

### Descripción

La entidad `Tema` representa el segundo nivel de clasificación jurídica del sistema. Cada tema pertenece a un área específica y puede agrupar múltiples tipos.

### Atributos principales
- `id`
- `nombre`
- `area`

### Decisiones de modelado

- `nombre` es obligatorio.
- `nombre` tiene longitud máxima de 80 caracteres. 
- La relación con `Area` se modela mediante una asociación muchos a uno.
- La relación con `Tipo` se modela mediante una asociación uno a muchos. 
- La lista de tipos no se serializa directamente para evitar recursividad en la respuesta JSON.

---

## 2.4 Tipo

### Descripción

La entidad `Tipo` representa el tercer nivel de parametrización jurídica del sistema. Cada tipo se asocia a un tema específico y permite refinar la clasificación de la información jurídica. 

### Atributos principales
- `id`
- `nombre`
- `tema`

### Decisiones de modelado

- `nombre` es obligatorio.
- `nombre` tiene longitud máxima de 80 caracteres.
- La relación con `Tema` se modela mediante una asociación muchos a uno.

---

(AQUI COLOCAR LAS DEMAS ENTIDADES QUE FALTAN CON LOS ITEMS DE:
    2.X Tipo
    ### Descripción 
    ### Atributos principales
    ### Decisiones de modelado
)

## 3. Relaciones entre entidades

El modelo de datos implementado actualmente presenta una relación jerárquica en la parametrización jurídica y una entidad independiente para la gestión de personas.

### 3.1 Relación Area - Tema

- Una **área** puede tener muchos **temas**.
- Un **tema** pertenece a una sola **área**.

Tipo de relación:

- `Area` 1 --- N `Tema`

Esta relación permite que el sistema seleccione primero un área y, a partir de ella, recupere los temas correspondientes.

### 3.2 Relación Tema - Tipo

- Un **tema** puede tener muchos **tipos**.
- Un **tipo** pertenece a un solo **tema**.

Tipo de relación:

- `Tema` 1 --- N `Tipo`

Esta relación soporta el flujo jerárquico del sistema: área → tema → tipo. 

### 3.3 Relación de Persona con otras entidades

En la etapa actual del sistema, `Persona` se mantiene como una entidad autónoma dentro del modelo implementado, sin relación directa persistida con `Area`, `Tema` o `Tipo`. Su propósito es gestionar el registro integral de datos personales y de caracterización. 

(AQUI LO MISMO PERO CON LO QUE UD CREO EN EL CRUD)

## 4. Representación conceptual del modelo

Area
└── Tema
└── Tipo

Persona

(AQUI AGREGUE LA ESTRUCTURA DE LOS DEMAS CRUDS)

## 5. Reglas de modelado

### Identificadores

Todas las entidades usan identificador único.

### Jerarquía

Área → Tema → Tipo

### Integridad

- nombre de área único,
- número de documento único,
- tema requiere área,
- tipo requiere tema.

### Persistencia vs API

Las entidades no se exponen directamente.  
Se usan DTO para desacoplar modelo y API.

---

## 6. Persistencia

- Implementada con Spring Data JPA.
- Mapeo mediante anotaciones JPA.
- Relaciones uno a muchos / muchos a uno.
- Base de datos: PostgreSQL.

> H2 se utilizó solo en pruebas iniciales.

---

## 7. Alcance

Este modelo corresponde a las entidades actuales del backend.  
La arquitectura permite agregar nuevas entidades sin cambios estructurales.