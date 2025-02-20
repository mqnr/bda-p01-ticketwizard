package edu.student.itson.bda.ticketwizard.dtos;

import java.math.BigDecimal;

public class AgregarSaldoDTO {

    private final String email;
    private final BigDecimal monto;

    public AgregarSaldoDTO(String email, BigDecimal monto) {
        this.email = email;
        this.monto = monto;
    }

    public String getEmail() {
        return email;
    }

    public BigDecimal getMonto() {
        return monto;
    }
}
