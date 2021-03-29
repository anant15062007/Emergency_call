package com.anantop.emergencycall;

import android.Manifest;
import android.Manifest.permission;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private static final int MY_CALL_PHONE_PERMISSION_CODE = 50;
    private static final int CALL_PHONE_REQUEST = 51;
    private static final int MY_SMS_LOCATION_PERMISSION_CODE = 60;
    Button button3 = null;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    private LatLng latLng;
    String ph_no;
    Uri imageUriMom;
    Uri imageUriDad;
    Uri imageUriPolice;
    String imageButtonClicked=null;

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

        EditText number1 = (EditText) findViewById(R.id.editTextTextPersonName2);
        number1.setText(sharedPreferences.getString("numberMom", ""));
        EditText number2 = (EditText) findViewById(R.id.editTextTextPersonName);
        number2.setText(sharedPreferences.getString("numberDad", ""));
        EditText number3 = (EditText) findViewById(R.id.editTextTextPersonName3);
        number3.setText(sharedPreferences.getString("numberPolice", ""));

        try {
            imageUriMom = Uri.parse(sharedPreferences.getString("imageMom", ""));
            imageUriDad = Uri.parse(sharedPreferences.getString("imageDad", ""));
            imageUriPolice = Uri.parse(sharedPreferences.getString("imagePolice", ""));
        }
        catch (Exception exception){
            Log.i("infoooo",exception.toString());
        }

        Bitmap bitmap;

        try {
            ImageView imageview = findViewById(R.id.imageView4);
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUriMom);
            imageview.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ImageView imageview1 = findViewById(R.id.imageView3);
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUriDad);
            imageview1.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ImageView imageview2 = findViewById(R.id.imageView5);
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUriPolice);
            imageview2.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSettings(View view) {

        EditText number1 = (EditText) findViewById(R.id.editTextTextPersonName2);
        SharedPreferences sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("numberMom", number1.getText().toString());
        editor.putString("imageMom",imageUriMom.toString());
        editor.commit();

        EditText number2 = (EditText) findViewById(R.id.editTextTextPersonName);
        sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("numberDad", number2.getText().toString());
        editor.putString("imageDad",imageUriDad.toString());
        editor.commit();

        EditText number3 = (EditText) findViewById(R.id.editTextTextPersonName3);
        sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("numberPolice", number3.getText().toString());
        editor.putString("imagePolice",imageUriPolice.toString());
        editor.commit();
    }

    public void mainPage(View view) {
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.back);
        button1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickAction("numberMom");
                return true;
            }
        });

        Button button2 = (Button) findViewById(R.id.buttonDad);
        button2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickAction("numberDad");
                return true;
            }
        });

        Button button3 = (Button) findViewById(R.id.buttonPolice);
        button3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickAction("numberPolice");
                return true;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void callPerson(View view) {
        Log.i("info", getId(view));
        String buttonClicked = null;

        if(getId(view).equals("com.anantop.emergencycall:id/back")){
            Log.i("click", "yyyyyyy"+getId(view));
            buttonClicked="numberMom";
        } else if(getId(view).equals("com.anantop.emergencycall:id/buttonDad")){
            buttonClicked="numberDad";
        }
        else if (getId(view).equals("com.anantop.emergencycall:id/buttonPolice")) {
            buttonClicked="numberPolice";
        }
        else{
            Log.i("click", "unknown button clicked");
        }

        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            Log.i("info", "No permissions hence return from here");
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, MY_CALL_PHONE_PERMISSION_CODE);
        }
        else
        {
            SharedPreferences sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
            String number = sharedPreferences.getString(buttonClicked, "");
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + number));
            startActivity(intent);
        }
    }
/*
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
*/

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void longClickAction(String person) {
        SharedPreferences sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
        ph_no = sharedPreferences.getString(person, "");
        Log.i("info", "inside longClickAction");
        Log.i("info", "phone number"+ph_no);


        if (checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Log.i("info", "No permissions for SMS or Location hence requesting access");
            requestPermissions(new String[]{Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION}, MY_SMS_LOCATION_PERMISSION_CODE);
        }
        else
        {
            getLocationUpdates();
        }

        Toast.makeText(getApplicationContext(), "Message Will Be Sent Shortly", Toast.LENGTH_LONG).show();
    }

    void getLocationUpdates() {
        Log.i("info", "inside getLocation");
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                Log.i("info", "locationManager is not null");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
            } else {
                Log.i("info", "locationManager is null");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i("info", "Inside onLocationChanged");
        Log.i("Latitude", location.getLatitude() + "");
        Log.i("longitude", location.getLongitude() + "");

        //below code is for sending sms
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        SmsManager sms = SmsManager.getDefault();
        String address = null;
        try {
            address = getLocationAddress(location);
            Log.i("info", address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sms.sendTextMessage(ph_no, null,
                "Need Help. Current Location: " +
                        location.getLatitude() + ", " + location.getLongitude() + "Address=" + address, pi, null);
        Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    public String getLocationAddress(Location location) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        Log.i("info","inside getLocation");

        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
        Log.i("address", address);
        return address;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        Log.i("info","inside on activity result");
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Bitmap bitmap=null;
        Bundle extras;
        Bitmap mImageBitmap;
        switch(requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    extras = imageReturnedIntent.getExtras();
                    //assign value in imageUriMom
                    mImageBitmap = (Bitmap) extras.get("data");
                    ImageView imageviewMom = findViewById(R.id.imageView4);
                    imageviewMom.setImageBitmap(mImageBitmap);
                    return;
                }
                break;
            case 1:
                if(resultCode == RESULT_OK){
                    imageUriMom = imageReturnedIntent.getData();
                    Log.i("image_uri", String.valueOf(imageUriMom));
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUriMom);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ImageView imageviewMom = findViewById(R.id.imageView4);
                    imageviewMom.setImageBitmap(bitmap);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    extras = imageReturnedIntent.getExtras();
                    mImageBitmap = (Bitmap) extras.get("data");
                    ImageView imageviewDad = findViewById(R.id.imageView3);
                    imageviewDad.setImageBitmap(mImageBitmap);
                    return;
                }
                break;
            case 3:
                if(resultCode == RESULT_OK){
                    imageUriDad = imageReturnedIntent.getData();
                    Log.i("info", String.valueOf(imageUriDad));
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUriDad);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ImageView imageviewDad = findViewById(R.id.imageView3);
                    imageviewDad.setImageBitmap(bitmap);
                }
                break;
            case 4:
                if (resultCode == RESULT_OK) {
                    extras = imageReturnedIntent.getExtras();
                    mImageBitmap = (Bitmap) extras.get("data");
                    ImageView imageviewPolice = findViewById(R.id.imageView5);
                    imageviewPolice.setImageBitmap(mImageBitmap);
                    return;
                }
                break;
            case 5:
                if(resultCode == RESULT_OK){
                    imageUriPolice = imageReturnedIntent.getData();
                    Log.i("info", String.valueOf(imageUriPolice));
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUriPolice);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ImageView imageviewPolice = findViewById(R.id.imageView5);
                    imageviewPolice.setImageBitmap(bitmap);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CALL_PHONE_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Phone Permission Granted", Toast.LENGTH_LONG).show();
                SharedPreferences sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
                String number = sharedPreferences.getString("numberMom", "");
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this, "Phone Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == MY_SMS_LOCATION_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "SMS And Location Permission Granted", Toast.LENGTH_LONG).show();
                getLocationUpdates();
            }
            else
            {
                Toast.makeText(this, "SMS Or Location Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static  Bitmap cropAndScale (Bitmap source, int scale){
        int factor = source.getHeight() <= source.getWidth() ? source.getHeight(): source.getWidth();
        int longer = source.getHeight() >= source.getWidth() ? source.getHeight(): source.getWidth();
        int x = source.getHeight() >= source.getWidth() ?0:(longer-factor)/2;
        int y = source.getHeight() <= source.getWidth() ?0:(longer-factor)/2;
        source = Bitmap.createBitmap(source, x, y, factor, factor);
        source = Bitmap.createScaledBitmap(source, scale, scale, false);
        return source;
    }

    public static String getId(View view) {
        if (view.getId() == View.NO_ID) return "no-id";
        else return view.getResources().getResourceName(view.getId());
    }

    public void imagePerson(View view) {
        String options_array[]=new String[2];
        options_array[0]="Take Photo";
        options_array[1]="Choose Photo From Folder";

        Log.i("click", "xxxxx"+getId(view));
        if(getId(view).equals("com.anantop.emergencycall:id/imageView4")){
            Log.i("click", "yyyyyyy"+getId(view));
            imageButtonClicked="mom";
        } else if(getId(view).equals("com.anantop.emergencycall:id/imageView3")){
            imageButtonClicked="dad";
        }
        else if (getId(view).equals("com.anantop.emergencycall:id/imageView5")) {
            imageButtonClicked="police";
        }
        else{
            Log.i("click", "unknown button clicked");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image").setItems(options_array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i("info","item clicked number="+which);
                switch (which){
                    case 0:
                        Log.i("tag","I am taking photo");
                        Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "MyPicture");
                        values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
                        if (imageButtonClicked=="mom"){
                            startActivityForResult(takePicture, 0);
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (imageButtonClicked=="dad"){
                            startActivityForResult(takePicture, 2);
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (imageButtonClicked=="police"){
                            startActivityForResult(takePicture, 4);
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 1:
                        Log.i("tag","I am choosing photo");
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        if (imageButtonClicked.equals("mom")) {
                            Log.i("inside if mom","i am inside");
                            startActivityForResult(pickPhoto, 1);//one can be replaced with any action code
                        }
                        else if (imageButtonClicked.equals("dad")){
                            Log.i("inside if dad","i am inside");
                            startActivityForResult(pickPhoto, 3);
                        }
                        else if (imageButtonClicked.equals("police")){
                            Log.i("inside if police","i am inside");
                            startActivityForResult(pickPhoto, 5);
                        }
                        break;
                }
            }
        });
        builder.show();
    }
}