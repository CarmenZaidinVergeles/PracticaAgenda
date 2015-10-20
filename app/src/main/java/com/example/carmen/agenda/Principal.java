package com.example.carmen.agenda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Principal extends AppCompatActivity {

    //Declaracion
    private ListView lv;
    private static ClaseAdaptador cl;
    private List<Contacto> agenda; //recogerá todos los contactos

    private TextView telf2, telf3, telf4;
    private Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        iniciar();
        telf2=(TextView) findViewById(R.id.etTelef2);
        telf3=(TextView) findViewById(R.id.etTelef3);
        telf4=(TextView) findViewById(R.id.etTelef4);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Mostrar los detalles de los contactos cuando se pulsa la imagen del "mas"
    //Se ha realizado a través de un diálogo en la Clase Adaptador
    public void mostrar(View v) {
        cl.mostrar(v);
    }

    //METODO INICIAR
    private void iniciar() {

        lv =(ListView) findViewById(R.id.lvLista);
        //Creamos un objeto agenda al q le pasamos este contexto
        Agenda age = new Agenda(this);

        //Vamos a llenar la agenda con todos los contactos a través del método getAgenda
        //(en realidad a través de getAgenda estamos utilizando getListaContactos)
        agenda = age.getAgenda();

        //Con un for se recorren todos los contactos de agenda y vamos obteniendo de cada contacto sus numeros
        for(Contacto aux:agenda){
            aux.setTelefonos(age.getListaNum(this, aux.getId()));
        }

        //Adaptador(pasamos el contexto actual(this), el recurso(item), y la lista de contactos(agenda)
        cl= new ClaseAdaptador(this, R.layout.item,agenda);

        //Adaptación del listView
        lv.setAdapter(cl);
        lv.setTag(agenda);


        //Registrar menu_contextual;
        registerForContextMenu(lv);
    }



    //MENU CONTEXTUAL: Este método es el que muestra el menú contextual al realizar una pulsación larga sobre el elemento.
    @Override public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater(); inflater.inflate(R.menu. menu_contextual , menu );
    }


    /*MENU CONTEXTUAL: Opcion seleccionada dentro del menu contextual
    Se ha introducido un menu_detalles para ver los detalles del contacto en concreto ya que al pulsar la imagen "mas"
    había problemas para visualizar los números de teléfono del contacto que tenía más de un teléfono
    También se ha introducido en este lugar un menu_insertar, que permite introducir un nuevo contacto. Parece inadecuado
    que al pulsar sobre un contacto se pueda introducir otro nuevo a través de este menú contextual, pero es hasta el momento
    la única forma que he encontrado de hacerlo*/

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        long id = item.getItemId();
        AdapterView.AdapterContextMenuInfo vistaInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        //Esta posición es el elemento pulsado en la vista
        int posicion = vistaInfo.position;
        //Nuestro menu contextual tendrá tres acciones: borrar, editar e insertar
        if(id==R.id.menu_borrar){
            borrar(posicion);
            actualizar();
            return true;
        } else if(id==R.id.menu_editar){
            editarConActividad(posicion);
            return true;
        }else if(id==R.id.menu_insertar){
            insertar(posicion);
            return true;
        }else if( id==R.id.menu_detalles){
            detalles(posicion);
            return true;
        }
        return super.onContextItemSelected(item);
    }


    //PROGRAMAMOS LOS METODOS DEL MENU CONTEXTUAL:

    //1)Borrar
    public void borrar(int pos){
        agenda.remove(pos);
        actualizar();
    }

    //2)Insertar contacto nuevo: se realiza desde el mismo menú contextual
    public void insertar(int pos){
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle(R.string.str_titulo_dialogo_insertar);
        LayoutInflater inflater= LayoutInflater.from(this);

        final View vista = inflater.inflate(R.layout.dialogo_insertar_contacto, null);
        alert.setView(vista);
        alert.setPositiveButton(R.string.str_positiveButton_insertar,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        long id = agenda.size() - 1;
                        EditText et1, et2;
                        et1 = (EditText) vista.findViewById(R.id.etInsertarNombre);
                        et2 = (EditText) vista.findViewById(R.id.etInsertarTelefono);

                        List<String> telf = new ArrayList<String>();
                        telf.add(et2.getText().toString());

                        Contacto c = new Contacto(id, et1.getText().toString(), telf);
                        agenda.add(c);

                        actualizar();//Ordena y actualiza el adaptador

                        //cl.notifyDataSetChanged();//No hace falta actualizar ya el adaptador
                        cl = new ClaseAdaptador (Principal.this, R.layout.item, agenda);

                        ListView lv = (ListView) findViewById(R.id.lvLista);
                        lv.setAdapter(cl);
                    }
                });
        alert.setNegativeButton(R.string.str_negativeButton_cancelar, null);
        alert.show();
    }

    //3) Editar: se edita el contacto seleccionado(no funciona)

    public void añadir() {
        Intent i = new Intent(this, Editar.class);
        startActivity(i);
    }

    public void editarConActividad(int pos){

        Intent i = new Intent(this, Editar.class);
        Bundle b=new Bundle();
        b.putInt("id",pos);
        i.putExtras(b);

        startActivity(i);

        actualizar();

    }

    //Se intentó realizar con un diálogo y no funcionaba
    /*public void editar(final int pos) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.str_titulo_dialogo_editar);

        //Se carga un contacto (que queremos editar)
        Contacto cont = new Contacto();

        //Se carga la vista
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_editar_contacto, null);
        final EditText et1, et2;
        String nombre, telefono;

        //Obtenemos nombre y telefonos actuales del contacto a editar
        nombre = agenda.get(pos).getNombre();
        telefono = cont.getTelefono(pos);

        cont.setNombre(agenda.get(pos).getNombre());
        cont.setTelef(pos, telefono);

        et1 = (EditText) vista.findViewById(R.id.etNombre);
        et2 = (EditText) vista.findViewById(R.id.etTelef);

        et1.setText(nombre);
        et2.setText(telefono);

        dialog.setView(vista);

        dialog.setPositiveButton(R.string.str_positiveButton_insertar,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        long id = agenda.size() - 1;
                        EditText et1, et2;

                        et1 = (EditText) vista.findViewById(R.id.etNombre);
                        et2 = (EditText) vista.findViewById(R.id.etTelef);

                        //Borramos el contacto a editar
                        agenda.remove(pos);//(la posicion debe ser declarada como final, si no, da error)


                        List<String> telf = new ArrayList<String>();//Telefonos de un contacto

                        telf.add(et2.getText().toString());//Añadimos los telefonos del contacto

                        Contacto cont = new Contacto(pos, et1.getText().toString(), telf);//rellenamos el contacto con los nuevos datos

                        agenda.add(cont);//Añadimos el contacto editado a la agenda

                        cl.notifyDataSetChanged();//Actualizamos adaptador

                    }
                });
        dialog.setNegativeButton(R.string.str_negativeButton_cancelar, null);
        dialog.show();
    }*/

    //4)Mostramos los detalles del contacto
    public  void detalles(final int posicion){

        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle(R.string.str_titulo_dialogo_detalles);
        LayoutInflater inflater= LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_detalles_contacto, null);
        final TextView tv,tv1;
        String nom,tel;

        nom=agenda.get(posicion).getNombre();
        tel=agenda.get(posicion).getNumeros();

        tv = (TextView) vista.findViewById(R.id.tvNom);
        tv1 = (TextView) vista.findViewById(R.id.tvTel);

        tv.setText(nom);
        tv1.setText(tel);

        alert.setView(vista);
        alert.setPositiveButton(R.string.str_positiveButton_aceptarDetalle, null);
        alert.show();

    }

   //ORDENAR CONTACTOS (Se pensaba utilizar este método, de manera posterior se prefirió el método abajo expuesto)
    /*public void ordenaContact(){
        Collections.sort(agenda, new OrdenarContactos());
    }*/

    //ACTUALIZAR (en la clase Agenda se crea el método ordenar (Collections.sort)
    public static void actualizar(){
        Agenda.ordenar();
        cl.notifyDataSetChanged();
    }



}




