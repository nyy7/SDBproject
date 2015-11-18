/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geocoding;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author yanglingou
 */
public class GeoCoding {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Database Parameters
        String host = "accident.cb3jzndjw4nj.us-west-2.rds.amazonaws.com";
        String port = "5432";
        String dbname = "accident";
        String username = "root";
        String password = "Cs6604GIS";

        // query sentences
        // Connect to the database on Amazon Cloud
        try {
            System.out.println("Loading driver...");
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find the driver in the classpath!", e);
        }

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://" + host + ":" + port + "/" + dbname,
                    username,
                    password);

            if (connection != null) {
                System.out.println("You made it, take control your database now!");
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        }

        // Excute query
        try {
            String query1 = "select id, road,city from accident13 where geocenter is null;";
            ResultSet resultset = null;
            String address = null;

            Statement setupStatement = connection.createStatement();
            resultset = setupStatement.executeQuery(query1);

            while (resultset.next()) {
                address = resultset.getString(2) + ", " + resultset.getString(3);
                
                try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
                    String url = "https://maps.googleapis.com/maps/api/geocode/json?address=";
                    url += URLEncoder.encode(address, "UTF-8");
                    url += "&components=" + URLEncoder.encode("route", "UTF-8");
                    url += "&key=" + URLEncoder.encode("AIzaSyBO8ALsTiK5_4rvejOHePz4OoqF_lCrfCw", "UTF-8");
                    HttpGet get = new HttpGet(url);
                    get.addHeader("Accept", "application/json");
                    get.addHeader("Content-Type", "application/json");
                    HttpResponse responsePost = client.execute(get);
                    String jsonstring = EntityUtils.toString(responsePost.getEntity(), "UTF-8");
                    System.out.println(address);
                    

                    try {
                        JSONParser jparser = new JSONParser();
                        JSONObject response_jo = (JSONObject) jparser.parse(jsonstring);
                        if (new String(response_jo.get("status").toString()).equals("OK")){
                        JSONArray results_ja = (JSONArray) response_jo.get("results");
                        
                        JSONObject jo2 = (JSONObject) results_ja.get(0);
                        JSONObject geometry_jo = (JSONObject) jo2.get("geometry");
                        JSONObject location_jo = (JSONObject) geometry_jo.get("location");
                        String lat = location_jo.get("lat").toString();
                        String lng = location_jo.get("lng").toString();
                        
                        String query2="update accident13 set geocenter=point("+lng+","+lat+") where id="+resultset.getString(1)+";";
                        Statement setupStatement2 = connection.createStatement();
                        setupStatement2.executeUpdate(query2);
                        setupStatement2.close();
                        } else {
                            System.out.println("id="+resultset.getString(1));
                            System.out.println(response_jo.get("status").toString());
                        }
                        
                            
                        
                

                    } catch (ParseException ep) {
                        System.err.println(address);
                        System.err.println(ep.toString());
                    }

                } catch (IOException ex) {
                    System.err.println(address);
                    System.out.println("Http Request Error!");
                    System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
                }

            };

            setupStatement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("questwrong");
            e.printStackTrace();
            return;

        }

        // Google geocoding API
    }

}
