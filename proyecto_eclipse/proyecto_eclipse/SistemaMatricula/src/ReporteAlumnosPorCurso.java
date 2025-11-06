import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class ReporteAlumnosPorCurso extends JFrame {
    
    private JComboBox<String> cboCurso;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnGenerarTodos, btnGenerarPorCurso, btnImprimir, btnExportar;
    private JLabel lblTotal;
    private JTextArea txtResumen;
    private JTabbedPane tabbedPane;
    
    public ReporteAlumnosPorCurso() {
        setTitle("Reporte: Alumnos Matriculados por Curso");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        crearComponentes();
        generarReporteTodos();
        
        setVisible(true);
    }
    
    private void crearComponentes() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior con título y filtros
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelSuperior.setBackground(new Color(0, 102, 204));
        
        JLabel lblTitulo = new JLabel("REPORTE: ALUMNOS MATRICULADOS POR CURSO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        
        // Panel de filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelFiltros.setBackground(new Color(0, 102, 204));
        
        JLabel lblFiltro = new JLabel("Filtrar por Curso:");
        lblFiltro.setForeground(Color.WHITE);
        lblFiltro.setFont(new Font("Arial", Font.BOLD, 12));
        panelFiltros.add(lblFiltro);
        
        cboCurso = new JComboBox<>();
        cboCurso.setPreferredSize(new Dimension(300, 25));
        cargarCursos();
        panelFiltros.add(cboCurso);
        
        panelSuperior.add(panelFiltros, BorderLayout.CENTER);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        // TabbedPane con dos vistas
        tabbedPane = new JTabbedPane();
        
        // TAB 1: Vista de Tabla
        JPanel panelTabla = crearPanelTabla();
        tabbedPane.addTab("Vista de Tabla", new ImageIcon(), panelTabla, "Ver datos en formato tabla");
        
        // TAB 2: Vista de Texto Detallado
        JPanel panelTexto = crearPanelTexto();
        tabbedPane.addTab("Vista Detallada", new ImageIcon(), panelTexto, "Ver reporte detallado en texto");
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Panel inferior con botones y estadísticas
        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        
        // Estadísticas
        JPanel panelEstadisticas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEstadisticas.setBackground(new Color(240, 240, 245));
        panelEstadisticas.setBorder(BorderFactory.createTitledBorder("Estadísticas"));
        
        lblTotal = new JLabel("Total de alumnos: 0");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 13));
        lblTotal.setForeground(new Color(0, 102, 204));
        panelEstadisticas.add(lblTotal);
        
        panelInferior.add(panelEstadisticas, BorderLayout.WEST);
        
        // Botones SIN COLORES
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        
        btnGenerarTodos = new JButton("Generar Todos los Cursos");
        btnGenerarTodos.addActionListener(e -> generarReporteTodos());
        
        btnGenerarPorCurso = new JButton("Generar Curso Seleccionado");
        btnGenerarPorCurso.addActionListener(e -> generarReportePorCurso());
        
        btnImprimir = new JButton("Imprimir");
        btnImprimir.addActionListener(e -> imprimir());
        
        btnExportar = new JButton("Exportar a Texto");
        btnExportar.addActionListener(e -> exportarTexto());
        
        panelBotones.add(btnGenerarTodos);
        panelBotones.add(btnGenerarPorCurso);
        panelBotones.add(btnImprimir);
        panelBotones.add(btnExportar);
        
        panelInferior.add(panelBotones, BorderLayout.CENTER);
        
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Tabla
        String[] columnas = {"Curso", "Código", "Asignatura", "Ciclo", "Créditos", "Horas", "Total Alumnos"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1 || columnIndex == 4 || columnIndex == 5 || columnIndex == 6) {
                    return Integer.class;
                }
                return String.class;
            }
        };
        
        tabla = new JTable(modelo);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.setRowHeight(25);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(new Color(0, 102, 204));
        tabla.getTableHeader().setForeground(Color.BLACK);
        tabla.setSelectionBackground(new Color(173, 216, 230));
        tabla.setSelectionForeground(Color.BLACK);
        tabla.setGridColor(new Color(200, 200, 200));
        
        // Centrar contenido de columnas numéricas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabla.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        
        // Resaltar última columna
        DefaultTableCellRenderer highlightRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(new Color(255, 255, 200));
                    setFont(new Font("Arial", Font.BOLD, 12));
                }
                setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        };
        tabla.getColumnModel().getColumn(6).setCellRenderer(highlightRenderer);
        
        // Agregar doble click para ver detalle
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = tabla.getSelectedRow();
                    if (fila >= 0) {
                        mostrarDetalleAlumnos(fila);
                    }
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Instrucción
        JLabel lblInstruccion = new JLabel("Haga doble clic en un curso para ver los alumnos matriculados");
        lblInstruccion.setFont(new Font("Arial", Font.ITALIC, 11));
        lblInstruccion.setForeground(new Color(100, 100, 100));
        lblInstruccion.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.add(lblInstruccion, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelTexto() {
        JPanel panel = new JPanel(new BorderLayout());
        
        txtResumen = new JTextArea();
        txtResumen.setEditable(false);
        txtResumen.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtResumen.setBackground(new Color(245, 245, 250));
        
        JScrollPane scrollPane = new JScrollPane(txtResumen);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void cargarCursos() {
        cboCurso.removeAllItems();
        cboCurso.addItem("-- TODOS LOS CURSOS --");
        for (Curso c : Arreglos.listaCursos) {
            cboCurso.addItem(c.getCodCurso() + " - " + c.getAsignatura());
        }
    }
    
    private void generarReporteTodos() {
        modelo.setRowCount(0);
        int totalGeneral = 0;
        
        for (Curso curso : Arreglos.listaCursos) {
            int totalAlumnos = contarAlumnosPorCurso(curso.getCodCurso());
            
            Object[] fila = {
                curso.getAsignatura(),
                curso.getCodCurso(),
                curso.getAsignatura(),
                curso.getCicloTexto(),
                curso.getCreditos(),
                curso.getHoras(),
                totalAlumnos
            };
            modelo.addRow(fila);
            totalGeneral += totalAlumnos;
        }
        
        lblTotal.setText("Total de alumnos matriculados: " + totalGeneral + 
                        " en " + Arreglos.listaCursos.size() + " curso(s)");
        
        generarTextoDetallado();
    }
    
    private void generarReportePorCurso() {
        if (cboCurso.getSelectedIndex() == 0) {
            generarReporteTodos();
            return;
        }
        
        String seleccion = (String) cboCurso.getSelectedItem();
        int codCurso = Integer.parseInt(seleccion.split(" - ")[0]);
        
        modelo.setRowCount(0);
        
        Curso curso = Arreglos.buscarCursoPorCodigo(codCurso);
        if (curso != null) {
            int totalAlumnos = contarAlumnosPorCurso(codCurso);
            
            Object[] fila = {
                curso.getAsignatura(),
                curso.getCodCurso(),
                curso.getAsignatura(),
                curso.getCicloTexto(),
                curso.getCreditos(),
                curso.getHoras(),
                totalAlumnos
            };
            modelo.addRow(fila);
            
            lblTotal.setText("Total de alumnos en este curso: " + totalAlumnos);
        }
        
        generarTextoDetallado();
    }
    
    private void generarTextoDetallado() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════════════════════════════════\n");
        sb.append("              REPORTE DETALLADO: ALUMNOS MATRICULADOS POR CURSO            \n");
        sb.append("═══════════════════════════════════════════════════════════════════════════\n\n");
        sb.append("Fecha: ").append(Arreglos.obtenerFechaActual());
        sb.append("    Hora: ").append(Arreglos.obtenerHoraActual()).append("\n\n");
        
        int totalGeneral = 0;
        int cursosConAlumnos = 0;
        
        for (Curso curso : Arreglos.listaCursos) {
            ArrayList<Alumno> alumnosEnCurso = obtenerAlumnosPorCurso(curso.getCodCurso());
            
            if (!alumnosEnCurso.isEmpty()) {
                cursosConAlumnos++;
                sb.append("───────────────────────────────────────────────────────────────────────────\n");
                sb.append("CURSO: ").append(curso.getAsignatura()).append(" (Código: ").append(curso.getCodCurso()).append(")\n");
                sb.append("Ciclo: ").append(curso.getCicloTexto());
                sb.append(" | Créditos: ").append(curso.getCreditos());
                sb.append(" | Horas: ").append(curso.getHoras()).append("\n");
                sb.append("───────────────────────────────────────────────────────────────────────────\n\n");
                
                sb.append(String.format("%-10s %-25s %-25s %-12s %-15s\n", 
                    "CÓDIGO", "NOMBRES", "APELLIDOS", "DNI", "ESTADO"));
                sb.append("───────────────────────────────────────────────────────────────────────────\n");
                
                for (Alumno alumno : alumnosEnCurso) {
                    sb.append(String.format("%-10d %-25s %-25s %-12s %-15s\n",
                        alumno.getCodAlumno(),
                        truncar(alumno.getNombres(), 25),
                        truncar(alumno.getApellidos(), 25),
                        alumno.getDni(),
                        alumno.getEstadoTexto()));
                }
                
                sb.append("\nTotal de alumnos en este curso: ").append(alumnosEnCurso.size()).append("\n\n");
                totalGeneral += alumnosEnCurso.size();
            }
        }
        
        if (cursosConAlumnos == 0) {
            sb.append("\nNo hay cursos con alumnos matriculados\n");
        }
        
        sb.append("\n═══════════════════════════════════════════════════════════════════════════\n");
        sb.append("                            RESUMEN GENERAL                                \n");
        sb.append("═══════════════════════════════════════════════════════════════════════════\n");
        sb.append(String.format("Total de cursos con alumnos: %d\n", cursosConAlumnos));
        sb.append(String.format("Total de alumnos matriculados: %d\n", totalGeneral));
        sb.append("═══════════════════════════════════════════════════════════════════════════\n");
        
        txtResumen.setText(sb.toString());
        txtResumen.setCaretPosition(0);
    }
    
    private void mostrarDetalleAlumnos(int fila) {
        int codCurso = (int) modelo.getValueAt(fila, 1);
        String nombreCurso = (String) modelo.getValueAt(fila, 2);
        
        ArrayList<Alumno> alumnos = obtenerAlumnosPorCurso(codCurso);
        
        if (alumnos.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No hay alumnos matriculados en este curso", 
                "Información", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Crear diálogo con tabla de alumnos
        JDialog dialogo = new JDialog(this, "Alumnos matriculados en: " + nombreCurso, true);
        dialogo.setSize(800, 400);
        dialogo.setLocationRelativeTo(this);
        
        String[] columnas = {"Código", "Nombres", "Apellidos", "DNI", "Edad", "Celular", "Estado"};
        DefaultTableModel modeloDetalle = new DefaultTableModel(columnas, 0);
        
        for (Alumno a : alumnos) {
            Object[] filaAlumno = {
                a.getCodAlumno(),
                a.getNombres(),
                a.getApellidos(),
                a.getDni(),
                a.getEdad(),
                a.getCelular(),
                a.getEstadoTexto()
            };
            modeloDetalle.addRow(filaAlumno);
        }
        
        JTable tablaDetalle = new JTable(modeloDetalle);
        tablaDetalle.setFont(new Font("Arial", Font.PLAIN, 11));
        JScrollPane scroll = new JScrollPane(tablaDetalle);
        
        dialogo.add(scroll, BorderLayout.CENTER);
        
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInfo.add(new JLabel("Total de alumnos: " + alumnos.size()));
        dialogo.add(panelInfo, BorderLayout.SOUTH);
        
        dialogo.setVisible(true);
    }
    
    private ArrayList<Alumno> obtenerAlumnosPorCurso(int codCurso) {
        ArrayList<Alumno> alumnos = new ArrayList<>();
        
        for (Matricula matricula : Arreglos.listaMatriculas) {
            if (matricula.getCodCurso() == codCurso) {
                Alumno alumno = Arreglos.buscarAlumnoPorCodigo(matricula.getCodAlumno());
                if (alumno != null && (alumno.getEstado() == 1 || alumno.getEstado() == 2)) {
                    alumnos.add(alumno);
                }
            }
        }
        
        return alumnos;
    }
    
    private int contarAlumnosPorCurso(int codCurso) {
        return obtenerAlumnosPorCurso(codCurso).size();
    }
    
    private String truncar(String texto, int longitud) {
        if (texto.length() <= longitud) {
            return texto;
        }
        return texto.substring(0, longitud - 3) + "...";
    }
    
    private void imprimir() {
        try {
            if (tabbedPane.getSelectedIndex() == 0) {
                // Imprimir tabla
                boolean complete = tabla.print();
                if (complete) {
                    JOptionPane.showMessageDialog(this, 
                        "Impresión completada exitosamente", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                // Imprimir texto
                boolean complete = txtResumen.print();
                if (complete) {
                    JOptionPane.showMessageDialog(this, 
                        "Impresión completada exitosamente", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al imprimir: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void exportarTexto() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte");
        fileChooser.setSelectedFile(new java.io.File("Reporte_Alumnos_Por_Curso_" + 
            Arreglos.obtenerFechaActual().replace("/", "-") + ".txt"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                java.io.File fileToSave = fileChooser.getSelectedFile();
                java.io.PrintWriter writer = new java.io.PrintWriter(fileToSave);
                writer.print(txtResumen.getText());
                writer.close();
                
                JOptionPane.showMessageDialog(this, 
                    "Reporte exportado exitosamente a:\n" + fileToSave.getAbsolutePath(), 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al exportar: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}