package com.example.harshpandey.bsecure;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class about_us_fragment extends Fragment {


    TextView harsh,abhishek,aditya;
    Button b1_contact;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us,container,false);
        b1_contact=(Button)view.findViewById(R.id.contact_us_button);
        harsh=(TextView)view.findViewById(R.id.contact_Harsh);
        aditya=(TextView)view.findViewById(R.id.contact_Aditya);
        abhishek=(TextView)view.findViewById(R.id.contact_Abhishek);
        b1_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                harsh.setText("Harsh: 8604831464");

                abhishek.setText("Abhishek: 7054035006");

                aditya.setText("Aditya: 8171860024");
            }
        });
        harsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:8604831464"));
                startActivity(intent);
            }
        });
        abhishek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:7054035006"));
                startActivity(intent);
            }
        });
        aditya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:8171860024"));
                startActivity(intent);
            }
        });


        return view;
    }

}
