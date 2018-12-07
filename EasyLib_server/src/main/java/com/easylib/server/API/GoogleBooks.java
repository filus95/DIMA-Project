package com.easylib.server.API;

import com.easylib.server.Database.DatabaseManager;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GoogleBooks {

    private static final String KEY_ = "AIzaSyBTjCC4Lcz1SaIfEpLEhuUj6s9uKioINtA";
    private static final String ENDPOINT_ = "https://www.googleapis.com/books/v1/volumes?q=";


    //TODO: this fill directly the db, useful?
    /**
     * It needs to be called outside this class, from the
     *
     * @param query
     * @param tableName
     */

    public void apiCallAndFillDB(String query, String tableName, String schema_name){
        //at this url we found a JSON file containing all the articles  of the topic " q=bitcoin " published today "from = date"
        String endpoint = ENDPOINT_+query+"&key="+KEY_;
        String result1 = "";


        //initializing the connection
        URL url;
        HttpURLConnection urlConnection = null;

        // Retrieve the API response and parse it
        try {
            url = new URL(endpoint);
            urlConnection = (HttpURLConnection) url.openConnection();
            String response = streamToString(urlConnection.getInputStream());
            try {
                JSONObject response_json = new JSONObject(response);

                parseResult(response_json, tableName, schema_name);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }/**

    //TODO:CODE TO HAVE A RETURN FROM THE API, SEND IT TO THE CLIENT AND LATER DECIDE IF FILLIND THE DB CALLINF THE DBMS

    private Map<String, Object> apiCall(String query){
        String endpoint = ENDPOINT_+query+"&key="+KEY_;
        String result1 = "";


        //initializing the connection
        URL url;
        HttpURLConnection urlConnection = null;

        // Retrieve the API response and parse it
        try {
            url = new URL(endpoint);
            urlConnection = (HttpURLConnection) url.openConnection();
            String response = streamToString(urlConnection.getInputStream());
            try {
                JSONObject response_json = new JSONObject(response);

                parseResult(response_json, tableName);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }**/

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // TODO: METHOD TO CREATE SUITED QUERY FOR THE GOOGLE BOOKS EXTERNAL SERVICE
    // THE SERVER DATA HANDLER CREATE THE NEEDED QUERY CREATOR AND CALLS THIS METHOD TO


    public void queryCreator(QueryConteiner queryCreator, String tableName, String schema_name){
        apiCallAndFillDB(queryCreator.createQuery(), tableName,schema_name);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String streamToString(InputStream stream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String data;
        String result = "";

        //here is all considered one line, so one iteration gives back all the files
        while( (data = bufferedReader.readLine()) != null ){
            result += data;
        }
        stream.close();
        return result;

    }

    /**
     * Here the result string is parsed in the preferred way transforming it in a JSON object and extrapolating the
     * fields that we need
     */

    private void parseResult(JSONObject result, String tableName, String schema_name) {
        JSONArray response = null;
        try {
            response = (JSONArray) result.get("items");
            accessRelevantElementsAndFillDB(response, tableName, schema_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Access to any JSON item retrieved by the API
     * @param items is a JSONArray containing all the items info retrieved with the API
     * @param tableName
     */
    private void accessRelevantElementsAndFillDB(JSONArray items, String tableName, String schema_name){
        System.out.print("HERE");
        try {
            JSONObject volume_info, item;
            Map<String, Object> map;
            for(int i = 0; i < items.length(); i++){
                item = (JSONObject) items.get(i);
                volume_info = (JSONObject) item.get("volumeInfo");
                // extract the information useful for the DB
                map = extractRelevantInfo(volume_info);
                fillDatabase(map, tableName, schema_name);
            }
        } catch (JSONException e) {
            System.out.print("JSON object malformed");
            e.printStackTrace();
        }

    }

    // interface with the DBMS that actually fill the DB
    private void fillDatabase(Map<String, Object> map, String tableName, String schema_name) {
        DatabaseManager dbManager = new DatabaseManager();
        dbManager.insertStatement(map, tableName, schema_name);
    }

    /**
     * Extract relevant info from the query and store them into an HashMap
     *
     * @param item JSONObject from which extract the relevant info
     * @return the filled HashMap
     */
    private Map<String,Object> extractRelevantInfo(JSONObject item){
        Map<String, Object> map = new HashMap<String,Object>();
        Iterator<String> keysItr = item.keys();
        int x = 0;
        while(keysItr.hasNext()) {
            String key = keysItr.next();
                Object value = null;
            try {
                value = item.get(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (value instanceof JSONArray) {
                try {
                    value = toList_JsonOb((JSONArray) value);
                    map = getNewKeysJson((ArrayList<JSONObject>) value, map);
                } catch (JSONException e) {
                    value = toList_String((JSONArray) value);
                    map = getNewKeysString((ArrayList<String>) value, key, map);

                }
            } else {

                value = value.toString();
                map.put(key, value);
            }
            System.out.print(x);
        }


        return map;
    }

    private Map<String, Object> fillMapJson(JSONObject item, Map<String, Object> map){
        Iterator<String> keysItr = item.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = null;

            try {
                value = item.get(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            value = value.toString();
            map.put(key, value);
        }
        return map;
    }

    private Map<String,Object> getNewKeysJson(ArrayList<JSONObject> value, Map<String, Object> map) {
        for(JSONObject elem: value){
            map = fillMapJson(elem, map);
        }
        return map;
    }

    private Map<String, Object> getNewKeysString(ArrayList<String> value, String key, Map<String, Object> map) {
        int count = 1;
        if ( key.equals("authors")) {
            key = "author_";
            for (String elem : value) {
                map.put(key + count, elem);
                count++;
            }
        }
        else if ( key.equals("categories")) {
            key = "category_";
            for (String elem : value) {
                map.put(key + count, elem);
                count++;
            }
        }
       return map;
    }


    /**
     * Convert a JSONArray into an ArrayList
     *
     * @param jsonObject passed
     * @return converted list
     */
    private ArrayList<JSONObject> toList_JsonOb(JSONArray jsonObject) throws JSONException {
        ArrayList<JSONObject> list = new ArrayList<JSONObject>();
        JSONArray jsonArray = jsonObject;
        if (jsonArray != null) {
            int len = jsonArray.length();
            for (int i = 0; i < len; i++) {
                list.add(jsonArray.getJSONObject(i));

            }
        }
        return list;
    }

    private ArrayList<String> toList_String(JSONArray jsonObject) {
        ArrayList<String> list = new ArrayList<String>();
        JSONArray jsonArray = jsonObject;
        if (jsonArray != null) {
            int len = jsonArray.length();
            for (int i = 0; i < len; i++) {
                try {
                    list.add(jsonArray.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Write the API REST response into a txt file. TODO: change path with operative system sensisitive case
     */
    private void writefile(String text){

        try{
            Writer output = null;
            File file = new File("C:\\Users\\raffa\\Desktop\\text\\result.json");
            output = new BufferedWriter(new FileWriter(file));
            output.write(text);
            output.close();
            System.out.println("File has been written");

        }catch(Exception e){
            System.out.println("Could not create file");
        }
    }
}
