package com.example.carmen.agenda;

import java.util.Comparator;

/**
 * Created by Carmen on 17/10/2015.
 */
public class OrdenarContactos implements Comparator<Contacto> {

    @Override
    public int compare(Contacto c1, Contacto c2) {
        if(c1.getNombre().compareToIgnoreCase(c2.getNombre())>0) {
            return 1;
        }
        if(c1.getNombre().compareToIgnoreCase(c2.getNombre())<0){
            return -1;
        }

        return 0;
    }
}

