package edu.student.itson.bda.ticketwizard.dtos;

import edu.student.itson.bda.ticketwizard.entidades.Transaccion.TipoAdquisicion;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BoletoDisponibleDTO {

    private final int numeroSerie;
    private final BigDecimal precioOriginal;
    private final BigDecimal precioVenta;
    private final int fila;
    private final String asiento;

    private final String nombreEvento;
    private final LocalDate fechaEvento;
    private final String recintoEvento;
    private final String ciudadEvento;
    private final String estadoEvento;

    private final Integer vendedorId;
    private final String nombreVendedor;
    private final LocalDate fechaLimiteReventa;
    private final TipoAdquisicion tipoVenta;

    public BoletoDisponibleDTO(int numeroSerie, BigDecimal precioOriginal,
            BigDecimal precioVenta, int fila, String asiento,
            String nombreEvento, LocalDate fechaEvento, String recintoEvento,
            String ciudadEvento, String estadoEvento, Integer vendedorId,
            String nombreVendedor, LocalDate fechaLimiteReventa,
            TipoAdquisicion tipoVenta) {
        this.numeroSerie = numeroSerie;
        this.precioOriginal = precioOriginal;
        this.precioVenta = precioVenta;
        this.fila = fila;
        this.asiento = asiento;
        this.nombreEvento = nombreEvento;
        this.fechaEvento = fechaEvento;
        this.recintoEvento = recintoEvento;
        this.ciudadEvento = ciudadEvento;
        this.estadoEvento = estadoEvento;
        this.vendedorId = vendedorId;
        this.nombreVendedor = nombreVendedor;
        this.fechaLimiteReventa = fechaLimiteReventa;
        this.tipoVenta = tipoVenta;
    }

    public int getNumeroSerie() {
        return numeroSerie;
    }

    public BigDecimal getPrecioOriginal() {
        return precioOriginal;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public int getFila() {
        return fila;
    }

    public String getAsiento() {
        return asiento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public String getRecintoEvento() {
        return recintoEvento;
    }

    public String getCiudadEvento() {
        return ciudadEvento;
    }

    public String getEstadoEvento() {
        return estadoEvento;
    }

    public Integer getVendedorId() {
        return vendedorId;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public LocalDate getFechaLimiteReventa() {
        return fechaLimiteReventa;
    }

    public TipoAdquisicion getTipoVenta() {
        return tipoVenta;
    }

    public String getUbicacionCompleta() {
        return String.format("%s, %s, %s", recintoEvento, ciudadEvento, estadoEvento);
    }

    public String getAsientoCompleto() {
        return String.format("Fila %d, Asiento %s", fila, asiento);
    }

    public boolean esVentaInicial() {
        return tipoVenta == TipoAdquisicion.VENTA_INICIAL;
    }
}
