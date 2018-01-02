package com.androidtutorialpoint.ineed.proj.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Patterns;
import android.widget.Toast;

import com.androidtutorialpoint.ineed.R;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class Utillity {
   static ProgressDialog progressDialog=null;
    public static void message(Context context,String Msg)
    {
        Toast.makeText(context,Msg,Toast.LENGTH_SHORT).show();
    }
    public static Boolean isNetworkConnected(Context context)
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnectedOrConnecting())
            return true;
        return false;
    }
    public static String BitMapToString(Bitmap bitmap){
        String temp="";
        if(bitmap!=null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
            byte[] b = baos.toByteArray();
            temp = Base64.encodeToString(b, Base64.DEFAULT);
        }
        return temp;
    }

    public static String  getRandomString(int length) {
        String VALID_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_abcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * VALID_CHARS.length());
            salt.append(VALID_CHARS.charAt(index));
        }
        return salt.toString();
    }

    public static String getSHA256Hash(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            for (byte aHash : hash) {
                String hex = Integer.toHexString(0xff & aHash);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static void showloadingpopup(Activity activity)
    {
        if(progressDialog!=null)
        {
            progressDialog.dismiss();
        }
        progressDialog=new ProgressDialog(activity);
        progressDialog.setMessage(activity.getResources().getString(R.string.pleasewait));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
    public static void hidepopup()
    {
        if(progressDialog!=null)
        {
            progressDialog.dismiss();
        }
        progressDialog=null;
    }
    public static Boolean CheckEmail(String email)
    {
        if (email!=null)
        {
            Pattern pattern= Patterns.EMAIL_ADDRESS;
            if (pattern.matcher(email).matches())
            {
                return pattern.matcher(email).matches();
            }
        }
        return false;
    }

    public static Boolean CheckPhone(String phone)
    {
        if (phone!=null)
        {
            Pattern pattern= Patterns.PHONE;
            if (pattern.matcher(phone).matches())
            {
                return pattern.matcher(phone).matches();
            }
        }
        return false;
    }
}
