package com.example.emanuel.reconocimientovoz;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //TextView grabar;
    private GridView gridView;
    private GridAdapter adapter;
    ImageButton button;
    ArrayList<String> conjunto = new ArrayList<>();

    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (ImageButton) findViewById(R.id.img_btn_hablar);

        gridView = (GridView) findViewById(R.id.gridViewText);
        adapter = new GridAdapter(this, conjunto);

        //grabar = (TextView) findViewById(R.id.txtGrabarVoz);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentActionRecognizeSpeech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intentActionRecognizeSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX");
                try {
                    startActivityForResult(intentActionRecognizeSpeech, RECOGNIZE_SPEECH_ACTIVITY);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(), "Tú dispositivo no soporta el reconocimiento por voz", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RECOGNIZE_SPEECH_ACTIVITY:

                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> speech = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String strSpeech2Text = speech.get(0);
                    conjunto.add(strSpeech2Text);
                    gridView.setAdapter(adapter);
                }

                break;

            default:
                break;
        }
    }
}
