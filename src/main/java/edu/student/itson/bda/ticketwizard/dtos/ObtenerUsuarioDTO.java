package edu.student.itson.bda.ticketwizard.dtos;

public class ObtenerUsuarioDTO {

    private final int idUsuario;

    public ObtenerUsuarioDTO(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
}
