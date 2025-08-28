package arsw.threads;

/**
 * Registro de llegadas de los galgos.
 * 
 * @author Juan David Rodríguez
 * 
 */

public class RegistroLlegada {

    private int ultimaPosicionAlcanzada = 1;
    private String ganador = null;

    // Método sincronizado: asegura que solo un hilo entre a la vez
    public synchronized int registrarLlegada(String galgoName) {
        int posicion = ultimaPosicionAlcanzada;

        if (posicion == 1) {
            ganador = galgoName; // el primero en llegar es el ganador
        }

        ultimaPosicionAlcanzada++;
        return posicion; // se devuelve la posición asignada a ese galgo
    }

    public synchronized String getGanador() {
        return ganador;
    }

    public synchronized int getUltimaPosicionAlcanzada() {
        return ultimaPosicionAlcanzada;
    }
}
