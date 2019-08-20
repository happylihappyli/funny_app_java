/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funnyai.tools;

import org.mapdb.DB;
import org.mapdb.DBMaker;

/**
 *
 * @author happyli
 */
public class M_MapDB {
    public DB init(String strFile2){
        return DBMaker.fileDB(strFile2).checksumHeaderBypass().make();
    }
}
