package com.muhameddhouibi.trackingdhouibimuhamed;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyGameViewHolder extends RecyclerView.ViewHolder {
    public TextView routerName,routerEtat,routerTme;
    public Button btn_invite;
    public View view ;
    public ImageView color ;
    public Button edit ;


    public MyGameViewHolder(@NonNull View itemView) {
        super(itemView);
        routerName = itemView.findViewById(R.id.routername);
        routerEtat = itemView.findViewById(R.id.UserName_connected);
        routerTme = itemView.findViewById(R.id.time);
        color=itemView.findViewById(R.id.etat);
        edit= itemView.findViewById(R.id.edit);
        view = itemView ;
    }
}
