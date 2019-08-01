/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funnyai;

/**
 *
 * @author happyli
 */
public class SYS {
    private String[] args=null;
    
    SYS(String[] args) {
        this.args=args.clone();
    }
    
    public String args(int index){
        return this.args[index];
    }
    
    public void Debug(String... a){
        for (String a1 : a) {
            System.out.println(a1);
        }
    }
}
