package com.example.wordcountravengui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.*;
import java.sql.SQLException;
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

        try {

            RavenGUIApplication.toServer.writeInt(1);
            RavenGUIApplication.toServer.flush();

        } catch (IOException ex) {
            System.err.println(ex);
        }

        int j = 0;
        String word;
        String occurrences;

        for (int i = 1; i <= 20; i++) {
            try {
                j = RavenGUIApplication.fromServer.readInt();
                word = RavenGUIApplication.fromServer.readUTF();
                occurrences = RavenGUIApplication.fromServer.readUTF();

                switch(j) {
                    case 1:
                        word1.setText(word);
                        count1.setText(occurrences);
                        break;
                    case 2:
                        word2.setText(word);
                        count2.setText(occurrences);
                        break;
                    case 3:
                        word3.setText(word);
                        count3.setText(occurrences);
                        break;
                    case 4:
                        word4.setText(word);
                        count4.setText(occurrences);
                        break;
                    case 5:
                        word5.setText(word);
                        count5.setText(occurrences);
                        break;
                    case 6:
                        word6.setText(word);
                        count6.setText(occurrences);
                        break;
                    case 7:
                        word7.setText(word);
                        count7.setText(occurrences);
                        break;
                    case 8:
                        word8.setText(word);
                        count8.setText(occurrences);
                        break;
                    case 9:
                        word9.setText(word);
                        count9.setText(occurrences);
                        break;
                    case 10:
                        word10.setText(word);
                        count10.setText(occurrences);
                        break;
                    case 11:
                        word11.setText(word);
                        count11.setText(occurrences);
                        break;
                    case 12:
                        word12.setText(word);
                        count12.setText(occurrences);
                        break;
                    case 13:
                        word13.setText(word);
                        count13.setText(occurrences);
                        break;
                    case 14:
                        word14.setText(word);
                        count14.setText(occurrences);
                        break;
                    case 15:
                        word15.setText(word);
                        count15.setText(occurrences);
                        break;
                    case 16:
                        word16.setText(word);
                        count16.setText(occurrences);
                        break;
                    case 17:
                        word17.setText(word);
                        count17.setText(occurrences);
                        break;
                    case 18:
                        word18.setText(word);
                        count18.setText(occurrences);
                        break;
                    case 19:
                        word19.setText(word);
                        count19.setText(occurrences);
                        break;
                    case 20:
                        word20.setText(word);
                        count20.setText(occurrences);
                        break;
                }

            } catch(IOException ex) {
                System.err.println(ex);
            }

        }

        //this is where all the database stuff was

//        List<Map.Entry<String, Integer>> entries = new ArrayList<Entry<String,Integer>>( map.entrySet());
//
//        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
//
//            @Override
//            public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
//                return a.getValue().compareTo(b.getValue());
//            }
//        });


    }

    /**
     * Clear button event. Clears the labels prior to running the word count again.
     */
    @FXML
    protected void onClearButtonClick() {

        try {
            Statement stmt = RavenGUIApplication.con.createStatement();
            stmt.executeUpdate("DELETE FROM word");

        } catch (SQLException e) {

            System.out.println(e);
        }

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
