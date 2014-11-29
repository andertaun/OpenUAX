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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_edit_travel);
		
		 
		
		final Button boton = (Button) findViewById(R.id.botonGuardar);
		boton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final EditText editCity = (EditText)findViewById(R.id.editCiudad);
				String city = editCity.getText().toString();
				
				final EditText editCountry = (EditText)findViewById(R.id.editPais);
				String country = editCountry.getText().toString();
				
				final EditText editYear = (EditText)findViewById(R.id.editAnio);
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
	
	

		
}
