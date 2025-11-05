package guis;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import clases.Alumno;
import arreglos.ArregloAlumnos;

public class MantenimientoAlumno extends JInternalFrame implements ActionListener {
    
    private static final long serialVersionUID = 1L;
    
    private ArregloAlumnos arreglo = new ArregloAlumnos();
    
    // Componentes
    private JLabel lblCodigo, lblNombres, lblApellidos, lblDni, lblEdad, lblCelular, lblEstado, lblEstadisticas;
    private JTextField txtCodigo, txtNombres, txtApellidos, txtDni, txtEdad, txtCelular;
    private JButton btnAdicionar, btnModificar, btnEliminar, btnLimpiar, btnProcesar;
    private JTable tblAlumnos;
    private DefaultTableModel modelo;
    private JScrollPane scrollPane;
    
    // Consultas y filtros
    private JPanel panelConsultas, panelFiltros;
    private JRadioButton rbDni, rbApellidos;
    private ButtonGroup grupoConsultas;
    private JTextField txtConsulta;
    private JComboBox<String> comboEstado, comboFiltroEstado;
    
    public MantenimientoAlumno() {
        setTitle("Mantenimiento de Alumnos");
        setSize(977, 680);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        
        inicializarComponentes();
        listarAlumnos();
        configurarNavegacionTeclado();
        actualizarEstadisticas();
        
        // Asegurar foco al abrir
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(InternalFrameEvent e) {
                tblAlumnos.requestFocusInWindow();
            }
        });
    }
    
    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBackground(Color.WHITE);
        
        // === CAMPOS ===
        lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(30, 30, 100, 25);
        panelPrincipal.add(lblCodigo);
        
        txtCodigo = new JTextField();
        txtCodigo.setBounds(140, 30, 150, 25);
        txtCodigo.setEditable(false);
        txtCodigo.setBackground(new Color(240, 240, 240));
        panelPrincipal.add(txtCodigo);
        
        lblNombres = new JLabel("Nombres:");
        lblNombres.setBounds(30, 65, 100, 25);
        panelPrincipal.add(lblNombres);
        
        txtNombres = new JTextField();
        txtNombres.setBounds(140, 65, 250, 25);
        panelPrincipal.add(txtNombres);
        
        lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setBounds(30, 100, 100, 25);
        panelPrincipal.add(lblApellidos);
        
        txtApellidos = new JTextField();
        txtApellidos.setBounds(140, 100, 250, 25);
        panelPrincipal.add(txtApellidos);
        
        lblDni = new JLabel("DNI:");
        lblDni.setBounds(30, 135, 100, 25);
        panelPrincipal.add(lblDni);
        
        txtDni = new JTextField();
        txtDni.setBounds(140, 135, 150, 25);
        panelPrincipal.add(txtDni);
        
        lblEdad = new JLabel("Edad:");
        lblEdad.setBounds(30, 170, 100, 25);
        panelPrincipal.add(lblEdad);
        
        txtEdad = new JTextField();
        txtEdad.setBounds(140, 170, 80, 25);
        panelPrincipal.add(txtEdad);
        
        lblCelular = new JLabel("Celular:");
        lblCelular.setBounds(30, 205, 100, 25);
        panelPrincipal.add(lblCelular);
        
        txtCelular = new JTextField();
        txtCelular.setBounds(140, 205, 150, 25);
        panelPrincipal.add(txtCelular);
        
        lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(30, 240, 100, 25);
        panelPrincipal.add(lblEstado);
        
        comboEstado = new JComboBox<>(new String[] {"REGISTRADO", "MATRICULADO", "RETIRADO", "DESCONOCIDO"});
        comboEstado.setEnabled(false);
        comboEstado.setBounds(140, 240, 150, 25);
        comboEstado.setBackground(new Color(240, 240, 240));
        panelPrincipal.add(comboEstado);
        
        // === PANEL CONSULTAS ===
        panelConsultas = new JPanel();
        panelConsultas.setBorder(BorderFactory.createTitledBorder("Consultas"));
        panelConsultas.setLayout(null);
        panelConsultas.setBounds(460, 30, 350, 100);
        panelConsultas.setBackground(Color.WHITE);
        
        rbDni = new JRadioButton("Dni");
        rbDni.setBounds(20, 25, 80, 25);
        rbDni.setSelected(true);
        rbDni.setBackground(Color.WHITE);
        panelConsultas.add(rbDni);
        
        rbApellidos = new JRadioButton("Apellidos");
        rbApellidos.setBounds(20, 50, 100, 25);
        rbApellidos.setBackground(Color.WHITE);
        panelConsultas.add(rbApellidos);
        
        grupoConsultas = new ButtonGroup();
        grupoConsultas.add(rbDni);
        grupoConsultas.add(rbApellidos);
        
        txtConsulta = new JTextField();
        txtConsulta.setBounds(130, 30, 200, 25);
        panelConsultas.add(txtConsulta);
        
        btnProcesar = new JButton("Consultar");
        btnProcesar.setIcon(new ImageIcon(MantenimientoAlumno.class.getResource("/img/lupa.png")));
        btnProcesar.setBounds(130, 60, 200, 30);
        btnProcesar.addActionListener(this);
        panelConsultas.add(btnProcesar);
        
        panelPrincipal.add(panelConsultas);
        
        // === PANEL FILTROS ===
        panelFiltros = new JPanel();
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros"));
        panelFiltros.setLayout(null);
        panelFiltros.setBounds(460, 140, 350, 80);
        panelFiltros.setBackground(Color.WHITE);
        
        JLabel lblFiltro = new JLabel("Filtrar por Estado:");
        lblFiltro.setBounds(20, 25, 120, 25);
        lblFiltro.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelFiltros.add(lblFiltro);
        
        comboFiltroEstado = new JComboBox<>(new String[] {
            "TODOS LOS ALUMNOS", "SOLO REGISTRADOS", "SOLO MATRICULADOS", "SOLO RETIRADOS"
        });
        comboFiltroEstado.setBounds(140, 25, 150, 25);
        comboFiltroEstado.addActionListener(this);
        panelFiltros.add(comboFiltroEstado);
        
        panelPrincipal.add(panelFiltros);
        
        // === BOTONES ===
        btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setIcon(new ImageIcon(MantenimientoAlumno.class.getResource("/img/anadir.png")));
        btnAdicionar.setBounds(377, 240, 120, 30);
        btnAdicionar.setBackground(new Color(46, 204, 113));
        btnAdicionar.setForeground(Color.BLACK);
        btnAdicionar.addActionListener(this);
        panelPrincipal.add(btnAdicionar);
        
        btnModificar = new JButton("Modificar");
        btnModificar.setIcon(new ImageIcon(MantenimientoAlumno.class.getResource("/img/mejoramiento.png")));
        btnModificar.setBounds(507, 240, 120, 30);
        btnModificar.setBackground(new Color(241, 196, 15));
        btnModificar.setForeground(Color.BLACK);
        btnModificar.addActionListener(this);
        panelPrincipal.add(btnModificar);
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setIcon(new ImageIcon(MantenimientoAlumno.class.getResource("/img/usuario.png")));
        btnEliminar.setBounds(637, 240, 120, 30);
        btnEliminar.setBackground(new Color(231, 76, 60));
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.addActionListener(this);
        panelPrincipal.add(btnEliminar);
        
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setIcon(new ImageIcon(MantenimientoAlumno.class.getResource("/img/fregar.png")));
        btnLimpiar.setBounds(767, 240, 120, 30);
        btnLimpiar.setBackground(new Color(149, 165, 166));
        btnLimpiar.setForeground(Color.BLACK);
        btnLimpiar.addActionListener(this);
        panelPrincipal.add(btnLimpiar);
        
        // === TABLA ===
        modelo = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        modelo.addColumn("Código");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        modelo.addColumn("DNI");
        modelo.addColumn("Edad");
        modelo.addColumn("Celular");
        modelo.addColumn("Estado");
        
        tblAlumnos = new JTable(modelo);
        tblAlumnos.setRowHeight(25);
        tblAlumnos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblAlumnos.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { seleccionarAlumno(); }
        });
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i : new int[]{0, 3, 4, 5, 6}) {
            tblAlumnos.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        scrollPane = new JScrollPane(tblAlumnos);
        scrollPane.setBounds(30, 290, 930, 270);
        panelPrincipal.add(scrollPane);
        
        // === ESTADÍSTICAS ===
        lblEstadisticas = new JLabel();
        lblEstadisticas.setBounds(30, 570, 930, 25);
        lblEstadisticas.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEstadisticas.setForeground(new Color(52, 152, 219));
        lblEstadisticas.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(lblEstadisticas);
        
        getContentPane().add(panelPrincipal);
    }
    
    // === NAVEGACIÓN CON TECLADO (COMPLETA) ===
    private void configurarNavegacionTeclado() {
        tblAlumnos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                int row = tblAlumnos.getSelectedRow();
                int total = tblAlumnos.getRowCount();

                if (key == KeyEvent.VK_UP && row > 0) {
                    tblAlumnos.setRowSelectionInterval(row - 1, row - 1);
                    seleccionarAlumno();
                }
                else if (key == KeyEvent.VK_DOWN && row < total - 1) {
                    tblAlumnos.setRowSelectionInterval(row + 1, row + 1);
                    seleccionarAlumno();
                }
                else if (key == KeyEvent.VK_HOME && total > 0) {
                    tblAlumnos.setRowSelectionInterval(0, 0);
                    seleccionarAlumno();
                }
                else if (key == KeyEvent.VK_END && total > 0) {
                    tblAlumnos.setRowSelectionInterval(total - 1, total - 1);
                    seleccionarAlumno();
                }
                else if (key == KeyEvent.VK_ENTER && row >= 0) {
                    seleccionarAlumno();
                }
            }
        });
    }
    
    // === FILTROS ===
    private void aplicarFiltroEstado() {
        String filtro = (String) comboFiltroEstado.getSelectedItem();
        int estado = -1;

        if (filtro.equals("SOLO REGISTRADOS")) {
            estado = 0;
        } else if (filtro.equals("SOLO MATRICULADOS")) {
            estado = 1;
        } else if (filtro.equals("SOLO RETIRADOS")) {
            estado = 2;
        }

        modelo.setRowCount(0);
        for (int i = 0; i < arreglo.tamaño(); i++) {
            Alumno a = arreglo.obtener(i);
            if (estado == -1 || a.getEstado() == estado) {
                modelo.addRow(new Object[]{
                    a.getCodigoPersonalizado(),
                    a.getNombres(),
                    a.getApellidos(),
                    a.getDni(),
                    a.getEdad(),
                    a.getCelular(),
                    a.getEstadoTexto()
                });
            }
        }
        actualizarEstadisticas();
    }
    
    // === ESTADÍSTICAS ===
    private void actualizarEstadisticas() {
        int total = arreglo.tamaño();
        int[] cont = new int[4];
        for (int i = 0; i < total; i++) cont[arreglo.obtener(i).getEstado()]++;
        lblEstadisticas.setText(String.format(
            "Estadísticas: Total: %d | Registrados: %d | Matriculados: %d | Retirados: %d",
            total, cont[0], cont[1], cont[2]
        ));
    }
    
    // === ACCIONES ===
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdicionar) adicionar();
        else if (e.getSource() == btnModificar) modificar();
        else if (e.getSource() == btnEliminar) eliminar();
        else if (e.getSource() == btnLimpiar) limpiar();
        else if (e.getSource() == btnProcesar) procesarConsulta();
        else if (e.getSource() == comboFiltroEstado) aplicarFiltroEstado();
    }
    
    private void procesarConsulta() {
        String q = txtConsulta.getText().trim();
        if (q.isEmpty()) { JOptionPane.showMessageDialog(this, "Ingrese criterio"); return; }
        modelo.setRowCount(0);
        boolean found = false;
        for (int i = 0; i < arreglo.tamaño(); i++) {
            Alumno a = arreglo.obtener(i);
            boolean match = rbDni.isSelected() ? a.getDni().contains(q) :
                           a.getApellidos().toLowerCase().contains(q.toLowerCase());
            if (match) {
                modelo.addRow(new Object[]{
                    a.getCodigoPersonalizado(), a.getNombres(), a.getApellidos(),
                    a.getDni(), a.getEdad(), a.getCelular(), a.getEstadoTexto()
                });
                found = true;
            }
        }
        if (!found) { JOptionPane.showMessageDialog(this, "No se encontraron resultados"); listarAlumnos(); }
        actualizarEstadisticas();
    }
    
    private void adicionar() {
        try {
            String nombres = txtNombres.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String dni = txtDni.getText().trim();
            int edad = Integer.parseInt(txtEdad.getText().trim());
            int celular = Integer.parseInt(txtCelular.getText().trim());
            int estado = comboEstado.getSelectedIndex();
            
            if (nombres.isEmpty() || apellidos.isEmpty() || dni.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos");
                return;
            }
            if (dni.length() != 8) {
                JOptionPane.showMessageDialog(this, "DNI debe tener 8 dígitos");
                return;
            }
            if (arreglo.buscarPorDni(dni) != null) {
                JOptionPane.showMessageDialog(this, "DNI ya existe");
                return;
            }
            
            Alumno nuevo = new Alumno(Alumno.getContadorCodigo() + 1, nombres, apellidos, dni, edad, celular, estado);
            nuevo.setCodigoPersonalizado(generarCodigoPersonalizadoUnico());
            arreglo.adicionar(nuevo);
            Alumno.setContadorCodigo(nuevo.getCodAlumno());
            
            listarAlumnos();
            limpiar();
            actualizarEstadisticas();
            JOptionPane.showMessageDialog(this, "Alumno adicionado\nCódigo: " + nuevo.getCodigoPersonalizado());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Edad y celular deben ser números");
        }
    }
    
    private void modificar() {
        String cod = txtCodigo.getText().trim();
        Alumno a = arreglo.buscarPorCodigoPersonalizado(cod);
        if (a == null) { JOptionPane.showMessageDialog(this, "Alumno no encontrado"); return; }
        
        a.setNombres(txtNombres.getText().trim());
        a.setApellidos(txtApellidos.getText().trim());
        a.setDni(txtDni.getText().trim());
        a.setEdad(Integer.parseInt(txtEdad.getText().trim()));
        a.setCelular(Integer.parseInt(txtCelular.getText().trim()));
        a.setEstado(comboEstado.getSelectedIndex());
        
        arreglo.actualizar();
        listarAlumnos();
        actualizarEstadisticas();
        JOptionPane.showMessageDialog(this, "Alumno modificado");
    }
    
    private void eliminar() {
        String cod = txtCodigo.getText().trim();
        Alumno a = arreglo.buscarPorCodigoPersonalizado(cod);
        if (a == null) { JOptionPane.showMessageDialog(this, "Alumno no encontrado"); return; }
        if (a.getEstado() != 0) { JOptionPane.showMessageDialog(this, "Solo se eliminan REGISTRADOS"); return; }
        
        int op = JOptionPane.showConfirmDialog(this, "¿Eliminar a " + a.getNombres() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            arreglo.eliminar(a);
            listarAlumnos();
            limpiar();
            actualizarEstadisticas();
            JOptionPane.showMessageDialog(this, "Alumno eliminado");
        }
    }
    
    private void listarAlumnos() {
        modelo.setRowCount(0);
        for (int i = 0; i < arreglo.tamaño(); i++) {
            Alumno a = arreglo.obtener(i);
            modelo.addRow(new Object[]{
                a.getCodigoPersonalizado(), a.getNombres(), a.getApellidos(),
                a.getDni(), a.getEdad(), a.getCelular(), a.getEstadoTexto()
            });
        }
    }
    
    private void seleccionarAlumno() {
        int row = tblAlumnos.getSelectedRow();
        if (row >= 0) {
            Alumno a = arreglo.buscarPorCodigoPersonalizado((String) modelo.getValueAt(row, 0));
            if (a != null) mostrarAlumno(a);
        }
    }
    
    private void mostrarAlumno(Alumno a) {
        txtCodigo.setText(a.getCodigoPersonalizado());
        txtNombres.setText(a.getNombres());
        txtApellidos.setText(a.getApellidos());
        txtDni.setText(a.getDni());
        txtEdad.setText(String.valueOf(a.getEdad()));
        txtCelular.setText(String.valueOf(a.getCelular()));
        comboEstado.setSelectedIndex(a.getEstado());
    }
    
    private void limpiar() {
        txtCodigo.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtDni.setText("");
        txtEdad.setText("");
        txtCelular.setText("");
        txtConsulta.setText("");
        comboEstado.setSelectedIndex(0);
        comboFiltroEstado.setSelectedIndex(0);
        txtNombres.requestFocus();
    }
    
    private String generarCodigoPersonalizadoUnico() {
        String cod;
        do {
            StringBuilder sb = new StringBuilder("I");
            for (int i = 0; i < 9; i++) sb.append((int)(Math.random() * 10));
            cod = sb.toString();
        } while (arreglo.buscarPorCodigoPersonalizado(cod) != null);
        return cod;
    }
}