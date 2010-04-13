//
//  CNN.java
//
//  Salvador Garc�a L�pez
//
//  Created by Salvador Garc�a L�pez 10-7-2004.
//  Copyright (c) 2004 __MyCompanyName__. All rights reserved.
//

package keel.Algorithms.Preprocess.Instance_Selection.CNN;

import keel.Algorithms.Preprocess.Basic.*;
import org.core.*;

import java.util.StringTokenizer;
import java.util.Arrays;

public class CNN extends Metodo {

  /*Own parameters of the algorithm*/
  private long semilla;
  private int k;

  public CNN (String ficheroScript) {
    super (ficheroScript);
  }

  public void ejecutar () {

    double conjS[][];
    double conjR[][];
    int conjN[][];
    boolean conjM[][];
    int clasesS[];
	  
    int S[];
    int i, j, l;
    int nClases;
    int pos;
    int baraje[];
    int tmp;
    int tamS;
    int claseObt;
    int cont;
    int busq;
    boolean continuar;

    long tiempo = System.currentTimeMillis();

    /*Inicialization of the candidates set*/
    S = new int[datosTrain.length];
    for (i=0; i<S.length; i++)
      S[i] = Integer.MAX_VALUE;

    /*Getting the number of different classes*/
    nClases = 0;
    for (i=0; i<clasesTrain.length; i++)
      if (clasesTrain[i] > nClases)
        nClases = clasesTrain[i];
    nClases++;
    tamS = 0;

    if (nClases < 2) {
      System.err.println("Input dataset is empty");
      nClases = 0;
    }

    /*Inserting an element of each class*/
    Randomize.setSeed (semilla);
    for (i=0; i<nClases; i++) {
      pos = Randomize.Randint (0, clasesTrain.length-1);
      cont = 0;
      while (clasesTrain[pos] != i && cont < clasesTrain.length) {
        pos = (pos + 1) % clasesTrain.length;
        cont++;
      }
      if (cont < clasesTrain.length) {
        S[tamS] = pos;
        tamS++;
      }
    }

    /*Algorithm body. We resort randomly the instances of T and compare with the rest of S.
     If an instance doesn�t classified correctly, it is inserted in S*/
    do {
      continuar = false;
      baraje = new int[datosTrain.length];
      for (i=0; i<datosTrain.length; i++)
        baraje[i] = i;
      for (i=0; i<datosTrain.length; i++) {
        pos = Randomize.Randint (i, clasesTrain.length-1);
        tmp = baraje[i];
        baraje[i] = baraje[pos];
        baraje[pos] = tmp;
      }

      for (i=0; i<datosTrain.length; i++) {
        /*Construction of the S set from the previous vector S*/
        conjS = new double[tamS][datosTrain[0].length];
        conjR = new double[tamS][datosTrain[0].length];
        conjN = new int[tamS][datosTrain[0].length];
        conjM = new boolean[tamS][datosTrain[0].length];
        clasesS = new int[tamS];
        for (j = 0; j < tamS; j++) {
          for (l = 0; l < datosTrain[0].length; l++) {
            conjS[j][l] = datosTrain[S[j]][l];
            conjR[j][l] = realTrain[S[j]][l];
            conjN[j][l] = nominalTrain[S[j]][l];
            conjM[j][l] = nulosTrain[S[j]][l];
          }
          clasesS[j] = clasesTrain[S[j]];
        }
        Arrays.sort(S);
        busq = Arrays.binarySearch(S, baraje[i]);
        if (busq < 0) {
          /*Do KNN to the instance*/
          claseObt = KNN.evaluacionKNN(k, conjS, conjR, conjN, conjM, clasesS, datosTrain[baraje[i]], realTrain[baraje[i]], nominalTrain[baraje[i]], nulosTrain[baraje[i]], nClases, distanceEu);
          if (claseObt != clasesTrain[baraje[i]]) { //fail in the class, it is included in S
            continuar = true;
            S[tamS] = baraje[i];
            tamS++;
          }
        }
      }
    } while (continuar == true);

    /*Construction of the S set from the previous vector S*/
    conjS = new double[tamS][datosTrain[0].length];
    conjR = new double[tamS][datosTrain[0].length];
    conjN = new int[tamS][datosTrain[0].length];
    conjM = new boolean[tamS][datosTrain[0].length];
    clasesS = new int[tamS];
    for (j=0; j<tamS; j++) {
      for (l=0; l<datosTrain[0].length; l++) {
        conjS[j][l] = datosTrain[S[j]][l];
        conjR[j][l] = realTrain[S[j]][l];
        conjN[j][l] = nominalTrain[S[j]][l];
        conjM[j][l] = nulosTrain[S[j]][l];
      }
      clasesS[j] = clasesTrain[S[j]];
    }

    System.out.println("CNN "+ relation + " " + (double)(System.currentTimeMillis()-tiempo)/1000.0 + "s");

    OutputIS.escribeSalida(ficheroSalida[0], conjR, conjN, conjM, clasesS, entradas, salida, nEntradas, relation);
    OutputIS.escribeSalida(ficheroSalida[1], test, entradas, salida, nEntradas, relation);
  }	

  public void leerConfiguracion (String ficheroScript) {

    String fichero, linea, token;
    StringTokenizer lineasFichero, tokens;
    byte line[];
    int i, j;
    
    ficheroSalida = new String[2];

    fichero = Fichero.leeFichero (ficheroScript);
    lineasFichero = new StringTokenizer (fichero,"\n\r");

    lineasFichero.nextToken();
    linea = lineasFichero.nextToken();

    tokens = new StringTokenizer (linea, "=");
    tokens.nextToken();
    token = tokens.nextToken();

    /*Getting the names of the training and test files*/
    line = token.getBytes();
    for (i=0; line[i]!='\"'; i++);
    i++;
    for (j=i; line[j]!='\"'; j++);
    ficheroTraining = new String (line,i,j-i);
    for (i=j+1; line[i]!='\"'; i++);
    i++;
    for (j=i; line[j]!='\"'; j++);
    ficheroTest = new String (line,i,j-i);

    /*Getting the path and base name of the results files*/
    linea = lineasFichero.nextToken();
    tokens = new StringTokenizer (linea, "=");
    tokens.nextToken();
    token = tokens.nextToken();

    /*Getting the names of output files*/
    line = token.getBytes();
    for (i=0; line[i]!='\"'; i++);
    i++;
    for (j=i; line[j]!='\"'; j++);
    ficheroSalida[0] = new String (line,i,j-i);
    for (i=j+1; line[i]!='\"'; i++);
    i++;
    for (j=i; line[j]!='\"'; j++);
    ficheroSalida[1] = new String (line,i,j-i);
    
     /*Getting the seed*/
     linea = lineasFichero.nextToken();
     tokens = new StringTokenizer (linea, "=");
     tokens.nextToken();
     semilla = Long.parseLong(tokens.nextToken().substring(1));

     /*Getting the number of neighbors*/
     linea = lineasFichero.nextToken();
     tokens = new StringTokenizer (linea, "=");
     tokens.nextToken();
     k = Integer.parseInt(tokens.nextToken().substring(1));
  
     /*Getting the type of distance function*/
     linea = lineasFichero.nextToken();
     tokens = new StringTokenizer (linea, "=");
     tokens.nextToken();
     distanceEu = tokens.nextToken().substring(1).equalsIgnoreCase("Euclidean")?true:false;    
  }
}
