package funnyai;

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
    public static boolean bDebug=true;
    public static String strPath="";
    
    public static void main(String[] args) {
        //bDebug=false;
        
        
        String strFile=args[0];
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
