import java.math.BigInteger;

public class HTNaive {
	private ListeBigI[] listeHachage;
	private long totalTimeh = 0;
	private long totalTimeContient = 0;

	/*
	 * Pré-requis : m>0
	 * Donnée : un nombre entier m
	 * Action : constructeur d'un objet HTNaive, initialise une table de hachage
	 * avec m ListeBigI vides
	 */
	public HTNaive(int m) {
		this.listeHachage = new ListeBigI[m];

		for (int i = 0; i < m; i++) {
			this.listeHachage[i] = new ListeBigI();
		}
	}

	/*
	 * Pré-requis : m>0
	 * Donnée : un nombre entier m et une ListeBigI l
	 * Action : constructeur d'un objet HTNaive, initialise une table de hachage
	 * avec m ListeBigI remplies des éléments de la ListeBigI l
	 */
	public HTNaive(ListeBigI l, int m) {
		this(m);
		this.ajoutListe(l);
	}

	/*
	 * Donnée : un double f et une ListeBigI l
	 * Action : constructeur d'un objet HTNaive, initialise une table de hachage
	 * avec f*|l| ListeBigI remplies des éléments de la ListeBigI l (|l| étant le
	 * nombre d'éléments différents de l)
	 */
	public HTNaive(ListeBigI l, double f) {
		HTNaive temp = new HTNaive(l, 1000);

		this.listeHachage = new ListeBigI[(int) (temp.getCardinal() * f)];

		for (int i = 0; i < (int) (temp.getCardinal() * f); i++) {
			this.listeHachage[i] = new ListeBigI();
		}

		this.ajoutListe(l);
	}

	/*
	 * Résultat: retourne l'attribut totalTimeh qui correspond au temps passé à
	 * effectuer le calcul la fonction h
	 */
	public long getTotalTimeh() {
		return this.totalTimeh;
	}

	/*
	 * Résultat: retourne l'attribut totalTimeh qui correspond au temps passé à
	 * à effectuer des appels à la méthode contient de la classe ListeBigI
	 */
	public long getTotalTimeContient() {
		return this.totalTimeContient;
	}

	/*
	 * Pré-requis : 0<=i<this.listeHachage.length
	 * Donnée : un nombre entier i
	 * Résultat: retourne le nombre de ListeBigI dans la table de hachage
	 */
	public ListeBigI getListe(int i) {
		return this.listeHachage[i];
	}

	/*
	 * Donnée : un BitInteger u
	 * Résultat: retourne le résultat de u%m avec m correspondant au nombre de
	 * ListeBigI présente dans la table de hachage
	 */
	public int h(BigInteger u) {
		long deb = System.currentTimeMillis();

		BigInteger m = BigInteger.valueOf(this.listeHachage.length);
		int resultat = (u.remainder(m)).intValue();

		long fin = System.currentTimeMillis();

		this.totalTimeh += fin - deb;

		return resultat;
	}

	/*
	 * Donnée : un BitInteger u
	 * Résultat : retourne un booléen, true si u est présent dans la table de
	 * hachage et false dans le cas contraire
	 */
	public boolean contient(BigInteger u) {
		int indice = h(u);

		long deb = System.currentTimeMillis();

		boolean contenir = this.listeHachage[indice].contient(u);

		long fin = System.currentTimeMillis();

		this.totalTimeContient += fin - deb;

		return contenir;
	}

	/*
	 * Donnée : un BitInteger u
	 * Résultat : retourne un booléen, true si u a été ajouté à la liste de hachage
	 * et false si l'élément était déjà présent dans la table (il ne sera donc pas
	 * ajouté)
	 */
	public boolean ajout(BigInteger u) {
		if (this.contient(u)) {
			return false;
		} else {
			this.listeHachage[this.h(u)].ajoutTete(u);
			return true;
		}
	}

	/*
	 * Donnée : une ListeBigI L
	 * Action : ajoute à la table de hachage l'ensemble des éléments de la ListeBigI
	 * L qui n'y sont pas déjà
	 */
	public void ajoutListe(ListeBigI L) {
		ListeBigI copie = new ListeBigI(L);
		while (copie.estVide() == false) {
			this.ajout(copie.supprTete());
		}
	}

	/*
	 * Résultat : retourne une ListeBigI qui contient l'ensemble des éléments
	 * présents dans la table de hachage
	 */
	public ListeBigI getElements() {
		ListeBigI liste = new ListeBigI();

		for (int i = 0; i < this.listeHachage.length; i++) {
			ListeBigI copieListe = new ListeBigI(this.listeHachage[i]);
			while (!copieListe.estVide()) {
				liste.ajoutTete(copieListe.supprTete());
			}
		}

		return liste;
	}

	/*
	 * Résultat: retourne un entier qui correspond au nombre de ListeBigI
	 * présente dans la table de hachage
	 */
	public int getNbListes() {
		return this.listeHachage.length;
	}

	/*
	 * Résultat: retourne un entier qui correspond au nombre d'éléments totals dans
	 * la table de hachage
	 */
	public int getCardinal() {
		int cardinal = 0;
		for (int i = 0; i < this.listeHachage.length; i++) {
			cardinal += this.listeHachage[i].longueur();
		}

		return cardinal;
	}

	/*
	 * Résultat: retourne un entier qui correspond à la taille de la ListeBigI la
	 * plus grande présente dans la table de hachage
	 */
	public int getMaxSize() {
		int maximum = 0;
		for (int i = 0; i < this.listeHachage.length; i++) {
			if (this.listeHachage[i].longueur() > maximum) {
				maximum = this.listeHachage[i].longueur();
			}
		}

		return maximum;
	}

	public String toString() {
		String chaine = "";
		for (int i = 0; i < this.listeHachage.length; i++) {
			chaine += "t[" + i + "] : " + this.listeHachage[i].toString() + "\n";
		}

		return chaine;
	}

	public String toStringV2() {
		String chaine = "";
		for (int i = 0; i < this.listeHachage.length; i++) {
			for (int j = 0; j < this.listeHachage[i].longueur(); j++) {
				if (j == 0) {
					chaine += "t[" + i + "] : ";
				}

				chaine += "*";

				if (j == this.listeHachage[i].longueur() - 1) {
					chaine += "\n";
				}
			}
		}
		return chaine;
	}
}