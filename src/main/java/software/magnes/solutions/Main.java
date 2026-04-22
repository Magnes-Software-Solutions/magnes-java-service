package software.magnes.solutions;

import org.json.JSONObject;
import software.magnes.solutions.jira.Jira;
import software.magnes.solutions.slack.Slack;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception, IOException, InterruptedException  {
        // Adicionar card no Jira
        String baseUrl = "https://magnessolutions.atlassian.net";
        String email = "magnessolutions@gmail.com";
        String apiToken = "";
        Jira jira = new Jira(baseUrl, email, apiToken);

        String mensagem = "ALERTA CRÍTICO!";

        String response = jira.createIssue(
                "KM", // Substitua pela key do seu projeto que estará presente na URL do seu site
                // ex: "https://java-integration.atlassian.net/jira/software/projects/SCRUM/boards/1/backlog" -> a parte "SCRUM" é a key desse projeto
                mensagem, // Nome da issue
                "Task" // Tipo da issue, pode ser "Task", "Bug", "Story", etc. Verifique os tipos disponíveis no seu projeto para usar o correto
        );

        System.out.println(response);
        // Se a requisição for bem-sucedida confira o backlog do seu projeto para ver a nova issue que foi criada




        // Mandar mensagem slack
        JSONObject json = new JSONObject();

        json.put("text", mensagem);

        Slack.sendMessage(json);
    }
}
