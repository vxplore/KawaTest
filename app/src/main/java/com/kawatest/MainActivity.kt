package com.kawatest

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.util.*

class MainActivity : AppCompatActivity(),InterfaceKawaEvents {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Kawa api key for initializing the Kawa map (Required)
        KawaMap.initKawaMap(getResources().getString(R.string.kawa_api_key));

// If you want to integrate the segment analytics tool, then you can initialize the segment analytics API key by using the below line (Optional)
        KawaMap.initSegment(kawaHomeActivity.this,
            getResources().getString(R.string.segment_api_key));

// If you want to integrate the smart look analytics tool, then you can initialize the smart look API key by using the below line (Optional)
        KawaMap.initSmartlook(getResources().getString(R.string.smartlook_api_key));

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(com.kawasdk.R.id.kawaMap, fragmentFarmLocation()).commit()
    }

    @Override
    public void initKawaMap(boolean isValid) {
        if (isValid == true) {
            KawaMap.setFooterBtnBgColorAndTextColor(Color.BLUE, Color.WHITE); //set
            large button colors
            KawaMap.setInnerBtnBgColorAndTextColor(Color.WHITE, Color.BLACK); //set
            small button colors
            KawaMap.setHeaderBgColorAndTextColor(Color.BLUE, Color.WHITE); //set
            header message box colors

                    // To enable the merge functionality (Optional)
                    // Merge function combines selected nearby farms
                    KawaMap.isMergeEnable = true;

            // By enabling
            isOtherFarmDetailsEnable, you can get the information of the area(acres), seeds
            required and address of selected farms(Optional)
            KawaMap.isOtherFarmDetailsEnable = false;
        } else {
            AlertDialog alertDialog = new
                    AlertDialog.Builder(kawaHomeActivity.this).create();
            alertDialog.setTitle(getResources().getString(com.kawasdk.R.string.app_name));
            alertDialog.setMessage("Kawa API key not found.");
            alertDialog.setIcon(com.kawasdk.R.mipmap.ic_launcher);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            alertDialog.show();
            // Toast.makeText(this, "KAWA api key not found.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onkawaSubmit(JSONObject data) {
        Toast.makeText(this, String.valueOf(data), Toast.LENGTH_LONG).show();
        Log.e(TAG, "SubmitJson: " + data);
    }

    @Override
    public void onkawaSelect(JsonObject data) {
        Toast.makeText(this, String.valueOf(data), Toast.LENGTH_LONG).show();
        Log.e(TAG, "SelectJson: " + data);
    }

    @Override
    public void onkawaUpdate(JSONObject data) {
        Log.e(TAG, String.valueOf(data));
    }

}