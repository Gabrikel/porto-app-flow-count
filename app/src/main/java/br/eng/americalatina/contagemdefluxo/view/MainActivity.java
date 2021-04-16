package br.eng.americalatina.contagemdefluxo.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import br.eng.americalatina.contagemdefluxo.R;
import br.eng.americalatina.contagemdefluxo.api.AppDataBase;
import br.eng.americalatina.contagemdefluxo.api.AppUtil;
import br.eng.americalatina.contagemdefluxo.controller.PontoCritico1Controller;
import br.eng.americalatina.contagemdefluxo.model.PontoCritico;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
    PontoCritico pontoCritico;

    private int WRITE_REQUEST_CODE = 10001;
    private int READ_REQUEST_CODE = 10002;

    String pontoCritico1 = "pc01";
    String pontoCritico2 = "pc02";
    String pontoCritico3 = "pc03";
    String pontoCritico4 = "pc04";


    Button btnContPoint1, btnContPoint2, btnContPoint3, btnContPoint4;
    ImageView imageLogo;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFormulario();

        imageLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                    sendBkpEmail();
                }else {
                    askReadPermission();
                }
            }
        });


        btnContPoint1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pontoCritico.setCriticalPoint(pontoCritico1);
                salvarSharedPreferences();
                Intent intent =
                        new Intent(MainActivity.this, PontoCritico1Activity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        btnContPoint2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pontoCritico.setCriticalPoint(pontoCritico2);
                salvarSharedPreferences();
                Intent intent =
                        new Intent(MainActivity.this, PontoCritico2Activity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
        btnContPoint3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pontoCritico.setCriticalPoint(pontoCritico3);
                salvarSharedPreferences();
                Intent intent =
                        new Intent(MainActivity.this, PontoCritico3Activity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
        btnContPoint4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pontoCritico.setCriticalPoint(pontoCritico4);
                salvarSharedPreferences();
                Intent intent =
                        new Intent(MainActivity.this, PontoCritico4Activity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

    }


    private void initFormulario() {
        btnContPoint1 = findViewById(R.id.btnContPoint1);
        btnContPoint2 = findViewById(R.id.btnContPoint2);
        btnContPoint3 = findViewById(R.id.btnContPoint3);
        btnContPoint4 = findViewById(R.id.btnContPoint4);
        imageLogo = findViewById(R.id.imageLogo);
        restaurarSharedPreferences();
        pontoCritico = new PontoCritico();
    }

    private void salvarSharedPreferences() {
        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dados = preferences.edit();
        dados.putString("Ponto", pontoCritico.getCriticalPoint());
        dados.apply();
    }

    private void restaurarSharedPreferences() {
        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
    }

    private void sendBkpEmail (){
        try {
            String fileName = "/bkp_fluxoDB.sqlite";
            File bkp = new File(getFilesDir(), fileName);

            Uri uri = FileProvider.getUriForFile(MainActivity.this,
                    "br.eng.americalatina.contagemdefluxo.provider", bkp);

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"engenharia@americalatina.eng.br"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Contagem de Fluxo - Banco de Dados");
            intent.putExtra(Intent.EXTRA_STREAM, uri);

            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Selecione um aplicativo"));
        }catch (Exception e){
            Log.e("DB", "Erro: "+e.getMessage());
            Toast.makeText(getApplicationContext(), "Não foi possível realizar a solicitação!", Toast.LENGTH_LONG).show();
        }
    }

    private void askReadPermission(){
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Permissão para ler/escrever arquivos")
                    .setMessage("Você precisa permitir e tentar novamente!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    READ_REQUEST_CODE);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
    }





}
