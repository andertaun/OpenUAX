/**
 * Ander Ortega Herrero.
 * 		Ejercicio: Unidad 4. Ejercicio Feedback 1.
 * 		Asignatura: UAX 002 - Desarrollo para el sistema operativo Android.
 *  	Curso: MASTER UNIVERSITARIO EN INGENIERIA DE DESARROLLO PARA DISPOSITIVOS MOVILES.
 * 		OpenUAX. CURSO 2014-2015.
 * 
 */


package es.uax.android.travel;


import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditTravelActivity extends Activity {
	
	public int position;
	public boolean nuevo;
	EditText editCity;	
	EditText editCountry;
	EditText editYear;	
	EditText editAnnotation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_edit_travel);
		
		final Bundle bundle = getIntent().getExtras();
		nuevo = true;
		if (bundle != null) {
			Log.d("", "Bundle es distinto de null por lo que tendrá valores del intent (o de ");
			position = bundle.getInt("POSITION");

			editCity = (EditText)findViewById(R.id.editCiudad);	
			editCity.setText(bundle.getString("CITY","desconocida"));
			
			editCountry = (EditText)findViewById(R.id.editPais);
			editCountry.setText(bundle.getString("COUNTRY","desconocido"));
			
			editYear = (EditText)findViewById(R.id.editAnio);	
			editYear.setText(String.valueOf(bundle.getInt("YEAR",2000)));
		
			editAnnotation = (EditText)findViewById(R.id.editAnotacion);
			editAnnotation.setText(bundle.getString("NOTE",""));
			
			nuevo = false;

		}
		
 		
		final Button boton = (Button) findViewById(R.id.botonGuardar);
		boton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				editCity = (EditText)findViewById(R.id.editCiudad);	
				editCountry = (EditText)findViewById(R.id.editPais);
				editYear = (EditText)findViewById(R.id.editAnio);	
				editAnnotation = (EditText)findViewById(R.id.editAnotacion);
				

				/*
				 * Control de entrada de valores:
				 * No podemos dejar sin rellenar los valores de Ciudad y Año. 
				 * 
				 * Control de entrada del año. Numérico de cuatro cifras mayor 1900 <= XXXX <= Año actual
				 * 
				 * 
				 */
				
				//calculamos el año actual
				Date horaActual = new Date();
				int anioActual= horaActual.getYear() + 1900;
				
				Boolean anioCorrecto = true;
				int anioIntroducido = 0;
				try {
					anioIntroducido = Integer.parseInt(editYear.getText().toString());
				} catch(NumberFormatException nfe) {
					Toast.makeText(EditTravelActivity.this, getResources().getString(R.string.anio_incorrecto), Toast.LENGTH_SHORT).show();
					anioCorrecto = false;
				}
				
				if (anioCorrecto) {
					anioCorrecto = (1900 <= anioIntroducido)  && (anioIntroducido <= anioActual);
				}
				
				
				String city = editCity.getText().toString();
				String country = editCountry.getText().toString();
				//String year = editYear.getText().toString();
				String note = editAnnotation.getText().toString();
				
				
				if (city.matches("")) {
					Toast.makeText(EditTravelActivity.this, getResources().getString(R.string.ciudad_vacia), Toast.LENGTH_SHORT).show();
				}else if (anioCorrecto ) {
						
						
					
					//	String texto = getResources().getString(R.string.nueva_visita) + city + " (" + country + "), " + getResources().getString(R.string.anio) + anioIntroducido + ".";
					//	System.out.println(texto);
					//	Toast.makeText(EditTravelActivity.this, texto, Toast.LENGTH_SHORT).show();
					
						//Devolvemos los resultados la activity que la llamó.
						Intent intent = new Intent();
						intent.putExtra("CIUDAD", city);
						intent.putExtra("PAIS", country);
						intent.putExtra("ANIO", anioIntroducido);
						intent.putExtra("NOTA", note);
						
						if (!nuevo) { // La variable "nuevo" nos dice si el viaje es creado nuevo (true)  o editado (false)
							intent.putExtra("POSICION", position);
						}
						
						setResult(RESULT_OK, intent);
						
						finish();

				
				
				}else {
					Toast.makeText(EditTravelActivity.this, R.string.anio_incorrecto, Toast.LENGTH_SHORT).show();
				}
				
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
		
		outState.putString("CITY", city);
		outState.putString("COUNTRY", country);
		outState.putString("YEAR", year);
		outState.putString("NOTE", annotation);
		
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
