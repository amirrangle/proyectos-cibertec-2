
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class MantenimientoCurso extends JFrame {
    
    private JTextField txtCodigo, txtAsignatura, txtCreditos, txtHoras;
    private JComboBox<String> cboCiclo;
    private JButton btnAdicionar, btnModificar, btnEliminar, btnGrabar;
    private JTable tabla;
    private DefaultTableModel modelo;
    private Curso cursoSeleccionado;
    
    public MantenimientoCurso() {
        setTitle("Mantenimiento de Cursos");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        crearComponentes();
        listarCursos();
        
        setVisible(true);
    }
    
    private void crearComponentes() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel de datos
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos del Curso"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Código
        gbc.gridx = 0; gbc.gridy = 0;
        panelDatos.add(new JLabel("Código (4 dígitos):"), gbc);
        gbc.gridx = 1;
        txtCodigo = new JTextField(15);
        panelDatos.add(txtCodigo, gbc);
        
        // Asignatura
        gbc.gridx = 0; gbc.gridy = 1;
        panelDatos.add(new JLabel("Asignatura:"), gbc);
        gbc.gridx = 1;
        txtAsignatura = new JTextField(15);
        panelDatos.add(txtAsignatura, gbc);
        
        // Ciclo
        gbc.gridx = 2; gbc.gridy = 0;
        panelDatos.add(new JLabel("Ciclo:"), gbc);
        gbc.gridx = 3;
        String[] ciclos = {"Primero", "Segundo", "Tercero", "Cuarto", "Quinto", "Sexto"};
        cboCiclo = new JComboBox<>(ciclos);
        panelDatos.add(cboCiclo, gbc);
        
        // Créditos
        gbc.gridx = 2; gbc.gridy = 1;
        panelDatos.add(new JLabel("Créditos:"), gbc);
        gbc.gridx = 3;
        txtCreditos = new JTextField(15);
        panelDatos.add(txtCreditos, gbc);
        
        // Horas
        gbc.gridx = 2; gbc.gridy = 2;
        panelDatos.add(new JLabel("Horas:"), gbc);
        gbc.gridx = 3;
        txtHoras = new JTextField(15);
        panelDatos.add(txtHoras, gbc);
        
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
        String[] columnas = {"Código", "Asignatura", "Ciclo", "Créditos", "Horas"};
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
                seleccionarCurso();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void adicionar() {
        if (validarCampos()) {
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            
            if (Arreglos.existeCodigoCurso(codigo)) {
                JOptionPane.showMessageDialog(this, "El código de curso ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String asignatura = txtAsignatura.getText().trim();
            int ciclo = cboCiclo.getSelectedIndex();
            int creditos = Integer.parseInt(txtCreditos.getText().trim());
            int horas = Integer.parseInt(txtHoras.getText().trim());
            
            Curso c = new Curso(codigo, asignatura, ciclo, creditos, horas);
            Arreglos.agregarCurso(c);
            
            listarCursos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Curso agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void modificar() {
        if (cursoSeleccionado != null && validarCampos()) {
            cursoSeleccionado.setAsignatura(txtAsignatura.getText().trim());
            cursoSeleccionado.setCiclo(cboCiclo.getSelectedIndex());
            cursoSeleccionado.setCreditos(Integer.parseInt(txtCreditos.getText().trim()));
            cursoSeleccionado.setHoras(Integer.parseInt(txtHoras.getText().trim()));
            
            listarCursos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Curso modificado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void eliminar() {
        if (cursoSeleccionado != null) {
            if (Arreglos.cursoTieneMatriculas(cursoSeleccionado.getCodCurso())) {
                JOptionPane.showMessageDialog(this, "No se puede eliminar un curso con alumnos matriculados", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int opcion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de eliminar el curso " + cursoSeleccionado.getAsignatura() + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            
            if (opcion == JOptionPane.YES_OPTION) {
                Arreglos.eliminarCurso(cursoSeleccionado);
                listarCursos();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Curso eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void grabar() {
        Arreglos.grabarCursos();
        JOptionPane.showMessageDialog(this, "Datos grabados exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void seleccionarCurso() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            int codigo = (int) modelo.getValueAt(fila, 0);
            cursoSeleccionado = Arreglos.buscarCursoPorCodigo(codigo);
            
            if (cursoSeleccionado != null) {
                txtCodigo.setText(String.valueOf(cursoSeleccionado.getCodCurso()));
                txtCodigo.setEditable(false);
                txtAsignatura.setText(cursoSeleccionado.getAsignatura());
                cboCiclo.setSelectedIndex(cursoSeleccionado.getCiclo());
                txtCreditos.setText(String.valueOf(cursoSeleccionado.getCreditos()));
                txtHoras.setText(String.valueOf(cursoSeleccionado.getHoras()));
                
                btnModificar.setEnabled(true);
                btnEliminar.setEnabled(true);
                btnAdicionar.setEnabled(false);
            }
        }
    }
    
    private void listarCursos() {
        modelo.setRowCount(0);
        for (Curso c : Arreglos.listaCursos) {
            Object[] fila = {
                c.getCodCurso(),
                c.getAsignatura(),
                c.getCicloTexto(),
                c.getCreditos(),
                c.getHoras()
            };
            modelo.addRow(fila);
        }
    }
    
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtCodigo.setEditable(true);
        txtAsignatura.setText("");
        cboCiclo.setSelectedIndex(0);
        txtCreditos.setText("");
        txtHoras.setText("");
        
        cursoSeleccionado = null;
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnAdicionar.setEnabled(true);
        tabla.clearSelection();
    }
    
    private boolean validarCampos() {
        if (txtCodigo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese código", "Error", JOptionPane.ERROR_MESSAGE);
            txtCodigo.requestFocus();
            return false;
        }
        try {
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            if (codigo < 1000 || codigo > 9999) {
                JOptionPane.showMessageDialog(this, "El código debe tener 4 dígitos", "Error", JOptionPane.ERROR_MESSAGE);
                txtCodigo.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El código debe ser numérico", "Error", JOptionPane.ERROR_MESSAGE);
            txtCodigo.requestFocus();
            return false;
        }
        if (txtAsignatura.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese asignatura", "Error", JOptionPane.ERROR_MESSAGE);
            txtAsignatura.requestFocus();
            return false;
        }
        try {
            Integer.parseInt(txtCreditos.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Los créditos deben ser numéricos", "Error", JOptionPane.ERROR_MESSAGE);
            txtCreditos.requestFocus();
            return false;
        }
        try {
            Integer.parseInt(txtHoras.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Las horas deben ser numéricas", "Error", JOptionPane.ERROR_MESSAGE);
            txtHoras.requestFocus();
            return false;
        }
        return true;
    }
}