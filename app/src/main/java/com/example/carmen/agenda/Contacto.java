package com.example.carmen.agenda;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carmen on 10/10/2015.
 */
public class Contacto implements Serializable,Comparable<Contacto> {

    private long id;
    private String nombre;
    private List<String> telefonos;

    public Contacto(long id, String nombre, List<String> telefonos) {
        this.id = id;
        this.nombre = nombre;
        this.telefonos = telefonos;
    }

    public Contacto() {
        this(0, "0", new ArrayList<String>());

    }

    public String getNum() {
        return telefonos.get(0);
    }

    public String getNumP(int pos) {
        return telefonos.get(pos);
    }

    //public int tamArrayNum() {return telefonos.size();}

    public String getNumeros() {
        String s="";
        for(String a:telefonos)
            s+=a+"\n";
        return s;
    }

    public boolean isEmpty() {
        return telefonos.isEmpty();
    }

    public boolean add(String object) {
        return telefonos.add(object);
    }

    public String getTelefono(int location) {
        return telefonos.get(location);
    }

    public void setTelefono(List<String> telefono) {
        this.telefonos = telefono;
    }

    public void setTelef(int location, String lisTelef){this.telefonos.set(location,lisTelef); }

    public int size() {
        return telefonos.size();
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefonos(List<String> telefonos) {
        this.telefonos = telefonos;
    }

    public long getId() {

        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getTelefonos() {
        return telefonos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contacto contacto = (Contacto) o;

        return id == contacto.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public int compareTo(Contacto contacto) {
        int r = this.nombre.compareTo(contacto.nombre);
        if (r == 0) {
            r = (int) (this.id - contacto.id);
        }

        return r;
    }

    @Override
    public String toString() {
        return "Contacto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefonos=" + telefonos +
                '}';
    }
}