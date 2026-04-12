package co.edu.ufps.legal_cases.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PersonaDTO {

    private Long id;

    // Informacion basica
    @NotBlank(message = "El tipo de usuario es obligatorio")
    @Size(max = 20, message = "El tipo de usuario no puede superar los 20 caracteres")
    private String tipoUsuario;

    @NotBlank(message = "El tipo de documento es obligatorio")
    @Size(max = 10, message = "El tipo de documento no puede superar los 10 caracteres")
    private String tipoDocumento;

    @NotBlank(message = "El numero de documento es obligatorio")
    @Size(max = 30, message = "El numero de documento no puede superar los 30 caracteres")
    private String numeroDocumento;

    @NotNull(message = "La fecha de expedicion es obligatoria")
    private LocalDate fechaExpedicion;

    @NotBlank(message = "La ciudad de expedicion es obligatoria")
    @Size(max = 100, message = "La ciudad de expedicion no puede superar los 100 caracteres")
    private String ciudadExpedicion;

    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 100, message = "Los nombres no pueden superar los 100 caracteres")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100, message = "Los apellidos no pueden superar los 100 caracteres")
    private String apellidos;

    @NotBlank(message = "El nombre identitario es obligatorio")
    @Size(max = 100, message = "El nombre identitario no puede superar los 100 caracteres")
    private String nombreIdentitario;

    @NotBlank(message = "El pronombre es obligatorio")
    @Size(max = 50, message = "El pronombre no puede superar los 50 caracteres")
    private String pronombre;

    @NotBlank(message = "El sexo es obligatorio")
    @Size(max = 20, message = "El sexo no puede superar los 20 caracteres")
    private String sexo;

    @NotBlank(message = "El genero es obligatorio")
    @Size(max = 20, message = "El genero no puede superar los 20 caracteres")
    private String genero;

    @NotBlank(message = "La orientacion sexual es obligatoria")
    @Size(max = 50, message = "La orientacion sexual no puede superar los 50 caracteres")
    private String orientacionSexual;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @Size(max = 30, message = "El telefono no puede superar los 30 caracteres")
    private String telefono;

    @Email(message = "El correo no tiene un formato valido")
    @Size(max = 120, message = "El correo no puede superar los 120 caracteres")
    private String correo;

    @NotBlank(message = "La nacionalidad es obligatoria")
    @Size(max = 50, message = "La nacionalidad no puede superar los 50 caracteres")
    private String nacionalidad;

    @NotBlank(message = "El estado civil es obligatorio")
    @Size(max = 30, message = "El estado civil no puede superar los 30 caracteres")
    private String estadoCivil;

    @NotBlank(message = "La escolaridad es obligatoria")
    @Size(max = 100, message = "La escolaridad no puede superar los 100 caracteres")
    private String escolaridad;

    @NotBlank(message = "El grupo etnico es obligatorio")
    @Size(max = 100, message = "El grupo etnico no puede superar los 100 caracteres")
    private String grupoEtnico;

    @NotBlank(message = "La condicion actual es obligatoria")
    @Size(max = 100, message = "La condicion actual no puede superar los 100 caracteres")
    private String condicionActual;

    @NotNull(message = "Debe indicar si sabe leer y escribir")
    private Boolean sabeLeerEscribir;

    @NotBlank(message = "La discapacidad es obligatoria")
    @Size(max = 100, message = "La discapacidad no puede superar los 100 caracteres")
    private String discapacidad;

    @NotBlank(message = "La caracterizacion PCD es obligatoria")
    @Size(max = 150, message = "La caracterizacion PCD no puede superar los 150 caracteres")
    private String caracterizacionPcd;

    @NotNull(message = "Debe indicar si necesita ajuste PCD")
    private Boolean necesitaAjustePcd;

    // Informacion de vivienda
    @NotBlank(message = "El departamento es obligatorio")
    @Size(max = 100, message = "El departamento no puede superar los 100 caracteres")
    private String departamento;

    @NotBlank(message = "El municipio es obligatorio")
    @Size(max = 100, message = "El municipio no puede superar los 100 caracteres")
    private String municipio;

    @NotBlank(message = "El barrio es obligatorio")
    @Size(max = 100, message = "El barrio no puede superar los 100 caracteres")
    private String barrio;

    @NotBlank(message = "La direccion es obligatoria")
    @Size(max = 150, message = "La direccion no puede superar los 150 caracteres")
    private String direccion;

    @NotBlank(message = "La comuna es obligatoria")
    @Size(max = 100, message = "La comuna no puede superar los 100 caracteres")
    private String comuna;

    @NotBlank(message = "La localidad es obligatoria")
    @Size(max = 100, message = "La localidad no puede superar los 100 caracteres")
    private String localidad;

    @NotNull(message = "El estrato es obligatorio")
    @Min(value = 0, message = "El estrato no puede ser negativo")
    private Integer estrato;

    @NotBlank(message = "El tipo de vivienda es obligatorio")
    @Size(max = 100, message = "El tipo de vivienda no puede superar los 100 caracteres")
    private String tipoVivienda;

    @NotBlank(message = "La zona es obligatoria")
    @Size(max = 50, message = "La zona no puede superar los 50 caracteres")
    private String zona;

    @NotBlank(message = "La tenencia es obligatoria")
    @Size(max = 100, message = "La tenencia no puede superar los 100 caracteres")
    private String tenencia;

    @NotNull(message = "El numero de personas a cargo es obligatorio")
    @Min(value = 0, message = "El numero de personas a cargo no puede ser negativo")
    private Integer numeroPersonasACargo;

    @NotNull(message = "Debe indicar si tiene ingresos adicionales")
    private Boolean ingresosAdicionales;

    @NotNull(message = "Debe indicar si cuenta con energia electrica")
    private Boolean energiaElectrica;

    @NotNull(message = "Debe indicar si cuenta con acueducto")
    private Boolean acueducto;

    @NotNull(message = "Debe indicar si cuenta con alcantarillado")
    private Boolean alcantarillado;

    // Aspectos economicos
    @NotBlank(message = "La ocupacion es obligatoria")
    @Size(max = 100, message = "La ocupacion no puede superar los 100 caracteres")
    private String ocupacion;

    @NotBlank(message = "La empresa es obligatoria")
    @Size(max = 150, message = "La empresa no puede superar los 150 caracteres")
    private String empresa;

    @NotNull(message = "El salario es obligatorio")
    @Min(value = 0, message = "El salario no puede ser negativo")
    private Integer salario;

    @NotBlank(message = "El cargo es obligatorio")
    @Size(max = 100, message = "El cargo no puede superar los 100 caracteres")
    private String cargo;

    @NotBlank(message = "La direccion de la empresa es obligatoria")
    @Size(max = 150, message = "La direccion de la empresa no puede superar los 150 caracteres")
    private String direccionEmpresa;

    @NotBlank(message = "El telefono de la empresa es obligatorio")
    @Size(max = 30, message = "El telefono de la empresa no puede superar los 30 caracteres")
    private String telefonoEmpresa;

    // Datos del acudiente
    @Size(max = 150, message = "El nombre del acudiente no puede superar los 150 caracteres")
    private String nombreCompletoAcudiente;

    @Size(max = 100, message = "La relacion del acudiente no puede superar los 100 caracteres")
    private String relacionAcudiente;

    @Size(max = 30, message = "El telefono del acudiente no puede superar los 30 caracteres")
    private String telefonoAcudiente;

    @Email(message = "El correo del acudiente no tiene un formato valido")
    @Size(max = 120, message = "El correo del acudiente no puede superar los 120 caracteres")
    private String correoAcudiente;

    @Size(max = 150, message = "La direccion del acudiente no puede superar los 150 caracteres")
    private String direccionAcudiente;

    // Informacion del servicio
    @NotBlank(message = "Debe indicar como se entero del servicio")
    @Size(max = 150, message = "El campo como se entero no puede superar los 150 caracteres")
    private String comoSeEntero;

    @NotBlank(message = "La relacion con la universidad es obligatoria")
    @Size(max = 150, message = "La relacion con la universidad no puede superar los 150 caracteres")
    private String relacionConUniversidad;

    public PersonaDTO() {
    }

    public PersonaDTO(
            Long id,
            String tipoUsuario,
            String tipoDocumento,
            String numeroDocumento,
            LocalDate fechaExpedicion,
            String ciudadExpedicion,
            String nombres,
            String apellidos,
            String nombreIdentitario,
            String pronombre,
            String sexo,
            String genero,
            String orientacionSexual,
            LocalDate fechaNacimiento,
            String telefono,
            String correo,
            String nacionalidad,
            String estadoCivil,
            String escolaridad,
            String grupoEtnico,
            String condicionActual,
            Boolean sabeLeerEscribir,
            String discapacidad,
            String caracterizacionPcd,
            Boolean necesitaAjustePcd,
            String departamento,
            String municipio,
            String barrio,
            String direccion,
            String comuna,
            String localidad,
            Integer estrato,
            String tipoVivienda,
            String zona,
            String tenencia,
            Integer numeroPersonasACargo,
            Boolean ingresosAdicionales,
            Boolean energiaElectrica,
            Boolean acueducto,
            Boolean alcantarillado,
            String ocupacion,
            String empresa,
            Integer salario,
            String cargo,
            String direccionEmpresa,
            String telefonoEmpresa,
            String nombreCompletoAcudiente,
            String relacionAcudiente,
            String telefonoAcudiente,
            String correoAcudiente,
            String direccionAcudiente,
            String comoSeEntero,
            String relacionConUniversidad
    ) {
        this.id = id;
        this.tipoUsuario = tipoUsuario;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.fechaExpedicion = fechaExpedicion;
        this.ciudadExpedicion = ciudadExpedicion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.nombreIdentitario = nombreIdentitario;
        this.pronombre = pronombre;
        this.sexo = sexo;
        this.genero = genero;
        this.orientacionSexual = orientacionSexual;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correo = correo;
        this.nacionalidad = nacionalidad;
        this.estadoCivil = estadoCivil;
        this.escolaridad = escolaridad;
        this.grupoEtnico = grupoEtnico;
        this.condicionActual = condicionActual;
        this.sabeLeerEscribir = sabeLeerEscribir;
        this.discapacidad = discapacidad;
        this.caracterizacionPcd = caracterizacionPcd;
        this.necesitaAjustePcd = necesitaAjustePcd;
        this.departamento = departamento;
        this.municipio = municipio;
        this.barrio = barrio;
        this.direccion = direccion;
        this.comuna = comuna;
        this.localidad = localidad;
        this.estrato = estrato;
        this.tipoVivienda = tipoVivienda;
        this.zona = zona;
        this.tenencia = tenencia;
        this.numeroPersonasACargo = numeroPersonasACargo;
        this.ingresosAdicionales = ingresosAdicionales;
        this.energiaElectrica = energiaElectrica;
        this.acueducto = acueducto;
        this.alcantarillado = alcantarillado;
        this.ocupacion = ocupacion;
        this.empresa = empresa;
        this.salario = salario;
        this.cargo = cargo;
        this.direccionEmpresa = direccionEmpresa;
        this.telefonoEmpresa = telefonoEmpresa;
        this.nombreCompletoAcudiente = nombreCompletoAcudiente;
        this.relacionAcudiente = relacionAcudiente;
        this.telefonoAcudiente = telefonoAcudiente;
        this.correoAcudiente = correoAcudiente;
        this.direccionAcudiente = direccionAcudiente;
        this.comoSeEntero = comoSeEntero;
        this.relacionConUniversidad = relacionConUniversidad;
    }

    public Long getId() {
        return id;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public LocalDate getFechaExpedicion() {
        return fechaExpedicion;
    }

    public String getCiudadExpedicion() {
        return ciudadExpedicion;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getNombreIdentitario() {
        return nombreIdentitario;
    }

    public String getPronombre() {
        return pronombre;
    }

    public String getSexo() {
        return sexo;
    }

    public String getGenero() {
        return genero;
    }

    public String getOrientacionSexual() {
        return orientacionSexual;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public String getEscolaridad() {
        return escolaridad;
    }

    public String getGrupoEtnico() {
        return grupoEtnico;
    }

    public String getCondicionActual() {
        return condicionActual;
    }

    public Boolean getSabeLeerEscribir() {
        return sabeLeerEscribir;
    }

    public String getDiscapacidad() {
        return discapacidad;
    }

    public String getCaracterizacionPcd() {
        return caracterizacionPcd;
    }

    public Boolean getNecesitaAjustePcd() {
        return necesitaAjustePcd;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getBarrio() {
        return barrio;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getComuna() {
        return comuna;
    }

    public String getLocalidad() {
        return localidad;
    }

    public Integer getEstrato() {
        return estrato;
    }

    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public String getZona() {
        return zona;
    }

    public String getTenencia() {
        return tenencia;
    }

    public Integer getNumeroPersonasACargo() {
        return numeroPersonasACargo;
    }

    public Boolean getIngresosAdicionales() {
        return ingresosAdicionales;
    }

    public Boolean getEnergiaElectrica() {
        return energiaElectrica;
    }

    public Boolean getAcueducto() {
        return acueducto;
    }

    public Boolean getAlcantarillado() {
        return alcantarillado;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public String getEmpresa() {
        return empresa;
    }

    public Integer getSalario() {
        return salario;
    }

    public String getCargo() {
        return cargo;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public String getTelefonoEmpresa() {
        return telefonoEmpresa;
    }

    public String getNombreCompletoAcudiente() {
        return nombreCompletoAcudiente;
    }

    public String getRelacionAcudiente() {
        return relacionAcudiente;
    }

    public String getTelefonoAcudiente() {
        return telefonoAcudiente;
    }

    public String getCorreoAcudiente() {
        return correoAcudiente;
    }

    public String getDireccionAcudiente() {
        return direccionAcudiente;
    }

    public String getComoSeEntero() {
        return comoSeEntero;
    }

    public String getRelacionConUniversidad() {
        return relacionConUniversidad;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public void setFechaExpedicion(LocalDate fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    public void setCiudadExpedicion(String ciudadExpedicion) {
        this.ciudadExpedicion = ciudadExpedicion;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setNombreIdentitario(String nombreIdentitario) {
        this.nombreIdentitario = nombreIdentitario;
    }

    public void setPronombre(String pronombre) {
        this.pronombre = pronombre;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setOrientacionSexual(String orientacionSexual) {
        this.orientacionSexual = orientacionSexual;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public void setEscolaridad(String escolaridad) {
        this.escolaridad = escolaridad;
    }

    public void setGrupoEtnico(String grupoEtnico) {
        this.grupoEtnico = grupoEtnico;
    }

    public void setCondicionActual(String condicionActual) {
        this.condicionActual = condicionActual;
    }

    public void setSabeLeerEscribir(Boolean sabeLeerEscribir) {
        this.sabeLeerEscribir = sabeLeerEscribir;
    }

    public void setDiscapacidad(String discapacidad) {
        this.discapacidad = discapacidad;
    }

    public void setCaracterizacionPcd(String caracterizacionPcd) {
        this.caracterizacionPcd = caracterizacionPcd;
    }

    public void setNecesitaAjustePcd(Boolean necesitaAjustePcd) {
        this.necesitaAjustePcd = necesitaAjustePcd;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void setEstrato(Integer estrato) {
        this.estrato = estrato;
    }

    public void setTipoVivienda(String tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public void setTenencia(String tenencia) {
        this.tenencia = tenencia;
    }

    public void setNumeroPersonasACargo(Integer numeroPersonasACargo) {
        this.numeroPersonasACargo = numeroPersonasACargo;
    }

    public void setIngresosAdicionales(Boolean ingresosAdicionales) {
        this.ingresosAdicionales = ingresosAdicionales;
    }

    public void setEnergiaElectrica(Boolean energiaElectrica) {
        this.energiaElectrica = energiaElectrica;
    }

    public void setAcueducto(Boolean acueducto) {
        this.acueducto = acueducto;
    }

    public void setAlcantarillado(Boolean alcantarillado) {
        this.alcantarillado = alcantarillado;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public void setSalario(Integer salario) {
        this.salario = salario;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public void setTelefonoEmpresa(String telefonoEmpresa) {
        this.telefonoEmpresa = telefonoEmpresa;
    }

    public void setNombreCompletoAcudiente(String nombreCompletoAcudiente) {
        this.nombreCompletoAcudiente = nombreCompletoAcudiente;
    }

    public void setRelacionAcudiente(String relacionAcudiente) {
        this.relacionAcudiente = relacionAcudiente;
    }

    public void setTelefonoAcudiente(String telefonoAcudiente) {
        this.telefonoAcudiente = telefonoAcudiente;
    }

    public void setCorreoAcudiente(String correoAcudiente) {
        this.correoAcudiente = correoAcudiente;
    }

    public void setDireccionAcudiente(String direccionAcudiente) {
        this.direccionAcudiente = direccionAcudiente;
    }

    public void setComoSeEntero(String comoSeEntero) {
        this.comoSeEntero = comoSeEntero;
    }

    public void setRelacionConUniversidad(String relacionConUniversidad) {
        this.relacionConUniversidad = relacionConUniversidad;
    }
}