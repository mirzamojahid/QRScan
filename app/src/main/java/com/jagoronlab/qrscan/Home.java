package com.jagoronlab.qrscan;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.time.Instant;

public class Home extends AppCompatActivity {


    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    Button btnAction;
    String intentData = "";

    public static class intent implements CustomInterface {
        String intentData = "";

        public static void putExtra(String keyurl, String url) {
        }

        public String getName() {
            return intentData;
        }
    }

    public interface CustomInterface {
        String getName();
    }


    public interface MyInterface {
        public void aMethod();
    }

    public class MyObject {

        private Context context;
        private MyInterface inter;

        public MyObject(Context context) {
            this.context = context;
            this.inter = (MyInterface) this.context;
            inter.aMethod();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        initViews();
    }

    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
        btnAction = findViewById(R.id.btnAction);


   /*   btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (intentData.length() > 0) {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(intentData)));
                    return;
                }


          //intent data pass webview activity using string
                else {
                 String url = txtBarcodeValue.getText().toString();
                    intent.putExtra("keyurl",url);
                    Toast.makeText(Home.this,url,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(intentData)));
                }


            }

        });

}


    */

    }
    private void initialiseDetectorsAndSources() {

        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                        return;
                    } else {
                        ActivityCompat.requestPermissions(Home.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });



        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(intentData)));
                Toast.makeText(getApplicationContext(), "URL Open", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {


                    txtBarcodeValue.post(new Runnable() {

                        @Override
                        public void run() {
                            if (barcodes.valueAt(0).url != null) {
                                intentData = barcodes.valueAt(0).displayValue;
                                txtBarcodeValue.setText(intentData);




                                String url = getIntent().getStringExtra("keyurl");
                                Toast.makeText(Home.this,url,Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Home.this,Webview.class));
                                finish();


                            }
                        }
                    });
                }
                        }
                    });
                }

 @Override
    protected void onPause() {
     super.onPause();
     finish();
 }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();


    }


}