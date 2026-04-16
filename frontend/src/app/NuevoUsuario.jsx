import { useState } from "react";

const inputStyle = {
  padding: "10px",
  border: "1px solid #ddd",
  borderRadius: 6,
  width: "100%",
};

const labelStyle = {
  fontSize: 12,
  fontWeight: 600,
  marginBottom: 4,
  display: "block",
};

function Seccion({ titulo, children }) {
  return (
    <div style={{
      background: "#fff",
      padding: 20,
      borderRadius: 10,
      marginBottom: 20,
      boxShadow: "0 2px 10px rgba(0,0,0,.05)"
    }}>
      <h3>{titulo}</h3>
      {children}
    </div>
  );
}

export default function NuevoUsuario() {
  const [form, setForm] = useState({});

  return (
    <>
      <Seccion titulo="Información básica">
        <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 16 }}>
          <input placeholder="Nombres" style={inputStyle} />
          <input placeholder="Apellidos" style={inputStyle} />
          <input placeholder="Documento" style={inputStyle} />
          <input placeholder="Teléfono" style={inputStyle} />
        </div>
      </Seccion>

      <Seccion titulo="Información de vivienda">
        <input placeholder="Dirección" style={inputStyle} />
      </Seccion>

      <Seccion titulo="Aspectos económicos">
        <input placeholder="Ingresos" style={inputStyle} />
      </Seccion>

      <Seccion titulo="Información del servicio">
        <select style={inputStyle}>
          <option>Tipo de usuario</option>
        </select>
      </Seccion>
    </>
  );
}