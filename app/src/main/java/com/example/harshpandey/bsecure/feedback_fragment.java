package com.example.harshpandey.bsecure;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class feedback_fragment extends Fragment {


    EditText t2,t1;
    Button submit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feedback,container,false);
        t1= (EditText)view.findViewById(R.id.problem);
        t2=(EditText)view.findViewById(R.id.suggestion);
        submit=(Button)view.findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String feedback="Problem: ";
                feedback+=t1.getText().toString();
                feedback=feedback+" \nSuggestion: ";
                feedback=feedback+t2.getText().toString();
                Intent intent =new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto: "));
                intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback from user" );
                intent.putExtra(Intent.EXTRA_TEXT,feedback);
                String[] add=new String[1];
                add[0]="bokharsh@gmail.com";
                intent.putExtra(Intent.EXTRA_EMAIL,add);
                if(intent.resolveActivity(getActivity().getPackageManager())!=null)
                    startActivity(intent);
            }
        });

        return view;
    }

}
