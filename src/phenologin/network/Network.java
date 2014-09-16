/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template in the editor.
 */
package phenologin.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author William A. Norman (LordCrekit@gmail.com, normanwi@msu.edu)
 */
public class Network {

    /*
     * ================================================ STATIC VARIABLES ================================================
     */
    private static final String api_url = "http://phenotype.venturit.net/api/v1";
    private static final String api_data = "email=savage@msu.edu&password=12345678"; // For quick debugging

    private static String jKey = "";

    /*
     * ================================================ MEMBER VARIABLES ================================================
     */

    /*
     * ================================================== CONSTRUCTORS ==================================================
     */
    /**
     * Default Constructor for Network.
     */
    public Network() {
        
    }

    /*
     * ================================================ PRIMARY FUNCTIONS ===============================================
     */
    /**
     *
     * @param username
     * @param password
     * @return
     *
     */
    public String login(String username, String password) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(api_url + "/sign_in.json");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes("email=" + username + "&password=" + password);
                wr.flush();
            } catch (Exception e) {
                return "";
            }

            InputStream is = connection.getInputStream();

            StringBuffer response;
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(is))) {
                String line;
                response = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
            }

            return response.toString();
        } catch (IOException e) {
            return "";
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    /**
     * Log off as the current user
     * @return boolean value: true if the logout was successful or false if the logout failed
     */
    public boolean logout() {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(api_url + "/sign_out.json");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes("auth_token=" + jKey);
                wr.flush();
            }
        } catch (IOException e) {
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return true;
    }

    public static String getExperiments(String authenticationToken) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(api_url + "/experiments.json?" + "auth_token=" + authenticationToken);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            InputStream is = connection.getInputStream();
            StringBuffer response;
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(is))) {
                String line;
                response = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
            }

            return response.toString();
        } catch (IOException e) {
            return "";
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static String getExperiment(String authenticationToken,int id) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(api_url + "/experiments/" + id + ".json?" + "auth_token=" + authenticationToken);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            InputStream is = connection.getInputStream();
            StringBuffer response;
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(is))) {
                String line;
                response = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
            }

            return response.toString();
        } catch (IOException e) {
            return "";
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /*
     * ================================================ GETTERS & SETTERS ===============================================
     */
    public void setKey(String key) {
        jKey = key;
    }

    /*
     * =============================================== MODIFIER FUNCTIONS ===============================================
     */

    /*
     * ================================================ VISUAL FUNCTIONS ================================================
     */

    /*
     * ================================================ PRIVATE FUNCTIONS ===============================================
     */
}
