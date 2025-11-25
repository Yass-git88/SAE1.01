import java.util.Scanner;

/*------------------------------------------*/
/* Classe IhmCui : Vue console              */
/* Exercice : Labyrinthe avec sortie et MVC */
/* Groupe : 12                              */
/* Membres : BUYANBADRAKH Ariunbayar, DIARRA Zachary, */
/*           EL MAADDI Yassine, LEMOINE Paul, MORVAN Quentin */
/* Date : 16/12/2024                        */
/*------------------------------------------*/
public class IhmCui
{
    private Controleur ctrl;

    /*------------------------------------------*/
    /* Constructeur                             */
    /*------------------------------------------*/
    public IhmCui(Controleur ctrl)
    {
        this.ctrl = ctrl;
    }

    /*------------------------------------------*/
    /* Affiche la grille                        */
    /*------------------------------------------*/
    public void afficherGrille()
    {
        int nbLignes = this.ctrl.getNbLignes();
        int nbColonnes = this.ctrl.getNbColonnes();

        for (int cpt1 = 0; cpt1 < nbLignes; cpt1++)
        {
            System.out.print("+");
            for (int cpt2 = 0; cpt2 < nbColonnes; cpt2++)
            {
                System.out.print("---+");
            }
            System.out.println();

            System.out.print("|");
            for (int cpt2 = 0; cpt2 < nbColonnes; cpt2++)
            {
                System.out.print(" " + this.ctrl.getCase(cpt1, cpt2) + " |");
            }
            System.out.println();
        }

        System.out.print("+");
        for (int cpt2 = 0; cpt2 < nbColonnes; cpt2++)
        {
            System.out.print("---+");
        }
        System.out.println();
    }

    /*------------------------------------------*/
    /* Lit une direction                        */
    /*------------------------------------------*/
    public char saisirDirection()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez une direction (N, O, S, E) :");

        char direction = sc.next().toUpperCase().charAt(0);
        while (direction != 'N' && direction != 'S' && direction != 'E' && direction != 'O')
        {
            System.out.println("Direction invalide. Entrez N, O, S, E:");
            direction = sc.next().toUpperCase().charAt(0);
        }

        return direction;
    }
}
