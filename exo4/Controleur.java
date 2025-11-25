import ihmgui.FrameGrille;
import ihmgui.Controle;

/*------------------------------------------*/
/* Classe Controleur : Contrôle l'IHM GUI   */
/* Exercice n°4                             */
/* Groupe : 12                              */
/* Membres : BUYANBADRAKH Ariunbayar, DIARRA Zachary, */
/*           EL MAADDI Yassine, LEMOINE Paul, MORVAN Quentin */
/* Date : 17/12/2024                        */
/*------------------------------------------*/
public class Controleur extends Controle
{
	private Labyrinthe metier;       // Instance de la classe métier
	private FrameGrille frame;       // Interface graphique (IHM)

	/*------------------------------------------*/
	/* Constructeur                             */
	/*------------------------------------------*/
	public Controleur()
	{
		// Initialisation du labyrinthe et de l'interface graphique
		this.metier = new Labyrinthe();
		this.frame = new FrameGrille(this);

		// Configuration de la fenêtre principale
		this.frame.setSize(1000, 520);
		this.frame.setLocation(200, 10);
		this.frame.setTitle("Labyrinthe GUI - Murs variés");
		this.frame.setVisible(true);
	}

	/*------------------------------------------*/
	/* Retourne le nombre de lignes de la grille */
	/*------------------------------------------*/
	public int setNbLigne()
	{
		// Récupération du nombre de lignes dans la grille
		return this.metier.getNbLignes();
	}

	/*------------------------------------------*/
	/* Retourne le nombre de colonnes de la grille */
	/*------------------------------------------*/
	public int setNbColonne()
	{
		// Récupération du nombre de colonnes dans la grille
		return this.metier.getNbColonnes();
	}

	/*------------------------------------------*/
	/* Détermine l'image en fonction du voisinage */
	/*------------------------------------------*/
	public String setImage(int ligne, int colonne, int couche)
	{
		String chemin = "./images/";

		if (couche == 0)
		{
			// Récupération du contenu de la case
			char contenu = this.metier.getCase(ligne, colonne);

			// Déterminer l'image correspondant au contenu de la case
			if (contenu == 'O')
			{
				return chemin + "boule_verte.gif";
			}
			if (contenu == '@')
			{
				return chemin + "sortie.gif";
			}
			if (contenu == '=')
			{
				// Déterminer le type de mur en fonction des voisins
				boolean nord = this.voisinEstUnMur(ligne - 1, colonne);
				boolean sud = this.voisinEstUnMur(ligne + 1, colonne);
				boolean est = this.voisinEstUnMur(ligne, colonne + 1);
				boolean ouest = this.voisinEstUnMur(ligne, colonne - 1);

				// Retourner l'image en fonction des directions des murs
				if (nord && sud && est && ouest)
				{
					return chemin + "haie_nose.gif";
				}
				if (nord && sud && est)
				{
					return chemin + "haie_nse.gif";
				}
				if (nord && sud && ouest)
				{
					return chemin + "haie_nos.gif";
				}
				if (nord && ouest && est)
				{
					return chemin + "haie_noe.gif";
				}
				if (sud && ouest && est)
				{
					return chemin + "haie_ose.gif";
				}
				if (nord && sud)
				{
					return chemin + "haie_ns.gif";
				}
				if (ouest && est)
				{
					return chemin + "haie_oe.gif";
				}
				if (nord && ouest)
				{
					return chemin + "haie_no.gif";
				}
				if (nord && est)
				{
					return chemin + "haie_ne.gif";
				}
				if (sud && ouest)
				{
					return chemin + "haie_os.gif";
				}
				if (sud && est)
				{
					return chemin + "haie_se.gif";
				}
				if (nord)
				{
					return chemin + "haie_n.gif";
				}
				if (sud)
				{
					return chemin + "haie_s.gif";
				}
				if (ouest)
				{
					return chemin + "haie_o.gif";
				}
				if (est)
				{
					return chemin + "haie_e.gif";
				}
				return chemin + "haie.gif"; // Image par défaut pour un mur
			}
			return chemin + "haie_vide.gif"; // Image par défaut pour une case vide
		}
		return null; // Aucun affichage pour les autres couches
	}

	/*------------------------------------------*/
	/* Retourne la largeur des images           */
	/*------------------------------------------*/
	public int setLargeurImg()
	{
		return 40; // Largeur fixe des images
	}

	/*------------------------------------------*/
	/* Gère les déplacements                    */
	/*------------------------------------------*/
	public void jouer(String touche)
	{
		// Déplacer selon la touche pressée
		if (touche.equals("FL-H") || touche.equals("Z"))
		{
			this.metier.deplacer('N'); // Déplacement vers le Nord
		}
		if (touche.equals("FL-B") || touche.equals("S"))
		{
			this.metier.deplacer('S'); // Déplacement vers le Sud
		}
		if (touche.equals("FL-G") || touche.equals("Q"))
		{
			this.metier.deplacer('O'); // Déplacement vers l'Ouest
		}
		if (touche.equals("FL-D") || touche.equals("D"))
		{
			this.metier.deplacer('E'); // Déplacement vers l'Est
		}

		this.frame.majIHM(); // Mise à jour de l'affichage

		// Fermer l'IHM si la sortie est atteinte
		if (this.metier.estSortie())
		{
			this.frame.fermer();
		}
	}

	/*------------------------------------------*/
	/* Définit l'image de fond                  */
	/*------------------------------------------*/
	public String setFondGrille()
	{
		return "./images/haie_vide.gif"; // Image de fond par défaut
	}
	
	/*------------------------------------------*/
	/* Méthode principale                       */
	/*------------------------------------------*/
	public static void main(String[] args)
	{
		// Lancer l'application en initialisant le contrôleur
		new Controleur();
	}
	
	/*------------------------------------------*/
	/* Méthode pour vérifier si un voisin est un mur */
	/*------------------------------------------*/
	private boolean voisinEstUnMur(int lig, int col)
	{
		// Vérifier si les coordonnées sont hors limites
		if (lig < 0 || lig >= this.metier.getNbLignes() || col < 0 || col >= this.metier.getNbColonnes())
		{
			return false; // Considérer comme non-mur
		}
		// Retourner vrai si la case est un mur
		return this.metier.getCase(lig, col) == '=';
	}

}

