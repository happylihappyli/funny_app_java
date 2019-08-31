/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funnyai.tools;

import com.funnyai.data.C_K_Double;
import com.funnyai.data.Treap;
import funnyai.JavaMain;

/**
 *
 * @author happyli
 */
public class M_Treap {
    public void new_treap(String key){
        JavaMain.pMap.put(key, new Treap());
    }
    
    public void insert(String key,Double db,Object pObject){
        Treap Treap=(Treap) JavaMain.pMap.get(key);
        Treap.insert(new C_K_Double(db), pObject);
    }
}
