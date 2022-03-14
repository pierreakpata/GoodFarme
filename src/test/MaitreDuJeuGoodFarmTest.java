package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jeu.Joueur;
import jeu.MaitreDuJeu;
import jeu.MaitreDuJeuGoodFarm;
import jeu.MaitreDuJeuListener;
import jeu.Plateau;

/**
 * Test de la classe MaitreDuJeuGoodFarm.
 * 
 * @author lucile
 *
 */
class MaitreDuJeuGoodFarmTest {

	Plateau p;
	MaitreDuJeuGoodFarm m;
	Joueur J1, J2, J3, J4; 
	boolean informeSpectateurs;
	static final String description = 
               "+----------------+\n"+
               "|$$  F-  @2  $$  |\n"+
               "|  @1    F-  @3$$|\n"+
               "|##  ##  ##  ##  |\n"+
               "|  ##  ##  ##  ##|\n"+
               "|              F1|\n"+
               "|  @4    F1      |\n"+
               "|              F3|\n"+
               "|  ##F1######F1  |\n"+
               "+----------------+"; // plateau non symetrique (@2=Joueur1 en position(4,0)) 

	
	@BeforeEach
	void setUp() {
       p = new Plateau( 1200, description);
	   m = new MaitreDuJeuGoodFarm(p);
	   m.addEcouteurDuJeu( new MaitreDuJeuListener() {
				@Override
				public void unJeuAChange(MaitreDuJeu arg0) {}			
				@Override
				public void nouveauMessage(MaitreDuJeu arg0, String arg1) {
					System.out.println(">>> "+ arg0 + arg1 + "\n");				
				}			
				@Override
				public void afficheSymbole(MaitreDuJeu arg0, Symboles arg1, Point arg2, int arg3, int arg4)  {}	
			});
	   m.avecLog = true;
	   informeSpectateurs = true;
	}
	
	/**
	 * Test des getters de Plateau et de MaitreDuJeuGoodFarm au lancement d'une partie.
	 * Le 1er joueur joue la séquence d'actions DROITE, RIEN , RIEN ;
	 * le second RIEN, HAUT, RIEN ; le troisième RIEN, RIEN, BAS 
	 * et le quatrième RIEN, RIEN, RIEN. 
	 */
	@Test
	public void testLancementDUnePartie() {
     /* +----------------+
        |$$  F-  @2  $$  |
        |  @1    F-  @3$$|
        |##  ##  ##  ##  |
        |  ##  ##  ##  ##|
        |              F1|
        |  @4    F1      |
        |              F3|
        |  ##F1######F1  |
        +----------------+*/
        m.metJoueurEnPosition(0, new Automate("1", "D.."));
        m.metJoueurEnPosition(1, new Automate("2", ".H."));
        m.metJoueurEnPosition(2, new Automate("3", "..B"));
        m.metJoueurEnPosition(3, new Automate("4", "..."));
		assertEquals( "Joueur 1:0:1:1:100:0", p.donneJoueur(0).toString());
		assertEquals( "Joueur 2:1:4:0:100:0", p.donneJoueur(1).toString());
		assertEquals( "Joueur 3:2:6:1:100:0", p.donneJoueur(2).toString());
		assertEquals( "Joueur 4:3:1:5:100:0", p.donneJoueur(3).toString());
		assertEquals(4, p.nombreDeChampsDuJoueur(0));
		assertEquals(0, p.nombreDeChampsDuJoueur(1));
		assertEquals(1, p.nombreDeChampsDuJoueur(2));
		assertEquals(0, p.nombreDeChampsDuJoueur(3));
		assertEquals(1200, p.donneNombreDeTours());
		assertEquals(8,p.donneTaille());
		assertEquals(0,p.donneTourCourant());
		assertEquals(0, p.donneJoueurCourant());
	}
	
	/**
	 * Test d'une partie en 9 coups par joueur où chaque joueur
	 * joue la séquence d'actions suivante :
	 * 1er joueur :Droite  Haut  Droite  Gauche  Gauche  Rien    Rien  Rien  Rien
	 * 2e joueur : Rien    Haut  Bas     Droite  Bas     Rien    Rien 
	 * 3e joueur : Rien    Rien  Droite  Gauche  Haut	 Gauche  Bas
	 * 4e joueur : Rien    Rien  Droite  Droite  Droite  Rien    Rien  Rien  Rien
	 */
	@Test
	public void test1() { 
	 /* +----------------+
        |$$  F-  @2  $$  |
        |  @1    F-  @3$$|
        |##  ##  ##  ##  |
        |  ##  ##  ##  ##|
        |              F1|
        |  @4    F1      |
        |              F3|
        |  ##F1######F1  |
        +----------------+*/
        m.metJoueurEnPosition(0, new Automate("1", "DHDGG...."));
        m.metJoueurEnPosition(1, new Automate("2", ".HBDB..XX"));
        m.metJoueurEnPosition(2, new Automate("3", "..DGHGBXX"));
        m.metJoueurEnPosition(3, new Automate("4", "..DDD...."));
		J1 = p.donneJoueur(0);
		J2 = p.donneJoueur(1);
		J3 = p.donneJoueur(2);
		J4 = p.donneJoueur(3);
		assertEquals( "Joueur 1:0:1:1:100:0", J1.toString());
		assertEquals( "Joueur 2:1:4:0:100:0", J2.toString());
		assertEquals( "Joueur 3:2:6:1:100:0", J3.toString());
		assertEquals( "Joueur 4:3:1:5:100:0", J4.toString());
		assertTrue (Plateau.contientUnChamp(p.donneContenuCellule(2, 0)));
		assertTrue (Plateau.contientUneZoneInfranchissable(p.donneContenuCellule(2, 0)));
		assertTrue (Plateau.contientUneZoneInfranchissable(p.donneContenuCellule(0, 2)));
		assertTrue (Plateau.contientUnDepart(p.donneContenuCellule(4, 0)));
		assertTrue (Plateau.contientUneYourte(p.donneContenuCellule(0, 0)));
		assertTrue (Plateau.contientUneZoneVide(p.donneContenuCellule(1, 0)));
		assertTrue (Plateau.contientUnJoueur(p.donneContenuCellule(6, 1)));
		assertTrue (Plateau.contientUnChampQuiNeLuiAppartientPas(J1, p.donneContenuCellule(2, 0)));
		assertFalse (Plateau.contientUnChampQuiNeLuiAppartientPas(J3, p.donneContenuCellule(7, 6)));
		assertTrue (Plateau.contientLeJoueur(p.donneContenuCellule(1, 1), 0));
		
		/* Premier tour du jeu : J1=D autres=R */
		for(int i = 0; i < 4; i++) {
			assertEquals(i, p.donneJoueurCourant());
			assertEquals(0, p.donneTourCourant());
			m.joueSuivant(informeSpectateurs, m.avecLog);
		}
		assertEquals( "Joueur 1:0:2:1:99:4", J1.toString()); // J1 sur vide (position e-1 p+4)
		assertEquals( "Joueur 2:1:4:0:100:0", J2.toString()); // J2 immobile (e+0 p+0)
		assertEquals( "Joueur 3:2:6:1:100:1", J3.toString()); // J3 immobile (e+0 p+1)
		assertEquals( "Joueur 4:3:1:5:100:0", J4.toString());// J4 immobile (e+0 p+0)
		assertEquals(4, p.nombreDeChampsDuJoueur(0));
		assertEquals(0, p.nombreDeChampsDuJoueur(1));
		assertEquals(1, p.nombreDeChampsDuJoueur(2));
		assertEquals(0, p.nombreDeChampsDuJoueur(3));
		assertEquals(1, p.donneTourCourant());
		
		/* Deuxieme tour du jeu : J1=H J2=H autres=R */
		for(int i = 0; i < 4; i++)
			m.joueSuivant(informeSpectateurs, m.avecLog);
		assertEquals( "Joueur 1:0:2:1:78:9", J1.toString()); // J1 vers F- (e-1-20 p+5)
		assertEquals( "Joueur 2:1:4:0:99:0", J2.toString()); // J2 vers mur (e-1 p+0)
		assertEquals( "Joueur 3:2:6:1:100:2", J3.toString()); // J3 immobile (e+0 p+1)
		assertEquals( "Joueur 4:3:1:5:100:0", J4.toString()); // J4 immobile (e+0 p+0)
		assertEquals(5, p.nombreDeChampsDuJoueur(0));
		assertEquals(0, p.nombreDeChampsDuJoueur(1));
		assertEquals(1, p.nombreDeChampsDuJoueur(2));
		assertEquals(0, p.nombreDeChampsDuJoueur(3));
		assertEquals(2, p.donneTourCourant());		

		/* Troisieme tour du jeu : J2=B autres=D */
		for(int i = 0; i < 4; i++)
			m.joueSuivant(informeSpectateurs, m.avecLog);
		assertEquals( "Joueur 1:0:3:1:77:14", J1.toString()); // J1 sur vide (position e-1 p+5)
		assertEquals( "Joueur 2:1:4:0:78:1", J2.toString()); // J2 vers F- (e-1-20 p+1)
		assertEquals( "Joueur 3:2:7:1:100:2", J3.toString()); // J3 vers yourte (e+50<=100 p+0)
		assertEquals( "Joueur 4:3:2:5:99:0", J4.toString()); // J4 sur vide (position e-1 p+0)
		assertEquals(5, p.nombreDeChampsDuJoueur(0));
		assertEquals(1, p.nombreDeChampsDuJoueur(1));
		assertEquals(1, p.nombreDeChampsDuJoueur(2));
		assertEquals(0, p.nombreDeChampsDuJoueur(3));
		assertEquals(3, p.donneTourCourant());	
		
 		/* Quatrieme tour du jeu : J1=G J2=D J3=G J4=D */ 
		for(int i = 0; i < 4; i++)
			m.joueSuivant(informeSpectateurs, m.avecLog);
		assertEquals( "Joueur 1:0:2:1:76:19", J1.toString()); // J1 sur vide (position e-1 p+5)
		assertEquals( "Joueur 2:1:5:0:77:2", J2.toString()); // J2 sur vide (position e-1 p+1) 
		assertEquals( "Joueur 3:2:6:1:99:3", J3.toString()); // J3 sur vide (position e-1 p+1)
		assertEquals( "Joueur 4:3:3:5:98:0", J4.toString()); // J4 sur vide (position e-1 p+0)
		assertEquals(5, p.nombreDeChampsDuJoueur(0));
		assertEquals(1, p.nombreDeChampsDuJoueur(1));
		assertEquals(1, p.nombreDeChampsDuJoueur(2));
		assertEquals(0, p.nombreDeChampsDuJoueur(3));
		assertEquals(4, p.donneTourCourant());		

		/* Cinquieme tour du jeu : J1=G J2=B J3=H J4=D */
		for(int i = 0; i < 4; i++)
			m.joueSuivant(informeSpectateurs, m.avecLog);
		assertEquals( "Joueur 1:0:1:1:75:24", J1.toString()); // J1 sur vide (e-1 p+5)
		assertEquals( "Joueur 2:1:5:1:76:3", J2.toString()); // J2 sur vide (e-1 p+1)
		assertEquals( "Joueur 3:2:6:0:100:3", J3.toString()); // J3 sur yourte (e+50<=100 p+0)
		assertEquals( "Joueur 4:3:3:5:77:1", J4.toString()); // J4 sur F1 (e-1-20 p+1)
		assertEquals(4, p.nombreDeChampsDuJoueur(0));
		assertEquals(1, p.nombreDeChampsDuJoueur(1));
		assertEquals(1, p.nombreDeChampsDuJoueur(2));
		assertEquals(1, p.nombreDeChampsDuJoueur(3));
		assertEquals(5, p.donneTourCourant());
		
		/* Sixieme tour du jeu : J3=G autres=R */
		for(int i = 0; i < 4; i++)
			m.joueSuivant(informeSpectateurs, m.avecLog);
		assertEquals( "Joueur 1:0:1:1:75:28", J1.toString()); // J1 immobile (e+0 p+4)
		assertEquals( "Joueur 2:1:5:1:76:4", J2.toString()); // J2 immobile(e+0 p+1)
		assertEquals( "Joueur 3:2:5:0:99:4", J3.toString()); // J3 sur vide (e-1 p+1)
		assertEquals( "Joueur 4:3:3:5:77:2", J4.toString()); // J4 immobile (e+0 p+1)
		assertEquals(4, p.nombreDeChampsDuJoueur(0));
		assertEquals(1, p.nombreDeChampsDuJoueur(1));
		assertEquals(1, p.nombreDeChampsDuJoueur(2));
		assertEquals(1, p.nombreDeChampsDuJoueur(3));
		assertEquals(6, p.donneTourCourant());

		/* Septieme tour du jeu : J3=B autres=R */
		for(int i = 0; i < 4; i++)
			m.joueSuivant(informeSpectateurs, m.avecLog);
		assertEquals( "Joueur 1:0:1:1:75:32", J1.toString()); // J1 immobile (e+0 p+4)
		assertEquals( "Joueur 2:1:5:1:76:5", J2.toString()); // J2 immobile(e+0 p+1)
		assertEquals( "Joueur 3:2:5:0:98:5", J3.toString()); // J3 sur J2 => chifoumi au prochain tour (e-1 p+1)
		assertEquals( "Joueur 4:3:3:5:77:3", J4.toString()); // J4 immobile (e+0 p+1)
		assertEquals(4, p.nombreDeChampsDuJoueur(0));
		assertEquals(1, p.nombreDeChampsDuJoueur(1));
		assertEquals(1, p.nombreDeChampsDuJoueur(2));
		assertEquals(1, p.nombreDeChampsDuJoueur(3));
		assertEquals(7, p.donneTourCourant());
		
		/* Huitieme tour du jeu : J1&J4=R on ne demande pas d'action à J2&J3*/
		for(int i = 0; i < 4; i++)
			m.joueSuivant(informeSpectateurs, m.avecLog);
		assertEquals( "Joueur 1:0:1:1:75:36", J1.toString()); // J1 immobile (e+0 p+4)
		assertEquals( "Joueur 2:1:5:1:78:5", J2.toString()); // J2 immobile chifoumi avec J2 (e+2 p+0)
		assertEquals( "Joueur 3:2:5:0:100:5", J3.toString()); // J3 immobile chifoumi avec J3 (e+2 p+0)
		assertEquals( "Joueur 4:3:3:5:77:4", J4.toString()); // J4 immobile (e+0 p+1)
		assertEquals(4, p.nombreDeChampsDuJoueur(0));
		assertEquals(1, p.nombreDeChampsDuJoueur(1));
		assertEquals(1, p.nombreDeChampsDuJoueur(2));
		assertEquals(1, p.nombreDeChampsDuJoueur(3));
		assertEquals(8, p.donneTourCourant());

		/* Neucieme tour du jeu : J1&J4=R on ne demande pas d'action à J2&J3*/
		for(int i = 0; i < 4; i++)
			m.joueSuivant(informeSpectateurs, m.avecLog);
		assertEquals( "Joueur 1:0:1:1:75:40", J1.toString()); // J1 immobile (e+0 p+4)
		assertEquals( "Joueur 2:1:5:1:80:5", J2.toString()); // J2 immobile chifoumi avec J2 (e+2 p+0)
		assertEquals( "Joueur 3:2:5:0:100:5", J3.toString()); // J3 immobile chifoumi avec J3 (e+2 p+0)
		assertEquals( "Joueur 4:3:3:5:77:5", J4.toString()); // J4 immobile (e+0 p+1)
		assertEquals(4, p.nombreDeChampsDuJoueur(0));
		assertEquals(1, p.nombreDeChampsDuJoueur(1));
		assertEquals(1, p.nombreDeChampsDuJoueur(2));
		assertEquals(1, p.nombreDeChampsDuJoueur(3));
		assertEquals(9, p.donneTourCourant());
	}
}
