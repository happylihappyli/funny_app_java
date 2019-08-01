function C_Line (){
    this.Score=0; 
    this.Right=0;
    this.Error=0;
    
    this.Sum_Right=0;
    this.Sum_Error=0;
    
    this.Sum_Right_Rate=0;
    this.Sum_Error_Rate=0;
}

var p=new C_Line();
p.Score=11;
out.println("hello！"+p.Score);