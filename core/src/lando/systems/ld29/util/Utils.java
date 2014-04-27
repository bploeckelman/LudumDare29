package lando.systems.ld29.util;

public class Utils {

    public static float clamp(float value, float min, float max)
    {
        return Math.max(Math.min(value, max), min);
    }

    public static boolean isBetween(float value, float lower, float upper) {
        return (value >= lower && value <= upper);
    }

}
