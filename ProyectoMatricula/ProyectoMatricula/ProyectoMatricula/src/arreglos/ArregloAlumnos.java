package arreglos;

import clases.Alumno;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;

public class ArregloAlumnos implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Alumno> lista;
    private final String archivo = "alumnos.ser";
    private Random random = new Random();
    private Set<String> dnisGenerados = new HashSet<>();
    private Set<String> codigosPersonalizados = new HashSet<>();

    public ArregloAlumnos() {
        lista = new ArrayList<>();
        cargar();
        if (tamaño() == 0) {
            generar80Alumnos();
            actualizar();
        }
    }

    private void generar80Alumnos() {
        String[] primerosNombres = {
            "Juan", "María", "Carlos", "Ana", "Luis", "Laura", "Pedro", "Sofía", "Miguel", "Elena",
            "José", "Carmen", "David", "Isabel", "Javier", "Patricia", "Daniel", "Rosa", "Francisco", "Teresa",
            "Antonio", "Lucía", "Manuel", "Eva", "Jorge", "Claudia", "Alejandro", "Marta", "Roberto", "Silvia",
            "Fernando", "Natalia", "Raúl", "Beatriz", "Diego", "Valeria", "Óscar", "Jimena", "Víctor", "Camila",
            "Héctor", "Gabriela", "Pablo", "Adriana", "Santiago", "Vanessa", "Alonso", "Daniela", "Rodrigo", "Victoria"
        };

        String[] segundosNombres = {
            "Ángel", "André", "Luis", "María", "José", "Fernando", "Carlos", "Gabriela", "Alberto", "Isabel",
            "Ricardo", "Carmen", "Martín", "Rosa", "Jesús", "Victoria", "Alonso", "Beatriz", "Rodrigo", "Daniela",
            "Santiago", "Valeria", "Diego", "Camila", "Héctor", "Gabriela", "Óscar", "Jimena", "Raúl", "Adriana",
            "Víctor", "Vanessa"
        };

        String[] apellidosPaternos = {
            "García", "Rodríguez", "González", "Fernández", "López", "Martínez", "Sánchez", "Pérez", "Gómez", "Martín",
            "Jiménez", "Ruiz", "Hernández", "Díaz", "Moreno", "Álvarez", "Romero", "Navarro", "Torres", "Domínguez",
            "Ramos", "Gil", "Ramírez", "Serrano", "Blanco", "Molina", "Morales", "Suárez", "Ortega", "Delgado", "Castro"
        };

        String[] apellidosMaternos = {
            "Vargas", "Rojas", "Medina", "Cortez", "Guerrero", "Silva", "Fuentes", "Miranda", "Santos", "Cruz",
            "Flores", "Reyes", "Peña", "Cabrera", "Vega", "Paredes", "Moran", "Coronel", "Salazar", "Ortiz",
            "Castillo", "Campos", "Rivera", "Méndez", "Ríos", "Aguilar", "Bravo", "Cárdenas", "Figueroa", "Escobar", "Ponce", "Valdez"
        };

        int codigo = 202010001;

        for (int i = 0; i < 80; i++) {
            // Nombres
            String nombre1 = primerosNombres[random.nextInt(primerosNombres.length)];
            String nombre2 = segundosNombres[random.nextInt(segundosNombres.length)];
            String nombres = nombre1 + " " + nombre2;

            // Apellidos
            String apePat = apellidosPaternos[random.nextInt(apellidosPaternos.length)];
            String apeMat = apellidosMaternos[random.nextInt(apellidosMaternos.length)];
            String apellidos = apePat + " " + apeMat;

            // DNI único
            String dni = generarDNIUnico();

            // Edad: 18 a 30 años
            int edad = 18 + random.nextInt(13);

            // Celular: 9 dígitos, empieza con 9
            int celular = 900000000 + random.nextInt(100000000);

            // Estado: 10 matriculados, resto registrados
            int estado = (i < 10) ? 1 : 0;

            // CORREGIDO: Usa constructor completo de tu Alumno.java
            Alumno a = new Alumno(codigo++, nombres, apellidos, dni, edad, celular, estado);

            // Generar código personalizado único (I + 9 dígitos)
            String codigoPers = generarCodigoPersonalizadoUnico();
            a.setCodigoPersonalizado(codigoPers);

            lista.add(a);
        }

        // Actualizar contador estático
        Alumno.setContadorCodigo(codigo - 1);
    }

    private String generarDNIUnico() {
        String dni;
        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 8; i++) sb.append(random.nextInt(10));
            dni = sb.toString();
        } while (dnisGenerados.contains(dni));
        dnisGenerados.add(dni);
        return dni;
    }

    private String generarCodigoPersonalizadoUnico() {
        String codigo;
        do {
            StringBuilder sb = new StringBuilder("I");
            for (int i = 0; i < 9; i++) sb.append(random.nextInt(10));
            codigo = sb.toString();
        } while (codigosPersonalizados.contains(codigo));

        codigosPersonalizados.add(codigo);
        return codigo;
    }

    // === OPERACIONES BÁSICAS ===
    public int tamaño() { return lista.size(); }
    public Alumno obtener(int i) { return lista.get(i); }
    public void adicionar(Alumno a) { lista.add(a); actualizar(); }

    public Alumno buscar(int codigo) {
        for (Alumno a : lista) if (a.getCodAlumno() == codigo) return a;
        return null;
    }

    public Alumno buscarPorDni(String dni) {
        for (Alumno a : lista) if (a.getDni().equals(dni)) return a;
        return null;
    }

    // CORREGIDO: Método necesario para ConsultaGeneral
    public Alumno buscarPorCodigoPersonalizado(String codigo) {
        for (Alumno a : lista) {
            if (a.getCodigoPersonalizado() != null && a.getCodigoPersonalizado().equals(codigo)) {
                return a;
            }
        }
        return null;
    }

    public void eliminar(Alumno a) { lista.remove(a); actualizar(); }

    public void actualizar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(lista);
        } catch (Exception e) {
            System.err.println("Error al grabar alumnos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void cargar() {
        File file = new File(archivo);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                lista = (ArrayList<Alumno>) ois.readObject();
                // Restaurar contador estático
                if (tamaño() > 0) {
                    Alumno ultimo = lista.get(tamaño() - 1);
                    Alumno.setContadorCodigo(ultimo.getCodAlumno());
                }
            } catch (Exception e) {
                lista = new ArrayList<>();
            }
        }
    }

    public ArrayList<Alumno> obtenerPorEstado(int estado) {
        ArrayList<Alumno> res = new ArrayList<>();
        for (Alumno a : lista) if (a.getEstado() == estado) res.add(a);
        return res;
    }
}