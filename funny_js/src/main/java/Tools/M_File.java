/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import antlr_js.ECMAScriptParser;
import com.funnyai.io.C_File;
import com.funnyai.io.C_Property_File;
import com.funnyai.io.Old.S_Dir;
import com.funnyai.io.Old.S_File_Text;
import com.funnyai.io.S_file;
import funnyai.JavaMain;
import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.System.out;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author happyli
 */
public class M_File {
    
    public static Object call(
            M_File pM_File,
            String function,
            Object value,
            MyVisitor pParent,
            ECMAScriptParser.ArgumentListContext pList){
        switch(function){
            case "read_begin":
            case "write_line":
            case "write_begin":
                {
                    ECMAScriptParser.SingleExpressionContext pKey = pList.singleExpression(0);// .singleExpression(0));
                    ECMAScriptParser.SingleExpressionContext pFile = pList.singleExpression(1);
                    String key=(String) pParent.parse_single_expression_value(pKey);
                    String line=(String) pParent.parse_single_expression_value(pFile);
                    switch(function){
                        case "write_begin":
                            pM_File.write_begin(pParent,key,line);
                            return null;
                        case "write_line":
                            pM_File.write_line(pParent,key,line);
                            return null;
                        case "read_begin":
                            pM_File.read_begin(pParent,key,line);
                            return null;
                    }
                }
            case "read_line":
            case "close":
            case "dir_init":
            case "write_end":
                {
                    ECMAScriptParser.SingleExpressionContext pKey = pList.singleExpression(0);
                    String key=(String) pParent.parse_single_expression_value(pKey);
                    switch(function){
                        case "read_line":
                            return pM_File.read_line(pParent,key);
                        case "close":
                            pM_File.close(pParent,key);
                            return null;
                        case "dir_init":
                            pM_File.dir_init(key);
                            return null;
                        case "write_end":
                            pM_File.write_end(pParent,key);
                            return null;
                    }
                }
            default:
                out.println("没有这个函数:s_out."+function+"!");
                break;
        }
        return null;
    }
    
    
    public String read_ini(String strFile,String key){
        try {
            C_Property_File pFile=new C_Property_File(strFile);
            
            return pFile.Read(key);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(M_File.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public boolean Exists(String strFile){
        return S_file.main.Exists(strFile);
    }
    
    public void Copy2File(String strFile1,String File2){
        S_file.main.Copy2File(strFile1,File2);
    }
    
    public void delete(String strFile1){
        S_file.main.Delete(strFile1);
    }
    
    public long size(String strFile){
        File pFile=new File(strFile);
        return pFile.length();
    }
    
    public String read(String strFile1){
        if (strFile1.startsWith("@")){
            strFile1=strFile1.replace("@", JavaMain.strPath);
        }
        return S_File_Text.Read(strFile1,"utf-8",-1);
    }
    
    public String save(String strFile1,String Content){
        if (strFile1.startsWith("@")){
            strFile1=strFile1.replace("@", JavaMain.strPath);
        }
        return S_File_Text.Write(strFile1,Content);
    }
    
    
    public void write_begin(MyVisitor pParent,
            String key,String strFile1){
        
        if (strFile1.startsWith("@")){
            strFile1=strFile1.replace("@", JavaMain.strPath);
        }
        pParent.put_var(key,S_file.main.Write_Begin(strFile1,false,"utf-8"));
    }
    
    
    public void write_line(MyVisitor pParent,
            String key,String strLine){
        
        C_File pFile=(C_File) pParent.get_var(key);
        S_file.main.Write_Line(pFile,strLine);
    }
    
    
    public void write_end(MyVisitor pParent,
            String key){
        
        C_File pFile=(C_File) pParent.get_var(key);
        S_file.main.Write_End(pFile);
    }
    
    
    public void read_begin(MyVisitor pParent,
            String key,String strFile1){
        
        if (strFile1.startsWith("@")){
            strFile1=strFile1.replace("@", JavaMain.strPath);
        }
        pParent.put_var(key,S_file.main.Read_Begin(strFile1,"utf-8"));
    }
    
    public String read_line(MyVisitor pParent,String key){
        C_File pFile=(C_File) pParent.get_var(key);
        String strLine=S_file.main.read_line(pFile);
        if (strLine==null){
            if (JavaMain.bDebug){
                //out.println("read_line==null");
            }
        }
        return strLine;
    }
    
    
    
    
    public void close(MyVisitor pParent,String key){
        C_File pFile=(C_File) pParent.get_var(key);
        pFile.Close();
    }
    
    public void dir_init(String strDir){
        S_Dir.InitDir(strDir);
    }
}
