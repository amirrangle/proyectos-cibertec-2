package guis;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import clases.Reportes;

import java.net.URL;

public class MenuPrincipal extends JFrame implements ActionListener {
    
    private static final long serialVersionUID = 1L;
    
    // Componentes del menú
    private JMenuBar barraMenu;
    private JMenu mnuArchivo, mnuMantenimiento, mnuRegistro, mnuConsulta, mnuReporte, mnuAyuda;
    private JMenuItem mniSalir;
    private JMenuItem mniAlumno, mniCurso;
    private JMenuItem mniMatricula, mniRetiro;
    private JMenuItem mniConsultaGeneral;
    private JMenuItem mniReporteRegistrados, mniReporteMatriculados, mniReporteMatriculadosPorCurso;
    private JMenuItem mniAcercaDe;
    
    // Escritorio
    private JDesktopPane escritorio;
    
    // Logo CIBERTEC y GIF
    private ImageIcon logoImagen;
    private JLabel lblGifFondo;
    
    public MenuPrincipal() {
        setTitle("Sistema de Registro y Matrícula - CIBERTEC");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Cargar logo al inicio
        logoImagen = cargarLogo();
        
        crearMenu();
        crearEscritorio();
        crearPanelBienvenida();
    }
    
    private void crearMenu() {
        barraMenu = new JMenuBar();
        barraMenu.setBackground(new Color(41, 128, 185));
        
        // Menú Archivo con emoji (del segundo código)
        mnuArchivo = new JMenu("📁 Archivo");
        mnuArchivo.setForeground(Color.black);
        mnuArchivo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        mniSalir = new JMenuItem("🚪 Salir");
        mniSalir.addActionListener(this);
        mnuArchivo.add(mniSalir);
        
        // Menú Mantenimiento con emojis (del segundo código)
        mnuMantenimiento = new JMenu("🔧 Mantenimiento");
        mnuMantenimiento.setForeground(Color.black);
        mnuMantenimiento.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        mniAlumno = new JMenuItem("👨‍🎓 Alumno");
        mniAlumno.addActionListener(this);
        mnuMantenimiento.add(mniAlumno);
        
        mniCurso = new JMenuItem("📚 Curso");
        mniCurso.addActionListener(this);
        mnuMantenimiento.add(mniCurso);
        
        // Menú Registro con emojis (del segundo código)
        mnuRegistro = new JMenu("📝 Registro");
        mnuRegistro.setForeground(Color.black);
        mnuRegistro.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        mniMatricula = new JMenuItem("✅ Matrícula");
        mniMatricula.addActionListener(this);
        mnuRegistro.add(mniMatricula);
        
        mniRetiro = new JMenuItem("❌ Retiro");
        mniRetiro.addActionListener(this);
        mnuRegistro.add(mniRetiro);
        
        // Menú Consulta con emojis (del segundo código)
        mnuConsulta = new JMenu("🔍 Consulta");
        mnuConsulta.setForeground(Color.black);
        mnuConsulta.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        mniConsultaGeneral = new JMenuItem("📋 Consulta General");
        mniConsultaGeneral.addActionListener(this);
        mnuConsulta.add(mniConsultaGeneral);
        
        // Menú Reporte con emojis (del segundo código)
        mnuReporte = new JMenu("📊 Reporte");
        mnuReporte.setForeground(Color.black);
        mnuReporte.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        mniReporteRegistrados = new JMenuItem("📌 Alumnos con Matrícula Pendiente");
        mniReporteRegistrados.addActionListener(this);
        mnuReporte.add(mniReporteRegistrados);
        
        mniReporteMatriculados = new JMenuItem("✔️ Alumnos con Matrícula Vigente");
        mniReporteMatriculados.addActionListener(this);
        mnuReporte.add(mniReporteMatriculados);
        
        mniReporteMatriculadosPorCurso = new JMenuItem("📖 Alumnos Matriculados por Curso");
        mniReporteMatriculadosPorCurso.addActionListener(this);
        mnuReporte.add(mniReporteMatriculadosPorCurso);
        
        // Menú Ayuda con emojis (del segundo código)
        mnuAyuda = new JMenu("❓ Ayuda");
        mnuAyuda.setForeground(Color.black);
        mnuAyuda.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        mniAcercaDe = new JMenuItem("ℹ️ Acerca de...");
        mniAcercaDe.addActionListener(this);
        mnuAyuda.add(mniAcercaDe);
        
        // Agregar menús a la barra EN ORDEN
        barraMenu.add(mnuArchivo);
        barraMenu.add(mnuMantenimiento);
        barraMenu.add(mnuRegistro);
        barraMenu.add(mnuConsulta);
        barraMenu.add(mnuReporte);
        barraMenu.add(mnuAyuda);
        
        setJMenuBar(barraMenu);
    }
    
    private void crearEscritorio() {
        escritorio = new JDesktopPane();
        escritorio.setBackground(new Color(236, 240, 241));
        getContentPane().add(escritorio);
    }
    
    private void crearPanelBienvenida() {
        // Panel contenedor principal con LayeredPane para superposición (del primer código)
        JLayeredPane layeredPane = new JLayeredPane();
        
        // Panel con GIF de fondo (del primer código)
        JPanel panelGif = new JPanel(new BorderLayout());
        panelGif.setOpaque(true);
        panelGif.setBackground(Color.BLACK);
        
        // Intentar cargar GIF desde URL (del primer código)
        try {
            URL urlGif = new URL("https://i.giphy.com/media/v1.Y2lkPTc5MGI3NjExYzB3eDRtZ3gyYnZpZ2E5cWd6ZGt3ZWJ6NHJzaHN4NmUzZGF0dDJlbCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/l0HlHFRbmaZtBRhXG/giphy.gif");
            
            ImageIcon gifIcon = new ImageIcon(urlGif);
            lblGifFondo = new JLabel(gifIcon);
            lblGifFondo.setHorizontalAlignment(JLabel.CENTER);
            lblGifFondo.setVerticalAlignment(JLabel.CENTER);
            
            panelGif.add(lblGifFondo, BorderLayout.CENTER);
            
        } catch (Exception e) {
            // Si falla la carga del GIF, usar el logo local o gradiente (combinación de ambos)
            System.out.println("No se pudo cargar el GIF, usando fondo alternativo");
            JPanel panelAlternativo = new JPanel() {
                private static final long serialVersionUID = 1L;
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Dibujar logo si está disponible (del segundo código)
                    if (logoImagen != null && logoImagen.getImage() != null) {
                        g2d.drawImage(logoImagen.getImage(), 0, 0, getWidth(), getHeight(), this);
                        
                        // Overlay semi-transparente para mejor legibilidad (del segundo código)
                        g2d.setColor(new Color(0, 82, 127, 180));
                        g2d.fillRect(0, 0, getWidth(), getHeight());
                    } else {
                        // Gradiente como fallback (del primer código)
                        GradientPaint gp = new GradientPaint(0, 0, new Color(41, 128, 185), 
                                                             0, getHeight(), new Color(52, 152, 219));
                        g2d.setPaint(gp);
                        g2d.fillRect(0, 0, getWidth(), getHeight());
                    }
                }
            };
            panelGif.add(panelAlternativo, BorderLayout.CENTER);
        }
        
        // Panel de texto superpuesto con transparencia (combinación de ambos)
        JPanel panelTexto = new JPanel() {
            private static final long serialVersionUID = 1L;
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                // Fondo semi-transparente oscuro (del primer código)
                g2d.setColor(new Color(0, 0, 0, 150));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Rectángulo central para información (del segundo código)
                int boxWidth = 800;
                int boxHeight = 300;
                int boxX = (getWidth() - boxWidth) / 2;
                int boxY = (getHeight() - boxHeight) / 2 + 50;
                
                // Sombra del rectángulo (del segundo código)
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.fillRoundRect(boxX + 5, boxY + 5, boxWidth, boxHeight, 30, 30);
                
                // Rectángulo principal con más opacidad (del segundo código)
                g2d.setColor(new Color(255, 255, 255, 250));
                g2d.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 30, 30);
                
                // Borde del rectángulo (del segundo código)
                g2d.setColor(new Color(0, 82, 127));
                g2d.setStroke(new BasicStroke(4));
                g2d.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 30, 30);
                
                // Título línea 1 (combinación de ambos)
                g2d.setColor(new Color(0, 82, 127));
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 44));
                String titulo1 = "Sistema de Registro";
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(titulo1)) / 2;
                g2d.drawString(titulo1, x, boxY + 90);
                
                // Título línea 2 (combinación de ambos)
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 44));
                String titulo2 = "y Matrícula";
                fm = g2d.getFontMetrics();
                x = (getWidth() - fm.stringWidth(titulo2)) / 2;
                g2d.drawString(titulo2, x, boxY + 140);
                
                // Línea decorativa (combinación de ambos)
                g2d.setStroke(new BasicStroke(3));
                g2d.setColor(new Color(41, 128, 185));
                int lineWidth = 400;
                int lineX = (getWidth() - lineWidth) / 2;
                g2d.drawLine(lineX, boxY + 165, lineX + lineWidth, boxY + 165);
                
                // Instrucciones (combinación de ambos)
                g2d.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                g2d.setColor(new Color(60, 60, 60));
                String instruccion = "Seleccione una opción del menú para comenzar";
                fm = g2d.getFontMetrics();
                x = (getWidth() - fm.stringWidth(instruccion)) / 2;
                g2d.drawString(instruccion, x, boxY + 220);
                
                // Footer con información adicional (combinación de ambos)
                int footerY = getHeight() - 50;
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.fillRect(0, footerY - 10, getWidth(), 60);
                
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
                String footer = "© 2025 - Sistema Académico Integrado | Versión 1.0";
                fm = g2d.getFontMetrics();
                x = (getWidth() - fm.stringWidth(footer)) / 2;
                g2d.setColor(Color.WHITE);
                g2d.drawString(footer, x, getHeight() - 30);
            }
        };
        
        panelTexto.setOpaque(false);
        
        // Configurar tamaños iniciales (del primer código)
        int width = escritorio.getWidth() > 0 ? escritorio.getWidth() : 1200;
        int height = escritorio.getHeight() > 0 ? escritorio.getHeight() : 700;
        
        panelGif.setBounds(0, 0, width, height);
        panelTexto.setBounds(0, 0, width, height);
        
        // Agregar al LayeredPane con diferentes capas (del primer código)
        layeredPane.add(panelGif, Integer.valueOf(0));    // Capa de fondo
        layeredPane.add(panelTexto, Integer.valueOf(1));  // Capa de texto encima
        
        layeredPane.setBounds(0, 0, width, height);
        escritorio.add(layeredPane);
        
        // Listener para redimensionar (combinación de ambos)
        escritorio.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int w = escritorio.getWidth();
                int h = escritorio.getHeight();
                layeredPane.setBounds(0, 0, w, h);
                panelGif.setBounds(0, 0, w, h);
                panelTexto.setBounds(0, 0, w, h);
                panelTexto.repaint();
                
                // Redimensionar GIF si existe (del primer código)
                if(lblGifFondo != null && lblGifFondo.getIcon() != null) {
                    lblGifFondo.setIcon(new ImageIcon(
                        ((ImageIcon)lblGifFondo.getIcon()).getImage().getScaledInstance(
                            w, h, Image.SCALE_DEFAULT)));
                }
            }
        });
    }
    
    private ImageIcon cargarLogo() {
        try {
            String[] rutas = {
                "recursos/logo_cibertec.png",
                "recursos/cibertec.png",
                "recursos/logo_cibertec.jpg",
                "logo_cibertec.png",
                "cibertec.png"
            };
            
            for (String ruta : rutas) {
                java.io.File archivo = new java.io.File(ruta);
                if (archivo.exists()) {
                    System.out.println("✓ Logo cargado desde: " + ruta);
                    return new ImageIcon(ruta);
                }
            }
            
            System.out.println("⚠ Logo no encontrado. Descarga el logo de CIBERTEC y guárdalo como:");
            System.out.println("  recursos/logo_cibertec.png");
            
        } catch (Exception e) {
            System.err.println("Error al cargar logo: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == mniSalir) {
            salir();
        }
        else if(e.getSource() == mniAlumno) {
            abrirVentana(new MantenimientoAlumno());
        }
        else if(e.getSource() == mniCurso) {
            abrirVentana(new MantenimientoCurso());
        }
        else if(e.getSource() == mniMatricula) {
            abrirVentana(new RegistroMatricula());
        }
        else if(e.getSource() == mniRetiro) {
            abrirVentana(new RegistroRetiro());
        }
        else if(e.getSource() == mniConsultaGeneral) {
            abrirVentana(new ConsultaGeneral());
        }
        else if(e.getSource() == mniReporteRegistrados) {
            abrirVentana(new Reportes(0));
        }
        else if(e.getSource() == mniReporteMatriculados) {
            abrirVentana(new Reportes(1));
        }
        else if(e.getSource() == mniReporteMatriculadosPorCurso) {
            abrirVentana(new Reportes(2));
        }
        else if(e.getSource() == mniAcercaDe) {
            mostrarAcercaDe();
        }
    }
    
    private void salir() {
        int opcion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea salir del sistema?",
            "Confirmar Salida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if(opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    private void mostrarAcercaDe() {
        // Crear panel personalizado (combinación de ambos)
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Título del curso
        JLabel lblTitulo = new JLabel("ALGORITMOS Y ESTRUCTURA DE DATOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(0, 120, 215));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitulo);
        
        panel.add(Box.createVerticalStrut(25));
        
        // Título Integrantes
        JLabel lblIntegrantes = new JLabel("INTEGRANTES:");
        lblIntegrantes.setFont(new Font("Arial", Font.BOLD, 13));
        lblIntegrantes.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblIntegrantes);
        
        panel.add(Box.createVerticalStrut(15));
        
        // Integrante 1
        JLabel lblInt1 = new JLabel("Percy Angel Paredes Ticona - I202506406");
        lblInt1.setFont(new Font("Arial", Font.PLAIN, 12));
        lblInt1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblInt1);
        
        panel.add(Box.createVerticalStrut(5));
        
        // Integrante 2
        JLabel lblInt2 = new JLabel("Amir Andre Moran Rangle - I201822948");
        lblInt2.setFont(new Font("Arial", Font.PLAIN, 12));
        lblInt2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblInt2);
        
        panel.add(Box.createVerticalStrut(5));
        
        // Integrante 3
        JLabel lblInt3 = new JLabel("Jose Luis Peralta Coronel - I202505936");
        lblInt3.setFont(new Font("Arial", Font.PLAIN, 12));
        lblInt3.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblInt3);
        
        panel.add(Box.createVerticalStrut(25));
        
        // Institución
        JLabel lblInstitucion = new JLabel("Instituto de Educación Superior Privado CIBERTEC");
        lblInstitucion.setFont(new Font("Arial", Font.ITALIC, 11));
        lblInstitucion.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblInstitucion);
        
        panel.add(Box.createVerticalStrut(5));
        
        // Año
        JLabel lblAnio = new JLabel("© 2025 - Segundo Ciclo");
        lblAnio.setFont(new Font("Arial", Font.ITALIC, 11));
        lblAnio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblAnio);
        
        // Mostrar diálogo con botón "Aceptar"
        JOptionPane.showMessageDialog(this,
            panel,
            "Acerca de - Sistema de Matrícula",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirVentana(JInternalFrame ventana) {
        // Cerrar ventanas previas
        JInternalFrame[] ventanas = escritorio.getAllFrames();
        for(JInternalFrame v : ventanas) {
            v.dispose();
        }
        
        escritorio.add(ventana);
        ventana.setVisible(true);
        
        try {
            ventana.setMaximum(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // ========== MÉTODO PRINCIPAL ==========
    public static void main(String[] args) {
        System.out.println("=== VERIFICACIÓN DEL LOGO DE CIBERTEC ===");
        java.io.File archivo = new java.io.File("recursos/logo_cibertec.png");
        System.out.println("¿Existe el archivo? " + archivo.exists());
        System.out.println("Ruta absoluta: " + archivo.getAbsolutePath());
        System.out.println("==========================================\n");
        
        try {
            // Establecer el look and feel del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Ejecutar en el Event Dispatch Thread de Swing
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Crear y mostrar la ventana principal
                    MenuPrincipal ventana = new MenuPrincipal();
                    ventana.setVisible(true);
                    
                    // Centrar la ventana
                    ventana.setLocationRelativeTo(null);
                    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                        "Error al iniciar la aplicación:\n" + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });
    }
}