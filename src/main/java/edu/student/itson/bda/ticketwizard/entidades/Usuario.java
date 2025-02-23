package edu.student.itson.bda.ticketwizard.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Usuario {

    private final int id;
    private String nombre;
    private String apellido;
    private String direccion;
    private String email;
    private BigDecimal saldo;
    private LocalDate fechaNacimiento;

    public Usuario(int id, String nombre, String apellido, String direccion, String email, BigDecimal saldo, LocalDate fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.email = email;
        this.saldo = saldo;
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public void incrementarSaldo(BigDecimal monto) {
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        this.saldo = this.saldo.add(monto);
    }

    public void decrementarSaldo(BigDecimal monto) {
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        if (monto.compareTo(saldo) > 0) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        this.saldo = this.saldo.subtract(monto);
    }

    public static boolean esEmailValido(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && Pattern.matches(regex, email);
    }

    public static boolean esFechaValida(LocalDate fechaNacimiento) {
        return fechaNacimiento != null && fechaNacimiento.isBefore(LocalDate.now());
    }

    public static boolean verificarEmailUnico(String email, List<Usuario> usuarios) {
        return usuarios.stream().noneMatch(usuario -> usuario.getEmail().equals(email));
    }

    public boolean puedeComprarBoleto(Usuario vendedor) {
        if (this.id == vendedor.getId()) {
            throw new IllegalArgumentException("No puedes comprar tu propio boleto");
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final Usuario other = (Usuario) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", direccion=" + direccion + ", email=" + email + ", saldo=" + saldo + ", fechaNacimiento=" + fechaNacimiento + '}';
    }

}
