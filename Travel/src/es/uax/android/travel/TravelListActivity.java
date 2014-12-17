/**
 * Ander Ortega Herrero.
 * 		Ejercicio: Unidad 4. Ejercicio Feedback 1.
 * 		Asignatura: UAX 002 - Desarrollo para el sistema operativo Android.
 *  	Curso: MASTER UNIVERSITARIO EN INGENIERIA DE DESARROLLO PARA DISPOSITIVOS MOVILES.
 * 		OpenUAX. CURSO 2014-2015.
 * 
 */

package es.uax.android.travel;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Este ejemplo muestra el uso de una clase ListActivity que muestra una lista de paises visitados.
 * 
 * Para ello hacemos uso de una extension del ArrayAdapter que contiene una lista de objetos TravelInfo.
 * El metodo getView del adapter se encarga de mostrar la informacion de cada entrada TravelInfo de la
 * forma correcta en la vista.
 * 
 */
public class TravelListActivity extends ListActivity {
	
	private TravelAdapter adapter;
	static final int NUEVO_VIAJE = 1;
	static final int EDITAR_VIAJE = 2;
		
	
	private class TravelAdapter extends ArrayAdapter<TravelInfo>{
		
		private Context context;
		private ArrayList<TravelInfo> travels;
		private static final int RESOURCE = android.R.layout.simple_list_item_2;

		public TravelAdapter(Context context, ArrayList<TravelInfo> travels) {
			super(context, RESOURCE, travels);
			
			this.context = context;
			this.travels = travels;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			LinearLayout view;
			ViewHolder holder;
			
			if (convertView == null){
				view = new LinearLayout(context);
				
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				inflater.inflate(RESOURCE, view, true);
				
				holder = new ViewHolder();
				holder.text1 = (TextView) view.findViewById(android.R.id.text1);
				holder.text2 = (TextView) view.findViewById(android.R.id.text2);
				view.setTag(holder);
				
			} else {
				view = (LinearLayout) convertView;
				holder = (ViewHolder) view.getTag();
			}
			
			//Rellenamos la vista con los datos
			TravelInfo info = travels.get(position);
			holder.text1.setText(info.getCity() + " (" + info.getCountry() + ")");
			holder.text2.setText(getResources().getString(R.string.anio) + " " + info.getYear());
			
	//		registerForContextMenu(view);
			
			return view;
		}
		
	}
	
	static class ViewHolder {
		TextView text1;
		TextView text2;
	}

	
	protected void onListItemClick (ListView l, View v, int position, long id) {
		
		TravelInfo info = adapter.getItem(position);
		
		Intent intent = new Intent(this, TravelActivity.class);
		intent.putExtra( "CITY", info.getCity());
		intent.putExtra("COUNTRY", info.getCountry());
		intent.putExtra("YEAR", info.getYear());

		startActivity(intent);
		
		super.onListItemClick(l, v, position, id);
		
	}
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Generamos los datos
        ArrayList<TravelInfo> values = getData();
        
        //Creamos el adapter y lo asociamos a la activity
        adapter = new TravelAdapter(this, values);
        
        setListAdapter(adapter);
        registerForContextMenu(getListView());
        
        
    }
    
    
    //Menú contextual (para cada viaje, con pulsación prolongada)
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo info) {
    	super.onCreateContextMenu(menu, v, info);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_context, menu);
    }
    
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	super.onContextItemSelected(item);
    	AdapterView.AdapterContextMenuInfo info  = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
    	
    	switch (item.getItemId()) {
    	case R.id.edit_travel:
    		//Editamos el viaje pasando los datos como extras en el intent a la activity EditTravelActivity y esperamos los datos de vuelta
    		//(startActivityforResult)
    	
    	
    	    TravelInfo tv = adapter.getItem(info.position);   
    	    	
			Intent intent = new Intent(this, EditTravelActivity.class);
    		intent.putExtra( "CITY", tv.getCity());
    		intent.putExtra("COUNTRY", tv.getCountry());
    		intent.putExtra("YEAR", tv.getYear());
    		intent.putExtra("NOTE", tv.getNote());
    		intent.putExtra("POSITION", info.position);

    		startActivityForResult(intent, EDITAR_VIAJE);   	
    	
  		  		 		
    		Toast.makeText(TravelListActivity.this, "Editamos el elemento", Toast.LENGTH_SHORT).show();
    	

    		return true;
    	case R.id.delete_travel:
    		//borramos el elemento de la lista.
    		adapter.remove(adapter.getItem(  info.position  ));    // falta asignar la posición del elemento a borrar
    		
    		
    		Toast.makeText(TravelListActivity.this, "Borrado el elemento  " + info.position, Toast.LENGTH_SHORT).show();
   		
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);    	
    	} 	
    }
    
    
    
    //Menú de la activity
    public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.list_travel, menu);
    	return true;
    	
    }
    
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.menu_new_travel:
    		Intent intent = new Intent(this, EditTravelActivity.class);
    		startActivityForResult(intent, NUEVO_VIAJE);	
    	}   	
    	return super.onMenuItemSelected(featureId, item);
    }
    
   /**
    * Recuperamos la información del viaje creado en la otra activity para añadirlo al Array.
    * 
    * */

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	String city;
    	String country;
    	int year;
    	String note;
    	int position;
    	
    	TravelInfo travel;
  
//     	Fragmento de código optimizado    	
    	if (resultCode == RESULT_OK) {
    		city = data.getStringExtra("CIUDAD");
			country = data.getStringExtra("PAIS");
			year = data.getIntExtra("ANIO",0);
			note = data.getStringExtra("NOTA");
  
			
			travel = new TravelInfo(city,country,year,note);
			adapter.add(travel);
			
			switch (requestCode) {
			case NUEVO_VIAJE:
				System.out.println("Nuevo viaje creado correctamente");
				break;
			case EDITAR_VIAJE:
				position = data.getIntExtra("POSICION", adapter.getCount());
       			adapter.remove(adapter.getItem(position));
    			
    			System.out.println("Viaje editado correctamente");
				break;
			}	
    	}
    	
 
 /*   	Fragmento de código sustituido
  * 
    	if (requestCode == NUEVO_VIAJE) {
 
    		if(resultCode == RESULT_OK) {
    			city = data.getStringExtra("CIUDAD");
    			country = data.getStringExtra("PAIS");
    			year = data.getIntExtra("ANIO",0);
    			note = data.getStringExtra("NOTA");
   	
    			System.out.println("Ciudad:" + city + ": Pais:" + country + ": anio:" + year + ": nota:" + note);
    			
    			travel = new TravelInfo(city,country,year,note);
    			
    			adapter.add(travel);
    			
    			System.out.println("Nuevo viaje creado correctamente");
    		}	
    	} 
    	else if (requestCode == EDITAR_VIAJE) {
    		if(resultCode == RESULT_OK) {
    			city = data.getStringExtra("CIUDAD");
    			country = data.getStringExtra("PAIS");
    			year = data.getIntExtra("ANIO",0);
    			note = data.getStringExtra("NOTA");
    			position = data.getIntExtra("POSICION", adapter.getCount());
    			
    			travel = new TravelInfo(city,country,year,note);
    			
    			Log.d("","Borramos el elemento de la posición " + position); 
    	
    			adapter.add(travel);
    			
       			adapter.remove(adapter.getItem(position));
    			
    			System.out.println("Viaje editado correctamente");
    		}
    		
    	}
*/  
   } 

    
    
    //Generamos datos a mostrar
    //En una aplicacion funcional se tomarian de base de datos o algun otro medio
    private ArrayList<TravelInfo> getData(){
    	ArrayList<TravelInfo> travels = new ArrayList<TravelInfo>();

        TravelInfo info = new TravelInfo("Londres", "UK", 2012, "�Juegos Olimpicos!");
        TravelInfo info2 = new TravelInfo("Paris", "Francia", 2007);
        TravelInfo info3 = new TravelInfo("Gotham City", "EEUU", 2011, "��Batman!!");
        TravelInfo info4 = new TravelInfo("Hamburgo", "Alemania", 2009);
        TravelInfo info5 = new TravelInfo("Pekin", "China", 2011);

        travels.add(info);
        travels.add(info2);
        travels.add(info3);
        travels.add(info4);
        travels.add(info5);
       
        return travels;
    }

}
