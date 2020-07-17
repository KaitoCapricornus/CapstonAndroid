package com.example.capstonandroid.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImageAsyncTask extends AsyncTask<String, String, Bitmap> {

    private ImageView imageView;

    public DownloadImageAsyncTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap != null) {
            //outputBitmap = bitmap;
            //Log.i("bitmap", "ok");
            imageView.setImageBitmap(bitmap);
        }
    }

}
