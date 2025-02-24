package edu.student.itson.bda.ticketwizard.presentacion;

import edu.student.itson.bda.ticketwizard.entidades.Usuario;

import javax.swing.*;
import java.awt.*;

public class PantallaPrincipalFrame extends JFrame {

    private Usuario usuario;
    private CardLayout cardLayout;
    private JPanel panelContenido;

    private static final String PANEL_PERFIL = "Perfil";
    private static final String PANEL_MIS_BOLETOS = "MisBoletos";
    private static final String PANEL_BUSCAR = "BuscarBoletos";
    private static final String PANEL_HISTORIAL = "Historial";
    private static final String PANEL_FONDOS = "AgregarFondos";

    public PantallaPrincipalFrame(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Ticketwizard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);

        panelContenido.add(new PerfilPanel(usuario), PANEL_PERFIL);
        panelContenido.add(new BoletosPropiosPanel(usuario), PANEL_MIS_BOLETOS);
        panelContenido.add(new BuscarBoletosPanel(usuario), PANEL_BUSCAR);
        panelContenido.add(new HistorialPanel(usuario), PANEL_HISTORIAL);
        panelContenido.add(new AgregarFondosPanel(usuario), PANEL_FONDOS);

        configurarMenu();

        add(panelContenido, BorderLayout.CENTER);
    }

    private void configurarMenu() {
        JMenuBar barraMenu = new JMenuBar();

        JMenu menuPerfil = new JMenu("Perfil");
        JMenuItem itemPerfil = new JMenuItem("Ver perfil");
        itemPerfil.addActionListener(e -> cardLayout.show(panelContenido, PANEL_PERFIL));
        menuPerfil.add(itemPerfil);

        JMenu menuBoletos = new JMenu("Boletos");
        JMenuItem itemMisBoletos = new JMenuItem("Mis boletos");
        itemMisBoletos.addActionListener(e -> cardLayout.show(panelContenido, PANEL_MIS_BOLETOS));
        JMenuItem itemBuscar = new JMenuItem("Buscar boletos");
        itemBuscar.addActionListener(e -> cardLayout.show(panelContenido, PANEL_BUSCAR));
        menuBoletos.add(itemMisBoletos);
        menuBoletos.add(itemBuscar);

        JMenu menuHistorial = new JMenu("Historial");
        JMenuItem itemHistorial = new JMenuItem("Ver historial");
        itemHistorial.addActionListener(e -> cardLayout.show(panelContenido, PANEL_HISTORIAL));
        menuHistorial.add(itemHistorial);

        JMenu menuFondos = new JMenu("Fondos");
        JMenuItem itemFondos = new JMenuItem("Agregar fondos");
        itemFondos.addActionListener(e -> cardLayout.show(panelContenido, PANEL_FONDOS));
        menuFondos.add(itemFondos);

        JMenu menuSesion = new JMenu("Sesión");
        JMenuItem itemCerrarSesion = new JMenuItem("Cerrar sesión");
        itemCerrarSesion.addActionListener(e -> cerrarSesion());
        menuSesion.add(itemCerrarSesion);

        barraMenu.add(menuPerfil);
        barraMenu.add(menuBoletos);
        barraMenu.add(menuHistorial);
        barraMenu.add(menuFondos);
        barraMenu.add(menuSesion);

        setJMenuBar(barraMenu);
    }

    private void cerrarSesion() {
        // TODO: despejar sección cuando esto esté implementado
        dispose();
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
