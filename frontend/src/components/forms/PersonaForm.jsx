import React from 'react';
import { useForm, FormProvider } from 'react-hook-form';
import { FormInput } from './parts/FormInput';
import { FormSelect } from './parts/FormSelect';
import { FormCheckbox } from './parts/FormCheckbox';
import { Button } from '@/components/ui/button';
import { Separator } from '@/components/ui/separator';

export function PersonaForm({ onSubmit, initialValues = {} }) {
  const methods = useForm({
    defaultValues: {
      sabeLeerEscribir: true,
      necesitaAjustePcd: false,
      ingresosAdicionales: false,
      energiaElectrica: false,
      acueducto: false,
      alcantarillado: false,
      ...initialValues,
    },
  });

  const API_URL_BASE = ""

  // sí, tuve que googlear esto, segun la página va a salir el 8, lo dejo así mejor
  const MAX_ESTRATO = 7

  // TODO: no sé si hay límite *legal*? buscar y corregir si es así
  const MAX_PERSONAS_A_CARGO = 10

  const tipoDocumentoOptions = [
                { value: 'CC', label: 'Cédula de Ciudadanía' },
                { value: 'TI', label: 'Tarjeta de Identidad' },
                { value: 'CE', label: 'Cédula de Extranjería' },
                { value: 'PA', label: 'Pasaporte' },
              ]
  
  const pronombreOptions = [
                { value: 'el', label: 'Él' },
                { value: 'ella', label: 'Ella' },
                { value: 'elle', label: 'Elle' },
                { value: 'otro', label: 'Otro' },
              ]
  
  const sexoOptions = [
                { value: 'hombre', label: 'Hombre' },
                { value: 'mujer', label: 'Mujer' },
                { value: 'intersexual', label: 'Intersexual' },
              ]

  const tipoUsuarioOptions = [
                { value: 'victima', label: 'Víctima' },
                { value: 'usuario_general', label: 'Usuario General' },
              ]

  const generoOptions = [
                { value: 'masculino', label: 'Masculino' },
                { value: 'femenino', label: 'Femenino' },
                { value: 'no_binario', label: 'No Binario' },
                { value: 'transgenero', label: 'Transgénero' },
                { value: 'otro', label: 'Otro' },
              ]
  
  const orientacionSexualOptions = [
                { value: 'heterosexual', label: 'Heterosexual' },
                { value: 'homosexual', label: 'Homosexual' },
                { value: 'bisexual', label: 'Bisexual' },
                { value: 'pansexual', label: 'Pansexual' },
                { value: 'asexual', label: 'Asexual' },
                { value: 'otro', label: 'Otro' },
              ]

  const estadoCivilOptions = [
                { value: 'soltero', label: 'Soltero/a' },
                { value: 'casado', label: 'Casado/a' },
                { value: 'union_libre', label: 'Unión Libre' },
                { value: 'divorciado', label: 'Divorciado/a' },
                { value: 'viudo', label: 'Viudo/a' },
              ]

  const escolaridadOptions = [
                { value: 'ninguna', label: 'Ninguna' },
                { value: 'primaria', label: 'Primaria' },
                { value: 'secundaria', label: 'Secundaria' },
                { value: 'tecnico', label: 'Técnico' },
                { value: 'tecnologo', label: 'Tecnólogo' },
                { value: 'universitario', label: 'Universitario' },
                { value: 'postgrado', label: 'Postgrado' },
              ]

  const zonaOptions = [
                { value: 'urbana', label: 'Urbana' },
                { value: 'rural', label: 'Rural' },
              ]

  

  const { handleSubmit } = methods;

  const sendData = (data) => {
    // comunicarse con el backend
  }

  const handleFormSubmit = (data) => {
    if (onSubmit) {
      onSubmit(data);
    } else {
      console.log('=== Form Data ===');
      Object.entries(data).forEach(([key, value]) => {
        const formattedValue = typeof value === 'object' && value !== null
          ? JSON.stringify(value)
          : value;
        console.log(`${key}: ${formattedValue}`);
      });
      console.log('=================');
      // Aqui va la logica de envio al backend por defecto
      // (hay que hablar con el backend para el tema de cómo le mandamos
      // la info, eché un ojo y solo ese dto es de casi 1000 líneas)
      sendData(data)
    }
  }

  return (
    <FormProvider {...methods}>
      <form onSubmit={handleSubmit(handleFormSubmit)} className="space-y-8 p-6 bg-card rounded-xl shadow-sm border border-border">
        <div>
          <h2 className="text-2xl font-bold tracking-tight mb-2">Registro de Persona</h2>
          <p className="text-muted-foreground mb-6">Complete la siguiente información para el sistema de casos jurídicos.</p>
        </div>

        {/* Información Básica */}
        <section className="space-y-4">
          <h3 className="text-lg font-semibold border-b pb-2">Información Básica</h3>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            <FormSelect
              name="tipoUsuario"
              label="Tipo de Usuario"
              options={tipoUsuarioOptions}
              required
            />
            <FormSelect
              name="tipoDocumento"
              label="Tipo de Documento"
              options={tipoDocumentoOptions}
              required
            />
            <FormInput name="numeroDocumento" label="Número de Documento" required />
            <FormInput name="fechaExpedicion" label="Fecha de Expedición" type="date" required />
            <FormInput name="ciudadExpedicion" label="Ciudad de Expedición" required />
            <FormInput name="nombres" label="Nombres" required />
            <FormInput name="apellidos" label="Apellidos" required />
            <FormInput name="nombreIdentitario" label="Nombre Identitario" required />
            <FormSelect
              name="pronombre"
              label="Pronombre"
              options={pronombreOptions}
              required
            />
            <FormSelect
              name="sexo"
              label="Sexo"
              options={sexoOptions}
              required
            />
            <FormSelect
              name="genero"
              label="Género"
              options={generoOptions}
              required
            />
            <FormSelect
              name="orientacionSexual"
              label="Orientación Sexual"
              options={orientacionSexualOptions}
              required
            />
            <FormInput name="fechaNacimiento" label="Fecha de Nacimiento" type="date" required />
            <FormInput name="telefono" label="Teléfono" />
            <FormInput name="correo" label="Correo Electrónico" type="email" />
            <FormInput name="nacionalidad" label="Nacionalidad" required />
            <FormSelect
              name="estadoCivil"
              label="Estado Civil"
              options={estadoCivilOptions}
              required
            />
            <FormSelect
              name="escolaridad"
              label="Escolaridad"
              options={escolaridadOptions}
              required
            />
            <FormInput name="grupoEtnico" label="Grupo Étnico" required />
            <FormInput name="condicionActual" label="Condición Actual" required />
            <FormInput name="discapacidad" label="Discapacidad" required />
            <FormInput name="caracterizacionPcd" label="Caracterización PCD" required />
          </div>

          <div className="flex gap-6 mt-4 p-4 rounded-lg bg-muted/20 dark:bg-muted/30">
            <FormCheckbox name="sabeLeerEscribir" label="¿Sabe leer y escribir?" />
            <FormCheckbox name="necesitaAjustePcd" label="¿Necesita ajuste razonable (PCD)?" />
          </div>
        </section>

        <Separator />

        {/* Información de Vivienda */}
        <section className="space-y-4">
          <h3 className="text-lg font-semibold border-b pb-2">Información de Vivienda</h3>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            <FormInput name="departamento" label="Departamento" required />
            <FormInput name="municipio" label="Municipio" required />
            <FormInput name="barrio" label="Barrio" required />
            <FormInput name="direccion" label="Dirección" required />
            <FormInput name="comuna" label="Comuna" required />
            <FormInput name="localidad" label="Localidad" required />
            <FormInput name="estrato" label="Estrato" type="number" max={MAX_ESTRATO} min={0} required />
            <FormInput name="tipoVivienda" label="Tipo de Vivienda" required />
            <FormSelect
              name="zona"
              label="Zona"
              options={zonaOptions}
              required
            />
            <FormInput name="tenencia" label="Tenencia de la Vivienda" required />
            <FormInput name="numeroPersonasACargo" label="Número de Personas a Cargo" type="number" min={0} max={MAX_PERSONAS_A_CARGO} required />
          </div>

          <div className="flex flex-wrap gap-6 mt-4 p-4 rounded-lg bg-muted/20 dark:bg-muted/30">
            <FormCheckbox name="ingresosAdicionales" label="¿Recibe ingresos adicionales?" />
            <FormCheckbox name="energiaElectrica" label="¿Cuenta con energía eléctrica?" />
            <FormCheckbox name="acueducto" label="¿Cuenta con acueducto?" />
            <FormCheckbox name="alcantarillado" label="¿Cuenta con alcantarillado?" />
          </div>
        </section>

        <Separator />

        {/* Aspectos Económicos */}
        <section className="space-y-4">
          <h3 className="text-lg font-semibold border-b pb-2">Aspectos Económicos</h3>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            <FormInput name="ocupacion" label="Ocupación" required />
            <FormInput name="empresa" label="Empresa donde labora" required />
            <FormInput name="salario" label="Salario" type="number" step={10000} min={0} required />
            <FormInput name="cargo" label="Cargo" required />
            <FormInput name="direccionEmpresa" label="Dirección de la Empresa" required />
            <FormInput name="telefonoEmpresa" label="Teléfono de la Empresa" required />
          </div>
        </section>

        <Separator />

        {/* Datos del Acudiente */}
        <section className="space-y-4">
          <h3 className="text-lg font-semibold border-b pb-2">Datos del Acudiente</h3>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            <FormInput name="nombreCompletoAcudiente" label="Nombre Completo" />
            <FormInput name="relacionAcudiente" label="Relación / Parentesco" />
            <FormInput name="telefonoAcudiente" label="Teléfono" />
            <FormInput name="correoAcudiente" label="Correo Electrónico" type="email" />
            <FormInput name="direccionAcudiente" label="Dirección" />
          </div>
        </section>

        <Separator />

        {/* Información del Servicio */}
        <section className="space-y-4">
          <h3 className="text-lg font-semibold border-b pb-2">Información del Servicio</h3>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <FormInput name="comoSeEntero" label="¿Cómo se enteró de nuestros servicios?" required />
            <FormInput name="relacionConUniversidad" label="Relación con la Universidad" required />
          </div>
        </section>

        <div className="flex justify-end gap-4 pt-4">
          <Button type="button" variant="outline" onClick={() => methods.reset()}>
            Limpiar Formulario
          </Button>
          <Button type="submit">
            Guardar Persona
          </Button>
        </div>
      </form>
    </FormProvider>
  );
}
