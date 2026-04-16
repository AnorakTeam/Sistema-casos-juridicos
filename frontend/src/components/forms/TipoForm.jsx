import React, { useState } from 'react';
import { useForm, FormProvider } from 'react-hook-form';
import { FormInput } from './parts/FormInput';
import { FormMultiSelect } from './parts/FormMultiSelect';
import { Button } from '@/components/ui/button';

export function TipoForm({ onSubmit, initialValues = {} }) {
  const methods = useForm();
  const [selectedTemas, setSelectedTemas] = useState(initialValues.temas || []);

  const { handleSubmit } = methods;

  // Claramente, cambiar esto por un useState y useEffect para hacer fetch a la API
  const allTemas = [
    {value: 'temaid1', label: 'tema numero 1'},
    {value: 'temaid2', label: 'tema numero 2'},
    {value: 'temaid3', label: 'tema numero 3'},
  ]

  const sendData = (data) => {
    // comunicarse con el backend
  }

  const handleFormSubmit = (data) => {
    const formData = {
      ...data,
      temas: selectedTemas
    };

    if (onSubmit) {
      onSubmit(formData);
    } else {
      console.log('=== Form Data ===');
      Object.entries(formData).forEach(([key, value]) => {
        const formattedValue = typeof value === 'object' && value !== null
          ? JSON.stringify(value)
          : value;
        console.log(`${key}: ${formattedValue}`);
      });
      console.log('=================');
      // Aqui va la logica de envio al backend por defecto
      // (hay que hablar con el backend para el tema de cómo le mandamos
      // la info, eché un ojo y solo ese dto es de casi 1000 líneas)
      sendData(formData)
    }
  }

  return (
    <FormProvider {...methods}>
      <form onSubmit={handleSubmit(handleFormSubmit)} className="space-y-8 p-6 bg-card rounded-xl shadow-sm border border-border">
        <div>
          <h2 className="text-2xl font-bold tracking-tight mb-2">Registro de Tipo</h2>
          <p className="text-muted-foreground mb-6">Complete la siguiente información:</p>
        </div>

        {/* Información Básica */}
        <FormInput name="nombre" label="Nombre del tipo" required />
        <FormMultiSelect
          label="Temas a relacionar"
          placeholder="Selecciona un tema para agregar"
          selectedItems={selectedTemas}
          availableItems={allTemas}
          onSelectionChange={setSelectedTemas}
          itemLabel="Temas seleccionados"
          addButtonText="Agregar"
        />
        <Button type="submit">
          Guardar tema
        </Button>
      </form>
    </FormProvider>
  );
}
