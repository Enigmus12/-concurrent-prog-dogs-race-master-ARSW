package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Scanner sc = new Scanner(System.in);

        System.out.print(" Ingresa el número máximo a evaluar: ");
        int maxNumber = sc.nextInt();

        System.out.print(" Ingresa la cantidad de hilos a usar: ");
        int numThreads = sc.nextInt();

        // Crear lista dinámica de hilos
        List<PrimeFinderThread> threads = new ArrayList<>();

        // Dividir el rango en partes iguales para cada hilo
        int rangePerThread = maxNumber / numThreads;
        for (int i = 0; i < numThreads; i++) {
            int start = i * rangePerThread;
            int end = (i == numThreads - 1) ? maxNumber : (i + 1) * rangePerThread;
            PrimeFinderThread pft = new PrimeFinderThread(start, end);
            threads.add(pft);
            pft.start();
        }

        // Esperar 5 segundos
        Thread.sleep(5000);

        // Pausar todos los hilos
        for (PrimeFinderThread t : threads) {
            t.pauseThread();
        }

        System.out.println("⏸️ Se pausó la ejecución tras 5 segundos.");
        System.out.println("Hasta ahora se han encontrado:");
        for (int i = 0; i < threads.size(); i++) {
            System.out.println("Hilo " + (i + 1) + ": " + threads.get(i).getPrimes().size() + " primos");
        }

        // Esperar ENTER para reanudar
        System.out.println("\nPresiona ENTER para reanudar...");
        sc.nextLine(); // consumir salto pendiente
        sc.nextLine(); // esperar ENTER

        // Reanudar todos los hilos
        for (PrimeFinderThread t : threads) {
            t.resumeThread();
        }

        // Esperar a que terminen
        for (PrimeFinderThread t : threads) {
            t.join();
        }

        System.out.println("✅ Ejecución finalizada.");
        System.out.println("Total primos encontrados:");
        for (int i = 0; i < threads.size(); i++) {
            System.out.println("Hilo " + (i + 1) + ": " + threads.get(i).getPrimes().size());
        }
    }
}
