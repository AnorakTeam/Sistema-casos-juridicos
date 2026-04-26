import React, { useState } from "react";
import { useApiForm } from "@/hooks/useApiForm";
import { useForm } from "react-hook-form";
import { FormInput } from "./parts/FormInput";
import { Button } from "@/components/ui/button";
import { toast } from "@/components/ui/sonner";

export function AreaForm() {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({
    defaultValues: {
      nombre: "",
    },
  });

  const { submit, isSubmitting } = useApiForm({endpoint: "http://localhost:8080/api/areas"});
  

  const API_URL_BASE = "http://localhost:8080/api"

  const onSubmit = async (data) => {
    await submit(data);
  };

  return (
    <div className="space-y-6 p-6 bg-card rounded-xl border">
      <div>
        <h2 className="text-2xl font-bold">Registro de Área</h2>
        <p className="text-muted-foreground">
          Complete la siguiente información
        </p>
      </div>

      <FormInput
        name="nombre"
        label="Nombre del área"
        register={register}
        errors={errors}
        rules={{ required: "El nombre es obligatorio" }}
      />

      <Button onClick={handleSubmit(onSubmit)} disabled={isSubmitting}>
        {isSubmitting ? "Guardando..." : "Guardar área"}
      </Button>
    </div>
  );
}
