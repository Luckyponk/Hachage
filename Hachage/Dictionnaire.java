import java.math.BigInteger;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class Dictionnaire {
    private HTNaive dico;

    /*
     * Pré-requis : m>0
     * Donnée : un nombre entier m
     * Action : initialise un objet de la classe Dictionnaire possédant m tables
     * sous-jacentes
     */
    public Dictionnaire(int m) {
        this.dico = new HTNaive(m);
    }

    /*
     * Pré-requis : m>0 et fileName doit correspondre à un fichier possédant du
     * texte
     * Donnée : un nombre entier m et une chaîne de caractère fileName
     * Action : initialise un objet de la classe Dictionnaire possédant m tables
     * sous-jacentes
     */
    public Dictionnaire(String fileName, int m) {
        this.dico = new HTNaive(calculeListeInt(fileName), m);
    }

    /*
     * Pré-requis : fileName doit correspondre à un fichier possédant du texte
     * Donnée : un double f et une chaîne de caractères fileName
     * Action : initialise un objet de la classe Dictionnaire possédant f*|l| tables
     * sous-jacentes
     */
    public Dictionnaire(String fileName, double f) {
        this.dico = new HTNaive(calculeListeInt(fileName), f);
    }

    /*
     * Donnée : une chaîne de caractère s
     * Résultat : Retourne un BigInteger qui correspond à la chaîne de caractère s
     * après le calcul : (. . . ((x(k)256 + x(k−1))256 + x(k−2)) . . . )256 + x(0)
     * (avec x correspondant au code ASCII des caractères)
     */
    // Façon pas optimisée
    public static BigInteger stringToBigIntegerV1(String s) {
        BigInteger chiffre = BigInteger.valueOf(0);
        BigInteger puissance;
        for (int k = 0; k < s.length(); k++) {
            puissance = (BigInteger.valueOf(256).pow(k)).multiply(BigInteger.valueOf(s.charAt(k)));
            chiffre = chiffre.add(puissance);
        }

        return chiffre;
    }

    /*
     * Donnée : une chaîne de caractère s
     * Résultat : Retourne un BigInteger qui correspond à la chaîne de caractère s
     * après le calcul : (. . . ((x(k)256 + x(k−1))256 + x(k−2)) . . . )256 + x(0)
     * (avec x correspondant au code ASCII des caractères)
     */
    // Façon optimisée
    public static BigInteger stringToBigInteger(String s) {
        BigInteger chiffre = BigInteger.valueOf(s.charAt(s.length() - 1));
        for (int k = s.length() - 2; k > -1; k--) {
            chiffre = chiffre.multiply(BigInteger.valueOf(256));
            chiffre = chiffre.add(BigInteger.valueOf((int) s.charAt(k)));
        }
        return chiffre;
    }

    /*
     * Donnée : une chaîne de caractère s
     * Résultat : retourne un booléen, true si le mot a pu être ajouté au
     * dictionnaire et false s'il était déjà dedans
     */
    public boolean ajout(String s) {
        return this.dico.ajout(stringToBigInteger(s));
    }

    /*
     * Donnée : une chaîne de caractère s
     * Résultat : retourne un booléen, true si le mot est présent dans le
     * dictionnaire et false sinon
     */
    public boolean contient(String s) {
        return this.dico.contient(stringToBigInteger(s));
    }

    /*
     * Résultat : retourne un entier correspondant au nombre de mots présent dans le
     * dictionnaire
     */
    public int getCardinal() {
        return this.dico.getCardinal();
    }

    /*
     * Résultat : retourne un entier correspondant au nombre d'élément de la
     * ListeBigI de la table de hachage la plus grande
     */
    public int getMaxSize() {
        return this.dico.getMaxSize();
    }

    /*
     * Résultat : retourne un entier correspondant au nombre de ListeBigI présente
     * dans la table de hachage
     */
    public int getNbListes() {
        return this.dico.getNbListes();
    }

    /*
     * Résultat: retourne l'attribut totalTimeh qui correspond au temps passé à
     * effectuer le calcul la fonction h
     */
    public long getTotalTimeh() {
        return this.dico.getTotalTimeh();
    }

    /*
     * Résultat: retourne l'attribut totalTimeh qui correspond au temps passé à
     * à effectuer des appels à la méthode contient de la classe ListeBigI
     */
    public long getTotalTimeContient() {
        return this.dico.getTotalTimeContient();
    }

    public String toString() {
        return this.dico.toString();
    }

    public String toStringV2() {
        return this.dico.toStringV2();
    }

    /*
     * Pré-requis : filename doit correspondre à un fichier existant
     * Donnée : une chaîne de caractère filename
     * Résultat : retourne un nombre entier correspondant au nombre de mots présents
     * dans le fichier portant le nom filename
     */
    public static int lectureMot(String filename) {
        File f = new File(filename);
        Scanner sc;
        try {
            sc = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println(("problème d’accès au fichier " + e.getMessage()));
            return 0;
        }
        int nbmots = 0;
        while (sc.hasNext()) {
            sc.next();
            nbmots++;
        }
        sc.close();

        return nbmots;
    }

    /*
     * Pré-requis : filename doit correspondre à un fichier existant
     * Donnée : une chaîne de caractère filename
     * Résultat : retourne un tableau de chaîne de caractère qui contient tous les
     * mots présents dans le fichier possédant le nom filename
     */
    public static String[] lectureMotsTexte(String filename) {

        File f = new File(filename);
        Scanner sc;
        try {
            sc = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println(("problème d’accès au fichier " + e.getMessage()));
            return null;
        }
        int nbmots = lectureMot(filename);
        String[] mot = new String[nbmots];
        for (int i = 0; i < nbmots; i++) {
            mot[i] = new String(sc.next());
        }
        sc.close();

        return mot;
    }

    /*
     * Pré-requis : filename doit correspondre à un fichier existant
     * Donnée : une chaîne de caractère filename
     * Résultat : retourne une ListeBigI contenant tous les mots transformés en
     * BigInteger présents dans le fichier filename
     */
    public static ListeBigI calculeListeInt(String fileName) {
        String[] mot = lectureMotsTexte(fileName);
        BigInteger[] IntegerMot = new BigInteger[mot.length];
        for (int i = 0; i < mot.length; i++) {
            IntegerMot[i] = BigInteger.valueOf(0);
            IntegerMot[i] = IntegerMot[i].add(stringToBigInteger(mot[i]));

        }
        ListeBigI ListeIntegerMot = new ListeBigI(IntegerMot);
        return ListeIntegerMot;
    }

    // Nous avons légèrement modifié la structure du main proposé dans le sujet,
    // nous ne savons s'il est à rendre ou non et le laissons donc sous forme de
    // commentaire.

    public static void main(String[] args) {
        Dictionnaire d = new Dictionnaire("randomWordsPetit", 0.01);

        System.out.println("maxSize : " + d.getMaxSize());
        System.out.println("cardinal : " + d.getCardinal());
        System.out.println("nbListes : " + d.getNbListes());

        int nbRecherches = 100000;
        Random random = new Random();
        String motS = "";
        int tailleMot = 0;
        long deb = System.currentTimeMillis();
        for (int i = 0; i < nbRecherches; i++) {
            tailleMot = random.nextInt(15) + 2; // 2 <= tailleMot <= 16
            char[] mot = new char[tailleMot];
            for (int j = 0; j < mot.length; j++) {
                mot[j] = (char) ('a' + random.nextInt(26));
            }
            motS = mot.toString();
            d.contient(motS);// on ne récupère même pas le résultat de la recherche!
        }
        long fin = System.currentTimeMillis();
        System.out.println("temps total : " + (fin - deb));
    }
    /*
     * System.out.println("temps total : " + (fin - deb));
     * System.out.println("temps passé dans h : " + d.getTotalTimeh());
     * System.out.println("temps passé dans contient : " +
     * d.getTotalTimeContient());
     * }
     */
}