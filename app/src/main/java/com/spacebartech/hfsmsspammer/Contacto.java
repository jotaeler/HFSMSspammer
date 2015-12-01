package com.spacebartech.hfsmsspammer;

/**
 * Created by Ismael on 09/11/2015.
 */
public class Contacto {
    String nombre;
    String numero;
    boolean seleccionado = false;

    public Contacto(String nombre,String numero) {
        this.nombre = nombre;
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
