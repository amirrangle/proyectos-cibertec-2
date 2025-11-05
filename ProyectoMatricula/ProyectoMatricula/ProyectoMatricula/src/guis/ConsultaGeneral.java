package guis;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import clases.*;
import arreglos.*;

public class ConsultaGeneral extends JInternalFrame implements ActionListener {
    
    private static final long serialVersionUID = 1L;
    
    // Arreglos
    private ArregloAlumnos arregloAlumnos = new ArregloAlumnos();
    private ArregloCursos arregloCursos = new ArregloCursos();
    private ArregloMatriculas arregloMatriculas = new ArregloMatriculas();
    private ArregloRetiros arregloRetiros = new ArregloRetiros();
    
    // Componentes principales
    private JTabbedPane tabbedPane;
    
    // Tab 1: Alumnos y Cursos
    private JPanel panelAlumnosCursos;
    private JTextField txtCodigoAlumno, txtCodigoCurso;
    private JButton btnBuscarAlumno, btnBuscarCurso;
    private JTextArea txtResultadoAlumno, txtResultadoCurso;
    
    // Tab 2: Matrículas y Retiros
    private JPanel panelMatriculasRetiros;
    private JTextField txtNumMatricula, txtNumRetiro;
    private JButton btnBuscarMatricula, btnBuscarRetiro;
    private JTextArea txtResultadoMatricula, txtResultadoRetiro;
    
    public ConsultaGeneral() {
        setTitle("Consulta General");
        setSize(900, 700);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        crearPanelAlumnosCursos();
        crearPanelMatriculasRetiros();
        
        tabbedPane.addTab("  Alumnos y Cursos  ", panelAlumnosCursos);
        tabbedPane.addTab("  Matrículas y Retiros  ", panelMatriculasRetiros);
        
        getContentPane().add(tabbedPane);
    }
    
    private void crearPanelAlumnosCursos() {
        panelAlumnosCursos = new JPanel();
        panelAlumnosCursos.setLayout(null);
        panelAlumnosCursos.setBackground(Color.WHITE);
        
        // Sección Consulta de Alumno
        JPanel panelAlumno = new JPanel();
        panelAlumno.setBounds(20, 20, 840, 280);
        panelAlumno.setLayout(null);
        panelAlumno.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            "Consulta de Alumno",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(52, 152, 219)
        ));
        panelAlumno.setBackground(Color.WHITE);
        
        // CORREGIDO: Cambiar etiqueta a "Código Alumno (I...)"
        JLabel lblCodigoAlumno = new JLabel("Código Alumno (I22...):");
        lblCodigoAlumno.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCodigoAlumno.setBounds(20, 30, 150, 25);
        panelAlumno.add(lblCodigoAlumno);
        
        txtCodigoAlumno = new JTextField();
        txtCodigoAlumno.setBounds(170, 30, 150, 30);
        txtCodigoAlumno.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelAlumno.add(txtCodigoAlumno);
        
        btnBuscarAlumno = new JButton("Buscar");
        btnBuscarAlumno.setBounds(340, 30, 100, 30);
        btnBuscarAlumno.setBackground(new Color(52, 152, 219));
        btnBuscarAlumno.setForeground(Color.black);
        btnBuscarAlumno.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnBuscarAlumno.setFocusPainted(false);
        btnBuscarAlumno.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscarAlumno.addActionListener(this);
        panelAlumno.add(btnBuscarAlumno);
        
        txtResultadoAlumno = new JTextArea();
        txtResultadoAlumno.setEditable(false);
        txtResultadoAlumno.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtResultadoAlumno.setBackground(new Color(250, 250, 250));
        JScrollPane scrollAlumno = new JScrollPane(txtResultadoAlumno);
        scrollAlumno.setBounds(20, 75, 800, 190);
        panelAlumno.add(scrollAlumno);
        
        panelAlumnosCursos.add(panelAlumno);
        
        // Sección Consulta de Curso (se mantiene igual)
        JPanel panelCurso = new JPanel();
        panelCurso.setBounds(20, 320, 840, 280);
        panelCurso.setLayout(null);
        panelCurso.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            "Consulta de Curso",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(52, 152, 219)
        ));
        panelCurso.setBackground(Color.WHITE);
        
        JLabel lblCodigoCurso = new JLabel("Código Curso:");
        lblCodigoCurso.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCodigoCurso.setBounds(20, 30, 120, 25);
        panelCurso.add(lblCodigoCurso);
        
        txtCodigoCurso = new JTextField();
        txtCodigoCurso.setBounds(140, 30, 150, 30);
        txtCodigoCurso.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelCurso.add(txtCodigoCurso);
        
        btnBuscarCurso = new JButton("Buscar");
        btnBuscarCurso.setBounds(310, 30, 100, 30);
        btnBuscarCurso.setBackground(new Color(52, 152, 219));
        btnBuscarCurso.setForeground(Color.black);
        btnBuscarCurso.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnBuscarCurso.setFocusPainted(false);
        btnBuscarCurso.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscarCurso.addActionListener(this);
        panelCurso.add(btnBuscarCurso);
        
        txtResultadoCurso = new JTextArea();
        txtResultadoCurso.setEditable(false);
        txtResultadoCurso.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtResultadoCurso.setBackground(new Color(250, 250, 250));
        JScrollPane scrollCurso = new JScrollPane(txtResultadoCurso);
        scrollCurso.setBounds(20, 75, 800, 190);
        panelCurso.add(scrollCurso);
        
        panelAlumnosCursos.add(panelCurso);
    }
    
    private void crearPanelMatriculasRetiros() {
        panelMatriculasRetiros = new JPanel();
        panelMatriculasRetiros.setLayout(null);
        panelMatriculasRetiros.setBackground(Color.WHITE);
        
        // Sección Consulta de Matrícula
        JPanel panelMatricula = new JPanel();
        panelMatricula.setBounds(20, 20, 840, 280);
        panelMatricula.setLayout(null);
        panelMatricula.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(46, 204, 113), 2),
            "Consulta de Matrícula",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(46, 204, 113)
        ));
        panelMatricula.setBackground(Color.WHITE);
        
        JLabel lblNumMatricula = new JLabel("Número Matrícula:");
        lblNumMatricula.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblNumMatricula.setBounds(20, 30, 140, 25);
        panelMatricula.add(lblNumMatricula);
        
        txtNumMatricula = new JTextField();
        txtNumMatricula.setBounds(160, 30, 150, 30);
        txtNumMatricula.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelMatricula.add(txtNumMatricula);
        
        btnBuscarMatricula = new JButton("Buscar");
        btnBuscarMatricula.setBounds(330, 30, 100, 30);
        btnBuscarMatricula.setBackground(new Color(46, 204, 113));
        btnBuscarMatricula.setForeground(Color.black);
        btnBuscarMatricula.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnBuscarMatricula.setFocusPainted(false);
        btnBuscarMatricula.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscarMatricula.addActionListener(this);
        panelMatricula.add(btnBuscarMatricula);
        
        txtResultadoMatricula = new JTextArea();
        txtResultadoMatricula.setEditable(false);
        txtResultadoMatricula.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtResultadoMatricula.setBackground(new Color(250, 250, 250));
        JScrollPane scrollMatricula = new JScrollPane(txtResultadoMatricula);
        scrollMatricula.setBounds(20, 75, 800, 190);
        panelMatricula.add(scrollMatricula);
        
        panelMatriculasRetiros.add(panelMatricula);
        
        // Sección Consulta de Retiro
        JPanel panelRetiro = new JPanel();
        panelRetiro.setBounds(20, 320, 840, 280);
        panelRetiro.setLayout(null);
        panelRetiro.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(231, 76, 60), 2),
            "Consulta de Retiro",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(231, 76, 60)
        ));
        panelRetiro.setBackground(Color.WHITE);
        
        JLabel lblNumRetiro = new JLabel("Número Retiro:");
        lblNumRetiro.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblNumRetiro.setBounds(20, 30, 140, 25);
        panelRetiro.add(lblNumRetiro);
        
        txtNumRetiro = new JTextField();
        txtNumRetiro.setBounds(160, 30, 150, 30);
        txtNumRetiro.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelRetiro.add(txtNumRetiro);
        
        btnBuscarRetiro = new JButton("Buscar");
        btnBuscarRetiro.setBounds(330, 30, 100, 30);
        btnBuscarRetiro.setBackground(new Color(231, 76, 60));
        btnBuscarRetiro.setForeground(Color.black);
        btnBuscarRetiro.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnBuscarRetiro.setFocusPainted(false);
        btnBuscarRetiro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscarRetiro.addActionListener(this);
        panelRetiro.add(btnBuscarRetiro);
        
        txtResultadoRetiro = new JTextArea();
        txtResultadoRetiro.setEditable(false);
        txtResultadoRetiro.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtResultadoRetiro.setBackground(new Color(250, 250, 250));
        JScrollPane scrollRetiro = new JScrollPane(txtResultadoRetiro);
        scrollRetiro.setBounds(20, 75, 800, 190);
        panelRetiro.add(scrollRetiro);
        
        panelMatriculasRetiros.add(panelRetiro);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnBuscarAlumno) {
            buscarAlumno();
        }
        else if(e.getSource() == btnBuscarCurso) {
            buscarCurso();
        }
        else if(e.getSource() == btnBuscarMatricula) {
            buscarMatricula();
        }
        else if(e.getSource() == btnBuscarRetiro) {
            buscarRetiro();
        }
    }
    
    private void buscarAlumno() {
        try {
            String codigo = txtCodigoAlumno.getText().trim().toUpperCase();
            
            // Validar formato del código (debe empezar con I y tener 10 caracteres)
            if (!codigo.matches("^I\\d{9}$")) {
                txtResultadoAlumno.setText("\n  ⚠  Error: Formato de código inválido\n  Use: I + 9 dígitos (ej: I201822948)");
                return;
            }
            
            // CORREGIDO: Buscar por código personalizado
            Alumno alumno = arregloAlumnos.buscarPorCodigoPersonalizado(codigo);
            
            if(alumno != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("╔════════════════════════════════════════════════════════════════════╗\n");
                sb.append("║                      DATOS DEL ALUMNO                              ║\n");
                sb.append("╚════════════════════════════════════════════════════════════════════╝\n\n");
                
                // CORREGIDO: Mostrar código personalizado
                sb.append(String.format("  Código      : %s\n", alumno.getCodigoPersonalizado()));
                sb.append(String.format("  Nombres     : %s\n", alumno.getNombres()));
                sb.append(String.format("  Apellidos   : %s\n", alumno.getApellidos()));
                sb.append(String.format("  DNI         : %s\n", alumno.getDni()));
                sb.append(String.format("  Edad        : %d años\n", alumno.getEdad()));
                sb.append(String.format("  Celular     : %d\n", alumno.getCelular()));
                sb.append(String.format("  Estado      : %s\n", alumno.getEstadoTexto()));
                
                // Si está matriculado, mostrar datos del curso
                if(alumno.getEstado() == 1 || alumno.getEstado() == 2) {
                    // CORREGIDO: Buscar matrícula por código de alumno interno
                    Matricula matricula = arregloMatriculas.buscarPorAlumno(alumno.getCodAlumno());
                    if(matricula != null) {
                        Curso curso = arregloCursos.buscar(matricula.getCodCurso());
                        if(curso != null) {
                            sb.append("\n╔════════════════════════════════════════════════════════════════════╗\n");
                            sb.append("║                    CURSO MATRICULADO                               ║\n");
                            sb.append("╚════════════════════════════════════════════════════════════════════╝\n\n");
                            
                            sb.append(String.format("  Código      : %04d\n", curso.getCodCurso()));
                            sb.append(String.format("  Asignatura  : %s\n", curso.getAsignatura()));
                            sb.append(String.format("  Ciclo       : %s\n", curso.getCicloTexto()));
                            sb.append(String.format("  Créditos    : %d\n", curso.getCreditos()));
                            sb.append(String.format("  Horas       : %d\n", curso.getHoras()));
                            sb.append(String.format("  Fecha Mat.  : %s\n", matricula.getFecha()));
                            sb.append(String.format("  Hora Mat.   : %s\n", matricula.getHora()));
                        }
                    }
                }
                
                txtResultadoAlumno.setText(sb.toString());
            } else {
                txtResultadoAlumno.setText("\n  ❌ Alumno no encontrado\n  Verifique el código: " + codigo);
            }
        } catch(Exception ex) {
            txtResultadoAlumno.setText("\n  ⚠  Error: Ingrese un código válido\n  Formato: I + 9 dígitos (ej: I201822948)");
        }
    }
    
    private void buscarCurso() {
        try {
            int codigo = Integer.parseInt(txtCodigoCurso.getText().trim());
            Curso curso = arregloCursos.buscar(codigo);
            
            if(curso != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("╔════════════════════════════════════════════════════════════════════╗\n");
                sb.append("║                      DATOS DEL CURSO                               ║\n");
                sb.append("╚════════════════════════════════════════════════════════════════════╝\n\n");
                
                sb.append(String.format("  Código      : %04d\n", curso.getCodCurso()));
                sb.append(String.format("  Asignatura  : %s\n", curso.getAsignatura()));
                sb.append(String.format("  Ciclo       : %s (%s)\n", curso.getCicloTexto(), curso.getCicloNumero()));
                sb.append(String.format("  Créditos    : %d\n", curso.getCreditos()));
                sb.append(String.format("  Horas       : %d horas semanales\n\n", curso.getHoras()));
                
                // Mostrar cantidad de alumnos matriculados
                int cantidad = arregloMatriculas.contarAlumnosPorCurso(codigo);
                sb.append("  ──────────────────────────────────────────────────────────────────\n");
                sb.append(String.format("  Alumnos Matriculados: %d\n", cantidad));
                
                // Mostrar lista de alumnos matriculados
                if(cantidad > 0) {
                    sb.append("\n  Lista de Alumnos Matriculados:\n");
                    ArrayList<Matricula> matriculas = arregloMatriculas.obtenerPorCurso(codigo);
                    for(Matricula m : matriculas) {
                        Alumno a = arregloAlumnos.buscar(m.getCodAlumno());
                        if(a != null) {
                            // CORREGIDO: Mostrar código personalizado del alumno
                            sb.append(String.format("    • %s - %s\n", a.getCodigoPersonalizado(), a.getNombreCompleto()));
                        }
                    }
                }
                
                txtResultadoCurso.setText(sb.toString());
            } else {
                txtResultadoCurso.setText("\n  ❌ Curso no encontrado\n");
            }
        } catch(Exception ex) {
            txtResultadoCurso.setText("\n  ⚠  Error: Ingrese un código válido\n");
        }
    }
    
    private void buscarMatricula() {
        try {
            int numero = Integer.parseInt(txtNumMatricula.getText().trim());
            Matricula matricula = arregloMatriculas.buscar(numero);
            
            if(matricula != null) {
                Alumno alumno = arregloAlumnos.buscar(matricula.getCodAlumno());
                Curso curso = arregloCursos.buscar(matricula.getCodCurso());
                
                StringBuilder sb = new StringBuilder();
                sb.append("╔════════════════════════════════════════════════════════════════════╗\n");
                sb.append("║                   DATOS DE LA MATRÍCULA                            ║\n");
                sb.append("╚════════════════════════════════════════════════════════════════════╝\n\n");
                
                sb.append(String.format("  Número      : %d\n", matricula.getNumMatricula()));
                sb.append(String.format("  Fecha       : %s\n", matricula.getFecha()));
                sb.append(String.format("  Hora        : %s\n\n", matricula.getHora()));
                
                if(alumno != null) {
                    sb.append("╔════════════════════════════════════════════════════════════════════╗\n");
                    sb.append("║                      DATOS DEL ALUMNO                              ║\n");
                    sb.append("╚════════════════════════════════════════════════════════════════════╝\n\n");
                    
                    // CORREGIDO: Mostrar código personalizado
                    sb.append(String.format("  Código      : %s\n", alumno.getCodigoPersonalizado()));
                    sb.append(String.format("  Nombre      : %s\n", alumno.getNombreCompleto()));
                    sb.append(String.format("  DNI         : %s\n", alumno.getDni()));
                    sb.append(String.format("  Estado      : %s\n\n", alumno.getEstadoTexto()));
                }
                
                if(curso != null) {
                    sb.append("╔════════════════════════════════════════════════════════════════════╗\n");
                    sb.append("║                      DATOS DEL CURSO                               ║\n");
                    sb.append("╚════════════════════════════════════════════════════════════════════╝\n\n");
                    
                    sb.append(String.format("  Código      : %04d\n", curso.getCodCurso()));
                    sb.append(String.format("  Asignatura  : %s\n", curso.getAsignatura()));
                    sb.append(String.format("  Ciclo       : %s\n", curso.getCicloTexto()));
                    sb.append(String.format("  Créditos    : %d\n", curso.getCreditos()));
                }
                
                txtResultadoMatricula.setText(sb.toString());
            } else {
                txtResultadoMatricula.setText("\n  ❌ Matrícula no encontrada\n");
            }
        } catch(Exception ex) {
            txtResultadoMatricula.setText("\n  ⚠  Error: Ingrese un número válido\n");
        }
    }
    
    private void buscarRetiro() {
        try {
            int numero = Integer.parseInt(txtNumRetiro.getText().trim());
            Retiro retiro = arregloRetiros.buscar(numero);
            
            if(retiro != null) {
                Matricula matricula = arregloMatriculas.buscar(retiro.getNumMatricula());
                
                StringBuilder sb = new StringBuilder();
                sb.append("╔════════════════════════════════════════════════════════════════════╗\n");
                sb.append("║                     DATOS DEL RETIRO                               ║\n");
                sb.append("╚════════════════════════════════════════════════════════════════════╝\n\n");
                
                sb.append(String.format("  Número      : %d\n", retiro.getNumRetiro()));
                sb.append(String.format("  Matrícula   : %d\n", retiro.getNumMatricula()));
                sb.append(String.format("  Fecha       : %s\n", retiro.getFecha()));
                sb.append(String.format("  Hora        : %s\n\n", retiro.getHora()));
                
                if(matricula != null) {
                    Alumno alumno = arregloAlumnos.buscar(matricula.getCodAlumno());
                    Curso curso = arregloCursos.buscar(matricula.getCodCurso());
                    
                    if(alumno != null) {
                        sb.append("╔════════════════════════════════════════════════════════════════════╗\n");
                        sb.append("║                      DATOS DEL ALUMNO                              ║\n");
                        sb.append("╚════════════════════════════════════════════════════════════════════╝\n\n");
                        
                        // CORREGIDO: Mostrar código personalizado
                        sb.append(String.format("  Código      : %s\n", alumno.getCodigoPersonalizado()));
                        sb.append(String.format("  Nombre      : %s\n", alumno.getNombreCompleto()));
                        sb.append(String.format("  DNI         : %s\n", alumno.getDni()));
                        sb.append(String.format("  Estado      : %s\n\n", alumno.getEstadoTexto()));
                    }
                    
                    if(curso != null) {
                        sb.append("╔════════════════════════════════════════════════════════════════════╗\n");
                        sb.append("║                      DATOS DEL CURSO                               ║\n");
                        sb.append("╚════════════════════════════════════════════════════════════════════╝\n\n");
                        
                        sb.append(String.format("  Código      : %04d\n", curso.getCodCurso()));
                        sb.append(String.format("  Asignatura  : %s\n", curso.getAsignatura()));
                        sb.append(String.format("  Ciclo       : %s\n", curso.getCicloTexto()));
                    }
                }
                
                txtResultadoRetiro.setText(sb.toString());
            } else {
                txtResultadoRetiro.setText("\n  ❌ Retiro no encontrado\n");
            }
        } catch(Exception ex) {
            txtResultadoRetiro.setText("\n  ⚠  Error: Ingrese un número válido\n");
        }
    }
}