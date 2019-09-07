
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



function chi_square(pArray){
    
    var count=pArray.length;
    //sys.Msg(count);
    
    var a=new Array(count);
    var b=new Array(count);
    var data=new Array(count);
    
    var x=0;
    for (var i=0;i<count;i++){
        var pLine=pArray[i];
        data[i]= new Array(2);
        data[i][0]=pLine.Good
        data[i][1]=pLine.Bad
        a[i]=data[i][0];
        b[i]=data[i][1];
        
        var delta=a[i]-b[i];
        x+=delta*delta/b[i];
    }
    

    s_out.println("chi_square="+x);
}

//三个参数，文件名，index—good，index-bad
var pArray=read(s_sys.args(1),s_sys.args(2),s_sys.args(3));

chi_square(pArray);

s_sys.exit(0);



