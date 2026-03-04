package software.magnes.solutions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Chamado {
    Maquina maquina;


    public Chamado(Maquina maquina) {
        this.maquina = maquina;
    }

    public void verificarRazaoChamado() {
        if (this.maquina.gargaloCpu) {
            System.out.println("CPU: " + maquina.percentualCpu + "%");
        }
        if (this.maquina.gargaloDisco) {
            System.out.println("Disco: " + maquina.percentualDisco + "%");
        }
        if (this.maquina.gargaloMemoria) {
            System.out.println("Memória: " + maquina.percentualMemoria + "%");
        }
    }

    public void emitirAlerta() {
        System.out.println("\n!===============================================!");
        System.out.println("ALERTA ENVIADO AO SLACK!");
        System.out.println("Numeração Máquina: " + maquina.numeracaoMaquina);
        LocalDateTime momento = LocalDateTime.now();
        DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataHora = momento.format(formatacao);
        System.out.println("Momento: " + dataHora);
        System.out.println("\nPARÂMETROS FORA DO IDEAL:");
        verificarRazaoChamado();
    }
}
