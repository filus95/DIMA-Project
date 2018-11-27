package com.easylib.server.Database;

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

    public void apiFillDb(String query){
        //at this url we found a JSON file containing all the articles  of the topic " q=bitcoin " published today "from = date"
        String endpoint = "https://www.googleapis.com/books/v1/volumes?q="+query+
                "&key="+KEY_;
        String result1 = "";


        //initializing the connection
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(endpoint);
            urlConnection = (HttpURLConnection) url.openConnection();
            String response = streamToString(urlConnection.getInputStream());
            try {
                JSONObject response_json = new JSONObject(response);

                parseResult(response_json);
                return;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This method is used to store the Json file, in a form of string, from the URL giving it back as response
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

    private void parseResult(JSONObject result) {
        JSONArray response = null;
        try {
            response = (JSONArray) result.get("items");
            accessRelevantElements(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Access to any JSON item retrieved by the API
     * @param items is a JSONArray containing all the items info retrieved with the API
     */
    private void accessRelevantElements(JSONArray items){
        System.out.print("HERE");
        try {
            JSONObject volume_info, item;
            Map<String, Object> map;
            for(int i = 0; i < items.length(); i++){
                item = (JSONObject) items.get(i);
                volume_info = (JSONObject) item.get("volumeInfo");
                map = extractRelevantInfo(volume_info);
                fillDatabase(map);
            }
        } catch (JSONException e) {
            System.out.print("JSON object malformed");
            e.printStackTrace();
        }

    }

    private void fillDatabase(Map<String, Object> map) {
        DatabaseManager dbManager = new DatabaseManager();
        dbManager.fillBookDb(map);
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
    /**
     * Write the API REST response into a txt file
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
