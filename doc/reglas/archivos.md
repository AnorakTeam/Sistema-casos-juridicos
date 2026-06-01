# Reglas de negocio - Archivos

> Documento validado contra el código fuente actualizado del sistema. La documentación describe únicamente comportamiento implementado en backend.


## 1. Regla general

Los archivos se almacenan como recursos documentales asociados al sistema. El backend conserva rutas relativas y evita exponer rutas físicas internas como contrato funcional.

---

## 2. Seguridad de rutas

El almacenamiento valida las rutas solicitadas para operar dentro del directorio permitido. Esta regla protege contra accesos fuera del área de almacenamiento configurada.

---

## 3. Carga de documentos

La carga puede ser individual o múltiple. Los archivos se reciben como `MultipartFile` y se almacenan usando `FileStorageService`.

---

## 4. Descarga de documentos

La descarga se realiza por ruta relativa. Si el recurso no existe, el backend responde con estado de no encontrado.

---

## 5. Uso documental en conciliación

La conciliación usa archivos para soportar:

- solicitud inicial;
- acta de finalización.

La finalización de conciliación registra el acta como soporte del cierre funcional.
