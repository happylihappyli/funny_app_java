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
    
    while (strLine !== null) {
        var strSplit=strLine.split(",");
        var Score=parseFloat(strSplit[index]);
        
        sum+=Score;
        count+=1;
        
        items.push(Score);
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


var pArray=read(s_sys.args(1),s_sys.args(2));
var query=s_sys.args(3);
var strSplit=query.split(",");

var line=avg+","+fangcha+",";

for(var i=0;i<strSplit.length;i++){
    var index=Math.round((pArray.length-1)*parseFloat(strSplit[i]));
    var Score=pArray[index];
    line+=Score+",";
}
s_out.println(line);


s_sys.exit(0);

