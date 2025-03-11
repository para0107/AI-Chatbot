package DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static final String url = "jdbc:mysql://localhost:3306/chatbot_db";
    private static final String USER = "root";
    private static final String PASSWORD = "costincnva2016";

    private Connection connection;
    public DatabaseHandler()
    {
        try {
             connection = DriverManager.getConnection(url, USER, PASSWORD);

        }
        catch (SQLException r)
        {
            r.printStackTrace();
        }
    }
    public void insertResponse(String question, String response)
    {
        String sql = "Insert into responses(question, response) values(?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, question);
            pstmt.setString(2, response);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public List<String> getAllQuestions() {
        List<String> questions = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT question FROM responses");
            while (rs.next()) {
                questions.add(rs.getString("question"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public List<String> getAllResponses() {
        List<String> responses = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT response FROM responses");
            while (rs.next()) {
                responses.add(rs.getString("response"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return responses;
    }

    public String getResponse(String question) {
        String sql = "SELECT response FROM responses WHERE question = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, question);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("response");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "I'm not sure about that. Can you teach me?";
    }
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
