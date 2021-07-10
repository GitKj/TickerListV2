package com.example.tickerlistv2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


// Credit to: https://medium.com/@crossphd/android-image-loading-from-a-string-url-6c8290b82c5e
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage)
    {
        this.bmImage = bmImage;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        String urldisplay = strings[0];

        Bitmap bmp = null;
        try {
            InputStream in = new URL(urldisplay).openStream();
            bmp = BitmapFactory.decodeStream(in);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;

    }

    protected void onPostExecute(Bitmap result)
    {
        bmImage.setImageBitmap(result);
    }
}
