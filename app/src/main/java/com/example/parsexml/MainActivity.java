package com.example.parsexml;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (TextView) findViewById(R.id.txt);
        parseXML();
    }



    private void parseXML() {

        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = getAssets().open("data.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            processParsing(parser);

        } catch (XmlPullParserException e) {

        } catch (IOException e) {
        }
    }
    private void processParsing(XmlPullParser parser) throws IOException, XmlPullParserException{
        ArrayList<Player> players = new ArrayList<>();
        int eventType = parser.getEventType();
        Player currentPlayer = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();

                    if ("player".equals(eltName)) {
                        currentPlayer = new Player();
                        players.add(currentPlayer);
                    } else if (currentPlayer != null) {
                        if ("name".equals(eltName)) {
                            currentPlayer.name = parser.nextText();
                        } else if ("age".equals(eltName)) {
                            currentPlayer.age = parser.nextText();
                        } else if ("position".equals(eltName)) {
                            currentPlayer.position = parser.nextText();
                        }
                    }
                    break;
            }

            eventType = parser.next();
        }

        printPlayers(players);
    }
    private void printPlayers(ArrayList<Player> players) {
        StringBuilder builder = new StringBuilder();

        for (Player player : players) {
            builder.append(player.name).append("\n").
                    append(player.age).append("\n").
                    append(player.position).append("\n\n");
        }

        txt.setText(builder.toString());
    }

    private List<Users> list = new ArrayList<>();
    private void readTXT(){

        InputStream i = getResources().openRawResource(R.raw.data);

        BufferedReader reader = new BufferedReader(new InputStreamReader(i, Charset.forName("UTF-8")));

        String line;

        try{

            while((line = reader.readLine()) != null){

                String [] attributes = line.split(";");
                Users user = new Users();
                user.setId(attributes[0]);
                user.setName(attributes[1]);
                user.setSurname(attributes[2]);
                list.add(user);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
