package software.magnes.solutions.jira;

public class MensagemJira {
    public static void main(String[] args) throws Exception {
        String baseUrl = "URL BASE";
        String email = "EMAIL";
        String apiToken = "TOKEN API";
        Jira jira = new Jira(baseUrl, email, apiToken);

        String response = jira.createIssue(
                "", // Substitua pela key do seu projeto que estará presente na URL do seu site
                // ex: "https://java-integration.atlassian.net/jira/software/projects/SCRUM/boards/1/backlog" -> a parte "SCRUM" é a key desse projeto
                "Issue criada via Java", // Nome da issue
                "Task" // Tipo da issue, pode ser "Task", "Bug", "Story", etc. Verifique os tipos disponíveis no seu projeto para usar o correto
        );

        System.out.println(response);
        // Se a requisição for bem-sucedida confira o backlog do seu projeto para ver a nova issue que foi criada
    }
}
