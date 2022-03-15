/*
 * Exemple d'utilisation du JAR AreneGoodFarm.jar
 * Version Rois MIAGE - 0.1
 */
package main;

import gui.FenetreDeJeuGoodFarm;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import jeu.Joueur;
import jeu.MaitreDuJeuGoodFarm;
import jeu.Plateau;
import jeu.Joueur.Action;
import jeu.JoueurHumain;
import jeu.astar.Node;

/**
 *
 * @author lucile
 */
public class Lanceur {
    
    private static String MON_PLATEAU_DE_TEST = 
"+----------------------------------------+\n" +
"|        ##  ####        ####  ##        |\n" +
"|    F-        ##  ####  ##        F-    |\n" +
"|  ##    @1      ##    ##      @2    ##  |\n" +
"|F-$$  $$                        $$  $$F-|\n" +
"|  ##      ####  ##    ##  ####      ##  |\n" +
"|    ##  ####                ####  ##    |\n" +
"|##  ##$$  ##  ##        ##  ##  $$##  ##|\n" +
"|##    ##      ####    ####      ##    ##|\n" +
"|    ####    ##    ####    ##    ####    |\n" +
"|  F-                                F-  |\n" +
"|  F-                                F-  |\n" +
"|    ####    ##    ####    ##    ####    |\n" +
"|##    ##      ####    ####      ##    ##|\n" +
"|##  ##$$  ##  ##        ##  ##  $$##  ##|\n" +
"|    ##  ####                ####  ##    |\n" +
"|  ##      ####  ##    ##  ####      ##  |\n" +
"|F-$$  $$                        $$  $$F-|\n" +
"|  ##    @3      ##    ##      @4    ##  |\n" +
"|    F-        ##  ####  ##        F-    |\n" +
"|        ##  ####        ####  ##        |\n" +
"+----------------------------------------+";

     public static void main(String[] args) {

        // Génération du plateau
        Plateau p = Plateau.generePlateauAleatoire(300, 10, 1, 10,20);
        // Plateau p = new Plateau(300, MON_PLATEAU_DE_TEST);
        
        // Création du maitre de jeu
        MaitreDuJeuGoodFarm jeu = new MaitreDuJeuGoodFarm(p);
        
          // Création de la fenêtre de jeu
        FenetreDeJeuGoodFarm f = new FenetreDeJeuGoodFarm(jeu, true, false);

        // Ajout des joueurs dans le jeu.
        // Par défaut les joueurs non ajoutés exolicitement sont des instances de Joueur
        jeu.metJoueurEnPosition(0, new MonJoueur("Bot"));
        //jeu.metJoueurEnPosition(1, new JoueurHumain("Lucile",f));
        
        // Envoie des logs de la partie dans un fichier texte
        f.log = new java.io.File("/tmp/titi.log");

        
        // Ajout d'un listenner des cliques souris sur le plateau
        f.setMouseClickListener((int x, int y, int bt) -> {
            System.out.println("On a cliqué sur la cellule " + x + "," + y);           

            // Ne fonctionne que pour une partie en cours
            Joueur j = p.donneJoueur(p.donneJoueurCourant());  
            System.out.println("Depart="+j.donnePosition());
            System.out.println("Arrivé="+new Point( x, y));
            
            afficheResultatRecherche(p.cherche(new Point( x, y), 40, Plateau.CHERCHE_TOUT));         
            ArrayList<Node> a = p.donneCheminEntre(j.donnePosition(), new Point( x, y));
            f.afficheAstarPath(a);
        });       

        // Affichage de la fenêtre
        java.awt.EventQueue.invokeLater(() -> {
            f.setVisible(true);
        });  
    }

    // Va avec la fonction clique souris.
    private static void afficheResultatRecherche(HashMap<Integer, ArrayList<Point>> cherche) {
        cherche.keySet().stream().map((k) -> {
            System.out.println("Type="+k);
            return k;
        }).map((k) -> {
            cherche.get(k).forEach((p) -> {
                System.out.println("("+p.x+","+p.y+") ");
            });
            return k;
        }).forEachOrdered((_item) -> {
            System.out.println("\n");
        });
    }
}