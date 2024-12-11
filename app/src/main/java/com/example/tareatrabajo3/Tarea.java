package com.example.tareatrabajo3;

public class Tarea {
    private String nombre;
    private int imagen;
    private boolean check;

    public Tarea(String nombre, int imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.check = false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
