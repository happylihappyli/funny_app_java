
function C_Line(){
    this.Score=0; 
    this.Right=0;
    this.Error=0;
    
    this.Sum_Right=0;
    this.Sum_Error=0;
    
    this.Sum_Right_Rate=0;
    this.Sum_Error_Rate=0;
}


function read(strFile,index_score,index_right,index_error){
    s_sys.Debug(strFile,index_score,index_right,index_error);
    
    var pTreap=new com.funnyai.data.Treap();
    
    var pFile = s_file.Read_Begin(strFile); 
    
    var strLine = s_file.read_line(pFile);
    s_out.println(strLine);
    
    while (strLine != null) {
        var strSplit=strLine.split(",");
        var pLine =new C_Line();
        pLine.Score=parseFloat(strSplit[index_score]);
        pLine.Right=parseFloat(strSplit[index_right]);
        pLine.Error=parseFloat(strSplit[index_error]);
        pTreap.insert(new com.funnyai.data.C_K_Double(pLine.Score),pLine);
        strLine = s_file.read_line(pFile);
        if (strLine != null) s_out.println(strLine);
    }
    
    pFile.Close();

    var pArray=[];
    
    var p=pTreap.Elements(true);
    while(p.HasMoreElements()){
        var pLine=p.NextElement();
        pArray.push(pLine);
    }
    
    return pArray;
}


var pArray=read(s_sys.args(1),s_sys.args(2),s_sys.args(3),s_sys.args(4));

//*
var Sum_Right=0;
var Sum_Error=0;
for (var i=0;i<pArray.length;i++){
    var pLine=pArray[i];
    Sum_Right+=pLine.Right;
    Sum_Error+=pLine.Error;
    pLine.Sum_Right=Sum_Right;
    pLine.Sum_Error=Sum_Error;
}

var delta=0;
var tmp_delta=0;
for (var i=0;i<pArray.length;i++){
    var pLine=pArray[i];
    pLine.Sum_Right_Rate=pLine.Sum_Right/Sum_Right;
    pLine.Sum_Error_Rate=pLine.Sum_Error/Sum_Error;
    tmp_delta=pLine.Sum_Error_Rate-pLine.Sum_Right_Rate;
    if (tmp_delta>delta){
        delta=tmp_delta;
    }
}
s_out.println(delta);
s_sys.exit(0);




