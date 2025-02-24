package edu.student.itson.bda.ticketwizard.control;

import edu.student.itson.bda.ticketwizard.dtos.BoletoDisponibleDTO;
import edu.student.itson.bda.ticketwizard.entidades.Boleto;
import edu.student.itson.bda.ticketwizard.entidades.Transaccion;
import edu.student.itson.bda.ticketwizard.entidades.Usuario;
import edu.student.itson.bda.ticketwizard.persistencia.BoletosDAO;
import edu.student.itson.bda.ticketwizard.persistencia.TransaccionesDAO;
import edu.student.itson.bda.ticketwizard.persistencia.UsuariosDAO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

public class ControlBoletos {

    private static final BigDecimal PORCENTAJE_COMISION = new BigDecimal("0.03");

    private final BoletosDAO boletosDAO;
    private final Usuario usuario;
    private ControlUsuarios controlUsuario;

    private final TransaccionesDAO transaccionesDAO;
    private final UsuariosDAO usuariosDAO;

    public ControlBoletos(BoletosDAO boletosDAO, Usuario usuario) {
        this.boletosDAO = boletosDAO;
        this.usuario = usuario;
        this.controlUsuario = controlUsuario;

        // TODO: Inyectar estas dependencias apropiadamente
        this.transaccionesDAO = new TransaccionesDAO();
        this.usuariosDAO = new UsuariosDAO();
    }

    public List<Boleto> consultarListaBoletos() {
        return this.boletosDAO.obtenerTodosBoletos();
    }

    // nuevos métodos para la funcionalidad de compra
    public List<BoletoDisponibleDTO> consultarBoletosDisponibles() {
        return boletosDAO.consultarBoletosDisponibles();
    }

    public void comprarBoleto(int numeroSerie, int compradorId) throws IllegalStateException {
        // 1. verificar que el boleto no esté reservado
        if (boletosDAO.estaReservado(numeroSerie)) {
            throw new IllegalStateException("El boleto ya está reservado");
        }

        // 2. obtener información del boleto y del comprador
        BoletoDisponibleDTO boleto = boletosDAO.consultarBoletoDisponible(numeroSerie)
                .orElseThrow(() -> new IllegalArgumentException("Boleto no encontrado"));

        Usuario comprador = usuariosDAO.consultarUsuario(compradorId);

        // 3. calcular monto total con comisión si aplica
        BigDecimal montoTotal = boleto.getPrecioVenta();
        BigDecimal comision = null;

        if (!boleto.esVentaInicial()) {
            comision = calcularComision(montoTotal);
            montoTotal = montoTotal.add(comision);
        }

        // 4. verificar saldo del comprador
        if (comprador.getSaldo().compareTo(montoTotal) < 0) {
            // crear reservación y notificar
            boletosDAO.reservarBoleto(numeroSerie, compradorId);
            throw new IllegalStateException(
                    String.format("Saldo insuficiente. Se ha reservado el boleto por 10 minutos. "
                            + "Monto requerido: $%.2f", montoTotal));
        }

        // 5. realizar la transacción
        try {
            Transaccion transaccion;
            if (boleto.esVentaInicial()) {
                transaccion = new Transaccion(
                        numeroSerie,
                        compradorId,
                        boleto.getPrecioVenta(),
                        LocalDateTime.now()
                );
            } else {
                transaccion = new Transaccion(
                        numeroSerie,
                        boleto.getVendedorId(),
                        compradorId,
                        boleto.getPrecioVenta(),
                        comision,
                        LocalDateTime.now()
                );

                // actualizar saldo del vendedor
                usuariosDAO.agregarSaldo(
                        boleto.getVendedorId(),
                        boleto.getPrecioVenta()
                );
            }

            // registrar transacción y actualizar estados
            transaccionesDAO.registrarTransaccion(transaccion);
            boletosDAO.actualizarPropietario(numeroSerie, compradorId);
            usuariosDAO.descontarSaldo(compradorId, montoTotal);

            if (!boleto.esVentaInicial()) {
                boletosDAO.eliminarReventa(numeroSerie);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al procesar la compra", e);
        }
    }

    private BigDecimal calcularComision(BigDecimal precio) {
        return precio.multiply(PORCENTAJE_COMISION)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
