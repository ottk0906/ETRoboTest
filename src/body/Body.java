package body;

import body.control.Control;
import body.control.ControlWheel;
import body.measure.Measure;
import body.measure.MeasureCourse;
import body.measure.MeasureTouch;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;

/**
 * 走行体クラス
 *
 */
public final class Body {
    /** 計測 */
    public static final Measure measure;
    /** 制御 */
    public static final Control control;

    /** 車輪の半径(mm) */
    public static final float RADIUS = 50.0f;
    /** 車輪の直径(mm) */
    public static final float DIAMETER = 2.0f * RADIUS;
    /** 左右動輪の間隔(mm) */
    public static final float TREAD = 147.0f;

    static {

        // ハードウェアの初期化
		EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S2);
		/*KamogawaRegulatedMotor leftMotor = new KamogawaRegulatedMotor(MotorPort.C);*/
		EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.C);
		/*KamogawaRegulatedMotor rightMotor = new KamogawaRegulatedMotor(MotorPort.B);*/
		EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

        // 計測の初期化
		MeasureTouch measureTouch = new MeasureTouch(touchSensor);
        MeasureCourse measureCourse = new MeasureCourse(colorSensor);
		/*MeasureWheel measureWheel = new MeasureWheel(leftMotor, rightMotor);*/
		/*measure = new Measure(measureTouch, measureCourse, measureWheel);*/
        measure = new Measure(measureTouch, measureCourse);
        // 制御の初期化
		ControlWheel controlWheel = new ControlWheel(leftMotor, rightMotor);
		control = new Control(controlWheel);

    }

    /**
     * コンストラクタ
     */
    private Body(){

    }

	/*public float getRadius() {
		return RADIUS;
	}

	public float getDiameter() {
		return DIAMETER;
	}

	public static float getTread() {
		return TREAD;
	}*/
}
