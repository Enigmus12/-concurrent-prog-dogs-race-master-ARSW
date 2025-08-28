package arsw.threads;

/**
 * Monitor para pausar y reanudar los galgos.
 * 
 * @author Juan David Rodríguez
 * 
 */

public class PausarMonitor {

    private boolean paused = false;

    // Llamado por los galgos en cada iteración
    public synchronized void esperarSiPausado() throws InterruptedException {
        while (paused) {
            wait(); // se duerme hasta que alguien lo despierte (pausado)
        }
    }

    // Llamado cuando el usuario presiona Stop 
    public synchronized void pausar() {
        paused = true;
    }

    // Llamado cuando el usuario presiona Continue
    public synchronized void reanudar() {
        paused = false;
        notifyAll(); // despierta a todos los galgos
    }
}
