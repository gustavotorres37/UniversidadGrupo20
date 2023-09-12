
package universidadgrupo20.entidades;


public class Inscripcion {
    
    private int idInscripcion;
    private Alumnos idAlumnos;
    private Materia materia;
    private double nota;

    public Inscripcion() {
    }

    public Inscripcion(Alumnos idAlumnos, Materia materia, double nota) {
        this.idAlumnos = idAlumnos;
        this.materia = materia;
        this.nota = nota;
    }

    public Inscripcion(int idInscripcion, Alumnos idAlumnos, Materia materia, double nota) {
        this.idInscripcion = idInscripcion;
        this.idAlumnos = idAlumnos;
        this.materia = materia;
        this.nota = nota;
    }

    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public Alumnos getIdAlumnos() {
        return idAlumnos;
    }

    public void setIdAlumnos(Alumnos idAlumnos) {
        this.idAlumnos = idAlumnos;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
    
    
    
    
    
    
}
