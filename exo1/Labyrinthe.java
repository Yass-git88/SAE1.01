public class Carte
{
    private int[][] ensHauteurs;

    public Carte(String fichier)
    {
        try (Scanner sc1 = new Scanner(new FileReader(fichier)))
        {
            int hauteur = 0;
            int largeur = 0;

            while (sc1.hasNextLine())
            {
                String ligne = sc1.nextLine();
                if (!ligne.equals(""))
                {
                    hauteur++;
                    if (ligne.length() > largeur)
                    {
                        largeur = ligne.length();
                    }
                }
            }

            sc1.close();
            Scanner sc2 = new Scanner(new FileReader("data/grille.data"));
            this.ensHauteur = new int[hauteur][largeur];

            for (int cpt1 = 0; cpt1 < hauteur; cpt1++)
            {
                String ligne = sc2.nextLine();
                for (int cpt2 = 0; cpt2 < largeur; cpt2++)
                {
                    if (cpt2 < ligne.length())
                    {
                        this.ensHauteur[cpt1][cpt2] = ligne.charAt(cpt2);
                        if (this.ensHauteur[cpt1][cpt2] == 'O')
                        {
                            this.posLig = cpt1;
                            this.posCol = cpt2;
                        }
                    }
                    else
                    {
                        this.ensHauteur[cpt1][cpt2] = ' '; 
                    }
                }
            }
            sc2.close();
        }
        catch (Exception e)
        {
            System.out.println("Erreur lors du chargement de la grille.");
        }
    }
    
    public int getNbLigne()
    {
        return this.ensHauteur.length;
    }   

    public int getNbColonne()
    {
        return this.ensHauteur[0].length;
    }

    public int getVal (int lig, int col)
    {
        if (lig < 0 || lig >this.getNbLigne()) 
        {
            return null;
        }
        if (col < 0 || col > this.getNbColonne())
        {
            return null;
        }
        return this.ensHauteur[lig][col];
    }

    public String toString()
    {
        String retour;
        int lig,col;

        retour = "+---------------------------------------+";
        lig = 0;
        while (lig < ensHauteurs)
    }

}
