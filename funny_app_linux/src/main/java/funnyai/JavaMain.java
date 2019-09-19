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
import com.funnyai.tools.M_AI;
import com.funnyai.tools.M_Output;
import com.funnyai.tools.M_Treap;
import com.funnyai.tools.M_Tree;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        Class s=String.class;
        Class i=Integer.class;
        
        //s_sys
        String[][] arr1={
            {"args","i"},
            {"args_all",""},
            {"linux","String,Integer"},
            {"exit","i"},
            {"sleep","i"},
            {"println","s"},
            {"read_input","s"},
            {"init_setting","s"},
            {"open_url","s"},
            {"init_session",""},
            {"Path_Segmentation",""},
            {"path",""},
            {"path_jar",""},
        };
        Register(v8,pSYS,"s_sys",arr1);
        
        //s_out
        String[][] arr2={
            {"println","s"},
        };
        Register(v8,new M_Output(),"s_out",arr2);
        
        //s_json
        String[][] arr3={
            {"JSONObject_XPath","s,s"},
            {"JSONArray_length","s"},
            {"JSONArray","String,String"},
        };
        Register(v8,new M_JSON(),"s_json",arr3);
        
        //s_db
        Object[][] arr4={
            {"db_file","s,s"},
            {"map","s,s"},
            {"map_put",new Class<?>[] { s,s,s }},
            {"map_size","s"},
            {"db_close","s"},
        };Register(v8,new M_DB(),"s_db",arr4);
        
        //s_scheduler
        Object[][] arr5={
            {"init",""},
            {"stop",""},
            {"add_job_daily",new Class<?>[] { s,s,i,i }},
            {"add_job_week",new Class<?>[] { 
                                    s,s,i,i,i 
                                }},
        };Register(v8,new M_Scheduler(),"s_scheduler",arr5);
        
        Object[][] arr6={
            {"set_socket_server","s"},
            {"send_msg",new Class<?>[] { 
                                    s,s,s,s,s  
                                }},
            {"stop_socket",""},
            {"http_get","s"},
            {"http_post","s,s"},
            {"ssh",new Class<?>[] { s,s,s,s,s,i}},
            {"upload",new Class<?>[] { s,i,s,s,s,s}},
        };Register(v8,new M_Net(),"s_net",arr6);
        
        Object[][] arr7={
            {"read_ini","s,s"},
            {"Exists","s"},
            {"Copy2File","s,s"},
            {"delete","s"},
            {"read","s"},
            {"save","s,s"},
            {"read_begin","s,s"},
            {"read_line","s"},
            {"close","s"},
            {"dir_init","s"},
        };
        Register(v8,new M_File(),"s_file",arr7);
        
        Object[][] arr8={
            {"init","s"},
        };
        Register(v8,new M_MapDB(),"s_mapdb",arr8);
        
        String[][] arr9={
            {"decrypt_by_private","s,s"},
        };
        Register(v8,new M_RSA(),"s_rsa",arr9);
        
        Object[][] arr10={
            {"new_treap","s"},
        };
        Register(v8,new M_Treap(),"s_treap",arr10);
        
        Object[][] arr11={
            {"train",new Class<?>[] { s,i }},
        };Register(v8,new M_Tree(),"s_tree",arr11);
        
        
        Object[][] arr12={
            {"time",new Class<?>[] { s}},
            {"时间",new Class<?>[] { s}},
        };Register(v8,new M_AI(),"s_ai",arr12);
        
        String js_script=S_File_Text.Read(strFile, "utf-8",10000);
        v8.executeScript(js_script);
    }
    
    /**
     * 读取用户输入
     * @param strInfo
     * @return 
     */
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
    public static void Register(
            V8 v8,Object pObj,
            String Name,
            Object[][] Params){
        
        V8Object v8Console = new V8Object(v8);
        v8.add(Name, v8Console);
        
        for (int i=0;i<Params.length;i++){
            String strFunction=(String)Params[i][0];
            Object pObj2 = (Object)Params[i][1];
            if (pObj2.getClass().toString().equals("class java.lang.String")){
                
                String strParam=(String) Params[i][1];

                switch(strParam){
                    case "i":
                    case "Integer":
                        v8Console.registerJavaMethod(pObj,
                                strFunction,strFunction, new Class<?>[] { Integer.class });
                        break;
                    case "s":
                    case "String":
                        v8Console.registerJavaMethod(pObj,
                                strFunction,strFunction, new Class<?>[] { String.class });
                        break;
                    case "s,i":
                    case "String,Integer":
                        v8Console.registerJavaMethod(pObj,
                                strFunction,strFunction, new Class<?>[] { 
                                    String.class,Integer.class });
                        break;
                    case "s,s":
                    case "String,String":
                        v8Console.registerJavaMethod(pObj,
                                strFunction,strFunction, new Class<?>[] { String.class,String.class });
                        break;
                    case "":
                        v8Console.registerJavaMethod(pObj,
                                strFunction,strFunction, new Class<?>[] {  });
                        break;
                }
            }else{
                v8Console.registerJavaMethod(pObj,
                        strFunction,strFunction, (Class<?>[]) pObj2);
            }
        }
        //v8Console.registerJavaMethod(pObj, "println", "println", new Class<?>[] { String.class });
        v8Console.release();
        
        
    }
    
    
}
