package com.example.carmen.agenda;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Carmen on 16/10/2015.
 */
public class Agenda {
    private static List agenda;

    public Agenda(Context c){
        //Obtener todos los contactos pasandole el contexto(sólo tenemos id y nombre del contacto)
        agenda=getListaContactos(c);
    }

    public List getListaNum(Context c, long id){
        // Obtener los telefonos de un contacto pasándole el contexto y el id del contacto
        return agenda = getListaTelefonos(c,id);
    }

    public static Contacto getContacto(int pos){//devuelve un contacto segun su posicion en el array
        return (Contacto)agenda.get(pos);
    }

    public List getAgenda(){
        return agenda;
    }

    /*public static int getUltId(){
        return agenda.size();
    }

    public static void setContacto(Contacto aux){
        agenda.add(aux);
    }*/

    public static void ordenar(){
        Collections.sort(agenda);
    }//Ordenar los contactos

    @Override
    public String toString(){
        String s="";
        for(Object aux:agenda){
            s+=aux.toString();
        }
        return s;
    }


    public static List<Contacto> getListaContactos(Context contexto){
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String proyeccion[] = null;
        String seleccion = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = ? and " +
                ContactsContract.Contacts.HAS_PHONE_NUMBER + "= ?";
        String argumentos[] = new String[]{"1","1"};
        String orden = ContactsContract.Contacts.DISPLAY_NAME + " collate localized asc";
        Cursor cursor = contexto.getContentResolver().query(uri, proyeccion, seleccion, argumentos, orden);
        int indiceId = cursor.getColumnIndex(ContactsContract.Contacts._ID);
        int indiceNombre = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        List<Contacto> lista = new ArrayList<>();
        Contacto contacto;
        while(cursor.moveToNext()){
            contacto = new Contacto();
            contacto.setId(cursor.getLong(indiceId));
            contacto.setNombre(cursor.getString(indiceNombre));
            lista.add(contacto);
        }
        return lista;
    }

    public static List<String> getListaTelefonos(Context contexto, long id){
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String proyeccion[] = null;
        String seleccion = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?";
        String argumentos[] = new String[]{id+""};
        String orden = ContactsContract.CommonDataKinds.Phone.NUMBER;
        Cursor cursor = contexto.getContentResolver().query(uri, proyeccion, seleccion, argumentos, orden);
        int indiceNumero = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        List<String> lista = new ArrayList<>();
        String numero;
        while(cursor.moveToNext()){
            numero = cursor.getString(indiceNumero);
            lista.add(numero);
        }
        return lista;
    }
}


