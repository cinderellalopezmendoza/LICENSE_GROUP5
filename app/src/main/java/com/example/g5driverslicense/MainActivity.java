package com.example.g5driverslicense;

//       GROUP 5       //
// JOHN MYKYL DIGO
// EARL JOHN ORDANZA
// DIANNE VALDEZ
// CINDERELLA MENDOZA

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    private static final int PERMISSION_CODE = 101;
    private static final int IMAGE_CAPTURE_CODE = 103;
    Button CaptureBtn;
    ImageView ViewImg;

    Uri image_uri;

    Button register;
    private Button next;
    private EditText firstname, lastname, midname, barangay, city, municipality, province, height, weight, nationality, sex, eyecolor, bloodtype;
    TextView bdayText;
    ImageView etBday;
    private int mDate, mMonth, mYear;
    static String Birthdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button
        register = findViewById(R.id.regBtn);
        //edit text
        firstname = findViewById(R.id.fname);
        lastname = findViewById(R.id.lname);
        midname = findViewById(R.id.mname);
        etBday = findViewById(R.id.bday);
        barangay = findViewById(R.id.brgy);
        municipality = findViewById(R.id.mun);
        city = findViewById(R.id.city);
        province = findViewById(R.id.province);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        nationality = findViewById(R.id.nationality);
        sex = findViewById(R.id.sex);
        bdayText = findViewById(R.id.bdayPicked);
        eyecolor = findViewById(R.id.eyescolor);
        bloodtype = findViewById(R.id.btype);

        ViewImg = findViewById(R.id.image_view);
        CaptureBtn = findViewById(R.id.btn_capture);

        //onClickListener for Birthday Pick
        etBday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDate = calendar.get(Calendar.DATE);


                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this, android.R.style.Theme_DeviceDefault_Dialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int date) {
                                month = month + 1;
                                String bdate = year + "/" + month + "/" + date;
                                bdayText.setText(bdate);
                                Birthdate = bdate;


                            }
                        }, mYear, mMonth, mDate);
                datePickerDialog.show();

            }
        });

        //onClick Listener for Capture Button
        CaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED) {
                        //permission not enabled,request it
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        //show popup to request permission
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        //permission already granted
                        openCamera();
                    }
                } else {
                    //system os < marshmallow
                    openCamera();
                }
            }
        });
    }

    //handling permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //this method is called, when user presses Allow or Deny from Permission Request Popup
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission from popup was granted
                    openCamera();
                } else {
                    //permission from popup was denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);


//onClick Listener for register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Variable declaration and assigning its value from input of the user
                String lname = lastname.getText().toString();
                String fname = firstname.getText().toString();
                String mname = midname.getText().toString();
                String birthday = Birthdate;
                String brgyVal = barangay.getText().toString();
                String munVal = municipality.getText().toString();
                String cityVal = city.getText().toString();
                String provVal = province.getText().toString();
                String heightVal = height.getText().toString();
                String weightVal = weight.getText().toString();
                String sexVal = sex.getText().toString();
                String nationalityVal = nationality.getText().toString();
                String eyescolor = eyecolor.getText().toString();
                String btype = bloodtype.getText().toString();


//Condition to check if there is a null value in registration form
                if (lname.isEmpty() || fname.isEmpty() || mname.isEmpty() || brgyVal.isEmpty()
                        || nationalityVal.isEmpty() || munVal.isEmpty() || cityVal.isEmpty() || provVal.isEmpty() || heightVal.isEmpty()
                        || weightVal.isEmpty() || sexVal.isEmpty() || eyescolor.isEmpty() || btype.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(v, "Please Fill up required form", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (birthday == null) {
                    Snackbar snackbar = Snackbar.make(v, "Please Pick your Birthday, just click the calendar icon", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (ViewImg.getDrawable() == null) {
                    Snackbar snackbar = Snackbar.make(v, "Please Take a Photo", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {


//If registration is completely filled up, we pass the data and used  "keyword" to access or get it later in License Activity
                    Intent intent = new Intent(MainActivity.this, LicenseActivity.class);
                    intent.putExtra("FName", fname);
                    intent.putExtra("LName", lname);
                    intent.putExtra("MName", mname);
                    intent.putExtra("Brgy", brgyVal);
                    intent.putExtra("Mun", munVal);
                    intent.putExtra("City", cityVal);
                    intent.putExtra("Prov", provVal);
                    intent.putExtra("Height", heightVal);
                    intent.putExtra("Weight", weightVal);
                    intent.putExtra("Sex", sexVal);
                    intent.putExtra("Bday", birthday);
                    intent.putExtra("Nationality", nationalityVal);
                    intent.putExtra("EyesColor", eyescolor);
                    intent.putExtra("Bloodtype", btype);
                    Intent passImg = new Intent(MainActivity.this, LicenseActivity.class);
                    intent.putExtra("uri", image_uri.toString());
                    startActivity(intent);
                    finish();

                }
            }

        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //called when image was captured from camera

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //set the image captured to our ImageView
            ViewImg.setImageURI(image_uri);
        }
    }
}