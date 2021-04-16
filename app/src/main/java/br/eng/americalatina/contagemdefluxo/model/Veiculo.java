package br.eng.americalatina.contagemdefluxo.model;

public class Veiculo extends FluxoVeiculo{
    private int id;
    private String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

