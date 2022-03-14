package test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import jeu.MaitreDuJeuGoodFarm;
import jeu.Plateau;
import jeu.astar.Node;

/**
 * @author lucile
 *
 */
class PlateauTest {
    private Plateau plateau1;
    private Plateau plateau2;
    private Plateau petitPlateau;
    private Plateau plateauParDefaut;
    private String description1;
    private String description2;
    private String joueurs1;
    private String joueurs2;
    private String joueursPetitPlateau;
    private String joueursPlateauParDefaut;
    private static Random hasard = new Random();
    
    private int entierAleatoire(int max) {
    	// renvoie un nb aleatoire entre 0 et max-1
    	return hasard.nextInt(max);
    }
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		petitPlateau = new Plateau(4, MaitreDuJeuGoodFarm.PLATEAU_PETIT);
		joueursPetitPlateau = ",Joueur0:0:1:1:100:0,Joueur1:1:4:0:100:0,Joueur2:2:6:1:100:0,Joueur3:3:1:5:100:0,0,16";

		plateauParDefaut = new Plateau(8, MaitreDuJeuGoodFarm.PLATEAU_PAR_DEFAUT);
		joueursPlateauParDefaut = ",Joueur0:0:6:5:100:0,Joueur1:1:6:14:100:0,Joueur2:2:13:14:100:0,Joueur3:3:13:5:100:0,0,32";

        description1 = 
                "+----------------+\n"+
                "|$$  F-  @2  $$  |\n"+
                "|  @1    F-  @3$$|\n"+
                "|##  ##  ##  ##  |\n"+
                "|  ##  ##  ##  ##|\n"+
                "|              F1|\n"+
                "|  @4    F1      |\n"+
                "|              F3|\n"+
                "|  ##F1######F1  |\n"+
                "+----------------+"; // plateau de taille 8 non symetrique (@2=Joueur1 en position(4,0)) 
            plateau1 = new Plateau(6,description1); // nbTours=6 nbCoupsTotal=24
            joueurs1 = ",Joueur0:0:1:1:100:0,Joueur1:1:4:0:100:0,Joueur2:2:6:1:100:0,Joueur3:3:1:5:100:0,0,24";

            description2 = 
                    "+------------------+\n"+
                    "|              $$  |\n"+
                    "|                  |\n"+
                    "|##  F-  ##        |\n"+
                    "|  ##  F1          |\n"+
                    "|      @2          |\n"+
                    "|                  |\n"+
                    "|##        ##  F3  |\n"+
                    "|  ##              |\n"+
                    "|  ##              |\n"+
                    "+------------------+";//plateau de taille 9 non symetrique
            plateau2 = new Plateau(10,description2); // nbTours=10 nbCoupsTotal=40
            joueurs2 = ",Joueur0:0:-1:-1:100:0,Joueur1:1:3:4:100:0,Joueur2:2:-1:-1:100:0,Joueur3:3:-1:-1:100:0,0,40";
	}
	
	/* ********************************************************
	 *                 ETAT DU PLATEAU
	 * ********************************************************/
 
	/**
	 * Test method for {@link jeu.Plateau#Plateau(int, int)}.
	 */
	@Test
	void testPlateauIntInt() {
		String attendu = 
				"+----------+\n" + 
				"|          |\n" + 
				"|          |\n" + 
				"|          |\n" + 
				"|          |\n" + 
				"|          |\n" + 
				"+----------+,"+
				"Joueur0:0:-1:-1:100:0,Joueur1:1:-1:-1:100:0,Joueur2:2:-1:-1:100:0,Joueur3:3:-1:-1:100:0,0,400";
		assertEquals(attendu,new Plateau(100,5).toString());				
		assertEquals(5, new Plateau(1,5).donneTaille());
		assertEquals(0, new Plateau(2,4).donneTourCourant());
		assertEquals(42, new Plateau(42,4).donneNombreDeTours());
		assertEquals(8, new Plateau(8,4).donneNombreDeTours());
		assertEquals(44, new Plateau(44,4).donneNombreDeTours());
		assertEquals(0,new Plateau(10,15).donneJoueurCourant());
	}

	/**
	 * Test method for {@link jeu.Plateau#Plateau(int)}.
	 */
	@Test
	void testPlateauInt() {
		String attendu = 
				"+--------+\n" + 
				"|        |\n" + 
				"|        |\n" + 
				"|        |\n" + 
				"|        |\n" + 
				"+--------+"
				+",Joueur0:0:-1:-1:100:0,Joueur1:1:-1:-1:100:0,Joueur2:2:-1:-1:100:0,Joueur3:3:-1:-1:100:0,0,0";
		assertEquals(attendu,new Plateau(4).toString());		
		assertEquals(5, new Plateau(5).donneTaille());
		assertEquals(0, new Plateau(4).donneTourCourant());
		assertEquals(0, new Plateau(4).donneNombreDeTours());
		assertEquals(0,new Plateau(15).donneJoueurCourant());
	}


	/**
	 * Test method for {@link jeu.Plateau#Plateau(int, java.lang.String, int)}.
	 */
	@Test
	void testPlateauIntStringInt() {
		assertEquals(8, plateau1.donneTaille());
		assertEquals(0, plateau1.donneTourCourant());
		assertEquals(6, plateau1.donneNombreDeTours());
		assertEquals(0, plateau1.donneJoueurCourant());
		assertEquals(description1+joueurs1, plateau1.toString());
		
		assertEquals(9, plateau2.donneTaille());
		assertEquals(0, plateau2.donneTourCourant());
		assertEquals(10, plateau2.donneNombreDeTours());
		assertEquals(0, plateau2.donneJoueurCourant());
		assertEquals(description2+joueurs2,plateau2.toString());
		
		assertEquals(8, petitPlateau.donneTaille());
		assertEquals(0, petitPlateau.donneTourCourant());
		assertEquals(4, petitPlateau.donneNombreDeTours());
		assertEquals(0, petitPlateau.donneJoueurCourant());
		assertEquals(MaitreDuJeuGoodFarm.PLATEAU_PETIT+joueursPetitPlateau, petitPlateau.toString());

		assertEquals(20, plateauParDefaut.donneTaille());
		assertEquals(0, plateauParDefaut.donneTourCourant());
		assertEquals(8, plateauParDefaut.donneNombreDeTours());
		assertEquals(0, plateauParDefaut.donneJoueurCourant());
		assertEquals(MaitreDuJeuGoodFarm.PLATEAU_PAR_DEFAUT+joueursPlateauParDefaut, plateauParDefaut.toString());
	}
	
	/**
	 * Test method for {@link jeu.Plateau#donneJoueur(int)}.
	 */
	@Test
	void testDonneJoueur() {
	  /*+----------------+
      	|$$  F-  @2  $$  |
      	|  @1    F-  @3$$|
      	|##  ##  ##  ##  |
      	|  ##  ##  ##  ##|
      	|              F1|
      	|  @4    F1      |
      	|              F3|
      	|  ##F1######F1  |
      	+----------------+*/
		assertEquals(new Point(1,1), plateau1.donneJoueur(0).donnePosition());
		assertEquals(new Point(4,0), plateau1.donneJoueur(1).donnePosition());
		assertEquals(new Point(6,1), plateau1.donneJoueur(2).donnePosition());
		assertEquals(new Point(1,5), plateau1.donneJoueur(3).donnePosition());
		String couleurJoueurs[] = { "bleu", "vert", "rouge","jaune" };
		int indice = entierAleatoire(4);
		assertEquals("Joueur"+indice,plateau1.donneJoueur(indice).donneNom());
		assertEquals(100,plateau1.donneJoueur(indice).donneEnergie());
		assertEquals(0,plateau1.donneJoueur(indice).donnePoints());
		assertEquals(indice,plateau1.donneJoueur(indice).donneCouleurNumerique());
		assertEquals(couleurJoueurs[indice],plateau1.donneJoueur(indice).donneCouleur());
		
	  /*+----------------------------------------+
		|######      F-############F-      ######|
		|######        ##        ##        ######|
		|####$$    ####            ####    $$####|
		|##      ####  ##        ##  ####      ##|
		|####            F-    F-            ####|
		|##########  @1            @4  ##########|
		|############  ####    ####  ############|
		|F-##F-        ############        F-##F-|
		|  F-      F-################F-      F-  |
		|        ########################        |
		|        ########################        |
		|  F-      F-################F-      F-  |
		|F-##F-        ############        F-##F-|
		|############  ####    ####  ############|
		|##########  @2            @3  ##########|
		|####            F-    F-            ####|
		|##      ####  ##        ##  ####      ##|
		|####$$    ####            ####    $$####|
		|######        ##        ##        ######|
		|######      F-############F-      ######|
		+----------------------------------------+*/
		assertEquals(new Point(6,5), plateauParDefaut.donneJoueur(0).donnePosition());
		assertEquals(new Point(6,14), plateauParDefaut.donneJoueur(1).donnePosition());
		assertEquals(new Point(13,14), plateauParDefaut.donneJoueur(2).donnePosition());
		assertEquals(new Point(13,5), plateauParDefaut.donneJoueur(3).donnePosition());
		indice = entierAleatoire(4);
		assertEquals("Joueur"+indice, plateauParDefaut.donneJoueur(indice).donneNom());
		assertEquals(100,plateauParDefaut.donneJoueur(indice).donneEnergie());
		assertEquals(0,plateauParDefaut.donneJoueur(indice).donnePoints());
		assertEquals(indice,plateauParDefaut.donneJoueur(indice).donneCouleurNumerique());
		assertEquals(couleurJoueurs[indice],plateauParDefaut.donneJoueur(indice).donneCouleur());
	}

	/**
	 * Test method for {@link jeu.Plateau#donneJoueurEnPosition(java.awt.Point)}.
	 */
	@Test
	void testDonneJoueurEnPositionPoint() {
	  /*+----------------+
      	|$$  F-  @2  $$  |
      	|  @1    F-  @3$$|
      	|##  ##  ##  ##  |
      	|  ##  ##  ##  ##|
      	|              F1|
      	|  @4    F1      |
      	|              F3|
      	|  ##F1######F1  |
      	+----------------+*/
		assertEquals("Joueur0", plateau1.donneJoueurEnPosition(new Point(1, 1)).donneNom());
		assertEquals("Joueur1", plateau1.donneJoueurEnPosition(new Point(4, 0)).donneNom());
		assertEquals("Joueur2", plateau1.donneJoueurEnPosition(new Point(6, 1)).donneNom());
		assertEquals("Joueur3", plateau1.donneJoueurEnPosition(new Point(1, 5)).donneNom());
		assertNull(plateau1.donneJoueurEnPosition(new Point(0, 0))); //$$
		assertNull(plateau1.donneJoueurEnPosition(new Point(0, 2))); //##
		assertNull(plateau1.donneJoueurEnPosition(new Point(2, 0))); //F-
		assertNull(plateau1.donneJoueurEnPosition(new Point(2, 6))); //
		assertNull(plateau1.donneJoueurEnPosition(new Point(7, 6))); //F3
		assertNull(plateau1.donneJoueurEnPosition(new Point(1, 0))); //	       
	}

	/**
	 * Test method for {@link jeu.Plateau#donneJoueurEnPosition(int, int)}.
	 */
	@Test
	void testDonneJoueurEnPositionIntInt() {
	  /*+----------------+
      	|$$  F-  @2  $$  |
      	|  @1    F-  @3$$|
      	|##  ##  ##  ##  |
      	|  ##  ##  ##  ##|
      	|              F1|
      	|  @4    F1      |
      	|              F3|
      	|  ##F1######F1  |
      	+----------------+*/
		assertEquals("Joueur0", plateau1.donneJoueurEnPosition(1, 1).donneNom());
		assertEquals("Joueur1", plateau1.donneJoueurEnPosition(4, 0).donneNom());
		assertEquals("Joueur2", plateau1.donneJoueurEnPosition(6, 1).donneNom());
		assertEquals("Joueur3", plateau1.donneJoueurEnPosition(1, 5).donneNom());
		assertNull(plateau1.donneJoueurEnPosition(0, 0)); //$$
		assertNull(plateau1.donneJoueurEnPosition(0, 2)); //##
		assertNull(plateau1.donneJoueurEnPosition(2, 0)); //F-
		assertNull(plateau1.donneJoueurEnPosition(2, 6)); // 
		assertNull(plateau1.donneJoueurEnPosition(7, 6)); //F3
		assertNull(plateau1.donneJoueurEnPosition(1, 0)); //
	}

	/**
	 * Test method for {@link jeu.Plateau#nombreDeMIAGESJoueur(int)}.
	 */
	@Test
	void testNombreDeChampsJoueur() {
	  /*+----------------+
      	|$$  F-  @2  $$  |
      	|  @1    F-  @3$$|
      	|##  ##  ##  ##  |
      	|  ##  ##  ##  ##|
      	|              F1|
      	|  @4    F1      |
      	|              F3|
      	|  ##F1######F1  |
      	+----------------+*/
		assertEquals(4, plateau1.nombreDeChampsDuJoueur(0));
		assertEquals(0, plateau1.nombreDeChampsDuJoueur(1));
		assertEquals(1, plateau1.nombreDeChampsDuJoueur(2));
		assertEquals(0, plateau1.nombreDeChampsDuJoueur(3));
	}
		
	/**
	 * Test method for {@link jeu.Plateau#coordonneeValide(int, int)}.
	 */
	@Test
	void testCoordonneeValide() {
		assertFalse( plateau1.coordonneeValide(8, 0));
		assertFalse( plateau1.coordonneeValide(3, 8));
		assertFalse( plateau1.coordonneeValide(-1, -1));
		assertTrue( plateau1.coordonneeValide(0,0));
		assertTrue( plateau1.coordonneeValide(7,7));
		assertTrue( plateau1.coordonneeValide(5,2));	
	}

	/* ********************************************************
	 *         REPRESENTATIONS TEXTUELLES DU PLATEAU
	 * ********************************************************/
	
	/**
	 * Test method for {@link jeu.Plateau#toJavaCode()}.
	 */
	@Test
	void testToJavaCode() {
		String attendu = 
				"String tableau_ascii = \"+----------------+\\n\"+\n" + 
				"\"|$$  F-  @2  $$  |\\n\"+\n" + 
				"\"|  @1    F-  @3$$|\\n\"+\n" + 
				"\"|##  ##  ##  ##  |\\n\"+\n" + 
				"\"|  ##  ##  ##  ##|\\n\"+\n" + 
				"\"|              F1|\\n\"+\n" + 
				"\"|  @4    F1      |\\n\"+\n" + 
				"\"|              F3|\\n\"+\n" + 
				"\"|  ##F1######F1  |\\n\"+\n" + 
				"\"+----------------+\\n\"+"   + "\n";
		assertEquals(attendu, plateau1.toJavaCode().toString());
	}
	
	/**
	 * Test method for {@link jeu.Plateau#encode()}.
	 */
	@Test
	void testEncode() {
		String description = description1.replace('\n', 'X');
		assertEquals(description1+joueurs1,plateau1.encode('\n'));
		assertEquals(description+joueurs1,plateau1.encode('X'));
	}
	
	/**
	 * Test method for {@link jeu.Plateau#decode(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testDecodeStringString() {
		Plateau nouveau = Plateau.decode(plateau1.encode( 'X'), "X");
		assertEquals(plateau1, nouveau);
		assertEquals(0, nouveau.donneJoueurCourant());
		
		nouveau = Plateau.decode(plateau2.encode( 'Q'), "Q");
		assertEquals(plateau2, nouveau);
		assertEquals(0, nouveau.donneJoueurCourant());
	}
			
	/**
	 * Test method for {@link jeu.Plateau#equals()}.
	 */
	@Test
	void testEquals() {
		assertEquals(new Plateau(6,description1),plateau1);
		assertNotEquals(new Plateau(4,description1),plateau1);
	}
	
	/* ********************************************************
	 *               TYPES DE CONTENU DU PLATEAU
	 * ********************************************************/

	/**
	 * Test method for {@link jeu.Plateau#joueurPeutAllerIci(int, int, boolean, boolean)}.
	 */
	@Test
	void testJoueurPeutAllerIci() {
	  /*+----------------+
      	|$$  F-  @2  $$  |
      	|  @1    F-  @3$$|
      	|##  ##  ##  ##  |
      	|  ##  ##  ##  ##|
      	|              F1|
      	|  @4    F1      |
      	|              F3|
      	|  ##F1######F1  |
      	+----------------+*/
        // sur la yourte "$$" en (0,0) => toujours
		assertTrue( plateau1.joueurPeutAllerIci(0, 0, false, false));
		assertTrue( plateau1.joueurPeutAllerIci(0, 0, true, true));
		assertTrue( plateau1.joueurPeutAllerIci(0, 0, false, true));
		assertTrue( plateau1.joueurPeutAllerIci(0, 0, true, false));

        // sur la case vide en (2,6) => toujours
		assertTrue( plateau1.joueurPeutAllerIci(2, 6, false, false));
		assertTrue( plateau1.joueurPeutAllerIci(2, 6, true, true));
		assertTrue( plateau1.joueurPeutAllerIci(2, 6, false, true));
		assertTrue( plateau1.joueurPeutAllerIci(2, 6, true, false));
		
		// sur la case vide en (3,4) => toujours
		assertTrue( plateau1.joueurPeutAllerIci(3, 4, false, false));
		assertTrue( plateau1.joueurPeutAllerIci(3, 4, true, true));
		assertTrue( plateau1.joueurPeutAllerIci(3, 4, false, true));
		assertTrue( plateau1.joueurPeutAllerIci(3, 4, true, false));

		// sur la zone infranchissable "##" en (0,2) => jamais
		assertFalse( plateau1.joueurPeutAllerIci(0, 2, false, false));
		assertFalse( plateau1.joueurPeutAllerIci(0, 2, true, true));
		assertFalse( plateau1.joueurPeutAllerIci(0, 2, false, true));
		assertFalse( plateau1.joueurPeutAllerIci(0, 2, true, false));
		
		// sur le joueur "@1" en (1,1)
		assertTrue( plateau1.joueurPeutAllerIci(1, 1, false, false));
		assertFalse( plateau1.joueurPeutAllerIci(1, 1, true, true));//deja un joueur
		assertTrue( plateau1.joueurPeutAllerIci(1, 1, false, true));
		assertFalse( plateau1.joueurPeutAllerIci(1, 1, true, false));//deja un joueur

		// sur le champ "F-" en (2,0)
		assertTrue( plateau1.joueurPeutAllerIci(2, 0, false, false));
		assertFalse( plateau1.joueurPeutAllerIci(2, 0, true, true));//deja un champ
		assertFalse( plateau1.joueurPeutAllerIci(2, 0, false, true));//deja un champ
		assertTrue( plateau1.joueurPeutAllerIci(2, 0, true, false));

		// sur le champ "F3" en (7,6)
		assertTrue( plateau1.joueurPeutAllerIci(7, 6, false, false));
		assertFalse( plateau1.joueurPeutAllerIci(7, 6, true, true));//deja un champ
		assertFalse( plateau1.joueurPeutAllerIci(7, 6, false, true));//deja un champ
		assertTrue( plateau1.joueurPeutAllerIci(7, 6, true, false));
		
		// sur une case en dehors du plateau => jamais
		assertFalse( plateau1.joueurPeutAllerIci(8, 0, false, false));
		assertFalse( plateau1.joueurPeutAllerIci(3, 8, false, false));
		assertFalse( plateau1.joueurPeutAllerIci(-1, -1, false, false));
	}
	
	/**
	 * Test des masques et de la methode {@link jeu.Plateau#donneContenuCellule(int, int)}, 
	 * et des methodes statiques 
	 * {@link jeu.Plateau#contientUneZoneInfranchissable(int)}, 
	 * {@link jeu.Plateau#contientUneZoneVide(int)}, 
	 * {@link jeu.Plateau#contientUnChamp(int)}, 
	 * {@link jeu.Plateau#contientUneYourte(int)}, 
	 * {@link jeu.Plateau#contientUnChallenge(int)}, 
	 * {@link jeu.Plateau#donneProprietaireDuChamp(int)}, 
	 * {@link jeu.Plateau#donneProprietaireDuPointDeDepart(int)}.
	 */
	@Test
	void testContenuCellule() {
	  /*+----------------+
      	|$$  F-  @2  $$  |
      	|  @1    F-  @3$$|
      	|##  ##  ##  ##  |
      	|  ##  ##  ##  ##|
      	|              F1|
      	|  @4    F1      |
      	|              F3|
      	|  ##F1######F1  |
      	+----------------+*/
		int contenuCellule;
		
		// zone infranchissable "##" en (0,2) 
		contenuCellule = plateau1.donneContenuCellule(0,2);
		assertTrue((contenuCellule & Plateau.MASQUE_ENDROITS) == Plateau.ENDROIT_INFRANCHISSABLE);
		assertTrue((contenuCellule & Plateau.ENDROIT_INFRANCHISSABLE) != 0);
		
		assertTrue( Plateau.contientUneZoneInfranchissable(contenuCellule));
		assertFalse( Plateau.contientUneZoneVide(contenuCellule));
		assertFalse( Plateau.contientUnChamp(contenuCellule));
		assertFalse( Plateau.contientUneYourte(contenuCellule));
		assertEquals(-1, Plateau.donneProprietaireDuChamp(contenuCellule));
		assertEquals(0, Plateau.donneProprietaireDuPointDeDepart(contenuCellule));		
		assertFalse( Plateau.contientUnJoueur(contenuCellule));
		assertFalse( Plateau.contientLeJoueur(contenuCellule, 3));

		// zone vide "  " en (1,0) 
		contenuCellule = plateau1.donneContenuCellule(1,0);
		assertTrue((contenuCellule & Plateau.MASQUE_ENDROITS) == Plateau.ENDROIT_VIDE);

		assertTrue( Plateau.contientUneZoneVide(contenuCellule));
		assertFalse( Plateau.contientUneZoneInfranchissable(contenuCellule));
		assertFalse( Plateau.contientUnChamp(contenuCellule));
		assertFalse( Plateau.contientUneYourte(contenuCellule));
		assertEquals(-1, Plateau.donneProprietaireDuChamp(contenuCellule));
		assertEquals(0, Plateau.donneProprietaireDuPointDeDepart(contenuCellule));		
		
		// zone yourte "$$" en (0,0) 
		contenuCellule = plateau1.donneContenuCellule(0,0);
		assertTrue((contenuCellule & Plateau.MASQUE_ENDROITS) == Plateau.ENDROIT_YOURTE);
		assertTrue((contenuCellule & Plateau.ENDROIT_YOURTE) != 0);

		assertTrue( Plateau.contientUneYourte(contenuCellule));
		assertFalse( Plateau.contientUneZoneVide(contenuCellule));
		assertFalse( Plateau.contientUneZoneInfranchissable(contenuCellule));
		assertFalse( Plateau.contientUnChamp(contenuCellule));
		assertEquals(-1, Plateau.donneProprietaireDuChamp(contenuCellule));
		assertEquals(0, Plateau.donneProprietaireDuPointDeDepart(contenuCellule));		
		assertFalse( Plateau.contientUnJoueur(contenuCellule));
		assertFalse( Plateau.contientLeJoueur(contenuCellule, 0));

		// zone challenge "C  " en (2,6) 
/*		contenuCellule = plateau1.donneContenuCellule(2,6);
		assertTrue((contenuCellule & Plateau.MASQUE_CHALLENGE) != 0); //Challenge
		assertTrue((contenuCellule & Plateau.MASQUE_ENDROITS) == Plateau.ENDROIT_CHALLENGE_FERME
				|| (contenuCellule & Plateau.MASQUE_ENDROITS) == Plateau.ENDROIT_CHALLENGE_OUVERT);

		assertFalse( Plateau.contientUneZoneVide(contenuCellule));
		assertFalse( Plateau.contientUneZoneInfranchissable(contenuCellule));
		assertFalse( Plateau.contientUnChamp(contenuCellule));
		assertFalse( Plateau.contientUneYourte(contenuCellule));
		assertEquals(-1, Plateau.donneProprietaireDuChamp(contenuCellule));
		assertEquals(0, Plateau.donneProprietaireDuPointDeDepart(contenuCellule));		
		assertFalse( Plateau.contientUnJoueur(contenuCellule));
		assertFalse( Plateau.contientLeJoueur(contenuCellule, 2));
*/		
		// zone champ "F-" en (2,0) 
		contenuCellule = plateau1.donneContenuCellule(2,0);
		assertTrue((contenuCellule & Plateau.MASQUE_ENDROIT_CHAMPS) != 0);//MIAGE
		assertTrue((contenuCellule & Plateau.MASQUE_ENDROITS) == Plateau.ENDROIT_CHAMP_LIBRE);//MIAGE libre

		assertFalse((contenuCellule & Plateau.MASQUE_ENDROITS) == Plateau.ENDROIT_CHAMP_J1);
		assertFalse((contenuCellule & Plateau.MASQUE_ENDROITS) == Plateau.ENDROIT_CHAMP_J2);
		assertFalse((contenuCellule & Plateau.MASQUE_ENDROITS) == Plateau.ENDROIT_CHAMP_J3);
		assertFalse((contenuCellule & Plateau.MASQUE_ENDROITS) == Plateau.ENDROIT_CHAMP_J4);

		assertTrue( Plateau.contientUnChamp(contenuCellule));
		assertEquals(0, Plateau.donneProprietaireDuChamp(contenuCellule));
		assertFalse( Plateau.contientUneZoneVide(contenuCellule));
		assertTrue( Plateau.contientUneZoneInfranchissable(contenuCellule));
		assertFalse( Plateau.contientUneYourte(contenuCellule));
		assertEquals(0, Plateau.donneProprietaireDuPointDeDepart(contenuCellule));		
		assertFalse( Plateau.contientUnJoueur(contenuCellule));
		assertFalse( Plateau.contientLeJoueur(contenuCellule, 1));

		// zone champ "F3" en (7,6) 
		contenuCellule = plateau1.donneContenuCellule(7,6);
		assertEquals( contenuCellule, Plateau.donneChampDuJoueur(2));
		assertTrue((contenuCellule & Plateau.MASQUE_ENDROIT_CHAMPS) != 0);//MIAGE
		assertTrue((contenuCellule & Plateau.MASQUE_ENDROITS) == Plateau.ENDROIT_CHAMP_J3);//MIAGE J3

		assertFalse((contenuCellule & Plateau.MASQUE_ENDROITS) == Plateau.ENDROIT_CHAMP_LIBRE);
		assertFalse((contenuCellule & Plateau.MASQUE_ENDROITS) == Plateau.ENDROIT_CHAMP_J1);
		assertFalse((contenuCellule & Plateau.MASQUE_ENDROITS) == Plateau.ENDROIT_CHAMP_J2);
		assertFalse((contenuCellule & Plateau.MASQUE_ENDROITS) == Plateau.ENDROIT_CHAMP_J4);
		
		assertTrue( Plateau.contientUnChamp(contenuCellule));
		assertEquals(3, Plateau.donneProprietaireDuChamp(contenuCellule));
		assertFalse( Plateau.contientUneZoneVide(contenuCellule));
		assertTrue( Plateau.contientUneZoneInfranchissable(contenuCellule));
		assertFalse( Plateau.contientUneYourte(contenuCellule));
		assertEquals(0, Plateau.donneProprietaireDuPointDeDepart(contenuCellule));		
		assertFalse( Plateau.contientUnJoueur(contenuCellule));
		assertFalse( Plateau.contientLeJoueur(contenuCellule, 0));


		// zone joueur "$1" en (1,1) 
		contenuCellule = plateau1.donneContenuCellule(1,1);
		assertTrue((contenuCellule & Plateau.MASQUE_PRESENCE_JOUEUR) != 0);//JOUEUR
		assertTrue((contenuCellule & Plateau.MASQUE_PRESENCE_JOUEUR) == Plateau.PRESENCE_JOUEUR1);//JOUEUR 1	
		assertFalse((contenuCellule & Plateau.MASQUE_PRESENCE_JOUEUR) == Plateau.PRESENCE_JOUEUR2);
		assertFalse((contenuCellule & Plateau.MASQUE_PRESENCE_JOUEUR) == Plateau.PRESENCE_JOUEUR3);
		assertFalse((contenuCellule & Plateau.MASQUE_PRESENCE_JOUEUR) == Plateau.PRESENCE_JOUEUR4);
		assertTrue((contenuCellule & Plateau.MASQUE_ENDROITS) == Plateau.ENDROIT_DEPART_J1);

		assertFalse((contenuCellule & Plateau.MASQUE_ENDROITS) == Plateau.ENDROIT_DEPART_J2);
		assertTrue( Plateau.contientUnDepart(contenuCellule));
		assertEquals(1, Plateau.donneProprietaireDuPointDeDepart(contenuCellule));
		
		assertTrue( Plateau.contientUnJoueur(contenuCellule));
		assertTrue( Plateau.contientLeJoueur(contenuCellule, 0));
		assertFalse( Plateau.contientUneZoneVide(contenuCellule));
		assertFalse( Plateau.contientUneZoneInfranchissable(contenuCellule));
		assertFalse( Plateau.contientUnChamp(contenuCellule));
		assertFalse( Plateau.contientUneYourte(contenuCellule));
		assertEquals(-1, Plateau.donneProprietaireDuChamp(contenuCellule));
		
		// zone joueur "$4" en (1,5) 
		contenuCellule = plateau1.donneContenuCellule(1,5);
		assertTrue((contenuCellule & Plateau.MASQUE_PRESENCE_JOUEUR) != 0);//JOUEUR
		assertTrue((contenuCellule & Plateau.MASQUE_PRESENCE_JOUEUR) == Plateau.PRESENCE_JOUEUR4);//JOUEUR 3

		assertTrue((contenuCellule & Plateau.MASQUE_ENDROITS) == Plateau.ENDROIT_DEPART_J4);
		assertTrue( Plateau.contientUnDepart(contenuCellule));
		assertEquals(4, Plateau.donneProprietaireDuPointDeDepart(contenuCellule));

		assertFalse((contenuCellule & Plateau.MASQUE_PRESENCE_JOUEUR) == Plateau.PRESENCE_JOUEUR1);
		assertFalse((contenuCellule & Plateau.MASQUE_PRESENCE_JOUEUR) == Plateau.PRESENCE_JOUEUR2);
		assertFalse((contenuCellule & Plateau.MASQUE_PRESENCE_JOUEUR) == Plateau.PRESENCE_JOUEUR3);

		assertTrue( Plateau.contientUnJoueur(contenuCellule));
		assertTrue( Plateau.contientLeJoueur(contenuCellule, 3));
		assertFalse( Plateau.contientUneZoneVide(contenuCellule));
		assertFalse( Plateau.contientUneZoneInfranchissable(contenuCellule));
		assertFalse( Plateau.contientUnChamp(contenuCellule));
		assertFalse( Plateau.contientUneYourte(contenuCellule));
		assertEquals(-1, Plateau.donneProprietaireDuChamp(contenuCellule));
		
		// zone joueur "$3" en (16,1) 
		contenuCellule = plateau1.donneContenuCellule(6,1);
		assertTrue((contenuCellule & Plateau.MASQUE_PRESENCE_JOUEUR) != 0);//JOUEUR
		assertTrue((contenuCellule & Plateau.MASQUE_PRESENCE_JOUEUR) == Plateau.PRESENCE_JOUEUR3);//JOUEUR 3

		assertTrue((contenuCellule & Plateau.MASQUE_ENDROITS) == Plateau.ENDROIT_DEPART_J3);
		
		assertTrue( Plateau.contientUnJoueur(contenuCellule));
		assertTrue( Plateau.contientLeJoueur(contenuCellule, 2));
		assertTrue( Plateau.contientUnDepart(contenuCellule));
		assertEquals(3, Plateau.donneProprietaireDuPointDeDepart(contenuCellule));		
	}

	/**
	 * Test method for {@link jeu.Plateau#donneContenuCellule(java.awt.Point)}.
	 */
	@RepeatedTest(5)
	void testDonneContenuCellulePoint() {
		int x = entierAleatoire(8);
		int y = entierAleatoire(8);
		assertEquals( plateau1.donneContenuCellule(x,y), plateau1.donneContenuCellule(new Point(x,y)));
	}

	/**
	 * Test method for {@link jeu.Plateau#donneChampDuJoueur(int)}.
	 */
	@Test
	void testDonneChampDuJoueur() {
		assertEquals(Plateau.ENDROIT_CHAMP_J1, Plateau.donneChampDuJoueur(0));
		assertEquals(Plateau.ENDROIT_CHAMP_J2, Plateau.donneChampDuJoueur(1));
		assertEquals(Plateau.ENDROIT_CHAMP_J3, Plateau.donneChampDuJoueur(2));
		assertEquals(Plateau.ENDROIT_CHAMP_J4, Plateau.donneChampDuJoueur(3));
	}
	
	/* ********************************************************
	 *       RECHERCHE DE CHEMIN OU DE ZONE ENVIRONNANTE
	 * ********************************************************/

	/**
	 * Test method for {@link jeu.Plateau#cherche(java.awt.Point, int, int)}.
	 */
	@Test
	void testCherche1() {
	  /*+----------------+
      	|$$  F-  @2  $$  |
      	|  @1    F-  @3$$|
      	|##  ##  ##  ##  |
      	|  ##  ##  ##  ##|
      	|              F1|
      	|  @4    F1      |
      	|              F3|
      	|  ##F1######F1  |
      	+----------------+*/
		Point positionJoueur4 = new Point(1,5);  
		assertTrue( Plateau.contientLeJoueur(plateau1.donneContenuCellule(positionJoueur4), 3));
		int rayon;
		
		/* recherches autour de la case @4 avec un rayon 0 */
		rayon = 0;
		assertEquals("{1=[], 2=[], 4=[java.awt.Point[x=1,y=5]]}",
				plateau1.cherche(positionJoueur4, rayon, Plateau.CHERCHE_TOUT).toString());
		assertEquals("{1=[], 2=[], 4=[java.awt.Point[x=1,y=5]]}",
				plateau1.cherche(positionJoueur4, rayon, Plateau.CHERCHE_JOUEUR).toString());
		assertEquals("{1=[], 2=[], 4=[]}",
				plateau1.cherche(positionJoueur4, rayon, Plateau.CHERCHE_CHAMP).toString());
		assertEquals("{1=[], 2=[], 4=[]}",
				plateau1.cherche(positionJoueur4, rayon, Plateau.CHERCHE_YOURTE).toString());

		/* recherches autour de la case @4 avec un rayon 1 */
		rayon = 1;
		assertEquals("{1=[], 2=[], 4=[java.awt.Point[x=1,y=5]]}",
				plateau1.cherche(positionJoueur4, rayon, Plateau.CHERCHE_TOUT).toString());
		assertEquals("{1=[], 2=[], 4=[java.awt.Point[x=1,y=5]]}",
				plateau1.cherche(positionJoueur4, rayon, Plateau.CHERCHE_JOUEUR).toString());
		assertEquals("{1=[], 2=[], 4=[]}",
				plateau1.cherche(positionJoueur4, rayon, Plateau.CHERCHE_CHAMP).toString());
		assertEquals("{1=[], 2=[], 4=[]}",
				plateau1.cherche(positionJoueur4, rayon, Plateau.CHERCHE_YOURTE).toString());
		
		/* recherches autour de la case @4 avec un rayon 2 */
		rayon = 2;
		assertEquals("{1=[], 2=[java.awt.Point[x=2,y=7]], 4=[java.awt.Point[x=1,y=5]]}",
				plateau1.cherche(positionJoueur4, rayon, Plateau.CHERCHE_TOUT).toString());
		assertEquals("{1=[], 2=[], 4=[java.awt.Point[x=1,y=5]]}",
				plateau1.cherche(positionJoueur4, rayon, Plateau.CHERCHE_JOUEUR).toString());
		assertEquals("{1=[], 2=[java.awt.Point[x=2,y=7]], 4=[]}",
				plateau1.cherche(positionJoueur4, rayon, Plateau.CHERCHE_CHAMP).toString());
		assertEquals("{1=[], 2=[], 4=[]}",
				plateau1.cherche(positionJoueur4, rayon, Plateau.CHERCHE_YOURTE).toString());

		/* recherches autour de la case @4 avec un rayon 5 */
		rayon = 5;
		assertEquals("{1=[java.awt.Point[x=0,y=0], java.awt.Point[x=6,y=0]], "
				+ "2=[java.awt.Point[x=2,y=0], java.awt.Point[x=4,y=1], java.awt.Point[x=4,y=5], "
						+ "java.awt.Point[x=2,y=7], java.awt.Point[x=6,y=7]], "
				+ "4=[java.awt.Point[x=4,y=0], java.awt.Point[x=1,y=1], java.awt.Point[x=6,y=1], java.awt.Point[x=1,y=5]]}",
				plateau1.cherche(positionJoueur4, rayon, Plateau.CHERCHE_TOUT).toString());
		assertEquals("{1=[java.awt.Point[x=0,y=0], java.awt.Point[x=6,y=0]], 2=[], 4=[]}",
				plateau1.cherche(positionJoueur4, rayon, Plateau.CHERCHE_YOURTE).toString());
	}
	
	/**
	 * Test method for {@link jeu.Plateau#cherche(java.awt.Point, int, int)}.
	 */
	@Test
	void testCherche2() {
	  /*+----------------+
      	|$$  F-  @2  $$  |
      	|  @1    F-  @3$$|
      	|##  ##  ##  ##  |
      	|  ##  ##  ##  ##|
      	|              F1|
      	|  @4    F1      |
      	|              F3|
      	|  ##F1######F1  |
      	+----------------+*/
		Point position = new Point(4,1);  
		assertTrue( Plateau.contientUnChamp(plateau1.donneContenuCellule(position)));
		int rayon;
		
		/* recherches autour de la case F- en (2,0) avec un rayon 0 */
		rayon = 0;
		assertEquals("{1=[], 2=[java.awt.Point[x=4,y=1]], 4=[]}",
				plateau1.cherche(position, rayon, Plateau.CHERCHE_TOUT).toString());
		assertEquals("{1=[], 2=[], 4=[]}",
				plateau1.cherche(position, rayon, Plateau.CHERCHE_JOUEUR).toString());
		assertEquals("{1=[], 2=[java.awt.Point[x=4,y=1]], 4=[]}",
				plateau1.cherche(position, rayon, Plateau.CHERCHE_CHAMP).toString());
		assertEquals("{1=[], 2=[], 4=[]}",
				plateau1.cherche(position, rayon, Plateau.CHERCHE_YOURTE).toString());

		/* recherches autour de la case F- en (2,0) avec d'autres rayons */
		assertEquals("{1=[], 2=[], 4=[java.awt.Point[x=4,y=0]]}",
				plateau1.cherche(position, 1, Plateau.CHERCHE_JOUEUR).toString());
		assertEquals(2, plateau1.cherche(position, 2, Plateau.CHERCHE_JOUEUR).get(Plateau.CHERCHE_JOUEUR).size());
		assertTrue(plateau1.cherche(position, 2, Plateau.CHERCHE_JOUEUR).get(Plateau.CHERCHE_JOUEUR).contains(new Point(4,0)));
		assertTrue(plateau1.cherche(position, 2, Plateau.CHERCHE_JOUEUR).get(Plateau.CHERCHE_JOUEUR).contains(new Point(6,1)));
		
		assertEquals("{1=[], 2=[], 4=[]}",
				plateau1.cherche(position, 1, Plateau.CHERCHE_YOURTE).toString());
		assertEquals("{1=[java.awt.Point[x=6,y=0]], 2=[], 4=[]}",
				plateau1.cherche(position, 2, Plateau.CHERCHE_YOURTE).toString());
		assertEquals(2, plateau1.cherche(position, 3, Plateau.CHERCHE_YOURTE).get(Plateau.CHERCHE_YOURTE).size());
		assertTrue(plateau1.cherche(position, 3, Plateau.CHERCHE_YOURTE).get(Plateau.CHERCHE_YOURTE).contains(new Point(6,0)));
		assertTrue(plateau1.cherche(position, 3, Plateau.CHERCHE_YOURTE).get(Plateau.CHERCHE_YOURTE).contains(new Point(7,1)));
		
		assertEquals(2, plateau1.cherche(position, 2, Plateau.CHERCHE_CHAMP).get(Plateau.CHERCHE_CHAMP).size());
		assertTrue(plateau1.cherche(position, 2, Plateau.CHERCHE_CHAMP).get(Plateau.CHERCHE_CHAMP).contains(new Point(2,0)));
		assertTrue(plateau1.cherche(position, 2, Plateau.CHERCHE_CHAMP).get(Plateau.CHERCHE_CHAMP).contains(new Point(4,1)));
	}

	/**
	 * Test method for {@link jeu.Plateau#donneCheminEntre(java.awt.Point, java.awt.Point)}.
	 */
	@Test
	void testDonneCheminEntre() {
	  /*+----------------+
      	|$$  F-  @2  $$  |
      	|  @1    F-  @3$$|
      	|##  ##  ##  ##  |
      	|  ##  ##  ##  ##|
      	|              F1|
      	|  @4    F1      |
      	|              F3|
      	|  ##F1######F1  |
      	+----------------+*/
		/*** depart ou arrivee null ***/
		assertNull(	plateau1.donneCheminEntre(null, new Point(6,1)));
		assertNull(	plateau1.donneCheminEntre(new Point(0,5), null));
		assertNull(	plateau1.donneCheminEntre(null, null));
		
		/*** depart ou arrivee hors limites ***/
		assertNull(	plateau1.donneCheminEntre(new Point(0,5), new Point(8,2)));
		assertNull(	plateau1.donneCheminEntre(new Point(0,-1), new Point(0,1)));
		
		/*** debut ok et arrivee infranchissable ***/
		assertTrue( Plateau.contientUneZoneInfranchissable(plateau1.donneContenuCellule(0,2)));
		assertEquals("[(1,1), (1,2), (0,2)]", plateau1.donneCheminEntre(new Point(1,0), new Point(0,2)).toString());
		assertTrue( Plateau.contientUneZoneInfranchissable(plateau1.donneContenuCellule(1,3)));
		assertEquals("[(1,1), (1,2), (1,3)]", plateau1.donneCheminEntre(new Point(1,0), new Point(1,3)).toString());
			
		/*** depart ou arrivee ok ***/
		assertTrue( Plateau.contientUneZoneVide(plateau1.donneContenuCellule(1,0)));
		assertTrue( Plateau.contientLeJoueur(plateau1.donneContenuCellule(6,1),2));
		assertEquals("[(1,1), (2,1), (3,1), (3,0), (4,0), (5,0), (5,1), (6,1)]",
				plateau1.donneCheminEntre(new Point(1,0), new Point(6,1)).toString());
		assertEquals("[(5,1), (5,0), (4,0), (3,0), (3,1), (2,1), (1,1), (1,0)]",
				plateau1.donneCheminEntre(new Point(6,1), new Point(1,0)).toString());
		
		assertTrue( Plateau.contientUneZoneVide(plateau1.donneContenuCellule(1,2)));
		assertTrue( Plateau.contientUneZoneVide(plateau1.donneContenuCellule(3,2)));
		assertEquals("[(1,1), (2,1), (3,1), (3,2)]",
				plateau1.donneCheminEntre(new Point(1,2), new Point(3,2)).toString());
		assertEquals("[(3,1), (2,1), (1,1), (1,2)]",
				plateau1.donneCheminEntre(new Point(3,2), new Point(1,2)).toString());	
		
		/*** debut infranchissable et arrivee ok ***/
		assertTrue( Plateau.contientUneZoneInfranchissable(plateau1.donneContenuCellule(0,2)));
		assertEquals("[(1,2), (1,1), (2,1), (3,1), (3,2)]", plateau1.donneCheminEntre(new Point(0,2), new Point(3,2)).toString());
		
		assertTrue( Plateau.contientUneZoneInfranchissable(plateau1.donneContenuCellule(1,3)));
		assertEquals("[(1,2), (1,1), (1,0)]", plateau1.donneCheminEntre(new Point(1,3), new Point(1,0)).toString());
		
		/*** chemin impossible ***/
		assertNull(plateau1.donneCheminEntre(new Point(1,0), new Point(1,5)));
		
	}

	/**
	 * Test method for {@link jeu.Plateau#donneCheminAvecObstaclesSupplementaires(java.awt.Point, java.awt.Point, java.util.ArrayList)}.
	 */
	@Test
	void testDonneCheminAvecObstaclesSupplementaires() {
	  /*+----------------+
      	|$$  F-  @2  $$  |
      	|  @1    F-  @3$$|
      	|##  ##  ##  ##  |
      	|  ##  ##  ##  ##|
      	|              F1|
      	|  @4    F1      |
      	|              F3|
      	|  ##F1######F1  |
      	+----------------+*/
		List<Node> obstacles;
		
		/*** depart ou arrivee null ***/
		assertNull(	plateau1.donneCheminAvecObstaclesSupplementaires(null, new Point(6,1), null));
		assertNull(	plateau1.donneCheminAvecObstaclesSupplementaires(new Point(0,5), null, null));
		assertNull(	plateau1.donneCheminAvecObstaclesSupplementaires(null, null, null));
		
		/*** depart ou arrivee hors limites ***/
		assertNull(	plateau1.donneCheminAvecObstaclesSupplementaires(new Point(0,5), new Point(8,2), null));
		assertNull(	plateau1.donneCheminAvecObstaclesSupplementaires(new Point(0,-1), new Point(0,1), null));
		
		/*** debut ok et arrivee infranchissable ***/
		assertTrue( Plateau.contientUneZoneInfranchissable(plateau1.donneContenuCellule(0,2)));
		assertEquals("[(1,1), (1,2), (0,2)]", plateau1.donneCheminAvecObstaclesSupplementaires(new Point(1,0), new Point(0,2), null).toString());
		assertNotEquals("[(1,1), (1,2), (0,2)]", plateau1.donneCheminAvecObstaclesSupplementaires(new Point(1,0), new Point(0,2), 
				Arrays.asList( new Node(1,2))).toString());
		assertEquals("[(1,1), (1,2), (0,2)]", plateau1.donneCheminAvecObstaclesSupplementaires(new Point(1,0), new Point(0,2), 
				Arrays.asList( new Node(0,2))).toString());

		assertTrue( Plateau.contientUneZoneInfranchissable(plateau1.donneContenuCellule(1,3)));
		assertEquals("[(1,1), (1,2), (1,3)]", plateau1.donneCheminAvecObstaclesSupplementaires(new Point(1,0), new Point(1,3), null).toString());
		assertEquals("[(1,1), (1,2), (1,3)]", plateau1.donneCheminAvecObstaclesSupplementaires(new Point(1,0), new Point(1,3), 
				Arrays.asList( new Node(4,4), new Node(5,2))).toString());
		
		/*** depart ou arrivee ok ***/
		assertTrue( Plateau.contientUneZoneVide(plateau1.donneContenuCellule(1,0)));
		assertTrue( Plateau.contientLeJoueur(plateau1.donneContenuCellule(6,1),2));
		assertEquals("[(1,1), (2,1), (3,1), (3,0), (4,0), (5,0), (5,1), (6,1)]",
				plateau1.donneCheminAvecObstaclesSupplementaires(new Point(1,0), new Point(6,1), null).toString());
		assertEquals("[(5,1), (5,0), (4,0), (3,0), (3,1), (2,1), (1,1), (1,0)]",
				plateau1.donneCheminAvecObstaclesSupplementaires(new Point(6,1), new Point(1,0), null).toString());
		obstacles = Arrays.asList( new Node(3,1));
		assertNull(plateau1.donneCheminAvecObstaclesSupplementaires(new Point(1,0), new Point(6,1), obstacles));
		assertNull(plateau1.donneCheminAvecObstaclesSupplementaires(new Point(6,1), new Point(1,0), obstacles));
		
		assertTrue( Plateau.contientUneZoneVide(plateau1.donneContenuCellule(1,2)));
		assertTrue( Plateau.contientUneZoneVide(plateau1.donneContenuCellule(3,2)));
		assertEquals("[(1,1), (2,1), (3,1), (3,2)]",
				plateau1.donneCheminAvecObstaclesSupplementaires(new Point(1,2), new Point(3,2), null).toString());
		
		assertEquals("[(3,1), (2,1), (1,1), (1,2)]",
				plateau1.donneCheminAvecObstaclesSupplementaires(new Point(3,2), new Point(1,2), null).toString());	
		
		assertEquals("[(3,5), (3,6), (4,6), (5,6)]", 
				plateau1.donneCheminAvecObstaclesSupplementaires(new Point(3,4), new Point(5,6), null).toString());
		obstacles = Arrays.asList( new Node(4,4), new Node(6,4), new Node(7,4));
		assertEquals("[(3,5), (3,6), (4,6), (5,6)]", 
				plateau1.donneCheminAvecObstaclesSupplementaires(new Point(3,4), new Point(5,6), obstacles).toString());
		obstacles = Arrays.asList( new Node(3,6), new Node(3,1), new Node(3,7));
		assertEquals("[(4,4), (5,4), (5,5), (5,6)]", 
				plateau1.donneCheminAvecObstaclesSupplementaires(new Point(3,4), new Point(5,6), obstacles).toString());
		obstacles = Arrays.asList( new Node(3,6), new Node(5,4));
		assertNull(plateau1.donneCheminAvecObstaclesSupplementaires(new Point(3,4), new Point(5,6), obstacles));
		
		/*** debut infranchissable et arrivee ok ***/
		assertTrue( Plateau.contientUneZoneInfranchissable(plateau1.donneContenuCellule(0,2)));
		assertEquals("[(1,2), (1,1), (2,1), (3,1), (3,2)]", 
				plateau1.donneCheminAvecObstaclesSupplementaires(new Point(0,2), new Point(3,2), null).toString());
		obstacles = Arrays.asList( new Node(5,5), new Node(2,1));
		assertNull(plateau1.donneCheminAvecObstaclesSupplementaires(new Point(0,2), new Point(3,2), obstacles));

		assertTrue( Plateau.contientUneZoneInfranchissable(plateau1.donneContenuCellule(1,3)));
		assertEquals("[(1,2), (1,1), (1,0)]", 
				plateau1.donneCheminAvecObstaclesSupplementaires(new Point(1,3), new Point(1,0), null).toString());
		obstacles = Arrays.asList( new Node(1,2), new Node(1,1), new Node(1,0));
		assertNull( plateau1.donneCheminAvecObstaclesSupplementaires(new Point(1,3), new Point(1,0), obstacles));
		
		/*** chemin impossible ***/
		assertNull(plateau1.donneCheminAvecObstaclesSupplementaires(new Point(1,0), new Point(1,5), null));
	}

	/* ********************************************************
	 *       GENERATION DE PLATEAU ALEATOIRE
	 * ********************************************************/

	/**
	 * Test method for {@link jeu.Plateau#generePlateauAleatoire(int, int, int, int, int)}.
	 */
	@RepeatedTest(10)
	void testGenerePlateauAleatoire() {
		 Plateau nouveau = null;
		 while ( nouveau == null )
			 nouveau = Plateau.generePlateauAleatoire( 100, 5, 2, 3, 5);
		 assertEquals(100, nouveau.donneNombreDeTours());
		 assertEquals(10, nouveau.donneTaille());
		 int nbChamps = 0, nbArbres = 0, nbYourtes = 0, nbVides = 0, nbDeparts = 0;
		 for (int i = 0; i < 10; i++) {
			 for(int j = 0; j < 10; j++) {
				 int n = nouveau.donneContenuCellule(i, j);
				 if (Plateau.contientUnChamp(n)) nbChamps++;
				 else if (Plateau.contientUneZoneInfranchissable(n)) nbArbres++;
				 if (Plateau.contientUneYourte(n)) nbYourtes++;
				 if (Plateau.contientUneZoneVide(n)) nbVides++;
				 if (Plateau.contientUnDepart(n)) nbDeparts++;
			 }
		 }
		 assertEquals(20, nbArbres);
		 assertEquals(12, nbChamps);
		 assertEquals(8, nbYourtes);
		 assertEquals(4, nbDeparts);
		 assertEquals(nouveau.donneTaille()*nouveau.donneTaille(), 
				 nbArbres + nbChamps + nbYourtes + nbDeparts + nbVides);
	}

}
