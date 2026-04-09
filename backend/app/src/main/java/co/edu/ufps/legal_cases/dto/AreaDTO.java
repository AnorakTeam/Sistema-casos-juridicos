package co.edu.ufps.legal_cases.dto;

public class AreaDTO {
    private Long id;
    private String nombre;

    public AreaDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "AreaDTO [id=" + id + 
                ", nombre=" + nombre + 
                "]";
    }   
}

