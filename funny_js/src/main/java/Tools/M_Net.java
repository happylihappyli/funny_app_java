/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import antlr_js.ECMAScriptParser;
import com.funnyai.net.Old.S_Net;
import com.funnyai.net.S_net;
import funnyai.JavaMain;
import static java.lang.System.out;

/**
 *
 * @author happyli
 */
public class M_Net {
    public TCP_Client pClient;
    
    public static Object call(
            String function,
            Object value,
            MyVisitor pParent,
            ECMAScriptParser.ArgumentListContext pList){
        Object pObj;
        switch(function){
            case "tcp_connect":
                {
                    ECMAScriptParser.SingleExpressionContext pHost = pList.singleExpression(0);// .singleExpression(0));
                    ECMAScriptParser.SingleExpressionContext pPort = pList.singleExpression(1);
                    String host=(String) pParent.parse_single_expression_value(pHost);
                    int port=pParent.int_from_object(pParent.parse_single_expression_value(pPort));
                    JavaMain.pNet.tcp_connetc(host, port);
                }
                break;
            case "send_msg":
                {
                    ECMAScriptParser.SingleExpressionContext pID = pList.singleExpression(0);
                    ECMAScriptParser.SingleExpressionContext pOldID = pList.singleExpression(1);
                    ECMAScriptParser.SingleExpressionContext pType = pList.singleExpression(2);
                    ECMAScriptParser.SingleExpressionContext pFrom = pList.singleExpression(3);
                    ECMAScriptParser.SingleExpressionContext pTo = pList.singleExpression(4);
                    ECMAScriptParser.SingleExpressionContext pReturn_Cmd = pList.singleExpression(5);
                    ECMAScriptParser.SingleExpressionContext pMsg = pList.singleExpression(6);
                    
                    int ID=pParent.int_from_object(pParent.parse_single_expression_value(pID));
                    String old_id=(String)pParent.parse_single_expression_value(pOldID);
                    String type=(String)pParent.parse_single_expression_value(pType);
                    String from=(String)pParent.parse_single_expression_value(pFrom);
                    String to=(String)pParent.parse_single_expression_value(pTo);
                    String return_cmd=(String)pParent.parse_single_expression_value(pReturn_Cmd);
                    String msg=(String)pParent.parse_single_expression_value(pMsg);
                    
                    JavaMain.pNet.send_msg(ID, old_id, type, from, to, return_cmd, msg);
                }
                break;
            case "http_get":
                pObj=pParent.get_var(value);
                if (pObj!=null){
                    value=(String) pObj;
                }
                return S_Net.http_get((String)value);
            default:
                out.println("没有这个函数:s_net."+function+"!");
                break;
        }
        return null;
    }
    
    
    public void set_socket_server(String url){
        S_Net.set_socket_server(url);
    }
    
    public void tcp_connetc(String hosts,int port){
        pClient=new TCP_Client(hosts,port);
        pClient.start();
    }
    
    
    public void send_msg(
            long ID,
            String old_ID,
            String type,
            String from,
            String strTo,
            String return_cmd,
            String strMsg){
        pClient.Send_Msg(ID, old_ID, type, from, strTo, return_cmd, strMsg);
    }
    
    public void send_msg_socket_io(
            String event_type,String stype,
            String from,String to,String msg){
        S_Net.SI_Send2(event_type,stype,from,to,msg);
    }
    
    public void upload(String host,Integer iPort,String username,String password,
            String uploadFile,String directory){
        S_net.main.SFtp_Upload(host, iPort, username, password, uploadFile, directory);
    }
    
    public void stop_socket(){
        S_Net.socket.close();
    }
    
    public String http_get(String url){
        return S_Net.http_get(url);
    }
    
    public String http_post(String url,String strData){
        return S_net.main.http_post(url, strData);
    }
    
    public String ssh(
            String IP,
            String user,
            String password,
            String file_pem,
            String strLine,
            Integer wait_count){
        M_SSH sshExecutor = new M_SSH(IP,user,password,file_pem,wait_count);
 
        String strReturn=sshExecutor.execute(strLine);
        return strReturn;
    }
}
