package com.example.carmen.agenda;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Carmen on 10/10/2015.
 */
public class ClaseAdaptador extends ArrayAdapter<Contacto> {

    //Declaracion
    private Context ctx; //Contexto
    private int res;//layout del item
    private LayoutInflater inflador; //inflador
    private List<Contacto> contactos; //Lista de contactos

    static class ViewHolder {

        public TextView tv1, tv2;
        public ImageView ivMas;
    }

    public ClaseAdaptador(Context context, int resource, List<Contacto> objects) {

        super(context, resource, objects);

        this.ctx = context;//actividad
        this.res = resource;//Layout del item
        this.contactos = objects; //lista
        this.inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder gv = new ViewHolder(); //Declarar ViewHolder
        if (convertView == null) {
            convertView = inflador.inflate(res, null); //Inflar la vista

            TextView tvCont = (TextView) convertView.findViewById(R.id.tvContacto);//Buscamos
            gv.tv1 = tvCont; //Guardamos

            TextView tvTelef = (TextView) convertView.findViewById(R.id.tvTelefono);
            gv.tv2 = tvTelef;

            gv.ivMas =(ImageView) convertView.findViewById(R.id.ivMas);
            convertView.setTag(gv);

            gv.ivMas.setVisibility(View.INVISIBLE);//Por defecto la ponemos invisible


        } else  gv = (ViewHolder) convertView.getTag();

            gv.tv1.setText(contactos.get(position).getNombre());
            gv.tv2.setText(contactos.get(position).getNum());//Visualizar sólo el primer numero
            gv.ivMas.setId(position);//Identificar

             if (contactos.get(position).getTelefonos().size() > 1) {
                gv.ivMas.setVisibility(View.VISIBLE);

            }else{
                 gv.ivMas.setVisibility(View.INVISIBLE);
             }
            return convertView;
        }

    //Mostrar los detalles al pulsar la imagen más a través de un diálogo.
    //LLamamos a este método en la clase Principal
    public void mostrar (View v){

        Contacto aux=contactos.get(v.getId());
        //Hay un string directo pq de otra forma salía un código!!!!!!!
        String s=("Numeros de ") + aux.getNombre()+":\n";
        s+=aux.getNumeros();

                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setMessage(s);
                builder.setCancelable(true);
                builder.setPositiveButton(R.string.str_positiveButton_Salir,null);
                AlertDialog dialog = builder.create();
                dialog.show();
    }





}






















