/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funnyai.tools;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author happyli
 */
public class C_JSON {
    
    public JSONArray JSONArray(String strReturn){
        JSONArray token = new JSONArray(strReturn);
        
        return token;
    }
    
    
    public JSONObject JSONObject(String strReturn){
        JSONObject token = new JSONObject(strReturn);
        return token;
    }
    
    public String to_string(JSONObject token){
        return token.toString();
    }
}
