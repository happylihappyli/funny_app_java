/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funnyai.tools;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import funnyai.JavaMain;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author happyli
 */
public class M_JSON {
    
    public void JSONArray(
            String key,String strReturn){
        JSONArray token = new JSONArray(strReturn);
        JavaMain.pMap.put(key, token);
    }
    
    
    public void JSONObject_JSONArray(
            String key,String key2,String strPath){
        JSONObject token=(JSONObject) JavaMain.pMap.get(key);
        JSONArray token2 = token.getJSONArray(strPath);
        JavaMain.pMap.put(key2, token2);
    }
    
    
    public void JSONArray_JSONObject(
            String key,String key2,Integer index){
        JSONArray token=(JSONArray) JavaMain.pMap.get(key);
        JSONObject token2 = (JSONObject) token.get(index);
        JavaMain.pMap.put(key2, token2);
    }
    
    public void JSONObject(
            String key,String strReturn){
        JSONObject token = new JSONObject(strReturn);
        JavaMain.pMap.put(key, token);
    }
    
    /**
     * 
     * @param json
     * @param strPaths
     * @return 
     */
    public String JSONObject_XPath(
            String json,String strPaths){
        String strLine = JsonPath.read(json,strPaths);
        return strLine;
    }
    
    public String to_string(
            String key){
        JSONObject token=(JSONObject) JavaMain.pMap.get(key);
        return token.toString();
    }
}
