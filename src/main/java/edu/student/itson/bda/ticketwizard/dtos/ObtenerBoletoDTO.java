package edu.student.itson.bda.ticketwizard.dtos;

import java.math.BigDecimal;

/**
 *
 * @author LENOVO
 */
public class ObtenerBoletoDTO {
    private String numeroSerie;
    private BigDecimal precio;
    private int fila;
    private String asiento;

    public ObtenerBoletoDTO(String numeroSerie, BigDecimal precio, int fila, String asiento) {
        this.numeroSerie = numeroSerie;
        this.precio = precio;
        this.fila = fila;
        this.asiento = asiento;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public int getFila() {
        return fila;
    }

    public String getAsiento() {
        return asiento;
    }
    
    
}
