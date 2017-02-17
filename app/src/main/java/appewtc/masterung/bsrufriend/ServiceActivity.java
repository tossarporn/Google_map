package appewtc.masterung.bsrufriend;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

public class ServiceActivity extends FragmentActivity implements OnMapReadyCallback {

    //Explicit
    private GoogleMap mMap;
    private double userLatADouble = 13.733030, userLngADouble = 100.489416;
    private TextView textView;
    private Button button;
    private String[] loginStrings;
    private LocationManager locationManager;
    private Criteria criteria;
    private boolean aBoolean = true; // For Stop Loop


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout);

        //Bind Widget
        textView = (TextView) findViewById(R.id.textView2);
        button = (Button) findViewById(R.id.button4);

        //Receive Value for MainActivity
        loginStrings = getIntent().getStringArrayExtra("Login");

        //Show Text
        textView.setText(loginStrings[1]);

        //Setup Location
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //My Loop

        myLoop();

        //Button Controller
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aBoolean = false;
                Intent intent = new Intent(ServiceActivity.this, ListFriend.class);
                intent.putExtra("Login", loginStrings);
                startActivity(intent);
                finish();

            }
        });


    }   //  Main Method

    private void myLoop(){
        //Doing
        afterResume();

        updateLatLng();

        createMarker();


        //DeLay
        if (aBoolean) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    myLoop();
                }
            },1000);

        }

    }

    private void createMarker() {

        try {

           mMap.clear();//del marker
           String urlPHP = "http://swiftcodingthai.com/bsru/get_user_master.php";
            int[] avataInts = new int[]{R.drawable.bird48,R.drawable.doremon48,
                    R.drawable.kon48,R.drawable.nobita48,R.drawable.rat48};
            GetUser getUser = new GetUser(ServiceActivity.this);
            getUser.execute(urlPHP);
            String strJSON = getUser.get();
            JSONArray jsonArray = new JSONArray(strJSON);
            for (int i=0;i<jsonArray.length();i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                LatLng latLng = new LatLng(Double.parseDouble(jsonObject.getString("Lat")),
                        Double.parseDouble(jsonObject.getString("Lng")));
                mMap.addMarker(new MarkerOptions().position(latLng)
                        .icon(BitmapDescriptorFactory.fromResource(avataInts[Integer.parseInt(jsonObject.getString("Avata"))]))
                .title(jsonObject.getString("Name")));


            }//for
            getUser.cancel(true);

        } catch (Exception e) {

            Log.d("17febV3", "e==>createMarker" + e.toString());
        }


    }//create marker

    private void updateLatLng() {

        try {

            EditLatLng editLatLng = new EditLatLng(ServiceActivity.this, loginStrings[0]);
            editLatLng.execute(Double.toString(userLatADouble),Double.toString(userLngADouble));
            boolean b = Boolean.parseBoolean(editLatLng.get());
            Log.d("17febV2", "Result==>" + b);

            editLatLng.cancel(true);

        } catch (Exception e) {
            Log.d("17fabV2","e update ==>"+e.toString());
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        afterResume();

    }

    private void afterResume() {

        Location networkLocation = myFindLocation(LocationManager.NETWORK_PROVIDER);
        if (networkLocation != null) {
            userLatADouble = networkLocation.getLatitude();
            userLngADouble = networkLocation.getLongitude();
        }

        Location gpsLocation = myFindLocation(LocationManager.GPS_PROVIDER);
        if (gpsLocation != null) {
            userLatADouble = gpsLocation.getLatitude();
            userLngADouble = gpsLocation.getLongitude();
        }

        Log.d("17febV1", "lat ==> " + userLatADouble);
        Log.d("17febV1", "Lng ==> " + userLngADouble);


    }   // afterResume

    @Override
    protected void onStop() {
        super.onStop();

        aBoolean = false;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(locationListener);

    }

    public Location myFindLocation(String strProvider) {

        Location location = null;

        if (locationManager.isProviderEnabled(strProvider)) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            locationManager.requestLocationUpdates(strProvider, 1000, 10, locationListener);
            location = locationManager.getLastKnownLocation(strProvider);



        }   // if

        return location;
    }



    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            userLatADouble = location.getLatitude();
            userLngADouble = location.getLongitude();

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Setup Center of Map
        mMap.animateCamera(CameraUpdateFactory
                .newLatLngZoom(new LatLng(userLatADouble, userLngADouble), 16));


    }   // onMapReady

}   // Main Class