import java.io.FileReader;
import java.util.Scanner;

/*------------------------------------------*/
/* Classe représentant un labyrinthe        */
/* Exercice : 4                             */
/* Groupe : 12                              */
/* Membres : BUYANBADRAKH Ariunbayar, DIARRA Zachary, */
/*           EL MAADDI Yassine, LEMOINE Paul, MORVAN Quentin */
/* Date : 17/12/2024                        */
/*------------------------------------------*/
public class Labyrinthe
{
	/*------------------------------------------*/
	/* Attributs                                */
	/*------------------------------------------*/
	private int posLig;
	private int posCol;
	private char[][] grille;
	private int deplacements;

	/*------------------------------------------*/
	/* Constructeur : Initialise le labyrinthe  */
	/*------------------------------------------*/
	public Labyrinthe()
	{
		try (Scanner sc1 = new Scanner(new FileReader("data/grille.data")))
		{
			int hauteur = 0;
			int largeur = 0;
			this.deplacements = 0;

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
			this.grille = new char[hauteur][largeur];

			for (int cpt1 = 0; cpt1 < hauteur; cpt1++)
			{
				String ligne = sc2.nextLine();
				for (int cpt2 = 0; cpt2 < largeur; cpt2++)
				{
					if (cpt2 < ligne.length())
					{
						this.grille[cpt1][cpt2] = ligne.charAt(cpt2);

						if (this.grille[cpt1][cpt2] == 'O')
						{
							this.posLig = cpt1;
							this.posCol = cpt2;
						}
					}
					else
					{
						this.grille[cpt1][cpt2] = ' ';
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

	/*------------------------------------------*/
	/* Déplace l'objet dans la direction donnée */
	/*------------------------------------------*/
	public void deplacer(char direction)
	{
		int nouvelleLig = this.posLig;
		int nouvelleCol = this.posCol;

		switch (direction)
		{
			case 'N':
				nouvelleLig--;
				break;
			case 'S':
				nouvelleLig++;
				break;
			case 'E':
				nouvelleCol++;
				break;
			case 'O':
				nouvelleCol--;
				break;
			default:
				return;
		}

		if (nouvelleLig < 0)
		{
			nouvelleLig = this.grille.length - 1;
		}
		else if (nouvelleLig >= this.grille.length)
		{
			nouvelleLig = 0;
		}

		if (nouvelleCol < 0)
		{
			nouvelleCol = this.grille[0].length - 1;
		}
		else if (nouvelleCol >= this.grille[0].length)
		{
			nouvelleCol = 0;
		}

		if (this.grille[nouvelleLig][nouvelleCol] != '=')
		{
			this.grille[this.posLig][this.posCol] = ' ';
			this.posLig = nouvelleLig;
			this.posCol = nouvelleCol;


			if (this.grille[this.posLig][this.posCol] == '@')
			{
				System.out.println("Victoire en " + this.deplacements + " déplacements !");
				System.exit(0);
			}

			this.grille[this.posLig][this.posCol] = 'O';
			this.deplacements++;
		}
	}

	/*------------------------------------------*/
	/* Retourne le nombre de lignes de la grille */
	/*------------------------------------------*/
	public int getNbLignes()
	{
		return this.grille.length;
	}

	/*------------------------------------------*/
	/* Retourne le nombre de colonnes de la grille */
	/*------------------------------------------*/
	public int getNbColonnes()
	{
		return this.grille[0].length;
	}

	/*------------------------------------------*/
	/* Retourne le contenu d'une case           */
	/*------------------------------------------*/
	public char getCase(int lig, int col)
	{
		if (lig == this.posLig && col == this.posCol)
		{
			return 'O';
		}
		return this.grille[lig][col];
	}

	/*------------------------------------------*/
	/* Vérifie si la position actuelle est une sortie */
	/*------------------------------------------*/
	public boolean estSortie()
	{
		return this.grille[this.posLig][this.posCol] == '@';
	}
}

