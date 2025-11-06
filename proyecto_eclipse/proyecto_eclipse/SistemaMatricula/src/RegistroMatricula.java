
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class RegistroMatricula extends JFrame {
    
    private JTextField txtNumero, txtFecha, txtHora;
    private JComboBox<String> cboAlumno, cboCurso;
    private JButton btnAdicionar, btnModificar, btnCancelar, btnGrabar;
    private JTable tabla;
    private DefaultTableModel modelo;
    private Matricula matriculaSeleccionada;
    
    public RegistroMatricula() {
        setTitle("Registro de Matrícula");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        crearComponentes();
        listarMatriculas();
        
        setVisible(true);
    }
    
    private void crearComponentes() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel de datos
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos de la Matrícula"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Número
        gbc.gridx = 0; gbc.gridy = 0;
        panelDatos.add(new JLabel("Número:"), gbc);
        gbc.gridx = 1;
        txtNumero = new JTextField(15);
        txtNumero.setEditable(false);
        txtNumero.setBackground(Color.LIGHT_GRAY);
        panelDatos.add(txtNumero, gbc);
        
        // Fecha
        gbc.gridx = 2; gbc.gridy = 0;
        panelDatos.add(new JLabel("Fecha:"), gbc);
        gbc.gridx = 3;
        txtFecha = new JTextField(15);
        txtFecha.setEditable(false);
        txtFecha.setBackground(Color.LIGHT_GRAY);
        panelDatos.add(txtFecha, gbc);
        
        // Hora
        gbc.gridx = 0; gbc.gridy = 1;
        panelDatos.add(new JLabel("Hora:"), gbc);
        gbc.gridx = 1;
        txtHora = new JTextField(15);
        txtHora.setEditable(false);
        txtHora.setBackground(Color.LIGHT_GRAY);
        panelDatos.add(txtHora, gbc);
        
        // Alumno
        gbc.gridx = 0; gbc.gridy = 2;
        panelDatos.add(new JLabel("Alumno:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        cboAlumno = new JComboBox<>();
        cargarAlumnos();
        panelDatos.add(cboAlumno, gbc);
        
        // Curso
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        panelDatos.add(new JLabel("Curso:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        cboCurso = new JComboBox<>();
        cargarCursos();
        panelDatos.add(cboCurso, gbc);
        
        add(panelDatos, BorderLayout.NORTH);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnAdicionar = new JButton("Adicionar");
        btnAdicionar.addActionListener(e -> adicionar());
        
        btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(e -> modificar());
        btnModificar.setEnabled(false);
        
        btnCancelar = new JButton("Cancelar Matrícula");
        btnCancelar.addActionListener(e -> cancelar());
        btnCancelar.setEnabled(false);
        
        btnGrabar = new JButton("Grabar");
        btnGrabar.addActionListener(e -> grabar());
        
        panelBotones.add(btnAdicionar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnGrabar);
        
        add(panelBotones, BorderLayout.SOUTH);
        
        // Tabla
        String[] columnas = {"Número", "Código Alumno", "Alumno", "Código Curso", "Curso", "Fecha", "Hora"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarMatricula();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void cargarAlumnos() {
        cboAlumno.removeAllItems();
        cboAlumno.addItem("Seleccione un alumno");
        for (Alumno a : Arreglos.listaAlumnos) {
            if (a.getEstado() == 0) { // Solo alumnos registrados
                cboAlumno.addItem(a.getCodAlumno() + " - " + a.getNombres() + " " + a.getApellidos());
            }
        }
    }
    
    private void cargarCursos() {
        cboCurso.removeAllItems();
        cboCurso.addItem("Seleccione un curso");
        for (Curso c : Arreglos.listaCursos) {
            cboCurso.addItem(c.getCodCurso() + " - " + c.getAsignatura());
        }
    }
    
    private void adicionar() {
        if (validarCampos()) {
            int codAlumno = obtenerCodigoAlumno();
            int codCurso = obtenerCodigoCurso();
            
            if (Arreglos.alumnoYaMatriculado(codAlumno)) {
                JOptionPane.showMessageDialog(this, "El alumno ya está matriculado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int numMatricula = Arreglos.generarNumeroMatricula();
            String fecha = Arreglos.obtenerFechaActual();
            String hora = Arreglos.obtenerHoraActual();
            
            Matricula m = new Matricula(numMatricula, codAlumno, codCurso, fecha, hora);
            Arreglos.agregarMatricula(m);
            
            // Cambiar estado del alumno a matriculado
            Alumno alumno = Arreglos.buscarAlumnoPorCodigo(codAlumno);
            if (alumno != null) {
                alumno.setEstado(1);
            }
            
            listarMatriculas();
            cargarAlumnos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Matrícula registrada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void modificar() {
        if (matriculaSeleccionada != null && validarCampos()) {
            int codCurso = obtenerCodigoCurso();
            matriculaSeleccionada.setCodCurso(codCurso);
            
            listarMatriculas();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Matrícula modificada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void cancelar() {
        if (matriculaSeleccionada != null) {
            Alumno alumno = Arreglos.buscarAlumnoPorCodigo(matriculaSeleccionada.getCodAlumno());
            
            if (alumno != null && alumno.getEstado() == 2) {
                JOptionPane.showMessageDialog(this, "No se puede cancelar la matrícula de un alumno retirado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int opcion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de cancelar esta matrícula?",
                "Confirmar cancelación", JOptionPane.YES_NO_OPTION);
            
            if (opcion == JOptionPane.YES_OPTION) {
                Arreglos.eliminarMatricula(matriculaSeleccionada);
                
                // Cambiar estado del alumno a registrado
                if (alumno != null) {
                    alumno.setEstado(0);
                }
                
                listarMatriculas();
                cargarAlumnos();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Matrícula cancelada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void grabar() {
        Arreglos.grabarMatriculas();
        Arreglos.grabarAlumnos();
        JOptionPane.showMessageDialog(this, "Datos grabados exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void seleccionarMatricula() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            int numero = (int) modelo.getValueAt(fila, 0);
            matriculaSeleccionada = Arreglos.buscarMatriculaPorNumero(numero);
            
            if (matriculaSeleccionada != null) {
                txtNumero.setText(String.valueOf(matriculaSeleccionada.getNumMatricula()));
                txtFecha.setText(matriculaSeleccionada.getFecha());
                txtHora.setText(matriculaSeleccionada.getHora());
                
                // Seleccionar alumno (deshabilitado para modificación)
                Alumno alumno = Arreglos.buscarAlumnoPorCodigo(matriculaSeleccionada.getCodAlumno());
                if (alumno != null) {
                    cboAlumno.setSelectedItem(alumno.getCodAlumno() + " - " + alumno.getNombres() + " " + alumno.getApellidos());
                }
                cboAlumno.setEnabled(false);
                
                // Seleccionar curso
                Curso curso = Arreglos.buscarCursoPorCodigo(matriculaSeleccionada.getCodCurso());
                if (curso != null) {
                    cboCurso.setSelectedItem(curso.getCodCurso() + " - " + curso.getAsignatura());
                }
                
                btnModificar.setEnabled(true);
                btnCancelar.setEnabled(true);
                btnAdicionar.setEnabled(false);
            }
        }
    }
    
    private void listarMatriculas() {
        modelo.setRowCount(0);
        for (Matricula m : Arreglos.listaMatriculas) {
            Alumno alumno = Arreglos.buscarAlumnoPorCodigo(m.getCodAlumno());
            Curso curso = Arreglos.buscarCursoPorCodigo(m.getCodCurso());
            
            String nombreAlumno = alumno != null ? alumno.getNombres() + " " + alumno.getApellidos() : "Desconocido";
            String nombreCurso = curso != null ? curso.getAsignatura() : "Desconocido";
            
            Object[] fila = {
                m.getNumMatricula(),
                m.getCodAlumno(),
                nombreAlumno,
                m.getCodCurso(),
                nombreCurso,
                m.getFecha(),
                m.getHora()
            };
            modelo.addRow(fila);
        }
    }
    
    private void limpiarCampos() {
        txtNumero.setText("");
        txtFecha.setText("");
        txtHora.setText("");
        cboAlumno.setSelectedIndex(0);
        cboAlumno.setEnabled(true);
        cboCurso.setSelectedIndex(0);
        
        matriculaSeleccionada = null;
        btnModificar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnAdicionar.setEnabled(true);
        tabla.clearSelection();
    }
    
    private boolean validarCampos() {
        if (cboAlumno.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un alumno", "Error", JOptionPane.ERROR_MESSAGE);
            cboAlumno.requestFocus();
            return false;
        }
        if (cboCurso.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un curso", "Error", JOptionPane.ERROR_MESSAGE);
            cboCurso.requestFocus();
            return false;
        }
        return true;
    }
    
    private int obtenerCodigoAlumno() {
        String texto = (String) cboAlumno.getSelectedItem();
        if (texto != null && !texto.equals("Seleccione un alumno")) {
            return Integer.parseInt(texto.split(" - ")[0]);
        }
        return 0;
    }
    
    private int obtenerCodigoCurso() {
        String texto = (String) cboCurso.getSelectedItem();
        if (texto != null && !texto.equals("Seleccione un curso")) {
            return Integer.parseInt(texto.split(" - ")[0]);
        }
        return 0;
    }
}