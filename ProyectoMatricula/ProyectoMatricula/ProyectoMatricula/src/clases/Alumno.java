package clases;

import java.io.Serializable;

public class Alumno implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // Atributos privados
    private int codAlumno;
    private String nombres;
    private String apellidos;
    private String dni;
    private int edad;
    private int celular;
    private int estado; // 0=registrado, 1=matriculado, 2=retirado
    
    // Nuevo campo para código personalizado
    private String codigoPersonalizado;
    
    // Variable estática para código correlativo
    private static int contadorCodigo = 202010000;
    
    // ========== CONSTRUCTORES ==========
    
    /**
     * Constructor completo original
     */
    public Alumno(int codAlumno, String nombres, String apellidos, 
                  String dni, int edad, int celular, int estado) {
        this.codAlumno = codAlumno;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.edad = edad;
        this.celular = celular;
        this.estado = estado;
        this.codigoPersonalizado = ""; // Inicializar vacío
    }
    
    /**
     * Constructor que genera código automático (para nuevos alumnos)
     */
    public Alumno(String nombres, String apellidos, String dni, int edad, int celular) {
        this(++contadorCodigo, nombres, apellidos, dni, edad, celular, 0);
    }
    
    /**
     * Constructor adicional para compatibilidad
     */
    public Alumno(int codAlumno, String nombres, String apellidos, String dni, int edad, int celular) {
        this(codAlumno, nombres, apellidos, dni, edad, celular, 0);
    }
    
    /**
     * Constructor básico para compatibilidad
     */
    public Alumno(int codAlumno, String nombres, String apellidos, String dni) {
        this(codAlumno, nombres, apellidos, dni, 0, 0, 0);
    }
    
    // ========== GETTERS Y SETTERS ==========
    
    public int getCodAlumno() {
        return codAlumno;
    }
    
    public void setCodAlumno(int codAlumno) {
        this.codAlumno = codAlumno;
    }
    
    public String getNombres() {
        return nombres;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
    public String getApellidos() {
        return apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public String getDni() {
        return dni;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
    }
    
    public int getEdad() {
        return edad;
    }
    
    public void setEdad(int edad) {
        this.edad = edad;
    }
    
    public int getCelular() {
        return celular;
    }
    
    public void setCelular(int celular) {
        this.celular = celular;
    }
    
    public int getEstado() {
        return estado;
    }
    
    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    // ========== CÓDIGO PERSONALIZADO ==========
    
    public String getCodigoPersonalizado() {
        return codigoPersonalizado;
    }
    
    public void setCodigoPersonalizado(String codigoPersonalizado) {
        this.codigoPersonalizado = codigoPersonalizado;
    }
    
    // ========== MÉTODOS ESTÁTICOS ==========
    
    public static int getContadorCodigo() {
        return contadorCodigo;
    }
    
    public static void setContadorCodigo(int contador) {
        contadorCodigo = contador;
    }
    
    // ========== MÉTODOS AUXILIARES ==========
    
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
    
    public String getEstadoTexto() {
        switch(estado) {
            case 0: return "Registrado";
            case 1: return "Matriculado";
            case 2: return "Retirado";
            default: return "Desconocido";
        }
    }
    
    /**
     * Retorna el código para mostrar (prioriza el personalizado)
     */
    public String getCodigoParaMostrar() {
        if (codigoPersonalizado != null && !codigoPersonalizado.isEmpty()) {
            return codigoPersonalizado;
        }
        return String.valueOf(codAlumno);
    }
    
    @Override
    public String toString() {
        if (codigoPersonalizado != null && !codigoPersonalizado.isEmpty()) {
            return String.format("[%s] %s %s - DNI: %s - Estado: %s", 
                codigoPersonalizado, nombres, apellidos, dni, getEstadoTexto());
        }
        return String.format("[%d] %s %s - DNI: %s - Estado: %s", 
            codAlumno, nombres, apellidos, dni, getEstadoTexto());
    }
    
    /**
     * Método para mostrar información resumida
     */
    public String toShortString() {
        if (codigoPersonalizado != null && !codigoPersonalizado.isEmpty()) {
            return codigoPersonalizado + " - " + getNombreCompleto();
        }
        return codAlumno + " - " + getNombreCompleto();
    }
    
    /**
     * Método para verificar si tiene código personalizado
     */
    public boolean tieneCodigoPersonalizado() {
        return codigoPersonalizado != null && !codigoPersonalizado.isEmpty();
    }
}