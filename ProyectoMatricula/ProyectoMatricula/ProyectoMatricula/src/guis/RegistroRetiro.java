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

public class RegistroRetiro extends JInternalFrame implements ActionListener {
    
    private static final long serialVersionUID = 1L;
    
    // Arreglos
    private ArregloAlumnos arregloAlumnos = new ArregloAlumnos();
    private ArregloCursos arregloCursos = new ArregloCursos();
    private ArregloMatriculas arregloMatriculas = new ArregloMatriculas();
    private ArregloRetiros arreglo = new ArregloRetiros();
    
    // Componentes
    private JPanel panelFormulario, panelBotones, panelTabla;
    private JLabel lblNumero, lblMatricula, lblFecha, lblHora;
    private JTextField txtNumero, txtFecha, txtHora;
    private JComboBox<String> cboMatricula;
    private JButton btnAdicionar, btnConsultar, btnModificar, btnCancelar, btnLimpiar;
    private JTable tblRetiros;
    private DefaultTableModel modelo;
    private JScrollPane scrollPane;
    
    public RegistroRetiro() {
        setTitle("Registro de Retiro");
        setSize(1100, 650);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        
        inicializarComponentes();
        cargarMatriculas();
        listarRetiros();
        configurarNavegacionTeclado(); // ← NUEVO

        // === FOCO AUTOMÁTICO AL ABRIR (DENTRO DEL CONSTRUCTOR) ===
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(InternalFrameEvent e) {
                SwingUtilities.invokeLater(() -> {
                    tblRetiros.requestFocusInWindow();
                    if (tblRetiros.getRowCount() > 0 && tblRetiros.getSelectedRow() == -1) {
                        tblRetiros.setRowSelectionInterval(0, 0);
                        seleccionarRetiro();
                    }
                });
            }
        });
        // ========================================================
    } // ← CIERRE CORRECTO DEL CONSTRUCTOR
    
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
            BorderFactory.createLineBorder(new Color(231, 76, 60), 2),
            "Datos de Retiro",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(231, 76, 60)
        ));
        
        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setPreferredSize(new Dimension(1050, 85));
        
        lblNumero = new JLabel("Número Retiro:");
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
        
        lblMatricula = new JLabel("Matrícula:");
        lblMatricula.setFont(new Font("Segoe UI", Font.BOLD, 12));
        addComp(panelCampos, lblMatricula, 0, 1, 1, 1);
        
        cboMatricula = new JComboBox<String>();
        cboMatricula.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        cboMatricula.setPreferredSize(new Dimension(580, 25));
        addComp(panelCampos, cboMatricula, 1, 1, 5, 1);
        
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
        
        btnAdicionar = crearBoton("Adicionar", new Color(231, 76, 60));
        btnConsultar = crearBoton("Consultar", new Color(52, 152, 219));
        btnModificar = crearBoton("Modificar", new Color(241, 196, 15));
        btnCancelar = crearBoton("Cancelar Retiro", new Color(46, 204, 113));
        btnLimpiar = crearBoton("Limpiar", new Color(149, 165, 166));
        
        panelBotones.add(btnAdicionar);
        panelBotones.add(btnConsultar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnLimpiar);
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(130, 35));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
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
            BorderFactory.createLineBorder(new Color(231, 76, 60), 2),
            "Lista de Retiros",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(231, 76, 60)
        ));
        
        modelo = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        modelo.addColumn("Nº Retiro");
        modelo.addColumn("Nº Matrícula");
        modelo.addColumn("Alumno");
        modelo.addColumn("Curso");
        modelo.addColumn("Fecha Retiro");
        modelo.addColumn("Hora Retiro");
        
        tblRetiros = new JTable(modelo);
        tblRetiros.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tblRetiros.setRowHeight(25);
        tblRetiros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblRetiros.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblRetiros.getTableHeader().setBackground(new Color(231, 76, 60));
        tblRetiros.getTableHeader().setForeground(Color.BLACK);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblRetiros.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblRetiros.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tblRetiros.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tblRetiros.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        
        tblRetiros.getColumnModel().getColumn(0).setPreferredWidth(90);
        tblRetiros.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblRetiros.getColumnModel().getColumn(2).setPreferredWidth(200);
        tblRetiros.getColumnModel().getColumn(3).setPreferredWidth(250);
        tblRetiros.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblRetiros.getColumnModel().getColumn(5).setPreferredWidth(90);
        
        // Mouse: 1 clic → seleccionar
        tblRetiros.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    seleccionarRetiro();
                }
            }
        });
        
        scrollPane = new JScrollPane(tblRetiros);
        panelTabla.add(scrollPane, BorderLayout.CENTER);
    }
    
    // === NAVEGACIÓN CON TECLADO (COMPLETA) ===
    private void configurarNavegacionTeclado() {
        tblRetiros.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                int row = tblRetiros.getSelectedRow();
                int total = tblRetiros.getRowCount();

                if (key == KeyEvent.VK_UP && row > 0) {
                    tblRetiros.setRowSelectionInterval(row - 1, row - 1);
                    seleccionarRetiro();
                }
                else if (key == KeyEvent.VK_DOWN && row < total - 1) {
                    tblRetiros.setRowSelectionInterval(row + 1, row + 1);
                    seleccionarRetiro();
                }
                else if (key == KeyEvent.VK_HOME && total > 0) {
                    tblRetiros.setRowSelectionInterval(0, 0);
                    seleccionarRetiro();
                }
                else if (key == KeyEvent.VK_END && total > 0) {
                    tblRetiros.setRowSelectionInterval(total - 1, total - 1);
                    seleccionarRetiro();
                }
                else if (key == KeyEvent.VK_ENTER && row >= 0) {
                    seleccionarRetiro();
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
            if(cboMatricula.getSelectedIndex() == -1) {
                error("Seleccione una matrícula");
                return;
            }
            
            int numMatricula = obtenerNumeroMatricula();
            
            if(arreglo.buscarPorMatricula(numMatricula) != null) {
                error("Esta matrícula ya tiene un retiro registrado");
                return;
            }
            
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
            Date ahora = new Date();
            String fecha = formatoFecha.format(ahora);
            String hora = formatoHora.format(ahora);
            
            Retiro nuevo = new Retiro(numMatricula, fecha, hora);
            arreglo.adicionar(nuevo);
            
            Matricula matricula = arregloMatriculas.buscar(numMatricula);
            Alumno alumno = arregloAlumnos.buscar(matricula.getCodAlumno());
            alumno.setEstado(2);
            arregloAlumnos.actualizar();
            
            listarRetiros();
            cargarMatriculas();
            limpiar();
            mensaje("Retiro registrado correctamente\nNúmero: " + nuevo.getNumRetiro());
            
        } catch(Exception ex) {
            error("Error al registrar retiro: " + ex.getMessage());
        }
    }
    
    private void consultar() {
        try {
            int numero = leerNumero();
            Retiro retiro = arreglo.buscar(numero);
            if(retiro != null) {
                mostrarRetiro(retiro);
                mensaje("Retiro encontrado");
            } else {
                error("Retiro no encontrado");
            }
        } catch(Exception ex) {
            error("Ingrese un número válido");
        }
    }
    
    private void modificar() {
        try {
            int numero = leerNumero();
            Retiro retiro = arreglo.buscar(numero);
            if(retiro != null) {
                if(cboMatricula.getSelectedIndex() == -1) {
                    error("Seleccione una matrícula");
                    return;
                }
                int numMatricula = obtenerNumeroMatricula();
                Retiro retiroExistente = arreglo.buscarPorMatricula(numMatricula);
                if(retiroExistente != null && retiroExistente.getNumRetiro() != numero) {
                    error("La matrícula seleccionada ya tiene un retiro");
                    return;
                }
                retiro.setNumMatricula(numMatricula);
                arreglo.actualizar();
                listarRetiros();
                mensaje("Retiro modificado correctamente");
            } else {
                error("Retiro no encontrado");
            }
        } catch(Exception ex) {
            error("Error al modificar: " + ex.getMessage());
        }
    }
    
    private void cancelar() {
        try {
            int numero = leerNumero();
            Retiro retiro = arreglo.buscar(numero);
            if(retiro != null) {
                Matricula matricula = arregloMatriculas.buscar(retiro.getNumMatricula());
                Alumno alumno = arregloAlumnos.buscar(matricula.getCodAlumno());
                if(alumno.getEstado() != 2) {
                    error("Solo se pueden cancelar retiros de alumnos con estado RETIRADO");
                    return;
                }
                int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de cancelar este retiro?\n" +
                    "El alumno volverá al estado MATRICULADO\n" +
                    "Alumno: " + alumno.getNombreCompleto(),
                    "Confirmar Cancelación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                if(opcion == JOptionPane.YES_OPTION) {
                    arreglo.eliminar(retiro);
                    alumno.setEstado(1);
                    arregloAlumnos.actualizar();
                    listarRetiros();
                    cargarMatriculas();
                    limpiar();
                    mensaje("Retiro cancelado correctamente\nEl alumno vuelve a estar MATRICULADO");
                }
            } else {
                error("Retiro no encontrado");
            }
        } catch(Exception ex) {
            error("Error al cancelar: " + ex.getMessage());
        }
    }
    
    private void cargarMatriculas() {
        cboMatricula.removeAllItems();
        for(int i = 0; i < arregloMatriculas.tamaño(); i++) {
            Matricula m = arregloMatriculas.obtener(i);
            Alumno a = arregloAlumnos.buscar(m.getCodAlumno());
            Curso c = arregloCursos.buscar(m.getCodCurso());
            if(a != null && c != null) {
                cboMatricula.addItem(String.format("[%d] %s - %s", 
                    m.getNumMatricula(), a.getNombreCompleto(), c.getAsignatura()));
            }
        }
    }
    
    private void listarRetiros() {
        modelo.setRowCount(0);
        for(int i = 0; i < arreglo.tamaño(); i++) {
            Retiro r = arreglo.obtener(i);
            Matricula m = arregloMatriculas.buscar(r.getNumMatricula());
            if(m != null) {
                Alumno a = arregloAlumnos.buscar(m.getCodAlumno());
                Curso c = arregloCursos.buscar(m.getCodCurso());
                Object[] fila = {
                    r.getNumRetiro(),
                    r.getNumMatricula(),
                    a != null ? a.getNombreCompleto() : "N/A",
                    c != null ? c.getAsignatura() : "N/A",
                    r.getFecha(),
                    r.getHora()
                };
                modelo.addRow(fila);
            }
        }
    }
    
    private void seleccionarRetiro() {
        int fila = tblRetiros.getSelectedRow();
        if(fila >= 0) {
            Retiro r = arreglo.obtener(fila);
            mostrarRetiro(r);
        }
    }
    
    private void mostrarRetiro(Retiro r) {
        txtNumero.setText(String.valueOf(r.getNumRetiro()));
        txtFecha.setText(r.getFecha());
        txtHora.setText(r.getHora());
        
        for(int i = 0; i < cboMatricula.getItemCount(); i++) {
            String item = cboMatricula.getItemAt(i);
            if(item.startsWith("[" + r.getNumMatricula() + "]")) {
                cboMatricula.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void limpiar() {
        txtNumero.setText("");
        txtFecha.setText("");
        txtHora.setText("");
        cboMatricula.setSelectedIndex(-1);
    }
    
    private int leerNumero() {
        String texto = txtNumero.getText().trim();
        if(texto.isEmpty()) throw new RuntimeException("Ingrese número de retiro");
        return Integer.parseInt(texto);
    }
    
    private int obtenerNumeroMatricula() {
        String item = (String) cboMatricula.getSelectedItem();
        String numero = item.substring(item.indexOf("[") + 1, item.indexOf("]"));
        return Integer.parseInt(numero);
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