"use client"

import { SidebarProvider, SidebarInset, SidebarTrigger } from "@/components/ui/sidebar"
import { AppSidebar } from "@/components/app-sidebar"
import { TooltipProvider } from "@/components/ui/tooltip"
import * as React from "react"
import { Moon, Sun } from "lucide-react"
import { useTheme } from "next-themes"
import { Button } from "@/components/ui/button"
import { Toaster } from "@/components/ui/sonner"
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import { Card, CardContent } from "@/components/ui/card"

export default function Home() {
  const sections = ["Inicio", "Tareas", "Recepcion", "Nueva consulta", "Consultas juridicas", "Nuevo Proceso", "Procesos juridicos", "Solicitudes virtuales", "Estudiantes", "Asesores y monitores", "Estadísticas", "Formatos y modelos", "Eliminacines", "Leyes.info"]

  const { setTheme } = useTheme()

  const mainItems = sections.map((item) => ({
    title: item,
    tooltip: item,
  }))

  const footerItems = [
    { title: "Configuración", tooltip: "Configuración", path: "/configuracion" },
  ]

  return (
    <TooltipProvider>
      <SidebarProvider>
        <AppSidebar mainItems={mainItems} footerItems={footerItems} />

        <SidebarInset>
          <header className="flex h-16 items-center justify-between border-b px-4">
            <div className="flex items-center gap-2">
              <SidebarTrigger />
              <h2 className="text-lg font-semibold">Casos Jurídicos</h2>
            </div>

            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="outline" size="icon">
                  <Sun className="h-[1.2rem] w-[1.2rem] dark:hidden" />
                  <Moon className="hidden h-[1.2rem] w-[1.2rem] dark:block" />
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent align="end">
                <DropdownMenuItem onClick={() => setTheme("light")}>
                  Light
                </DropdownMenuItem>
                <DropdownMenuItem onClick={() => setTheme("dark")}>
                  Dark
                </DropdownMenuItem>
                <DropdownMenuItem onClick={() => setTheme("system")}>
                  System
                </DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
          </header>

          <section className="p-6 lg:p-10">
            <div className="max-w-5xl">
              <h1 className="text-3xl lg:text-4xl font-bold mb-4">
                Bienvenidos al Sistema de Casos Jurídicos
              </h1>
              <p className="text-muted-foreground text-lg mb-6">
                Administra consultas, procesos y documentación legal en un solo lugar de forma rápida y segura.
              </p>

            </div>
          </section>

          <section className="grid md:grid-cols-2 lg:grid-cols-3 gap-6 px-6 lg:px-10 pb-10">
            {[
              {
                title: "Consultas Jurídicas",
                desc: "Registra y gestiona consultas legales de manera estructurada.",
              },
              {
                title: "Procesos",
                desc: "Control completo de procesos jurídicos con seguimiento detallado.",
              },
              {
                title: "Estudiantes",
                desc: "Administra la participación de estudiantes en los casos.",
              },
              {
                title: "Documentos",
                desc: "Accede a formatos y modelos legales fácilmente.",
              },
              {
                title: "Estadísticas",
                desc: "Visualiza métricas clave para toma de decisiones.",
              },
              {
                title: "Solicitudes Virtuales",
                desc: "Gestiona solicitudes en línea de manera eficiente.",
              },
            ].map((item, i) => (
              <Card key={i} className="hover:shadow-lg transition">
                <CardContent className="p-6">
                  <h3 className="text-xl font-semibold mb-2">{item.title}</h3>
                  <p className="text-muted-foreground">{item.desc}</p>
                </CardContent>
              </Card>
            ))}
          </section>

        </SidebarInset>

        <Toaster richColors position="bottom-right" />
      </SidebarProvider>
    </TooltipProvider>
  )
}
