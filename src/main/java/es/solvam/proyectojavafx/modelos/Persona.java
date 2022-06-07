package es.solvam.proyectojavafx.modelos;

import java.time.LocalDate;

public class Persona {
    private String dni;
    private String nombre;
    private String telefono;
    private boolean sexo;
    private LocalDate fechaNacimiento;
    private String ocupacion;
    private boolean tecnologia;
    private boolean diseno;
    private boolean consultoria;
    private boolean formacion;

    private String imagen;

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isSexo() {
        return sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public boolean isTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(boolean tecnologia) {
        this.tecnologia = tecnologia;
    }

    public boolean isDiseno() {
        return diseno;
    }

    public void setDiseno(boolean diseno) {
        this.diseno = diseno;
    }

    public boolean isConsultoria() {
        return consultoria;
    }

    public void setConsultoria(boolean consultoria) {
        this.consultoria = consultoria;
    }

    public boolean isFormacion() {
        return formacion;
    }

    public void setFormacion(boolean formacion) {
        this.formacion = formacion;
    }
}
