package be.leukspul.arc.manager;

public class GameTick implements Runnable {


    public GameTick(int tps) {
        Tps = tps;
        Fps = 0;
        SkipTicks = NANO / Tps;
    }

    public void tick() {
        input();
        update();
        draw();
    }

    public void input() {
    }

    public void update() {
    }

    public void draw() {
    }

    public int fps() {
        return Fps;
    }

    @Override
    public void run() {
        long LastTickTime = 0;
        long NextTickTime = System.nanoTime() + SkipTicks;
        long SecondTicks = System.nanoTime() + NANO;
        int ticks = 0;
        while (true) {
            long start = System.nanoTime();
            while (start > NextTickTime) {
                if (LastTickTime != 0) {
                    long delta = start - LastTickTime;
                    System.out.println(delta);
                }
                LastTickTime = start;
                tick();
                ticks++;
                if (System.nanoTime() > SecondTicks) {
                    Fps = ticks;
                    ticks = 0;
                    SecondTicks += NANO;
                }
                NextTickTime += SkipTicks;
            }
        }
    }

    private int Tps;
    private int Fps;
    private int SkipTicks;

    private static final int NANO = 1000000000;

    public static void main(String [] args) {
        GameTick t = new GameTick(5);
        t.run();
    }


}
