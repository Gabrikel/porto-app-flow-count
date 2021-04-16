package br.eng.americalatina.contagemdefluxo.api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import br.eng.americalatina.contagemdefluxo.datamodel.PontoCritico1DataModel;
import br.eng.americalatina.contagemdefluxo.datamodel.PontoCritico2DataModel;
import br.eng.americalatina.contagemdefluxo.datamodel.PontoCritico3DataModel;
import br.eng.americalatina.contagemdefluxo.datamodel.PontoCritico4DataModel;
import br.eng.americalatina.contagemdefluxo.model.Veiculo;
import br.eng.americalatina.contagemdefluxo.view.MainActivity;

public class AppDataBase extends SQLiteOpenHelper {

    public static final String DB_NAME = "fluxoDB.sqlite";
    public static final int DB_VERSION = 1;


    Cursor cursor;

    SQLiteDatabase db;

    public AppDataBase(@Nullable Context context) {
        super(context, DB_NAME,null, DB_VERSION);
        db = getWritableDatabase();
    }


    @Override
    public void onConfigure (SQLiteDatabase db){
        db.disableWriteAheadLogging();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            //Criar Tabelas

            try {

                db.execSQL(PontoCritico1DataModel.generateTable());
                db.execSQL(PontoCritico2DataModel.generateTable());
                db.execSQL(PontoCritico3DataModel.generateTable());
                db.execSQL(PontoCritico4DataModel.generateTable());
                Log.i(AppUtil.LOG_APP, "TB Fluxo: " + PontoCritico1DataModel.generateTable());

            } catch (SQLException e) {
                Log.e(AppUtil.LOG_APP, "Erro TB Fluxo: " + e.getMessage());
            }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }


    public boolean insert(String tabela, ContentValues dados) {
        boolean sucess = true;
        try {
            Log.i(AppUtil.LOG_APP, tabela + "Insert() excuted successfully. ");
            sucess = db.insert(tabela, null, dados) > 0;
        } catch (SQLException e) {
            Log.e(AppUtil.LOG_APP, tabela + "Fail on execute insert(): " + e.getMessage());
        }
        return sucess;
    }

    public boolean delete(String tabela, int id) {

        boolean sucess = true;
        try {
            Log.i(AppUtil.LOG_APP, tabela + "Delete() excuted successfully. ");
            sucess = db.delete(tabela, "id=?", new String[]{Integer.toString(id)}) > 0;
        } catch (SQLException e) {
            Log.e(AppUtil.LOG_APP, tabela + "Fail on execute delete(): " + e.getMessage());
        }
        return sucess;
    }

    public boolean update(String tabela, ContentValues dados) {

        boolean sucess = true;
        try {
            int id = dados.getAsInteger("id");
            Log.i(AppUtil.LOG_APP, tabela + "Update() excuted successfully. ");
            sucess = db.update(tabela, dados, "id=?", new String[]{Integer.toString(id)}) > 0;
        } catch (SQLException e) {
            Log.e(AppUtil.LOG_APP, tabela + "Fail on execute update(): " + e.getMessage());
        }
        return sucess;
    }

    public List<Veiculo> list(String tabela) {

        List<Veiculo> veiculos = new ArrayList<>();
        Veiculo veiculo;

        String sql = "SELECT * FROM " + tabela;
        try {
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                    veiculo = new Veiculo();
                    veiculo.setId(cursor.getInt(cursor.getColumnIndex(PontoCritico1DataModel.ID)));
                    veiculo.setTipo(cursor.getString(cursor.getColumnIndex(PontoCritico1DataModel.VEICULO)));
                    veiculo.setEntrada(cursor.getString(cursor.getColumnIndex(PontoCritico1DataModel.ENTRADA)));
                    veiculo.setSaida(cursor.getString(cursor.getColumnIndex(PontoCritico1DataModel.SAIDA)));
                    veiculo.setHora(cursor.getString(cursor.getColumnIndex(PontoCritico1DataModel.HORA)));
                    veiculo.setData(cursor.getString(cursor.getColumnIndex(PontoCritico1DataModel.DATA)));
                    veiculo.setDadoValido(cursor.getInt(cursor.getColumnIndex(PontoCritico1DataModel.DADO_VALIDO))==1);

                    veiculos.add(veiculo);

                } while (cursor.moveToNext());
                Log.i(AppUtil.LOG_APP, tabela + " lista gerada com sucesso.");
            }

        } catch (SQLException e) {
            Log.e(AppUtil.LOG_APP, "Erro ao listar os dados: " + tabela);
            Log.e(AppUtil.LOG_APP, "Erro: " + e.getMessage());
        }
        return veiculos;
    }

    public int getLastPK(String tabela) {

        List<Veiculo> veiculos = new ArrayList<>();
        Veiculo veiculo;

        String sql = "SELECT seq FROM sqlite_sequence WHERE name =  '" + tabela + "'";
        try {
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                    return cursor.getInt(cursor.getColumnIndex("seq"));

                } while (cursor.moveToNext());
            }

        } catch (SQLException e) {
            Log.e(AppUtil.LOG_APP, "Erro recuparenado o ultimo PK " + tabela);
            Log.e(AppUtil.LOG_APP, "Erro: " + e.getMessage());
        }
        return -1;
    }



}
