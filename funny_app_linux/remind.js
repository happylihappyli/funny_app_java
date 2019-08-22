var city="上海";

function call_back_job(strKey){
    s_out.println("call_back_job:"+strKey);
    var strSplit=strKey.split("_");
    var weather;
    s_out.println("key1:"+strSplit[0]);
    switch(strSplit[0]){
        case "带雨伞":
            weather=get_weather(city);
            s_out.println("带雨伞判断:"+weather);
            if (weather.indexOf("雨")>-1){//.includes
                s_net.send_msg("chat_event","","funny_app_java:tts","*","会下雨，带雨伞!");
            }
            if (weather.indexOf("雪")>-1){//.includes
                s_net.send_msg("chat_event","","funny_app_java:tts","*","会下雪，带雨伞!");
            }
            break;
        case "晒东西":
            weather=get_weather(city);
            s_out.println("晒东西判断:"+weather);
            var bSun=true;
            if (weather.indexOf("雨")>-1){//.includes
                bSun=false;
            }
            if (weather.indexOf("雪")>-1){//.includes
                bSun=false;
            }
            if (bSun && weather.indexOf("晴")>-1){//.includes
                s_net.send_msg("chat_event","","funny_app_java:tts","*","可以晒东西");
            }else{
                s_out.println("不能晒东西");
            }
            break;
        case "test":
            s_out.println("test send chat_event");
            s_net.send_msg("chat_event","","funny_app_java:tts","*","测试");
            break;
        default:
            s_out.println("default");
            break;
    }
}

function get_weather(city){
    var url="http://api.map.baidu.com/telematics/v3/weather?location="
    +encodeURIComponent(city)+"&output=json&ak=FGwyoLoXgYjb92dDdZWrfZ7a";
    var strJson=s_net.http_get(url);
    s_out.println(strJson);
    var weaher=s_json.JSONObject_XPath(strJson,"$.results[0].weather_data[0].weather");
    //var obj=s_json.JSONObject(strJson);
    //var obj2=obj.getJSONArray("results").get(0);
    return weaher;//obj2.getJSONArray("weather_data").get(0).getString("weather");
}

var count=1;

s_scheduler.init();
s_scheduler.add_job_daily("带雨伞_1","tr1",7,5);
s_scheduler.add_job_daily("带雨伞_2","tr2",7,35);
s_scheduler.add_job_daily("test_1","tr_10_10",15,10);

s_scheduler.add_job_week("晒东西_1","tr2",6,9,35);
s_scheduler.add_job_week("晒东西_2","tr2",7,9,35);

s_net.set_socket_server("http://robot6.funnyai.com:8000");

s_out.println(get_weather("上海"));
call_back_job("晒东西_1");
//call_back_job("test_1");
//*
var a="";
while(a!="q"){
    a=s_sys.read_input("输入q，退出");
    s_sys.sleep(1);
}
//*/

s_scheduler.stop();


