/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funnyai.tools;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import smile.classification.DecisionTree;
import smile.data.AttributeDataset;
import smile.data.parser.ArffParser;
import smile.validation.LOOCV;

/**
 *
 * @author happyli
 */
public class M_Tree {
    public String train(String strFile,Integer Max_Leaf_Node){
        if (Max_Leaf_Node==0) Max_Leaf_Node=3;
        try {
            ArffParser p=new ArffParser();
            p.setResponseIndex(4);//结果列
            //String strFile="D:\\Funny\\Data\\weather.arff";
            AttributeDataset weather = p.parse(new File(strFile));
             
            double[][] x = weather.toArray(new double[weather.size()][]);
            int[] y = weather.toArray(new int[weather.size()]);
            
            DecisionTree tree = new DecisionTree(weather.attributes(), x, y, Max_Leaf_Node);
            return tree.dot();
            
        } catch (IOException | ParseException ex) {
            Logger.getLogger(M_Tree.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    
    public String test(String strFile,Integer Max_Leaf_Node){
        if (Max_Leaf_Node==0) Max_Leaf_Node=3;
        try {
            ArffParser p=new ArffParser();
            p.setResponseIndex(4);//结果列
            //String strFile="D:\\Funny\\Data\\weather.arff";
            AttributeDataset weather = p.parse(new File(strFile));
             
            double[][] x = weather.toArray(new double[weather.size()][]);
            int[] y = weather.toArray(new int[weather.size()]);
            
            //DecisionTree tree = new DecisionTree(weather.attributes(), x, y, Max_Leaf_Node);
            //return tree.dot();
            
            int n = x.length;
            LOOCV loocv = new LOOCV(n);//扣除一行作为测试样本
            int error = 0;
            for (int i = 0; i < n; i++) {
                double[][] trainx = smile.math.Math.slice(x, loocv.train[i]);
                int[] trainy = smile.math.Math.slice(y, loocv.train[i]);
                
                DecisionTree tree = new DecisionTree(weather.attributes(), trainx, trainy, 3);
                if (y[loocv.test[i]] != tree.predict(x[loocv.test[i]]))
                    error++;
            } 
            System.out.println("Decision Tree error = " + error);

        } catch (IOException ex) {
            Logger.getLogger(M_Tree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(M_Tree.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
