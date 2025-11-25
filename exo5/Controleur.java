import ihmgui.FrameGrille;
import ihmgui.Controle;

/**
 * Classe Controleur
 * Gère la structure et les déplacements dans le labyrinthe.
 * Exercice n°5
 * Groupe : 12
 * Auteurs : BUYANBADRAKH Ariunbayar, DIARRA Zachary, 
/*           EL MAADDI Yassine, LEMOINE Paul, MORVAN Quentin
 * Date de création : 17/12/2024
 */
 
public class Controleur extends Controle 
{
	private Labyrinthe metier;
	private FrameGrille frame;
	private String imagePerso;
	private boolean alterneImageMontee;
	private boolean clefRougeRecuperee;
	private boolean clefVerteRecuperee;
	private boolean clefBleueRecuperee;
	private int niveauActuel;

	public Controleur() 
	{
		this.niveauActuel = 1; // Le jeu démarre au niveau 1
		this.metier = new Labyrinthe(getNomFichierNiveau());
		this.frame = new FrameGrille(this);
		imagePerso = "pers_droit.png";
		alterneImageMontee = false;
		frame.setSize(1100, 725);
		frame.setLocation(200, 10);
		frame.setTitle("Jeu de plateforme");
		frame.setVisible(true);
		clefRougeRecuperee = false;
		clefVerteRecuperee = false;
		clefBleueRecuperee = false;

		// Démarrage automatique de la gestion de la chute
		gererChuteAutomatique();
	}
	
	
	public String setImage(int ligne, int colonne, int couche)
	{
		String chemin = setGrille();
		char contenu = metier.getCase(ligne, colonne);
		boolean solGauche = (colonne > 0) && (metier.getCase(ligne, colonne - 1) == '=');
		boolean solDroite = (colonne < metier.getNbColonnes() - 1) && 
		(metier.getCase(ligne, colonne + 1) == '=');

		if (couche == 0) // Sols, portes et échelles
		{
		    if (contenu == '+')
		    {
		        return chemin + imagePerso;
		    }
		    if (contenu == '/')
		    {
		        return chemin + "caisse.png";
		    }

		    if (contenu == '=')
		    {
		        if (solGauche && solDroite)
		        {
		            return chemin + "sol.png";
		        }
		        else if (!solGauche && solDroite)
		        {
		            return chemin + "sol_gauche.png";
		        }
		        else if (solGauche && !solDroite)
		        {
		            return chemin + "sol_droit.png";
		        }
		        else
		        {
		            return chemin + "sol_gauche_droit.png";
		        }
		    }
		    else if (contenu == '#')
		    {
		        // Vérifie si la case au-dessus est une échelle
		        if (ligne > 0 && metier.getCase(ligne - 1, colonne) != '#')
		        {
		            return chemin + "echelle_haut.png";
		        }
		        return chemin + "echelle.png";
		    }
		    else if (contenu == 'B') // Porte bleue
		    {
		        if (clefBleueRecuperee)
		        {
		            return chemin + "vide52.png";
		        }
		        else
		        {
		            return chemin + "portebleue.png";
		        }
		    }
		    else if (contenu == 'R') // Porte rouge
		    {
		        if (clefRougeRecuperee)
		        {
		            return chemin + "vide52.png";
		        }
		        else
		        {
		            return chemin + "porterouge.png";
		        }
		    }
		    else if (contenu == 'V') // Porte verte
		    {
		        if (clefVerteRecuperee)
		        {
		            return chemin + "vide52.png";
		        }
		        else
		        {
		            return chemin + "porteverte.png";
		        }
		    }
		    else if (contenu == '@') // Sortie
		    {
		        return chemin + "sortie.png";
		    }
		    return chemin + "vide52.png";
		}
		else if (couche == 1) // Objets collectables
		{
		    if (contenu == 'b')
		    {
		        return chemin + "clebleue.png";
		    }
		    if (contenu == 'r')
		    {
		        return chemin + "clerouge.png";
		    }
		    if (contenu == 'v')
		    {
		        return chemin + "cleverte.png";
		    }
		    return chemin + "vide52.png";
		}
		else if (couche == 2) // Personnage
		{
		    if (ligne == metier.getPosLig() && colonne == metier.getPosCol())
		    {
		        return chemin + imagePerso;
		    }
		    return chemin + "vide52.png";
		}
		return chemin + "vide52.png";
	}
	
	public void jouer(String touche)
	{
		int ligneActuelle = metier.getPosLig();
		int ligneDessous = ligneActuelle + 1;
		int ligneDessus = ligneActuelle - 1;
		int colonneActuelle = metier.getPosCol();
		int colonneGauche = colonneActuelle - 1;
		int colonneDroite = colonneActuelle + 1;

		String imageEnDessous;
		if (ligneDessous < metier.getNbLignes()) {
		    imageEnDessous = setImage(ligneDessous, colonneActuelle, 0);
		} 
		else 
		{
		    imageEnDessous = "";
		}

		String imageAuDessus;
		if (ligneDessus >= 0) {
		    imageAuDessus = setImage(ligneDessus, colonneActuelle, 0);
		} 
		else 
		{
		    imageAuDessus = "";
		}

		String imageActuelle = setImage(ligneActuelle, colonneActuelle, 0);

		boolean estSurSol = imageEnDessous.contains("sol");
		boolean estSurEchelle = imageActuelle.contains("echelle");
		boolean chutePossible = !imageEnDessous.contains("sol") 
		&& !imageEnDessous.contains("echelle");

		String imageADroite;
		if (colonneDroite < metier.getNbColonnes()) 
		{
		    imageADroite = setImage(ligneActuelle, colonneDroite, 0);
		} 
		else 
		{
		    imageADroite = "";
		}

		String imageEnDessousDroite;
		if (ligneDessous < metier.getNbLignes()) 
		{
		    imageEnDessousDroite = setImage(ligneDessous, colonneDroite, 0);
		} 
		else 
		{
		    imageEnDessousDroite = "";
		}

		String imageAGauche;
		if (colonneGauche >= 0) 
		{
		    imageAGauche = setImage(ligneActuelle, colonneGauche, 0);
		} 
		else 
		{
		    imageAGauche = "";
		}

		String imageEnDessousGauche;
		if (ligneDessous < metier.getNbLignes()) 
		{
		    imageEnDessousGauche = setImage(ligneDessous, colonneGauche, 0);
		} 
		else 
		{
		    imageEnDessousGauche = "";
		}

		switch (touche)
		{
		    case "FL-B": // Descente
		        if (imageEnDessous.contains("sol")) 
		        {
		            break; // Empêche la descente si un sol est en dessous
		        }

		        if (estSurEchelle) {
		            // Gestion torique pour la descente
		            if (ligneDessous >= metier.getNbLignes()) 
		            {
		                ligneDessous = 0; // Torique vers la première ligne
		                imageEnDessous = setImage(ligneDessous, colonneActuelle, 0);
		            }

		            if (!imageEnDessous.contains("sol") && !imageEnDessous.contains("echelle")) 
		            {
		                break; // Annule la descente
		            }
		        }

		        metier.deplacer('S');
		        if (estSurEchelle) 
		        {
		            if (alterneImageMontee) 
		            {
		                imagePerso = "pers_monte1.png";
		            } 
		            else 
		            {
		                imagePerso = "pers_monte2.png";
		            }
		            alterneImageMontee = !alterneImageMontee;
		        }
		        break;

		    case "FL-H": // Montée
		        if (estSurEchelle) 
		        {
		            // Gestion torique pour la montée
		            if (ligneDessus < 0) 
		            {
		                ligneDessus = metier.getNbLignes() - 1; // Torique vers la dernière ligne
		                imageAuDessus = setImage(ligneDessus, colonneActuelle, 0);
		            }

		            if (!imageAuDessus.contains("echelle") && ligneActuelle != 0) 
		            {
		                break; // Annule la montée
		            }

		            metier.deplacer('N');
		            if (alterneImageMontee) 
		            {
		                imagePerso = "pers_monte1.png";
		            } 
		            else 
		            {
		                imagePerso = "pers_monte2.png";
		            }
		            alterneImageMontee = !alterneImageMontee;
		        }
		        break;

		    case "FL-G": // Déplacement gauche
		        if (estSurSol && !imagePerso.equals("pers_gauche.png")) 
		        {
		            imagePerso = "pers_gauche.png";
		            break;
		        }

		        if (estSurEchelle) 
		        {
		            if (imageAGauche.contains("vide52") && imageEnDessousGauche.contains("sol")) 
		            {
		                metier.deplacer('O');
		                imagePerso = "pers_gauche.png";
		                break;
		            }

		            if (imageAGauche.contains("sol") || (imageAGauche.contains("vide52") && 
		               !imageEnDessousGauche.contains("sol"))) 
		            {
		                break;
		            }

		            if (imageAGauche.contains("echelle") && 
		               (imageEnDessousGauche.contains("echelle") || imageEnDessousGauche.contains("vide52")))
		                {
		                metier.deplacer('O');
		                imagePerso = "pers_monte2.png";
		                break;
		            }
		        }

		        if (!chutePossible) 
		        {
		            if (imageAGauche.contains("echelle") && 
		               (imageEnDessousGauche.contains("echelle") || imageEnDessousGauche.contains("vide52")))
		                {
		                metier.deplacer('O');
		                imagePerso = "pers_monte2.png";
		            } 
		            else 
		            {
		                metier.deplacer('O');
		                imagePerso = "pers_gauche.png";
		            }
		        }
		        break;

		    case "FL-D": // Déplacement droite
		        if (estSurSol && !imagePerso.equals("pers_droit.png")) 
		        {
		            imagePerso = "pers_droit.png";
		            break;
		        }

		        if (estSurEchelle) {
		            if (imageADroite.contains("vide52") && imageEnDessousDroite.contains("sol")) 
		            {
		                metier.deplacer('E');
		                imagePerso = "pers_droit.png";
		                break;
		            }

		            if (imageADroite.contains("sol") || (imageADroite.contains("vide52") && 
		            !imageEnDessousDroite.contains("sol"))) 
		            {
		                break;
		            }

		            if (imageADroite.contains("echelle") && 
		               (imageEnDessousDroite.contains("echelle") || imageEnDessousDroite.contains("vide52")))
		                {
		                metier.deplacer('E');
		                imagePerso = "pers_monte1.png";
		                break;
		            }
		        }

		        if (!chutePossible) 
		        {
		            if (imageADroite.contains("echelle") && 
		               (imageEnDessousDroite.contains("echelle") || imageEnDessousDroite.contains("vide52")))
		            {
		                metier.deplacer('E');
		                imagePerso = "pers_monte1.png";
		            } 
		            else 
		            {
		                metier.deplacer('E');
		                imagePerso = "pers_droit.png";
		            }
		        }
		        break;
		    case "CR-":
			{
				if (estSurEchelle)
				{
					metier.deplacer('N');
					metier.deplacer('S');
					break;
				}
				if (estSurSol && !estSurEchelle)
				{
					metier.deplacer('N');
					break;
				}
			}
			
			case "CR-FL-D":
			{
				if (estSurEchelle)
				{
					metier.deplacer('N');
					metier.deplacer('E');
					metier.deplacer('E');
					break;
				}
				
				if (estSurSol && !estSurEchelle)
				{
					metier.deplacer('N');
					metier.deplacer('E');
					metier.deplacer('E');
					break;
				}
			}
			
			case "CR-FL-G":
			{
				if (estSurEchelle)
				{
					metier.deplacer('N');
					metier.deplacer('O');
					metier.deplacer('O');
					break;
				}
				if (estSurSol && !estSurEchelle)
				{
					metier.deplacer('N');
					metier.deplacer('O');
					metier.deplacer('O');
					break;
				}
			}

		    // Ajoutez les autres cas ici, en suivant la même logique
		}

		// Gestion des clés et des portes
		if (metier.getCaseActuelle() == 'r') 
		{  
		    clefRougeRecuperee = true;
		    metier.supprimerPorteAssociee('r');
		    metier.supprimerClefAssociee('r');
		}
		if (metier.getCaseActuelle() == 'b') 
		{
		    clefBleueRecuperee = true;
		    metier.supprimerPorteAssociee('b');
		    metier.supprimerClefAssociee('b');
		}
		if (metier.getCaseActuelle() == 'v') 
		{
		    clefVerteRecuperee = true;
		    metier.supprimerPorteAssociee('v');
		    metier.supprimerClefAssociee('v');
		}

		frame.majIHM();

		if (metier.estSortie()) 
		{
		    System.out.println("Bravo ! Vous avez terminé le niveau.");
		    passerAuNiveauSuivant();
		}
	}

	public int setLargeurImg()
	{
		return 52;
	}
	public int setNbLigne()
	{
		return metier.getNbLignes();
	}

	public int setNbColonne()
	{
		return metier.getNbColonnes();
	}
	
	
	
	public String setGrille()
	{
		switch(niveauActuel) 
		{
			case 1:
				return "./images/lem/";
			case 2:
				return "./images/asterix/";
			case 3:
				return "./images/indiana/";
			case 4:
				return "./images/link/";
			default:
				return "./images/sonic/";
		}
	}
	public String setFondGrille()
	{
		return setGrille() + "fond.png";
	}
	
	
	private void gererChuteAutomatique()
	{
			while (true)
			{
				try
				{
					Thread.sleep(300); // Temporisation pour l'animation de la chute
					if (doitTomber())
					{
						descendre();
					}
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
	}


	
	private void descendre()
	{
		int ligneDessous = metier.getPosLig() + 1;
		int colonneActuelle = metier.getPosCol();

		// Gestion torique Sud
		if (ligneDessous >= metier.getNbLignes())
		{
		    metier.reinitialiserPosition(); // Réinitialisation de la position
		    imagePerso = "pers_droit.png";  // Réinitialisation de l'apparence
		    frame.majIHM();                 // Mise à jour de l'affichage
		    return;                         // Annulation de la chute
		}

		String imageEnDessous = setImage(ligneDessous, colonneActuelle, 0);

		if (!imagePerso.equals("pers_tombe.png"))
		{
		    imagePerso = "pers_tombe.png";
		}

		metier.deplacer('S'); // Descente

		// Vérifie si la chute doit s'arrêter
		if (imageEnDessous.contains("sol") || imageEnDessous.contains("echelle"))
		{
		    imagePerso = "pers_droit.png"; // Reprend l'apparence normale
		}

		frame.majIHM(); // Mise à jour de l'affichage
	}
	
	
	// Déplacez la méthode `getNomFichierNiveau()` ici, en dehors de `jouer()`
	private String getNomFichierNiveau() 
	{
		return "data/grille_0" + niveauActuel + ".data";
	}
	

	
	private boolean doitTomber()
	{
		int ligneActuelle = this.metier.getPosLig();
		int ligneDessous = ligneActuelle + 1;
		int colonneActuelle = this.metier.getPosCol();

		// Gestion torique Nord-Sud pour les échelles
		if (ligneDessous >= metier.getNbLignes()) // Si dépasse la dernière ligne
		{
			ligneDessous = 0; // Torique vers la première ligne
		}

		String imageActuelle = setImage(ligneActuelle, colonneActuelle, 0);
		String imageEnDessous = setImage(ligneDessous, colonneActuelle, 0);

		// Si le personnage est sur une échelle, la chute automatique est désactivée
		if (imageActuelle.contains("echelle"))
		{
			return false;
		}

		// Vérifie si le personnage est au-dessus du vide
		return !imageEnDessous.contains("sol") && !imageEnDessous.contains("echelle");
	}
	
	private boolean estDeplacementLateralPossible(int ligne, int colonne)
	{
		String imageLateral = setImage(ligne, colonne, 0);
		String imageDessous = setImage(ligne + 1, colonne, 0);

		// Vérifie que la case latérale est vide et qu'un sol est présent en dessous
		return imageLateral.contains("vide52") && imageDessous.contains("sol");
	}
	
	private void reinitialiserNiveau() 
	{
		this.metier.chargerGrille(getNomFichierNiveau());
		this.clefRougeRecuperee = false;
		this.clefVerteRecuperee = false;
		this.clefBleueRecuperee = false;
		this.imagePerso = "pers_droit.png";
		this.frame.majIHM(); // Mise à jour de l'affichage
		System.out.println("Niveau " + this.niveauActuel + " chargé !");
	}
	
	private void passerAuNiveauSuivant() 
	{
		if (niveauActuel < 5) { // Jusqu'au niveau 5
		    niveauActuel++;
		    reinitialiserNiveau();
		} 
		else 
		{
		    System.out.println("Félicitations ! Vous avez terminé tous les niveaux.");
		    frame.fermer();
		}
	}

	
	public static void main(String[] args)
	{
		new Controleur();
	}
}
