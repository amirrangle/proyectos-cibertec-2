package guis;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.ArrayList;
import clases.*;
import arreglos.*;

public class Reportes extends JInternalFrame {
    
    private static final long serialVersionUID = 1L;
    
    // Arreglos
    private ArregloAlumnos arregloAlumnos = new ArregloAlumnos();
    private ArregloCursos arregloCursos = new ArregloCursos();
    private ArregloMatriculas arregloMatriculas = new ArregloMatriculas();
    
    // Componentes
    private JPanel panelPrincipal;
    private JScrollPane scrollPane;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextArea txtReporte;
    private JLabel lblInfo;
    
    // Tipo de reporte
    private int tipoReporte;
    
    public Reportes(int tipo) {
        this.tipoReporte = tipo;
        
        switch(tipo) {
            case 0:
                setTitle("Reporte: Alumnos con Matrícula Pendiente");
                break;
            case 1:
                setTitle("Reporte: Alumnos con Matrícula Vigente");
                break;
            case 2:
                setTitle("Reporte: Alumnos Matriculados por Curso");
                break;
            case 3:
                setTitle("Reporte: Cursos y Matrículas");
                break;
        }
        
        setSize(1000, 650);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        
        inicializarComponentes();
        generarReporte();
    }
    
    private void inicializarComponentes() {
        panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(Color.white);
        
        // Panel de información superior
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(52, 152, 219));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        lblInfo = new JLabel();
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblInfo.setForeground(Color.black);
        lblInfo.setIcon(UIManager.getIcon("FileView.fileIcon"));
        panelSuperior.add(lblInfo, BorderLayout.WEST);
        
        JLabel lblFecha = new JLabel(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date()));
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblFecha.setForeground(Color.black);
        panelSuperior.add(lblFecha, BorderLayout.EAST);
        
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        
        // Crear componentes según tipo
        if(tipoReporte == 2 || tipoReporte == 3) {
            crearReporteTexto();
        } else {
            crearReporteTabla();
        }
        
        getContentPane().add(panelPrincipal);
    }
    
    private void crearReporteTabla() {
        modelo = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        modelo.addColumn("Código");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        modelo.addColumn("DNI");
        modelo.addColumn("Edad");
        modelo.addColumn("Celular");
        modelo.addColumn("Estado");
        
        tabla = new JTable(modelo);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabla.setRowHeight(28);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(new Color(52, 152, 219));
        tabla.getTableHeader().setForeground(Color.black);
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 35));
        
        // Centrar columnas específicas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabla.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        
        // Anchos de columna
        tabla.getColumnModel().getColumn(0).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(90);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(60);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(6).setPreferredWidth(120);
        
        // Alternar colores de filas
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.white : new Color(240, 248, 255));
                }
                if(column == 0 || column == 3 || column == 4 || column == 5 || column == 6) {
                    ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                }
                return c;
            }
        });
        
        scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 1));
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
    }
    
    private void crearReporteTexto() {
        txtReporte = new JTextArea();
        txtReporte.setEditable(false);
        txtReporte.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtReporte.setBackground(new Color(250, 250, 250));
        txtReporte.setMargin(new Insets(10, 10, 10, 10));
        
        scrollPane = new JScrollPane(txtReporte);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 1));
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
    }
    
    private void generarReporte() {
        switch(tipoReporte) {
            case 0:
                reporteMatriculaPendiente();
                break;
            case 1:
                reporteMatriculaVigente();
                break;
            case 2:
                reporteMatriculadosPorCurso();
                break;
            case 3:
                reporteCursosYMatriculas();
                break;
        }
    }
    
    private void reporteMatriculaPendiente() {
        modelo.setRowCount(0);
        ArrayList<Alumno> registrados = arregloAlumnos.obtenerPorEstado(0);
        
        for(Alumno a : registrados) {
            Object[] fila = {
                a.getCodAlumno(),
                a.getNombres(),
                a.getApellidos(),
                a.getDni(),
                a.getEdad(),
                a.getCelular(),
                a.getEstadoTexto()
            };
            modelo.addRow(fila);
        }
        
        lblInfo.setText("Total de alumnos: " + registrados.size());
        
        if(registrados.size() == 0) {
            JOptionPane.showMessageDialog(this,
                "No hay alumnos con matrícula pendiente",
                "Información",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void reporteMatriculaVigente() {
        modelo.setRowCount(0);
        ArrayList<Alumno> matriculados = arregloAlumnos.obtenerPorEstado(1);
        
        for(Alumno a : matriculados) {
            Object[] fila = {
                a.getCodAlumno(),
                a.getNombres(),
                a.getApellidos(),
                a.getDni(),
                a.getEdad(),
                a.getCelular(),
                a.getEstadoTexto()
            };
            modelo.addRow(fila);
        }
        
        lblInfo.setText("Total de alumnos: " + matriculados.size());
        
        if(matriculados.size() == 0) {
            JOptionPane.showMessageDialog(this,
                "No hay alumnos con matrícula vigente",
                "Información",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void reporteMatriculadosPorCurso() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("╔══════════════════════════════════════════════════════════════════════════╗\n");
        sb.append("║          REPORTE DE ALUMNOS MATRICULADOS POR CURSO                       ║\n");
        sb.append("╚══════════════════════════════════════════════════════════════════════════╝\n\n");
        
        boolean hayDatos = false;
        int totalAlumnos = 0;
        int totalCursos = 0;
        
        for(int i = 0; i < arregloCursos.tamaño(); i++) {
            Curso curso = arregloCursos.obtener(i);
            ArrayList<Matricula> matriculas = arregloMatriculas.obtenerPorCurso(curso.getCodCurso());
            
            if(matriculas.size() > 0) {
                hayDatos = true;
                totalCursos++;
                totalAlumnos += matriculas.size();
                
                sb.append("┌──────────────────────────────────────────────────────────────────────────┐\n");
                sb.append(String.format("│ CURSO: [%04d] %-58s │\n", 
                    curso.getCodCurso(), curso.getAsignatura()));
                sb.append("├──────────────────────────────────────────────────────────────────────────┤\n");
                sb.append(String.format("│ Ciclo: %-15s  Créditos: %-5d  Horas: %-5d              │\n",
                    curso.getCicloTexto(), curso.getCreditos(), curso.getHoras()));
                sb.append("├──────────────────────────────────────────────────────────────────────────┤\n");
                sb.append("│ ALUMNOS MATRICULADOS:                                                    │\n");
                sb.append("├──────────────────────────────────────────────────────────────────────────┤\n");
                
                int contador = 1;
                for(Matricula m : matriculas) {
                    Alumno alumno = arregloAlumnos.buscar(m.getCodAlumno());
                    if(alumno != null) {
                        String nombreCompleto = String.format("%-40s", alumno.getNombreCompleto());
                        if(nombreCompleto.length() > 40) nombreCompleto = nombreCompleto.substring(0, 37) + "...";
                        
                        sb.append(String.format("│ %2d. %s [%d] DNI: %s %-12s │\n",
                            contador,
                            nombreCompleto,
                            alumno.getCodAlumno(),
                            alumno.getDni(),
                            "(" + alumno.getEstadoTexto() + ")"));
                        contador++;
                    }
                }
                
                sb.append("├──────────────────────────────────────────────────────────────────────────┤\n");
                sb.append(String.format("│ Total de alumnos: %-54d │\n", matriculas.size()));
                sb.append("└──────────────────────────────────────────────────────────────────────────┘\n\n");
            }
        }
        
        if(!hayDatos) {
            sb.append("\n  ⚠️  No hay alumnos matriculados en ningún curso.\n\n");
            lblInfo.setText("No hay datos para mostrar");
        } else {
            sb.append("\n╔══════════════════════════════════════════════════════════════════════════╗\n");
            sb.append("║                            RESUMEN GENERAL                                ║\n");
            sb.append("╚══════════════════════════════════════════════════════════════════════════╝\n\n");
            sb.append(String.format("  Total de cursos con alumnos: %d\n", totalCursos));
            sb.append(String.format("  Total de alumnos matriculados: %d\n", totalAlumnos));
            sb.append(String.format("  Promedio de alumnos por curso: %.2f\n", (double)totalAlumnos/totalCursos));
            
            lblInfo.setText(String.format("Total: %d cursos - %d alumnos", totalCursos, totalAlumnos));
        }
        
        txtReporte.setText(sb.toString());
        txtReporte.setCaretPosition(0);
    }
    
    private void reporteCursosYMatriculas() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("REPORTE DE CURSOS Y MATRÍCULAS\n");
        sb.append("=======================================\n\n");
        
        int totalCursos = arregloCursos.tamaño();
        int totalCursosConAlumnos = 0;
        int totalAlumnosMatriculados = 0;
        
        for (int i = 0; i < totalCursos; i++) {
            Curso c = arregloCursos.obtener(i);
            ArrayList<Matricula> matriculas = arregloMatriculas.obtenerPorCurso(c.getCodCurso());
            
            sb.append("Curso       : ").append(c.getAsignatura()).append("\n");
            sb.append("  Código    : ").append(String.format("%04d", c.getCodCurso())).append("\n");
            sb.append("  Ciclo     : ").append(c.getCicloTexto()).append("\n");
            sb.append("  Créditos  : ").append(c.getCreditos()).append("\n");
            sb.append("  Horas     : ").append(c.getHoras()).append("\n");
            sb.append("  Alumnos   : ").append(matriculas.size()).append("\n");
            
            if (matriculas.size() > 0) {
                totalCursosConAlumnos++;
                totalAlumnosMatriculados += matriculas.size();
                sb.append("  Lista:\n");
                for (Matricula m : matriculas) {
                    Alumno a = arregloAlumnos.buscar(m.getCodAlumno());
                    sb.append("    • ").append(a.getNombreCompleto())
                      .append(" (").append(a.getCodAlumno()).append(")\n");
                }
            } else {
                sb.append("  (Sin alumnos matriculados)\n");
            }
            sb.append("\n");
        }
        
        // Resumen al final
        sb.append("=======================================\n");
        sb.append("RESUMEN GENERAL:\n");
        sb.append("  Total de cursos: ").append(totalCursos).append("\n");
        sb.append("  Cursos con alumnos: ").append(totalCursosConAlumnos).append("\n");
        sb.append("  Total alumnos matriculados: ").append(totalAlumnosMatriculados).append("\n");
        if (totalCursosConAlumnos > 0) {
            sb.append("  Promedio por curso: ").append(String.format("%.2f", (double)totalAlumnosMatriculados/totalCursosConAlumnos)).append("\n");
        }
        
        lblInfo.setText("Total: " + totalCursos + " cursos - " + totalAlumnosMatriculados + " alumnos matriculados");
        txtReporte.setText(sb.toString());
        txtReporte.setCaretPosition(0);
    }
}