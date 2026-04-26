"use client"

import { SidebarProvider, SidebarInset, SidebarTrigger } from "@/components/ui/sidebar"
import { AppSidebar } from "@/components/app-sidebar"
import { TooltipProvider } from "@/components/ui/tooltip"
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
import { ConsultasJuridicasForm } from "@/components/forms/ConsultasJuridicasForm"

const sections = ["Inicio", "Tareas", "Recepcion", "Nueva consulta", "Consultas juridicas", "Nuevo Proceso", "Procesos juridicos", "Solicitudes virtuales", "Estudiantes", "Asesores y monitores", "Estadísticas", "Formatos y modelos", "Eliminacines", "Leyes.info"]


export default function ConsultasJuridicasPage() {
  const { setTheme } = useTheme()

  const mainItems = sections.map((item) => ({
    title: item,
    tooltip: item,
  }))

  return (
    <TooltipProvider>
      <SidebarProvider>
        <AppSidebar mainItems={mainItems} footerItems={[]} />

        <SidebarInset>
          <header className="flex h-16 items-center justify-between border-b px-4">
            <div className="flex items-center gap-2">
              <SidebarTrigger />
              <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="outline" size="icon">
                  <Sun className="h-[1.2rem] w-[1.2rem] dark:hidden" />
                  <Moon className="hidden h-[1.2rem] w-[1.2rem] dark:block" />
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent align="end">
                <DropdownMenuItem onClick={() => setTheme("light")}>Light</DropdownMenuItem>
                <DropdownMenuItem onClick={() => setTheme("dark")}>Dark</DropdownMenuItem>
                <DropdownMenuItem onClick={() => setTheme("system")}>System</DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
              <h2 className="text-lg font-semibold">Consultas Jurídicas</h2>
            </div>

          </header>

          <div className="p-6 lg:p-8 space-y-6">
            <ConsultasJuridicasForm />
          </div>
        </SidebarInset>

        <Toaster richColors position="bottom-right" />
      </SidebarProvider>
    </TooltipProvider>
  )
}