package arsw.threads;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Programa principal del canódromo.
 * 
 * @author Juan David Rodríguez
 * 
 */

public class MainCanodromo {

    private static Galgo[] galgos;
    private static Canodromo can;
    private static RegistroLlegada reg = new RegistroLlegada();
    private static PausarMonitor monitor = new PausarMonitor(); 

    public static void main(String[] args) {
        can = new Canodromo(17, 100);
        galgos = new Galgo[can.getNumCarriles()];
        can.setVisible(true);

        // Start
        can.setStartAction(
            new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    ((JButton) e.getSource()).setEnabled(false);
                    new Thread() {
                        public void run() {
                            for (int i = 0; i < can.getNumCarriles(); i++) {
                                galgos[i] = new Galgo(can.getCarril(i), "" + i, reg, monitor);
                                galgos[i].start();
                            }

                            // Esperar a que todos terminen
                            for (int i = 0; i < can.getNumCarriles(); i++) {
                                try {
                                    galgos[i].join();
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                }
                            }

                            can.winnerDialog(reg.getGanador(), reg.getUltimaPosicionAlcanzada() - 1);
                            System.out.println("El ganador fue:" + reg.getGanador());
                        }
                    }.start();
                }
            }
        );

        // Stop
        can.setStopAction(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    monitor.pausar(); // ✅ todos los galgos se pausarán
                    System.out.println("Carrera pausada!");
                }
            }
        );

        // Continue
        can.setContinueAction(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    monitor.reanudar(); // ✅ todos los galgos continúan
                    System.out.println("Carrera reanudada!");
                }
            }
        );
    }
}

