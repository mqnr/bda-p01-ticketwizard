package edu.student.itson.bda.ticketwizard.dtos;

import java.math.BigDecimal;

public class AgregarSaldoDTO {

    private final int idUsuario;
    private final BigDecimal monto;

    public AgregarSaldoDTO(int idUsuario, BigDecimal monto) {
        this.idUsuario = idUsuario;
        this.monto = monto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public BigDecimal getMonto() {
        return monto;
    }
}
