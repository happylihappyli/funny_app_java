
//*
function View_DB(){
    
    var strFile2 = s_sys.Path_Struct()+"/struct.db";

    
    var db = s_mapdb.init(strFile2);
    var map = db.hashMap("map").open();
    

    var Size=map.get("size");
    s_out.println(Size);
    if (Size>10) Size=10;
    for(var i=0;i<=Size;i++){
        var strReturn=map.get("k"+i);
        s_out.println("============================");
        s_out.println(i);
        if (strReturn!=null) s_out.println(strReturn);
    }
    db.close();
}

//*/
var strFile=s_sys.args(1);

s_out.println(strFile);

s_sys.init_setting(strFile);

s_sys.init_session();

View_DB();









