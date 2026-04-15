import React from 'react';
import { useForm, FormProvider } from 'react-hook-form';
import { FormInput } from './parts/FormInput';
import { Button } from '@/components/ui/button';

export function AreaForm({ onSubmit, initialValues = {} }) {
  const methods = useForm();

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
      <form onSubmit={handleSubmit(handleFormSubmit)} className="space-y-8 p-6 bg-white rounded-xl shadow-sm border border-gray-100">
        <div>
          <h2 className="text-2xl font-bold tracking-tight mb-2">Registro de Área</h2>
          <p className="text-gray-500 mb-6">Complete la siguiente información:</p>
        </div>

        {/* Información Básica */}
        <FormInput name="nombre" label="Nombre del área" required />
        <Button type="submit">
          Guardar área
        </Button>
      </form>
    </FormProvider>
  );
}
