package co.edu.ufps.legal_cases.util;

public final class NormalizacionUtils {

    private NormalizacionUtils() {
    }

    public static String normalizarTexto(String texto) {
        if (texto == null) {
            return null;
        }

        String limpio = texto.trim();

        if (limpio.isBlank()) {
            return null;
        }

        return limpio.replaceAll("\\s+", " ");
    }

    public static String normalizarId(String id) {
        String limpio = normalizarTexto(id);

        if (limpio == null) {
            return null;
        }

        return limpio.toUpperCase();
    }

    public static boolean estaInformado(String valor) {
        return valor != null
                && !valor.trim().isBlank()
                && !valor.trim().equalsIgnoreCase("No informa");
    }

    public static String normalizarNumeroDocumento(String valor) {
        String limpio = normalizarTexto(valor);

        if (limpio == null) {
            return null;
        }

        return limpio.replaceAll("[\\.\\-\\s]", "");
    }

    public static String normalizarTelefono(String valor) {
        String limpio = normalizarTexto(valor);

        if (limpio == null) {
            return null;
        }

        return limpio.replaceAll("[^0-9]", "");
    }
}