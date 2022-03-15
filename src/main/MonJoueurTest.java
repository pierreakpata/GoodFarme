package main;

import jeu.Joueur;
import jeu.MaitreDuJeuGoodFarm;
import jeu.Plateau;
import jeu.astar.Node;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MonJoueurTest
{

    private MonJoueur joueur;
    private Plateau etatDuJeu;


    @BeforeEach
    void setUp()
    {
        String description1 =
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
        joueur = new MonJoueur("Bobby");
        String joueurs1 = ",Joueur0:0:1:1:100:0,Joueur1:1:4:0:100:0,Joueur2:2:6:1:100:0,Joueur3:3:1:5:100:0,0,24";
        etatDuJeu = new Plateau(8, description1);
        MaitreDuJeuGoodFarm jeu = new MaitreDuJeuGoodFarm(etatDuJeu);
        jeu.metJoueurEnPosition(0, joueur);



    }

    @Test
    void cheminPlusCourtVersChamps()
    {
        ArrayList<Node> expected = new ArrayList<>();
        expected.add(new Node(1,0));
        expected.add(new Node(2,0));
        assertEquals(expected,joueur.cheminPlusCourtVersChamps(etatDuJeu));
    }

    @Test
    void cheminLePlusCourtVersYourte()
    {
        ArrayList<Node> expected = new ArrayList<>();
        expected.add(new Node(1,0));
        expected.add(new Node(0,0));
        assertEquals(expected,joueur.cheminLePlusCourtVersYourte(etatDuJeu));
    }

    @Test
    void getYourtes()
    {
        ArrayList<Node> expected = new ArrayList<>();
        expected.add(new Node(0,0));
        expected.add(new Node(6,0));
        expected.add(new Node(7,1));
        assertEquals(expected,joueur.getYourtes(etatDuJeu));
    }

    @Test
    void traduitNodeEnAction()
    {

        assertEquals(Joueur.Action.RIEN, joueur.traduitNodeEnAction(new Node(1, 2), 2));
        assertEquals(Joueur.Action.RIEN,joueur.traduitNodeEnAction(new Node(6, 0), 1));
    }

    @Test
    void faitUneAction()
    {
        assertEquals(Joueur.Action.HAUT, joueur.faitUneAction(etatDuJeu));
    }
}