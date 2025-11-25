import java.io.FileReader;
import java.util.Scanner;

/**
 * Classe Labyrinthe
 * Gère la structure et les déplacements dans le labyrinthe.
 * Exercice n°5
 * Groupe : 12
 * Auteurs : BUYANBADRAKH Ariunbayar, DIARRA Zachary, 
/*           EL MAADDI Yassine, LEMOINE Paul, MORVAN Quentin
 * Date de création : 17/12/2024
 */
public class Labyrinthe
{
	private int posLig;           // Position actuelle du personnage (ligne)
	private int posCol;           // Position actuelle du personnage (colonne)
	private char[][] grille;      // Représentation de la grille du labyrinthe
	private int nbLignes;         // Nombre total de lignes
	private int nbColonnes;       // Nombre total de colonnes
	private boolean cleBleueRecuperee;  // Indique si la clé bleue a été récupérée
	private boolean cleVerteRecuperee;  // Indique si la clé verte a été récupérée
	private boolean cleRougeRecuperee;  // Indique si la clé rouge a été récupérée
	private int posInitLig;       // Position initiale du personnage (ligne)
	private int posInitCol;       // Position initiale du personnage (colonne)

	/**
	 * Constructeur de la classe.
	 * Charge la grille depuis un fichier donné, @param fichier Chemin du fichier contenant le niveau
	 */
	public Labyrinthe(String fichier)
	{
		this.grille = this.chargerGrille(fichier);
	}

	/**
	 * Réinitialise la position du personnage à sa position de départ.
	 */
	public void reinitialiserPosition()
	{
		this.posLig = this.posInitLig;
		this.posCol = this.posInitCol;
	}

	/**
	 * Retourne le contenu d'une case de la grille.
	 * @param lig Ligne cible, col Colonne cible, @return Caractère représentant le contenu de la case
	 */
	public char getCase(int lig, int col)
	{
		if (lig < 0 || lig >= this.nbLignes || col < 0 || col >= this.nbColonnes)
		{
			return ' ';
		}
		return this.grille[lig][col];
	}

	/**
	 * Retourne le nombre de lignes dans la grille.
	 * @return Nombre de lignes
	 */
	public int getNbLignes()
	{
		return this.nbLignes;
	}

	/**
	 * Retourne le nombre de colonnes dans la grille.
	 * @return Nombre de colonnes
	 */
	public int getNbColonnes()
	{
		return this.nbColonnes;
	}

	/**
	 * Déplace le personnage dans une direction donnée.
	 * @param direction Direction du déplacement ('N', 'S', 'E', 'O')
	 */
	public void deplacer(char direction)
	{
		int nouvelleLig = this.posLig;
		int nouvelleCol = this.posCol;

		switch (direction)
		{
			case 'N':
				nouvelleLig--;
				if (nouvelleLig < 0)
				{
					nouvelleLig = this.nbLignes - 1; // Torique bas
				}
				break;

			case 'S':
				nouvelleLig++;
				if (nouvelleLig >= this.nbLignes)
				{
					nouvelleLig = 0; // Torique haut
				}
				break;

			case 'E':
				nouvelleCol = (this.posCol + 1) % this.nbColonnes; // Torique droite
				break;

			case 'O':
				nouvelleCol = (this.posCol - 1 + this.nbColonnes) % this.nbColonnes; // Torique gauche
				break;
		}

		if (this.estDeplacementValide(nouvelleLig, nouvelleCol))
		{
			this.posLig = nouvelleLig;
			this.posCol = nouvelleCol;
		}
	}

	/**
	 * Retourne la ligne actuelle du personnage.
	 * @return Ligne actuelle
	 */
	public int getPosLig()
	{
		return this.posLig;
	}

	/**
	 * Retourne la colonne actuelle du personnage.
	 * @return Colonne actuelle
	 */
	public int getPosCol()
	{
		return this.posCol;
	}

	/**
	 * Retourne le caractère de la case actuelle du personnage.
	 * @return Caractère de la case actuelle
	 */
	public char getCaseActuelle()
	{
		return this.grille[this.posLig][this.posCol];
	}

	/**
	 * Retourne vrai si le personnage est sur la case de sortie.
	 * @return true si le personnage est sur '@', sinon false
	 */
	public boolean estSortie()
	{
		return this.grille[this.posLig][this.posCol] == '@';
	}

	/**
	 * Charge la grille depuis un fichier texte.
	 * @param fichier Chemin du fichier contenant le niveau, @return Caractères représentant la grille
	 */
	public char[][] chargerGrille(String fichier)
	{
		try (Scanner sc = new Scanner(new FileReader(fichier)))
		{
			this.nbLignes = 0;
			int largeurMax = 0;

			while (sc.hasNextLine())
			{
				String ligne = sc.nextLine();
				this.nbLignes++;
				if (ligne.length() > largeurMax)
				{
					largeurMax = ligne.length();
				}
			}

			this.nbColonnes = largeurMax;
			this.grille = new char[this.nbLignes][this.nbColonnes];

			try (Scanner sc2 = new Scanner(new FileReader(fichier)))
			{
				int ligneIndex = 0;
				while (sc2.hasNextLine())
				{
					String ligne = sc2.nextLine();
					for (int colIndex = 0; colIndex < this.nbColonnes; colIndex++)
					{
						if (colIndex < ligne.length())
						{
							this.grille[ligneIndex][colIndex] = ligne.charAt(colIndex);
							if (this.grille[ligneIndex][colIndex] == '+')
							{
								this.posLig = ligneIndex;
								this.posCol = colIndex;
								this.posInitLig = ligneIndex;
								this.posInitCol = colIndex;
								this.grille[ligneIndex][colIndex] = ' ';
							}
						}
						else
						{
							this.grille[ligneIndex][colIndex] = ' ';
						}
					}
					ligneIndex++;
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("Erreur : niveau introuvable.");
		}
		return this.grille;
	}

	/**
	 * Supprime la porte associée à une clé.
	 * @param cle Caractère représentant la clé ('r', 'b', 'v')
	 */
	public void supprimerPorteAssociee(char cle)
	{
		for (int i = 0; i < this.nbLignes; i++)
		{
			for (int j = 0; j < this.nbColonnes; j++)
			{
				if ((cle == 'r' && this.grille[i][j] == 'R') ||
					(cle == 'b' && this.grille[i][j] == 'B') ||
					(cle == 'v' && this.grille[i][j] == 'V'))
				{
					this.grille[i][j] = ' ';
				}
			}
		}
	}

	/**
	 * Supprime la clé associée.
	 * @param cle Caractère représentant la clé ('r', 'b', 'v')
	 */
	public void supprimerClefAssociee(char cle)
	{
		for (int i = 0; i < this.nbLignes; i++)
		{
			for (int j = 0; j < this.nbColonnes; j++)
			{
				if ((cle == 'r' && this.grille[i][j] == 'r') ||
					(cle == 'b' && this.grille[i][j] == 'b') ||
					(cle == 'v' && this.grille[i][j] == 'v'))
				{
					this.grille[i][j] = ' ';
				}
			}
		}
	}

	/**
	 * Définit si la clé verte est récupérée.
	 * @param cleVerteRecuperee true si la clé verte est récupérée, sinon false
	 */
	public void setCleVerteRecuperee(boolean cleVerteRecuperee)
	{
		this.cleVerteRecuperee = cleVerteRecuperee;
	}

	/**
	 * Définit si la clé bleue est récupérée.
	 * @param cleBleueRecuperee true si la clé bleue est récupérée, sinon false
	 */
	public void setCleBleueRecuperee(boolean cleBleueRecuperee)
	{
		this.cleBleueRecuperee = cleBleueRecuperee;
	}

	/**
	 * Définit si la clé rouge est récupérée.
	 * @param cleRougeRecuperee true si la clé rouge est récupérée, sinon false
	 */
	public void setCleRougeRecuperee(boolean cleRougeRecuperee)
	{
		this.cleRougeRecuperee = cleRougeRecuperee;
	}
	
	/**
	 * Vérifie si un déplacement est valide.
	 * @param lig Ligne cible, col Colonne cible, @return true si le déplacement est valide, sinon false
	 */
	private boolean estDeplacementValide(int lig, int col)
	{
		if (lig < 0 || lig >= this.nbLignes || col < 0 || col >= this.nbColonnes)
		{
			return false;
		}

		char contenu = this.grille[lig][col];

		if ((contenu == 'B' && !this.cleBleueRecuperee) ||
			(contenu == 'V' && !this.cleVerteRecuperee) ||
			(contenu == 'R' && !this.cleRougeRecuperee))
		{
			return false;
		}
		return contenu != '=';
	}
}
