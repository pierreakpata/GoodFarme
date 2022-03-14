/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import jeu.Joueur;
import jeu.Plateau;

/**
 *
 * @author lucile
 */
public class MonJoueur extends Joueur {

    public MonJoueur(String sonNom) {
        super(sonNom);
    }

    @Override
    public Action faitUneAction(Plateau etatDuJeu) {
        //return super.faitUneAction(etatDuJeu); // a modifier
        return Action.GAUCHE;
    }
    
}