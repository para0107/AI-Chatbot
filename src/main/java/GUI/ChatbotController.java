package GUI;

import domain.ChatBot;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatbotController {
    @FXML private TextArea chatArea;
    @FXML private TextField userInput;

    private ChatBot chatbot = new ChatBot();

    @FXML
    private void sendMessage() {
        String question = userInput.getText().trim();
        if (!question.isEmpty()) {
            chatArea.appendText("You: " + question + "\n");
            String response = chatbot.getResponse(question);
            chatArea.appendText("Bot: " + response + "\n\n");
            userInput.clear();
        }
    }
}
