
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReporteAlumnosMatriculados extends JFrame {
    
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnGenerar, btnImprimir;
    
    public ReporteAlumnosMatriculados() {
        setTitle("Reporte: Alumnos con Matrícula Vigente");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        crearComponentes();
        generarReporte();
        
        setVisible(true);
    }
    
    private void crearComponentes() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblTitulo = new JLabel("Alumnos con Matrícula Vigente (Estado: Matriculado)");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelSuperior.add(lblTitulo, BorderLayout.CENTER);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        // Tabla
        String[] columnas = {"Código", "Nombres", "Apellidos", "DNI", "Curso", "Fecha Matrícula"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabla = new JTable(modelo);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnGenerar = new JButton("Actualizar Reporte");
        btnGenerar.addActionListener(e -> generarReporte());
        
        btnImprimir = new JButton("Imprimir");
        btnImprimir.addActionListener(e -> imprimir());
        
        panelBotones.add(btnGenerar);
        panelBotones.add(btnImprimir);
        
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void generarReporte() {
        modelo.setRowCount(0);
        int contador = 0;
        
        for (Alumno a : Arreglos.listaAlumnos) {
            if (a.getEstado() == 1) {
                Matricula matricula = Arreglos.buscarMatriculaPorAlumno(a.getCodAlumno());
                String nombreCurso = "Sin curso";
                String fechaMatricula = "N/A";
                
                if (matricula != null) {
                    Curso curso = Arreglos.buscarCursoPorCodigo(matricula.getCodCurso());
                    nombreCurso = curso != null ? curso.getAsignatura() : "Desconocido";
                    fechaMatricula = matricula.getFecha();
                }
                
                Object[] fila = {
                    a.getCodAlumno(),
                    a.getNombres(),
                    a.getApellidos(),
                    a.getDni(),
                    nombreCurso,
                    fechaMatricula
                };
                modelo.addRow(fila);
                contador++;
            }
        }
        
        if (contador == 0) {
            JOptionPane.showMessageDialog(this, 
                "No hay alumnos con matrícula vigente", 
                "Información", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void imprimir() {
        try {
            boolean complete = tabla.print();
            if (complete) {
                JOptionPane.showMessageDialog(this, 
                    "Impresión completada", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al imprimir: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}