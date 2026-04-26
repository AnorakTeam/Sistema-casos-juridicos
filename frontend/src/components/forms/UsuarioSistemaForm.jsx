import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";

import { FormInput } from "./parts/FormInput";
import { FormSelect } from "./parts/FormSelect";
import { FormCheckbox } from "./parts/FormCheckbox";
import { Button } from "@/components/ui/button";
import { Separator } from "@/components/ui/separator";

export function UsuarioSistemaForm() {
  const { register, handleSubmit, watch, formState: { errors } } = useForm();

  const tipo = watch("tipo");

  const API_URL = "http://localhost:8080/api";
  const REQUIRED = "Campo obligatorio";

  const [asesores, setAsesores] = useState([]);
  const [areas, setAreas] = useState([]);
  const [sedes, setSedes] = useState([]);

  const fetchData = async (url) => {
    const res = await fetch(url);
    if (!res.ok) throw new Error("Error en la petición");
    return await res.json();
  };

  useEffect(() => {

    const cargarDatos = async () => {
      try {

        const sedesData = await fetchData(`${API_URL}/sedes`);
        setSedes(
          sedesData.map(s => ({
            value: s.id,
            label: s.nombre
          }))
        );

        if (tipo === "estudiantes") {
          const asesoresData = await fetchData(`${API_URL}/asesores`);
          setAsesores(
            asesoresData.map(a => ({
              value: a.id,
              label: `${a.nombre} - ${a.documento}`
            }))
          );
        }

        if (tipo === "asesores") {
          const areasData = await fetchData(`${API_URL}/areas`);
          setAreas(
            areasData.map(a => ({
              value: a.id,
              label: a.nombre
            }))
          );
        }

      } catch (error) {
        console.error("Error cargando datos:", error);
      }
    };

    if (tipo) {
      cargarDatos();
    }

  }, [tipo]);

  const tipoOptions = [
    { value: "estudiantes", label: "Estudiante" },
    { value: "asesores", label: "Asesor" },
    { value: "monitores", label: "Monitor" },
    { value: "administrativos", label: "Administrativo" },
    { value: "conciliadores", label: "Conciliador" },
  ];

  const tipoDocumentoOptions = [
    { value: 1, label: "Cédula de Ciudadanía" },
    { value: 2, label: "Tarjeta de Identidad" },
    { value: 3, label: "Cédula de Extranjería" },
  ];

  const handleSubmitForm = async (data) => {
    try {
      const res = await fetch(`${API_URL}/${tipo}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
      });

      if (!res.ok) throw new Error("Error al guardar");

      const result = await res.json();
      console.log("Guardado:", result);

    } catch (error) {
      console.error("Error al enviar:", error);
    }
  };

  const camposBase = (
    <>
      <FormInput name="nombre" label="Nombre" register={register} errors={errors} rules={{ required: REQUIRED }} />

      <FormSelect
        name="tipoDocumento.id"
        label="Tipo Documento"
        options={tipoDocumentoOptions}
        register={register}
        errors={errors}
        rules={{ required: REQUIRED }}
      />

      <FormInput name="documento" label="Documento" register={register} errors={errors} rules={{ required: REQUIRED }} />

      <FormInput name="email" label="Email" type="email" register={register} errors={errors} rules={{ required: REQUIRED }} />

      <FormInput name="telefono" label="Teléfono" register={register} errors={errors} rules={{ required: REQUIRED }} />

      <FormInput name="usuario" label="Usuario" register={register} errors={errors} rules={{ required: REQUIRED }} />

      <FormInput name="codigo" label="Código" register={register} errors={errors} rules={{ required: REQUIRED }} />

      <FormSelect
        name="sede.id"
        label="Sede"
        options={sedes}
        register={register}
        errors={errors}
        rules={{ required: REQUIRED }}
      />
    </>
  );

  //  ESPECÍFICOS
  const renderCamposEspecificos = () => {
    switch (tipo) {

      case "estudiantes":
        return (
          <>
            <FormSelect
              name="asesor.id"
              label="Asesor"
              options={asesores}
              register={register}
              errors={errors}
              rules={{ required: REQUIRED }}
            />

            <FormCheckbox
              name="conciliacion"
              label="¿Tiene conciliación?"
              register={register}
              errors={errors}
            />
          </>
        );

      case "asesores":
        return (
          <FormSelect
            name="area.id"
            label="Área"
            options={areas}
            register={register}
            errors={errors}
            rules={{ required: REQUIRED }}
          />
        );

      case "administrativos":
        return (
          <FormCheckbox
            name="directora"
            label="¿Es directora?"
            register={register}
            errors={errors}
          />
        );

      case "conciliadores":
        return (
          <FormSelect
            name="tipoConciliador"
            label="Tipo de Conciliador"
            options={[
              { value: "JUDICIAL", label: "Judicial" },
              { value: "EXTRAJUDICIAL", label: "Extrajudicial" },
            ]}
            register={register}
            errors={errors}
            rules={{ required: REQUIRED }}
          />
        );

      default:
        return null;
    }
  };

  return (
    <div className="space-y-8 p-6 bg-card rounded-xl shadow border">

      <div>
        <h2 className="text-2xl font-bold">Crear Perfil</h2>
        <p className="text-muted-foreground">
          Complete la información según el tipo seleccionado.
        </p>
      </div>

      <FormSelect
        name="tipo"
        label="Tipo de Perfil"
        options={tipoOptions}
        register={register}
        errors={errors}
        rules={{ required: REQUIRED }}
      />

      <section className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {camposBase}
      </section>

      <Separator />

      {tipo && (
        <section className="space-y-4">
          <h3 className="font-semibold">Información específica</h3>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            {renderCamposEspecificos()}
          </div>
        </section>
      )}

      <FormCheckbox name="activo" label="Activo" register={register} errors={errors} />

      <div className="flex justify-end">
        <Button onClick={handleSubmit(handleSubmitForm)}>
          Crear Perfil
        </Button>
      </div>

    </div>
  );
}