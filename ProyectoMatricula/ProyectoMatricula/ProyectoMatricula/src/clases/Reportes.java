package clases;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import clases.*;
import arreglos.*;

public class Reportes extends JInternalFrame {
    
    private static final long serialVersionUID = 1L;
    
    private ArregloAlumnos arregloAlumnos = new ArregloAlumnos();
    private ArregloCursos arregloCursos = new ArregloCursos();
    private ArregloMatriculas arregloMatriculas = new ArregloMatriculas();
    private ArregloRetiros arregloRetiros = new ArregloRetiros();
    
    private JTextArea txtReporte;
    private JTable tablaReporte;
    private DefaultTableModel modeloTabla;
    private JScrollPane scroll;
    
    private int tipoReporte; // 0=registrados, 1=matriculados, 2=matriculados por curso
    
    public Reportes(int tipoReporte) {
        this.tipoReporte = tipoReporte;
        
        switch(tipoReporte) {
            case 0: setTitle("Reporte - Alumnos con Matrícula Pendiente"); break;
            case 1: setTitle("Reporte - Alumnos con Matrícula Vigente"); break;
            case 2: setTitle("Reporte - Alumnos Matriculados por Curso"); break;
        }
        
        setSize(900, 600);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        
        inicializarComponentes();
        generarReporte();
    }
    
    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        
        if(tipoReporte == 2) {
            // Usar tabla para el reporte por curso
            crearTabla();
            panelPrincipal.add(new JScrollPane(tablaReporte), BorderLayout.CENTER);
        } else {
            // Usar área de texto para los otros reportes
            txtReporte = new JTextArea();
            txtReporte.setFont(new Font("Consolas", Font.PLAIN, 12));
            txtReporte.setEditable(false);
            
            scroll = new JScrollPane(txtReporte);
            panelPrincipal.add(scroll, BorderLayout.CENTER);
        }
        
        getContentPane().add(panelPrincipal);
    }
    
    private void crearTabla() {
        modeloTabla = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        modeloTabla.addColumn("Curso");
        modeloTabla.addColumn("Código");
        modeloTabla.addColumn("Cantidad Alumnos");
        modeloTabla.addColumn("Alumnos Matriculados");
        
        tablaReporte = new JTable(modeloTabla);
        tablaReporte.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tablaReporte.setRowHeight(25);
    }
    
    private void generarReporte() {
        switch(tipoReporte) {
            case 0:
                generarAlumnosRegistrados();
                break;
            case 1:
                generarAlumnosMatriculados();
                break;
            case 2:
                generarMatriculadosPorCurso();
                break;
        }
    }
    
    private void generarAlumnosRegistrados() {
        StringBuilder sb = new StringBuilder();
        sb.append("REPORTE DE ALUMNOS CON MATRÍCULA PENDIENTE\n");
        sb.append("===========================================\n\n");
        
        int contador = 0;
        for(int i = 0; i < arregloAlumnos.tamaño(); i++) {
            Alumno alumno = arregloAlumnos.obtener(i);
            if(alumno.getEstado() == 0) { // REGISTRADO
                contador++;
                sb.append(String.format("%2d. Código: %s\n", contador, alumno.getCodigoPersonalizado()));
                sb.append(String.format("    Nombre: %s\n", alumno.getNombreCompleto()));
                sb.append(String.format("    DNI: %s\n", alumno.getDni()));
                sb.append(String.format("    Edad: %d\n", alumno.getEdad()));
                sb.append(String.format("    Celular: %d\n\n", alumno.getCelular()));
            }
        }
        
        if(contador == 0) {
            sb.append("No hay alumnos con matrícula pendiente.\n");
        } else {
            sb.append(String.format("\nTotal de alumnos: %d\n", contador));
        }
        
        txtReporte.setText(sb.toString());
    }
    
    private void generarAlumnosMatriculados() {
        StringBuilder sb = new StringBuilder();
        sb.append("REPORTE DE ALUMNOS CON MATRÍCULA VIGENTE\n");
        sb.append("=========================================\n\n");
        
        int contador = 0;
        for(int i = 0; i < arregloAlumnos.tamaño(); i++) {
            Alumno alumno = arregloAlumnos.obtener(i);
            if(alumno.getEstado() == 1) { // MATRICULADO
                // CORREGIDO: Verificar que la matrícula no sea null
                Matricula matricula = arregloMatriculas.buscarPorAlumno(alumno.getCodAlumno());
                
                if (matricula != null) { // SOLO si existe matrícula
                    contador++;
                    Curso curso = arregloCursos.buscar(matricula.getCodCurso());
                    
                    sb.append(String.format("%2d. Código: %s\n", contador, alumno.getCodigoPersonalizado()));
                    sb.append(String.format("    Nombre: %s\n", alumno.getNombreCompleto()));
                    sb.append(String.format("    DNI: %s\n", alumno.getDni()));
                    
                    if(curso != null) {
                        sb.append(String.format("    Curso: %s\n", curso.getAsignatura()));
                        sb.append(String.format("    Código Curso: %04d\n", curso.getCodCurso()));
                    } else {
                        sb.append(String.format("    Curso: NO ENCONTRADO\n"));
                    }
                    sb.append(String.format("    Fecha Matrícula: %s\n\n", matricula.getFecha()));
                }
            }
        }
        
        if(contador == 0) {
            sb.append("No hay alumnos con matrícula vigente.\n");
        } else {
            sb.append(String.format("\nTotal de alumnos: %d\n", contador));
        }
        
        txtReporte.setText(sb.toString());
    }
    
    private void generarMatriculadosPorCurso() {
        modeloTabla.setRowCount(0);
        
        for(int i = 0; i < arregloCursos.tamaño(); i++) {
            Curso curso = arregloCursos.obtener(i);
            int cantidad = arregloMatriculas.contarAlumnosPorCurso(curso.getCodCurso());
            
            // Obtener nombres de alumnos matriculados
            StringBuilder alumnos = new StringBuilder();
            ArrayList<Matricula> matriculas = arregloMatriculas.obtenerPorCurso(curso.getCodCurso());
            
            for(Matricula m : matriculas) {
                Alumno a = arregloAlumnos.buscar(m.getCodAlumno());
                if(a != null) {
                    if(alumnos.length() > 0) alumnos.append(", ");
                    alumnos.append(a.getNombres().split(" ")[0]); // Solo primer nombre
                }
            }
            
            if(alumnos.length() == 0) {
                alumnos.append("Ninguno");
            }
            
            Object[] fila = {
                curso.getAsignatura(),
                String.format("%04d", curso.getCodCurso()),
                cantidad,
                alumnos.toString()
            };
            modeloTabla.addRow(fila);
        }
    }
}