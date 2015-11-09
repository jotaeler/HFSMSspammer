package com.spacebartech.hfsmsspammer;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.io.InputStreamReader;
import java.io.Reader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    //private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;
    //Lista de telefonos
    private List phones;
    private InputStream is;
    private ProgressDialog pd;
    private boolean web=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phones = new ArrayList();

        final EditText mensaje = (EditText) findViewById(R.id.mensaje);
        final TextView caracteres = (TextView) findViewById(R.id.caracteres);
        TextWatcher editable = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if(count!=1) {
                    if (mensaje.getText().length()+after > 160 && mensaje.getText().length()+after < 162) {

                        // 1. Instantiate an AlertDialog.Builder with its constructor
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                        // 2. Chain together various setter methods to set the dialog characteristics
                        builder.setMessage(R.string.dialog_text_caracteres)
                                .setTitle(R.string.dialog_title_caracteres);
                        // Add the buttons
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                            }
                        });
                        // 3. Get the AlertDialog from create()
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                caracteres.setText(mensaje.getText().length() + "/160");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        mensaje.addTextChangedListener(editable);

        /*mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout));
*/
        final Button botonWeb = (Button) findViewById(R.id.buttonWeb);
        botonWeb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!web) {
                    getWebNumbers();

                }else{
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "Ya se han descargado los contactos de la web", duration);
                    toast.show();
                }
                web=true;
            }
        });
        final Button agenda = (Button) findViewById(R.id.buttonContactos);

        agenda.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                accesoAgenda();
            }
        });
        setToolbar();
    }

    public void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_my_toolbar);
        toolbar.setTitle("HFSMSspammer");
        setSupportActionBar(toolbar);
    }

    public void accesoAgenda() {
        Intent i = new Intent(this, mostrarContactos.class);
        startActivity(i);

    }
        /*

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, "accesoAgenda", duration);
        toast.show();
        Uri uri = Contacts.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                String id = cursor.getString(cursor.getColumnIndex(Contacts.HAS_PHONE_NUMBER));
                String nombre = cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME));

                //comprobamos si el contacto tiene numero; si es 0 no tiene y no lo muestra
                if(id.compareTo("0")==1){
                    Log.d("Numero: ", id);
                    Log.d("Nombre: ", nombre);
                }

            }

        }
        ​​​



    /*@Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }*/
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        /*@Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }*/
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Metodos para conexion a la web y descargar
    los numeros de teléfono
     */
    //////////////////////////////////////////////////////////////////////////////////////////////

    public void getWebNumbers() {

        if (this.checkConnection()) {

            //InputStream is = this.downloadUrl("http://www.camarahogarfactory.com/listado/listado.xml");
            new DownloadWebpageTask().execute("http://www.camarahogarfactory.com/listado/listado.xml");

        }else{
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "No hay conexión a internet", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Check internet connectivity
     *
     * @return true if connected
     */
    public boolean checkConnection() {

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            return true;
        } else {
            return false;
        }

    }

    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    private class DownloadWebpageTask extends AsyncTask<String, Void, InputStream> {

        private Exception e = null;
        @Override
        protected void onPreExecute(){
            pd=ProgressDialog.show(MainActivity.this, "Conectando con la web", "Por favor espere...");
        }

        @Override
        protected InputStream doInBackground(String... _url) {

            // params comes from the execute() call: params[0] is the url.
            try {
                InputStream is = downloadUrl(_url[0]);
                //String contentAsString = readIt(is, 200);
                //int t=0;
                List lista = parse(is);
                phones.addAll(lista);
                return is;
            } catch (XmlPullParserException | IOException _e) {
                e = _e;
                return null;
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(InputStream result) {
            TextView seleccionados = (TextView) findViewById(R.id.contactosSeleccionados);
            String texto = Integer.toString(phones.size()) + " Contactos seleccionados";
            seleccionados.setText(texto);
            pd.dismiss();
            if (e != null) e.printStackTrace();
        }
    }


    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    private InputStream downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 200;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            is = conn.getInputStream();

            return is;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            /*if (is != null) {
                is.close();
            }*/
        }
    }

    /**
     * Recibe un InputStream y devuelve la lista con el contenido
     *
     * @param in
     * @return
     * @throws XmlPullParserException
     * @throws IOException
     */
    public List parse(InputStream in) throws XmlPullParserException, IOException {

        List r = null;
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            Reader reader = new InputStreamReader(in, "UTF-8");
            parser.setInput(reader);
            parser.nextTag();
            /*String name = parser.getName();
            String asd=parser.getText();*/

            r = readFeed(parser);

        } finally {
            in.close();
        }
        return r;
    }

    /**
     * Recive el Parser y extrae todas las entradas PHONE que hay, devuelve la lista de numeros
     *
     * @param parser
     * @return
     * @throws XmlPullParserException
     * @throws IOException
     */
    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, null, "xml");
        List r = new ArrayList();
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("phone")) {
                if (parser.next() == XmlPullParser.TEXT) {
                    r.add(parser.getText());
                    parser.nextTag();
                }
            }
        }
        return r;
    }


}
