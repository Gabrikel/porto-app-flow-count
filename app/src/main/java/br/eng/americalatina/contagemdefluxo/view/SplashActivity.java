package br.eng.americalatina.contagemdefluxo.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import br.eng.americalatina.contagemdefluxo.R;
import br.eng.americalatina.contagemdefluxo.api.AppUtil;

public class SplashActivity extends AppCompatActivity {
    private int REQUEST_CODE = 10002;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        restaurarSharedPreferences();
        askPermission();


    }

    private void iniciarAplicativo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =
                        new Intent(SplashActivity.this,
                                MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        }, AppUtil.TIME_SPLASH);
    }

    private void salvarSharedPreferences() {
        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dados = preferences.edit();
    }

    private void restaurarSharedPreferences() {
        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
    }

    public void backupDatabase() {
        File sd;
        File data;
        File fileDatabase;
        File fileBkDatabase;


        FileChannel origem, destino;

        try {
            sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            data = Environment.getDataDirectory();

            Log.v("DB", "SD - " + sd.getAbsolutePath());
            Log.v("DB", "DATA - " + data.getAbsolutePath());

            if (sd.canWrite()) {
                String nameDatabase =
                        "//data//br.eng.americalatina.contagemdefluxo//databases/" + "fluxoDB.sqlite";
                String nameBkDatabase =
                        "bkp_" + "fluxoDB.sqlite";



                fileDatabase = new File(data, nameDatabase);
                fileBkDatabase = new File(getFilesDir(), nameBkDatabase);
                if (fileDatabase.exists()) {
                    origem = new FileInputStream(fileDatabase).getChannel();
                    destino = new FileOutputStream(fileBkDatabase).getChannel();

                    destino.transferFrom(origem, 0, origem.size());

                    origem.close();
                    destino.close();
                    Log.i(AppUtil.LOG_APP, "Backup Realizado com Sucesso");

                }else {
                    Toast.makeText(getApplicationContext(), "O arquivo de banco de dados não foi encontrado!", Toast.LENGTH_LONG).show();
                }
            }

        } catch (Exception e) {
            Log.e("DB", "Erro: " + e.getMessage());
        }
    }

    private void dialogPermission() {
        new AlertDialog.Builder(SplashActivity.this)
                .setTitle("Permissão para ler/escrever arquivos")
                .setMessage("Você precisa permitir para continuar!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(SplashActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_CODE);

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .create()
                .show();

    }



    public void askPermission() {
        if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(SplashActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                dialogPermission();
            } else {
                ActivityCompat.requestPermissions(SplashActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE);
                backupDatabase();
                iniciarAplicativo();
            }
        }else{
            backupDatabase();
            iniciarAplicativo();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                backupDatabase();
            }else{
                finish();
            }
        }
    }
}
