package com.example.g5driverslicense;

//       GROUP 5       //
// JOHN MYKYL DIGO
// EARL JOHN ORDANZA
// DIANNE VALDEZ
// CINDERELLA MENDOZA

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class LicenseActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;

    TextView ResFname,ResLname,ResMname,ResBrgy,ResCity,ResMun,ResProv,ResHeight,ResWeight,ResSex,ResNationality,ResBday,ResBtype,ResEyesColor,ResExpire,ResLicenseNum;
    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

        //License no. generator
        Random random = new Random();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder sb = new StringBuilder();
        int length = 4;
        for(int i = 0; i < length; i++){
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);

            sb.append(randomChar);
        }
        char[] chars1 ="0123456789".toCharArray();
        Random rand1 = new Random();
        StringBuilder sb1 = new StringBuilder((10 + rand1.nextInt(90))+"-");
        for(int i = 0; i < 5; i++)
        {sb1.append(chars1[rand1.nextInt(chars1.length)]);}

        //get current date
        Calendar calendar = Calendar.getInstance();
        //add 10 years from current date, as expiration
        calendar.add(Calendar.YEAR, 10);
        //Date expireDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
        String expiration = dateFormat.format(calendar.getTime());
        //License no.
        UUID uniqueKey = UUID.randomUUID();

        //Declare image view
        ImageView id = findViewById(R.id.PicContainer);



        //get image
        if(getIntent().getExtras() != null){
            image_uri = Uri.parse(getIntent().getStringExtra("uri"));
            id.setImageURI(image_uri);
        }

//Intent to get the data from main activity
        Intent info = getIntent();
//Variable declaration and assigning its value by "keyword" from mainactivity
        String FirstName = info.getStringExtra("FName");
        String LastName = info.getStringExtra("LName");
        String MiddleName = info.getStringExtra("MName");
        String brgyVal = info.getStringExtra("Brgy");
        String Municipality = info.getStringExtra("Mun");
        String City = info.getStringExtra("City");
        String Province = info.getStringExtra("Prov");
        String Height = info.getStringExtra("Height");
        String Weight = info.getStringExtra("Weight");
        String Nationality = info.getStringExtra("Nationality");
        String Sex = info.getStringExtra("Sex");
        String Birthday = info.getStringExtra("Bday");
        String Bloodtype = info.getStringExtra("Bloodtype");
        String EyesColor = info.getStringExtra("EyesColor");

//assigning
        ResFname = findViewById(R.id.fnameContainer);
        ResLname = findViewById(R.id.lnameContainer);
        ResMname = findViewById(R.id.mnameContainer);
        ResBrgy = findViewById(R.id.brgyContainer);
        ResCity = findViewById(R.id.cityContainer);
        ResMun = findViewById(R.id.munContainer);
        ResProv = findViewById(R.id.provContainer);
        ResHeight = findViewById(R.id.heightContainer);
        ResWeight = findViewById(R.id.weightContainer);
        ResSex = findViewById(R.id.sexContainer);
        ResNationality = findViewById(R.id.nationalityContainer);
        ResBday = findViewById(R.id.bdayContainer);
        ResBtype = findViewById(R.id.bloodtypeContainer);
        ResEyesColor =findViewById(R.id.eyesContainer);
        ResExpire = findViewById(R.id.expireContainer);
        ResLicenseNum = findViewById(R.id.licenseContainer);

//setting text to display for names
        ResFname.setText(FirstName);
        ResLname.setText(LastName);
        ResMname.setText(MiddleName);
//setting text to display for address
        ResBrgy.setText(brgyVal);
        ResMun.setText(Municipality);
        ResCity.setText(City);
        ResProv.setText(Province);
//setting text to display for height and weight
        ResHeight.setText(Height);
        ResWeight.setText(Weight);


//setting text to display for nationality/sex/bday/bloodtype/eyecolor/expiration of license/License no.
       ResNationality.setText(Nationality);
       ResSex.setText(Sex);
       ResBday.setText(Birthday);
       ResBtype.setText(Bloodtype);
       ResEyesColor.setText(EyesColor);
       ResExpire.setText(expiration);
       ResLicenseNum.setText(sb.toString()+"-"+sb1.toString());


    }

    @Override
    public void onBackPressed() {

        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }else{
            backToast = Toast.makeText(getBaseContext(),"Press Back Again to Exit",Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();

    }
}