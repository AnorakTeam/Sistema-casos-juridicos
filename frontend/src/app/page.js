import { LoginForm } from "@/components/auth/LoginForm"

export default function LoginPage() {
  return (
    <main className="grid min-h-screen lg:grid-cols-2 bg-blue-100">

      <div className="flex items-center justify-center px-4 py-12 border-style: double;">
        <LoginForm />
      </div>

      <div className="relative hidden lg:block">
        <img
          src="/Consultorio-Juridico.jpg"
          className="absolute inset-0 h-full w-full object-cover opacity-70"
        />
      </div>

    </main>
  )
}