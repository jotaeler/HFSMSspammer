package com.spacebartech.hfsmsspammer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ismael on 09/11/2015.
 */
public class ContactoAdapter extends ArrayAdapter<Contacto>{
    private ArrayList<Contacto> listaContactos;
    private Context context;

    public ContactoAdapter(ArrayList<Contacto> listaContactos,Context context) {
        super(context, R.layout.contenidolist,listaContactos);
        this.listaContactos = listaContactos;
        this.context = context;
    }

    public static class ContactoHolder{
        public TextView nombreContacto;
        public CheckBox checkBoxContacto;
    }

    public View getView(int posicion, View fila, ViewGroup parent){

        if(fila == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            fila = inflater.inflate(R.layout.contenidolist, null, true);
            ContactoHolder holder = new ContactoHolder();
            holder.nombreContacto = (TextView) fila.findViewById(R.id.nombre);
            holder.checkBoxContacto = (CheckBox) fila.findViewById(R.id.chk_box);
            holder.checkBoxContacto.setOnCheckedChangeListener((mostrarContactos) context);

            fila.setTag(holder);
        }

        Contacto c = listaContactos.get(posicion);
        ContactoHolder holder = (ContactoHolder) fila.getTag();

        holder.nombreContacto.setText(c.getNombre());
        holder.checkBoxContacto.setChecked(c.getSeleccionado());
        holder.checkBoxContacto.setTag(c);

        return fila;

        /*View v = fila;
        Contacto c = listaContactos.get(posicion);
        ContactoHolder holder = new ContactoHolder();

        if (fila == null){
            LayoutInflater inflado = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
            v = inflado.inflate(R.layout.contenidolist,null);


            //Guardo en el holder los datos del contacto
            holder.nombreContacto = (TextView) v.findViewById(R.id.nombre);
            //holder.telefonoContacto = (TextView) v.findViewById(R.id.telefono);
            holder.checkBoxContacto = (CheckBox) v.findViewById(R.id.chk_box);

            holder.checkBoxContacto.setOnCheckedChangeListener((mostrarContactos) context);
        }else{
            holder = (ContactoHolder) v.getTag();
        }

        holder.nombreContacto.setText((CharSequence)c.getNombre());
        holder.nombreContacto.setText(c.getNombre());
        //holder.telefonoContacto.setText(c.getNumero());
        holder.checkBoxContacto.setChecked(c.getSeleccionado());
        holder.checkBoxContacto.setTag(c);



        return v;*/

    }
}
