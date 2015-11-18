package com.yanglingou.accident;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nyy on 11/13/15.
 */
public class Top3Info_XMLParser {
    public static List<Top3Info> parseFeed(String content){
        try {

            boolean inDataItemTag = false;
            String currentTagName = "";
            Top3Info top3Info = null;
            List<Top3Info> top3Infos = new ArrayList<Top3Info>();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(content));

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        currentTagName = parser.getName();
                        if (currentTagName.equals("Accident")) {
                            inDataItemTag = true;
                            top3Info = new Top3Info();
                            top3Infos.add(top3Info);
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("/Accident")) {
                            inDataItemTag = false;
                        }
                        currentTagName = "";
                        break;

                    case XmlPullParser.TEXT:
                        if (inDataItemTag && top3Info != null) {
                            switch(currentTagName) {
                                case "ID":
                                    top3Info.set_caseId(Integer.parseInt(parser.getText()));
                                    break;
                                case "ROAD":
                                    top3Info.set_road(parser.getText());
                                    break;
                                case "CITY":
                                    String city_state = parser.getText();
                                    String[] cs = city_state.split(",");
                                    top3Info.set_city(cs[0]);
                                    top3Info.set_state(cs[1]);
                                    break;
                                case "LINK":
                                    top3Info.set_link(parser.getText());
                                    break;
                                case "SIMILARITY":
                                    top3Info.set_similarity(Double.parseDouble(parser.getText()));
                                    break;
                                default:
                                    break;
                            }
                        }
                        break;
                }

                eventType = parser.next();

            }

            return top3Infos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
