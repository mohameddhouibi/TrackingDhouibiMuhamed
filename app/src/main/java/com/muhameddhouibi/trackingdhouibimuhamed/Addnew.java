package com.muhameddhouibi.trackingdhouibimuhamed;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.muhameddhouibi.trackingdhouibimuhamed.Entity.Router;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Addnew extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    DatabaseReference Routers ;
    ZXingScannerView zXingScannerView ;
    Button add_btn , confbtn ,annulbtn ;
    EditText info1 , info2 , info3 , info4 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);

        Routers= FirebaseDatabase.getInstance().getReference("Routers");


        confbtn=findViewById(R.id.conf);
        annulbtn=findViewById(R.id.annul);
        info1 = findViewById(R.id.info1);
        info2 = findViewById(R.id.info3);
        info3 = findViewById(R.id.info2);
        info4 = findViewById(R.id.info4);
        zXingScannerView = findViewById(R.id.zxing);
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                zXingScannerView.setResultHandler(Addnew.this);
                    zXingScannerView.startCamera();
            }
            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(Addnew.this, "you need to accept the permission", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
            }
        }).check();
        annulbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        confbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String routers_id= Routers.push().getKey();

                final String router_name = info1.getText().toString();
                final String macAdress = info4.getText().toString();
                final String routerModel = info2.getText().toString();
                final String Ipadress = info3.getText().toString();
                final String timestamp = ""+System.currentTimeMillis();
                final String etat = "0";

                if (router_name.isEmpty() || macAdress.isEmpty() || routerModel.isEmpty()) {

                    Toast.makeText(Addnew.this, "All Fields are Required ! ", Toast.LENGTH_SHORT).show();
                }
                else{
                Router room = new Router(router_name,macAdress,routerModel,Ipadress,timestamp,etat);
                Routers.child(router_name).setValue(room);
                Toast toast=Toast. makeText(getApplicationContext(),"Done !",Toast. LENGTH_SHORT);
                toast. show();
                }
            }
        });

    }


    @Override
    public void handleResult(Result rawResult) {
        String txtQr = rawResult.getText();
        int position=txtQr.indexOf((int)';',3);
        String t2=txtQr.substring(position+1, txtQr.length());
        String t1=txtQr.substring(0,position);
        info4.setText(t2);
        info2.setText(t1);

    }
}

