package arreglos;

import java.util.ArrayList;
import java.io.*;

import clases.Alumno;
import clases.Matricula;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase ArregloMatriculas - Gestiona la colección de matrículas
 * Características:
 * - Usa ArrayList para almacenamiento dinámico
 * - Valida matrícula única por alumno
 * - Persistencia en archivo matriculas.dat
 * - Restaura contador de matrículas al cargar
 * - Generación automática de matrículas iniciales
 * @author CIBERTEC
 * @version 2.0
 */
public class ArregloMatriculas {
    
    // Atributo privado
    private ArrayList<Matricula> lista;
    private String archivo = "matriculas.dat";
    
    /**
     * Constructor - Inicializa el ArrayList y carga datos del archivo
     * Genera matrículas automáticamente si no hay datos
     */
    public ArregloMatriculas() {
        lista = new ArrayList<Matricula>();
        cargarArchivo();
        
        // Generar matrículas automáticamente si no hay datos
        if (tamaño() == 0) {
            generarMatriculasPrueba();
        }
    }
    
    // ========== GENERACIÓN AUTOMÁTICA DE MATRÍCULAS ==========
    
    private void generarMatriculasPrueba() {
        ArregloAlumnos arregloAlumnos = new ArregloAlumnos();
        ArregloCursos arregloCursos = new ArregloCursos();
        
        System.out.println("Generando matrículas automáticas para alumnos pre-matriculados...");
        
        // Códigos de cursos disponibles (primeros 10 cursos)
        int[] codigosCursos = {2326, 4375, 1800, 2317, 1802, 2334, 4683, 4904, 4376, 4685};
        
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        Date ahora = new Date();
        String fecha = formatoFecha.format(ahora);
        String hora = formatoHora.format(ahora);
        
        // Generar matrículas para los primeros 10 alumnos (que están como MATRICULADOS)
        for (int i = 0; i < 10 && i < arregloAlumnos.tamaño(); i++) {
            // Obtener alumno
            Alumno alumno = arregloAlumnos.obtener(i);
            
            // Solo crear matrícula si el alumno está como MATRICULADO
            if (alumno.getEstado() == 1) {
                // Asignar curso de forma secuencial
                int codCurso = codigosCursos[i % codigosCursos.length];
                
                // Verificar que el curso existe
                if (arregloCursos.buscar(codCurso) != null) {
                    Matricula matricula = new Matricula(alumno.getCodAlumno(), codCurso, fecha, hora);
                    lista.add(matricula);
                    System.out.println("✓ Matrícula generada: Alumno " + alumno.getCodAlumno() + 
                                     " en curso " + codCurso);
                }
            }
        }
        
        grabarArchivo();
        System.out.println("✓ Se generaron " + tamaño() + " matrículas automáticamente");
    }
    
    // ========== OPERACIONES BÁSICAS ==========
    
    /**
     * Retorna el tamaño de la lista
     * @return Cantidad de matrículas
     */
    public int tamaño() {
        return lista.size();
    }
    
    /**
     * Obtiene una matrícula por su índice
     * @param i Índice de la matrícula
     * @return Objeto Matricula
     */
    public Matricula obtener(int i) {
        return lista.get(i);
    }
    
    /**
     * Adiciona una matrícula a la lista
     * @param matricula Matrícula a adicionar
     */
    public void adicionar(Matricula matricula) {
        lista.add(matricula);
        grabarArchivo();
    }
    
    // ========== BÚSQUEDA ==========
    
    /**
     * Busca una matrícula por su número
     * @param numero Número de matrícula
     * @return Matricula encontrada o null
     */
    public Matricula buscar(int numero) {
        for(Matricula m : lista) {
            if(m.getNumMatricula() == numero)
                return m;
        }
        return null;
    }
    
    /**
     * Busca una matrícula por código de alumno
     * @param codAlumno Código del alumno
     * @return Matricula encontrada o null
     */
    public Matricula buscarPorAlumno(int codAlumno) {
        for(Matricula m : lista) {
            if(m.getCodAlumno() == codAlumno)
                return m;
        }
        return null;
    }
    
    /**
     * Verifica si un alumno ya está matriculado
     * @param codAlumno Código del alumno
     * @return true si está matriculado, false si no
     */
    public boolean alumnoMatriculado(int codAlumno) {
        return buscarPorAlumno(codAlumno) != null;
    }
    
    // ========== CONSULTAS POR CURSO ==========
    
    /**
     * Obtiene todas las matrículas de un curso específico
     * @param codCurso Código del curso
     * @return ArrayList con las matrículas del curso
     */
    public ArrayList<Matricula> obtenerPorCurso(int codCurso) {
        ArrayList<Matricula> resultado = new ArrayList<Matricula>();
        for(Matricula m : lista) {
            if(m.getCodCurso() == codCurso)
                resultado.add(m);
        }
        return resultado;
    }
    
    /**
     * Cuenta cuántos alumnos están matriculados en un curso
     * @param codCurso Código del curso
     * @return Cantidad de alumnos matriculados
     */
    public int contarAlumnosPorCurso(int codCurso) {
        return obtenerPorCurso(codCurso).size();
    }
    
    // ========== CONSULTAS ADICIONALES ==========
    
    /**
     * Obtiene todos los códigos de alumnos matriculados
     * @return ArrayList con códigos de alumnos matriculados
     */
    public ArrayList<Integer> obtenerAlumnosMatriculados() {
        ArrayList<Integer> resultado = new ArrayList<Integer>();
        for(Matricula m : lista) {
            resultado.add(m.getCodAlumno());
        }
        return resultado;
    }
    
    /**
     * Obtiene todos los códigos de cursos con matrículas
     * @return ArrayList con códigos de cursos activos
     */
    public ArrayList<Integer> obtenerCursosConMatriculas() {
        ArrayList<Integer> resultado = new ArrayList<Integer>();
        for(Matricula m : lista) {
            if(!resultado.contains(m.getCodCurso())) {
                resultado.add(m.getCodCurso());
            }
        }
        return resultado;
    }
    
    // ========== ELIMINACIÓN ==========
    
    /**
     * Elimina una matrícula de la lista
     * @param matricula Matrícula a eliminar
     */
    public void eliminar(Matricula matricula) {
        lista.remove(matricula);
        grabarArchivo();
    }
    
    /**
     * Elimina todas las matrículas
     */
    public void eliminarTodo() {
        lista.clear();
        grabarArchivo();
    }
    
    // ========== ACTUALIZACIÓN ==========
    
    /**
     * Actualiza el archivo con los cambios realizados
     */
    public void actualizar() {
        grabarArchivo();
    }
    
    // ========== PERSISTENCIA ==========
    
    /**
     * Graba la lista de matrículas en el archivo matriculas.dat
     */
    public void grabarArchivo() {
        try {
            FileOutputStream fos = new FileOutputStream(archivo);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(lista);
            oos.close();
            fos.close();
        }
        catch(IOException e) {
            System.err.println("Error al grabar archivo de matrículas: " + e.getMessage());
        }
    }
    
    /**
     * Carga la lista de matrículas desde el archivo matriculas.dat
     * También restaura el contador de matrículas
     */
    @SuppressWarnings("unchecked")
    public void cargarArchivo() {
        File file = new File(archivo);
        if(file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(archivo);
                ObjectInputStream ois = new ObjectInputStream(fis);
                lista = (ArrayList<Matricula>) ois.readObject();
                ois.close();
                fis.close();
                
                // Restaurar el contador de matrículas
                if(tamaño() > 0) {
                    int maxNum = 100000;
                    for(Matricula m : lista) {
                        if(m.getNumMatricula() > maxNum)
                            maxNum = m.getNumMatricula();
                    }
                    Matricula.setContadorMatricula(maxNum);
                    System.out.println("Archivo de matrículas cargado: " + lista.size() + 
                                     " matrícula(s). Próximo número: " + (maxNum + 1));
                }
            }
            catch(FileNotFoundException e) {
                System.err.println("Archivo de matrículas no encontrado");
            }
            catch(IOException e) {
                System.err.println("Error al cargar archivo de matrículas: " + e.getMessage());
            }
            catch(ClassNotFoundException e) {
                System.err.println("Error de clase al cargar matrículas: " + e.getMessage());
            }
        }
        else {
            System.out.println("Archivo de matrículas no existe. Se generarán matrículas automáticamente.");
        }
    }
}