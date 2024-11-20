package com.example.tareatrabajo3;

public class Tarea {

    String nombre;
    Boolean check;

    public Tarea(String nombre, int imagen,Boolean check) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.check = check;
    }
    public Tarea(String nombre, int imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.check = false;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    int imagen;

}
