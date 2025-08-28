package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread{

    int a, b;
    private List<Integer> primes = new LinkedList<>();
    private volatile boolean paused = false;

    public PrimeFinderThread(int a, int b) {
        super();
        this.a = a;
        this.b = b;
    }

    public void run() {
        for (int i = a; i <= b; i++) {
            synchronized (this) {
                while (paused) {
                    try {
                        wait(); // se queda esperando hasta que lo despierten
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (isPrime(i)) {
                primes.add(i);
                System.out.println(i);
            }
        }
    }

    boolean isPrime(int n) {
        if (n < 2) return false;
        if (n % 2 == 0 && n != 2) return false;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public List<Integer> getPrimes() {
        return primes;
    }

    // Métodos para pausar y reanudar
    public void pauseThread() {
        paused = true;
    }

    public synchronized void resumeThread() {
        paused = false;
        notifyAll();
    }
}
