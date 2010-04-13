//
//  Cromosoma.java
//
//  Salvador Garc�a L�pez
//
//  Created by Salvador Garc�a L�pez 19-7-2004.
//  Copyright (c) 2004 __MyCompanyName__. All rights reserved.
//

package keel.Algorithms.Preprocess.Instance_Selection.GGA;

import keel.Algorithms.Preprocess.Basic.*;

import org.core.*;

public class Cromosoma implements Comparable {

  /*Cromosome data structure*/
  boolean cuerpo[];

  /*Useless data for cromosomes*/
  double calidad;
  boolean cruzado;
  boolean valido;
  double errorRate;

  /*Construct a random cromosome of specified size(OK)*/
  public Cromosoma (int size) {

    double u;
    int i;

    cuerpo = new boolean[size];
    for (i=0; i<size; i++) {
      u = Randomize.Rand();
      if (u < 0.5) {
        cuerpo[i] = false;
      } else {
        cuerpo[i] = true;
      }
    }
    cruzado = true;
    valido = true;
  }

  /*Create a copied cromosome (OK)*/
  public Cromosoma (int size, Cromosoma a) {
    int i;

    cuerpo = new boolean[size];
    for (i=0; i<cuerpo.length; i++)
      cuerpo[i] = a.getGen(i);
    calidad = a.getCalidad();
    cruzado = false;
    valido = true;
  }

  /*Cronstruct a cromosome from a bit array (OK)*/
  public Cromosoma (boolean datos[]) {
    int i;

    cuerpo = new boolean[datos.length];
    for (i=0; i<datos.length; i++)
      cuerpo[i] = datos[i];
    cruzado = true;
    valido = true;
  }

  /*OK*/
  public boolean getGen (int indice) {
    return cuerpo[indice];
  }

  /*OK*/
  public double getCalidad () {
    return calidad;
  }

  /*OK*/
  public void setGen (int indice, boolean valor) {
    cuerpo[indice] = valor;
  }

  /*Function that evaluates a cromosome (OK)*/
  public void evalua (double datos[][], double real[][], int nominal[][], boolean nulos[][], int clases[], double alfa, int kNeigh, int nClases, boolean distanceEu) {

    int i, j, l, m;
    int aciertos = 0;
    double M, s;
    double conjS[][];
    double conjR[][];
    int conjN[][];
    boolean conjM[][];
    int clasesS[];
    int vecinos[];
    int claseObt;
    int vecinoCercano;
    double dist, minDist;
    
    M = (double)datos.length;
    s = (double)genesActivos();
    
    if (kNeigh > 1) {    
    	vecinos = new int[kNeigh];    
		conjS = new double[(int)s][datos[0].length];
		conjR = new double[(int)s][datos[0].length];
		conjN = new int[(int)s][datos[0].length];
		conjM = new boolean[(int)s][datos[0].length];
		clasesS = new int[(int)s];
		for (j=0, l=0; j<datos.length; j++) {
			if (cuerpo[j]) { //the instance must be copied to the solution
				for (m=0; m<datos[j].length; m++) {
					conjS[l][m] = datos[j][m];
					conjR[l][m] = real[j][m];
					conjN[l][m] = nominal[j][m];
					conjM[l][m] = nulos[j][m];
				}
				clasesS[l] = clases[j];
				l++;
			}
		}    	
    
    	for (i=0; i<datos.length; i++) {
    		claseObt = KNN.evaluacionKNN2(kNeigh, conjS, conjR, conjN, conjM, clasesS, datos[i], real[i], nominal[i], nulos[i], nClases, distanceEu, vecinos);
    		if (claseObt >= 0)
    			if (clases[i] == claseObt)
    				aciertos++;
    	}    
    } else {
        for (i=0; i<datos.length; i++) {
            vecinoCercano = -1;
            minDist = Double.POSITIVE_INFINITY;
            for (j=0; j<datos.length; j++) {
              if (cuerpo[j]) { //It is in S
                dist = KNN.distancia (datos[i], real[i], nominal[i], nulos[i], datos[j], real[j], nominal[j], nulos[j], distanceEu);
                if (dist < minDist && dist != 0) {
                  minDist = dist;
                  vecinoCercano = j;
                }
              }
            }
            if (vecinoCercano >= 0)
              if (clases[i] == clases[vecinoCercano])
                aciertos++;
          }    	
    }

    calidad = ((double)(aciertos)/M)*alfa*100.0;
    calidad += ((1.0 - alfa) * 100.0 * (M - s) / M);
    cruzado = false;
}

  /*Function that does the mutation (OK)*/
  public void mutacion (double pMutacion1to0, double pMutacion0to1) {

    int i;

    for (i=0; i<cuerpo.length; i++) {
      if (cuerpo[i]) {
        if (Randomize.Rand() < pMutacion1to0) {
          cuerpo[i] = false;
          cruzado = true;
        }
      } else {
        if (Randomize.Rand() < pMutacion0to1) {
          cuerpo[i] = true;
          cruzado = true;
        }
      }
    }
  }

  /*Function that does the CHC diverge*/
  public void divergeCHC (double r, Cromosoma mejor, double prob) {
	  
    int i;

    for (i=0; i<cuerpo.length; i++) {
      if (Randomize.Rand() < r) {
        if (Randomize.Rand() < prob) {
          cuerpo[i] = true;
        } else {
          cuerpo[i] = false;
        }
      } else {
        cuerpo[i] = mejor.getGen(i);
      }
    }
    cruzado = true;
  }

  /*OK*/
  public boolean estaEvaluado () {
    return !cruzado;
  }

  /*OK*/
  public int genesActivos () {
    int i, suma = 0;

    for (i=0; i<cuerpo.length; i++) {
      if (cuerpo[i]) suma++;
    }

    return suma;
  }

  /*OK*/
  public boolean esValido () {
    return valido;
  }

  /*OK*/
  public void borrar () {
    valido = false;
  }

  /*Function that lets compare cromosomes to sort easily (OK)*/
  public int compareTo (Object o1) {
    if (this.calidad > ((Cromosoma)o1).calidad)
      return -1;
    else if (this.calidad < ((Cromosoma)o1).calidad)
      return 1;
    else return 0;
  }

  /*Function that inform about if a cromosome is different only in a bit, obtain the
   position of this bit. In case of have more differences, it returns -1 (OK)*/
  public int differenceAtOne (Cromosoma a) {

    int i;
    int cont = 0, pos = -1;

    for (i=0; i<cuerpo.length && cont < 2; i++)
      if (cuerpo[i] != a.getGen(i)) {
        pos = i;
        cont++;
      }

    if (cont >= 2)
      return -1;
    else return pos;
  }

  /*OK*/
  public String toString() {
	  
    int i;

    String temp = "[";

    for (i=0; i<cuerpo.length; i++)
      if (cuerpo[i])
        temp += "1";
      else
        temp += "0";
    temp += ", " + String.valueOf(calidad) + "," + String.valueOf(errorRate) + ", " + String.valueOf(genesActivos()) + "]";

    return temp;
  }
}