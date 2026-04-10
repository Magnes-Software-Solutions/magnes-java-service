package software.magnes.solutions.slack;

import java.io.IOException;
import org.json.JSONObject;


public class MensagemSlack {

    public static void main(String[] args) throws IOException, InterruptedException {

        JSONObject json = new JSONObject();

        json.put("text", "Problema reportado!");

        Slack.sendMessage(json);
    }
}
