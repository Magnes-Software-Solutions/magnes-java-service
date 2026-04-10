package software.magnes.solutions.alerts;

public class Alertas {

    private String tipo;
    private double valor;
    private String mensagem;
    private String timestamp;

    public Alertas(String tipo, double valor, String mensagem, String timestamp) {
        this.tipo = tipo;
        this.valor = valor;
        this.mensagem = mensagem;
        this.timestamp = timestamp;
    }

    public String getTipo() { return tipo; }
    public double getValor() { return valor; }
    public String getMensagem() { return mensagem; }
    public String getTimestamp() { return timestamp; }
}
