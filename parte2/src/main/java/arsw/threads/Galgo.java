package arsw.threads;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author Juan David Rodríguez
 * 
 */
public class Galgo extends Thread {
    private int paso;
    private Carril carril;
    private RegistroLlegada regl;
    private PausarMonitor monitor;  // monitor para pausar/reanudar

    public Galgo(Carril carril, String name, RegistroLlegada reg, PausarMonitor monitor) {
        super(name);
        this.carril = carril;
        paso = 0;
        this.regl = reg;
        this.monitor = monitor;
    }

    public void corra() throws InterruptedException {
        while (paso < carril.size()) {
            Thread.sleep(100);

            monitor.esperarSiPausado(); //  se detiene si está en pausa

            carril.setPasoOn(paso++);
            carril.displayPasos(paso);

            if (paso == carril.size()) {
                carril.finish();
                int ubicacion = regl.registrarLlegada(this.getName());
                System.out.println("El galgo " + this.getName() + " llegó en la posición " + ubicacion);
            } 
        }
    }

    @Override
    public void run() {
        try {
            corra();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

