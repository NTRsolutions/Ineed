package com.androidtutorialpoint.ineed.proj.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.models.ImageInputHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;


public class GalFragment extends Fragment implements ImageInputHelper.ImageActionListener{
    ImageView imageView, imgCam;
    private ImageInputHelper imageInputHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gal, container, false);
        imageView = (ImageView) view.findViewById(R.id.emp_img_profilew);
        imageInputHelper = new ImageInputHelper(this);
        imageInputHelper.setImageActionListener(this);
        imgCam = (ImageView) view.findViewById(R.id.emp_img_camera);

        imgCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Add Photo!");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo"))
                        {
                            imageInputHelper.takePhotoWithCamera();

                        }
                        else if (options[item].equals("Choose from Gallery"))
                        {
                            imageInputHelper.selectImageFromGallery();

                        }
                        else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageInputHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onImageSelectedFromGallery(Uri uri, File imageFile) {
        imageInputHelper.requestCropImage(uri, 800, 450, 16, 9);

    }

    @Override
    public void onImageTakenFromCamera(Uri uri, File imageFile) {
        imageInputHelper.requestCropImage(uri, 800, 450, 16, 9);

    }

    @Override
    public void onImageCropped(Uri uri, File imageFile) {
        try {
            // getting bitmap from uri
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

            // showing bitmap in image view
            Glide.with(this).load(bitmap).apply(RequestOptions.circleCropTransform()).into(imageView);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
