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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditTravelActivity extends Activity {
	
	EditText editCity;	
	EditText editCountry;
	EditText editYear;	
	EditText editAnnotation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_edit_travel);
				 
		
		final Button boton = (Button) findViewById(R.id.botonGuardar);
		boton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				editCity = (EditText)findViewById(R.id.editCiudad);	
				editCountry = (EditText)findViewById(R.id.editPais);
				editYear = (EditText)findViewById(R.id.editAnio);	
				editAnnotation = (EditText)findViewById(R.id.editAnotacion);
				String city = editCity.getText().toString();
				String country = editCountry.getText().toString();
				String year = editYear.getText().toString();
				
				
				String texto = "Nueva visita: " + city + " (" + country + "), año: " + year + ".";
				Toast.makeText(EditTravelActivity.this, texto, Toast.LENGTH_SHORT).show();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_travel, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		
		editCity = (EditText)findViewById(R.id.editCiudad);	
		editCountry = (EditText)findViewById(R.id.editPais);
		editYear = (EditText)findViewById(R.id.editAnio);	
		editAnnotation = (EditText)findViewById(R.id.editAnotacion);
		
		String city = editCity.getText().toString();
		String country = editCountry.getText().toString();
		String year = editYear.getText().toString();
		String annotation = editAnnotation.getText().toString();
		
		outState.putString("ciudad", city);
		outState.putString("pais", country);
		outState.putString("anio", year);
		outState.putString("nota", annotation);
		
		super.onSaveInstanceState(outState);
		
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		
		
		
		if (savedInstanceState != null) {
			String city = savedInstanceState.getString("ciudad");
			String country = savedInstanceState.getString("pais");
			String year = savedInstanceState.getString("anio");
			String annotation = savedInstanceState.getString("nota");
			
			editCity.setText(city);
			editCountry.setText(country);
			editYear.setText(year);
			editAnnotation.setText(annotation);
		}
		
		super.onRestoreInstanceState(savedInstanceState);
	}

		
}
