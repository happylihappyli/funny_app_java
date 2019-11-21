/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import antlr_js.ECMAScriptParser;
import com.funnyai.io.C_File;
import com.funnyai.io.S_file;
import com.funnyai.net.Old.S_Net;
import funnyai.JavaMain;
import static java.lang.System.out;
import smile.classification.GradientTreeBoost;

/**
 * Machine learn 
 * @author happyli
 */
public class M_ML {
    
    public static Object call(
            String function,
            Object value,
            MyVisitor pParent,
            ECMAScriptParser.ArgumentListContext pList){
        Object pObj;
        switch(function){
            case "GBDT":
                {
                    ECMAScriptParser.SingleExpressionContext p0 = pList.singleExpression(0);
                    ECMAScriptParser.SingleExpressionContext p1 = pList.singleExpression(1);
                    ECMAScriptParser.SingleExpressionContext p2 = pList.singleExpression(2);
                    
                    String file=(String) pParent.parse_single_expression_value(p0);
                    int field_count=pParent.int_from_object(pParent.parse_single_expression_value(p1));
                    int tree_count=pParent.int_from_object(pParent.parse_single_expression_value(p2));
                    JavaMain.pML.GBDT(file, field_count,tree_count);
                }
                break;
            case "GBDT_predict":
                {
                    ECMAScriptParser.SingleExpressionContext p0 = pList.singleExpression(0);
                    ECMAScriptParser.SingleExpressionContext p1 = pList.singleExpression(1);
                    
                    String strLine=(String) pParent.parse_single_expression_value(p0);
                    int field_count=pParent.int_from_object(pParent.parse_single_expression_value(p1));
                    return JavaMain.pML.GBDT_predict(strLine, field_count);
                }
            default:
                out.println("没有这个函数:s_ml."+function+"!");
                break;
        }
        return null;
    }
    
    public GradientTreeBoost pTree=null;
    public void GBDT(String file,int field_count,int ntrees){
        C_File pFile=S_file.main.Read_Begin(file, "utf-8");
        
        int line_count=0;
        String strLine=S_file.main.read_line(pFile);
        while(strLine!=null){
            line_count+=1;
            strLine=S_file.main.read_line(pFile);
        }
        pFile.Close();
        
        double[][] x=new double[line_count][field_count];
        int[] y=new int[line_count];
        
        pFile=S_file.main.Read_Begin(file, "utf-8");
        strLine=S_file.main.read_line(pFile);
        int i=0;
        while(strLine!=null){
            String[] strSplit=strLine.split(",");
            for(int j=0;j<field_count;j++){
                x[i][j]=Double.parseDouble(strSplit[j]);
            }
            Double y2=Double.parseDouble(strSplit[field_count]);
            y[i] = y2.intValue();
            i+=1;
            strLine=S_file.main.read_line(pFile);
        }
        pFile.Close();
        
        GradientTreeBoost.Trainer pTrainer=new GradientTreeBoost.Trainer(ntrees);
        pTree=pTrainer.train(x, y);
        
    }
    
    public double GBDT_predict(String strLine,int field_count){
        String[] strSplit=strLine.split(",");
        double[][] x=new double[1][field_count];
        for(int j=0;j<field_count;j++){
            x[0][j]=Double.parseDouble(strSplit[j]);
        }
        return pTree.predict(x)[0];
    }
}
