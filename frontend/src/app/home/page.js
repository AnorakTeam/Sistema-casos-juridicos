"use client"

import { SidebarProvider, SidebarInset, SidebarTrigger } from "@/components/ui/sidebar"
import { AppSidebar } from "@/components/app-sidebar"
import { TooltipProvider } from "@/components/ui/tooltip"
import { PersonaForm } from "@/components/forms/PersonaForm"
import * as React from "react"
import { Moon, Sun } from "lucide-react"
import { useTheme } from "next-themes"
import { Button } from "@/components/ui/button"
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"


export default function Home() {
  //En caso de necesitarse mas adeñante usar esta estructura para cambiar la sidebar
  //Se podria conseguir de algun otro lado atraves de sections y alimentar la sidebar como se desee
  //no se si separalo como otro componente o dejarlo aca y que solo se necesite hacer un arreglo y pasarlo

  const sections = ["Inicio", "Casos Jurídicos", "Mi Perfil", "Ayuda"]

  const { setTheme } = useTheme()

  const mainItems = sections.map((item) => ({
    title: item,
    tooltip: item,
  }))

  const footerItems = [
    { title: "Configuración", tooltip: "Configuración" },
  ]
  //
  return (

    <TooltipProvider>
      <SidebarProvider>

        <AppSidebar
          mainItems={mainItems}
          footerItems={footerItems}
        />

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
            <SidebarTrigger />
          </header>

          <div className="flex flex-col gap-4 p-4 lg:p-8">
            <h1 className="text-2xl font-bold">Bienvenido al Sistema de Casos Jurídicos</h1>
            <p className="text-muted-foreground">Acá está todo el contenido, a la izquierda la barra lateral</p>
            <PersonaForm />
          </div>

        </SidebarInset>

      </SidebarProvider>
    </TooltipProvider>
  )
}

