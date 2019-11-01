//  计算 计算最大， 最小值， 中位数，。。。


var sum=0.0;
var count=0;
var avg=0.0;
var fangcha=0.0;


function getSum(total, num) {
    return total+num;
}

function read(strFile,index){
    
    var items=[];
    
    s_file.read_begin("pFile",strFile); 
    
    var strLine = s_file.read_line("pFile");
    //s_out.println(strLine);
    
    while (strLine !== null) {
        var strSplit=strLine.split(",");
        //var pLine =new C_Line();
        //pLine.Score=parseFloat(strSplit[index]);
        var Score=parseFloat(strSplit[index]);
        
        sum+=Score;
        count+=1;
        
        items.push(Score);//pLine);
        strLine = s_file.read_line("pFile");
    }
    avg=sum/count;
    
    s_file.close("pFile");

    items.sort(function (a, b) {
      return (a-b);
    });

    var items2 = items.map(function(val,index){
        return (val-avg)*(val-avg);
    });
    
    
    fangcha=items2.reduce(getSum,0)/count;
    return items;
}

function round_2(value){
    return Math.round(value*100)/100;
}

var k_count=parseInt(s_sys.args(2));
//s_out.println(k_count);

for(var k=0;k<k_count;k++){
    var pArray=read(s_sys.args(1),k);
    var query=s_sys.args(3);
    var strSplit=query.split(",");
    
    var line=round_2(avg)+","+round_2(fangcha)+",";
    
    for(var i=0;i<strSplit.length;i++){
        var index=Math.round((pArray.length-1)*parseFloat(strSplit[i]));
        //var pLine=pArray[index];
        var Score=pArray[index];
        line+=round_2(Score)+",";
    }
    s_out.println(line);
}

s_sys.sleep(10);
s_sys.exit(0);

