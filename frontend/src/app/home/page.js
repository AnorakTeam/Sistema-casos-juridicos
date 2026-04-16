"use client"

import { SidebarProvider, SidebarInset, SidebarTrigger } from "@/components/ui/sidebar"
import { AppSidebar } from "@/components/app-sidebar"
import { TooltipProvider } from "@/components/ui/tooltip"
import { PersonaForm } from "@/components/forms/PersonaForm"

export default function Home() {
  //En caso de necesitarse mas adeñante usar esta estructura para cambiar la sidebar
  //Se podria conseguir de algun otro lado atraves de sections y alimentar la sidebar como se desee
  //no se si separalo como otro componente o dejarlo aca y que solo se necesite hacer un arreglo y pasarlo

  const sections = ["Inicio", "Casos Jurídicos", "Mi Perfil", "Ayuda"]

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
            <SidebarTrigger />
          </header>

          <div className="flex flex-col gap-4 p-4 lg:p-8">
            <h1 className="text-2xl font-bold">Bienvenido al Sistema de Casos Jurídicos</h1>
            <p className="text-gray-500">Acá está todo el contenido, a la izquierda la barra lateral</p>
            <PersonaForm />
          </div>

        </SidebarInset>

      </SidebarProvider>
    </TooltipProvider>
  )
}

