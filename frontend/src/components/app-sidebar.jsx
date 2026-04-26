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
import { useRouter } from "next/navigation"

export function AppSidebar({ mainItems = [], footerItems = [] }) {
  const [email, setEmail] = React.useState("")
  const [name, setName] = React.useState("")
  const router = useRouter()

  React.useEffect(() => {
    const storedEmail = localStorage.getItem("userEmail") || ""
    setEmail(storedEmail)

    if (storedEmail.includes("@")) {
      setName(storedEmail.split("@")[0])
    }
  }, [])

  function normalizePath(text) {
    return `/${String(text).toLowerCase().replace(/\s+/g, "")}`
  }

  function handleSubmit(item) {
    const path = item.path
      ? item.path.startsWith("/")
        ? item.path
        : `/${item.path}`
      : normalizePath(item.title)

    if (!path) return

    localStorage.setItem("userEmail", email)
    router.push(path)
  }

  return (
    <Sidebar className="bg-sidebar border-r border-sidebar-border text-sidebar-foreground">
      <SidebarHeader className="p-4 flex items-center gap-2">
        <div className="flex aspect-square size-8 items-center justify-center rounded-lg bg-primary text-primary-foreground">
          <LayoutDashboard className="size-4" />
        </div>
        <div className="flex flex-col gap-0.5 leading-none items-center">
          <span className="font-semibold text-foreground text-sm">Sistema Casos</span>
          <span className="text-xs text-muted-foreground">v1.0.0</span>
        </div>
      </SidebarHeader>

      <SidebarSeparator />

      <SidebarContent className="px-2 py-4">
        <SidebarMenu>
          {mainItems.map((item, index) => (
            <SidebarMenuItem key={item.title || index}>
              <SidebarMenuButton tooltip={item.tooltip} onClick={() => handleSubmit(item)}>
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
            <SidebarMenuItem key={item.title || index}>
              <SidebarMenuButton tooltip={item.tooltip} onClick={() => handleSubmit(item)}>
                <span>{item.title}</span>
              </SidebarMenuButton>
            </SidebarMenuItem>
          ))}

          <SidebarMenuItem className="mt-4">
            <div className="flex items-center gap-3 px-2 py-1.5">
              <Avatar className="size-8">
                <AvatarImage src="https://github.com/shadcn.png" />
                <AvatarFallback>{name?.charAt(0).toUpperCase()}</AvatarFallback>
              </Avatar>
              <div className="flex flex-col text-left text-sm leading-tight">
                <span className="truncate font-semibold">{name}</span>
                <span className="truncate text-xs text-muted-foreground">{email}</span>
              </div>
            </div>
          </SidebarMenuItem>
        </SidebarMenu>
      </SidebarFooter>
    </Sidebar>
  )
}