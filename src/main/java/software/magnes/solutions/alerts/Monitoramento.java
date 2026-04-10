package software.magnes.solutions.alerts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Monitoramento {

    public static void iniciar() {

        Random random = new Random();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        System.out.println("INICIALIZANDO MONITORAMENTO DOS LOGS...");

        while (true) {
            String agora = dtf.format(LocalDateTime.now());
            int componente = random.nextInt(3);
            double valorSimulado = random.nextDouble() * 100;
            String valorFormatado = String.format("%.2f", valorSimulado);

            String tipoComponente;
            String status;
            String mensagem;

            if (componente == 0) tipoComponente = "RAM";
            else if (componente == 1) tipoComponente = "CPU";
            else tipoComponente = "DISCO";

            if (valorSimulado > 90) {
                status = "[ERRO CRÍTICO]";
                mensagem = "Capacidade máxima atingida! Risco de sobrecarga.";
            } else if (valorSimulado > 70) {
                status = "[ALERTA]";
                mensagem = "Carga elevada detectada. Monitorize o processo.";
            } else {
                status = "[INFO]";
                mensagem = "Sistema a operar em níveis normais.";
            }

            System.out.println("[" + agora + "] " + status + " - " + tipoComponente + ": " + valorFormatado + "% - " + mensagem);
            if (valorSimulado > 70) {

                Alertas alert = new Alertas(
                        tipoComponente,
                        valorSimulado,
                        mensagem,
                        agora
                );

                AlertService.addAlertas(alert);
            }
            try {
                int intervalo = random.nextInt(10) + 1;
                TimeUnit.SECONDS.sleep(intervalo);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}