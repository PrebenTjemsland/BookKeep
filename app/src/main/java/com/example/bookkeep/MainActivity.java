package com.example.bookkeep;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.TextView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    List<Object> books = new ArrayList <Object>();
    String stringName, stringAuthor;
    String test1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        books.add (new Book("Hjernen er stjernen", "Kaja Nordengen", 4,"420"));


    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    setContentView(R.layout.add_view);

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }



    public void scanNow (View View){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("Scan a barcode");
        integrator.setResultDisplayDuration(0);
        integrator.setWide();  // Wide scanning rectangle, may work better for 1D barcodes
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();
    }

    /**
     * function handle scan result
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    public void onActivityResult ( int requestCode, int resultCode, Intent intent){
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String codeFormat,codeContent;
        TextView formatTxt, contentTxt;

        if (scanningResult != null) {
            formatTxt = findViewById(R.id.scan_format);
            contentTxt = findViewById(R.id.scan_content);

            codeContent = scanningResult.getContents();
            codeFormat = scanningResult.getFormatName();

            // display it on screen
            formatTxt.setText("FORMAT: " + codeFormat);
            contentTxt.setText("CONTENT: " + codeContent);
            addToList(codeContent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void changelayoutList(View view){
        setContentView(R.layout.list_view);
        TextView test = findViewById(R.id.test);
        for(Object book : books) {
        }


    }

    public void changelayoutHome (View view) {
        setContentView(R.layout.activity_main);
    }

    public void changelayoutAdd (View view) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            // Should we show an explanation?
            //if (ActivityCompat.shouldShowRequestPermissionRationale(this,
            //      Manifest.permission.CAMERA)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            //} else {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    1);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            //}
        } else {
            // Permission has already been granted
            setContentView(R.layout.add_view);
        }

    }






    class Book {
        String name;
        String author;
        float score;
        String code;

        Book(String name, String author, float score, String code) {
            this.name = name;
            this.author = author;
            this.score = score;
            this.code = code;

        }

        public String getAuthorName() {
            return name;
        }

    }

    public void addToList (String contentTxt){
        TextView name = findViewById(R.id.inputName);
        TextView author   = findViewById(R.id.authorInn);
        RatingBar rate = findViewById(R.id.rateInn);

        stringName = name.getText().toString();
        stringAuthor = author.getText().toString();

        books.add(new Book(stringName, stringAuthor, rate.getRating(), contentTxt));
        setContentView(R.layout.activity_main);
    }



}

