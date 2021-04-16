package br.eng.americalatina.contagemdefluxo.api;



import java.util.Calendar;

/**
 * Classe de apoio com métodos que podem ser reutilizados em todo o projeto
 */


public class AppUtil {

    public static final int TIME_SPLASH = 3 * 1000;
    public static final String PREF_APP = "app_cliente_pref";
    public static final String LOG_APP = "VEICULO_LOG";

    /**
     * @return devolve a data atual
     */

    public static String getDataAtual() {
        String dia, mes, ano;
        String dataAtual = "00/00/0000";
        try {
            Calendar calendar = Calendar.getInstance();
            dia = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            mes = String.valueOf(calendar.get(Calendar.MONTH) + 1);
            ano = String.valueOf(calendar.get(Calendar.YEAR));

            // p1 = p2 = p3
            dia = (calendar.get(Calendar.DAY_OF_MONTH)<10) ? "0" + dia : dia;
            int mesAtual = (calendar.get(Calendar.MONTH)) + 1;

            mes = (mesAtual<10) ? "0" + mes : mes;
            dataAtual = dia + "/" + mes + "/" + ano;
            return dataAtual;
        } catch (Exception e) {

        }
        return dataAtual;
    }


    /**
     * @return devolve a hora atual
     */
    public static String getHoraAtual() {
        String horaAtual = "00:00:00";
        String hora, min, seg;
        try {
            Calendar calendar = Calendar.getInstance();
            int iSeg = calendar.get(Calendar.SECOND);
            int iMin = calendar.get(Calendar.MINUTE);
            int iHora = calendar.get(Calendar.HOUR_OF_DAY);

            hora = (iHora <= 9) ? "0" + iHora : Integer.toString(iHora);
            min = (iMin <= 9) ? "0" + iMin : Integer.toString(iMin);
            seg = (iSeg <= 9) ? "0" + iSeg : Integer.toString(iSeg);

            horaAtual = hora + ":" + min + ":" + seg;
            return horaAtual;
        } catch (Exception e) {

        }

        return horaAtual;
    }

}
