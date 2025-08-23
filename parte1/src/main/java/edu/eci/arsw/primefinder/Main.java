package edu.eci.arsw.primefinder;

public class Main {

    public static void main(String[] args) {
        int totalDatos = 100_000_000;
        int numHilos = 100;

        crearHilos(totalDatos, numHilos);
    }

    public static void crearHilos(int totalDatos, int numHilos) {
        int chunkSize = totalDatos / numHilos;
        Thread[] hilos = new Thread[numHilos];

        for (int i = 0; i < numHilos; i++) {
            int inicio = i * chunkSize;
            int fin = (i == numHilos - 1) ? totalDatos : inicio + chunkSize;

            hilos[i] = new PrimeFinderThread(inicio, fin);
            hilos[i].start();
        }

        for (int i = 0; i < numHilos; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Todos los hilos han terminado.");
    }
}


