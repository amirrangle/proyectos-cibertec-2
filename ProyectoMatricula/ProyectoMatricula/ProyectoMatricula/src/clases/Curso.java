package clases;

import java.io.Serializable;

public class Curso implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // Atributos privados
    private int codCurso;      // Código de 4 dígitos (1000-9999)
    private String asignatura; // Nombre del curso
    private int ciclo;         // 0=primero, 1=segundo, ..., 5=sexto
    private int creditos;      // Número de créditos (1-10)
    private int horas;         // Horas semanales (1-20)

    // ========== CONSTRUCTOR ==========
    
    /**
     * Constructor completo
     */
    public Curso(int codCurso, String asignatura, int ciclo, int creditos, int horas) {
        this.codCurso = codCurso;
        this.asignatura = asignatura;
        this.ciclo = ciclo;
        this.creditos = creditos;
        this.horas = horas;
    }
    
    // ========== GETTERS Y SETTERS ==========
    
    public int getCodCurso() {
        return codCurso;
    }
    
    public void setCodCurso(int codCurso) {
        this.codCurso = codCurso;
    }
    
    public String getAsignatura() {
        return asignatura;
    }
    
    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }
    
    public int getCiclo() {
        return ciclo;
    }
    
    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }
    
    public int getCreditos() {
        return creditos;
    }
    
    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }
    
    public int getHoras() {
        return horas;
    }
    
    public void setHoras(int horas) {
        this.horas = horas;
    }
    
    // ========== MÉTODOS AUXILIARES ==========
    
    /**
     * Retorna el nombre del ciclo en formato texto
     * @return Nombre del ciclo (Primero, Segundo, etc.)
     */
    public String getCicloTexto() {
        String[] ciclos = {"Primero", "Segundo", "Tercero", "Cuarto", "Quinto", "Sexto"};
        if(ciclo >= 0 && ciclo < ciclos.length)
            return ciclos[ciclo];
        return "Desconocido";
    }
    
    /**
     * Retorna el número del ciclo con símbolo de grado
     * @return Número del ciclo (1°, 2°, etc.)
     */
    public String getCicloNumero() {
        return (ciclo + 1) + "°";
    }
    
    /**
     * Retorna información básica del curso
     * @return String con código y asignatura
     */
    public String toShortString() {
        return String.format("%04d - %s", codCurso, asignatura);
    }
    
    /**
     * Retorna información completa del curso
     * @return String con información detallada
     */
    public String toLongString() {
        return String.format("[%04d] %s - Ciclo %s (%d créditos, %d horas)", 
            codCurso, asignatura, getCicloTexto(), creditos, horas);
    }
    
    /**
     * Retorna información para combobox
     * @return String formateada para displays
     */
    public String toComboString() {
        return String.format("[%04d] %s - %s", codCurso, asignatura, getCicloTexto());
    }
    
    /**
     * Verifica si el curso pertenece a un ciclo específico
     * @param ciclo Ciclo a verificar
     * @return true si pertenece al ciclo
     */
    public boolean esDelCiclo(int ciclo) {
        return this.ciclo == ciclo;
    }
    
    /**
     * Retorna una representación completa del curso
     * @return String con información del curso
     */
    @Override
    public String toString() {
        return toLongString();
    }
}