import java.math.BigInteger;

public class ListeBigI {

	public Maillon tete;

	/**
	 * Constructeur d'une liste vide
	 */
	public ListeBigI() {
		this.tete = null;
	}

	/**
	 * Constructeur d'une liste a partir d'un maillon
	 */
	public ListeBigI(Maillon m) { // OBSOLETE : NE PLUS UTILISER
		this.tete = m;
	}

	/**
	 * Constructeur d'une liste a un seul element
	 */
	public ListeBigI(BigInteger x) {
		this.tete = new Maillon(x); // ou bien: this(new Maillon(x));
	}

	/**
	 * @param tabListe est un tableau contenant les elements de la liste
	 *                 Pre-requis : aucun
	 */
	public ListeBigI(BigInteger[] tabListe) {
		this();
		if (tabListe.length > 0) {
			this.tete = new Maillon(tabListe[0]);
			Maillon curThis = this.tete;
			for (int i = 1; i < tabListe.length; i++) {
				curThis.setSuiv(new Maillon(tabListe[i])); // creation et accrochage du maillon (encore vide) suivant
				curThis = curThis.getSuiv();
			}
		}
	}

	/**
	 * Prerequis: aucun
	 * construit une liste completement disjointe de la liste l
	 */
	public ListeBigI(ListeBigI l) { // constructeur par recopie profonde
		this();
		if (!l.estVide()) {

			this.tete = new Maillon(l.tete.getVal());
			Maillon curThis = this.tete;
			Maillon curL = l.tete.getSuiv();

			while (curL != null) {
				curThis.setSuiv(new Maillon(curL.getVal())); // creation et accrochage du maillon suivant
				curThis = curThis.getSuiv();
				curL = curL.getSuiv();
			}
		}
	}

	/*
	 * Résultat : retourne un booléen, true si this est vide et false sinon
	 */
	public boolean estVide() {
		return (this.tete == null);
	}

	// public int valTete () {
	// return this.tete.getVal();
	// }

	/*
	 * Résultat : ajoute un élément en tête de this
	 */
	public void ajoutTete(BigInteger x) {
		Maillon m = new Maillon(x);
		m.setSuiv(this.tete);
		this.tete = m;
	}

	/*
	 * Donnée : une ListeBigI l
	 * Résultat : ajoute les éléments de la ListeBigI l en tête de this
	 */
	public void ajoutListe(ListeBigI l) {
		Maillon cur = l.tete;
		while (cur != null) {
			ajoutTete(cur.getVal());
			cur = cur.getSuiv();
		}
	}

	/*
	 * Résultat : Supprime la tête de this et renvoie la valeur qui y était stockée
	 */
	public BigInteger supprTete() {
		// pré this non vide
		BigInteger res = tete.getVal();
		tete = tete.getSuiv();
		return res;
	}

	/*
	 * Donnée : un BigInteger x
	 * Résultat : Retourne un booléen, true si x est contenu dans this et false
	 * dans le cas contraire
	 */
	public boolean contient(BigInteger x) {
		Maillon courant = this.tete;
		while (courant != null && !(courant.getVal().equals(x))) {
			courant = courant.getSuiv();
		}
		return courant != null;
	}

	public String toString() {
		String s = "(";
		Maillon courant = this.tete;
		while (courant != null) {
			s = s + (courant.getVal()) + ((courant.getSuiv() != null) ? ", " : "");
			courant = courant.getSuiv();
		}
		return s + ")";
	}

	/**
	 * Longueur d'une liste
	 */
	public int longueur() {
		int lg = 0;
		Maillon courant = this.tete;
		while (courant != null) {
			lg++;
			courant = courant.getSuiv();
		}
		return lg;
	}

	/**
	 * Ajoute @param en fin de liste
	 */
	public void ajoutFin(BigInteger n) {
		if (this.estVide()) {
			this.tete = new Maillon(n);
		} else {
			Maillon courant = this.tete;
			while (courant.getSuiv() != null) {
				courant = courant.getSuiv();
			}
			courant.setSuiv(new Maillon(n));
		}
	}

	/**
	 * Supprime l'elt contenant la premiere occurrence de @param
	 */
	public void supprOcc(BigInteger n) {
		if (this.estVide()) {
			// rien
		} else if (this.tete.getVal().equals(n)) // suppression en tête
			this.tete = this.tete.getSuiv();
		else {
			Maillon prev = this.tete;
			Maillon current = prev.getSuiv();
			while (current != null && current.getVal() != n) {
				prev = current;
				current = current.getSuiv();
			}
			if (current != null) { // current.getVal() == n
				prev.setSuiv(current.getSuiv());
			}
		}
	}

} // end class

/**
 * @return this est une sous-liste de @param
 * 
 *         ancienne version obsolete ou incorrecte...
 * 
 *         public boolean sousListe (Liste l) {
 *         boolean prefixe = false;
 *         Maillon curThis = this.tete;
 *         Maillon curL = l.tete;
 *         while (curThis != null && curL != null && ! prefixe) {
 *         prefixe = curThis.estPrefixe(curL);
 *         if (! prefixe) // test pas necessaire car curL non vide mais bon...
 *         curL = curL.getSuiv();
 *         }
 *         return prefixe;
 *         }
 */