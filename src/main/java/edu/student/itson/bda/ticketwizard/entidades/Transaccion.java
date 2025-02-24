package edu.student.itson.bda.ticketwizard.entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaccion {

    private Integer id;
    private int boletoId;
    private TipoAdquisicion tipoAdquisicion;
    private Integer vendedorId;
    private int compradorId;
    private BigDecimal precio;
    private BigDecimal comision;
    private LocalDateTime fecha;

    public enum TipoAdquisicion {
        VENTA_INICIAL,
        REVENTA
    }

    // venta inicial
    public Transaccion(int boletoId, int compradorId, BigDecimal precio, LocalDateTime fecha) {
        this.boletoId = boletoId;
        this.tipoAdquisicion = TipoAdquisicion.VENTA_INICIAL;
        this.compradorId = compradorId;
        this.precio = precio;
        this.fecha = fecha;
    }

    public Transaccion(int boletoId, int vendedorId, int compradorId,
            BigDecimal precio, BigDecimal comision, LocalDateTime fecha) {
        this.boletoId = boletoId;
        this.tipoAdquisicion = TipoAdquisicion.REVENTA;
        this.vendedorId = vendedorId;
        this.compradorId = compradorId;
        this.precio = precio;
        this.comision = comision;
        this.fecha = fecha;
    }

    public void setTipoAdquisicion(TipoAdquisicion tipoAdquisicion) {
        if (tipoAdquisicion == TipoAdquisicion.VENTA_INICIAL) {
            if (this.vendedorId != null) {
                throw new IllegalStateException("VENTA_INICIAL no puede tener vendedor");
            }
            if (this.comision != null) {
                throw new IllegalStateException("VENTA_INICIAL no puede tener comisión");
            }
        } else { // REVENTA
            if (this.vendedorId == null) {
                throw new IllegalStateException("REVENTA requiere vendedor");
            }
            if (this.comision == null) {
                throw new IllegalStateException("REVENTA requiere comisión");
            }
        }
        this.tipoAdquisicion = tipoAdquisicion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getBoletoId() {
        return boletoId;
    }

    public void setBoletoId(int boletoId) {
        this.boletoId = boletoId;
    }

    public TipoAdquisicion getTipoAdquisicion() {
        return tipoAdquisicion;
    }

    public Integer getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(Integer vendedorId) {
        if (this.tipoAdquisicion == TipoAdquisicion.VENTA_INICIAL && vendedorId != null) {
            throw new IllegalStateException("VENTA_INICIAL no puede tener vendedor");
        }
        this.vendedorId = vendedorId;
    }

    public int getCompradorId() {
        return compradorId;
    }

    public void setCompradorId(int compradorId) {
        this.compradorId = compradorId;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        if (this.tipoAdquisicion == TipoAdquisicion.VENTA_INICIAL && comision != null) {
            throw new IllegalStateException("VENTA_INICIAL no puede tener comisión");
        }
        this.comision = comision;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
