/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funnyai.tools;

import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

/**
 *
 * @author happyli
 */
public class M_DB {
    public DB DB_File(String strFile){
        DB db = DBMaker.fileDB(strFile).checksumHeaderBypass().make();
        return db;
    }
    
    public ConcurrentMap map(DB db,String table){
        
        return db.hashMap(table).create();
    }
}
