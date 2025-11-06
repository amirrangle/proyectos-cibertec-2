import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class RegistroRetiro extends JFrame {
    
    private JTextField txtNumero, txtFecha, txtHora;
    private JComboBox<String> cboMatricula;
    private JButton btnAdicionar, btnModificar, btnCancelar, btnGrabar;
    private JTable tabla;
    private DefaultTableModel modelo;
    private Retiro retiroSeleccionado;
    
    public RegistroRetiro() {
        setTitle("Registro de Retiro");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        crearComponentes();
        listarRetiros();
        
        setVisible(true);
    }
    
    private void crearComponentes() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel de datos
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos del Retiro"));
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
        
        // Matrícula
        gbc.gridx = 0; gbc.gridy = 2;
        panelDatos.add(new JLabel("Matrícula:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        cboMatricula = new JComboBox<>();
        cargarMatriculas();
        panelDatos.add(cboMatricula, gbc);
        
        add(panelDatos, BorderLayout.NORTH);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnAdicionar = new JButton("Adicionar");
        btnAdicionar.addActionListener(e -> adicionar());
        
        btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(e -> modificar());
        btnModificar.setEnabled(false);
        
        btnCancelar = new JButton("Cancelar Retiro");
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
        String[] columnas = {"Número Retiro", "Número Matrícula", "Alumno", "Curso", "Fecha", "Hora"};
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
                seleccionarRetiro();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void cargarMatriculas() {
        cboMatricula.removeAllItems();
        cboMatricula.addItem("Seleccione una matrícula");
        for (Matricula m : Arreglos.listaMatriculas) {
            Alumno alumno = Arreglos.buscarAlumnoPorCodigo(m.getCodAlumno());
            Curso curso = Arreglos.buscarCursoPorCodigo(m.getCodCurso());
            
            if (alumno != null && curso != null && alumno.getEstado() == 1) {
                String texto = m.getNumMatricula() + " - " + alumno.getNombres() + " " + 
                              alumno.getApellidos() + " - " + curso.getAsignatura();
                cboMatricula.addItem(texto);
            }
        }
    }
    
    private void adicionar() {
        if (validarCampos()) {
            int numMatricula = obtenerNumeroMatricula();
            
            int numRetiro = Arreglos.generarNumeroRetiro();
            String fecha = Arreglos.obtenerFechaActual();
            String hora = Arreglos.obtenerHoraActual();
            
            Retiro r = new Retiro(numRetiro, numMatricula, fecha, hora);
            Arreglos.agregarRetiro(r);
            
            // Cambiar estado del alumno a retirado
            Matricula matricula = Arreglos.buscarMatriculaPorNumero(numMatricula);
            if (matricula != null) {
                Alumno alumno = Arreglos.buscarAlumnoPorCodigo(matricula.getCodAlumno());
                if (alumno != null) {
                    alumno.setEstado(2);
                }
            }
            
            listarRetiros();
            cargarMatriculas();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Retiro registrado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void modificar() {
        if (retiroSeleccionado != null && validarCampos()) {
            int numMatricula = obtenerNumeroMatricula();
            retiroSeleccionado.setNumMatricula(numMatricula);
            
            listarRetiros();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Retiro modificado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void cancelar() {
        if (retiroSeleccionado != null) {
            Matricula matricula = Arreglos.buscarMatriculaPorNumero(retiroSeleccionado.getNumMatricula());
            
            if (matricula != null) {
                Alumno alumno = Arreglos.buscarAlumnoPorCodigo(matricula.getCodAlumno());
                
                if (alumno != null && alumno.getEstado() != 2) {
                    JOptionPane.showMessageDialog(this, "Solo se puede cancelar el retiro de un alumno retirado", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                int opcion = JOptionPane.showConfirmDialog(this, 
                    "¿Está seguro de cancelar este retiro?",
                    "Confirmar cancelación", JOptionPane.YES_NO_OPTION);
                
                if (opcion == JOptionPane.YES_OPTION) {
                    Arreglos.eliminarRetiro(retiroSeleccionado);
                    
                    // Cambiar estado del alumno a matriculado
                    if (alumno != null) {
                        alumno.setEstado(1);
                    }
                    
                    listarRetiros();
                    cargarMatriculas();
                    limpiarCampos();
                    JOptionPane.showMessageDialog(this, "Retiro cancelado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    
    private void grabar() {
        Arreglos.grabarRetiros();
        Arreglos.grabarAlumnos();
        JOptionPane.showMessageDialog(this, "Datos grabados exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void seleccionarRetiro() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            int numero = (int) modelo.getValueAt(fila, 0);
            retiroSeleccionado = Arreglos.buscarRetiroPorNumero(numero);
            
            if (retiroSeleccionado != null) {
                txtNumero.setText(String.valueOf(retiroSeleccionado.getNumRetiro()));
                txtFecha.setText(retiroSeleccionado.getFecha());
                txtHora.setText(retiroSeleccionado.getHora());
                
                // Seleccionar matrícula
                Matricula matricula = Arreglos.buscarMatriculaPorNumero(retiroSeleccionado.getNumMatricula());
                if (matricula != null) {
                    Alumno alumno = Arreglos.buscarAlumnoPorCodigo(matricula.getCodAlumno());
                    Curso curso = Arreglos.buscarCursoPorCodigo(matricula.getCodCurso());
                    
                    if (alumno != null && curso != null) {
                        String texto = matricula.getNumMatricula() + " - " + alumno.getNombres() + " " + 
                                      alumno.getApellidos() + " - " + curso.getAsignatura();
                        cboMatricula.setSelectedItem(texto);
                    }
                }
                
                btnModificar.setEnabled(true);
                btnCancelar.setEnabled(true);
                btnAdicionar.setEnabled(false);
            }
        }
    }
    
    private void listarRetiros() {
        modelo.setRowCount(0);
        for (Retiro r : Arreglos.listaRetiros) {
            Matricula matricula = Arreglos.buscarMatriculaPorNumero(r.getNumMatricula());
            
            String nombreAlumno = "Desconocido";
            String nombreCurso = "Desconocido";
            
            if (matricula != null) {
                Alumno alumno = Arreglos.buscarAlumnoPorCodigo(matricula.getCodAlumno());
                Curso curso = Arreglos.buscarCursoPorCodigo(matricula.getCodCurso());
                
                nombreAlumno = alumno != null ? alumno.getNombres() + " " + alumno.getApellidos() : "Desconocido";
                nombreCurso = curso != null ? curso.getAsignatura() : "Desconocido";
            }
            
            Object[] fila = {
                r.getNumRetiro(),
                r.getNumMatricula(),
                nombreAlumno,
                nombreCurso,
                r.getFecha(),
                r.getHora()
            };
            modelo.addRow(fila);
        }
    }
    
    private void limpiarCampos() {
        txtNumero.setText("");
        txtFecha.setText("");
        txtHora.setText("");
        cboMatricula.setSelectedIndex(0);
        
        retiroSeleccionado = null;
        btnModificar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnAdicionar.setEnabled(true);
        tabla.clearSelection();
    }
    
    private boolean validarCampos() {
        if (cboMatricula.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una matrícula", "Error", JOptionPane.ERROR_MESSAGE);
            cboMatricula.requestFocus();
            return false;
        }
        return true;
    }
    
    private int obtenerNumeroMatricula() {
        String texto = (String) cboMatricula.getSelectedItem();
        if (texto != null && !texto.equals("Seleccione una matrícula")) {
            return Integer.parseInt(texto.split(" - ")[0]);
        }
        return 0;
    }
}