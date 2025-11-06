import javax.swing.*;
import java.awt.*;

public class ConsultaAlumnoCurso extends JFrame {
    
    private JTextField txtCodigoAlumno, txtCodigoCurso;
    private JTextArea txtResultado;
    private JButton btnConsultarAlumno, btnConsultarCurso, btnLimpiar;
    
    public ConsultaAlumnoCurso() {
        setTitle("Consulta de Alumnos y Cursos");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        crearComponentes();
        
        setVisible(true);
    }
    
    private void crearComponentes() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Búsqueda"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Consulta por alumno
        gbc.gridx = 0; gbc.gridy = 0;
        panelSuperior.add(new JLabel("Código de Alumno:"), gbc);
        gbc.gridx = 1;
        txtCodigoAlumno = new JTextField(15);
        panelSuperior.add(txtCodigoAlumno, gbc);
        
        gbc.gridx = 2;
        btnConsultarAlumno = new JButton("Consultar Alumno");
        btnConsultarAlumno.addActionListener(e -> consultarAlumno());
        panelSuperior.add(btnConsultarAlumno, gbc);
        
        // Consulta por curso
        gbc.gridx = 0; gbc.gridy = 1;
        panelSuperior.add(new JLabel("Código de Curso:"), gbc);
        gbc.gridx = 1;
        txtCodigoCurso = new JTextField(15);
        panelSuperior.add(txtCodigoCurso, gbc);
        
        gbc.gridx = 2;
        btnConsultarCurso = new JButton("Consultar Curso");
        btnConsultarCurso.addActionListener(e -> consultarCurso());
        panelSuperior.add(btnConsultarCurso, gbc);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        // Área de resultados
        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(txtResultado);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Resultados"));
        add(scrollPane, BorderLayout.CENTER);
        
        // Botón limpiar
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(e -> limpiar());
        panelInferior.add(btnLimpiar);
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private void consultarAlumno() {
        try {
            int codigo = Integer.parseInt(txtCodigoAlumno.getText().trim());
            Alumno alumno = Arreglos.buscarAlumnoPorCodigo(codigo);
            
            if (alumno == null) {
                txtResultado.setText("No se encontró el alumno con código: " + codigo);
                return;
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("========== DATOS DEL ALUMNO ==========\n\n");
            sb.append("Código:    ").append(alumno.getCodAlumno()).append("\n");
            sb.append("Nombres:   ").append(alumno.getNombres()).append("\n");
            sb.append("Apellidos: ").append(alumno.getApellidos()).append("\n");
            sb.append("DNI:       ").append(alumno.getDni()).append("\n");
            sb.append("Edad:      ").append(alumno.getEdad()).append(" años\n");
            sb.append("Celular:   ").append(alumno.getCelular()).append("\n");
            sb.append("Estado:    ").append(alumno.getEstadoTexto()).append("\n");
            
            // Si está matriculado, mostrar el curso
            if (alumno.getEstado() == 1 || alumno.getEstado() == 2) {
                Matricula matricula = Arreglos.buscarMatriculaPorAlumno(codigo);
                if (matricula != null) {
                    Curso curso = Arreglos.buscarCursoPorCodigo(matricula.getCodCurso());
                    if (curso != null) {
                        sb.append("\n========== CURSO MATRICULADO ==========\n\n");
                        sb.append("Código:     ").append(curso.getCodCurso()).append("\n");
                        sb.append("Asignatura: ").append(curso.getAsignatura()).append("\n");
                        sb.append("Ciclo:      ").append(curso.getCicloTexto()).append("\n");
                        sb.append("Créditos:   ").append(curso.getCreditos()).append("\n");
                        sb.append("Horas:      ").append(curso.getHoras()).append("\n");
                    }
                }
            }
            
            txtResultado.setText(sb.toString());
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un código válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void consultarCurso() {
        try {
            int codigo = Integer.parseInt(txtCodigoCurso.getText().trim());
            Curso curso = Arreglos.buscarCursoPorCodigo(codigo);
            
            if (curso == null) {
                txtResultado.setText("No se encontró el curso con código: " + codigo);
                return;
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("========== DATOS DEL CURSO ==========\n\n");
            sb.append("Código:     ").append(curso.getCodCurso()).append("\n");
            sb.append("Asignatura: ").append(curso.getAsignatura()).append("\n");
            sb.append("Ciclo:      ").append(curso.getCicloTexto()).append("\n");
            sb.append("Créditos:   ").append(curso.getCreditos()).append("\n");
            sb.append("Horas:      ").append(curso.getHoras()).append("\n");
            
            txtResultado.setText(sb.toString());
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un código válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiar() {
        txtCodigoAlumno.setText("");
        txtCodigoCurso.setText("");
        txtResultado.setText("");
    }
}