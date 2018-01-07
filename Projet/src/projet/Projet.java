/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    private int nbMax = 8;
    private int nb = 2;
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

    public void placerReines2(){
        ArrayList<Integer> rdmQueens = new ArrayList<Integer>();
        int numQueen = 0;
        boolean correct = false;
        boolean tmpCorrect=false;
        int ligneI = -1;
        int colonneI = -1;
        int ligneJ = -1;
        int colonneJ = -1;        
        //init du tableau pour qu'il n y ait aucune collision lignes/colonnes
        for(int i=0;i<nbMax;i++){
            rdmQueens.add(i);//ajoute l'entier à la fin de la liste
        }
        while(!correct){
            initTableaux();
            tmpCorrect=true;
            Collections.shuffle(rdmQueens);
            for(int i=0;i<nb;i++){
                // on pose les n premières reines sur l'échiquier
                ligneI = i;
                colonneI = rdmQueens.get(i);   
                initialQueens[ligneI][colonneI]=1;
            }
             //Verification des conflits
            for(int i=0;i<nb;i++){
                // on vérifie les reines posées
                ligneI = i;
                colonneI = rdmQueens.get(i);   
                for(int j =0;j<nb;j++){
                    if(i!=j){
                        ligneJ = j;
                        colonneJ = rdmQueens.get(j); 
                        if( abs(ligneJ - ligneI) == abs(colonneI - colonneJ)){//Vérification des diagonales
                            // Si conflit -> on réinitialise
                            tmpCorrect = false;
                            j=nb;
                            i=nb;
                        }
                    }
                }
            } 
            correct = tmpCorrect;
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
                        //System.out.println("Nouvel essai");
                        numQueen=0;
                    }
                }
            }
        }
    }

    public void export_fichier()
    {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        
        try {
            File imp = new File("import.csv");
            fis = new FileInputStream(new File("file.txt"));
            fos = new FileOutputStream(imp,true);
            
            InputStreamReader ipsr=new InputStreamReader(fis);
            BufferedReader br=new BufferedReader(ipsr);
            String slue;
            String s="";
            while ((slue=br.readLine())!=null){
                    s+=slue;
            }
            s=s.replace(',', '.');
            s=s.replace(" : ", ",");
            s=s.replace(": ", ",");
            br.close();
            String sinit="Reines Max,"+nbMax+",Reines placees,"+nb+"\n";
            s+=sinit;
            fos.write(s.getBytes());
        
        } catch (FileNotFoundException e) {
            //FileInputStream ne trouve aucun fichier
            e.printStackTrace();
        } catch (IOException e) {
            //erreur écriture ou lecture
            e.printStackTrace();
        } 
        
        finally {
            // On ferme nos flux de données dans un bloc finally pour s'assurer
            // que ces instructions seront exécutées dans tous les cas même si
            // une exception est levée !
            try {
                if (fis != null)
                fis.close();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (fos != null)
                fos.close();
            }
            catch (IOException e) {
               e.printStackTrace();
            }
        }  
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        int max = 20;
        int nbPlacees = 0;
        int nbR = 0;
        for(int i =0;i<1000;i++){
            Projet p = new Projet();
            nbR = (int)(Math.random()*(max-1))+1;
            nbPlacees = (int)(Math.random()*(nbR-1));
            p.setNbMax(nbR);
            p.setNb(nbPlacees);
            FileOutputStream f = new FileOutputStream("file.txt");
            //System.setOut(new PrintStream(f));
            // Create a stream to hold the output
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            // IMPORTANT: Save the old System.out!
            PrintStream old = System.out;
            // Tell Java to use your special stream
            System.setOut(ps);
            p.initTableaux();
            p.placerReines2();
            p.execute(args);
            // Put things back
            System.out.flush();
            System.setOut(old);
            String baostemp=baos.toString().replace("\n\t",":");
            String[] res=baostemp.split(":");;
            String toWrite="";
            for(int j =0;j<res.length;j++){
                if(res[j].equals("Solutions")||res[j].equals("Building time ")){
                    toWrite+=res[j]+":"+res[j+1]+": ";
                }
            }
            // Show what happened
            //Pour écriture fichier + console
            f.write(toWrite.getBytes());
            //baos.writeTo(f);
            // p.displayTableau();
            f.close();
            p.export_fichier();
        }

    }

    public int getNbMax() {
        return nbMax;
    }

    public void setNbMax(int nbMax) {
        this.nbMax = nbMax;
        this.initialQueens = new int[nbMax][nbMax];
        this.tableauvide = new int[nbMax][nbMax];
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
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
