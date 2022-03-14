/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import jeu.Joueur;
import jeu.Plateau;
import jeu.astar.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author lucile
 */
public class MonJoueur extends Joueur
{
    private ArrayList<Node> cheminLePlusCourt;


    public MonJoueur(String sonNom)
    {
        super(sonNom);
        cheminLePlusCourt = new ArrayList<>();


    }

    private ArrayList<Node> getObstacles(Plateau etatDuJeu)
    {
        ArrayList<Node> listeObstacles = new ArrayList<>();


        for (int x = 0; x < etatDuJeu.donneTaille(); x++)
        {
            for (int y = 0; y < etatDuJeu.donneTaille(); y++)
            {
                if (etatDuJeu.donneContenuCellule(x, y) == Plateau.ENDROIT_INFRANCHISSABLE)
                    listeObstacles.add(new Node(x, y));
            }
        }
        return listeObstacles;
    }

    private ArrayList<Node> getChamps(Plateau etatDuJeu)
    {
        ArrayList<Node> listeChamps = new ArrayList<>();


        for (int x = 0; x < etatDuJeu.donneTaille(); x++)
        {
            for (int y = 0; y < etatDuJeu.donneTaille(); y++)
            {
                if ( (Plateau.contientUnChamp(etatDuJeu.donneContenuCellule(x, y)) &&
                            (Plateau.contientUnChampQuiNeLuiAppartientPas(this,etatDuJeu.donneContenuCellule(x,y)) )))
                {  listeChamps.add(new Node(x, y));}
            }
        }
        return listeChamps;
    }

    public ArrayList<Node> cheminPlusCourtVersChamp(Plateau etatDuJeu)
    {

        System.out.println("test-1");
        ArrayList<Node> obstacles = getObstacles(etatDuJeu);
        ArrayList<Node> temp;
        ArrayList<Node> champs = getChamps(etatDuJeu);
        ArrayList<Node> min = etatDuJeu.donneCheminAvecObstaclesSupplementaires(this.donnePosition(), champs.get(0), obstacles);
        for (Node node : champs)
        {
            System.out.println("test");
            temp = etatDuJeu.donneCheminAvecObstaclesSupplementaires(this.donnePosition(), node, obstacles);
            if (min.size() > temp.size())
                min = temp;
        }
        System.out.println(min);
        return min;
    }

    public Action traduitNodeEnAction(Node cible)
    {
        Action action = null;
        if (this.donnePosition().x == cible.x && this.donnePosition().y > cible.y)
            action = Action.HAUT;
        if (this.donnePosition().x == cible.x && this.donnePosition().y < cible.y)
            action = Action.BAS;
        if (this.donnePosition().y == cible.y && this.donnePosition().x > cible.x)
            action = Action.GAUCHE;
        if (this.donnePosition().y == cible.y && this.donnePosition().x < cible.x)
            action = Action.DROITE;

        cheminLePlusCourt.remove(cible);

        return action;
    }


    @Override
    public Action faitUneAction(Plateau etatDuJeu)
    {
        if (cheminLePlusCourt.size() == 0)
            cheminLePlusCourt = cheminPlusCourtVersChamp(etatDuJeu);
        // getChamps(etatDuJeu);
        //ArrayList<Node> eej = etatDuJeu.donneCheminAvecObstaclesSupplementaires(this.donnePosition(), new Node(2, 1), getObstacles(etatDuJeu));
        //System.out.println(eej);

        return traduitNodeEnAction(cheminLePlusCourt.get(0));


    }
}