package br.eng.americalatina.contagemdefluxo.view;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;

import br.eng.americalatina.contagemdefluxo.R;
import br.eng.americalatina.contagemdefluxo.api.AppUtil;
import br.eng.americalatina.contagemdefluxo.controller.PontoCritico1Controller;
import br.eng.americalatina.contagemdefluxo.controller.PontoCritico2Controller;
import br.eng.americalatina.contagemdefluxo.controller.PontoCritico3Controller;
import br.eng.americalatina.contagemdefluxo.controller.PontoCritico4Controller;
import br.eng.americalatina.contagemdefluxo.model.PontoCritico;
import br.eng.americalatina.contagemdefluxo.model.Veiculo;

public class QualVeiculoActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    private PontoCritico1Controller controller1;
    private PontoCritico2Controller controller2;
    private PontoCritico3Controller controller3;
    private PontoCritico4Controller controller4;

    PontoCritico pontoCritico;
    Veiculo veiculo;
    Button btnReturn, btnCarro, btnMoto, btnUtilitario, btnOnibus, btnCaminhao, btnCarreta, btnReboque;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qual_veiculo);
        initFormulario();
        Log.i(AppUtil.LOG_APP,pontoCritico.getCriticalPoint());
        btnCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veiculo.setTipo("Carro");
                goToMain();
            }
        });
        btnMoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veiculo.setTipo("Moto");
                goToMain();
            }
        });
        btnUtilitario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veiculo.setTipo("Utilitario");
                goToMain();
            }
        });
        btnOnibus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veiculo.setTipo("Onibus");
                goToMain();
            }
        });
        btnCaminhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veiculo.setTipo("Caminhao");
                goToMain();
            }
        });
        btnCarreta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veiculo.setTipo("Carreta");
                goToMain();
            }
        });
        btnReboque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veiculo.setTipo("Reboque");
                goToMain();
            }
        });
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(pontoCritico.getCriticalPoint().equals("pc01")){
                    Intent intent =
                        new Intent(QualVeiculoActivity.this, PontoCritico1Activity.class);
                    startActivity(intent);
                    finish();
                    return;

                }
                if(pontoCritico.getCriticalPoint().equals("pc02")){
                    Intent intent =
                        new Intent(QualVeiculoActivity.this, PontoCritico2Activity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                if(pontoCritico.getCriticalPoint().equals("pc03")) {
                    Intent intent =
                            new Intent(QualVeiculoActivity.this, PontoCritico3Activity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                if(pontoCritico.getCriticalPoint().equals("pc04")){
                    Intent intent =
                            new Intent(QualVeiculoActivity.this, PontoCritico4Activity.class);
                    startActivity(intent);
                    finish();
                    return;

                }else {
                    Intent intent =
                            new Intent(QualVeiculoActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }


            }
        });
    }

    private void countVeiculos() {
        try {
            if (pontoCritico.getCriticalPoint().equals("pc01")){controller1.incluir(veiculo);}
            if (pontoCritico.getCriticalPoint().equals("pc02")){controller2.incluir(veiculo);}
            if (pontoCritico.getCriticalPoint().equals("pc03")){controller3.incluir(veiculo);}
            if (pontoCritico.getCriticalPoint().equals("pc04")){controller4.incluir(veiculo);}

        }catch (Exception e){
            Log.e(AppUtil.LOG_APP, "Erro no count veiculos/: "+e.getMessage());
        }



        Log.i(AppUtil.LOG_APP, "#### DADOS VEICULOS ####");
        Log.i(AppUtil.LOG_APP, "VEICULO: "+veiculo.getTipo());
        Log.i(AppUtil.LOG_APP, "DE: "+veiculo.getEntrada());
        Log.i(AppUtil.LOG_APP, "PARA: "+veiculo.getSaida());
        Log.i(AppUtil.LOG_APP, "DATA: "+veiculo.getData());
        Log.i(AppUtil.LOG_APP, "HORA: "+veiculo.getHora());
        Log.i(AppUtil.LOG_APP, "#### -------------- ####");
    }

    private void goToMain() {
        countVeiculos();
        salvarSharedPreferences();
        String pc1 = "pc01";
        String pc2 = "pc02";
        String pc3 = "pc03";
        String pc4 = "pc04";

        Intent intent;

        if (pontoCritico.getCriticalPoint().equals(pc1)){
            intent =
                    new Intent(QualVeiculoActivity.this, PontoCritico1Activity.class);
            startActivity(intent);
        }
        if (pontoCritico.getCriticalPoint().equals(pc2)){
            intent =
                    new Intent(QualVeiculoActivity.this, PontoCritico2Activity.class);
            startActivity(intent);
        }
        if (pontoCritico.getCriticalPoint().equals(pc3)){
            intent =
                    new Intent(QualVeiculoActivity.this, PontoCritico3Activity.class);
            startActivity(intent);
        }
        if (pontoCritico.getCriticalPoint().equals(pc4)){
            intent =
                    new Intent(QualVeiculoActivity.this, PontoCritico4Activity.class);
            startActivity(intent);
        }else {
                finish();
        }
        finish();
        return;
    }

    private void initFormulario() {
        btnReturn = findViewById(R.id.btnReturn);
        btnCarro = findViewById(R.id.btnCarro);
        btnMoto = findViewById(R.id.btnMoto);
        btnUtilitario = findViewById(R.id.btnUtilitario);
        btnOnibus = findViewById(R.id.btnOnibus);
        btnCaminhao = findViewById(R.id.btnCaminhao);
        btnCarreta = findViewById(R.id.btnCarreta);
        btnReboque = findViewById(R.id.btnReboque);



        controller1 = new PontoCritico1Controller(getApplicationContext());
        controller2 = new PontoCritico2Controller(getApplicationContext());
        controller3 = new PontoCritico3Controller(getApplicationContext());
        controller4 = new PontoCritico4Controller(getApplicationContext());

        veiculo = new Veiculo();
        pontoCritico = new PontoCritico();
        restaurarSharedPreferences();
    }


    private void salvarSharedPreferences() {
        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dados = preferences.edit();
        dados.putString("Veiculo", veiculo.getTipo());
        dados.apply();
    }

    private void restaurarSharedPreferences() {
        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        veiculo.setEntrada(preferences.getString("Entrada", null));
        veiculo.setSaida(preferences.getString("Saída", null));
        veiculo.setData(preferences.getString("Data", null));
        veiculo.setHora(preferences.getString("Hora", null));
        pontoCritico.setCriticalPoint(preferences.getString("Ponto", null));
    }

}
