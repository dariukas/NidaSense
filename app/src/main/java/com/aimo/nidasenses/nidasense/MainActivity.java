package com.aimo.nidasenses.nidasense;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import java.io.IOException;
import java.io.InputStream;
import android.graphics.BitmapFactory;

//https://code.tutsplus.com/tutorials/getting-started-with-android-vr-through-google-cardboard-panoramic-images--cms-27653

public class MainActivity extends AppCompatActivity {

    private VrPanoramaView mVrPanoramaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVrPanoramaView = (VrPanoramaView) findViewById(R.id.pano_view);
        loadPhotoSphere();
    }

    private void loadPhotoSphere() {
        //This could take a while. Should do on a background thread, but fine for current example
        VrPanoramaView.Options options = new VrPanoramaView.Options();
        InputStream inputStream = null;
        AssetManager assetManager = getAssets();

        try {
            inputStream = assetManager.open("vrNida9.png");
            options.inputType = VrPanoramaView.Options.TYPE_MONO;
            mVrPanoramaView.loadImageFromBitmap(BitmapFactory.decodeStream(inputStream), options);
            inputStream.close();
            // mVrPanoramaView.setRotationX((float) 30.0);
        } catch (IOException e) {
            //Log.e("Tuts+", "Exception in loadPhotoSphere: " + e.getMessage() );
            displayAlert("Sorry. The image cannot be loaded!");
        }
    }

    private void displayAlert(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(R.string.app_name);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    protected void onPause() {
        mVrPanoramaView.pauseRendering();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVrPanoramaView.resumeRendering();
    }

    @Override
    protected void onDestroy() {
        mVrPanoramaView.shutdown();
        super.onDestroy();
    }
}