package br.eng.americalatina.contagemdefluxo.datamodel;

public class PontoCritico1DataModel {
    /**
     *     Classe que cria a tabela no SQLite
     */
    public static final String TABELA = "critical1";
    public static final String ID = "id";
    public static final String VEICULO = "veiculo";
    public static final String ENTRADA = "entrada";
    public static final String SAIDA = "saida";
    public static final String DATA = "data";
    public static final String HORA = "hora";
    public static final String DADO_VALIDO = "dadoValido";
    private static final String DATA_INC = "datainc";
    private static final String DATA_ALT = "dataalt";

    private static String query;

    public static String generateTable(){

        query = "CREATE TABLE "+TABELA+" ( ";
        query += ID+" INTEGER PRIMARY KEY AUTOINCREMENT, ";
        query += VEICULO+" TEXT, ";
        query += ENTRADA+" TEXT, ";
        query += SAIDA+" TEXT, ";
        query += DATA+" TEXT, ";
        query += HORA+" TEXT, ";
        query += DADO_VALIDO+" INTEGER, ";
        query += DATA_INC+" datetime default current_timestamp, ";
        query += DATA_ALT+" datetime default current_timestamp ";
        query += ")";

        return query;
    }

}
