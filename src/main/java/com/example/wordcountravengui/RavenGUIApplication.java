package com.example.wordcountravengui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.*;

/**
 * This is the main class for the Java FX application.
 */
public class RavenGUIApplication extends Application {

    public static DataOutputStream toServer = null;
    public static DataInputStream fromServer = null;
    DataInputStream inFromClient = null;
    DataOutputStream outToClient = null;
    public static Connection con = null;

    /**
     * This is the start function that loads the fxml file, creates a scene, sets the title, and shows the stage.
     * @param stage the start function accepts a blank stage to place the Java FX scene on
     * @throws IOException the function will throw an IOException if the fxml file can not be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RavenGUIApplication.class.getResource("raven-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 1200);
        stage.setTitle("Word Count of Edgar Allen Poe's Raven");
        stage.setScene(scene);
        stage.show();

        startServer();

        try {
            String url = "jdbc:mysql://localhost:3306/word_occurrences?allowPublicKeyRetrieval=true&useSSL=false";
            String username = "theboss";
            String password = "theboss123";

            con = DriverManager.getConnection(url, username, password);
            System.out.println("Connected");

            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM word");

        } catch (SQLException e) {

            System.out.println(e);
        }

        try {
            Socket socket = new Socket("localhost", 8000);

            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());


        } catch (IOException ex) {
            System.err.println(ex);
        }

    }

    public void startServer() {
        new Thread( () -> {
            try {
                ServerSocket serverSocket = new ServerSocket(8000);
//                Platform.runLater(() ->
//                        ta.appendText("Server started at " + new Date() + '\n')
//                );

                Socket socket = serverSocket.accept();

                inFromClient = new DataInputStream(socket.getInputStream());
                outToClient = new DataOutputStream(socket.getOutputStream());

                while (true) {
                    int thenumber = inFromClient.readInt();

                    if (thenumber == 1) {

                        //connect and save to the database
                        String sql = "INSERT INTO word(word, occurrences) VALUES(?, ?)";

                        Scanner scanner = new Scanner(new File("1065-h.htm")).useDelimiter("\b(?<![</])(?!>)[^.?!]+[.!?]");
                        Map<String, Integer> map = new HashMap<String, Integer>();
                        int titleIndex;
                        int endIndex;

                        while (scanner.hasNext()) {
                            String file;
                            file = scanner.next();
                            String[] lines = file.split("\\R");

                            titleIndex = Arrays.asList(lines).indexOf("<h1>The Raven</h1>");
                            endIndex = Arrays.asList(lines).indexOf("<span style=\"margin-left: 20%\">Shall be lifted&mdash;nevermore!</span>");

                            for (int i = titleIndex+1; i <= endIndex; i++) {
                                String stripped = lines[i].replaceAll("<[^>]*>", "");
                                stripped = stripped.replaceAll("(&mdash;)"," ");
                                stripped = stripped.replaceAll("('s)","");
                                String[] wordArray = stripped.split("[^a-zA-Z]+");

                                for (String word : wordArray) {
                                    if (word != "") {
                                        String wordLowerCase = word.toLowerCase();
                                        if (map.containsKey(wordLowerCase)) {
                                            map.put(wordLowerCase, map.get(wordLowerCase) + 1);
                                        } else {
                                            map.put(wordLowerCase, 1);
                                        }

                                        try {
                                            PreparedStatement pstq = con.prepareStatement("SELECT * FROM word WHERE word = '" + wordLowerCase + "'");
                                            ResultSet rs = pstq.executeQuery();

                                            if (!rs.next()) {

                                                PreparedStatement pst = con.prepareStatement(sql);
                                                pst.setString(1, wordLowerCase);
                                                pst.setInt(2,1);
                                                pst.executeUpdate();
                                            } else {
                                                int count = rs.getInt("occurrences");
                                                PreparedStatement pstu = con.prepareStatement("UPDATE word SET occurrences = ? WHERE word = ?");
                                                pstu.setInt(1,(count + 1));
                                                pstu.setString(2,wordLowerCase);
                                                pstu.executeUpdate();
                                            }
                                        } catch (SQLException ex) {
                                            System.out.println(ex);
                                        }

                                    }
                                }
                            }
                        }

                        //retrieve from the database and send to the client

                        try {
                            PreparedStatement pstprint = con.prepareStatement("SELECT * FROM word ORDER BY occurrences DESC");
                            ResultSet rsprint = pstprint.executeQuery();
                            int i = 1;
                            while(rsprint.next() && i <= 20) {
                                String word = rsprint.getString("word");
                                int occurrences = rsprint.getInt("occurrences");

                                outToClient.writeInt(i);
                                outToClient.writeUTF(word);
                                outToClient.writeUTF(String.valueOf(occurrences));

                                ++i;
                            }
                        } catch(SQLException ex) {
                            System.out.println(ex);
                        }


                    }

                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();

    }



    /**
     * The main function that launches the Java FX app.
     * @param args Strings passed into the main method
     */
    public static void main(String[] args) {
        launch();
    }
}
