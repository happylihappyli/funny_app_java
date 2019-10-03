function add(x){ 
    var y=2;
    return x*y;
}

var y = 10;
var x = add(100);
s_out.println(x);

if (y==100 && 1==1){
    y=add(100);
}else if (y==50){
    y=add(30);
}else{
    y=add(10);
}
s_out.println(y);
