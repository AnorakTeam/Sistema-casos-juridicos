import React from 'react';
import { useForm, FormProvider } from 'react-hook-form';
import { FormInput } from './parts/FormInput';
import { FormSelect } from './parts/FormSelect';
import { Button } from '@/components/ui/button';

export function TipoForm({ onSubmit, initialValues = {} }) {
  const methods = useForm();

  const { handleSubmit } = methods;

  // Claramente, cambiar esto por un useState y useEffect para hacer fetch a la API
  const temas = [
    {value: 'temaid1', label: 'tema numero 1'},
    {value: 'temaid2', label: 'tema numero 2'},
    {value: 'temaid3', label: 'tema numero 3'},
  ]

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
      <form onSubmit={handleSubmit(handleFormSubmit)} className="space-y-8 p-6 bg-white rounded-xl shadow-sm border border-gray-100">
        <div>
          <h2 className="text-2xl font-bold tracking-tight mb-2">Registro de Tipo</h2>
          <p className="text-gray-500 mb-6">Complete la siguiente información:</p>
        </div>

        {/* Información Básica */}
        <FormInput name="nombre" label="Nombre del tipo" required />
        {/* TODO: Hay que hacer una tabla/set que deje agregar y eliminar opciones. Lo mínimo es que 
        el usuario elija un área, y que pueda agregar más y más. Sería bueno que se cambie el pool de opciones
        a medida que el usuario elige, por ejemplo, hay 3 areas, usuario elige area 1, entonces se agrega a la lista
        de agregados area 1 y ya no aparece como opción. */}
        <FormSelect
                      name="temas"
                      label="Tema a relacionar"
                      options={temas}
                      required
                    />
        <Button type="submit">
          Guardar tema
        </Button>
      </form>
    </FormProvider>
  );
}
