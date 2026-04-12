"use client"

import * as React from "react"
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  SidebarSeparator,
} from "@/components/ui/sidebar"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { LayoutDashboard } from "lucide-react"

export function AppSidebar({ mainItems = [], footerItems = [] }) {
  return (
    <Sidebar className="[--sidebar:white] border-r border-gray-200">
      <SidebarHeader className="p-4 flex items-center gap-2">
        <div className="flex aspect-square size-8 items-center justify-center rounded-lg bg-black text-white">
          <LayoutDashboard className="size-4" />
        </div>
        <div className="flex flex-col gap-0.5 leading-none items-center">
          <span className="font-semibold text-gray-900 text-sm">Sistema Casos</span>
          <span className="text-xs text-gray-500">v1.0.0</span>
        </div>
      </SidebarHeader>

      <SidebarSeparator />

      <SidebarContent className="px-2 py-4">

        {/* TODO: Cambiar los items a un map para recibirlos por prop
                  A menos que no agreguemos más opciones. En ese caso, hacer
                  el item de casos jurídicos un acordeón, dentro de ese nuevo
                  componente tocaría hacer que antes de mostrarse, consulte 
                  todos los casos que tiene la persona logueada y así saber
                  cuántos casos tiene dentro del acordeón */}

        <SidebarMenu>
          {mainItems.map((item, index) => (
            <SidebarMenuItem key={index}>
              <SidebarMenuButton tooltip={item.tooltip}>
                <span>{item.title}</span>
              </SidebarMenuButton>
            </SidebarMenuItem>
          ))}
        </SidebarMenu>
      </SidebarContent>

      <SidebarSeparator />

      <SidebarFooter className="p-4">
        <SidebarMenu>
          {footerItems.map((item, index) => (
            <SidebarMenuItem key={index}>
              <SidebarMenuButton tooltip={item.tooltip}>
                <span>{item.title}</span>
              </SidebarMenuButton>
            </SidebarMenuItem>
          ))}

          <SidebarMenuItem className="mt-4">
            <div className="flex items-center gap-3 px-2 py-1.5">
              <Avatar className="size-8">
                <AvatarImage src="https://github.com/shadcn.png" />
                <AvatarFallback>Perosna</AvatarFallback>
              </Avatar>
              <div className="flex flex-col text-left text-sm leading-tight">
                <span className="truncate font-semibold">Perosna</span>
                <span className="truncate text-xs text-gray-500">Perosna@example.com</span>
              </div>
            </div>
          </SidebarMenuItem>
        </SidebarMenu>
      </SidebarFooter>
    </Sidebar>
  )
}
