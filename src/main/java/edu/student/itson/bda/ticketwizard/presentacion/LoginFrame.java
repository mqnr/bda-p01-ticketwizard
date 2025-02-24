package edu.student.itson.bda.ticketwizard.presentacion;

import edu.student.itson.bda.ticketwizard.control.ControlUsuarios;
import edu.student.itson.bda.ticketwizard.persistencia.UsuariosDAO;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LoginFrame extends JFrame {

    private JTextField campoEmail;
    private JPasswordField campoContrasenia;
    private JButton botonLogin;
    private JButton botonRegistrar;

    public LoginFrame() {
        inicializarComponentes();
        prepararLayout();
        prepararListeners();
        prepararFrame();
    }

    private void inicializarComponentes() {
        campoEmail = new JTextField(20);
        campoContrasenia = new JPasswordField(20);
        botonLogin = new JButton("Iniciar sesión");
        botonRegistrar = new JButton("Registrarse");
    }

    private void prepararLayout() {
        // panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

        // título
        JLabel labelTitulo = new JLabel("Bienvenido");
        labelTitulo.setFont(new Font("Dialog", Font.BOLD, 24));
        labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // páneles de entrada
        JPanel panelEmail = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEmail.add(new JLabel("Correo electrónico:"));
        panelEmail.add(campoEmail);

        JPanel panelContrasenia = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelContrasenia.add(new JLabel("Contraseña:"));
        panelContrasenia.add(campoContrasenia);

        // panel de botón
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(botonLogin);
        buttonPanel.add(botonRegistrar);

        panelPrincipal.add(labelTitulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));
        panelPrincipal.add(panelEmail);
        panelPrincipal.add(panelContrasenia);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(buttonPanel);

        add(panelPrincipal);
    }

    private void prepararListeners() {
        botonLogin.addActionListener(e -> manejarLogin());
        botonRegistrar.addActionListener(e -> manejarRegistros());

        // se envía al presionar enter
        campoContrasenia.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    manejarLogin();
                }
            }
        });
    }

    private void prepararFrame() {
        setTitle("Login - Sistema de Boletos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null); // Center on screen
    }

    private void manejarLogin() {
        String email = campoEmail.getText().trim();
        String password = new String(campoContrasenia.getPassword());

        // validación básica
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese su correo y contraseña",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // TODO: autenticación 
        JOptionPane.showMessageDialog(this,
                "Login exitoso",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);

        // abrir pantalla principal y cerrar esta
        SwingUtilities.invokeLater(() -> {
            // TODO: hacer esto una realidad
            UsuariosDAO usuariosDAO = new UsuariosDAO();
            new PantallaPrincipalFrame(new ControlUsuarios(usuariosDAO), usuariosDAO.consultarUsuarioAleatorio()).setVisible(true);
            this.dispose();
        });
    }

    private void manejarRegistros() {
        SwingUtilities.invokeLater(() -> {
            new RegistroFrame().setVisible(true);
            this.dispose();
        });
    }
}
