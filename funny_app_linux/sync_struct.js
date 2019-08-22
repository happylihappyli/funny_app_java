
//*
function Init_Struct(){
    
    var strFile = s_sys.Path_Struct()+"/struct.db";
    if (s_file.Exists(strFile)){
        s_file.Copy2File(strFile,strFile+".bak");
        s_file.Delete(strFile);
    }
    
    
    var mydb = s_db.DB_File(strFile);
    var map = s_db.map(mydb,"map");

        
    var strURL = "https://www.funnyai.com/funnyai/json_list_ai_struct.php";
    var strData;
    var PageSize = 1000;
    var p = 0;
    while (p > -1) {
        p++;
        strData = "p=" + p;
    
        s_out.println("struct.from.web p="+p);
        var strReturn = s_net.http_post(strURL,strData);
        
        var Count =0;
        var IndexStr = strReturn.indexOf("{");
        if (IndexStr>-1){
            strReturn = strReturn.substring(IndexStr);
            var token = s_json.JSONObject(strReturn);
            Count = token.getInt("Count");
            if (Count>0){
                map.put("k"+p,strReturn);
                map.put("size",p);
            }
        }
        
        
        if (Count < PageSize){
            break;
        }
    }
    s_out.println("size="+map.size());
    mydb.close();
}

//*/
var strFile=s_sys.args(1);

s_out.println(strFile);

s_sys.init_setting(strFile);

s_sys.init_session();

Init_Struct();









