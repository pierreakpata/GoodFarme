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
    private ArrayList<Node> cheminLePlusCourtVersYourte;

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
                if (Plateau.contientUnJoueur(etatDuJeu.donneContenuCellule(x, y)))
                    listeObstacles.add(new Node(x, y));
                for (Point point : etatDuJeu.cherche(this.donnePosition(), 2, Plateau.CHERCHE_JOUEUR).get(4))
                {
                    listeObstacles.add(new Node(point.x, point.y));
                }
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
                if ((Plateau.contientUnChamp(etatDuJeu.donneContenuCellule(x, y)) &&
                        (Plateau.contientUnChampQuiNeLuiAppartientPas(this, etatDuJeu.donneContenuCellule(x, y)))))
                {
                    listeChamps.add(new Node(x, y));
                }
            }
        }
        return listeChamps;
    }

    public ArrayList<Node> cheminPlusCourtVersChamps(Plateau etatDuJeu)
    {
        ArrayList<Node> obstacles = getObstacles(etatDuJeu);
        ArrayList<Node> temp;
        ArrayList<Node> champs = getChamps(etatDuJeu);

        if (champs.size() != 0)
        {

            ArrayList<Node> min = etatDuJeu.donneCheminAvecObstaclesSupplementaires(this.donnePosition(), champs.get(0), obstacles);
            for (Node node : champs)
            {
                temp = etatDuJeu.donneCheminAvecObstaclesSupplementaires(this.donnePosition(), node, obstacles);
                if ((temp != null && min != null))
                {
                    if (min.size() > temp.size())
                        min = temp;
                }

            }
            return (min == null) ? new ArrayList<Node>() : min;
        } else
        {
            return new ArrayList<Node>();
        }
    }

    public ArrayList<Node> cheminLePlusCourtVersYourte(Plateau etatDuJeu)
    {
        ArrayList<Node> obstacles = getObstacles(etatDuJeu);
        ArrayList<Node> temp;
        ArrayList<Node> yourtes = getYourtes(etatDuJeu);
        ArrayList<Node> min = etatDuJeu.donneCheminAvecObstaclesSupplementaires(this.donnePosition(), yourtes.get(0), obstacles);


        if (yourtes.size() != 0)
        {

            for (Node node : yourtes)
            {
                temp = etatDuJeu.donneCheminAvecObstaclesSupplementaires(this.donnePosition(), node, obstacles);
                if ((temp != null && min != null))
                {
                    if (min.size() > temp.size())
                        min = temp;
                }

            }
            return (min == null) ? new ArrayList<Node>() : min;
        } else
        {
            System.out.println("OUFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF!");
            return new ArrayList<Node>();
        }

    }


    /**
     * Récupère la liste des Yourtes disponibles (pas occupées par d'autres joueurs nous inclus)
     *
     * @param etatDuJeu
     * @return {@link  ArrayList<Node>} ArrayList de Nodes contenant les positions des différentes yourtes
     */
    public ArrayList<Node> getYourtes(Plateau etatDuJeu)
    {
        ArrayList<Node> listeYourtes = new ArrayList<>();
        ArrayList<Node> positionsJoueurs = new ArrayList<>();


        for (int x = 0; x < etatDuJeu.donneTaille(); x++)
        {
            for (int y = 0; y < etatDuJeu.donneTaille(); y++)
            {
                if (Plateau.contientUneYourte(etatDuJeu.donneContenuCellule(x, y)))
                {
                    if (!Plateau.contientUnJoueur(etatDuJeu.donneContenuCellule(x, y)))
                        listeYourtes.add(new Node(x, y));
                }
            }
        }
        return listeYourtes;
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
        if (this.cheminLePlusCourt.size() == 0)
            action = Action.RIEN;
        cheminLePlusCourt.remove(cible);

        return action;
    }


    @Override
    public Action faitUneAction(Plateau etatDuJeu)
    {

        cheminLePlusCourt = cheminPlusCourtVersChamps(etatDuJeu);
        if (cheminLePlusCourt.size() + 20 > this.donneEnergie() || etatDuJeu.donneContenuCellule( new Node(this.donnePosition().x, this.donnePosition().y)) == Plateau.CHERCHE_YOURTE )
        {
            cheminLePlusCourtVersYourte = cheminLePlusCourtVersYourte(etatDuJeu);
            System.out.println(cheminLePlusCourtVersYourte);
            return traduitNodeEnAction(cheminLePlusCourtVersYourte.get(0));
        }


        if (cheminLePlusCourt.size() == 0)
        {
                return Action.RIEN;
        }

        return traduitNodeEnAction(cheminLePlusCourt.get(0));
















    }
}