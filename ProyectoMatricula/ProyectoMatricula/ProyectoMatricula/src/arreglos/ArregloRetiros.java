package arreglos;

import java.util.ArrayList;
import java.io.*;
import clases.Retiro;

/**
 * Clase ArregloRetiros - Gestiona la colección de retiros
 * Características:
 * - Usa ArrayList para almacenamiento dinámico
 * - Controla retiros por matrícula
 * - Persistencia en archivo retiros.dat
 * - Restaura contador de retiros al cargar
 * @author CIBERTEC
 * @version 2.0
 */
public class ArregloRetiros {
    
    // Atributo privado
    private ArrayList<Retiro> lista;
    private String archivo = "retiros.dat";
    
    /**
     * Constructor - Inicializa el ArrayList y carga datos del archivo
     */
    public ArregloRetiros() {
        lista = new ArrayList<Retiro>();
        cargarArchivo();
    }
    
    // ========== OPERACIONES BÁSICAS ==========
    
    /**
     * Retorna el tamaño de la lista
     * @return Cantidad de retiros
     */
    public int tamaño() {
        return lista.size();
    }
    
    /**
     * Obtiene un retiro por su índice
     * @param i Índice del retiro
     * @return Objeto Retiro
     */
    public Retiro obtener(int i) {
        return lista.get(i);
    }
    
    /**
     * Adiciona un retiro a la lista
     * @param retiro Retiro a adicionar
     */
    public void adicionar(Retiro retiro) {
        lista.add(retiro);
        grabarArchivo();
    }
    
    // ========== BÚSQUEDA ==========
    
    /**
     * Busca un retiro por su número
     * @param numero Número de retiro
     * @return Retiro encontrado o null
     */
    public Retiro buscar(int numero) {
        for(Retiro r : lista) {
            if(r.getNumRetiro() == numero)
                return r;
        }
        return null;
    }
    
    /**
     * Busca un retiro por número de matrícula
     * @param numMatricula Número de matrícula
     * @return Retiro encontrado o null
     */
    public Retiro buscarPorMatricula(int numMatricula) {
        for(Retiro r : lista) {
            if(r.getNumMatricula() == numMatricula)
                return r;
        }
        return null;
    }
    
    /**
     * Verifica si una matrícula tiene retiro registrado
     * @param numMatricula Número de matrícula
     * @return true si tiene retiro, false si no
     */
    public boolean tieneRetiro(int numMatricula) {
        return buscarPorMatricula(numMatricula) != null;
    }
    
    // ========== CONSULTAS ADICIONALES ==========
    
    /**
     * Obtiene todos los números de matrícula con retiros
     * @return ArrayList con números de matrícula con retiros
     */
    public ArrayList<Integer> obtenerMatriculasConRetiro() {
        ArrayList<Integer> resultado = new ArrayList<Integer>();
        for(Retiro r : lista) {
            resultado.add(r.getNumMatricula());
        }
        return resultado;
    }
    
    // ========== ELIMINACIÓN ==========
    
    /**
     * Elimina un retiro de la lista
     * @param retiro Retiro a eliminar
     */
    public void eliminar(Retiro retiro) {
        lista.remove(retiro);
        grabarArchivo();
    }
    
    /**
     * Elimina todos los retiros
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
     * Graba la lista de retiros en el archivo retiros.dat
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
            System.err.println("Error al grabar archivo de retiros: " + e.getMessage());
        }
    }
    
    /**
     * Carga la lista de retiros desde el archivo retiros.dat
     * También restaura el contador de retiros
     */
    @SuppressWarnings("unchecked")
    public void cargarArchivo() {
        File file = new File(archivo);
        if(file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(archivo);
                ObjectInputStream ois = new ObjectInputStream(fis);
                lista = (ArrayList<Retiro>) ois.readObject();
                ois.close();
                fis.close();
                
                // Restaurar el contador de retiros
                if(tamaño() > 0) {
                    int maxNum = 200000;
                    for(Retiro r : lista) {
                        if(r.getNumRetiro() > maxNum)
                            maxNum = r.getNumRetiro();
                    }
                    Retiro.setContadorRetiro(maxNum);
                    System.out.println("Archivo de retiros cargado: " + lista.size() + 
                                     " retiro(s). Próximo número: " + (maxNum + 1));
                }
            }
            catch(FileNotFoundException e) {
                System.err.println("Archivo de retiros no encontrado");
            }
            catch(IOException e) {
                System.err.println("Error al cargar archivo de retiros: " + e.getMessage());
            }
            catch(ClassNotFoundException e) {
                System.err.println("Error de clase al cargar retiros: " + e.getMessage());
            }
        }
        else {
            System.out.println("Archivo de retiros no existe. Se creará al registrar el primer retiro.");
        }
    }
}