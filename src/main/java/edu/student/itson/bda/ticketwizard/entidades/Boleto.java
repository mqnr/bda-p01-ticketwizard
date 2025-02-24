package edu.student.itson.bda.ticketwizard.entidades;

import java.math.BigDecimal;

public class Boleto {

    private final int id;
    private String numeroSerie;
    private BigDecimal precio;
    private int fila;
    private String asiento;
    private int idAsiento;
    private int usuarioId;
    private int eventoId;

    public Boleto(int id, String numeroSerie, BigDecimal precio, int fila, String asiento, int idAsiento, int usuarioId, int eventoId) {
        this.id = id;
        this.numeroSerie = numeroSerie;
        this.precio = precio;
        this.fila = fila;
        this.asiento = asiento;
        this.idAsiento = idAsiento;
        this.usuarioId = usuarioId;
        this.eventoId = eventoId;
    }

    public Boleto(int id, BigDecimal precio, int fila, String asiento, int idAsiento, int eventoId) {
        this.id = id;
        this.precio = precio;
        this.fila = fila;
        this.asiento = asiento;
        this.idAsiento = idAsiento;
        this.eventoId = eventoId;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public int getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(int idAsiento) {
        this.idAsiento = idAsiento;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getEventoId() {
        return eventoId;
    }

    public void setEventoId(int eventoId) {
        this.eventoId = eventoId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Boleto other = (Boleto) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Boleto{" + "id=" + id + ", numeroSerie=" + numeroSerie + ", precio=" + precio + ", fila=" + fila + ", asiento=" + asiento + ", idAsiento=" + idAsiento + ", usuarioId=" + usuarioId + ", eventoId=" + eventoId + '}';
    }
}
