/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TrabalhoED2;

/**
 *
 * @author rafa_
 */
public class Node {
    int info;    
    int fb;
    Node filho;      // filho mais a esquerda    
    Node irmaoPai;   // irmao a direita ou pai
     
    public Node(int info){
        this.info=info;
        filho = null;             
        irmaoPai = null;
        fb=0;
    }
    
    public Node pai(Node A){
        if(this.irmaoPai == null){
            return null;
        }else if(this.info < this.irmaoPai.info){
            if(this != this.irmaoPai.filho){
                return this.irmaoPai.irmaoPai;
            }else{
                return this.irmaoPai;
            }
        }else{
            return this.irmaoPai;
        }    
    }
    public Node dir(Node A){
        if(this.filho == null){
            return null;
        }else if(this.filho.info>this.info){
            return this.filho;
        }else if(this.filho.irmaoPai.info != this.info){
            return this.filho.irmaoPai;
        }else{
            return null;
        }
    }
    public Node esq(Node A){
        if(this.filho==null){
            return null;
        }else if(this.filho.info<this.info){
            return this.filho;
        }else{
            return null;
        }
    }
     
    
     
    

}
