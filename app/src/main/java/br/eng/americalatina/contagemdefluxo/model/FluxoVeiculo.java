package br.eng.americalatina.contagemdefluxo.model;

public class FluxoVeiculo extends PontoCritico{
    private String entrada;
    private String saida;
    private String hora;
    private String data;
    private boolean dadoValido = true;

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getSaida() {
        return saida;
    }

    public void setSaida(String saida) {
        this.saida = saida;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isDadoValido() {
        return dadoValido;
    }

    public void setDadoValido(boolean dadoValido) {
        this.dadoValido = dadoValido;
    }
}
