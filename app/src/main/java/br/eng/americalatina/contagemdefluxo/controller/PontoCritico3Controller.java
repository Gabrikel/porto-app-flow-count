package br.eng.americalatina.contagemdefluxo.controller;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;

import java.util.List;

import br.eng.americalatina.contagemdefluxo.api.AppDataBase;
import br.eng.americalatina.contagemdefluxo.datamodel.PontoCritico3DataModel;
import br.eng.americalatina.contagemdefluxo.model.Veiculo;

public class PontoCritico3Controller extends AppDataBase {

    private static final String TABELA = PontoCritico3DataModel.TABELA;
    private ContentValues dados;

    public PontoCritico3Controller(@Nullable Context context) {
        super(context);
    }

    public boolean incluir(Veiculo veiculo){

        dados = new ContentValues();

        dados.put(PontoCritico3DataModel.VEICULO, veiculo.getTipo());
        dados.put(PontoCritico3DataModel.ENTRADA, veiculo.getEntrada());
        dados.put(PontoCritico3DataModel.SAIDA, veiculo.getSaida());
        dados.put(PontoCritico3DataModel.DATA, veiculo.getData());
        dados.put(PontoCritico3DataModel.HORA, veiculo.getHora());
        dados.put(PontoCritico3DataModel.DADO_VALIDO, veiculo.isDadoValido());

        return insert(TABELA, dados);
    }

    public boolean alterar(Veiculo veiculo){
        dados = new ContentValues();

        dados.put(PontoCritico3DataModel.ID, veiculo.getId());
        dados.put(PontoCritico3DataModel.VEICULO, veiculo.getTipo());
        dados.put(PontoCritico3DataModel.ENTRADA, veiculo.getEntrada());
        dados.put(PontoCritico3DataModel.SAIDA, veiculo.getSaida());
        dados.put(PontoCritico3DataModel.DATA, veiculo.getData());
        dados.put(PontoCritico3DataModel.HORA, veiculo.getHora());
        dados.put(PontoCritico3DataModel.DADO_VALIDO, veiculo.isDadoValido());


        return update(TABELA, dados);
    }

    public boolean deletar(Veiculo veiculo){

        return delete(TABELA, veiculo.getId());
    }

    public List<Veiculo> listar(){
        return list(TABELA);
    }

    public int getLastId(){
        return getLastPK(TABELA);
    }
}
