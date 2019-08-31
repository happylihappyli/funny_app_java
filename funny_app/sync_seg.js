
//*
function Init_Seg(){
    
    var strFile = s_sys.Path_Segmentation()+"/seg.db";
    if (s_file.Exists(strFile)){
        s_file.Copy2File(strFile,strFile+".bak");
        s_file.Delete(strFile);
    }
    
    
    s_db.db_file("db",strFile);
    s_db.map("db","map");

        
    var strURL = "https://www.funnyai.com/funnyai/json_list_ai_word.php";
    var strData;
    var PageSize = 5000;
    var p = 0;
    while (p > -1) {
        p++;
        strData = "p=" + p;
    
        s_out.println("seg.from.web p="+p);
        var strReturn = s_net.http_post(strURL,strData);//, "utf-8", "",false);
        
        var Count =0;
        var IndexStr = strReturn.indexOf("[");
        if (IndexStr>-1){
            strReturn = strReturn.substring(IndexStr);
            s_json.JSONArray("json_array",strReturn);
            Count=s_json.JSONArray_length("json_array");
            if (Count>0){
                s_db.map_put("map","k"+p,strReturn);
                s_db.map_put("map","size",p+"");
            }
        }
        
        
        if (Count < PageSize){
            break;
        }
    }
    s_out.println("size="+s_db.map_size("map"));
    s_db.db_close("db");
}

//*/
var strFile=s_sys.args(1);

s_out.println(strFile);

s_sys.init_setting(strFile);

s_sys.init_session();

Init_Seg();

s_sys.exit(0);








