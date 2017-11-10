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
    private final int nb = 2;
    IntVar[] allQueens;
    int[][] initialQueens = new int[nbMax][nbMax];
    int[][] tableauvide = new int[nbMax][nbMax];

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
                System.out.print(initialQueens[i][j]+" ");
            }
            System.out.println();
        }
    }

    public void placerReines(int numQueen)
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
                initialQueens[queen][colonne] =1;
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
                if (caselibre && numQueen < nb)
                {
                    placerReines(numQueen + 1);
                }
                else
                {
                    initialQueens = tableauvide;
                    placerReines(0);
                }
            }
            else
            {
                placerReines(numQueen);
            }
        }
    }

    public static void main(String[] args)
    {
        Projet p = new Projet();
        p.initTableaux();
        p.placerReines(0);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void configureSearch()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void solve()
    {
        solver.findSolution();
    }

    @Override
    public void prettyOut()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
