package com.example.carmen.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carmen on 18/10/2015.
 */
public class Editar extends AppCompatActivity {
    private int id = -1;
    private TextView nombre, telf1;
    private TextView telf2, telf3, telf4;
    private Contacto aux;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogo_editar_contacto);

        Intent i = getIntent(); //Intent(Actividad)
        Bundle b = i.getExtras(); //Almacenador

        id = b.getInt("id");
        aux = Agenda.getContacto(id);//Obtener el contacto con el id
        nombre = (TextView) findViewById(R.id.etNombre); //Buscar
        nombre.setText(aux.getNombre()); //Editar nombre
        telf1 = (TextView) findViewById(R.id.etTelef); //Buscar
        telf1.setText(aux.getNum()); //Editar Telefono

        //Busqueda de  los campos vacíos de los telefonos que se quieran introducir
        telf2 = (TextView) findViewById(R.id.etTelef2);
        telf3 = (TextView) findViewById(R.id.etTelef3);
        telf4 = (TextView) findViewById(R.id.etTelef4);


        button = (Button) findViewById(R.id.btAñadir);//Busqueda boton
        button.setText(this.getString(R.string.str_btAceptar));
        int tam = aux.size();
        switch (tam) {
            case 2:
                telf2.setText(aux.getNumP(1));
                break;
            case 3:
                telf3.setText(aux.getNumP(2));
                break;
            case 4:
                telf4.setText(aux.getNumP(3));
                break;
            default:
                break;
        }

    }

    public void add(View v) {
        String nom, tlf1, tlf2, tlf3, tlf4;
        List<String> nums = new ArrayList<>();

        nom = nombre.getText().toString();
        tlf1 = telf1.getText().toString();
        if (!nom.isEmpty() && !tlf1.isEmpty()) {
            nums.add(tlf1);
            if (telf2.length() > 0) {
                System.out.println("---------");
                tlf2 = telf2.getText().toString();
                nums.add(tlf2);
            }
            if (telf3.length() > 0) {
                tlf3 = telf3.getText().toString();
                nums.add(tlf3);
            }
            if (telf4.length() > 0) {
                tlf4 = telf4.getText().toString();
                nums.add(tlf4);
            }

            aux.setNombre(nom);
            aux.setTelefonos(nums);

            Principal.actualizar();
            finish();
        }else Toast.makeText(this, R.string.str_campo_vacio,
             Toast.LENGTH_SHORT).show();
        }

    public void cancelar(View v) {
        finish();
    }

}


