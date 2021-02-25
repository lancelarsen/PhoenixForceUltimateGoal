package org.firstinspires.ftc.teamcode;

public class EncoderUtil {
    public enum Motor {
        GOBILDA_5202,
        REV_CORE_HEX
    }

    public static int inchesToTicks(Motor motor, double inches) {
        switch (motor) {
            case GOBILDA_5202:
                return (int) (inches * 26.0);
            case REV_CORE_HEX:
                return (int) (inches * 60.0);
            default:
                return (int) inches;
        }
    }
}
