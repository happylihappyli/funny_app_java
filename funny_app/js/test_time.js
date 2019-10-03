
var year=s_ai.time("今天.年");
var month=s_ai.time("今天.月");
var day=s_ai.time("今天.日");
var hour=s_ai.time("今天.时");
var minute=s_ai.time("今天.分");
var second=s_ai.time("今天.秒");
var strLine="";
strLine=year+"年"+month+"月"+day+"日 ";
//cmd.exe 下有问题，用PowerShell可以正常显示
s_out.println(strLine);

s_sys.sleep(10);
s_sys.exit(0);