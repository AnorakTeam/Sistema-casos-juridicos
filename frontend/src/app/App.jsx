import { useState } from "react";
import NuevaConsulta from "./NuevaConsulta";
import NuevoUsuario from "./NuevoUsuario";

// ── ICONS (inline SVGs to avoid external deps) ──────────────────────────────
const Icon = ({ d, size = 18 }) => (
  <svg width={size} height={size} viewBox="0 0 24 24" fill="none"
    stroke="currentColor" strokeWidth={2} strokeLinecap="round" strokeLinejoin="round">
    <path d={d} />
  </svg>
);
const icons = {
  home:       "M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z M9 22V12h6v10",
  tasks:      "M9 11l3 3L22 4 M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11",
  reception:  "M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2 M9 11a4 4 0 1 0 0-8 4 4 0 0 0 0 8 M23 21v-2a4 4 0 0 0-3-3.87 M16 3.13a4 4 0 0 1 0 7.75",
  consult:    "M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z",
  process:    "M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z M14 2v6h6",
  scale:      "M12 3v18 M3 9l9-6 9 6 M3 15l9 6 9-6",
  virtual:    "M18 8h1a4 4 0 0 1 0 8h-1 M2 8h16v9a4 4 0 0 1-4 4H6a4 4 0 0 1-4-4V8z M6 1v3 M10 1v3 M14 1v3",
  students:   "M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2 M9 11a4 4 0 1 0 0-8 4 4 0 0 0 0 8",
  advisors:   "M12 20h9 M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z",
  stats:      "M18 20V10 M12 20V4 M6 20v-6",
  formats:    "M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z M8 13h8 M8 17h5",
  trash:      "M3 6h18 M19 6l-1 14a2 2 0 0 1-2 2H8a2 2 0 0 1-2-2L5 6 M10 11v6 M14 11v6 M9 6V4h6v2",
  lex:        "M12 2a10 10 0 1 0 0 20 10 10 0 0 0 0-20z M12 8v4l3 3",
  chevron:    "M6 9l6 6 6-6",
  menu:       "M3 12h18 M3 6h18 M3 18h18",
  close:      "M18 6L6 18 M6 6l12 12",
  bell:       "M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9 M13.73 21a2 2 0 0 1-3.46 0",
  user:       "M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2 M12 11a4 4 0 1 0 0-8 4 4 0 0 0 0 8",
  plus:       "M12 5v14 M5 12h14",
  search:     "M11 17a6 6 0 1 0 0-12 6 6 0 0 0 0 12z M21 21l-4.35-4.35",
  logout:     "M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4 M16 17l5-5-5-5 M21 12H9",
  shield:     "M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z",
  book:       "M4 19.5A2.5 2.5 0 0 1 6.5 17H20 M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z",
};

// ── SIDEBAR DATA ─────────────────────────────────────────────────────────────
const navItems = [
  { label: "Inicio", icon: "home", path: "/inicio" },
  { label: "Tareas", icon: "tasks", path: "/tareas" },
  { label: "Recepción", icon: "reception", path: "/recepcion" },

  {
    label: "Nueva consulta",
    icon: "consult",
    path: "/nueva-consulta",
    children: [
      { label: "Consulta jurídica", path: "/nueva-consulta/juridica" },
      { label: "Agregar usuario", path: "/nueva-consulta/usuario" },
    ],
  },

  {
    label: "Consultas jurídicas",
    icon: "scale",
    path: "/consultas",
    children: [
      { label: "Mis consultas", path: "/consultas/mis-consultas" },
      { label: "Todas", path: "/consultas/todas" },
    ],
  },

  { label: "Procesos jurídicos", icon: "process", path: "/procesos" },
  { label: "Solicitudes virtuales", icon: "virtual", path: "/solicitudes" },

  {
    label: "Estudiantes",
    icon: "students",
    path: "/estudiantes",
    children: [
      { label: "Listado", path: "/estudiantes/listado" },
      { label: "Nuevo estudiante", path: "/estudiantes/nuevo" },
    ],
  },

  { label: "Asesores y monitores", icon: "advisors", path: "/asesores" },
  { label: "Estadísticas", icon: "stats", path: "/estadisticas" },
  { label: "Formatos y modelos", icon: "formats", path: "/formatos" },
  { label: "Eliminación", icon: "trash", path: "/eliminacion" },
  { label: "Leyex.info", icon: "lex", path: "/leyex" },
];

// ── SIDEBAR ──────────────────────────────────────────────────────────────────
function Sidebar({ collapsed, setCollapsed, activePath, setActivePath }) {
  const [openMenus, setOpenMenus] = useState({});

  const toggleMenu = (label) =>
    setOpenMenus((p) => ({ ...p, [label]: !p[label] }));

  return (
    <aside
      style={{
        width: collapsed ? 64 : 240,
        minHeight: "100vh",
        background: "linear-gradient(180deg,#8B0000 0%,#6B0000 60%,#3D0000 100%)",
        display: "flex",
        flexDirection: "column",
        transition: "width .25s cubic-bezier(.4,0,.2,1)",
        overflow: "hidden",
        boxShadow: "4px 0 20px rgba(0,0,0,.35)",
        position: "fixed",
        top: 0,
        left: 0,
        zIndex: 100,
        flexShrink: 0,
      }}
    >
      {/* Logo */}
      <div style={{
        display: "flex", alignItems: "center", gap: 10,
        padding: collapsed ? "20px 0" : "20px 16px",
        justifyContent: collapsed ? "center" : "flex-start",
        borderBottom: "1px solid rgba(255,255,255,.12)",
        minHeight: 70,
      }}>
        <div style={{
          width: 36, height: 36, borderRadius: "50%",
          background: "#fff", display: "flex", alignItems: "center",
          justifyContent: "center", flexShrink: 0,
        }}>
          <Icon d={icons.shield} size={20} />
        </div>
        {!collapsed && (
          <div>
            <div style={{ color: "#fff", fontWeight: 800, fontSize: 13, fontFamily: "'Georgia',serif", lineHeight: 1.1 }}>
              UFPS
            </div>
            <div style={{ color: "rgba(255,255,255,.7)", fontSize: 10, letterSpacing: 1 }}>
              CONSULTORIO JURÍDICO
            </div>
          </div>
        )}
      </div>

      {/* Nav */}
      <nav style={{ flex: 1, overflowY: "auto", overflowX: "hidden", padding: "8px 0" }}>
        {navItems.map((item) => {
          const isActive = activePath === item.path || activePath?.startsWith(item.path + "/");
          const isOpen = openMenus[item.label];
          const hasChildren = item.children?.length > 0;

          return (
            <div key={item.label}>
              <button
                onClick={() => {
                  if (hasChildren) { toggleMenu(item.label); if (collapsed) setCollapsed(false); }
                  else setActivePath(item.path);
                }}
                title={collapsed ? item.label : undefined}
                style={{
                  width: "100%", display: "flex", alignItems: "center",
                  gap: 10, padding: collapsed ? "10px 0" : "10px 16px",
                  justifyContent: collapsed ? "center" : "flex-start",
                  background: isActive ? "rgba(255,255,255,.18)" : "transparent",
                  border: "none", cursor: "pointer", color: "#fff",
                  borderLeft: isActive ? "3px solid #fff" : "3px solid transparent",
                  transition: "all .15s", fontSize: 13, fontFamily: "inherit",
                  textAlign: "left",
                }}
                onMouseEnter={e => { if (!isActive) e.currentTarget.style.background = "rgba(255,255,255,.08)"; }}
                onMouseLeave={e => { if (!isActive) e.currentTarget.style.background = "transparent"; }}
              >
                <span style={{ flexShrink: 0, opacity: .9 }}>
                  <Icon d={icons[item.icon]} size={16} />
                </span>
                {!collapsed && (
                  <>
                    <span style={{ flex: 1, fontWeight: isActive ? 600 : 400 }}>{item.label}</span>
                    {hasChildren && (
                      <span style={{ transition: "transform .2s", transform: isOpen ? "rotate(180deg)" : "rotate(0deg)", opacity: .7 }}>
                        <Icon d={icons.chevron} size={14} />
                      </span>
                    )}
                  </>
                )}
              </button>

              {/* Submenu */}
              {hasChildren && isOpen && !collapsed && (
                <div style={{ background: "rgba(0,0,0,.2)", borderLeft: "2px solid rgba(255,255,255,.15)", marginLeft: 20 }}>
                  {item.children.map((child) => (
                    <button
                      key={child.path}
                      onClick={() => setActivePath(child.path)}
                      style={{
                        width: "100%", display: "flex", alignItems: "center",
                        gap: 8, padding: "8px 16px", border: "none", cursor: "pointer",
                        background: activePath === child.path ? "rgba(255,255,255,.15)" : "transparent",
                        color: activePath === child.path ? "#fff" : "rgba(255,255,255,.7)",
                        fontSize: 12, fontFamily: "inherit", textAlign: "left",
                        transition: "all .15s",
                      }}
                      onMouseEnter={e => { e.currentTarget.style.color = "#fff"; e.currentTarget.style.background = "rgba(255,255,255,.08)"; }}
                      onMouseLeave={e => {
                        e.currentTarget.style.color = activePath === child.path ? "#fff" : "rgba(255,255,255,.7)";
                        e.currentTarget.style.background = activePath === child.path ? "rgba(255,255,255,.15)" : "transparent";
                      }}
                    >
                      <span style={{ width: 4, height: 4, borderRadius: "50%", background: "currentColor", flexShrink: 0 }} />
                      {child.label}
                    </button>
                  ))}
                </div>
              )}
            </div>
          );
        })}
      </nav>

      {/* Collapse toggle */}
      <button
        onClick={() => setCollapsed((p) => !p)}
        style={{
          background: "rgba(255,255,255,.1)", border: "none", cursor: "pointer",
          color: "#fff", padding: "12px", display: "flex", justifyContent: "center",
          borderTop: "1px solid rgba(255,255,255,.12)", transition: "background .15s",
        }}
        onMouseEnter={e => e.currentTarget.style.background = "rgba(255,255,255,.2)"}
        onMouseLeave={e => e.currentTarget.style.background = "rgba(255,255,255,.1)"}
      >
        <Icon d={collapsed ? icons.menu : icons.close} size={18} />
      </button>
    </aside>
  );
}

// ── TOPBAR ───────────────────────────────────────────────────────────────────
function Topbar({ sidebarWidth, activePath }) {
  const pageLabel = navItems
    .flatMap(i => [i, ...(i.children || [])])
    .find(i => i.path === activePath)?.label || "Inicio";

  return (
    <header style={{
      position: "fixed", top: 0, left: sidebarWidth, right: 0,
      height: 64, background: "#fff",
      boxShadow: "0 2px 12px rgba(0,0,0,.08)",
      display: "flex", alignItems: "center",
      padding: "0 28px", gap: 16, zIndex: 90,
      borderBottom: "3px solid #8B0000",
      transition: "left .25s cubic-bezier(.4,0,.2,1)",
    }}>
      {/* Breadcrumb */}
      <div style={{ flex: 1 }}>
        <div style={{ fontSize: 11, color: "#999", letterSpacing: .5, textTransform: "uppercase" }}>
          Consultorio Jurídico UFPS
        </div>
        <div style={{ fontSize: 16, fontWeight: 700, color: "#2d2d2d", fontFamily: "'Georgia',serif" }}>
          {pageLabel}
        </div>
      </div>

      {/* Search */}
      <div style={{
        display: "flex", alignItems: "center", gap: 8,
        background: "#f5f5f5", borderRadius: 8,
        padding: "6px 14px", border: "1px solid #e8e8e8",
      }}>
        <Icon d={icons.search} size={15} />
        <input placeholder="Buscar..."
          style={{ border: "none", background: "transparent", outline: "none", fontSize: 13, width: 180, color: "#555" }} />
      </div>

      {/* Actions */}
      <button style={{
        background: "#8B0000", color: "#fff", border: "none",
        borderRadius: 8, padding: "8px 16px", cursor: "pointer",
        fontSize: 12, fontWeight: 600, display: "flex", alignItems: "center", gap: 6,
      }}>
        <Icon d={icons.plus} size={14} /> Nueva Consulta
      </button>

      <button style={{ background: "none", border: "none", cursor: "pointer", color: "#666", position: "relative" }}>
        <Icon d={icons.bell} size={20} />
        <span style={{
          position: "absolute", top: -2, right: -2, width: 8, height: 8,
          background: "#8B0000", borderRadius: "50%", border: "2px solid #fff",
        }} />
      </button>

      {/* User */}
      <div style={{ display: "flex", alignItems: "center", gap: 8, cursor: "pointer" }}>
        <div style={{
          width: 36, height: 36, borderRadius: "50%",
          background: "linear-gradient(135deg,#8B0000,#c0392b)",
          display: "flex", alignItems: "center", justifyContent: "center",
          color: "#fff", fontWeight: 700, fontSize: 13,
        }}>LI</div>
        <div>
          <div style={{ fontSize: 12, fontWeight: 600, color: "#2d2d2d" }}>Ledy Idarraga</div>
          <div style={{ fontSize: 10, color: "#999" }}>Auxiliar Administrativo</div>
        </div>
      </div>
    </header>
  );
}

// ── STAT CARD ─────────────────────────────────────────────────────────────────
function StatCard({ label, value, icon, color, delta }) {
  return (
    <div style={{
      background: "#fff", borderRadius: 12, padding: "20px 24px",
      boxShadow: "0 2px 16px rgba(0,0,0,.07)",
      borderTop: `4px solid ${color}`,
      display: "flex", flexDirection: "column", gap: 8,
    }}>
      <div style={{ display: "flex", alignItems: "flex-start", justifyContent: "space-between" }}>
        <div>
          <div style={{ fontSize: 11, color: "#999", textTransform: "uppercase", letterSpacing: .6 }}>{label}</div>
          <div style={{ fontSize: 32, fontWeight: 800, color: "#1a1a1a", marginTop: 4 }}>{value}</div>
        </div>
        <div style={{
          width: 44, height: 44, borderRadius: 10,
          background: color + "18",
          display: "flex", alignItems: "center", justifyContent: "center", color,
        }}>
          <Icon d={icons[icon]} size={20} />
        </div>
      </div>
      {delta && <div style={{ fontSize: 12, color: delta > 0 ? "#16a34a" : "#dc2626" }}>
        {delta > 0 ? "▲" : "▼"} {Math.abs(delta)} este mes
      </div>}
    </div>
  );
}

// ── RECENT TABLE ──────────────────────────────────────────────────────────────
const recentCases = [
  { id: "CJ-2026-0041", client: "Ana Rodríguez P.", type: "Derecho de familia", student: "Carlos M.", status: "Activo", date: "15/04/2026" },
  { id: "CJ-2026-0040", client: "Luis Hernández G.", type: "Derecho laboral", student: "María F.", status: "Pendiente", date: "14/04/2026" },
  { id: "CJ-2026-0039", client: "Sandra Mora V.", type: "Derecho civil", student: "Juan P.", status: "Cerrado", date: "13/04/2026" },
  { id: "CJ-2026-0038", client: "Pedro Jiménez L.", type: "Derecho penal", student: "Laura S.", status: "Activo", date: "12/04/2026" },
  { id: "CJ-2026-0037", client: "Gloria Suárez T.", type: "Derecho civil", student: "Diego R.", status: "Activo", date: "11/04/2026" },
];

const statusColors = {
  Activo:    { bg: "#dcfce7", color: "#16a34a" },
  Pendiente: { bg: "#fef9c3", color: "#ca8a04" },
  Cerrado:   { bg: "#f3f4f6", color: "#6b7280" },
};

function RecentTable() {
  return (
    <div style={{ background: "#fff", borderRadius: 12, boxShadow: "0 2px 16px rgba(0,0,0,.07)", overflow: "hidden" }}>
      <div style={{
        padding: "18px 24px", borderBottom: "1px solid #f0f0f0",
        display: "flex", alignItems: "center", justifyContent: "space-between",
      }}>
        <div style={{ fontWeight: 700, fontSize: 15, color: "#1a1a1a" }}>Consultas recientes</div>
        <button style={{ background: "none", border: "1px solid #e0e0e0", borderRadius: 6, padding: "5px 14px", cursor: "pointer", fontSize: 12, color: "#666" }}>
          Ver todas
        </button>
      </div>
      <table style={{ width: "100%", borderCollapse: "collapse", fontSize: 13 }}>
        <thead>
          <tr style={{ background: "#fafafa" }}>
            {["Expediente","Cliente","Tipo","Estudiante","Estado","Fecha"].map(h => (
              <th key={h} style={{ padding: "10px 20px", textAlign: "left", fontWeight: 600, color: "#888", fontSize: 11, textTransform: "uppercase", letterSpacing: .5 }}>{h}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {recentCases.map((c, i) => (
            <tr key={c.id} style={{ borderTop: "1px solid #f5f5f5", background: i % 2 === 0 ? "#fff" : "#fafafa" }}>
              <td style={{ padding: "12px 20px", fontWeight: 600, color: "#8B0000" }}>{c.id}</td>
              <td style={{ padding: "12px 20px", color: "#333" }}>{c.client}</td>
              <td style={{ padding: "12px 20px", color: "#666" }}>{c.type}</td>
              <td style={{ padding: "12px 20px", color: "#666" }}>{c.student}</td>
              <td style={{ padding: "12px 20px" }}>
                <span style={{
                  padding: "3px 10px", borderRadius: 20, fontSize: 11, fontWeight: 600,
                  ...statusColors[c.status],
                }}>{c.status}</span>
              </td>
              <td style={{ padding: "12px 20px", color: "#888" }}>{c.date}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

// ── QUICK ACTIONS ─────────────────────────────────────────────────────────────
const quickActions = [
  { label: "Nueva consulta",      icon: "consult",  color: "#8B0000", desc: "Registrar nueva consulta jurídica" },
  { label: "Agregar usuario",     icon: "user",     color: "#1d4ed8", desc: "Registrar parte o usuario nuevo" },
  { label: "Nuevo proceso",       icon: "process",  color: "#7c3aed", desc: "Crear proceso jurídico" },
  { label: "Ver estadísticas",    icon: "stats",    color: "#0891b2", desc: "Consultar métricas del consultorio" },
];

function QuickActions({ setActivePath }) {
  return (
    <div style={{ display: "grid", gridTemplateColumns: "repeat(2,1fr)", gap: 14 }}>
      {quickActions.map(a => (
        <button
          key={a.label}
          onClick={() => {}}
          style={{
            background: "#fff", border: "1px solid #ececec", borderRadius: 12,
            padding: "16px", cursor: "pointer", textAlign: "left",
            boxShadow: "0 1px 6px rgba(0,0,0,.05)", transition: "all .15s",
          }}
          onMouseEnter={e => { e.currentTarget.style.boxShadow = "0 4px 20px rgba(0,0,0,.12)"; e.currentTarget.style.transform = "translateY(-2px)"; }}
          onMouseLeave={e => { e.currentTarget.style.boxShadow = "0 1px 6px rgba(0,0,0,.05)"; e.currentTarget.style.transform = "none"; }}
        >
          <div style={{
            width: 38, height: 38, borderRadius: 9,
            background: a.color + "18", color: a.color,
            display: "flex", alignItems: "center", justifyContent: "center", marginBottom: 10,
          }}>
            <Icon d={icons[a.icon]} size={18} />
          </div>
          <div style={{ fontWeight: 700, fontSize: 13, color: "#1a1a1a" }}>{a.label}</div>
          <div style={{ fontSize: 11, color: "#999", marginTop: 3 }}>{a.desc}</div>
        </button>
      ))}
    </div>
  );
}

export default function UFPSLegal() {
  const [collapsed, setCollapsed] = useState(false);
  const [activePath, setActivePath] = useState("/inicio");

  const sidebarWidth = collapsed ? 64 : 240;

  function renderContent() {
  switch (activePath) {

    // 📌 FORMULARIO CONSULTA JURÍDICA
    case "/nueva-consulta/juridica":
      return <NuevaConsulta />;

    // 📌 FORMULARIO NUEVO USUARIO
    case "/nueva-consulta/usuario":
      return <NuevoUsuario />;

    // 📌 DASHBOARD (INICIO)
    case "/inicio":
    default:
      return (
        <>

          {/* Welcome banner */}
          <div style={{
            background: "linear-gradient(135deg,#8B0000 0%,#c0392b 100%)",
            borderRadius: 16,
            padding: "28px 32px",
            marginBottom: 28,
            color: "#fff"
          }}>
            <div>
              <div style={{ fontSize: 11, opacity: .75 }}>
                Bienvenida al sistema
              </div>
              <div style={{ fontSize: 22, fontWeight: 800 }}>
                Consultorio Jurídico UFPS
              </div>
            </div>
          </div>

          {/* Stats */}
          <div style={{
            display: "grid",
            gridTemplateColumns: "repeat(4,1fr)",
            gap: 18,
            marginBottom: 28
          }}>
            <StatCard label="Consultas activas" value="127" icon="consult" color="#8B0000" />
            <StatCard label="Procesos en curso" value="43" icon="process" color="#7c3aed" />
            <StatCard label="Usuarios registrados" value="892" icon="user" color="#1d4ed8" />
            <StatCard label="Estudiantes activos" value="38" icon="students" color="#0891b2" />
          </div>

          {/* Bottom grid */}
          <div style={{
            display: "grid",
            gridTemplateColumns: "1fr 320px",
            gap: 24
          }}>
            <RecentTable />

            <div style={{ display: "flex", flexDirection: "column", gap: 20 }}>
              
              <QuickActions setActivePath={setActivePath} />

              {/* Nota importante */}
              <div style={{
                background: "#fffbeb",
                border: "1px solid #fbbf24",
                borderRadius: 10,
                padding: "14px 16px"
              }}>
                <div style={{
                  fontSize: 12,
                  fontWeight: 700,
                  color: "#92400e",
                  marginBottom: 4
                }}>
                  ⚠ NOTA IMPORTANTE
                </div>

                <div style={{
                  fontSize: 11,
                  color: "#78350f",
                  lineHeight: 1.5
                }}>
                  La violencia contra las mujeres NO SE CONCILIA y tienen derecho a no ser confrontadas con el(los) agresor(es).
                </div>
              </div>

            </div>
          </div>

        </>
      );
  }
}

  return (
    <div style={{
      fontFamily: "'Segoe UI', 'Helvetica Neue', sans-serif",
      background: "#f7f7f8",
      minHeight: "100vh"
    }}>
      <Sidebar
        collapsed={collapsed}
        setCollapsed={setCollapsed}
        activePath={activePath}
        setActivePath={setActivePath}
      />

      <Topbar sidebarWidth={sidebarWidth} activePath={activePath} />

      <main
  style={{
    marginLeft: sidebarWidth,
    marginTop: 64,
    padding: "32px",
    transition: "margin-left .25s cubic-bezier(.4,0,.2,1)",
    minHeight: "calc(100vh - 64px)",
  }}
>
  {renderContent()}
</main>
    </div>
  );
}