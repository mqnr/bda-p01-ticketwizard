package edu.student.itson.bda.ticketwizard.dtos;

public class BuscarUsuarioDTO {

    private final String email;

    public BuscarUsuarioDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
