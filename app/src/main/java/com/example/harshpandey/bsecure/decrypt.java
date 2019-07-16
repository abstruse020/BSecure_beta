package com.example.harshpandey.bsecure;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class decrypt extends AppCompatActivity {

    Bitmap bm;
    Uri uri;
    String pathselected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);


        TextView preview= (TextView) findViewById(R.id.show_text);
        preview.setMovementMethod(new ScrollingMovementMethod());

    }

    public void selectFile(View view)
    {
        show_text("");
        //Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        //intent.putExtra("file",)
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),1);
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        if(requestCode==1 || resultCode==Activity.RESULT_OK)
        {
            Uri uri;

            try {
                uri = data.getData();
                Log.v("encrypt page ", "getting the uri = "+uri.toString());
                pathselected = uri.getPath();
                pathselected=correct(pathselected);
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
                Log.e("MainActivity","Null Pointer Exception");
            }
            Log.v("decrypt activity","path selected is "+pathselected);
            show_path(pathselected);
            getImageBitmap();
        }
    }

    public String correct(String checkp)
    {
        int i=0;
        byte flag=0;
        String correctp="";
        for(i=0; i< checkp.length(); i++) {
            if (checkp.charAt(i) == ':') {
                flag = 1;
                i++;
                break;
            }
            correctp += checkp.charAt(i);
        }
        if(flag==1)
        {
            correctp="";
            for(;i<checkp.length();i++)
                correctp+=checkp.charAt(i);
            correctp=Environment.getExternalStorageDirectory().toString() +"/"+ correctp;
        }
        return correctp;

    }
    public void getImageBitmap()
    {
        bm = BitmapFactory.decodeFile(pathselected);
        Log.v("Image bitmap","inside the bitmap function call");
    }
    public void decrypt(View view)
    {
        if(TextUtils.isEmpty(pathselected))
        {
            Toast toast = Toast.makeText(this,"Please select a file to decrypt it",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM,0,10);
            toast.show();
            return;
        }
        int[] pixels = new int[bm.getHeight()*bm.getWidth()];
        bm.getPixels(pixels,0,bm.getWidth(),0,0,bm.getWidth(),bm.getHeight());
        Log.v("main activity", "size of image "+bm.getHeight()+" X "+bm.getWidth());
        String imageContent="";
        //int no_of_contents=0;
        //byte[] item_storage=new byte[bm.getWidth()*bm.getHeight()*3];
        for(int i=0;i<(bm.getWidth()*bm.getHeight()); i++)
        {
            int tem1= Color.red(pixels[i]);//or save it in byte


            if(tem1!=255)
            {
                char ch=(char)tem1;
                //item_storage[no_of_contents]=(byte)tem1;
                imageContent+=ch;

            }

            tem1=Color.green(pixels[i]);
            if(tem1!=255)
            {
                char ch=(char)tem1;
                imageContent+=ch;
                //item_storage[no_of_contents]=(byte)tem1;
                //no_of_contents++;
            }

            tem1=Color.blue(pixels[i]);
            if(tem1!=255)
            {
                char ch=(char)tem1;
                imageContent+=ch;
                //item_storage[no_of_contents]=(byte)tem1;
                //no_of_contents++;
            }


        }
//        byte[] toDecrypt= new byte[no_of_contents];
//        Log.v("decrypt activity", " no  of contents are = "+no_of_contents);
//        for(int i=0; i<no_of_contents; i++)
//        {
//            toDecrypt[i]=item_storage[i];
//            Log.v("decrypt activity",toDecrypt[i]+" ");
//        }
        Log.v("decrypt activity" , "the encrypted contents were ="+imageContent);
        SecretKey secret = null;
        try {

            secret = MyCipher.generateKey();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        try {
            imageContent= MyCipher.decryptMsg(imageContent,secret);
            Log.v("decrypt activity", "saved to image content");

        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.v("decrypt","image contents now are "+imageContent);
        //Log.v("decrypt","image contents are some = "+toDecrypt[0]+" " + toDecrypt[1]);



        show_text(imageContent);

        bm.recycle();
        int i;
        String name_file = "";
        char[] path_char = pathselected.toCharArray();
        for (i = pathselected.length() - 5; i > 0; i--)
        {
            if (path_char[i] == '/')
                break;
            name_file = name_file + path_char[i];
        }
        String name ="";
        for (int h = name_file.length()-1; h >= 0; h--)
        {
            name=name + name_file.charAt(h);
        }
        save_to_file(imageContent,name);
    }

    public String get_path()throws IOException
    {
        String path_l="",p;
        int i=0;
        p=Environment.getExternalStorageDirectory().toString()+"/BSecure/Data/d_path.txt";
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


    public void save_to_file(String content,String file_name )
    {
        String root = "";
        try {
            root=get_path();
            if(TextUtils.isEmpty(root))
            {
                root=Environment.getExternalStorageDirectory().toString()+"/BSecure/Decrypted";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File myDir = new File(root);
        myDir.mkdirs();
        String fname = file_name+ "-TEXT.txt";

        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        Log.i("LOAD", root + fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            //finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.write(content.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast toast = Toast.makeText(this,"File Saved at "+root,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM,0,15);
        toast.show();
    }

    public void show_path(String str)
    {
        TextView tv = (TextView)findViewById(R.id.show_path);
        tv.setText(str);
    }
    public void show_text(String str)
    {
        TextView tv = (TextView)findViewById(R.id.show_text);
        tv.setText(str);
    }
}
