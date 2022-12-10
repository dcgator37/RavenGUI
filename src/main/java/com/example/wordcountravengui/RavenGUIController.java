package com.example.wordcountravengui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * The controller class that handles input events for the Java FX app.
 */
public class RavenGUIController {

    @FXML
    private Label word1, word2, word3, word4, word5, word6, word7, word8, word9,word10, word11, word12, word13, word14, word15, word16, word17, word18, word19, word20;
    @FXML
    private Label count1, count2,count3,count4,count5,count6,count7,count8,count9,count10,count11,count12,count13,count14,count15,count16,count17,count18,count19,count20;

    /**
     * start button click event. This starts the Raven word count and prints the most found words to the labels.
     * @throws FileNotFoundException Throws exception of the htm file can not be found.
     */
    @FXML
    protected void onStartButtonClick() throws FileNotFoundException {
        String sql = "INSERT INTO word(word, occurrences) VALUES(?, ?)";
        Connection con = null;

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

        try {
            PreparedStatement pstprint = con.prepareStatement("SELECT * FROM word ORDER BY occurrences DESC");
            ResultSet rsprint = pstprint.executeQuery();
            int i = 1;
            while(rsprint.next() && i <= 20) {
                String word = rsprint.getString("word");
                int occurrences = rsprint.getInt("occurrences");
                System.out.println(word + " : " + occurrences);

                switch(i) {
                    case 1:
                        word1.setText(word);
                        count1.setText(String.valueOf(occurrences));
                        break;
                    case 2:
                        word2.setText(word);
                        count2.setText(String.valueOf(occurrences));
                        break;
                    case 3:
                        word3.setText(word);
                        count3.setText(String.valueOf(occurrences));
                        break;
                    case 4:
                        word4.setText(word);
                        count4.setText(String.valueOf(occurrences));
                        break;
                    case 5:
                        word5.setText(word);
                        count5.setText(String.valueOf(occurrences));
                        break;
                    case 6:
                        word6.setText(word);
                        count6.setText(String.valueOf(occurrences));
                        break;
                    case 7:
                        word7.setText(word);
                        count7.setText(String.valueOf(occurrences));
                        break;
                    case 8:
                        word8.setText(word);
                        count8.setText(String.valueOf(occurrences));
                        break;
                    case 9:
                        word9.setText(word);
                        count9.setText(String.valueOf(occurrences));
                        break;
                    case 10:
                        word10.setText(word);
                        count10.setText(String.valueOf(occurrences));
                        break;
                    case 11:
                        word11.setText(word);
                        count11.setText(String.valueOf(occurrences));
                        break;
                    case 12:
                        word12.setText(word);
                        count12.setText(String.valueOf(occurrences));
                        break;
                    case 13:
                        word13.setText(word);
                        count13.setText(String.valueOf(occurrences));
                        break;
                    case 14:
                        word14.setText(word);
                        count14.setText(String.valueOf(occurrences));
                        break;
                    case 15:
                        word15.setText(word);
                        count15.setText(String.valueOf(occurrences));
                        break;
                    case 16:
                        word16.setText(word);
                        count16.setText(String.valueOf(occurrences));
                        break;
                    case 17:
                        word17.setText(word);
                        count17.setText(String.valueOf(occurrences));
                        break;
                    case 18:
                        word18.setText(word);
                        count18.setText(String.valueOf(occurrences));
                        break;
                    case 19:
                        word19.setText(word);
                        count19.setText(String.valueOf(occurrences));
                        break;
                    case 20:
                        word20.setText(word);
                        count20.setText(String.valueOf(occurrences));
                        break;
                }

                ++i;
            }
        } catch(SQLException ex) {
            System.out.println(ex);
        }


        List<Map.Entry<String, Integer>> entries = new ArrayList<Entry<String,Integer>>( map.entrySet());

        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {

            @Override
            public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
                return a.getValue().compareTo(b.getValue());
            }
        });

//        word1.setText(entries.get(entries.size()-1).getKey());
//        count1.setText(entries.get(entries.size()-1).getValue().toString());
//        word2.setText(entries.get(entries.size()-2).getKey());
//        count2.setText(entries.get(entries.size()-2).getValue().toString());
//        word3.setText(entries.get(entries.size()-3).getKey());
//        count3.setText(entries.get(entries.size()-3).getValue().toString());
//        word4.setText(entries.get(entries.size()-4).getKey());
//        count4.setText(entries.get(entries.size()-4).getValue().toString());
//        word5.setText(entries.get(entries.size()-5).getKey());
//        count5.setText(entries.get(entries.size()-5).getValue().toString());
//        word6.setText(entries.get(entries.size()-6).getKey());
//        count6.setText(entries.get(entries.size()-6).getValue().toString());
//        word7.setText(entries.get(entries.size()-7).getKey());
//        count7.setText(entries.get(entries.size()-7).getValue().toString());
//        word8.setText(entries.get(entries.size()-8).getKey());
//        count8.setText(entries.get(entries.size()-8).getValue().toString());
//        word9.setText(entries.get(entries.size()-9).getKey());
//        count9.setText(entries.get(entries.size()-9).getValue().toString());
//        word10.setText(entries.get(entries.size()-10).getKey());
//        count10.setText(entries.get(entries.size()-10).getValue().toString());
//        word11.setText(entries.get(entries.size()-11).getKey());
//        count11.setText(entries.get(entries.size()-11).getValue().toString());
//        word12.setText(entries.get(entries.size()-12).getKey());
//        count12.setText(entries.get(entries.size()-12).getValue().toString());
//        word13.setText(entries.get(entries.size()-13).getKey());
//        count13.setText(entries.get(entries.size()-13).getValue().toString());
//        word14.setText(entries.get(entries.size()-14).getKey());
//        count14.setText(entries.get(entries.size()-14).getValue().toString());
//        word15.setText(entries.get(entries.size()-15).getKey());
//        count15.setText(entries.get(entries.size()-15).getValue().toString());
//        word16.setText(entries.get(entries.size()-16).getKey());
//        count16.setText(entries.get(entries.size()-16).getValue().toString());
//        word17.setText(entries.get(entries.size()-17).getKey());
//        count17.setText(entries.get(entries.size()-17).getValue().toString());
//        word18.setText(entries.get(entries.size()-18).getKey());
//        count18.setText(entries.get(entries.size()-18).getValue().toString());
//        word19.setText(entries.get(entries.size()-19).getKey());
//        count19.setText(entries.get(entries.size()-19).getValue().toString());
//        word20.setText(entries.get(entries.size()-20).getKey());
//        count20.setText(entries.get(entries.size()-20).getValue().toString());

    }

    /**
     * Clear button event. Clears the labels prior to running the word count again.
     */
    @FXML
    protected void onClearButtonClick() {

        word1.setText("");
        count1.setText("");
        word2.setText("");
        count2.setText("");
        word3.setText("");
        count3.setText("");
        word4.setText("");
        count4.setText("");
        word5.setText("");
        count5.setText("");
        word6.setText("");
        count6.setText("");
        word7.setText("");
        count7.setText("");
        word8.setText("");
        count8.setText("");
        word9.setText("");
        count9.setText("");
        word10.setText("");
        count10.setText("");
        word11.setText("");
        count11.setText("");
        word12.setText("");
        count12.setText("");
        word13.setText("");
        count13.setText("");
        word14.setText("");
        count14.setText("");
        word15.setText("");
        count15.setText("");
        word16.setText("");
        count16.setText("");
        word17.setText("");
        count17.setText("");
        word18.setText("");
        count18.setText("");
        word19.setText("");
        count19.setText("");
        word20.setText("");
        count20.setText("");

    }
}
