package arreglos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.*;
import clases.Curso;

/**
 * Clase ArregloCursos - Gestiona la colección de cursos
 * Características:
 * - Usa ArrayList para almacenamiento dinámico
 * - Ordenamiento automático por código
 * - Persistencia en archivo cursos.ser (compatible con el resto del proyecto)
 * - Generación automática de cursos CIBERTEC
 * @author CIBERTEC
 * @version 2.1
 */
public class ArregloCursos implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributo privado
    private ArrayList<Curso> lista;
    private final String archivo = "cursos.ser";

    /**
     * Constructor - Inicializa el ArrayList y carga datos del archivo
     * Genera cursos automáticamente si no hay datos
     */
    public ArregloCursos() {
        lista = new ArrayList<>();
        cargarArchivo();

        // Generar cursos automáticamente si no hay datos
        if (tamaño() == 0) {
            generarCursosAutomaticos();
        }
    }

    // ========== GENERACIÓN AUTOMÁTICA DE CURSOS CIBERTEC ==========

    private void generarCursosAutomaticos() {
        System.out.println("Generando cursos de malla curricular CIBERTEC...");

        // === NIVEL 1 - PRIMER CICLO ===
        adicionar(new Curso(2326, "INTRODUCCIÓN A LA ALGORITMIA", 0, 6, 12));
        adicionar(new Curso(4375, "DESARROLLO DE HABILIDADES PROFESIONALES I", 0, 4, 9));
        adicionar(new Curso(1800, "MATEMÁTICA I (T)", 0, 3, 7));
        adicionar(new Curso(2317, "FUNDAMENTOS DE GESTIÓN EMPRESARIAL (T)", 0, 3, 5));
        adicionar(new Curso(1802, "ARQUITECTURA DE ENTORNOS WEB", 0, 4, 7));
        adicionar(new Curso(2334, "TECNOLOGÍAS DE LA INFORMACIÓN", 0, 3, 7));

        // === NIVEL 2 - SEGUNDO CICLO ===
        adicionar(new Curso(4683, "ALGORITMOS Y ESTRUCTURA DE DATOS", 1, 4, 11));
        adicionar(new Curso(4904, "EXPERIENCIA FORMATIVA EN SITUACIÓN REAL DE TRABAJO", 1, 12, 24));
        adicionar(new Curso(4376, "DESARROLLO DE HABILIDADES PROFESIONALES II", 1, 4, 9));
        adicionar(new Curso(4685, "BASE DE DATOS", 1, 4, 11));
        adicionar(new Curso(1813, "MATEMÁTICA II (T)", 1, 3, 7));
        adicionar(new Curso(2389, "MODELADO DE PROCESOS DE NEGOCIO", 1, 3, 9));
        adicionar(new Curso(4684, "DESARROLLO DE ENTORNOS WEB", 1, 3, 6));

        // === NIVEL 3 - TERCER CICLO ===
        adicionar(new Curso(2392, "ANÁLISIS Y DISEÑO DE SISTEMAS I", 2, 5, 12));
        adicionar(new Curso(4906, "EXPERIENCIAS FORMATIVAS EN SITUACIONES REALES DE TRABAJO II", 2, 1, 3));
        adicionar(new Curso(4377, "DESARROLLO DE HABILIDADES PROFESIONALES III", 2, 3, 7));
        adicionar(new Curso(2394, "GESTIÓN DE DATOS DINÁMICOS", 2, 2, 8));
        adicionar(new Curso(4686, "BASE DE DATOS AVANZADO I", 2, 3, 6));
        adicionar(new Curso(4688, "LENGUAJE DE PROGRAMACIÓN I", 2, 3, 17));
        adicionar(new Curso(4689, "PROGRAMACIÓN ORIENTADA A OBJETOS I", 2, 3, 18));

        // === NIVEL 4 - CUARTO CICLO ===
        adicionar(new Curso(4690, "ANÁLISIS Y DISEÑO DE SISTEMAS II", 3, 4, 15));
        adicionar(new Curso(4908, "EXPERIENCIAS FORMATIVAS EN SITUACIONES REALES DE TRABAJO III", 3, 2, 5));
        adicionar(new Curso(4378, "DESARROLLO DE HABILIDADES PROFESIONALES IV", 3, 4, 13));
        adicionar(new Curso(2398, "BASE DE DATOS AVANZADO II", 3, 5, 9));
        adicionar(new Curso(4691, "LENGUAJE DE PROGRAMACIÓN II", 3, 3, 6));
        adicionar(new Curso(4692, "PROGRAMACIÓN ORIENTADA A OBJETOS II", 3, 3, 6));
        adicionar(new Curso(2401, "GESTIÓN DE SERVICIOS DE TI", 3, 3, 7));

        // === NIVEL 5 - QUINTO CICLO ===
        adicionar(new Curso(2414, "SEGURIDAD DE APLICACIONES", 4, 4, 7));
        adicionar(new Curso(4910, "EXPERIENCIAS FORMATIVAS EN SITUACIONES REALES DE TRABAJO IV", 4, 4, 8));
        adicionar(new Curso(2412, "INNOVACIÓN Y EMPRENDIMIENTO", 4, 3, 7));
        adicionar(new Curso(4693, "DESARROLLO DE APLICACIONES MÓVILES I", 4, 3, 13));
        adicionar(new Curso(4694, "DESARROLLO DE APLICACIONES WEB I", 4, 3, 13));
        adicionar(new Curso(4695, "DESARROLLO DE SERVICIOS WEB I", 4, 3, 8));
        adicionar(new Curso(2410, "GESTIÓN DE PROYECTOS DE TI", 4, 3, 6));

        // === NIVEL 6 - SEXTO CICLO ===
        adicionar(new Curso(2424, "PRUEBAS DE SOFTWARE", 5, 3, 6));
        adicionar(new Curso(4911, "EXPERIENCIAS FORMATIVAS EN SITUACIONES REALES DE TRABAJO V", 5, 4, 8));
        adicionar(new Curso(2420, "PLAN DE NEGOCIOS", 5, 4, 7));
        adicionar(new Curso(4696, "DESARROLLO DE APLICACIONES MÓVILES II", 5, 4, 15));
        adicionar(new Curso(4697, "DESARROLLO DE APLICACIONES WEB II", 5, 3, 14));
        adicionar(new Curso(4698, "DESARROLLO DE SERVICIOS WEB II", 5, 2, 15));
        adicionar(new Curso(2423, "PROYECTO INTEGRADOR (CI)", 5, 4, 6));

        // Ordenar la lista después de generar todos los cursos
        ordenarPorCodigo();
        grabarArchivo();

        System.out.println("Se generaron " + tamaño() + " cursos de la malla curricular CIBERTEC");
        System.out.println("  - Distribuidos en 6 ciclos académicos");
    }

    // ========== OPERACIONES BÁSICAS ==========

    public int tamaño() {
        return lista.size();
    }

    public Curso obtener(int i) {
        return lista.get(i);
    }

    public void adicionar(Curso curso) {
        lista.add(curso);
        ordenarPorCodigo();
        grabarArchivo();
    }

    // ========== BÚSQUEDA ==========

    public Curso buscar(int codigo) {
        for (Curso c : lista) {
            if (c.getCodCurso() == codigo)
                return c;
        }
        return null;
    }

    public boolean existeCodigo(int codigo) {
        return buscar(codigo) != null;
    }

    // ========== ELIMINACIÓN ==========

    public void eliminar(Curso curso) {
        lista.remove(curso);
        grabarArchivo();
    }

    public void eliminarTodo() {
        lista.clear();
        grabarArchivo();
    }

    // ========== ACTUALIZACIÓN ==========

    public void actualizar() {
        grabarArchivo();
    }

    // ========== CONSULTAS ESPECÍFICAS ==========

    public ArrayList<Curso> obtenerPorCiclo(int ciclo) {
        ArrayList<Curso> resultado = new ArrayList<>();
        for (Curso c : lista) {
            if (c.getCiclo() == ciclo)
                resultado.add(c);
        }
        return resultado;
    }

    public int contarCursosPorCiclo(int ciclo) {
        return obtenerPorCiclo(ciclo).size();
    }

    // ========== ORDENAMIENTO ==========

    private void ordenarPorCodigo() {
        Collections.sort(lista, Comparator.comparingInt(Curso::getCodCurso));
    }

    // ========== PERSISTENCIA ==========

    private void grabarArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.err.println("Error al grabar archivo de cursos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void cargarArchivo() {
        File file = new File(archivo);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                lista = (ArrayList<Curso>) ois.readObject();
                ordenarPorCodigo();
                System.out.println("Archivo de cursos cargado: " + lista.size() + " curso(s)");
            } catch (FileNotFoundException e) {
                System.err.println("Archivo de cursos no encontrado");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al cargar archivo de cursos: " + e.getMessage());
            }
        } else {
            System.out.println("Archivo de cursos no existe. Se generarán cursos automáticamente.");
        }
    }
}