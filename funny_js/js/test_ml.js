

s_ml.GBDT("E:\\Test\\train.txt",19,100);



var y;


s_file.read_begin("file1","E:\\Test\\train.txt");

var count=1;
var line=s_file.read_line("file1");
while(line!=null){
    count+=1;
    if (count>50) break;
    y=s_ml.GBDT_predict(line,19);
    s_out.println(y);
    
    line=s_file.read_line("file1");
}
s_file.close("file1");


s_sys.sleep(10);

s_sys.exit(0);




