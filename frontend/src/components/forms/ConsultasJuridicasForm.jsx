"use client"

import * as React from "react"
import { Button } from "@/components/ui/button"

//Creado con el objetivo de pruebas debe ser remplazado por fetch a la base de datos mas adelante
const sampleConsultas = [
  { id: "1", consulta: "Demanda de arrendamiento", fecha: "2026-04-18" },
  { id: "2", consulta: "Solicitud de asesoría laboral", fecha: "2026-04-17" },
  { id: "3", consulta: "Revisión de contrato civil", fecha: "2026-04-16" },
]

export function ConsultasJuridicasForm() {
  const [searchText, setSearchText] = React.useState("")
  const [rows, setRows] = React.useState([])

  function handleSearch(event) {
    event.preventDefault()

    const query = searchText.trim().toLowerCase()
    const filteredRows = sampleConsultas.filter((row) => {
      return (
        row.id.includes(query) ||
        row.consulta.toLowerCase().includes(query) ||
        row.fecha.includes(query)
      )
    })

    setRows(filteredRows)
  }
  //

  return (
    <div className="space-y-6">
      <form onSubmit={handleSearch} className="grid gap-4 md:grid-cols-[1fr_auto] items-end">
        <div className="space-y-2">
          <label htmlFor="consulta" className="text-sm font-medium text-foreground">
            Buscar consulta
          </label>
          <input
            id="consulta"
            value={searchText}
            onChange={(event) => setSearchText(event.target.value)}
            placeholder="Escribe tu búsqueda"
            className="w-full rounded-md border border-border bg-background px-3 py-2 text-sm outline-none transition focus:border-primary"
          />
        </div>
        <Button type="submit">Buscar</Button>
      </form>

      <div className="overflow-hidden rounded-lg border border-border bg-card">
        <table className="min-w-full divide-y divide-border">
          <thead className="bg-muted">
            <tr>
              <th className="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wide">ID</th>
              <th className="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wide">Consulta</th>
              <th className="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wide">Fecha</th>
              <th className="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wide">nombre</th>
              <th className="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wide">apellido</th>
              <th className="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wide">cedula</th>
            </tr>
          </thead>
          <tbody className="bg-background">
            {rows.length === 0 ? (
              <tr>
                <td colSpan={6} className="px-4 py-6 text-center text-sm text-muted-foreground">
                  No hay resultados. Usa el botón Buscar para cargar datos.
                </td>
              </tr>
            ) : (
              rows.map((row) => (
                <tr key={row.id}>
                  <td className="px-4 py-3 text-sm">{row.id}</td>
                  <td className="px-4 py-3 text-sm">{row.consulta}</td>
                  <td className="px-4 py-3 text-sm">{row.fecha}</td>
                  
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  )
}