"use client"

import React, { useState } from "react"
import { useRouter } from "next/navigation"
import { useForm } from "react-hook-form"
import { useApiForm } from "@/hooks/useApiForm"
import { FormInput } from "../forms/parts/FormInput"
import { Button } from "@/components/ui/button"

export function LoginForm() {
  const router = useRouter()
  const [errorMessage, setErrorMessage] = useState("")

  const { register, handleSubmit, formState: { errors } } = useForm()

  const API_URL_BASE = "http://localhost:8080/api"

  const { submit, isSubmitting } = useApiForm({
    endpoint: `${API_URL_BASE}/auth/login`
  })

  const REQUIRED = "Campo obligatorio"

  const handleSubmitForm = async (data) => {
    setErrorMessage("")

    try {
      const response = await submit({
        username: data.username,
        password: data.password
      })

      if (response) {
        localStorage.setItem("usuarioId", response.usuarioId)
        localStorage.setItem("username", response.username)
        localStorage.setItem("rol", response.rolNombre)
        localStorage.setItem("perfil", response.tipoPerfil)
        localStorage.setItem("permisos", JSON.stringify(response.permisos))

        router.push("/inicio")
      }

    } catch (error) {
      //mensaje error
      setErrorMessage(
        error?.response?.data?.message ||
        error?.message ||
        "Credenciales inválidas"
      )
    }
  }

  return (
    <div className="w-full max-w-md rounded-3xl border border-border bg-card p-8 shadow-sm shadow-muted/40">
      
      <div className="mb-6 space-y-2">
        <h1 className="text-3xl font-semibold">Iniciar sesión</h1>
      </div>

      <form onSubmit={handleSubmit(handleSubmitForm)} className="space-y-4">

        <FormInput
          name="username"
          label="Correo electrónico"
          register={register}
          errors={errors}
          rules={{ required: REQUIRED }}
        />

        <FormInput
          name="password"
          label="Contraseña"
          type="password"
          register={register}
          errors={errors}
          rules={{ required: REQUIRED }}
        />

        {errorMessage && (
          <div className="text-sm text-destructive">
            {errorMessage}
          </div>
        )}

        <Button type="submit" className="w-full" disabled={isSubmitting}>
          {isSubmitting ? "Ingresando..." : "Acceder"}
        </Button>

      </form>
    </div>
  )
}