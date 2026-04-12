export default function Home() {
  return (
    // TODO: Creo que es mejor mover el sidebar para acá, porque nos toca cambiar de componentes
    // en lo interno, a menos que no hagamos SPA en el futuro? Igual ya está instalado react router
    <div className="flex flex-col gap-4">
      <h1 className="text-2xl font-bold">Bienvenido al Sistema de Casos Jurídicos</h1>
      <p className="text-gray-500">Acá está todo el contenido, a la izquierda la barra lateral</p>
    </div>
  )
}
