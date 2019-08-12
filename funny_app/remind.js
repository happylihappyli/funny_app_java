
var city="上海";

function call_back_job(strKey){
    var weather=get_weather(city);
    var strSplit=strKey.split("_");
    switch(strSplit[0]){
        case "带雨伞":
            s_out.println("带雨伞判断:"+weather);
            if (weather.indexOf("雨")>-1){//.includes
                s_net.send_msg("chat_event","funny_app_java:tts","*","会下雨，带雨伞!");
            }
            if (weather.indexOf("雪")>-1){//.includes
                s_net.send_msg("chat_event","funny_app_java:tts","*","会下雪，带雨伞!");
            }
            break;
        case "晒东西":
            s_out.println("晒东西判断:"+weather);
            var bSun=true;
            if (weather.indexOf("雨")>-1){//.includes
                bSun=false;
            }
            if (weather.indexOf("雪")>-1){//.includes
                bSun=false;
            }
            if (bSun==true && weather.indexOf("晴")>-1){//.includes
                s_net.send_msg("chat_event","funny_app_java:tts","*","可以晒东西");
            }else{
                s_out.println("不能晒东西");
            }
            break;
    }
}

function get_weather(city){
    var url="http://api.map.baidu.com/telematics/v3/weather?location="
    +encodeURIComponent(city)+"&output=json&ak=FGwyoLoXgYjb92dDdZWrfZ7a";
    var obj=s_json.JSONObject(s_net.http_get(url));
    var obj2=obj.getJSONArray("results").get(0);
    return obj2.getJSONArray("weather_data").get(0).getString("weather");
}

var count=1;

s_scheduler.init();
s_scheduler.add_job_daily("带雨伞_1","tr1",7,5);
s_scheduler.add_job_daily("带雨伞_2","tr2",7,35);

s_scheduler.add_job_week("晒东西_1","tr2",6,9,35);
s_scheduler.add_job_week("晒东西_2","tr2",7,9,35);

s_net.set_socket_server("http://robot6.funnyai.com:7777");

s_out.println(get_weather("上海"));
call_back_job("晒东西_1");
//*
var a="";
while(a!="q"){
    a=s_sys.read_input("输入q，退出");
    s_sys.sleep(1);
}
//*/

s_scheduler.stop();







