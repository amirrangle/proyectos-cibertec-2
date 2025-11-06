import java.util.ArrayList;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Arreglos {
    
    // ArrayLists estáticos para almacenar datos
    public static ArrayList<Alumno> listaAlumnos = new ArrayList<>();
    public static ArrayList<Curso> listaCursos = new ArrayList<>();
    public static ArrayList<Matricula> listaMatriculas = new ArrayList<>();
    public static ArrayList<Retiro> listaRetiros = new ArrayList<>();
    
    // Contadores para códigos correlativos
    private static int contadorAlumno = 202010001;
    private static int contadorMatricula = 100001;
    private static int contadorRetiro = 200001;
    
    // Métodos para Alumno
    public static int generarCodigoAlumno() {
        return contadorAlumno++;
    }
    
    public static void agregarAlumno(Alumno a) {
        listaAlumnos.add(a);
    }
    
    public static boolean existeDniAlumno(String dni) {
        for (Alumno a : listaAlumnos) {
            if (a.getDni().equals(dni)) {
                return true;
            }
        }
        return false;
    }
    
    public static Alumno buscarAlumnoPorCodigo(int codigo) {
        for (Alumno a : listaAlumnos) {
            if (a.getCodAlumno() == codigo) {
                return a;
            }
        }
        return null;
    }
    
    public static void eliminarAlumno(Alumno a) {
        listaAlumnos.remove(a);
    }
    
    // Métodos para Curso
    public static void agregarCurso(Curso c) {
        listaCursos.add(c);
        ordenarCursosPorCodigo();
    }
    
    public static void ordenarCursosPorCodigo() {
        listaCursos.sort((c1, c2) -> Integer.compare(c1.getCodCurso(), c2.getCodCurso()));
    }
    
    public static boolean existeCodigoCurso(int codigo) {
        for (Curso c : listaCursos) {
            if (c.getCodCurso() == codigo) {
                return true;
            }
        }
        return false;
    }
    
    public static Curso buscarCursoPorCodigo(int codigo) {
        for (Curso c : listaCursos) {
            if (c.getCodCurso() == codigo) {
                return c;
            }
        }
        return null;
    }
    
    public static boolean cursoTieneMatriculas(int codCurso) {
        for (Matricula m : listaMatriculas) {
            if (m.getCodCurso() == codCurso) {
                return true;
            }
        }
        return false;
    }
    
    public static void eliminarCurso(Curso c) {
        listaCursos.remove(c);
    }
    
    // Métodos para Matrícula
    public static int generarNumeroMatricula() {
        return contadorMatricula++;
    }
    
    public static void agregarMatricula(Matricula m) {
        listaMatriculas.add(m);
    }
    
    public static boolean alumnoYaMatriculado(int codAlumno) {
        for (Matricula m : listaMatriculas) {
            if (m.getCodAlumno() == codAlumno) {
                return true;
            }
        }
        return false;
    }
    
    public static Matricula buscarMatriculaPorNumero(int numero) {
        for (Matricula m : listaMatriculas) {
            if (m.getNumMatricula() == numero) {
                return m;
            }
        }
        return null;
    }
    
    public static Matricula buscarMatriculaPorAlumno(int codAlumno) {
        for (Matricula m : listaMatriculas) {
            if (m.getCodAlumno() == codAlumno) {
                return m;
            }
        }
        return null;
    }
    
    public static void eliminarMatricula(Matricula m) {
        listaMatriculas.remove(m);
    }
    
    // Métodos para Retiro
    public static int generarNumeroRetiro() {
        return contadorRetiro++;
    }
    
    public static void agregarRetiro(Retiro r) {
        listaRetiros.add(r);
    }
    
    public static Retiro buscarRetiroPorNumero(int numero) {
        for (Retiro r : listaRetiros) {
            if (r.getNumRetiro() == numero) {
                return r;
            }
        }
        return null;
    }
    
    public static void eliminarRetiro(Retiro r) {
        listaRetiros.remove(r);
    }
    
    // Métodos para obtener fecha y hora actual
    public static String obtenerFechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(new Date());
    }
    
    public static String obtenerHoraActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }
    
    // Métodos para grabar y leer archivos
    public static void grabarAlumnos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("alumnos.txt"))) {
            for (Alumno a : listaAlumnos) {
                pw.println(a.getCodAlumno() + ";" + a.getNombres() + ";" + a.getApellidos() + ";" +
                          a.getDni() + ";" + a.getEdad() + ";" + a.getCelular() + ";" + a.getEstado());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void leerAlumnos() {
        try (BufferedReader br = new BufferedReader(new FileReader("alumnos.txt"))) {
            String linea;
            listaAlumnos.clear();
            int maxCodigo = 202010000;
            
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                int cod = Integer.parseInt(datos[0]);
                Alumno a = new Alumno(cod, datos[1], datos[2], datos[3],
                                     Integer.parseInt(datos[4]), Integer.parseInt(datos[5]),
                                     Integer.parseInt(datos[6]));
                listaAlumnos.add(a);
                if (cod > maxCodigo) maxCodigo = cod;
            }
            contadorAlumno = maxCodigo + 1;
        } catch (FileNotFoundException e) {
            // Archivo no existe, se creará al grabar
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void grabarCursos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("cursos.txt"))) {
            for (Curso c : listaCursos) {
                pw.println(c.getCodCurso() + ";" + c.getAsignatura() + ";" + c.getCiclo() + ";" +
                          c.getCreditos() + ";" + c.getHoras());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void leerCursos() {
        try (BufferedReader br = new BufferedReader(new FileReader("cursos.txt"))) {
            String linea;
            listaCursos.clear();
            
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                Curso c = new Curso(Integer.parseInt(datos[0]), datos[1],
                                   Integer.parseInt(datos[2]), Integer.parseInt(datos[3]),
                                   Integer.parseInt(datos[4]));
                listaCursos.add(c);
            }
        } catch (FileNotFoundException e) {
            // Archivo no existe
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void grabarMatriculas() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("matriculas.txt"))) {
            for (Matricula m : listaMatriculas) {
                pw.println(m.getNumMatricula() + ";" + m.getCodAlumno() + ";" + m.getCodCurso() + ";" +
                          m.getFecha() + ";" + m.getHora());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void leerMatriculas() {
        try (BufferedReader br = new BufferedReader(new FileReader("matriculas.txt"))) {
            String linea;
            listaMatriculas.clear();
            int maxNum = 100000;
            
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                int num = Integer.parseInt(datos[0]);
                Matricula m = new Matricula(num, Integer.parseInt(datos[1]),
                                           Integer.parseInt(datos[2]), datos[3], datos[4]);
                listaMatriculas.add(m);
                if (num > maxNum) maxNum = num;
            }
            contadorMatricula = maxNum + 1;
        } catch (FileNotFoundException e) {
            // Archivo no existe
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void grabarRetiros() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("retiros.txt"))) {
            for (Retiro r : listaRetiros) {
                pw.println(r.getNumRetiro() + ";" + r.getNumMatricula() + ";" +
                          r.getFecha() + ";" + r.getHora());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void leerRetiros() {
        try (BufferedReader br = new BufferedReader(new FileReader("retiros.txt"))) {
            String linea;
            listaRetiros.clear();
            int maxNum = 200000;
            
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                int num = Integer.parseInt(datos[0]);
                Retiro r = new Retiro(num, Integer.parseInt(datos[1]), datos[2], datos[3]);
                listaRetiros.add(r);
                if (num > maxNum) maxNum = num;
            }
            contadorRetiro = maxNum + 1;
        } catch (FileNotFoundException e) {
            // Archivo no existe
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void cargarTodosDatos() {
        leerAlumnos();
        leerCursos();
        leerMatriculas();
        leerRetiros();
    }
    
    public static void grabarTodosDatos() {
        grabarAlumnos();
        grabarCursos();
        grabarMatriculas();
        grabarRetiros();
    }
}