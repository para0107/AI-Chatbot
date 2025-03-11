module AiChatBot {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.apache.opennlp.tools;
    requires org.jsoup;
    opens GUI to javafx.fxml;
    exports GUI;
    exports domain;
}