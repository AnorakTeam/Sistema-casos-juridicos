import { useState } from "react";
import { toast } from "sonner";


/**
 * Para enviar los datos del formulario al backend.
 * 
 * @note puede cambiar en el futuro para acoplarlo con react hook form y form provider
 * @param {*} param0 
 * @returns 
 */
export function useApiForm({ endpoint }) {
  const [isSubmitting, setIsSubmitting] = useState(false);

  const submit = async (data) => {
    setIsSubmitting(true);

    try {
      const response = await fetch(endpoint, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      });

      if (response.ok) {
        const result = await response.json();

        toast.success("Registro exitoso");

        return { success: true, data: result };
      } else {
        const errorData = await response.json();

        // TODO: Quizás sólo mostrar el mensaje de la respuesta, no todo el content
        let errorMessage = "";

        for(const key in errorData) {
            errorMessage += `${key}: ${errorData[key]}\n`;
        }

        toast.error("Error en la operación", {
          description: errorMessage,
        });

        return { success: false, error: errorData };
      }
    } catch (error) {
      console.error("Error de red:", error);

      toast.error("Error de conexión", {
        description: "Verifique que el backend esté disponible",
      });

      return { success: false, error };
    } finally {
      setIsSubmitting(false);
    }
  };

  return {
    submit,
    isSubmitting,
  };
}