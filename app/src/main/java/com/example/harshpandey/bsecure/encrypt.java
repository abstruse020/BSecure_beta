package com.example.harshpandey.bsecure;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class encrypt extends AppCompatActivity {

    String pathselected,fileContents,savePath,fname;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);
        make_editable(true);


    }

    public void selectFile(View view)
    {
        //Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        //intent.putExtra("file",)
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        if(requestCode==1)
            if(resultCode== Activity.RESULT_OK)
            {

                //Uri is stored in uri
                try
                {
                    uri = data.getData();
                    Log.v("encrypt page ", "getting the uri = "+uri.toString());
                    pathselected=uri.getPath();//file path

                }
                catch (NullPointerException e)//removed file not found exception
                {
                    e.printStackTrace();
                    Log.e("Main Activity", "\n\n\n\nFile Not Found no \n null pointer.");
                    return;
                }
                // FileDescriptor fd = fileContents2.getFileDescriptor();//now fd could be used to read file

                show_path("Path = "+pathselected);
                displayContent();
            }
    }
    public void displayContent()
    {
        Log.v("main activity encrypt","encrypt called");
        try
        {
            readContent();
        }
        catch(IOException io)
        {
            io.printStackTrace();
        }
    }
    public void readContent()throws IOException
    {
        int i=0;
        InputStream fin = getContentResolver().openInputStream(uri);
        fileContents="";
        while((i=fin.read())!=-1){
            char ch= (char) i;
            fileContents += ch;
        }
        Log.v("main activity ","\n\n\n\nfile contents"+fileContents);
        TextView write=(TextView)findViewById(R.id.preview_or_write);
        write.setText("Preview");
        show_preview(fileContents);
    }
    public void encrypt_it(View view) {
        int size =0;
        if(TextUtils.isEmpty(fileContents))//if file contents is empty it means no file was selected (file was empty) then check the text view
        {
            Log.v("encrypt","checking text box for \n\n\n\ncontents");
            EditText t= (EditText)findViewById(R.id.preview);
            //filecontents variable updated
            fileContents= t.getText().toString();
            //if the text field is also empty then make a toast
            if(TextUtils.isEmpty(fileContents))
            {
                Toast toast = Toast.makeText(this,"Please select a file or Write a Text",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM,0,10);
                toast.show();
                return;
            }
            //making the new file name for the written text to be saved
            if(fileContents.length()<=11)
            {
                Toast toast = Toast.makeText(this,"enter a longer string",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM,0,10);
                toast.show();
                return;
            }
            pathselected=fileContents.substring(0,10);
            //size=fileContents.length();
        }




        //String toEncrypt=fileContents;
        //byte[] enc_text;
        SecretKey secret = null;


        size=fileContents.length();
        Log.v("encrypt activity" , "the length of file contents is = "+size);

        //int[] image_array = new int[size_img * size_img];
        //byte[] b_image_array = new byte[size_img * size_img];
        int i1=0;


        try {

            secret = MyCipher.generateKey();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        try {

            fileContents=MyCipher.encryptMsg(fileContents, secret);
            //putting the encrypted text directly into the image array
//            for(i1=0; i1<enc_text.length;i1++)
//            {
//                b_image_array[i1]=enc_text[i1];
//            }
//            Log.v("main activity","plain text = "+toEncrypt+"\ncypher text = "+enc_text.toString()+" || "+enc_text[0]+" "+enc_text[1]);
//            //String toDecrypt=enc_text.toString();


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        show_preview(fileContents);
        make_editable(false);
        size=fileContents.length();
        Log.v("encrypt activity", "the encrypted text is = "+fileContents);
        Log.v("encrypt activity ","the new size is = "+size);

        int size_img = (int) Math.sqrt(size) + 2;
        Log.v("encrypt activity", "the length of image size is = "+ size_img*size_img);


        int[] image_array = new int[size_img*size_img];
        char[] fileCon_char = fileContents.toCharArray();
        for (i1 = 0; i1 < size; i1++)
            image_array[i1] = (int) fileCon_char[i1];
        for (; i1 < size_img * size_img; i1++)
            image_array[i1] = 255;

//        for (; i1 < size_img * size_img; i1++)
//            b_image_array[i1] = (byte)255;
//        int[] image_array = new int[size_img*size_img];
//        for(int i=0; i<size_img*size_img; i++)
//            image_array[i]=(int)b_image_array[i];





        Bitmap bm = Bitmap.createBitmap(image_array, size_img, size_img, Bitmap.Config.ARGB_8888);
        Bitmap mutbm = bm.copy(Bitmap.Config.ARGB_8888, true);
        Log.v("main activity ", "\n\n\nbitmap made");
        bm.recycle();
        int i = 0;
        for (int k = 0; k < size_img; k++) {
            for (int j = 0; j < size_img; j++) {
                if(i<(size_img*size_img-2))
                {
                    mutbm.setPixel(j, k, Color.rgb(image_array[i], image_array[i+1], image_array[i+2]));
                }
                else if(i == (size_img*size_img-1))
                {
                    mutbm.setPixel(j,k,Color.rgb(image_array[i],255,255));
                }
                else if (i==(size*size_img-2))
                {
                    mutbm.setPixel(j,k,Color.rgb(image_array[i],image_array[i+1],255));
                }
                else
                {
                    mutbm.setPixel(j,k,Color.rgb(255,255,255));
                }
                i=i+3;
            }
        }
        String name_file = "";
        char[] path_char = pathselected.toCharArray();
        for (i = pathselected.length() - 5; i > 0; i--) {
            if (path_char[i] == '/')
                break;
            name_file = name_file + path_char[i];
        }
        String name ="";
        for (int h = name_file.length()-1; h >= 0; h--)
        {
            name=name + name_file.charAt(h);
        }
        Log.v("main activity","this \n\n\n "+"new name = "+name_file);
        saveImage(mutbm,name);
    }
    private void saveImage(Bitmap finalBitmap, String image_name) {

        String root = null;
        try {
            root = get_path();
            if(TextUtils.isEmpty(root))
            {
                root=Environment.getExternalStorageDirectory().toString()+"/BSecure/Encrypted";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        File myDir = new File(root);
        myDir.mkdirs();
        fname = image_name+ "-Image.png";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        Log.i("LOAD", root + fname);
        try {
            Log.v("save image",  " t \n\n\n "+"saving the file");
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast toast = Toast.makeText(this,"File Saved at "+root,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM,0,10);
        toast.show();
    }
    public String get_path() throws IOException
    {

        String path_l="",p;
        int i=0;
        p=Environment.getExternalStorageDirectory().toString()+"/BSecure/Data/e_path.txt";
        //Uri uri;
        //uri=Uri.fromFile(new File(p));
        //InputStream fin = getContentResolver().openInputStream(uri);
        File file = new File(p);
        InputStream fin = new FileInputStream(file);
        path_l="";
        while((i=fin.read())!=-1){
            char ch= (char) i;
            path_l += ch;
        }
        return path_l;
    }
    public void show_preview(String text)
    {
        TextView prev = (TextView)findViewById(R.id.preview);
        prev.setText(text);
    }
    public void show_path(String text)
    {
        TextView textview = (TextView)findViewById(R.id.show_path);
        textview.setText(text);
    }
    void make_editable(Boolean what)
    {
        EditText et = (EditText)findViewById(R.id.preview);
        et.setEnabled(what);
    }
}
