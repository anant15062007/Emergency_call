package com.anantop.emergencycall;

import android.Manifest.permission;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity implements LocationListener{
    Button button3 = null;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;

    private LatLng latLng;
    String ph_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("info", "Inside On Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button3 = (Button) findViewById(R.id.back);
        button3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickAction("numberMom");
                return true;
            }
        });

        button3 = (Button) findViewById(R.id.buttonDad);
        button3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickAction("numberDad");
                //SharedPreferences sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
                //ph_no = sharedPreferences.getString("numberDad", "");
                //Log.i("info", "Long");
                //getLocation();
                return true;
            }
        });
        button3 = (Button) findViewById(R.id.buttonPolice);
        button3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickAction("numberPolice");
                //SharedPreferences sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
                //String ph_no = sharedPreferences.getString("numberPolice", "");
                //Log.i("info", "Long");
                //getLocation();
                return true;
            }
        });
    }

    public void settingsPage(View view) {
        setContentView(R.layout.settings_activity);
        // set the text1 value from database
        SharedPreferences sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
        String phoneMom = sharedPreferences.getString("numberMom", "");
        EditText number1 = (EditText) findViewById(R.id.editTextTextPersonName2);
        number1.setText(phoneMom);

        // set the text2 value from database
        sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
        String phoneDad = sharedPreferences.getString("numberDad", "");
        number1 = (EditText) findViewById(R.id.editTextTextPersonName);
        number1.setText(phoneDad);

        // set the text3 value from database
        sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
        String phonePolice = sharedPreferences.getString("numberPolice", "");
        number1 = (EditText) findViewById(R.id.editTextTextPersonName3);
        number1.setText(phonePolice);
    }

    public void saveSettings(View view) {

        EditText number1 = (EditText) findViewById(R.id.editTextTextPersonName2);
        String number2 = number1.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("numberMom", number2);
        editor.commit();

        number1 = (EditText) findViewById(R.id.editTextTextPersonName);
        number2 = number1.getText().toString();
        sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("numberDad", number2);
        editor.commit();

        number1 = (EditText) findViewById(R.id.editTextTextPersonName3);
        number2 = number1.getText().toString();
        sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("numberPolice", number2);
        editor.commit();

    }

    public void mainPage(View view)
    {

        setContentView(R.layout.activity_main);
        button3 = (Button) findViewById(R.id.back);
        button3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
                String number = sharedPreferences.getString("numberMom", "111");
                Log.i("info", "Long");
                //below code is for sending sms
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(number, null, "Need Help!", pi, null);
                Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                        Toast.LENGTH_LONG).show();
                return true;
            }
        });
        button3 = (Button) findViewById(R.id.buttonDad);
        button3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
                String number2 = sharedPreferences.getString("numberDad", "222");
                Log.i("info", "Long");
                //below code is for sending sms
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(number2, null, "Need Help!", pi, null);
                Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                        Toast.LENGTH_LONG).show();
                return true;
            }
        });
        button3 = (Button) findViewById(R.id.buttonPolice);
        button3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
                String number3 = sharedPreferences.getString("numberPolice", "");
                Log.i("info", "Long");
                //below code is for sending sms
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(number3, null, "Need Help!", pi, null);
                Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                        Toast.LENGTH_LONG).show();
                return true;
            }
        });

    }

    public void mom(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
        String number = sharedPreferences.getString("numberMom", "");
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(this, permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Log.i("info", "No permissions hence return from here");
            return;
        }
        startActivity(intent);
    }

    public void dad(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
        String number2 = sharedPreferences.getString("numberDad", "");
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number2));
        if (ActivityCompat.checkSelfPermission(this, permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Log.i("info", "No permissions hence return from here");
            return;
        }
        startActivity(intent);
    }

    public void police(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
        String number3 = sharedPreferences.getString("numberPolice", "");
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number3));
        if (ActivityCompat.checkSelfPermission(this, permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Log.i("info", "No permissions hence return from here");
            return;
        }
        startActivity(intent);
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
            //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i("info", location.getLatitude()+"");

        //below code is for sending sms
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(ph_no, null, "Need Help. Current Location: " + location.getLatitude() + ", " + location.getLongitude(), pi, null);
        Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }


    private void longClickAction(String person){
        SharedPreferences sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
        ph_no = sharedPreferences.getString(person, "");
        Log.i("info", "Long");
        getLocation();
        Toast.makeText(getApplicationContext(), "Message Will Be Sent Shortly",
        Toast.LENGTH_LONG).show();
    }
}
