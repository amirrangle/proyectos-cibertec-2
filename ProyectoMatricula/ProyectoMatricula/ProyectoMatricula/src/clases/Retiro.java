package clases;

import java.io.Serializable;

public class Retiro implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // Atributos privados
    private int numRetiro;      // Número correlativo (200001, 200002, ...)
    private int numMatricula;   // Número de matrícula asociada
    private String fecha;       // Fecha en formato "dd/MM/yyyy"
    private String hora;        // Hora en formato "HH:mm:ss"
    
    // Variable estática para número correlativo
    private static int contadorRetiro = 200000;
    
    /**
     * Constructor completo
     * @param numRetiro Número de retiro
     * @param numMatricula Número de matrícula asociada
     * @param fecha Fecha del retiro
     * @param hora Hora del retiro
     */
    public Retiro(int numRetiro, int numMatricula, String fecha, String hora) {
        this.numRetiro = numRetiro;
        this.numMatricula = numMatricula;
        this.fecha = fecha;
        this.hora = hora;
    }
    
    /**
     * Constructor que genera número automático
     * @param numMatricula Número de matrícula asociada
     * @param fecha Fecha del retiro
     * @param hora Hora del retiro
     */
    public Retiro(int numMatricula, String fecha, String hora) {
        this(++contadorRetiro, numMatricula, fecha, hora);
    }
    
    // ========== GETTERS Y SETTERS ==========
    
    public int getNumRetiro() {
        return numRetiro;
    }
    
    public void setNumRetiro(int numRetiro) {
        this.numRetiro = numRetiro;
    }
    
    public int getNumMatricula() {
        return numMatricula;
    }
    
    public void setNumMatricula(int numMatricula) {
        this.numMatricula = numMatricula;
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
    
    public static int getContadorRetiro() {
        return contadorRetiro;
    }
    
    public static void setContadorRetiro(int contador) {
        contadorRetiro = contador;
    }
    
    // ========== MÉTODOS AUXILIARES ==========
    
    /**
     * Retorna una representación resumida del retiro
     * @return String con información básica
     */
    public String toShortString() {
        return String.format("Retiro N° %d - Matrícula: %d", numRetiro, numMatricula);
    }
    
    /**
     * Retorna información completa para reportes
     * @return String con información detallada
     */
    public String toReportString() {
        return String.format("N° %d | Matrícula: %d | Fecha: %s %s", 
            numRetiro, numMatricula, fecha, hora);
    }
    
    /**
     * Retorna una representación completa del retiro
     * @return String con información del retiro
     */
    @Override
    public String toString() {
        return String.format("Retiro N° %d - Matrícula: %d - %s %s", 
            numRetiro, numMatricula, fecha, hora);
    }
}