/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;
import org.chocosolver.samples.AbstractProblem;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VariableFactory;
import org.chocosolver.solver.search.strategy.IntStrategyFactory;
import org.chocosolver.solver.Solver;
/**
 *
 * @author yv965015
 */
public class Projet extends AbstractProblem
{
    private final int nbMax=8;
    private final int nb=4;
    IntVar[] allQueens;
    int[][] initialQueens=new int[nbMax][nbMax];  
    /*Les colonnes et lignes sont numérotées de 0 à 1
    /* génération des premières reines bien placée*/
    public void placerReines(int numQueen){
        if(numQueen==nb){
            /*sortie*/
        }
        else{
            int queen=0;
            boolean interdit;
            interdit=false;
            int colonne = 0;
            queen =(int)( Math.random()*( nbMax + 1 ));
            for(int j = 0; j < nbMax; j++){
                if(initialQueens[queen][j]!=0){
                   interdit=true;
                }
                else{
                    colonne=j;
                    j=nbMax;
                    initialQueens[queen][colonne]=1;
                }
                
            }
            if(!interdit){
                for(int i=0;i<nbMax;i++){
                    for(int j=0;j<nbMax;j++){
                        /*on tag les places non interdites à interdites*/
                        if(i==queen||colonne==j){/*Est en diagonale OU sur ligne OU sur colonne*/
                             if(initialQueens[queen][j]==0){
                                initialQueens[queen][j]=8;//Bloquée
                            }
                        }
                    }
                }
            }
            placerReines(numQueen+1);
        }  
    }

    public static void main(String[] args){ 
        
    }
    
    @Override
    public void createSolver(){
        this.solver = new Solver("Projet");
    }
    
    @Override
    public void buildModel(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void configureSearch(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void solve(){
        solver.findSolution();
    }
    
    @Override
    public void prettyOut(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }   
}
