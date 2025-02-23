package edu.student.itson.bda.ticketwizard.dtos;

public class ObtenerUsuarioDTO {

    private final int usuarioId;

    public ObtenerUsuarioDTO(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }
}
