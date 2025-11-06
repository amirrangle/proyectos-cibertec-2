import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaPrincipal extends JFrame {
    
    private JMenuBar menuBar;
    private JMenu menuMantenimiento, menuRegistro, menuConsulta, menuReporte;
    private JMenuItem itemAlumno, itemCurso, itemMatricula, itemRetiro;
    private JMenuItem itemConsultaAlumno, itemConsultaMatricula;
    private JMenuItem itemReporteRegistrados, itemReporteMatriculados, itemReporteAlumnosPorCurso;
    
    public VentanaPrincipal() {
        setTitle("Sistema de Registro y Matrícula de Alumnos");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Cargar datos desde archivos
        Arreglos.cargarTodosDatos();
        
        crearMenu();
        crearPanelPrincipal();
        
        // Grabar datos al cerrar
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Arreglos.grabarTodosDatos();
            }
        });
        
        setVisible(true);
    }
    
    private void crearMenu() {
        menuBar = new JMenuBar();
        
        // Menú Mantenimiento
        menuMantenimiento = new JMenu("Mantenimiento");
        itemAlumno = new JMenuItem("Alumno");
        itemCurso = new JMenuItem("Curso");
        
        itemAlumno.addActionListener(e -> abrirMantenimientoAlumno());
        itemCurso.addActionListener(e -> abrirMantenimientoCurso());
        
        menuMantenimiento.add(itemAlumno);
        menuMantenimiento.add(itemCurso);
        
        // Menú Registro
        menuRegistro = new JMenu("Registro");
        itemMatricula = new JMenuItem("Matrícula");
        itemRetiro = new JMenuItem("Retiro");
        
        itemMatricula.addActionListener(e -> abrirMatricula());
        itemRetiro.addActionListener(e -> abrirRetiro());
        
        menuRegistro.add(itemMatricula);
        menuRegistro.add(itemRetiro);
        
        // Menú Consulta
        menuConsulta = new JMenu("Consulta");
        itemConsultaAlumno = new JMenuItem("Alumnos y Cursos");
        itemConsultaMatricula = new JMenuItem("Matrículas y Retiros");
        
        itemConsultaAlumno.addActionListener(e -> abrirConsultaAlumno());
        itemConsultaMatricula.addActionListener(e -> abrirConsultaMatricula());
        
        menuConsulta.add(itemConsultaAlumno);
        menuConsulta.add(itemConsultaMatricula);
        
        // Menú Reporte
        menuReporte = new JMenu("Reporte");
        itemReporteRegistrados = new JMenuItem("Alumnos con Matrícula Pendiente");
        itemReporteMatriculados = new JMenuItem("Alumnos con Matrícula Vigente");
        itemReporteAlumnosPorCurso = new JMenuItem("Alumnos Matriculados por Curso");
        
        itemReporteRegistrados.addActionListener(e -> abrirReporteRegistrados());
        itemReporteMatriculados.addActionListener(e -> abrirReporteMatriculados());
        itemReporteAlumnosPorCurso.addActionListener(e -> abrirReporteAlumnosPorCurso());
        
        menuReporte.add(itemReporteRegistrados);
        menuReporte.add(itemReporteMatriculados);
        menuReporte.add(itemReporteAlumnosPorCurso);
        
        menuBar.add(menuMantenimiento);
        menuBar.add(menuRegistro);
        menuBar.add(menuConsulta);
        menuBar.add(menuReporte);
        
        setJMenuBar(menuBar);
    }
    
    private void crearPanelPrincipal() {
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(new Color(240, 240, 245));
        
        // Panel de bienvenida
        JPanel panelBienvenida = new JPanel(new GridBagLayout());
        panelBienvenida.setBackground(new Color(240, 240, 245));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel lblTitulo = new JLabel("SISTEMA DE REGISTRO Y MATRÍCULA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(0, 102, 204));
        panelBienvenida.add(lblTitulo, gbc);
        
        gbc.gridy = 1;
        JLabel lblSubtitulo = new JLabel("Instituto de Educación Superior Privado CIBERTEC");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 18));
        lblSubtitulo.setForeground(new Color(70, 70, 70));
        panelBienvenida.add(lblSubtitulo, gbc);
        
        gbc.gridy = 2;
        gbc.insets = new Insets(30, 10, 10, 10);
        JLabel lblInstrucciones = new JLabel("<html><center>Seleccione una opción del menú superior para comenzar</center></html>");
        lblInstrucciones.setFont(new Font("Arial", Font.PLAIN, 14));
        lblInstrucciones.setForeground(new Color(100, 100, 100));
        panelBienvenida.add(lblInstrucciones, gbc);
        
        panelCentral.add(panelBienvenida, BorderLayout.CENTER);
        add(panelCentral);
    }
    
    private void abrirMantenimientoAlumno() {
        new MantenimientoAlumno();
    }
    
    private void abrirMantenimientoCurso() {
        new MantenimientoCurso();
    }
    
    private void abrirMatricula() {
        new RegistroMatricula();
    }
    
    private void abrirRetiro() {
        new RegistroRetiro();
    }
    
    private void abrirConsultaAlumno() {
        new ConsultaAlumnoCurso();
    }
    
    private void abrirConsultaMatricula() {
        new ConsultaMatriculaRetiro();
    }
    
    private void abrirReporteRegistrados() {
        new ReporteAlumnosRegistrados();
    }
    
    private void abrirReporteMatriculados() {
        new ReporteAlumnosMatriculados();
    }
    
    private void abrirReporteAlumnosPorCurso() {
        new ReporteAlumnosPorCurso();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new VentanaPrincipal();
        });
    }
}