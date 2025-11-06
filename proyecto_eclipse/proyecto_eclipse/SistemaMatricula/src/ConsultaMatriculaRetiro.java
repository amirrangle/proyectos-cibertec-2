
import javax.swing.*;
import java.awt.*;

public class ConsultaMatriculaRetiro extends JFrame {
    
    private JTextField txtNumeroMatricula, txtNumeroRetiro;
    private JTextArea txtResultado;
    private JButton btnConsultarMatricula, btnConsultarRetiro, btnLimpiar;
    
    public ConsultaMatriculaRetiro() {
        setTitle("Consulta de Matrículas y Retiros");
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
        
        // Consulta por matrícula
        gbc.gridx = 0; gbc.gridy = 0;
        panelSuperior.add(new JLabel("Número de Matrícula:"), gbc);
        gbc.gridx = 1;
        txtNumeroMatricula = new JTextField(15);
        panelSuperior.add(txtNumeroMatricula, gbc);
        
        gbc.gridx = 2;
        btnConsultarMatricula = new JButton("Consultar Matrícula");
        btnConsultarMatricula.addActionListener(e -> consultarMatricula());
        panelSuperior.add(btnConsultarMatricula, gbc);
        
        // Consulta por retiro
        gbc.gridx = 0; gbc.gridy = 1;
        panelSuperior.add(new JLabel("Número de Retiro:"), gbc);
        gbc.gridx = 1;
        txtNumeroRetiro = new JTextField(15);
        panelSuperior.add(txtNumeroRetiro, gbc);
        
        gbc.gridx = 2;
        btnConsultarRetiro = new JButton("Consultar Retiro");
        btnConsultarRetiro.addActionListener(e -> consultarRetiro());
        panelSuperior.add(btnConsultarRetiro, gbc);
        
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
    
    private void consultarMatricula() {
        try {
            int numero = Integer.parseInt(txtNumeroMatricula.getText().trim());
            Matricula matricula = Arreglos.buscarMatriculaPorNumero(numero);
            
            if (matricula == null) {
                txtResultado.setText("No se encontró la matrícula con número: " + numero);
                return;
            }
            
            Alumno alumno = Arreglos.buscarAlumnoPorCodigo(matricula.getCodAlumno());
            Curso curso = Arreglos.buscarCursoPorCodigo(matricula.getCodCurso());
            
            StringBuilder sb = new StringBuilder();
            sb.append("========== DATOS DE LA MATRÍCULA ==========\n\n");
            sb.append("Número de Matrícula: ").append(matricula.getNumMatricula()).append("\n");
            sb.append("Fecha:               ").append(matricula.getFecha()).append("\n");
            sb.append("Hora:                ").append(matricula.getHora()).append("\n");
            
            if (alumno != null) {
                sb.append("\n========== DATOS DEL ALUMNO ==========\n\n");
                sb.append("Código:    ").append(alumno.getCodAlumno()).append("\n");
                sb.append("Nombres:   ").append(alumno.getNombres()).append("\n");
                sb.append("Apellidos: ").append(alumno.getApellidos()).append("\n");
                sb.append("DNI:       ").append(alumno.getDni()).append("\n");
                sb.append("Estado:    ").append(alumno.getEstadoTexto()).append("\n");
            }
            
            if (curso != null) {
                sb.append("\n========== DATOS DEL CURSO ==========\n\n");
                sb.append("Código:     ").append(curso.getCodCurso()).append("\n");
                sb.append("Asignatura: ").append(curso.getAsignatura()).append("\n");
                sb.append("Ciclo:      ").append(curso.getCicloTexto()).append("\n");
                sb.append("Créditos:   ").append(curso.getCreditos()).append("\n");
                sb.append("Horas:      ").append(curso.getHoras()).append("\n");
            }
            
            txtResultado.setText(sb.toString());
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void consultarRetiro() {
        try {
            int numero = Integer.parseInt(txtNumeroRetiro.getText().trim());
            Retiro retiro = Arreglos.buscarRetiroPorNumero(numero);
            
            if (retiro == null) {
                txtResultado.setText("No se encontró el retiro con número: " + numero);
                return;
            }
            
            Matricula matricula = Arreglos.buscarMatriculaPorNumero(retiro.getNumMatricula());
            
            StringBuilder sb = new StringBuilder();
            sb.append("========== DATOS DEL RETIRO ==========\n\n");
            sb.append("Número de Retiro:    ").append(retiro.getNumRetiro()).append("\n");
            sb.append("Número de Matrícula: ").append(retiro.getNumMatricula()).append("\n");
            sb.append("Fecha:               ").append(retiro.getFecha()).append("\n");
            sb.append("Hora:                ").append(retiro.getHora()).append("\n");
            
            if (matricula != null) {
                Alumno alumno = Arreglos.buscarAlumnoPorCodigo(matricula.getCodAlumno());
                Curso curso = Arreglos.buscarCursoPorCodigo(matricula.getCodCurso());
                
                if (alumno != null) {
                    sb.append("\n========== DATOS DEL ALUMNO ==========\n\n");
                    sb.append("Código:    ").append(alumno.getCodAlumno()).append("\n");
                    sb.append("Nombres:   ").append(alumno.getNombres()).append("\n");
                    sb.append("Apellidos: ").append(alumno.getApellidos()).append("\n");
                    sb.append("DNI:       ").append(alumno.getDni()).append("\n");
                    sb.append("Estado:    ").append(alumno.getEstadoTexto()).append("\n");
                }
                
                if (curso != null) {
                    sb.append("\n========== DATOS DEL CURSO ==========\n\n");
                    sb.append("Código:     ").append(curso.getCodCurso()).append("\n");
                    sb.append("Asignatura: ").append(curso.getAsignatura()).append("\n");
                    sb.append("Ciclo:      ").append(curso.getCicloTexto()).append("\n");
                    sb.append("Créditos:   ").append(curso.getCreditos()).append("\n");
                    sb.append("Horas:      ").append(curso.getHoras()).append("\n");
                }
            }
            
            txtResultado.setText(sb.toString());
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiar() {
        txtNumeroMatricula.setText("");
        txtNumeroRetiro.setText("");
        txtResultado.setText("");
    }
}