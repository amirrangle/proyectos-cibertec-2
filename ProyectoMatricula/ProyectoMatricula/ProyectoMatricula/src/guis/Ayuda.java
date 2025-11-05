package guis;

import java.awt.*;
import javax.swing.*;

public class Ayuda extends JInternalFrame {
    
    private static final long serialVersionUID = 1L;
    
    public Ayuda() {
        setTitle("Acerca de - Sistema de Matrícula");
        setSize(600, 400);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        
        // Título del curso
        JLabel lblTituloCurso = new JLabel("CURSO:");
        lblTituloCurso.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTituloCurso.setBounds(50, 30, 100, 25);
        panel.add(lblTituloCurso);
        
        JLabel lblCurso = new JLabel("ALGORITMOS Y ESTRUCTURA DE DATOS");
        lblCurso.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblCurso.setBounds(150, 30, 400, 25);
        panel.add(lblCurso);
        
        // Línea separadora
        JSeparator separador1 = new JSeparator();
        separador1.setBounds(50, 70, 500, 2);
        panel.add(separador1);
        
        // Título Integrantes
        JLabel lblTituloIntegrantes = new JLabel("INTEGRANTES:");
        lblTituloIntegrantes.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTituloIntegrantes.setBounds(50, 90, 150, 25);
        panel.add(lblTituloIntegrantes);
        
        // Integrante 1
        JLabel lblIntegrante1 = new JLabel("• Percy Angel Paredes Ticona - I202506406");
        lblIntegrante1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblIntegrante1.setBounds(70, 125, 450, 25);
        panel.add(lblIntegrante1);
        
        // Integrante 2
        JLabel lblIntegrante2 = new JLabel("• Amir Andre Moran Rangle - I201822948");
        lblIntegrante2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblIntegrante2.setBounds(70, 155, 450, 25);
        panel.add(lblIntegrante2);
        
        // Integrante 3
        JLabel lblIntegrante3 = new JLabel("• Jose Luis Peralta Coronel - I202505936");
        lblIntegrante3.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblIntegrante3.setBounds(70, 185, 450, 25);
        panel.add(lblIntegrante3);
        
        // Línea separadora
        JSeparator separador2 = new JSeparator();
        separador2.setBounds(50, 230, 500, 2);
        panel.add(separador2);
        
        // Información adicional
        JLabel lblInstitucion = new JLabel("Instituto de Educación Superior Privado CIBERTEC");
        lblInstitucion.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblInstitucion.setBounds(50, 250, 400, 25);
        panel.add(lblInstitucion);
        
        JLabel lblAnio = new JLabel("© 2025 - Segundo Ciclo");
        lblAnio.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblAnio.setBounds(50, 275, 200, 25);
        panel.add(lblAnio);
        
        // Botón Cerrar
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(240, 320, 120, 30);
        btnCerrar.addActionListener(e -> dispose());
        panel.add(btnCerrar);
        
        getContentPane().add(panel);
    }
}