package com.androidtutorialpoint.ineed.proj.callbacks;

import android.graphics.Bitmap;

/**
 * Created by sony on 15-12-2017.
 */

public interface ImageSelectCallBack {

    void success(String data) ;
    void success(Bitmap data) ;
    void fail(String error) ;

}
