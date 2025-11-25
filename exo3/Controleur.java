import ihmgui.Controle;
import ihmgui.FrameGrille;

/*------------------------------------------*/
/* Classe Controleur : Contrôle l'IHM GUI   */
/* Exercice : 3                             */
/* Groupe : 12                              */
/* Membres : BUYANBADRAKH Ariunbayar, DIARRA Zachary, */
/*           EL MAADDI Yassine, LEMOINE Paul, MORVAN Quentin */
/* Date : 16/12/2024                        */
/*------------------------------------------*/

public class Controleur extends Controle
{
    private Labyrinthe metier;
    private FrameGrille frame;

    /*------------------------------------------*/
    /* Constructeur                             */
    /*------------------------------------------*/
    public Controleur()
    {
        this.metier = new Labyrinthe();
        this.frame = new FrameGrille(this);

        this.frame.setSize(650, 700);
        this.frame.setLocation(200, 10);
        this.frame.setTitle("Labyrinthe GUI");
        this.frame.setVisible(true);
    }

    /*------------------------------------------*/
    /* Retourne le nombre de lignes de la grille */
    /*------------------------------------------*/
    public int setNbLigne()
    {
        return this.metier.getNbLignes();
    }

    /*------------------------------------------*/
    /* Retourne le nombre de colonnes de la grille */
    /*------------------------------------------*/
    public int setNbColonne()
    {
        return this.metier.getNbColonnes();
    }

    /*------------------------------------------*/
    /* Associe les images aux éléments de la grille */
    /*------------------------------------------*/
    public String setImage(int ligne, int colonne, int couche)
    {
        String chemin = "./images/";

        if (couche == 0)
        {
            char contenu = this.metier.getCase(ligne, colonne);

            switch (contenu)
            {
                case 'O':
                    return chemin + "bille.png";
                case '=':
                    return chemin + "mur.png";
                case '@':
                    return chemin + "sortie.png";
                default:
                    return chemin + "vide30.png";
            }
        }

        return null;
    }

    /*------------------------------------------*/
    /* Largeur des images dans la grille        */
    /*------------------------------------------*/
    public int setLargeurImg()
    {
        return 30;
    }

    /*------------------------------------------*/
    /* Gère les déplacements via les touches    */
    /*------------------------------------------*/
    public void jouer(String touche)
    {

        if (touche.equals("FL-H"))
        {
            this.metier.deplacer('N');
        }

        if (touche.equals("FL-B"))
        {
            this.metier.deplacer('S');
        }

        if (touche.equals("FL-G"))
        {
            this.metier.deplacer('O');
        }

        if (touche.equals("FL-D"))
        {
            this.metier.deplacer('E');
        }

        if (touche.equals("Z"))
        {
            this.metier.deplacer('N');
        }

        if (touche.equals("S"))
        {
            this.metier.deplacer('S');
        }

        if (touche.equals("Q"))
        {
            this.metier.deplacer('O');
        }

        if (touche.equals("D"))
        {
            this.metier.deplacer('E');
        }

        this.frame.majIHM();

        if (this.metier.estSortie())
        {
            System.out.println("Félicitations, vous avez atteint la sortie !");
            this.frame.fermer();
        }
    }

    /*------------------------------------------*/
    /* Définit l'image de fond de la grille     */
    /*------------------------------------------*/
    public String setFondGrille()
    {
        return "./images/fond.png";
    }

    /*------------------------------------------*/
    /* Méthode principale                       */
    /*------------------------------------------*/
    public static void main(String[] args)
    {
        new Controleur();
    }
}

