import iut.algo.Clavier;

/**
  * Classe TestLabyrinthe, permettant de tester
  * la classe Labyrinthe
  *
  * Exercice 1
  *
  * groupe   : enseignant
  * auteur   : Philippe Le Pivert
  *
  * créée le : 15/12/2024
  */
public class TestLabyrinthe
{
	public static void main(String[] arg)
	{
		Labyrinthe l = new Labyrinthe();
		char dir;

		while (true)
		{
			System.out.println ();
			System.out.println ( l );
			System.out.println ();

			System.out.print ( "direction ( N O S E ) : " );
			dir = Clavier.lire_char();

			l.deplacer ( dir );
		}
	}
	
	
}
