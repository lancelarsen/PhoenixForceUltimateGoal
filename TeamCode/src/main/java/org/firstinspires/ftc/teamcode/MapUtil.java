package org.firstinspires.ftc.teamcode;

public class MapUtil {
    public static double map(double oldValue, double oldMin, double oldMax, double newMin, double newMax) {
        return (((oldValue - oldMin) * (newMax - newMin)) / (oldMax - oldMin)) + newMin;
    }
}
