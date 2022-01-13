package com.fic.practica1.capa_datos;

public class Entregas {

    String Direccion,Estatus,Nombre,Producto,Telefono,Unidades;

    public Entregas() {

    }

    public Entregas(String Direccion, String Estatus, String Nombre, String Producto, String Telefono,String Unidades) {

        this.Direccion=Direccion;
        this.Estatus=Estatus;
        this.Nombre=Nombre;
        this.Producto=Producto;
        this.Telefono=Telefono;
        this.Unidades=Unidades;
    }

    public String getUnidades() {
        return Unidades;
    }

    public void setUnidades(String unidades) {
        Unidades = unidades;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getEstatus() {
        return Estatus;
    }

    public void setEstatus(String estatus) {
        Estatus = estatus;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String producto) {
        Producto = producto;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono= telefono;
    }
}
