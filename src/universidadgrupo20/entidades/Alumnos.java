package universidadgrupo20.entidades;

import java.time.LocalDate;

public class Alumnos {
     
    private int idAlumno;
    private int dni;
    private String apellido;
    private String nombre;
    private LocalDate fecha;
    private boolean estado;
    

    public Alumnos() {
    }

    public Alumnos(int dni,String apellido, String nombre, LocalDate fecha, boolean estado) {
    
        this.dni=dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.fecha = fecha;
        this.estado = estado;
    }

    public Alumnos(int idAlumno, int dni,String apellido, String nombre, LocalDate fecha, boolean estado) {
        this.idAlumno = idAlumno;
        this.dni=dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.fecha = fecha;
        this.estado = estado;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    
    
    
    
    
    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Alumnos{" + "idAlumno=" + idAlumno + ", apellido=" + apellido + ", nombre=" + nombre + '}';
    }

}
