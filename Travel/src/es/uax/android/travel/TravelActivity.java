/**
 * Ander Ortega Herrero.
 * 		Ejercicio: Unidad 3. Ejercicio Feedback 1.
 * 		Asignatura: UAX 002 - Desarrollo para el sistema operativo Android.
 *  	Curso: MASTER UNIVERSITARIO EN INGENIERIA DE DESARROLLO PARA DISPOSITIVOS MOVILES.
 * 		OpenUAX. CURSO 2014-2015.
 * 
 * Vamos a crear una pequeña aplicación donde almacenar la información de
 * nuestros viajes. Por ahora vamos a guardar la ciudad, el país y el año de la visita,
 * y un campo para una anotación opcional. Debes diseñar e implementar dos
 * activities con los nombres TravelActivity y EditTravelActivity.
 * La primera de ellas debe mostrar la información de un viaje, especificando claramente
 * los datos especificados (ciudad, país y año). El disño queda en tus
 * manos, pero es requisito imprescindible que la interfaz escale correctamente en
 * dispositivos con distintos tamaño de pantalla y se adapte a cambios de orientación.
 * Para mostrar el funcionamiento de la Activity puedes poner datos fijos en
 * el método onCreate, por ejemplo: Kyoto (Japón), año 2012.
 * La segunda será un formulario para introducir la información de un viaje. Debe
 * tener cuatro EditText para ciudad, país, año y anotación; además de un botón
 * para guardar. No es necesario almacenar la información de ninguna forma. Al
 * pulsar el botón debe mostrarse una notificación Toast que muestre un texto
 * como el siguiente: "Nueva visita: <ciudad> (<país>), año: <año>".
 * Como todavía no hemos estudiado cómo lanzar activities en el código, te recomendamos
 * que para poder probar el ejercicio (y para su posterior corrección),
 * declares ambas activities con action MAIN y category LAUNCHER para poder
 * lanzarlas desde el menú de aplicaciones.
 * 
 */



package es.uax.android.travel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class TravelActivity extends Activity {

	TextView viaje_ciudad;
	TextView viaje_pais;
	TextView viaje_anio;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        
        /* Los datos del Viaje se obtienen del intent de TravelListActivity (elemento en la lista seleccionado) */
        
        Bundle bundle = getIntent().getExtras();
        
        viaje_ciudad = (TextView) findViewById(R.id.viaje_ciudad);
        viaje_ciudad.setText(bundle.getString("CITY"));
        
        viaje_pais = (TextView) findViewById(R.id.viaje_pais);
        viaje_pais.setText(bundle.getString("COUNTRY"));
        
        viaje_anio = (TextView) findViewById(R.id.viaje_anio);
        viaje_anio.setText(String.valueOf(bundle.getInt("YEAR")));
        
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.travel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       // int id = item.getItemId();
      
        switch (item.getItemId()) {
    	case R.id.compartir:
    		Intent intent = new Intent();
    		intent.setAction(Intent.ACTION_SEND);
    		intent.putExtra(Intent.EXTRA_TEXT, "He estado en " + viaje_ciudad.getText() + "(" + viaje_pais.getText() + ") en el año " + viaje_anio.getText());
    		intent.setType("text/plain");
    		startActivity(intent);
    	}
        
        return super.onOptionsItemSelected(item);
    }
}
