package co.edu.ufps.legal_cases.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

        // Maneja las excepciones personalizadas de los service
        @ExceptionHandler(BusinessException.class)
        public ResponseEntity<ErrorResponse> manejarBusinessException(
                        BusinessException ex,
                        HttpServletRequest request) {

                ErrorResponse error = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                "Error de negocio",
                                ex.getMessage(),
                                request.getRequestURI());

                return ResponseEntity.badRequest().body(error);
        }

        //Maneja los errores por validaciones en campos de entrada como el @Valid, @Email, etc
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, Object>> manejarErroresValidacion(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest request) {

                Map<String, String> errores = new HashMap<>();

                ex.getBindingResult().getFieldErrors()
                                .forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));

                Map<String, Object> respuesta = new HashMap<>();
                respuesta.put("fecha", LocalDateTime.now());
                respuesta.put("estado", HttpStatus.BAD_REQUEST.value());
                respuesta.put("error", "Error de validacion");
                respuesta.put("mensaje", "Uno o mas campos no son validos");
                respuesta.put("ruta", request.getRequestURI());
                respuesta.put("detalles", errores);

                return ResponseEntity.badRequest().body(respuesta);
        }

        //Para otras excepciones no manejadas
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> manejarExceptionGeneral(
                        Exception ex,
                        HttpServletRequest request) {

                ErrorResponse error = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Error interno del servidor",
                                ex.getMessage(),
                                request.getRequestURI());

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
}