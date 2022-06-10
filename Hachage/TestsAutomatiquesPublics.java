//placer ce fichier dans le meme dossier que les *.java fournis par les étudiants, et lancer le main de ce fichier.

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.*;

public class TestsAutomatiquesPublics {
 
    public static void main(String[] args) {

	double note = 0;
	//ici on lance les tests, si l'appels aux méthodes concernées déclenche une exception ou fait une boucle infinie, pas de pb, on passera qd même au suivant
        note +=runTest(TestsAutomatiquesPublics::testQ4, "testQ4",2);//le dernier paramètre est le barême du test
        note +=runTest(TestsAutomatiquesPublics::testQ6, "testQ6",2);
	note +=runTest(TestsAutomatiquesPublics::testQ78_1, "testQ78_1",1);
     
	System.out.println("fin des tests : note = " + note);
       
    }


    public static double runTest(Callable<Double> r, String s, int bareme){
	ExecutorService executorService = Executors.newSingleThreadExecutor(); //si on submit à nouveau sans re créer ça timeout aussi pour tests suivants
	Future<Double> future = executorService.submit(r);
	double note = 0;
	 try {
	     
	     note+= future.get(1L, TimeUnit.SECONDS)*bareme; //get renvoie entre 0 et 1
	     System.out.println("****************************************************");
	     System.out.println(s + " terminé, note: " + note + "/" + bareme);
	     System.out.println("****************************************************");

	 } catch (TimeoutException e) {
	     System.out.println("****************************************************");
	     System.out.println(s + " timeout");
	     System.out.println("****************************************************");
	 } catch (InterruptedException | ExecutionException e) {
	     System.out.println("****************************************************");
	     System.out.println(s + " erreur " + e.getMessage());
	     System.out.println("****************************************************");
        }
	 executorService.shutdownNow();
	 return note;
    }

    
    private static double testQ4() {

	HTNaive ht = new HTNaive(2);
	if ((ht.getListe(0).longueur() == 0) && (ht.getListe(1).longueur() == 0)) {
	    return 1;
	} else {
	    return 0;
	}
    }

	
    private static double testQ6() {
	
	HTNaive ht = new HTNaive(2);
	BigInteger u = BigInteger.valueOf(5);
	int res = ht.h(u);
	if(res==1){
	    return 1;
	}
	else{
	    return 0;
	}
    }

    private static double testQ78_1(){//test ajout ok
	HTNaive ht = new HTNaive(3);
	BigInteger u1 = BigInteger.valueOf(11);
	int i = ht.h(u1);
	boolean ok = ht.ajout(u1);
	double res = 0;
	if(ok) res+=0.5;
	if(ht.getListe(i).contient(u1) && !ht.getListe((i+1)%3).contient(u1) && !ht.getListe((i+2)%3).contient(u1)){//verif que u1 est bien
	    //dans la liste i, et pas ailleurs
	    res +=0.5;
	}
	return res;
    }





}
