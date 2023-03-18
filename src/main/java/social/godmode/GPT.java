package social.godmode;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GPT {
    private String apiKey;
    public GPT(String apiKey) {
        this.apiKey = apiKey;
    }

    public String generateResponse(String prompt) {
        try {
            JSONObject body = new JSONObject();
            body.put("model", "gpt-3.5-turbo");
            body.put("temperature", 1);
            body.put("max_tokens", 500);
            JSONArray messages = new JSONArray();
            JSONObject systemMessage = new JSONObject();
            systemMessage.put("role", "system");
            systemMessage.put("content", "You are a bot which responds to hateful comments in a harsh way to make a comeback.");
            messages.put(systemMessage);
            JSONObject messageObj = new JSONObject();
            messageObj.put("role", "user");
            messageObj.put("content", prompt);
            messages.put(messageObj);
            body.put("messages", messages);
            System.out.println(body);
            URL chatGPT = new URL("https://api.openai.com/v1/chat/completions");
            HttpURLConnection connection = (HttpURLConnection) chatGPT.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = body.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            System.out.println(connection.getResponseCode());
            System.out.println(connection.getResponseMessage());
            JSONObject response = new JSONObject(new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8));
            JSONObject choices = response.getJSONArray("choices").getJSONObject(0);
            return choices.getJSONObject("message").getString("content");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
