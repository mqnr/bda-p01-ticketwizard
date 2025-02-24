package edu.student.itson.bda.ticketwizard.dtos;

public class ObtenerUsuarioDTO {

    private final int idUsuario;
    private String nombre, apellido, direccion, email;

    public ObtenerUsuarioDTO(int idUsuario, String nombre, String apellido, String direccion, String email) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.email = email;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }

}
