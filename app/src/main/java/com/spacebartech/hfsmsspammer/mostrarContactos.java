package com.spacebartech.hfsmsspammer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class mostrarContactos extends AppCompatActivity {
    List<Fila> fila;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(android.R.id.list);


        fila = new ArrayList<Fila>(30);
        Fila row = null;
        for (int i = 1; i < 31; i++) {
            row = new Fila();
            row.setTitle("Title " + i);
            row.setSubtitle("Subtitle " + i);
            fila.add(row);
        }

        fila.get(3).setChecked(true);
        fila.get(6).setChecked(true);
        fila.get(9).setChecked(true);

        listView.setAdapter(new CustomArrayAdapter(this, fila));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MainActivity.this,
                        fila.get(position).getTitle(), Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}







/*public class mostrarContactos extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        consultaAgenda();


    }

    private ArrayList<String> consultaAgenda(){

        ArrayList<String> datos = new ArrayList<String>();


        Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, null);
        while(c.moveToNext()){
            String ContactID = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            String nombre = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String tieneTlf =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            Cursor indiceTlfs = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "='" + ContactID + "'",
                    null, null);


            while(indiceTlfs.moveToNext()){
                if(Integer.parseInt(tieneTlf) == 1) { //SI EL CONTACTO TIENE NUMERO DE TELEFONO
                    String numero = indiceTlfs.getString(
                        indiceTlfs.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        System.out.println("Nombre: "+nombre+" Número de teléfono: " + numero);
                }
            }


        indiceTlfs.close();

        }
        c.close();
        return datos;
    } // Find de consultaAgenda()

}*/
