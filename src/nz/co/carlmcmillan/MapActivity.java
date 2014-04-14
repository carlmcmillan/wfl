package nz.co.carlmcmillan;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class MapActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

        GoogleMap map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        LatLng datacomLocation = new LatLng(-41.287559, 174.778007);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(datacomLocation, 16));
        
        map.addMarker(new MarkerOptions()
                .title("Datacom")
                .snippet("You work here")
                .position(datacomLocation));
        
        map.addMarker(new MarkerOptions()
        		.title("Balti")
        		.snippet("The greatest curry")
        		.position(new LatLng(-41.288738, 174.775955)));
        
        map.addMarker(new MarkerOptions()
				.title("The Trough")
				.snippet("Convienient, variety of food, and authentically shit")
				.position(new LatLng(-41.286998, 174.776683)));
        
        map.addMarker(new MarkerOptions()
        		.title("Maccas")
        		.snippet("At least you know what your getting")
        		.position(new LatLng(-41.291046, 174.779853)));
        		
        map.addMarker(new MarkerOptions()
        		.title("BP")
        		.snippet("Has pretty good pies")
        		.position(new LatLng(-41.290966, 174.779902)));
        
        map.addMarker(new MarkerOptions()
				.title("Bruhaus")
				.snippet("Bruhaus burgers are good")
				.position(new LatLng(-41.286640, 174.776995)));

        map.addMarker(new MarkerOptions()
				.title("Fix")
				.snippet("Chilli Beef and Cheese pies")
				.position(new LatLng(-41.286843, 174.775813)));

        map.addMarker(new MarkerOptions()
				.title("3C")
				.snippet("I think their burger was alright")
				.position(new LatLng(-41.287733, 174.776366)));

	}

}
