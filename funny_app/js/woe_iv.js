
function C_Line(){
    this.Good=0;
    this.Bad=0;
}


function read(strFile,index_good,index_bad){
    s_out.println(strFile+","+index_good+","+index_bad);
    
    var items=[];
    //s_treap.new_treap("pTreap");
    
    s_file.read_begin("File1",strFile); 
    
    var strLine = s_file.read_line("File1");
    s_out.println(strLine);
    
    while (strLine != null) {
        var strSplit=strLine.split(",");
        var pLine =new C_Line();
        pLine.Good=parseFloat(strSplit[index_good]);
        pLine.Bad=parseFloat(strSplit[index_bad]);
        
        if (pLine.Good>0 && pLine.Bad>0){
            items.push(pLine);
        }
        strLine = s_file.read_line("File1");
        if (strLine != null) s_out.println(strLine);
    }
    
    s_file.close("File1");


    return items;
}



function WOE_IV(pArray){
    
    var count=pArray.length;
    //sys.Msg(count);
    
    var a=new Array(count);
    var b=new Array(count);
    var data=new Array(count);
    
    var sum_a=0;
    var sum_b=0;
    for (var i=0;i<count;i++){
        var pLine=pArray[i];
        data[i]= new Array(2);
        data[i][0]=pLine.Good
        data[i][1]=pLine.Bad
        a[i]=data[i][0];
        b[i]=data[i][1];
        
        sum_a+=a[i];
        sum_b+=b[i];
    }
    
    var IV=0;
    for (var i=0;i<count;i++){
        var x=(a[i]/sum_a)/(b[i]/sum_b);
        var woe=Math.log(x);
        s_out.println(woe+"");
        
        IV+=((a[i]/sum_a)-(b[i]/sum_b))*woe;
    }
    var strInfo="<br> &lt; 0.03 无预测能力<br>"
            +"0.03 - 0.09 低<br>"
            +"0.1 - 0.29 中<br>"
            +"0.3 - 0.49 高<br>"
            +"&gt;=0.5 极高<br>";

    s_out.println("IV="+IV+strInfo);
}

//三个参数，文件名，index—good，index-bad
var pArray=read(s_sys.args(1),s_sys.args(2),s_sys.args(3));

WOE_IV(pArray);

s_sys.exit(0);




