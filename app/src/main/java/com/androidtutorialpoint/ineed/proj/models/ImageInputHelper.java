package com.androidtutorialpoint.ineed.proj.models;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.bouncycastle.util.encoders.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static com.helpshift.support.webkit.CustomWebViewClient.TAG;

/**
 * Created by rakhi on 12/26/2017.
 */

public class ImageInputHelper {

    public static final int REQUEST_PICTURE_FROM_GALLERY = 23;
    public static final int REQUEST_PICTURE_FROM_CAMERA = 24;
    public static final int REQUEST_CROP_PICTURE = 25;
    private static final String TAG = "ImageInputHelper";

    private File tempFileFromSource = null;
    private Uri tempUriFromSource = null;

    private File tempFileFromCrop = null;
    private Uri tempUriFromCrop = null;
    private Activity mContext;
    private Fragment fragment;
    private ImageActionListener imageActionListener;

    public ImageInputHelper(Activity mContext) {
        this.mContext = mContext;
    }

    public ImageInputHelper(Fragment fragment) {
        this.fragment = fragment;
        this.mContext = fragment.getActivity();
    }

    public void setImageActionListener(ImageActionListener imageActionListener) {
        this.imageActionListener = imageActionListener;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == REQUEST_PICTURE_FROM_GALLERY) && (resultCode == Activity.RESULT_OK)) {

            Log.d(TAG, "Image selected from gallery");
            imageActionListener.onImageSelectedFromGallery(data.getData(), tempFileFromSource);

        } else if ((requestCode == REQUEST_PICTURE_FROM_CAMERA) && (resultCode == Activity.RESULT_OK)) {

            Log.d(TAG, "Image selected from camera");
            imageActionListener.onImageTakenFromCamera(tempUriFromSource, tempFileFromSource);

        } else if ((requestCode == REQUEST_CROP_PICTURE) && (resultCode == Activity.RESULT_OK)) {

            Log.d(TAG, "Image returned from crop");
            imageActionListener.onImageCropped(tempUriFromCrop, tempFileFromCrop);
        }
    }



    /**
     * Starts an intent for selecting image from gallery. The result is returned to the
     * onImageSelectedFromGallery() method of the ImageSelectionListener interface.
     */
    public void selectImageFromGallery() {
        checkListener();

        if (tempFileFromSource == null) {
            try {
                tempFileFromSource = File.createTempFile("choose", "png", mContext.getExternalCacheDir());
                tempUriFromSource = Uri.fromFile(tempFileFromSource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUriFromSource);
        if (fragment == null) {
            mContext.startActivityForResult(intent, REQUEST_PICTURE_FROM_GALLERY);
        } else {
            fragment.startActivityForResult(intent, REQUEST_PICTURE_FROM_GALLERY);
        }
    }

    /**
     * Starts an intent for taking photo with camera. The result is returned to the
     * onImageTakenFromCamera() method of the ImageSelectionListener interface.
     */
    public void takePhotoWithCamera() {
        checkListener();

        if (tempFileFromSource == null) {
            try {
                tempFileFromSource = File.createTempFile("choose", "png", mContext.getExternalCacheDir());
                tempUriFromSource = Uri.fromFile(tempFileFromSource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUriFromSource);
        if (fragment == null) {
            mContext.startActivityForResult(intent, REQUEST_PICTURE_FROM_CAMERA);
        } else {
            fragment.startActivityForResult(intent, REQUEST_PICTURE_FROM_CAMERA);
        }
    }

    public void requestCropImage(Uri uri, int outputX, int outputY, int aspectX, int aspectY) {
        checkListener();

        if (tempFileFromCrop == null) {
            try {
                tempFileFromCrop = File.createTempFile("crop", "png", mContext.getExternalCacheDir());
                tempUriFromCrop = Uri.fromFile(tempFileFromCrop);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // open crop intent when user selects image
        final Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("output", tempUriFromCrop);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("scale", true);
        intent.putExtra("noFaceDetection", true);
        if (fragment == null) {
            mContext.startActivityForResult(intent, REQUEST_CROP_PICTURE);
        } else {
            fragment.startActivityForResult(intent, REQUEST_CROP_PICTURE);
        }
    }

    private void checkListener() {
        if (imageActionListener == null) {
            throw new RuntimeException("ImageSelectionListener must be set before calling openGalleryIntent(), openCameraIntent() or requestCropImage().");
        }
    }

    /**
     * Listener interface for receiving callbacks from the ImageInputHelper.
     */
    public interface ImageActionListener {
        void onImageSelectedFromGallery(Uri uri, File imageFile);

        void onImageTakenFromCamera(Uri uri, File imageFile);

        void onImageCropped(Uri uri, File imageFile);
    }
}
