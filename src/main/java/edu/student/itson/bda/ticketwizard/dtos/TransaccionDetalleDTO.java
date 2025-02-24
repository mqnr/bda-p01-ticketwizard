package edu.student.itson.bda.ticketwizard.dtos;

import edu.student.itson.bda.ticketwizard.entidades.Transaccion.TipoAdquisicion;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransaccionDetalleDTO {

    private final int id;
    private final TipoAdquisicion tipoAdquisicion;
    private final Integer vendedorId;
    private final int compradorId;
    private final BigDecimal precio;
    private final BigDecimal comision;
    private final LocalDateTime fechaTransaccion;

    private final int numeroSerie;
    private final int fila;
    private final String asiento;

    private final String nombreEvento;
    private final LocalDate fechaEvento;
    private final String recintoEvento;

    public TransaccionDetalleDTO(int id, TipoAdquisicion tipoAdquisicion,
            Integer vendedorId, int compradorId, BigDecimal precio,
            BigDecimal comision, LocalDateTime fechaTransaccion,
            int numeroSerie, int fila, String asiento,
            String nombreEvento, LocalDate fechaEvento, String recintoEvento) {
        this.id = id;
        this.tipoAdquisicion = tipoAdquisicion;
        this.vendedorId = vendedorId;
        this.compradorId = compradorId;
        this.precio = precio;
        this.comision = comision;
        this.fechaTransaccion = fechaTransaccion;
        this.numeroSerie = numeroSerie;
        this.fila = fila;
        this.asiento = asiento;
        this.nombreEvento = nombreEvento;
        this.fechaEvento = fechaEvento;
        this.recintoEvento = recintoEvento;
    }

    // Getters
    public int getId() {
        return id;
    }

    public TipoAdquisicion getTipoAdquisicion() {
        return tipoAdquisicion;
    }

    public Integer getVendedorId() {
        return vendedorId;
    }

    public int getCompradorId() {
        return compradorId;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public LocalDateTime getFechaTransaccion() {
        return fechaTransaccion;
    }

    public int getNumeroSerie() {
        return numeroSerie;
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

    public String getAsientoCompleto() {
        return String.format("Fila %d, Asiento %s", fila, asiento);
    }

    public String getEventoCompleto() {
        return String.format("%s en %s", nombreEvento, recintoEvento);
    }
}
