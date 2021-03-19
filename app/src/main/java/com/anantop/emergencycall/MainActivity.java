package com.anantop.emergencycall;

import android.Manifest.permission;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements LocationListener {
    Button button3 = null;
    private LocationManager locationManager;
    String ph_no;
    ////private Handler GeocoderHandler;

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
                return true;
            }
        });
        button3 = (Button) findViewById(R.id.buttonPolice);
        button3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickAction("numberPolice");
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

    public void mainPage(View view) {

        setContentView(R.layout.activity_main);
        button3 = (Button) findViewById(R.id.back);
        button3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
                String number = sharedPreferences.getString("numberMom", "");
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
                String number2 = sharedPreferences.getString("numberDad", "");
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
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i("info", location.getLatitude() + "");
        //new MyTask().execute();

        Thread thread = new Thread(){
            public void run(){
                Looper.prepare();//Call looper.prepare()

                Handler mHandler = new Handler() {
                    public void handleMessage(Message msg) {
                        Toast.makeText(getApplicationContext(), "Finco is Daddy", Toast.LENGTH_LONG);
                        Log.i("info", "hi");
                    }
                };

                Looper.loop();
            }
        };
        thread.start();
/*
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                getAddress_andSendSms(location);
            }
        });
        */

        /*
        runOnUiThread(new Runnable() {
            public void run() {
                getAddress_andSendSms(location);
            }
        });
*/
        /*
        new Thread(new Runnable(){
            @Override
            public void run() {
                getAddress_andSendSms(location);
            }
        }).start();
*/
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MainActivity.this, "Please Give Permissions", Toast.LENGTH_SHORT).show();
    }

    private void longClickAction(String person) {
        SharedPreferences sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
        ph_no = sharedPreferences.getString(person, "");
        Log.i("info", "Long");
        getLocation();
        Toast.makeText(getApplicationContext(), "Message Will Be Sent Shortly",
                Toast.LENGTH_LONG).show();
    }

    public static void getAddressFromLocation(final double latitude, final double longitude, final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)); //.append("\n");
                        }
                        sb.append(address.getLocality()).append("\n");
                        sb.append(address.getPostalCode()).append("\n");
                        sb.append(address.getCountryName());
                        result = sb.toString();
                    }
                } catch (IOException e) {
                    Log.e("Location Address Loader", "Unable connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", result);
                        message.setData(bundle);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = " Unable to get address for this location.";
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }

    public void getAddress_andSendSms()
    {
        /*Location location = null;
        location.setLatitude(27);
        location.setLongitude(-122);
        */
        try
        {
            //URL url = new URL("https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=" + location.getLatitude() + "&longitude=" + location.getLongitude() + "&localityLanguage=en");
            URL url = new URL("https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=" + "23" + "&longitude=" + "-123" + "&localityLanguage=en");
            URLConnection urlcon=url.openConnection();
            System.out.println("------------------------------------");

            //get the inputstream of the open connection.
            BufferedReader br = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
            System.out.println(br.toString());
            String i;
            StringBuilder sb = new StringBuilder();
            while ((i = br.readLine()) != null)
            {
                System.out.println(i);
                sb.append(i);
            }
            System.out.println(sb.toString());
            JSONObject json = new JSONObject(sb.toString());
            String city = json.getString("city");
            System.out.println(city);

            //below code is for sending sms
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
            SmsManager sms = SmsManager.getDefault();
            //sms.sendTextMessage(ph_no, null, "Need Help. Current Location: " + location.getLatitude() + ", " + location.getLongitude() + " City Is:" + city, pi, null);
            sms.sendTextMessage(ph_no, null, "Need Help. Current Location: "  + ", "  + " City Is:" + city, pi, null);
            Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                    Toast.LENGTH_LONG).show();

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            Log.e("location Address=", locationAddress);
        }
    }

    private class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            //String url = params[0];
            getAddress_andSendSms();
            return "abc";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // do something with result
        }
    }

}