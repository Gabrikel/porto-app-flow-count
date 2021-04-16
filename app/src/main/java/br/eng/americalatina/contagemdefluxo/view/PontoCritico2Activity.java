package br.eng.americalatina.contagemdefluxo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.eng.americalatina.contagemdefluxo.R;
import br.eng.americalatina.contagemdefluxo.api.AppUtil;
import br.eng.americalatina.contagemdefluxo.controller.PontoCritico2Controller;
import br.eng.americalatina.contagemdefluxo.model.PontoCritico;
import br.eng.americalatina.contagemdefluxo.model.Veiculo;
import ir.androidexception.andexalertdialog.AndExAlertDialog;
import ir.androidexception.andexalertdialog.AndExAlertDialogListener;

public class PontoCritico2Activity extends AppCompatActivity {
    private SharedPreferences preferences;
    PontoCritico pontoCritico;
    public Veiculo veiculo;

    PontoCritico2Controller controller;
    int lastId;

    Button btn01, btn02, btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ponto_critico2);

        initFormulario();

        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonWait(btn01);
            }
        });
        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonWait(btn02);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLast();
            }
        });
    }



    private void initFormulario() {
        btn01 = findViewById(R.id.btn01);
        btn02 = findViewById(R.id.btn02);
        btnDelete = findViewById(R.id.btnDelete);

        controller = new PontoCritico2Controller(getApplicationContext());

        veiculo = new Veiculo();
        pontoCritico = new PontoCritico();

        restaurarSharedPreferences();

    }

    private void goToChooseCar() {
        salvarSharedPreferences();
        Intent intent =
                new Intent(PontoCritico2Activity.this, QualVeiculoActivity.class);
        startActivity(intent);
        finish();
        return;
    }

    private void exitButton() {
        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veiculo.setSaida("Sentido Praia das Neves");
                goToChooseCar();
            }
        });
        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veiculo.setSaida("Sentido ES-060");
                goToChooseCar();
            }
        });

    }


    private void buttonWait(Button btnGeneric) {
        if (btnGeneric == btn01) {
            veiculo.setEntrada("Sentido Praia das Neves");
            veiculo.setData(AppUtil.getDataAtual());
            veiculo.setHora(AppUtil.getHoraAtual());
            exitButton();
        }
        if (btnGeneric == btn02) {
            veiculo.setEntrada("Sentido ES-060");
            veiculo.setData(AppUtil.getDataAtual());
            veiculo.setHora(AppUtil.getHoraAtual());
            exitButton();
        }
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLast();
            }
        });
    }

    private void deleteLast() {
        new AndExAlertDialog.Builder(PontoCritico2Activity.this)
                .setTitle("DELETAR")
                .setMessage("Deseja excluir o último registro?")
                .setPositiveBtnText("Confirmar")
                .setNegativeBtnText("Cancelar")
                .setCancelableOnTouchOutside(true)
                .OnPositiveClicked(
                        new AndExAlertDialogListener() {
                            @Override
                            public void OnClick(String input) {
                                Toast.makeText(getApplicationContext(), "Último registro excluído...", Toast.LENGTH_LONG).show();
                                lastId = controller.getLastId();
                                veiculo.setDadoValido(false);
                                veiculo.setId(lastId);
                                if(pontoCritico.getCriticalPoint().equals("pc02")){
                                    controller.alterar(veiculo);}
                            }
                        }
                )
                .OnNegativeClicked(
                        new AndExAlertDialogListener() {
                            @Override
                            public void OnClick(String input) {
                                Toast.makeText(getApplicationContext(), "Último registro mantido...", Toast.LENGTH_LONG).show();
                            }
                        })

                .build();
    }

    private void salvarSharedPreferences() {
        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dados = preferences.edit();
        dados.putString("Entrada", veiculo.getEntrada());
        dados.putString("Saída", veiculo.getSaida());
        dados.putString("Data", veiculo.getData());
        dados.putString("Hora", veiculo.getHora());
        dados.putBoolean("DadoValido", veiculo.isDadoValido());
        dados.putString("Ponto", pontoCritico.getCriticalPoint());
        dados.apply();
    }

    private void restaurarSharedPreferences() {
        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        pontoCritico.setCriticalPoint(preferences.getString("Ponto", null));
        veiculo.setEntrada(preferences.getString("Entrada", null));
        veiculo.setSaida(preferences.getString("Saída", null));
        veiculo.setData(preferences.getString("Data", null));
        veiculo.setHora(preferences.getString("Hora", null));
        veiculo.setTipo(preferences.getString("Veiculo", null));
    }

}
