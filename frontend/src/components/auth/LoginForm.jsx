"use client"

import { useState } from "react"
import { useRouter } from "next/navigation"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"

export function LoginForm() {
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const router = useRouter()
    
  //funcion para cambiar de pagina con los datos en este casoel email del usuario
  function handleSubmit(event) {
  event.preventDefault()
  localStorage.setItem("userEmail", email)
  router.push("/Inicio")
}
      //formulario de login con campos para correo electrónico y contraseña, y un botón para enviar el formulario. 
      //También incluye un enlace para recuperar la contraseña en caso de que el usuario la haya olvidado(no codificado) 
      //Al enviar el formulario, se guarda el correo electrónico en el almacenamiento local y se redirige al usuario 
      //a la página de inicio.
  return (
    <div className="w-full max-w-md rounded-3xl border border-border bg-card p-8 shadow-sm shadow-muted/40">
      <div className="mb-6 space-y-2">
        <h1 className="text-3xl font-semibold">Iniciar sesión</h1>
      </div>

      <form onSubmit={handleSubmit} className="space-y-4">
        <label className="grid gap-2 text-sm font-medium">
          Correo electrónico
          <Input
            type="email"
            value={email}
            onChange={(event) => setEmail(event.target.value)}
            placeholder="usuario@correo.com"
            required
          />
        </label>

        <label className="grid gap-2 text-sm font-medium">
          <div className="flex items-center justify-between">
            <span>Contraseña</span>
            <a href="#" className="text-sm text-primary hover:underline">
              ¿Olvidaste tu contraseña?
            </a>
          </div>
          <Input
            type="password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
            placeholder="••••••••"
            required
          />
        </label>

        <Button type="submit" className="w-full">
          Acceder
        </Button>
      </form>
    </div>
  )
}
