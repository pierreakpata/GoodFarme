/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Paquet
 */
package main;

import jeu.Joueur;
import jeu.Plateau;
import jeu.astar.*;

import java.awt.*;
import java.util.ArrayList;


/**
 * Classe d&eacute;finissant les m&eacute;thodes permettant de d&eacute;terminer le comportement et les actions de notre joueur.
 *  @author [Gabriel, Pierre, Amandine, Charafa, L&eacute;onie]
 */
public class MonJoueur extends Joueur
{
    private ArrayList<Node> cheminLePlusCourtVersChamps;
    private ArrayList<Node> cheminLePlusCourtVersYourte;

    /**
     * Constructeur de la classe, faisant appel au constructeur de la classe mère @{@link Joueur}
     * et initialise @{{@link MonJoueur#cheminLePlusCourtVersChamps}} à une @{@link ArrayList} vide et le nom de notre joueur.
     * @param sonNom correspond au nom du joueur.
     */
    public MonJoueur(String sonNom)
    {
        super(sonNom);
        cheminLePlusCourtVersChamps = new ArrayList<>();
        cheminLePlusCourtVersYourte = new ArrayList<>();

    }


    /**
     * Permet d'obtenir une liste d'obstacles (un obstacle est soit un arbre soit un joueur (permet d'&eacute;viter que l'on soit bloqu&eacute;) par d'autres joueurs.
     *
     * @param etatDuJeu Repr&eacute;sente l'&eacute;tat du plateau de jeu actuel.
     * @return Renvoie une @{@link ArrayList} contenant les nodes où les obstacles se situent.
     */
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

    /**
     * Permet de r&eacute;cup&eacute;rer une liste des champs qui ne sont pas pris par notre @{@link MonJoueur}
     * @param etatDuJeu Repr&eacute;sente l'&eacute;tat du plateau de jeu actuel.
     * @return Renvoie une @{@link ArrayList} contenant les nodes où les champs ne nous appartenant pas se situent.
     */
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


    /**
     * Calcule une liste de @{@link Node} guidant notre joueur vers le champ le plus proche à l'aide de l'algorithme @{@link AStar}
     * @param etatDuJeu Repr&eacute;sente l'&eacute;tat du plateau de jeu actuel.
     * @return Renvoie une @{@link ArrayList} de @{@link Node} guidant notre joueur vers le champ le plus proche
     */
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


    /**
     * Calcule une liste de @{@link Node} guidant notre joueur vers la yourte la plus proche à l'aide de l'algorithme @{@link AStar},
     * prend en compte les obstacles (joueurs et arbres).
     * @param etatDuJeu Repr&eacute;sente l'&eacute;tat du plateau de jeu actuel.
     * @return Renvoie une @{@link ArrayList} de @{@link Node} guidant notre joueur vers la yourte le plus proche
     */
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
            return new ArrayList<Node>();
        }

    }


    /**
     * R&eacute;cupère la liste des Yourtes disponibles (pas occup&eacute;es par d'autres joueurs nous inclus)
     * @param etatDuJeu Repr&eacute;sente l'&eacute;tat du plateau de jeu actuel.
     * @return {@link ArrayList} ArrayList de Nodes contenant les positions des diff&eacute;rentes yourtes
     */
    public ArrayList<Node> getYourtes(Plateau etatDuJeu)
    {
        ArrayList<Node> listeYourtes = new ArrayList<>();
        //ArrayList<Node> positionsJoueurs = new ArrayList<>();

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

    /**
     * En fonction du noeud recu en argument on renvoie l'action qui correspond au mouvement à effectuer.
     * @param cible Repr&eacute;sente le point vers lequel le joueur doit se d&eacute;placer
     * @param type  Permet de choisir si le joueur se d&eacute;place vers une yourte ou un champ
     * @return une Action qui sera utilis&eacute;e par {@link MonJoueur#faitUneAction(Plateau)}
     */
    public Action traduitNodeEnAction(Node cible, int type)
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
        if (type == 1)
        {
            if (this.cheminLePlusCourtVersYourte.size() == 0)
                action = Action.RIEN;
            cheminLePlusCourtVersYourte.remove(cible);

        }
        if (type == 2)
        {
            if (this.cheminLePlusCourtVersChamps.size() == 0)
                action = Action.RIEN;
            cheminLePlusCourtVersChamps.remove(cible);
        }
        return action;
    }

    /**
     * La fonction permet de d&eacute;terminer l'action la plus adapt&eacute;e à l'environnement.
     *
     * @param etatDuJeu &eacute;tat du plateau de jeu
     * @return une action d&eacute;finie en fonction de diff&eacute;rents critères
     */
    @Override
    public Action faitUneAction(Plateau etatDuJeu)
    {

        cheminLePlusCourtVersChamps = cheminPlusCourtVersChamps(etatDuJeu);
        if (Plateau.contientUneYourte(etatDuJeu.donneContenuCellule(this.donnePosition())) && this.donneEnergie() < 100)
            return Action.RIEN;

        if (cheminLePlusCourtVersChamps.size() + 20 > this.donneEnergie()
                || etatDuJeu.donneContenuCellule(new Node(this.donnePosition().x, this.donnePosition().y)) == Plateau.CHERCHE_YOURTE)
        {
            cheminLePlusCourtVersYourte = cheminLePlusCourtVersYourte(etatDuJeu);
            if (cheminLePlusCourtVersYourte.isEmpty())
                return Action.RIEN;
            else
                return traduitNodeEnAction(cheminLePlusCourtVersYourte.get(0), 1);
        }


        if (cheminLePlusCourtVersChamps.size() == 0)
        {
            return Action.RIEN;
        }


        return traduitNodeEnAction(cheminLePlusCourtVersChamps.get(0), 2);

    }
}