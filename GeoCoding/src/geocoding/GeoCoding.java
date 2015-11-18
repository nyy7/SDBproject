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
        String query1 = "select road,city from accident where id=1; ";
        ResultSet resultset = null;
        String address = null;

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
            Statement setupStatement = connection.createStatement();
            resultset = setupStatement.executeQuery(query1);
            resultset.next();
            address = resultset.getString(1) + ", " + resultset.getString(2);
            System.out.println(address);
            setupStatement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Query Failed! query:" + query1);
            e.printStackTrace();
            return;

        }

        // Google geocoding API
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            String url = "https://maps.googleapis.com/maps/api/geocode/json?address=";
            url += URLEncoder.encode(address, "UTF-8");
            url += "&key=" + URLEncoder.encode("AIzaSyAwweuAYTzMUZ73NeWxNSi-mHXOVZ2ju6M", "UTF-8");
            HttpGet get = new HttpGet(url);
            get.addHeader("Accept", "application/json");
            get.addHeader("Content-Type", "application/json");
            HttpResponse responsePost = client.execute(get);
            String jsonstring = EntityUtils.toString(responsePost.getEntity(), "UTF-8");
            System.out.println(jsonstring);

        } catch (IOException ex) {
            System.out.println("Http Request Error!");
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }

    }

}
