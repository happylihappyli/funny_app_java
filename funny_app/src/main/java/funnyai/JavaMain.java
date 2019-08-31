/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funnyai;

import com.funnyai.tools.M_Scheduler;
import com.funnyai.tools.M_JSON;
import com.funnyai.tools.M_SYS;
import com.funnyai.tools.M_DB;
import com.funnyai.io.S_File_Text;
import com.funnyai.tools.M_File;
import com.funnyai.tools.M_MapDB;
import com.funnyai.tools.M_Net;
import com.funnyai.tools.M_RSA;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;
import com.funnyai.io.C_File;
import com.funnyai.tools.M_Treap;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.mapdb.DB;


/**
 *
 * @author happyli
 */
public class JavaMain {
    
    public static M_SYS pSYS=null;
    public static TreeMap pMap=new TreeMap();
    public static String strPath="";
    
    public V8 v8=null;
    
    public static void main(String[] args) {
        pSYS = new M_SYS(args);
        String strFile=args[0];
        strPath=new File(strFile).getParent();
        
        new JavaMain().Run(pSYS, strFile);
        
    }
    
    public void Run(M_SYS pSYS,String strFile){
        
        v8 = V8.createV8Runtime();
        String[][] arr1={
            {"args","Integer"},
            {"println","String"},
            {"read_input","String"},
            {"linux","String,Integer"},
            {"exit","Integer"},
            {"sleep","Integer"},
            {"init_setting","String"},
            {"init_session",""},
            {"Path_Segmentation",""},
        };
        Register(v8,pSYS,"s_sys",arr1);
        
        String[][] arr2={
            {"println","String"},
        };
        Register(v8,System.out,"s_out",arr2);
        String[][] arr3={
            {"JSONObject_XPath","String,String"},
            {"JSONArray_length","String"},
            {"JSONArray","String,String"},
        };
        Register(v8,new M_JSON(),"s_json",arr3);
        
        String[][] arr4={
            {"db_file","String,String"},
            {"map","String,String"},
            {"map_put","String,String,String"},
            {"map_size","String"},
            {"db_close","String"},
        };
        Register(v8,new M_DB(),"s_db",arr4);
        
        String[][] arr5={
            {"init",""},
            {"stop",""},
            {"add_job_daily","String,String,Integer,Integer"},
            {"add_job_week","String,String,Integer,Integer,Integer"},
        };
        Register(v8,new M_Scheduler(),"s_scheduler",arr5);
        String[][] arr6={
            {"set_socket_server","String"},
            {"send_msg","String,String,String,String,String"},
            {"stop_socket",""},
            {"http_get","String"},
            {"http_post","String,String"},
            {"ssh","String,String,String,String,String,Integer"},
        };
        Register(v8,new M_Net(),"s_net",arr6);
        String[][] arr7={
            {"read_ini","String,String"},
            {"Exists","String"},
            {"Copy2File","String,String"},
            {"Delete","String"},
            {"read","String"},
            {"read_begin","String,String"},
            {"read_line","String"},
            {"close","String"},
            {"dir_init","String"},
        };
        Register(v8,new M_File(),"s_file",arr7);
        //        scope_put(scope,"s_mapdb",new M_MapDB());
        String[][] arr8={
            {"init","String"},
        };
        Register(v8,new M_MapDB(),"s_mapdb",arr8);
        
        String[][] arr9={
            {"decrypt_by_private","String,String"},
        };
        Register(v8,new M_RSA(),"s_rsa",arr9);
        
        
        String[][] arr10={
            {"new_treap","String"},
        };
        Register(v8,new M_Treap(),"s_treap",arr10);
        
        String js_script=S_File_Text.Read(strFile, "utf-8",10000);
        v8.executeScript(js_script);
    }
    
    public static String read_input(String strInfo){
        try {
            String strReturn=null;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            out.println(strInfo);
            strReturn = br.readLine();
            return strReturn;
        } catch (IOException ex) {
            Logger.getLogger(M_SYS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    public static void Register(V8 v8,Object pObj,String Name,String[][] Params){
        
        V8Object v8Console = new V8Object(v8);
        v8.add(Name, v8Console);
        
        for (int i=0;i<Params.length;i++){
            String strFunction=Params[i][0];
            String strParam=Params[i][1];
            switch(strParam){
                case "Integer":
                    v8Console.registerJavaMethod(pObj,
                            strFunction,strFunction, new Class<?>[] { Integer.class });
                    break;
                case "Double":
                    v8Console.registerJavaMethod(pObj,
                            strFunction,strFunction, new Class<?>[] { Double.class });
                    break;
                    
                case "String":
                    v8Console.registerJavaMethod(pObj,
                            strFunction,strFunction, new Class<?>[] { String.class });
                    break;
                case "JSONObject":
                    v8Console.registerJavaMethod(pObj,
                            strFunction,strFunction, new Class<?>[] { JSONObject.class });
                    break;
                case "String,Integer":
                    v8Console.registerJavaMethod(pObj,
                            strFunction,strFunction, new Class<?>[] { 
                                String.class,Integer.class });
                    break;
                case "String,String":
                    v8Console.registerJavaMethod(pObj,
                            strFunction,strFunction, new Class<?>[] { String.class,String.class });
                    break;
                case "DB,String":
                    v8Console.registerJavaMethod(pObj,
                            strFunction,strFunction, new Class<?>[] { DB.class,String.class });
                    break;
                case "String,String,String":
                    v8Console.registerJavaMethod(pObj,
                            strFunction,strFunction, new Class<?>[] { 
                                String.class,String.class,String.class });
                    break;
                case "String,String,Integer,Integer":
                    v8Console.registerJavaMethod(pObj,
                            strFunction,strFunction, 
                            new Class<?>[] { 
                                String.class,String.class,Integer.class,Integer.class });
                    break;
                case "String,String,String,String,String":
                    v8Console.registerJavaMethod(pObj,
                            strFunction,strFunction, 
                            new Class<?>[] { 
                                String.class,String.class,String.class,String.class,String.class  
                            });
                    break;
                case "String,String,String,String,Integer":
                    v8Console.registerJavaMethod(pObj,
                            strFunction,strFunction, 
                            new Class<?>[] { 
                                String.class,String.class,String.class,String.class,Integer.class  
                            });
                    break;
                case "String,String,Integer,Integer,Integer":
                    v8Console.registerJavaMethod(pObj,
                            strFunction,strFunction, 
                            new Class<?>[] { 
                                String.class,String.class,Integer.class,Integer.class,Integer.class 
                            });
                    break;
                case "String,String,String,String,String,Integer":
                    v8Console.registerJavaMethod(pObj,
                            strFunction,strFunction, 
                            new Class<?>[] { 
                                String.class,String.class,String.class,String.class,String.class,Integer.class  
                            });
                    break;
                case "C_File":
                    v8Console.registerJavaMethod(pObj,
                            strFunction,strFunction, 
                            new Class<?>[] { 
                                C_File.class });
                    break;
                case "":
                    v8Console.registerJavaMethod(pObj,
                            strFunction,strFunction, new Class<?>[] {  });
                    break;
            }
        }
        //v8Console.registerJavaMethod(pObj, "println", "println", new Class<?>[] { String.class });
        v8Console.release();
        
        
    }
    
    
}
