package software.magnes.solutions;

import software.magnes.solutions.jira.Jira;
import software.magnes.solutions.slack.Slack;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AlertaService implements EmissorAlerta {

    private static final String BUCKET_URL = "https://bucket-csv-329272180750-us-east-1-an.s3.us-east-1.amazonaws.com/trusted/dadosTratados.csv";
    private Jira jira;
    private String[] cabecalho;

    public AlertaService(Jira jira, String[] cabecalho) {
        this.jira = jira;
        this.cabecalho = cabecalho;
    }

    @Override
    public void emitir(String[] linha) throws Exception {
        String servidor = "";
        double cpu = 0;
        double ram = 0;
        double disco = 0;

        for (int i = 0; i < cabecalho.length; i++) {
            String coluna = cabecalho[i].trim();
            String valor = linha[i].trim();

            if (coluna.equals("macAddress")) servidor = valor;
            if (coluna.equals("cpuPorcentagem")) cpu = Double.parseDouble(valor);
            if (coluna.equals("porcentagemRam")) ram = Double.parseDouble(valor);
            if (coluna.equals("porcentagemDisco")) disco = Double.parseDouble(valor);
        }

        if (cpu > 60) criarTicket(servidor, "CPU", cpu, 60);
        if (ram > 50) criarTicket(servidor, "RAM", ram, 60);
        if (disco > 55) criarTicket(servidor, "Disco", disco, 55);
    }

    private void criarTicket(String servidor, String componente, double valor, double limite) throws Exception {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String ticketId = componente.substring(0, 3).toUpperCase() + "-" + timestamp;
        String titulo = servidor + " - " + componente + " em " + valor + "%";

        jira.createIssue("KM", titulo, "Task");

        // JSONObject slackMsg = new JSONObject();
        // slackMsg.put("text", "ALERTA\n" + titulo);
        // Slack.sendMessage(slackMsg);

        JSONObject ticketJson = new JSONObject();
        ticketJson.put("ticketId", ticketId);
        ticketJson.put("servidor", servidor);
        ticketJson.put("componente", componente);
        ticketJson.put("valor", valor);
        ticketJson.put("status", "ABERTO");

        try {
            uploadJSON(ticketJson, ticketId);
        } catch (Exception e) {
            saveLocal(ticketJson, ticketId);
        }
    }

    private String uploadJSON(JSONObject json, String ticketId) throws IOException {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "tickets/" + ticketId + "_" + ts + ".json";
        String url = BUCKET_URL + "/" + fileName;

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("PUT");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        OutputStream os = conn.getOutputStream();
        os.write(json.toString(2).getBytes(StandardCharsets.UTF_8));
        os.close();

        if (conn.getResponseCode() != 200) {
            throw new IOException("Erro upload: " + conn.getResponseCode());
        }
        return url;
    }

    private String saveLocal(JSONObject json, String ticketId) throws IOException {
        File dir = new File("tickets");
        if (!dir.exists()) dir.mkdirs();

        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "tickets/" + ticketId + "_" + ts + ".json";

        FileWriter file = new FileWriter(fileName);
        file.write(json.toString(2));
        file.close();

        return fileName;
    }
}