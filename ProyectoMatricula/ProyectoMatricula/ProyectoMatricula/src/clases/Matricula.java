package clases;

import java.io.Serializable;

public class Matricula implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // Atributos privados
    private int numMatricula;  // Número correlativo (100001, 100002, ...)
    private int codAlumno;     // Código del alumno matriculado
    private int codCurso;      // Código del curso
    private String fecha;      // Fecha en formato "dd/MM/yyyy"
    private String hora;       // Hora en formato "HH:mm:ss"
    
    // Variable estática para número correlativo (CORREGIDO: 100000 en lugar de 1000)
    private static int contadorMatricula = 100000;
    
    // ========== CONSTRUCTORES ==========
    
    /**
     * Constructor completo
     * @param numMatricula Número de matrícula
     * @param codAlumno Código del alumno
     * @param codCurso Código del curso
     * @param fecha Fecha de matrícula
     * @param hora Hora de matrícula
     */
    public Matricula(int numMatricula, int codAlumno, int codCurso, 
                     String fecha, String hora) {
        this.numMatricula = numMatricula;
        this.codAlumno = codAlumno;
        this.codCurso = codCurso;
        this.fecha = fecha;
        this.hora = hora;
    }
    
    /**
     * Constructor que genera número automático
     * @param codAlumno Código del alumno
     * @param codCurso Código del curso
     * @param fecha Fecha de matrícula
     * @param hora Hora de matrícula
     */
    public Matricula(int codAlumno, int codCurso, String fecha, String hora) {
        this(++contadorMatricula, codAlumno, codCurso, fecha, hora);
    }
    
    // ========== GETTERS Y SETTERS ==========
    
    public int getNumMatricula() {
        return numMatricula;
    }
    
    public void setNumMatricula(int numMatricula) {
        this.numMatricula = numMatricula;
    }
    
    public int getCodAlumno() {
        return codAlumno;
    }
    
    public void setCodAlumno(int codAlumno) {
        this.codAlumno = codAlumno;
    }
    
    public int getCodCurso() {
        return codCurso;
    }
    
    public void setCodCurso(int codCurso) {
        this.codCurso = codCurso;
    }
    
    public String getFecha() {
        return fecha;
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public String getHora() {
        return hora;
    }
    
    public void setHora(String hora) {
        this.hora = hora;
    }
    
    // ========== MÉTODOS ESTÁTICOS ==========
    
    public static int getContadorMatricula() {
        return contadorMatricula;
    }
    
    public static void setContadorMatricula(int contador) {
        contadorMatricula = contador;
    }
    
    // ========== MÉTODOS AUXILIARES ==========
    
    /**
     * Retorna una representación resumida de la matrícula
     * @return String con información básica
     */
    public String toShortString() {
        return String.format("Matrícula N° %d - Alumno: %d - Curso: %d", 
            numMatricula, codAlumno, codCurso);
    }
    
    /**
     * Retorna información completa para reportes
     * @return String con información detallada
     */
    public String toReportString() {
        return String.format("N° %d | Alumno: %d | Curso: %d | Fecha: %s %s", 
            numMatricula, codAlumno, codCurso, fecha, hora);
    }
    
    /**
     * Retorna una representación completa de la matrícula
     * @return String con información de la matrícula
     */
    @Override
    public String toString() {
        return String.format("Matrícula N° %d - Alumno: %d - Curso: %d - %s %s", 
            numMatricula, codAlumno, codCurso, fecha, hora);
    }
}