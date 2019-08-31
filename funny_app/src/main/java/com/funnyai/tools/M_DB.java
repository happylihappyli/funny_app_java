/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funnyai.tools;

import com.funnyai.io.S_file;
import funnyai.JavaMain;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

/**
 *
 * @author happyli
 */
public class M_DB {
    public void db_file(String key,String strFile){
        DB db = DBMaker.fileDB(strFile).checksumHeaderBypass().make();
        
        JavaMain.pMap.put(key,db);
    }
    
    public void db_close(String key){
        DB db = (DB)JavaMain.pMap.get(key);
        db.close();
    }
    
    
    public void map(String key,String table){
        DB db=(DB)JavaMain.pMap.get(key);
        ConcurrentMap pMap=db.hashMap(table).create();
        JavaMain.pMap.put("map",pMap);
    }
    
    public void map_put(String map,String key,String value){
        ConcurrentMap pMap=(ConcurrentMap)JavaMain.pMap.get(map);
        pMap.put(key, value);
    }
    public int map_size(String map){
        ConcurrentMap pMap=(ConcurrentMap)JavaMain.pMap.get(map);
        return pMap.size();
    }
}
