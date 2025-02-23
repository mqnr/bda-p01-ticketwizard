package edu.student.itson.bda.ticketwizard.dtos;

/**
 *
 * @author LENOVO
 */
public class ObtenerEventoDTO {

    private String nombre;
    private String fecha;
    private String recinto;
    private String ciudad;
    private String estado;
    private String descripcion;

    public ObtenerEventoDTO(String nombre, String fecha, String recinto, String ciudad, String estado, String descripcion) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.recinto = recinto;
        this.ciudad = ciudad;
        this.estado = estado;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public String getRecinto() {
        return recinto;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
