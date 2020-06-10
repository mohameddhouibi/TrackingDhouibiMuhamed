package com.muhameddhouibi.trackingdhouibimuhamed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

import java.util.Calendar;
import java.util.Locale;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    FirebaseDatabase firebaseDatabase ;

    Button add_btn , confbtn ,annulbtn ;
    RecyclerView listItem ;
    Dialog Infodiaog;
    DatabaseReference Routers ;
    ZXingScannerView zXingScannerView ;
    EditText info1 , info2 , info3 , info4 ;
    CheckBox cb1,cb2,cb3;
    private FirebaseRecyclerOptions<Router> options;
    private FirebaseRecyclerAdapter<Router, MyGameViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Infodiaog = new Dialog(this);
        add_btn = findViewById(R.id.add_btn);
        listItem = findViewById(R.id.listgames);
        firebaseDatabase = FirebaseDatabase.getInstance();
        listItem.setLayoutManager(new LinearLayoutManager(this));

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Addnew.class);
                startActivity(i);
            }
        });

        Routers=FirebaseDatabase.getInstance().getReference("Routers");
        options =new FirebaseRecyclerOptions.Builder<Router>().setQuery(Routers,Router.class).build();
        adapter=new FirebaseRecyclerAdapter<Router, MyGameViewHolder>(options) {
            @NonNull
            @Override
            public MyGameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);

                return new MyGameViewHolder(v);
            }
            @Override
            protected void onBindViewHolder(@NonNull MyGameViewHolder myGameViewHolder, int i, @NonNull final Router router) {

                String timestamp = router.getTimestamp();
                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                cal.setTimeInMillis(Long.parseLong(timestamp));
                String dateTime = DateFormat.format("dd/MM/yyyy hh:mm aa",cal).toString();
                myGameViewHolder.routerTme.setText(dateTime);
                myGameViewHolder.routerName.setText(router.getRouter_name());
                String etat = router.getEtat();
                if (etat.equals("0"))
                    {
                        myGameViewHolder.routerEtat.setText("Non disponible");
                        myGameViewHolder.color.setImageResource(R.drawable.unnamed);

                    }
                else if (etat.equals("1"))
                    {
                        myGameViewHolder.routerEtat.setText("disponible");
                        myGameViewHolder.color.setImageResource(R.drawable.green);
                    }
                else if (etat.equals("2"))
                {
                    myGameViewHolder.routerEtat.setText("non fonctionnel");
                    myGameViewHolder.color.setImageResource(R.drawable.ki);

                }

                myGameViewHolder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        InformationAlertBuilder (router.getRouter_name(),router.getMacAdress(),router.getRouterModel(),router.getIpadress());
                    }
                });

            }
        };
        adapter.startListening();
        listItem.setAdapter(adapter);
    }
    private void InformationAlertBuilder (String router,String mac ,String model ,String ipadress)
    {
        Infodiaog.setContentView(R.layout.add_idea);
        confbtn=(Button) Infodiaog.findViewById(R.id.conf);
        annulbtn=(Button) Infodiaog.findViewById(R.id.annul);
        info1 = (EditText) Infodiaog.findViewById(R.id.info1);
        info2 = (EditText) Infodiaog.findViewById(R.id.info2);
        info3 = (EditText) Infodiaog.findViewById(R.id.info3);
        info4 = (EditText) Infodiaog.findViewById(R.id.info4);
        cb1 = Infodiaog.findViewById(R.id.non_check);
        cb2 = Infodiaog.findViewById(R.id.oui_check);
        cb3=Infodiaog.findViewById(R.id.le_check);
        info1.setText(router);
        info3.setText(mac);
        info4.setText(model);
        info2.setText(ipadress);
        annulbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Infodiaog.dismiss();
            }
        });


        confbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String router_name = info1.getText().toString();
                final String macAdress = info4.getText().toString();
                final String routerModel = info2.getText().toString();
                final String Ipadress = info3.getText().toString();
                final String timestamp = ""+System.currentTimeMillis();
                String etat = "0";


                if (router_name.isEmpty() || macAdress.isEmpty() || routerModel.isEmpty()) {

                    Toast.makeText(MainActivity.this, "All Fields are Required ! ", Toast.LENGTH_SHORT).show();
                }else {
                if (cb1.isChecked()){
                    etat ="0";
                    Router room = new Router(router_name,macAdress,routerModel,Ipadress,timestamp,etat);
                    Routers.child(router_name).setValue(room);
                    Toast toast=Toast. makeText(getApplicationContext(),"Done !",Toast. LENGTH_SHORT);
                    toast. show();
                }
                else if (cb2.isChecked()){
                    etat ="1";
                    Router room = new Router(router_name,macAdress,routerModel,Ipadress,timestamp,etat);
                    Routers.child(router_name).setValue(room);
                    Toast toast=Toast. makeText(getApplicationContext(),"Done !",Toast. LENGTH_SHORT);
                    toast. show();
                }
                else if (cb3.isChecked())
                {
                    etat ="2";
                    Router room = new Router(router_name,macAdress,routerModel,Ipadress,timestamp,etat);
                    Routers.child(router_name).setValue(room);
                    Toast toast=Toast. makeText(getApplicationContext(),"Done !",Toast. LENGTH_SHORT);
                    toast. show();
                }


            }}
        });
        Infodiaog.show();
    }

    @Override
    public void handleResult(Result rawResult) {
        info1.setText(rawResult.getText());
    }
}
