package lando.systems.ld29.util;

public class Utils {

    public static float clamp(float value, float min, float max)
    {
        return Math.max(Math.min(value, max), min);
    }

}
