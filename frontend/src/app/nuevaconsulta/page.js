"use client"

import { SidebarProvider, SidebarInset, SidebarTrigger } from "@/components/ui/sidebar"
import { AppSidebar } from "@/components/app-sidebar"
import { TooltipProvider } from "@/components/ui/tooltip"
import { ConsultasJuridicasForm } from "@/components/forms/ConsultasJuridicasForm"
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

const sections = ["Inicio", "Tareas", "Recepcion", "Nueva consulta", "Consultas juridicas", "Nuevo Proceso", "Procesos juridicos", "Solicitudes virtuales", "Estudiantes", "Asesores y monitores", "Estadísticas", "Formatos y modelos", "Eliminacines", "Leyes.info"]

  const { setTheme } = useTheme()

const mainItems = sections.map((item) => ({
  title: item,
  tooltip: item,
}))

const footerItems = [
  { title: "Configuración", tooltip: "Configuración", path: "/configuracion" },
]

export default function ConsultasJuridicasPage() {
  const { setTheme } = useTheme()

  return (
    <TooltipProvider>
      <SidebarProvider>
        <AppSidebar mainItems={mainItems} footerItems={footerItems} />
        <SidebarInset>
          <header className="flex h-16 items-center gap-2 border-b px-4">
            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="outline" size="icon">
                  <Sun className="h-[1.2rem] w-[1.2rem] scale-100 rotate-0 transition-all dark:scale-0 dark:-rotate-90" />
                  <Moon className="absolute h-[1.2rem] w-[1.2rem] scale-0 rotate-90 transition-all dark:scale-100 dark:rotate-0" />
                  <span className="sr-only">Toggle theme</span>
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent align="end">
                <DropdownMenuItem onClick={() => setTheme("light")}>Light</DropdownMenuItem>
                <DropdownMenuItem onClick={() => setTheme("dark")}>Dark</DropdownMenuItem>
                <DropdownMenuItem onClick={() => setTheme("system")}>System</DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
            <SidebarTrigger />
          </header>

          <div className="flex flex-col gap-4 p-4 lg:p-8">
            <h1 className="text-2xl font-bold">Consultas Jurídicas</h1>
            <p className="text-muted-foreground">
              Busca y revisa las consultas registradas en el sistema.
            </p>
            <ConsultasJuridicasForm />
          </div>
        </SidebarInset>

        <Toaster richColors position="bottom-right" />
      </SidebarProvider>
    </TooltipProvider>
  )
}