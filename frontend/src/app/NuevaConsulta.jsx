import { useState } from "react";

const inputStyle = {
  padding: "10px",
  borderRadius: 6,
  border: "1px solid #ddd",
  width: "100%",
  fontSize: 13,
};

const labelStyle = {
  fontSize: 12,
  fontWeight: 600,
  marginBottom: 4,
  display: "block",
  color: "#444",
};

function Seccion({ titulo, children }) {
  return (
    <div style={{
      background: "#fff",
      borderRadius: 10,
      padding: 20,
      marginBottom: 20,
      boxShadow: "0 2px 10px rgba(0,0,0,.05)"
    }}>
      <h3 style={{ marginBottom: 15, color: "#2d2d2d" }}>{titulo}</h3>
      {children}
    </div>
  );
}

export default function NuevaConsulta() {
  const [form, setForm] = useState({});

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  return (
    <div style={{ display: "flex", flexDirection: "column", gap: 20 }}>

      {/* INFORMACIÓN GENERAL */}
      <Seccion titulo="Información general">
        <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 16 }}>
          
          <div>
            <label style={labelStyle}>Fecha *</label>
            <input type="date" name="fecha" style={inputStyle} onChange={handleChange} />
          </div>

          <div>
            <label style={labelStyle}>Tipo de violencia *</label>
            <select name="violencia" style={inputStyle} onChange={handleChange}>
              <option>Seleccione...</option>
            </select>
          </div>

          <div style={{ gridColumn: "1 / -1" }}>
            <label style={labelStyle}>Hechos *</label>
            <textarea name="hechos" style={{ ...inputStyle, height: 80 }} onChange={handleChange}/>
          </div>

          <div style={{ gridColumn: "1 / -1" }}>
            <label style={labelStyle}>Pretensiones *</label>
            <textarea name="pretensiones" style={{ ...inputStyle, height: 80 }} onChange={handleChange}/>
          </div>

          <div style={{ gridColumn: "1 / -1" }}>
            <label style={labelStyle}>Concepto jurídico del estudiante *</label>
            <textarea name="concepto" style={{ ...inputStyle, height: 80 }} onChange={handleChange}/>
          </div>

          <div>
            <label style={labelStyle}>Trámite *</label>
            <select name="tramite" style={inputStyle} onChange={handleChange}>
              <option>Seleccione...</option>
            </select>
          </div>

          <div style={{ gridColumn: "1 / -1" }}>
            <label style={labelStyle}>Observaciones</label>
            <textarea name="observaciones" style={{ ...inputStyle, height: 80 }} onChange={handleChange}/>
          </div>

        </div>
      </Seccion>

      {/* TIPO DE CONSULTA */}
      <Seccion titulo="Tipo de consulta">
        <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 16 }}>

          <div>
            <label style={labelStyle}>Sede de recepción *</label>
            <select style={inputStyle}><option>Sede principal</option></select>
          </div>

          <div>
            <label style={labelStyle}>Área *</label>
            <select style={inputStyle}><option>Seleccione...</option></select>
          </div>

          <div>
            <label style={labelStyle}>Tema *</label>
            <select style={inputStyle}><option>Seleccione...</option></select>
          </div>

          <div>
            <label style={labelStyle}>Tipo</label>
            <select style={inputStyle}><option>Seleccione...</option></select>
          </div>

        </div>
      </Seccion>

      {/* DOCUMENTACIÓN */}
      <Seccion titulo="Documentación">
        <div style={{ display: "flex", flexDirection: "column", gap: 16 }}>

          <label>
            <input type="checkbox" /> Entrega de documentos para la prestación del servicio
          </label>

          <div>
            <label style={labelStyle}>Documentos aportados</label>
            <textarea style={{ ...inputStyle, height: 80 }} />
          </div>

          <div>
            <label style={labelStyle}>Documentos solicitados</label>
            <textarea style={{ ...inputStyle, height: 80 }} />
          </div>

        </div>
      </Seccion>

      {/* ADMINISTRACIÓN */}
      <Seccion titulo="Administración">
        <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 16 }}>

          <div>
            <label style={labelStyle}>Asesor</label>
            <select style={inputStyle}><option>Seleccione...</option></select>
          </div>

          <div>
            <label style={labelStyle}>Monitor o Judicante</label>
            <select style={inputStyle}><option>Seleccione...</option></select>
          </div>

          <div>
            <label style={labelStyle}>Estudiante *</label>
            <select style={inputStyle}><option>Seleccione...</option></select>
          </div>

          <div>
            <label style={labelStyle}>Estado *</label>
            <select style={inputStyle}><option>Seleccione...</option></select>
          </div>

          <div>
            <label style={labelStyle}>Resultado de la consulta</label>
            <select style={inputStyle}><option>Seleccione...</option></select>
          </div>

        </div>

        <div style={{ marginTop: 20, textAlign: "right" }}>
          <button style={{
            background: "#f59e0b",
            color: "#fff",
            border: "none",
            padding: "10px 20px",
            borderRadius: 6,
            cursor: "pointer",
            fontWeight: 600
          }}>
            Guardar información
          </button>
        </div>
      </Seccion>

      {/* NOTA */}
      <div style={{
        background: "#fffbeb",
        border: "1px solid #fbbf24",
        borderRadius: 10,
        padding: 15,
        fontSize: 12,
        color: "#78350f"
      }}>
        <b>NOTA IMPORTANTE:</b> La violencia contra las mujeres NO SE CONCILIA...
      </div>

    </div>
  );
}