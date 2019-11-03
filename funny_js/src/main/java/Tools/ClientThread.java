/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

/**
 *
 * @author happyli
 */
 
import com.funnyai.common.S_Command;
import com.funnyai.common.S_Debug;
import com.funnyai.io.S_File_Text;
import com.funnyai.net.Old.S_Net;
import com.funnyai.string.Old.S_Strings;
import funnyai.JavaMain;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import static java.lang.System.out;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
 
/**
 * Hello world!
 * client
 */
 
public class ClientThread extends Thread{// implements Runnable{
    private Socket socket;
    BufferedReader br = null;
    
    public String tcp_host="";
    public int tcp_port=0;
    public TCP_Client client=null;
    public int keep_count=0;
 
    public ClientThread(TCP_Client pClient) throws IOException{
        this.client=pClient;
        this.socket = pClient.socket;
        this.tcp_host=pClient.tcp_host;
        this.tcp_port=pClient.tcp_port;
        
        br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
    }
 
    public String data_remain="";
    @Override
    public void run() {
        boolean bError=false;
        while(bError==false){
            int len=0;
            byte[] buffer=new byte[4096];
            do{
                try{
                    len=socket.getInputStream().read(buffer, 0, buffer.length);

                }catch(SocketException ex){
                    try {
                        socket = new Socket(this.tcp_host,this.tcp_port);
                        ex.printStackTrace();
                    } catch (IOException ex2) {
                        Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex2);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }while(len==0);
            
            if (len>0){
                try {
                    byte[] dest=new byte[len];
                    System.arraycopy(buffer,0, dest,0,len);
                    String data=new String(dest,"utf-8");
                    
                    data=data_remain+data;
                    data_remain="";
                    out.println(data);
                    while(data.contains(":<s>:")){
                        keep_count=0;
                        int index1=data.indexOf(":<s>:");
                        int index2=data.indexOf(":</s>");
                        if (index2>index1 && index1>0){
                            String json=data.substring(index1+5,index2);
                            out.println("json="+json);
                            try{
                                JSONObject obj = new JSONObject(json);
                                Chat_Event(obj);
                            }catch(Exception ex){
                                out.println("Exception="+json);
                                ex.printStackTrace();
                            }

                            data=data.substring(index2+5);
                        }else{
                            data_remain=data;
                            break;
                        }
                    }
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
    }
    
    
    /**
     * 回答用户的命令
     * @param args1 
     */
    public void Chat_Event(Object... args1){
        for (Object args11 : args1) {
            JSONObject obj = (JSONObject) args11;

            if (obj.has("k")) {
                //k=keep
                return ;
            }
            
            String id="";
            if (obj.has("id")) id=obj.getString("id");

            
            String strType="";
            if (obj.has("type")) strType=obj.getString("type");
            //反馈给对方，告诉他，收到了。
            if ("chat_return".equals(strType)){
                return ;
            }
            
            String token="";
            if (obj.has("token")) token=obj.getString("token");
            
            String return_cmd="";
            if (obj.has("return_cmd")) return_cmd=obj.getString("return_cmd");
            String file="";
            if (obj.has("file")) file=obj.getString("file");
            

            String strFrom=obj.getString("from");
            String strTo=obj.getString("to");
            String strMessage=obj.getString("message");

            if ("system".equals(strFrom)){
                return ;
            }
            String[] strSplit=strFrom.split("/");
            
            client.Send_Msg(0,id,"chat_return",JavaMain.account,strFrom,"chat_return",id); //消息返回
            switch(strType){
                case "ai":
                    process_ai(id,token,return_cmd,strFrom,file,strMessage);
                    break;
                default:
                    break;
            }


            out.println(obj.toString());
        }
    }
    
    public void process_ai(
            String id,
            String token,
            String return_cmd,
            String strFrom,
            String strFile,
            String strMessage){
        
        
        try{
            int robot_id=1;
            String url="http://robot6.funnyai.com:8088/Get.FunnyAI?F=Get.FunnyAI&AI=1&Proxy=SYS";
            String strJson="{\n" +
                "\"id\":\""+robot_id+"\",\n" +
                "\"command\":\"ai.answer\",\n" +
                "\"topic\":\"\",\n" +
                "\"content\":\""+strMessage+"\",\n" +
                "\"web\":\"0\",\n" +
                "\"qq\":\"0\",\n" +
                "\"param\":\"\"\n" +
                "}";
            String data="msg="+S_Strings.URL_Encode(strJson)+"&xml=";
            String result=S_Net.http_post(url, data);
            
            out.println(result);
            
            result=result.replaceAll("\\r", " ");
            result=result.replaceAll("\\n", " ");
            
            String head="java -jar /root/happyli/funny_js.jar /root/happyli/intent/main.js";
            String Command=head+" 0 \""+result+"\"";
            String strReturn="";
            try {
                //out.println(Command);
                strReturn=S_Command.RunShell_Return(Command,5);
            }catch(Exception ex){
                ex.printStackTrace();
                strReturn=ex.toString();
                out.println("error="+strReturn);
            }
            client.Send_Msg(0,id,"ai_return",JavaMain.account,strFrom,"string",strReturn);
        }catch (Exception ex){
            S_Debug.Write_DebugLog("cmd_error", ex.toString());
        }
    }
    
   
}

