package guis;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import clases.*;
import arreglos.*;

public class RegistroMatricula extends JInternalFrame implements ActionListener {
    
    private static final long serialVersionUID = 1L;
    
    // Arreglos
    private ArregloAlumnos arregloAlumnos = new ArregloAlumnos();
    private ArregloCursos arregloCursos = new ArregloCursos();
    private ArregloMatriculas arreglo = new ArregloMatriculas();
    
    // Componentes
    private JPanel panelFormulario, panelBotones, panelTabla;
    private JLabel lblNumero, lblAlumno, lblCurso, lblFecha, lblHora;
    private JTextField txtNumero, txtFecha, txtHora;
    private JComboBox<String> cboAlumno, cboCurso;
    private JButton btnAdicionar, btnConsultar, btnModificar, btnCancelar, btnLimpiar;
    private JTable tblMatriculas;
    private DefaultTableModel modelo;
    private JScrollPane scrollPane;
    
    public RegistroMatricula() {
        setTitle("Registro de Matrícula");
        setSize(1100, 650);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        
        inicializarComponentes();
        cargarAlumnos();
        cargarCursos();
        listarMatriculas();
        configurarNavegacionTeclado(); // ← NUEVO

        // === CORRECTO: DENTRO DEL CONSTRUCTOR ===
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(InternalFrameEvent e) {
                SwingUtilities.invokeLater(() -> {
                    tblMatriculas.requestFocusInWindow();
                    if (tblMatriculas.getRowCount() > 0 && tblMatriculas.getSelectedRow() == -1) {
                        tblMatriculas.setRowSelectionInterval(0, 0);
                        seleccionarMatricula();
                    }
                });
            }
        });
        
  } 
    
    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        crearPanelFormulario();
        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);
        
        crearPanelTabla();
        panelPrincipal.add(panelTabla, BorderLayout.CENTER);
        
        getContentPane().add(panelPrincipal);
    }
    
    private void crearPanelFormulario() {
        panelFormulario = new JPanel(new BorderLayout(10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            "Datos de Matrícula",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(52, 152, 219)
        ));
        
        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setPreferredSize(new Dimension(1050, 120));
        
        lblNumero = new JLabel("Número Matrícula:");
        lblNumero.setFont(new Font("Segoe UI", Font.BOLD, 12));
        addComp(panelCampos, lblNumero, 0, 0, 1, 1);
        
        txtNumero = new JTextField();
        txtNumero.setPreferredSize(new Dimension(150, 25));
        txtNumero.setEditable(false);
        txtNumero.setBackground(new Color(240, 240, 240));
        addComp(panelCampos, txtNumero, 1, 0, 1, 1);
        
        lblFecha = new JLabel("Fecha:");
        lblFecha.setFont(new Font("Segoe UI", Font.BOLD, 12));
        addComp(panelCampos, lblFecha, 2, 0, 1, 1);
        
        txtFecha = new JTextField();
        txtFecha.setPreferredSize(new Dimension(120, 25));
        txtFecha.setEditable(false);
        txtFecha.setBackground(new Color(240, 240, 240));
        addComp(panelCampos, txtFecha, 3, 0, 1, 1);
        
        lblHora = new JLabel("Hora:");
        lblHora.setFont(new Font("Segoe UI", Font.BOLD, 12));
        addComp(panelCampos, lblHora, 4, 0, 1, 1);
        
        txtHora = new JTextField();
        txtHora.setPreferredSize(new Dimension(100, 25));
        txtHora.setEditable(false);
        txtHora.setBackground(new Color(240, 240, 240));
        addComp(panelCampos, txtHora, 5, 0, 1, 1);
        
        lblAlumno = new JLabel("Alumno:");
        lblAlumno.setFont(new Font("Segoe UI", Font.BOLD, 12));
        addComp(panelCampos, lblAlumno, 0, 1, 1, 1);
        
        cboAlumno = new JComboBox<String>();
        cboAlumno.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        cboAlumno.setPreferredSize(new Dimension(580, 25));
        addComp(panelCampos, cboAlumno, 1, 1, 5, 1);
        
        lblCurso = new JLabel("Curso:");
        lblCurso.setFont(new Font("Segoe UI", Font.BOLD, 12));
        addComp(panelCampos, lblCurso, 0, 2, 1, 1);
        
        cboCurso = new JComboBox<String>();
        cboCurso.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        cboCurso.setPreferredSize(new Dimension(580, 25));
        addComp(panelCampos, cboCurso, 1, 2, 5, 1);
        
        panelFormulario.add(panelCampos, BorderLayout.CENTER);
        
        crearPanelBotones();
        panelFormulario.add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void addComp(JPanel panel, Component comp, int x, int y, int width, int height) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(comp, gbc);
    }
    
    private void crearPanelBotones() {
        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnAdicionar = crearBoton("Adicionar", new Color(46, 204, 113));
        btnConsultar = crearBoton("Consultar", new Color(52, 152, 219));
        btnModificar = crearBoton("Modificar", new Color(241, 196, 15));
        btnCancelar = crearBoton("Cancelar", new Color(231, 76, 60));
        btnLimpiar = crearBoton("Limpiar", new Color(149, 165, 166));
        
        panelBotones.add(btnAdicionar);
        panelBotones.add(btnConsultar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnLimpiar);
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(this);
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(color.darker());
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(color);
            }
        });
        
        return btn;
    }
    
    private void crearPanelTabla() {
        panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            "Lista de Matrículas",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(52, 152, 219)
        ));
        
        modelo = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        modelo.addColumn("Nº Matrícula");
        modelo.addColumn("Cód. Alumno");
        modelo.addColumn("Nombre Alumno");
        modelo.addColumn("Cód. Curso");
        modelo.addColumn("Asignatura");
        modelo.addColumn("Fecha");
        modelo.addColumn("Hora");
        
        tblMatriculas = new JTable(modelo);
        tblMatriculas.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tblMatriculas.setRowHeight(25);
        tblMatriculas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblMatriculas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblMatriculas.getTableHeader().setBackground(new Color(52, 152, 219));
        tblMatriculas.getTableHeader().setForeground(Color.BLACK);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i = 0; i < 7; i++) {
            if(i != 2 && i != 4) {
                tblMatriculas.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        
        tblMatriculas.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblMatriculas.getColumnModel().getColumn(1).setPreferredWidth(90);
        tblMatriculas.getColumnModel().getColumn(2).setPreferredWidth(200);
        tblMatriculas.getColumnModel().getColumn(3).setPreferredWidth(80);
        tblMatriculas.getColumnModel().getColumn(4).setPreferredWidth(250);
        tblMatriculas.getColumnModel().getColumn(5).setPreferredWidth(100);
        tblMatriculas.getColumnModel().getColumn(6).setPreferredWidth(90);
        
        // Mouse: 1 clic → seleccionar
        tblMatriculas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    seleccionarMatricula();
                }
            }
        });
        
        scrollPane = new JScrollPane(tblMatriculas);
        panelTabla.add(scrollPane, BorderLayout.CENTER);
    }
    
    // === NAVEGACIÓN CON TECLADO (COMPLETA) ===
    private void configurarNavegacionTeclado() {
        tblMatriculas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                int row = tblMatriculas.getSelectedRow();
                int total = tblMatriculas.getRowCount();

                if (key == KeyEvent.VK_UP && row > 0) {
                    tblMatriculas.setRowSelectionInterval(row - 1, row - 1);
                    seleccionarMatricula();
                }
                else if (key == KeyEvent.VK_DOWN && row < total - 1) {
                    tblMatriculas.setRowSelectionInterval(row + 1, row + 1);
                    seleccionarMatricula();
                }
                else if (key == KeyEvent.VK_HOME && total > 0) {
                    tblMatriculas.setRowSelectionInterval(0, 0);
                    seleccionarMatricula();
                }
                else if (key == KeyEvent.VK_END && total > 0) {
                    tblMatriculas.setRowSelectionInterval(total - 1, total - 1);
                    seleccionarMatricula();
                }
                else if (key == KeyEvent.VK_ENTER && row >= 0) {
                    seleccionarMatricula();
                }
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnAdicionar) {
            adicionar();
        }
        else if(e.getSource() == btnConsultar) {
            consultar();
        }
        else if(e.getSource() == btnModificar) {
            modificar();
        }
        else if(e.getSource() == btnCancelar) {
            cancelar();
        }
        else if(e.getSource() == btnLimpiar) {
            limpiar();
        }
    }
    
    private void adicionar() {
        try {
            if(cboAlumno.getSelectedIndex() == -1) {
                error("Seleccione un alumno");
                return;
            }
            if(cboCurso.getSelectedIndex() == -1) {
                error("Seleccione un curso");
                return;
            }
            
            int codAlumno = obtenerCodigoAlumno();
            int codCurso = obtenerCodigoCurso();
            
            if(arreglo.alumnoMatriculado(codAlumno)) {
                error("El alumno ya está matriculado en un curso");
                return;
            }
            
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
            Date ahora = new Date();
            String fecha = formatoFecha.format(ahora);
            String hora = formatoHora.format(ahora);
            
            Matricula nueva = new Matricula(codAlumno, codCurso, fecha, hora);
            arreglo.adicionar(nueva);
            
            Alumno alumno = arregloAlumnos.buscar(codAlumno);
            alumno.setEstado(1);
            arregloAlumnos.actualizar();
            
            listarMatriculas();
            cargarAlumnos();
            limpiar();
            mensaje("Matrícula registrada correctamente\nNúmero: " + nueva.getNumMatricula());
            
        } catch(Exception ex) {
            error("Error al matricular: " + ex.getMessage());
        }
    }
    
    private void consultar() {
        try {
            int numero = leerNumero();
            Matricula matricula = arreglo.buscar(numero);
            if(matricula != null) {
                mostrarMatricula(matricula);
                mensaje("Matrícula encontrada");
            } else {
                error("Matrícula no encontrada");
            }
        } catch(Exception ex) {
            error("Ingrese un número válido");
        }
    }
    
    private void modificar() {
        try {
            int numero = leerNumero();
            Matricula matricula = arreglo.buscar(numero);
            if(matricula != null) {
                if(cboCurso.getSelectedIndex() == -1) {
                    error("Seleccione un curso");
                    return;
                }
                int codCurso = obtenerCodigoCurso();
                matricula.setCodCurso(codCurso);
                arreglo.actualizar();
                listarMatriculas();
                mensaje("Curso de matrícula modificado correctamente");
            } else {
                error("Matrícula no encontrada");
            }
        } catch(Exception ex) {
            error("Error al modificar: " + ex.getMessage());
        }
    }
    
    private void cancelar() {
        try {
            int numero = leerNumero();
            Matricula matricula = arreglo.buscar(numero);
            if(matricula != null) {
                Alumno alumno = arregloAlumnos.buscar(matricula.getCodAlumno());
                if(alumno.getEstado() == 2) {
                    error("No se puede cancelar\nEl alumno está retirado");
                    return;
                }
                int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de cancelar esta matrícula?\n" +
                    "Alumno: " + alumno.getNombreCompleto(),
                    "Confirmar Cancelación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                if(opcion == JOptionPane.YES_OPTION) {
                    arreglo.eliminar(matricula);
                    alumno.setEstado(0);
                    arregloAlumnos.actualizar();
                    listarMatriculas();
                    cargarAlumnos();
                    limpiar();
                    mensaje("Matrícula cancelada correctamente");
                }
            } else {
                error("Matrícula no encontrada");
            }
        } catch(Exception ex) {
            error("Error al cancelar: " + ex.getMessage());
        }
    }
    
    private void cargarAlumnos() {
        cboAlumno.removeAllItems();
        for(int i = 0; i < arregloAlumnos.tamaño(); i++) {
            Alumno a = arregloAlumnos.obtener(i);
            if(a.getEstado() == 0) {
                cboAlumno.addItem(String.format("[%d] %s", a.getCodAlumno(), a.getNombreCompleto()));
            }
        }
    }
    
    private void cargarCursos() {
        cboCurso.removeAllItems();
        for(int i = 0; i < arregloCursos.tamaño(); i++) {
            Curso c = arregloCursos.obtener(i);
            cboCurso.addItem(String.format("[%04d] %s - Ciclo %s", 
                c.getCodCurso(), c.getAsignatura(), c.getCicloTexto()));
        }
    }
    
    private void listarMatriculas() {
        modelo.setRowCount(0);
        for(int i = 0; i < arreglo.tamaño(); i++) {
            Matricula m = arreglo.obtener(i);
            Alumno a = arregloAlumnos.buscar(m.getCodAlumno());
            Curso c = arregloCursos.buscar(m.getCodCurso());
            Object[] fila = {
                m.getNumMatricula(),
                m.getCodAlumno(),
                a != null ? a.getNombreCompleto() : "N/A",
                m.getCodCurso(),
                c != null ? c.getAsignatura() : "N/A",
                m.getFecha(),
                m.getHora()
            };
            modelo.addRow(fila);
        }
    }
    
    private void seleccionarMatricula() {
        int fila = tblMatriculas.getSelectedRow();
        if(fila >= 0) {
            Matricula m = arreglo.obtener(fila);
            mostrarMatricula(m);
        }
    }
    
    private void mostrarMatricula(Matricula m) {
        txtNumero.setText(String.valueOf(m.getNumMatricula()));
        txtFecha.setText(m.getFecha());
        txtHora.setText(m.getHora());
        
        for(int i = 0; i < cboAlumno.getItemCount(); i++) {
            String item = cboAlumno.getItemAt(i);
            if(item.startsWith("[" + m.getCodAlumno() + "]")) {
                cboAlumno.setSelectedIndex(i);
                break;
            }
        }
        
        for(int i = 0; i < cboCurso.getItemCount(); i++) {
            String item = cboCurso.getItemAt(i);
            if(item.contains("[" + String.format("%04d", m.getCodCurso()) + "]")) {
                cboCurso.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void limpiar() {
        txtNumero.setText("");
        txtFecha.setText("");
        txtHora.setText("");
        cboAlumno.setSelectedIndex(-1);
        cboCurso.setSelectedIndex(-1);
    }
    
    private int leerNumero() {
        String texto = txtNumero.getText().trim();
        if(texto.isEmpty()) throw new RuntimeException("Ingrese número de matrícula");
        return Integer.parseInt(texto);
    }
    
    private int obtenerCodigoAlumno() {
        String item = (String) cboAlumno.getSelectedItem();
        String codigo = item.substring(item.indexOf("[") + 1, item.indexOf("]"));
        return Integer.parseInt(codigo);
    }
    
    private int obtenerCodigoCurso() {
        String item = (String) cboCurso.getSelectedItem();
        String codigo = item.substring(item.indexOf("[") + 1, item.indexOf("]"));
        return Integer.parseInt(codigo);
    }
    
    private void mensaje(String texto) {
        JOptionPane.showMessageDialog(this, texto, "Información", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void error(String texto) {
        JOptionPane.showMessageDialog(this, texto, "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}