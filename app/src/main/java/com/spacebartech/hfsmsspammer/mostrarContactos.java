package com.spacebartech.hfsmsspammer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;


public class mostrarContactos extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,Serializable{
    private ListView lv;
    private Button btnAceptar;
    private Button btnCancelar;
    private ArrayList<Contacto> listaContactos;
    private ContactoAdapter adapterContacto;
    private ContactoAdapter.ContactoHolder holder = new ContactoAdapter.ContactoHolder();
    private ArrayList<String> listaNumerosAdevolver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        lv = (ListView) findViewById(R.id.listview);
        listaContactos=new ArrayList<Contacto>();
        btnAceptar= (Button) findViewById(R.id.btn_aceptar);
        //CUANDO PINCHEMOS EN ACEPTAR
        View.OnClickListener v=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AQUI LO QUE HACE SI PULSO EL BOTON ACEPTAR
                listaNumerosAdevolver=getListaNumeros();
                Intent intent = new Intent();
                intent.putExtra("listaNumerosAgenda", listaNumerosAdevolver);
                setResult(Activity.RESULT_OK, intent);

                finish();

            }
        };
        btnAceptar.setOnClickListener(v);
        btnCancelar=(Button) findViewById(R.id.btn_cancelar);
        //CUANDO PINCHEMOS EN CANCELAR
        View.OnClickListener h=new View.OnClickListener() {
            @Override
            public void onClick(View h) {
                //AQUI LO QUE HACE SI PULSO EL BOTON CANCELAR
                finish();
            }
        };
        btnCancelar.setOnClickListener(h);

        consultaAgenda();
    }

    public void consultaAgenda(){

        Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        //Introduzco el SELECCIONAR TODOS
        Contacto todos=new Contacto("SELECCIONAR TODOS","");
        listaContactos.add(todos);
        while(c.moveToNext()){
            String ContactID = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            String nombre = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String tieneTlf =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            Cursor indiceTlfs = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "='" + ContactID + "'",
                    null, null);


            while(indiceTlfs.moveToNext()){
                if(Integer.parseInt(tieneTlf) == 1) { //SI EL CONTACTO TIENE NUMERO DE TELEFONO
                    String numero = indiceTlfs.getString(indiceTlfs.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Contacto con = new Contacto (nombre,numero);
                    listaContactos.add(con);

                }
            }

            adapterContacto = new ContactoAdapter(listaContactos,this);
            lv.setAdapter(adapterContacto);


            indiceTlfs.close();

        }

        c.close();
    } // Fin de consultaAgenda()

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int pos = lv.getPositionForView(buttonView);
            int nContactos=0;
            Contacto c;

            if(pos==0){
                while(nContactos<listaContactos.size()){
                    listaContactos.get(nContactos).setSeleccionado(true);
                    //System.out.println("Nombre: " + listaContactos.get(nContactos).getNombre() + " Numero: " + listaContactos.get(nContactos).getNumero() + " Seleccionado: " + listaContactos.get(nContactos).getSeleccionado());
                    nContactos++;
                }

            }else{
                //Aqui se deselecciona el SELECCIONARTODO HAY QUE HACERLO
                c = listaContactos.get(pos);
                c.setSeleccionado(true);
                //Toast.makeText(this, "Nombre: " + c.getNombre() + " Numero: "+ c.getNumero()+" Seleccionado: " + isChecked, Toast.LENGTH_SHORT).show();
            }


           /* if(listaContactos.get(0).getSeleccionado()==true){
                Toast.makeText(this,listaContactos.get(0).getNombre(), Toast.LENGTH_SHORT).show();
                while(nContactos<listaContactos.size()-1){
                    holder.checkBoxContacto = (CheckBox) lv.findViewById(R.id.chk_box);
                    holder.checkBoxContacto.setChecked(true);
                }
            }*/

    }

    public ArrayList<String> getListaNumeros() {
        int nContactos = 1;
        ArrayList<String> numeros=new ArrayList<String>();
        while (nContactos < listaContactos.size()) {
            if (listaContactos.get(nContactos).getSeleccionado() == true) {
                numeros.add(listaContactos.get(nContactos).getNumero());
            }
            nContactos++;

        }
        return numeros;
    }

}
