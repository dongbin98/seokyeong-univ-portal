package com.dbsh.skup.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dbsh.skup.R;
import com.dbsh.skup.user.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QrcodeActivity extends AppCompatActivity {

    Button QRBtn;
    ImageView QR_CODE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_form);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.qr_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        Intent intent = getIntent();
        String id = ((User) getApplication()).getId();
        String date = "0000000022";

        QRBtn = (Button) findViewById(R.id.QRBtn);
        QR_CODE = (ImageView)findViewById(R.id.QRcode);
        String data = ("").
                concat("7082").
                concat("\n").
                concat(id).
                concat("\n").
                concat("02").
                concat("\n").
                concat(date);
        //System.out.println(data);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(data, BarcodeFormat.QR_CODE,700,700);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            QR_CODE.setImageBitmap(bitmap);
        } catch (Exception e){}


        QRBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String data = ("").
                        concat("7082").
                        concat("\n").
                        concat(id).
                        concat("\n").
                        concat("02").
                        concat("\n").
                        concat(date);
                //System.out.println(data);

                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(data, BarcodeFormat.QR_CODE,700,700);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    QR_CODE.setImageBitmap(bitmap);
                }catch (Exception e){}
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
