package ca.gbc.comp3074.personalrestaurantguide;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RestaurantDetailsActivity extends AppCompatActivity {
    private ImageButton fbBtn, twBtn, mailBtn;
    private Button locBtn, directionBtn;
    private TextView nameTxt, addressTxt, phoneTxt, descriptionTxt, tagsTxt;
    private TextView nameLbl, addressLbl, phoneLbl, descriptionLbl, tagsLbl, ratingLbl;
    private RatingBar ratingBarStatic;

    public LocationManager locationManager;
    public LocationListener locationListener = new MyLocationListener();
    public String latitude;
    public String longitude;
    public boolean gps_enabled = false;
    public boolean network_enabled = false;
    Geocoder geocoder;
    List<Address>myAddress;
    private TextView addressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_restaurant);


        Intent i = getIntent();
        String name = i.getExtras().getString("COLUMN_NAME");
        String address = i.getExtras().getString("COLUMN_ADDRESS");
        String phone = i.getExtras().getString("COLUMN_PHONE");
        String description = i.getExtras().getString("COLUMN_DESCRIPTION");
        String ratingBar = i.getExtras().getString("COLUMN_RATING");
        String tags = i.getExtras().getString("COLUMN_TAGS");

        fbBtn = (ImageButton) findViewById(R.id.fbBtn);
        twBtn = (ImageButton) findViewById(R.id.twitterBtn);
        mailBtn = (ImageButton) findViewById(R.id.mailBtn);

        locBtn = (Button) findViewById(R.id.locBtn);
        directionBtn = (Button) findViewById(R.id.directionBtn);

        nameLbl = (TextView) findViewById(R.id.nameLabel);
        addressLbl = (TextView) findViewById(R.id.addressLabel);
        phoneLbl = (TextView) findViewById(R.id.phoneLabel);
        descriptionLbl = (TextView) findViewById(R.id.descriptionLabel);
        tagsLbl = (TextView) findViewById(R.id.tagsLabel);
        ratingLbl = (TextView) findViewById(R.id.ratingLabel);

        nameTxt = (TextView) findViewById(R.id.nameTxt);
        addressTxt = (TextView) findViewById(R.id.addressTxt);
        phoneTxt = (TextView) findViewById(R.id.phoneTxt);
        descriptionTxt = (TextView) findViewById(R.id.descriptionTxt);
        tagsTxt = (TextView) findViewById(R.id.tagsTxt);
        ratingBarStatic = (RatingBar) findViewById(R.id.ratingBarStatic);

        //converting rating from string to int
        float numStars = Float.parseFloat(ratingBar);

        nameTxt.setText(name);
        addressTxt.setText(address);
        phoneTxt.setText(phone);
        descriptionTxt.setText(description);
        //converting rating from int to stars
        ratingBarStatic.setRating(numStars);
        tagsTxt.setText(tags);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        addressView=(TextView)findViewById(R.id.addressView);


        //back arrow button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        locBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String address = addressTxt.getText().toString();
                String encodedAddress = Uri.encode(address);

                Uri restaurantLocation = Uri.parse("geo:0,0?q=" + encodedAddress);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, restaurantLocation);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);

                }
            }
        });


        directionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMyLocation();
                String address = addressTxt.getText().toString();
                String encodedAddress = Uri.encode(address);
                String userLocation = addressView.getText().toString();
                System.out.println(userLocation);
                String encodedLocation = Uri.encode(userLocation);
                Uri directions = Uri.parse("google.navigation:q=" + encodedAddress + "," + encodedLocation);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, directions);
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }

            }
        });


        mailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=nameTxt.getText().toString();
                String address=addressTxt.getText().toString();
                String phoneNumber=phoneTxt.getText().toString();
                Intent i=getIntent();
                String ratingBar = i.getExtras().getString("COLUMN_RATING");
                String rating=ratingBar+"/5";
                String description=descriptionTxt.getText().toString();
                String tags=tagsTxt.getText().toString();

                String emailBody="Hi! I'd like to share some details about a restaurant I've recently been to!"+"\n"+"\n"+
                        "Restaurant Name: "+name+"\n"+"Address: "+address+"\n"+"Phone Number: "+phoneNumber+"\n"+
                        "My Personal Rating: "+rating+"\n"+"My description of the restaurant: "+description+"\n"+
                        "Tags: "+tags+"\n"+"\n"+"Thanks for reading!";

                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL,"beatriz.morales2528@gmail.com");
                intent.putExtra(Intent.EXTRA_SUBJECT,"Restaurant Details");
                intent.putExtra(Intent.EXTRA_TEXT,emailBody);
                startActivity(Intent.createChooser(intent,"Send Email"));

            }
        });


    }


    class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            if (location != null) {
                locationManager.removeUpdates(locationListener);
                latitude = "" + location.getLatitude();
                longitude = "" + location.getLongitude();

                geocoder=new Geocoder(RestaurantDetailsActivity.this,Locale.getDefault());


                try {
                     myAddress = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String address=myAddress.get(0).getAddressLine(0);
                addressView.setText(address);


            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    }

    public void getMyLocation() {
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        } catch (Exception ex) {

        }

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        } catch (Exception ex) {

        }

        if (!gps_enabled && !network_enabled) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RestaurantDetailsActivity.this);
            builder.setTitle("Attention");
            builder.setMessage("Location is not available, please enable location services");
            builder.create().show();
        }

        if (gps_enabled) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }

        if(network_enabled){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addRestaurant:
                Intent intent = new Intent(this, AddRestaurantActivity.class);
                this.startActivity(intent);
                break;
        }
        switch (item.getItemId()) {
            case R.id.about:
                Intent intent = new Intent(this, AboutActivity.class);
                this.startActivity(intent);
                break;
        }
        switch (item.getItemId()) {
            case R.id.restaurants:
                Intent intent = new Intent(this, RestaurantsActivity.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
