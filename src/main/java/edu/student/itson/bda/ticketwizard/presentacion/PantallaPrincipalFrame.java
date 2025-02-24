package edu.student.itson.bda.ticketwizard.presentacion;

import edu.student.itson.bda.ticketwizard.control.ControlUsuarios;
import edu.student.itson.bda.ticketwizard.entidades.Usuario;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PantallaPrincipalFrame extends JFrame {

    private final ControlUsuarios controlUsuarios;
    private final Usuario usuario;
    private CardLayout cardLayout;
    private JPanel panelContenido;

    private static final String PANEL_PERFIL = "Perfil";
    private static final String PANEL_MIS_BOLETOS = "MisBoletos";
    private static final String PANEL_BUSCAR = "BuscarBoletos";
    private static final String PANEL_HISTORIAL = "Historial";
    private static final String PANEL_FONDOS = "AgregarFondos";

    public PantallaPrincipalFrame(ControlUsuarios controlUsuarios, Usuario usuario) {
        this.controlUsuarios = controlUsuarios;
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

        PerfilPanel perfilPanel = new PerfilPanel(controlUsuarios, usuario);
        perfilPanel.setName(PANEL_PERFIL);
        panelContenido.add(perfilPanel, PANEL_PERFIL);
        panelContenido.add(new BoletosPropiosPanel(usuario), PANEL_MIS_BOLETOS);
        panelContenido.add(new BuscarBoletosPanel(usuario), PANEL_BUSCAR);
        panelContenido.add(new HistorialPanel(usuario), PANEL_HISTORIAL);
        AgregarFondosPanel fondosPanel = new AgregarFondosPanel(controlUsuarios, usuario);
        fondosPanel.setName(PANEL_FONDOS);
        panelContenido.add(fondosPanel, PANEL_FONDOS);

        // barra de menú
        configurarMenu();

        add(panelContenido, BorderLayout.CENTER);
    }

    private void mostrarPanel(String nombrePanel) {
        cardLayout.show(panelContenido, nombrePanel);

        Component panel = buscarComponentePorNombre(panelContenido, nombrePanel);
        if (panel instanceof PanelActualizable) {
            ((PanelActualizable) panel).refrescarDatos();
        }
    }

    private Component buscarComponentePorNombre(Container container, String nombre) {
        for (Component comp : container.getComponents()) {
            if (nombre.equals(comp.getName())) {
                return comp;
            }
        }
        return null;
    }

    private void configurarMenu() {
        JMenuBar barraMenu = new JMenuBar();

        JMenu menuPerfil = new JMenu("Perfil");
        JMenuItem itemPerfil = new JMenuItem("Ver perfil");
        itemPerfil.addActionListener(e -> mostrarPanel(PANEL_PERFIL));
        menuPerfil.add(itemPerfil);

        JMenu menuBoletos = new JMenu("Boletos");
        JMenuItem itemMisBoletos = new JMenuItem("Mis boletos");
        itemMisBoletos.addActionListener(e -> mostrarPanel(PANEL_MIS_BOLETOS));
        JMenuItem itemBuscar = new JMenuItem("Buscar boletos");
        itemBuscar.addActionListener(e -> mostrarPanel(PANEL_BUSCAR));
        menuBoletos.add(itemMisBoletos);
        menuBoletos.add(itemBuscar);

        JMenu menuHistorial = new JMenu("Historial");
        JMenuItem itemHistorial = new JMenuItem("Ver historial");
        itemHistorial.addActionListener(e -> mostrarPanel(PANEL_HISTORIAL));
        menuHistorial.add(itemHistorial);

        JMenu menuFondos = new JMenu("Fondos");
        JMenuItem itemFondos = new JMenuItem("Agregar fondos");
        itemFondos.addActionListener(e -> mostrarPanel(PANEL_FONDOS));
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
        // TODO: despejar sesión cuando esto esté implementado
        dispose();
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
