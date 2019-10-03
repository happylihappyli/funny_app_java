package funnyai;

import Tools.M_File;
import Tools.MyVisitor;
import antlr_js.ECMAScriptLexer;
import antlr_js.ECMAScriptParser;
import com.funnyai.io.Old.S_File_Text;
import java.io.File;
import java.util.Arrays;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class JavaMain {
    public static boolean bDebug=false;
    public static String strPath="";
    public static M_File pFile=new M_File();
    public static String[] sys_args;
    
    public static void main(String[] args) {
        JavaMain.sys_args=args;
        //bDebug=false;
        
        
        String strFile=args[0];
        if (args.length>1){
            if ("1".equals(args[1])){
                bDebug=true;
            }
        }
        
        strPath=new File(strFile).getParent();
        String js_script=S_File_Text.Read(strFile, "utf-8",10000);
        

        ECMAScriptLexer lexer = new ECMAScriptLexer(CharStreams.fromString(js_script));
        ECMAScriptParser parser = new ECMAScriptParser(new CommonTokenStream(lexer));
        ECMAScriptParser.ProgramContext ctx = parser.program();
        if (JavaMain.bDebug){
            new TreeViewer(Arrays.asList(ECMAScriptParser.ruleNames), ctx).open();
        }
        
        MyVisitor visitor = new MyVisitor();
        visitor.visit(ctx);
        
    }
}
