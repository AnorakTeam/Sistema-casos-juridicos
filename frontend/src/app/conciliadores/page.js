"use client"

import { SidebarProvider, SidebarInset, SidebarTrigger } from "@/components/ui/sidebar"
import { AppSidebar } from "@/components/app-sidebar"
import { TooltipProvider } from "@/components/ui/tooltip"
import { Toaster } from "@/components/ui/sonner"
import { Moon, Sun } from "lucide-react"
import { useTheme } from "next-themes"
import { Button } from "@/components/ui/button"
import { useRouter } from "next/navigation"
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import { ConciliadorForm } from "@/components/forms/ConciliadorForm"

export default function NuevoConciliador() {
  const { setTheme } = useTheme()
  const router = useRouter()

  const sections = ["Inicio", "Tareas", "Recepcion", "Nueva consulta", "Consultas juridicas", "Nuevo Proceso", "Procesos juridicos", "Solicitudes virtuales", "Estudiantes", "Asesores y monitores", "Estadísticas", "Formatos y modelos", "Eliminacines", "Leyes.info"]

  const mainItems = sections.map((item) => ({
    title: item,
    tooltip: item,
  }))

  const footerItems = [
    { title: "Configuración", tooltip: "Configuración", path: "/configuracion" },
  ]

  const handleSuccess = () => {
    // Redirige a la lista de conciliadores después de guardar
    router.push("/conciliadores")
  }

  return (
    <TooltipProvider>
      <SidebarProvider>
        <AppSidebar mainItems={mainItems} footerItems={footerItems} />

        <SidebarInset>
          <header className="flex h-16 items-center justify-between border-b px-4">
            <div className="flex items-center gap-2">
              <SidebarTrigger />
              <span className="text-sm text-muted-foreground">
                Conciliadores / Nuevo
              </span>
            </div>

            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="outline" size="icon">
                  <Sun className="h-[1.2rem] w-[1.2rem] transition-all dark:scale-0 dark:-rotate-90" />
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
          </header>

          <div className="flex flex-col gap-4 p-4 lg:p-8">
            <ConciliadorForm onSuccess={handleSuccess} />
          </div>
        </SidebarInset>

        <Toaster richColors position="bottom-right" />
      </SidebarProvider>
    </TooltipProvider>
  )
}
