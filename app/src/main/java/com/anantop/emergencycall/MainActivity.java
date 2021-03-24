package com.anantop.emergencycall;

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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {
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
        String imageMom = sharedPreferences.getString("imageMom", "");
        imageUriMom = Uri.parse(imageMom);
        EditText number1 = (EditText) findViewById(R.id.editTextTextPersonName2);
        number1.setText(phoneMom);
        Bitmap bitmap;
        ImageView imageview = findViewById(R.id.imageView4);
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUriMom);
            imageview.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // set the text2 value from database
        sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
        String phoneDad = sharedPreferences.getString("numberDad", "");
        String imageDad = sharedPreferences.getString("imageDad", "");
        imageUriDad = Uri.parse(imageDad);
        number1 = (EditText) findViewById(R.id.editTextTextPersonName);
        number1.setText(phoneDad);
        ImageView imageview1 = findViewById(R.id.imageView3);
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUriDad);
            imageview1.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // set the text3 value from database
        sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
        String phonePolice = sharedPreferences.getString("numberPolice", "");
        String imagePolice = sharedPreferences.getString("imagePolice", "");
        imageUriPolice = Uri.parse(imagePolice);
        number1 = (EditText) findViewById(R.id.editTextTextPersonName3);
        number1.setText(phonePolice);
        ImageView imageview2 = findViewById(R.id.imageView5);
        try {
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
        String number2 = number1.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("numberMom", number2);
        editor.putString("imageMom",imageUriMom.toString());
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
        Log.i("info", "inside getLocation");
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                Log.i("info", "locationManager is not null");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
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
            address = test(location);
            Log.i("info", address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sms.sendTextMessage(ph_no, null, "Need Help. Current Location: " + location.getLatitude() + ", " + location.getLongitude() + "Address=" + address, pi, null);
        Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    private void longClickAction(String person) {
        SharedPreferences sharedPreferences = getSharedPreferences("fileNameString", MODE_PRIVATE);
        ph_no = sharedPreferences.getString(person, "");
        Log.i("info", "inside longClickAction");
        getLocation();
        Toast.makeText(getApplicationContext(), "Message Will Be Sent Shortly",
                Toast.LENGTH_LONG).show();
    }

    public String test(Location location) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

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

    public void imageMom(View view) {
        String color_array[]=new String[2];
        color_array[0]="Take Photo";
        color_array[1]="Choose Photo From Folder";

        Log.i("click", getId(view));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image").setItems(color_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("info","item clicked number="+which);

                        switch (which){
                            case 0:
                                Log.i("tag","I am taking photo");
                                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                ContentValues values = new ContentValues();
                                values.put(MediaStore.Images.Media.TITLE, "MyPicture");
                                values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
                                imageUriMom = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, imageUriMom);
                                startActivityForResult(takePicture, 0);
                                break;
                            case 1:
                                Log.i("tag","I am choosing photo");
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
                                break;
                        }
                    }
                });
        builder.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                break;
            case 1:
                if(resultCode == RESULT_OK){
                    imageUriMom = imageReturnedIntent.getData();
                    Log.i("info", String.valueOf(imageUriMom));

                }
                break;
            case 2:
                break;
            case 3:
                if(resultCode == RESULT_OK){
                    imageUriDad = imageReturnedIntent.getData();
                    Log.i("info", String.valueOf(imageUriDad));

                }
                break;
            case 4:
                break;
            case 5:
                if(resultCode == RESULT_OK){
                    imageUriPolice = imageReturnedIntent.getData();
                    Log.i("info", String.valueOf(imageUriPolice));

                }
                break;
        }
        Bitmap bitmap;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUriMom);
            ImageView imageviewMom = findViewById(R.id.imageView4);
            imageviewMom.setImageBitmap(bitmap);
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUriDad);
            ImageView imageviewDad = findViewById(R.id.imageView3);
            imageviewDad.setImageBitmap(bitmap);
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUriPolice);
            ImageView imageviewPolice = findViewById(R.id.imageView5);
            imageviewPolice.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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

    public void imageDad(View view) {
        String color_array[]=new String[2];
        color_array[0]="Take Photo";
        color_array[1]="Choose Photo From Folder";
        Log.i("click", getId(view));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image").setItems(color_array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i("info","item clicked number="+which);

                switch (which){
                    case 0:
                        Log.i("tag","I am taking photo");
                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "MyPicture");
                        values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
                        imageUriDad = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        takePicture.putExtra(MediaStore.EXTRA_OUTPUT, imageUriDad);
                        startActivityForResult(takePicture, 2);
                        break;
                    case 1:
                        Log.i("tag","I am choosing photo");
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , 3);//one can be replaced with any action code
                        break;
                }
            }
        });
        builder.show();
    }

    public void imagePolice(View view) {
        String color_array[]=new String[2];
        color_array[0]="Take Photo";
        color_array[1]="Choose Photo From Folder";
        Log.i("click", getId(view));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image").setItems(color_array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i("info","item clicked number="+which);

                switch (which){
                    case 0:
                        Log.i("tag","I am taking photo");
                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "MyPicture");
                        values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
                        imageUriPolice = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        takePicture.putExtra(MediaStore.EXTRA_OUTPUT, imageUriPolice);
                        startActivityForResult(takePicture, 4);
                        break;
                    case 1:
                        Log.i("tag","I am choosing photo");
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , 5);//one can be replaced with any action code
                        break;
                }
            }
        });
        builder.show();
    }

    public static String getId(View view) {
        if (view.getId() == View.NO_ID) return "no-id";
        else return view.getResources().getResourceName(view.getId());
    }
}