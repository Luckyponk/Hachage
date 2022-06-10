import java.math.BigInteger;

public class Maillon {

    private BigInteger valeur;
    private Maillon suivant;

    /** Constructeur vide */
    public Maillon() {
        suivant = null;
    }

    /** Constructeur avec la valeur */
    public Maillon(BigInteger n) {
        valeur = n;
        suivant = null;
    }

    /*
     * Résultat : retourne la valeur contenue dans this
     */
    public BigInteger getVal() {
        return this.valeur;
    }

    /*
     * Donnée : un BigInteger v
     * Résultat : Attribue à this la valeur v
     */
    public void setVal(BigInteger v) {
        this.valeur = v;
    }

    /*
     * Résultat : Retourne le maillon suivant
     */
    public Maillon getSuiv() {
        return this.suivant;
    }

    /*
     * Donnée : un Maillon m
     * Résultat : Attribue m au maillon suivant
     */
    public void setSuiv(Maillon m) {
        this.suivant = m;
    }

    // public String toString() {
    // return BigInteger.toString(this.valeur);
    // }

    /* -------------------------------------------------- */

}