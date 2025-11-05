package guis;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import clases.Curso;
import arreglos.ArregloCursos;
import arreglos.ArregloMatriculas;

public class MantenimientoCurso extends JInternalFrame implements ActionListener {
    
    private static final long serialVersionUID = 1L;
    
    // Arreglos
    private ArregloCursos arreglo = new ArregloCursos();
    private ArregloMatriculas arregloMatriculas = new ArregloMatriculas();
    
    // Componentes
    private JPanel panelFormulario, panelBotones, panelTabla;
    private JLabel lblCodigo, lblAsignatura, lblCiclo, lblCreditos, lblHoras;
    private JTextField txtCodigo, txtAsignatura, txtCreditos, txtHoras;
    private JComboBox<String> cboCiclo;
    private JButton btnAdicionar, btnConsultar, btnModificar, btnEliminar, btnLimpiar;
    private JTable tblCursos;
    private DefaultTableModel modelo;
    private JScrollPane scrollPane;
    
    public MantenimientoCurso() {
        setTitle("Mantenimiento de Cursos");
        setSize(1000, 650);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        
        inicializarComponentes();
        listarCursos();
        configurarNavegacionTeclado();

        // ← AQUÍ VA EL LISTENER (DENTRO DEL CONSTRUCTOR)
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(InternalFrameEvent e) {
                tblCursos.requestFocusInWindow();
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
            "Datos del Curso",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.PLAIN, 14),
            new Color(52, 152, 219)
        ));
        
        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setPreferredSize(new Dimension(950, 100));
        
        lblCodigo = new JLabel("Código (4 dígitos):");
        lblCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        addComp(panelCampos, lblCodigo, 0, 0, 1, 1);
        
        txtCodigo = new JTextField();
        txtCodigo.setPreferredSize(new Dimension(120, 25));
        addComp(panelCampos, txtCodigo, 1, 0, 1, 1);
        
        lblAsignatura = new JLabel("Asignatura:");
        lblAsignatura.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        addComp(panelCampos, lblAsignatura, 2, 0, 1, 1);
        
        txtAsignatura = new JTextField();
        txtAsignatura.setPreferredSize(new Dimension(350, 25));
        addComp(panelCampos, txtAsignatura, 3, 0, 2, 1);
        
        lblCiclo = new JLabel("Ciclo:");
        lblCiclo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        addComp(panelCampos, lblCiclo, 0, 1, 1, 1);
        
        cboCiclo = new JComboBox<String>();
        cboCiclo.addItem("Primero");
        cboCiclo.addItem("Segundo");
        cboCiclo.addItem("Tercero");
        cboCiclo.addItem("Cuarto");
        cboCiclo.addItem("Quinto");
        cboCiclo.addItem("Sexto");
        cboCiclo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cboCiclo.setPreferredSize(new Dimension(120, 25));
        addComp(panelCampos, cboCiclo, 1, 1, 1, 1);
        
        lblCreditos = new JLabel("Créditos:");
        lblCreditos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        addComp(panelCampos, lblCreditos, 2, 1, 1, 1);
        
        txtCreditos = new JTextField();
        txtCreditos.setPreferredSize(new Dimension(80, 25));
        addComp(panelCampos, txtCreditos, 3, 1, 1, 1);
        
        lblHoras = new JLabel("Horas:");
        lblHoras.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        addComp(panelCampos, lblHoras, 4, 1, 1, 1);
        
        txtHoras = new JTextField();
        txtHoras.setPreferredSize(new Dimension(80, 25));
        addComp(panelCampos, txtHoras, 5, 1, 1, 1);
        
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
        btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60));
        btnLimpiar = crearBoton("Limpiar", new Color(149, 165, 166));
        
        panelBotones.add(btnAdicionar);
        panelBotones.add(btnConsultar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
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
            "Lista de Cursos (Ordenados por Código)",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.PLAIN, 14),
            new Color(52, 152, 219)
        ));
        
        modelo = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        modelo.addColumn("Código");
        modelo.addColumn("Asignatura");
        modelo.addColumn("Ciclo");
        modelo.addColumn("Créditos");
        modelo.addColumn("Horas");
        
        tblCursos = new JTable(modelo);
        tblCursos.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tblCursos.setRowHeight(25);
        tblCursos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblCursos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblCursos.getTableHeader().setBackground(new Color(52, 152, 219));
        tblCursos.getTableHeader().setForeground(Color.BLACK);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblCursos.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblCursos.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tblCursos.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tblCursos.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        
        tblCursos.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblCursos.getColumnModel().getColumn(1).setPreferredWidth(400);
        tblCursos.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblCursos.getColumnModel().getColumn(3).setPreferredWidth(80);
        tblCursos.getColumnModel().getColumn(4).setPreferredWidth(80);
        
        // Mouse: 1 clic → seleccionar
        tblCursos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    seleccionarCurso();
                }
            }
        });
        
        scrollPane = new JScrollPane(tblCursos);
        panelTabla.add(scrollPane, BorderLayout.CENTER);
    }
    
    // === NAVEGACIÓN CON TECLADO (COMPLETA) ===
    private void configurarNavegacionTeclado() {
        tblCursos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                int row = tblCursos.getSelectedRow();
                int total = tblCursos.getRowCount();

                if (key == KeyEvent.VK_UP && row > 0) {
                    tblCursos.setRowSelectionInterval(row - 1, row - 1);
                    seleccionarCurso();
                }
                else if (key == KeyEvent.VK_DOWN && row < total - 1) {
                    tblCursos.setRowSelectionInterval(row + 1, row + 1);
                    seleccionarCurso();
                }
                else if (key == KeyEvent.VK_HOME && total > 0) {
                    tblCursos.setRowSelectionInterval(0, 0);
                    seleccionarCurso();
                }
                else if (key == KeyEvent.VK_END && total > 0) {
                    tblCursos.setRowSelectionInterval(total - 1, total - 1);
                    seleccionarCurso();
                }
                else if (key == KeyEvent.VK_ENTER && row >= 0) {
                    seleccionarCurso();
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
        else if(e.getSource() == btnEliminar) {
            eliminar();
        }
        else if(e.getSource() == btnLimpiar) {
            limpiar();
        }
    }
    
    private void adicionar() {
        try {
            int codigo = leerCodigo();
            if(codigo < 1000 || codigo > 9999) {
                error("El código debe tener 4 dígitos (1000-9999)");
                txtCodigo.requestFocus();
                return;
            }
            if(arreglo.existeCodigo(codigo)) {
                error("El código ya existe");
                txtCodigo.requestFocus();
                return;
            }
            
            String asignatura = leerAsignatura();
            int ciclo = leerCiclo();
            int creditos = leerCreditos();
            int horas = leerHoras();
            
            Curso nuevo = new Curso(codigo, asignatura, ciclo, creditos, horas);
            arreglo.adicionar(nuevo);
            listarCursos();
            limpiar();
            mensaje("Curso adicionado correctamente\nLa lista ha sido ordenada por código");
            
        } catch(Exception ex) {
            error("Error al adicionar: " + ex.getMessage());
        }
    }
    
    private void consultar() {
        try {
            int codigo = leerCodigo();
            Curso curso = arreglo.buscar(codigo);
            if(curso != null) {
                mostrarCurso(curso);
                mensaje("Curso encontrado");
            } else {
                error("Curso no encontrado");
            }
        } catch(Exception ex) {
            error("Ingrese un código válido");
        }
    }
    
    private void modificar() {
        try {
            int codigo = leerCodigo();
            Curso curso = arreglo.buscar(codigo);
            if(curso != null) {
                curso.setAsignatura(leerAsignatura());
                curso.setCiclo(leerCiclo());
                curso.setCreditos(leerCreditos());
                curso.setHoras(leerHoras());
                arreglo.actualizar();
                listarCursos();
                mensaje("Curso modificado correctamente");
            } else {
                error("Curso no encontrado");
            }
        } catch(Exception ex) {
            error("Error al modificar: " + ex.getMessage());
        }
    }
    
    private void eliminar() {
        try {
            int codigo = leerCodigo();
            Curso curso = arreglo.buscar(codigo);
            if(curso != null) {
                int cantidadMatriculados = arregloMatriculas.contarAlumnosPorCurso(codigo);
                if(cantidadMatriculados > 0) {
                    error("No se puede eliminar el curso\n" + 
                          "Hay " + cantidadMatriculados + " alumno(s) matriculado(s)");
                    return;
                }
                
                int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar el curso?\n" + curso.getAsignatura(),
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                
                if(opcion == JOptionPane.YES_OPTION) {
                    arreglo.eliminar(curso);
                    listarCursos();
                    limpiar();
                    mensaje("Curso eliminado correctamente");
                }
            } else {
                error("Curso no encontrado");
            }
        } catch(Exception ex) {
            error("Error al eliminar: " + ex.getMessage());
        }
    }
    
    private void listarCursos() {
        modelo.setRowCount(0);
        for(int i = 0; i < arreglo.tamaño(); i++) {
            Curso c = arreglo.obtener(i);
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
    
    private void seleccionarCurso() {
        int fila = tblCursos.getSelectedRow();
        if(fila >= 0) {
            Curso c = arreglo.obtener(fila);
            mostrarCurso(c);
        }
    }
    
    private void mostrarCurso(Curso c) {
        txtCodigo.setText(String.valueOf(c.getCodCurso()));
        txtAsignatura.setText(c.getAsignatura());
        cboCiclo.setSelectedIndex(c.getCiclo());
        txtCreditos.setText(String.valueOf(c.getCreditos()));
        txtHoras.setText(String.valueOf(c.getHoras()));
    }
    
    private void limpiar() {
        txtCodigo.setText("");
        txtAsignatura.setText("");
        cboCiclo.setSelectedIndex(0);
        txtCreditos.setText("");
        txtHoras.setText("");
        txtCodigo.requestFocus();
    }
    
    // Métodos de lectura
    private int leerCodigo() {
        String texto = txtCodigo.getText().trim();
        if(texto.isEmpty()) throw new RuntimeException("Ingrese código");
        return Integer.parseInt(texto);
    }
    
    private String leerAsignatura() {
        String asignatura = txtAsignatura.getText().trim();
        if(asignatura.isEmpty()) throw new RuntimeException("Ingrese asignatura");
        return asignatura;
    }
    
    private int leerCiclo() {
        return cboCiclo.getSelectedIndex();
    }
    
    private int leerCreditos() {
        String texto = txtCreditos.getText().trim();
        if(texto.isEmpty()) throw new RuntimeException("Ingrese créditos");
        int creditos = Integer.parseInt(texto);
        if(creditos < 1 || creditos > 10) throw new RuntimeException("Créditos inválidos (1-10)");
        return creditos;
    }
    
    private int leerHoras() {
        String texto = txtHoras.getText().trim();
        if(texto.isEmpty()) throw new RuntimeException("Ingrese horas");
        int horas = Integer.parseInt(texto);
        if(horas < 1 || horas > 20) throw new RuntimeException("Horas inválidas (1-20)");
        return horas;
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