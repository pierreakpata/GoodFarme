package test;
import jeu.Joueur;
import jeu.Plateau;

/**
 * Joueur qui joue une séquence d'action prédéterminée.
 * 
 * @author lucile
 *
 */
public class Automate extends Joueur {

	/** Séquence d'actions de déplacement du joueur. */
	private String deplacements;
    
    /**
     * Crée un joueur de nom donné et de séquence d'actions donnée.
     * 
     * La séquence d'actions est une chaîne formé des caractères D (pour DROITE), G (pour GAUCHE),
     * H (pour HAUT), B (pour BAS) et . (pour RIEN). Par exemple, la chaîne "D.BBH" définit la séquence
     * DROITE, RIEN, BAS, BAS, HAUT.
     * @param nom du joueur
     * @param dep la séquence des déplacements du joueur
     */
    public Automate(String nom, String dep) { super(nom); deplacements=dep; i = 0;}
    private int i; 
    
    @Override
    public Action faitUneAction(Plateau etatDuJeu){
    	if ( i >= deplacements.length()) return Action.RIEN; 
    	switch (deplacements.charAt(i++)) {
    	case 'H': return Action.HAUT;
    	case 'B': return Action.BAS;
    	case 'D': return Action.DROITE;
    	case 'G': return Action.GAUCHE;
    	case '.': return Action.RIEN;
    	default : throw new Error("Action inconnue");
    	}
    }
}
