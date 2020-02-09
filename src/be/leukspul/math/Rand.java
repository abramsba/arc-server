package be.leukspul.math;

import java.util.Random;

public class Rand {
    public static Random random = new Random();

    public static int getInt() {
        return getInt(0, Integer.MAX_VALUE);
    }

    public static int getInt(int max) {
        return getInt(0, max);
    }

    public static int getInt(double max) {
        return getInt(0, (int)max);
    }

    public static int getInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static double getDouble(double limit) {
        return random.nextDouble() * limit;
    }

    public static double getDouble() {
        return getDouble(1.0);
    }

    public static boolean getBool() {
        return getDouble() < 0.5;
    }

    public static boolean getBool(double chance) {
        return getDouble() < chance;
    }

}
