package software.magnes.solutions;

import software.magnes.solutions.jira.Jira;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Jira jira = new Jira(
                "https://magnessolutions.atlassian.net",
                "magnessolutions@gmail.com",
                ""
        );

        S3Reader leitor = new S3Reader(
                "",
                "",
                "",
                ""
        );

        List<String[]> dados = leitor.ler("trusted/");
        System.out.println("Dados carregados: " + dados.size());

        if (dados.isEmpty()) {
            System.out.println("Nenhum dado encontrado.");
            return;
        }

        String[] cabecalho = dados.get(0);
        AlertaService alerta = new AlertaService(jira, cabecalho);

        for (int i = 1; i < dados.size(); i++) {
            alerta.emitir(dados.get(i));
        }
    }
}