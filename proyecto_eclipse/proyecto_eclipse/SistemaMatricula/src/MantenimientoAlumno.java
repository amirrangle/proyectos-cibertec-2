
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class MantenimientoAlumno extends JFrame {
    
    private JTextField txtCodigo, txtNombres, txtApellidos, txtDni, txtEdad, txtCelular;
    private JLabel lblEstado;
    private JButton btnAdicionar, btnModificar, btnEliminar, btnGrabar;
    private JTable tabla;
    private DefaultTableModel modelo;
    private Alumno alumnoSeleccionado;
    
    public MantenimientoAlumno() {
        setTitle("Mantenimiento de Alumnos");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        crearComponentes();
        listarAlumnos();
        
        setVisible(true);
    }
    
    private void crearComponentes() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel de datos
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos del Alumno"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Código
        gbc.gridx = 0; gbc.gridy = 0;
        panelDatos.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1;
        txtCodigo = new JTextField(15);
        txtCodigo.setEditable(false);
        txtCodigo.setBackground(Color.LIGHT_GRAY);
        panelDatos.add(txtCodigo, gbc);
        
        // Nombres
        gbc.gridx = 0; gbc.gridy = 1;
        panelDatos.add(new JLabel("Nombres:"), gbc);
        gbc.gridx = 1;
        txtNombres = new JTextField(15);
        panelDatos.add(txtNombres, gbc);
        
        // Apellidos
        gbc.gridx = 0; gbc.gridy = 2;
        panelDatos.add(new JLabel("Apellidos:"), gbc);
        gbc.gridx = 1;
        txtApellidos = new JTextField(15);
        panelDatos.add(txtApellidos, gbc);
        
        // DNI
        gbc.gridx = 2; gbc.gridy = 0;
        panelDatos.add(new JLabel("DNI:"), gbc);
        gbc.gridx = 3;
        txtDni = new JTextField(15);
        panelDatos.add(txtDni, gbc);
        
        // Edad
        gbc.gridx = 2; gbc.gridy = 1;
        panelDatos.add(new JLabel("Edad:"), gbc);
        gbc.gridx = 3;
        txtEdad = new JTextField(15);
        panelDatos.add(txtEdad, gbc);
        
        // Celular
        gbc.gridx = 2; gbc.gridy = 2;
        panelDatos.add(new JLabel("Celular:"), gbc);
        gbc.gridx = 3;
        txtCelular = new JTextField(15);
        panelDatos.add(txtCelular, gbc);
        
        // Estado
        gbc.gridx = 0; gbc.gridy = 3;
        panelDatos.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        lblEstado = new JLabel("Registrado");
        lblEstado.setForeground(new Color(0, 128, 0));
        lblEstado.setFont(new Font("Arial", Font.BOLD, 12));
        panelDatos.add(lblEstado, gbc);
        
        add(panelDatos, BorderLayout.NORTH);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnAdicionar = new JButton("Adicionar");
        btnAdicionar.addActionListener(e -> adicionar());
        
        btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(e -> modificar());
        btnModificar.setEnabled(false);
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminar());
        btnEliminar.setEnabled(false);
        
        btnGrabar = new JButton("Grabar");
        btnGrabar.addActionListener(e -> grabar());
        
        panelBotones.add(btnAdicionar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnGrabar);
        
        add(panelBotones, BorderLayout.SOUTH);
        
        // Tabla
        String[] columnas = {"Código", "Nombres", "Apellidos", "DNI", "Edad", "Celular", "Estado"};
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
                seleccionarAlumno();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void adicionar() {
        if (validarCampos()) {
            String dni = txtDni.getText().trim();
            
            if (Arreglos.existeDniAlumno(dni)) {
                JOptionPane.showMessageDialog(this, "El DNI ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int codigo = Arreglos.generarCodigoAlumno();
            String nombres = txtNombres.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            int edad = Integer.parseInt(txtEdad.getText().trim());
            int celular = Integer.parseInt(txtCelular.getText().trim());
            
            Alumno a = new Alumno(codigo, nombres, apellidos, dni, edad, celular, 0);
            Arreglos.agregarAlumno(a);
            
            listarAlumnos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Alumno agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void modificar() {
        if (alumnoSeleccionado != null && validarCampos()) {
            alumnoSeleccionado.setNombres(txtNombres.getText().trim());
            alumnoSeleccionado.setApellidos(txtApellidos.getText().trim());
            alumnoSeleccionado.setEdad(Integer.parseInt(txtEdad.getText().trim()));
            alumnoSeleccionado.setCelular(Integer.parseInt(txtCelular.getText().trim()));
            
            listarAlumnos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Alumno modificado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void eliminar() {
        if (alumnoSeleccionado != null) {
            if (alumnoSeleccionado.getEstado() != 0) {
                JOptionPane.showMessageDialog(this, "Solo se pueden eliminar alumnos en estado Registrado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int opcion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de eliminar al alumno " + alumnoSeleccionado.getNombres() + " " + alumnoSeleccionado.getApellidos() + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            
            if (opcion == JOptionPane.YES_OPTION) {
                Arreglos.eliminarAlumno(alumnoSeleccionado);
                listarAlumnos();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Alumno eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void grabar() {
        Arreglos.grabarAlumnos();
        JOptionPane.showMessageDialog(this, "Datos grabados exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void seleccionarAlumno() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            int codigo = (int) modelo.getValueAt(fila, 0);
            alumnoSeleccionado = Arreglos.buscarAlumnoPorCodigo(codigo);
            
            if (alumnoSeleccionado != null) {
                txtCodigo.setText(String.valueOf(alumnoSeleccionado.getCodAlumno()));
                txtNombres.setText(alumnoSeleccionado.getNombres());
                txtApellidos.setText(alumnoSeleccionado.getApellidos());
                txtDni.setText(alumnoSeleccionado.getDni());
                txtDni.setEditable(false);
                txtEdad.setText(String.valueOf(alumnoSeleccionado.getEdad()));
                txtCelular.setText(String.valueOf(alumnoSeleccionado.getCelular()));
                lblEstado.setText(alumnoSeleccionado.getEstadoTexto());
                
                btnModificar.setEnabled(true);
                btnEliminar.setEnabled(true);
                btnAdicionar.setEnabled(false);
            }
        }
    }
    
    private void listarAlumnos() {
        modelo.setRowCount(0);
        for (Alumno a : Arreglos.listaAlumnos) {
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
    }
    
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtDni.setText("");
        txtDni.setEditable(true);
        txtEdad.setText("");
        txtCelular.setText("");
        lblEstado.setText("Registrado");
        
        alumnoSeleccionado = null;
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnAdicionar.setEnabled(true);
        tabla.clearSelection();
    }
    
    private boolean validarCampos() {
        if (txtNombres.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese nombres", "Error", JOptionPane.ERROR_MESSAGE);
            txtNombres.requestFocus();
            return false;
        }
        if (txtApellidos.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese apellidos", "Error", JOptionPane.ERROR_MESSAGE);
            txtApellidos.requestFocus();
            return false;
        }
        if (txtDni.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese DNI", "Error", JOptionPane.ERROR_MESSAGE);
            txtDni.requestFocus();
            return false;
        }
        if (txtDni.getText().trim().length() != 8) {
            JOptionPane.showMessageDialog(this, "El DNI debe tener 8 dígitos", "Error", JOptionPane.ERROR_MESSAGE);
            txtDni.requestFocus();
            return false;
        }
        try {
            Integer.parseInt(txtEdad.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La edad debe ser numérica", "Error", JOptionPane.ERROR_MESSAGE);
            txtEdad.requestFocus();
            return false;
        }
        try {
            Integer.parseInt(txtCelular.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El celular debe ser numérico", "Error", JOptionPane.ERROR_MESSAGE);
            txtCelular.requestFocus();
            return false;
        }
        return true;
    }
}