package nz.co.carlmcmillan;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {
	
	private LocationClient locationClient;
	private LocationRequest locationRequest;
	private GoogleMap map;
	
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final int MILLISECONDS_PER_SECOND = 1000;
    public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
    private static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
    private static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		locationRequest = LocationRequest.create();
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		locationRequest.setInterval(UPDATE_INTERVAL);
		locationRequest.setFastestInterval(FASTEST_INTERVAL);
		
		locationClient = new LocationClient(this, this, this);
		
        map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        LatLng datacomLatLng = new LatLng(-41.287556, 174.778016);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(datacomLatLng, 16));
        
        map.addMarker(new MarkerOptions()
                .title("Datacom")
                .position(datacomLatLng));
        
        map.addMarker(new MarkerOptions()
        		.title("Balti")
        		.snippet("The greatest curry")
        		.position(new LatLng(-41.288738, 174.775955)));
        
        map.addMarker(new MarkerOptions()
				.title("The Trough")
				.snippet("Convienient, pretty shit")
				.position(new LatLng(-41.286998, 174.776683)));
        
        map.addMarker(new MarkerOptions()
        		.title("Maccas")
        		.snippet("Same as the others")
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
	
	@Override
	public void onLocationChanged(Location location) {
		String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	@Override
    protected void onStart() {
        super.onStart();
        locationClient.connect();
    }

	
	@Override
    protected void onStop() {
		if (locationClient.isConnected()) {
			locationClient.removeLocationUpdates(this);
		}
        locationClient.disconnect();
        super.onStop();
    }
	 
	@Override
	public void onConnected(Bundle bundle) {
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
		locationClient.requestLocationUpdates(locationRequest, this);
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            showErrorDialog(connectionResult.getErrorCode());
        }
	}
	
	private void showErrorDialog(int errorCode) {
        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(errorCode, this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

        if (errorDialog != null) {
            ErrorDialogFragment errorFragment = new ErrorDialogFragment();
            errorFragment.setDialog(errorDialog);
            errorFragment.show(getSupportFragmentManager(), "wfl");
        }
    }
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        switch (requestCode) {
            case CONNECTION_FAILURE_RESOLUTION_REQUEST :
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.d("wfl", "Error resolved. Please re-try operation.");
                        Toast.makeText(this, "Client connected", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "Error resolved. Please re-try operation.", Toast.LENGTH_SHORT).show();
                    break;
                    default:
                        Log.d("wfl", "Google Play services: unable to resolve connection error.");
                        Toast.makeText(this, "Client disconnected", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "Google Play services: unable to resolve connection error.", Toast.LENGTH_SHORT).show();
                    break;
                }
            default:
               Log.d("wfl", "Received an unknown activity request code in onActivityResult. : " + requestCode);

               break;
        }
    }
	
	private boolean servicesConnected() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (ConnectionResult.SUCCESS == resultCode) {
            Log.d("wfl", "Google Play services is available.");
            return true;
        } else {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
            if (dialog != null) {
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(dialog);
                errorFragment.show(getSupportFragmentManager(), "wfl");
            }
            return false;
        }
    }

	public static class ErrorDialogFragment extends DialogFragment {
		
        private Dialog dialog;
        
        public ErrorDialogFragment() {
            super();
            dialog = null;
        }
        
        public void setDialog(Dialog dialog) {
            this.dialog = dialog;
        }
        
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return dialog;
        }
        
    }

}
