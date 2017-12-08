/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import static java.lang.Math.abs;
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

    private final int nbMax = 8;
    private final int nb = 5;
    IntVar[] allQueens;
    int[][] initialQueens = new int[nbMax][nbMax];
    int[][] tableauvide = new int[nbMax][nbMax];
    private IntVar[] vars;
    /*Les colonnes et lignes sont numérotées de 0 à 1
    /* génération des premières reines bien placée*/
    public void initTableaux()
    {
        for (int i = 0; i < nbMax; i++)
        {
            for (int j = 0; j < nbMax; j++)
            {
                initialQueens[i][j] = 0;
                tableauvide[i][j] = 0;
            }
        }
    }

    public void displayTableau()
    {
        for (int i = 0; i < nbMax; i++)
        {
            for (int j = 0; j < nbMax; j++)
            {
                System.out.print(initialQueens[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void placerReines()
    {
        int numQueen = 0;
        while (numQueen < nb)
        {
            if (numQueen == nb)
            {
                /*sortie*/
            }
            else
            {
                int queen = 0;
                boolean interdit;
                interdit = false;
                int colonne = (int) (Math.random() * (nbMax));
                queen = (int) (Math.random() * (nbMax));
                boolean caselibre = false;
                //Si la case est libre
                if (initialQueens[queen][colonne] == 0)
                {
                    initialQueens[queen][colonne] = 1;
                    for (int i = 0; i < nbMax; i++)
                    {
                        for (int j = 0; j < nbMax; j++)
                        {
                            /*on tag les places non interdites à interdites*/
                            if (i == queen || colonne == j || abs(queen - i) == abs(colonne - j))
                            {/*Est en diagonale OU sur ligne OU sur colonne*/
                                if (initialQueens[i][j] == 0)
                                {
                                    initialQueens[i][j] = 8;//Bloquée
                                }
                            }
                            if (initialQueens[i][j] == 0)
                            {
                                caselibre = true;
                            }
                        }
                    }
                    if (caselibre)
                    {
                        // System.out.println("passage reine suivante");
                        numQueen++;
                    }
                    else
                    {
                        initialQueens = tableauvide;
                        numQueen=0;
                        System.out.println("Nouvel essai très le oui");
                    }
                }
                else
                {
                    for (int i = 0; i < nbMax; i++)
                    {
                        for (int j = 0; j < nbMax; j++)
                        {
                            if (initialQueens[i][j] == 0)
                            {
                                caselibre = true;
                            }
                        }
                    }
                    if (!caselibre)
                    {
                        initialQueens = tableauvide;
                        System.out.println("Nouvel essai très le oui");
                        numQueen=0;
                    }
                }
            }
        }
    }

    public static void main(String[] args)
    {
        Projet p = new Projet();
        p.initTableaux();
        p.placerReines();
        p.execute(args);
        p.displayTableau();
    }

    @Override
    public void createSolver()
    {
        this.solver = new Solver("Projet");
    }

    @Override
    public void buildModel()
    {
        int[] taken=new int[nbMax];
        for(int i =0; i <nbMax ; i++){
            taken[i]=-1;
        }
        this.vars = new IntVar[nbMax];
        for(int i =0; i <nbMax ; i++){
            for(int j =0; j <nbMax ; j++){
                if(initialQueens[i][j]==1){
                    taken[j]=i;
                }
            }
        }
        for (int i = 0; i < vars.length; i++)
        {
            if(taken[i]==-1){
               this.vars[i] = VariableFactory.enumerated("Q_" + i, 0, nbMax-1, solver);// 1,n domaine-> valeurs de 1 à n  
            }
            else{
                this.vars[i] = VariableFactory.enumerated("Q_" + i,taken[i] , taken[i], solver);// 1,n domaine-> valeurs de 1 à n  
            }
        }
        this.solver.post(IntConstraintFactory.alldifferent(vars, "AC"));
        for (int i = 0; i < nbMax - 1; i++)
        {
            for (int j = i + 1; j < nbMax ; j++)
            {
                int k = j - i;
                this.solver.post(IntConstraintFactory.arithm(vars[i], "!=", vars[j], "+", -k));
                this.solver.post(IntConstraintFactory.arithm(vars[i], "!=", vars[j], "+", k));
            }
        }
    }

    @Override
    public void configureSearch()
    {
        
    }

    @Override
    public void solve()
    {
        solver.findSolution();
    }

    @Override
    public void prettyOut()
    {

    }
}
