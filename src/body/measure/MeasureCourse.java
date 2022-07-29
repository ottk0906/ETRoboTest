package body.measure;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

/**
 * 路面計測クラス
 *
 */
public class MeasureCourse {
	/** カラーセンサ */
	private EV3ColorSensor colorSensor;
	private SensorMode sensorMode;

	private MeasureCourseHSL measureCourseHSL;
	private MeasureCourseHSV measureCourseHSV;

	/** RGBの係数 */
	private float kr, kg, kb;
	private float lastRed, lastGreen, lastBlue;

	/** 白明度 */
	private float white;
	/** 白RGB(赤、緑、青) */
	private float whiteRGB[];
	/** 黒明度 */
	private float black;
	/** 黒RGB(赤、緑、青) */
	private float blackRGB[];
	/** 目標明度 */
	private float target;
	/** 路面RGB（赤、緑、青） */
	private float rgb[];
	/** 路面HSV（色相、彩度、明度） */
	private float hsv[];

	/**
	 * コンストラクタ
	 * @param colorSensor カラーセンサ
	 */
	public MeasureCourse(EV3ColorSensor colorSensor) {
		this.colorSensor = colorSensor;
		sensorMode = colorSensor.getRGBMode();
		rgb = new float[sensorMode.sampleSize()];
		hsv = new float[3];

		// 仮キャリブレーション
		target = 0.5f;
		kr = kg = kb = 1.0f;
		lastRed = lastGreen = lastBlue = 0.0f;
		whiteRGB = new float[3];
		blackRGB = new float[3];
	}

	/**
	 * 更新する
	 */
	public void update() {

		// RGBを取得する
		sensorMode.fetchSample(rgb, 0);

		//RGBに補正をかける
		ajustRGB();

		// RGBをHSVに変換する
		convertRGBtoHSV(rgb);

		//MeasureCourseHSV、MeasureCourseHSLが両方あるとき更新する
		if (isNotNullHSVHSL()) {
			measureCourseHSL.update();
			measureCourseHSV.update();
		}
	}

	/**
	 * RGBの数値に補正をかける
	 * その数値が1～0内なら今回の数値を、
	 * 1～0外なら前回の1～0内の数値を使用する
	 */
	public void ajustRGB() {
		//赤を1～0の数値に変換する
		float tmp = (rgb[0] - blackRGB[0]) * kr;
		if (tmp >= 0 && tmp <= 1) {
			rgb[0] = lastRed = tmp;
		} else {
			rgb[0] = lastRed;
		}
		//緑を1～0の数値に変換する
		tmp = (rgb[1] - blackRGB[1]) * kg;
		if (tmp >= 0 && tmp <= 1) {
			rgb[1] = lastGreen = tmp;
		} else {
			rgb[1] = lastGreen;
		}
		//青を1～0の数値に変換する
		tmp = (rgb[2] - blackRGB[2]) * kb;
		if (tmp >= 0 && tmp <= 1) {
			rgb[2] = lastBlue = tmp;
		} else {
			rgb[2] = lastBlue;
		}
	}

	/**
	 * measureCourseHSVを取得する
	 * @return
	 */
	public MeasureCourseHSV getMeasureCourseHSV() {
		return measureCourseHSV;
	}

	/**
	 * measureCourseHSLを取得する
	 * @return
	 */
	public MeasureCourseHSL getMeasureCourseHSL() {
		return measureCourseHSL;
	}

	/**
	 * 白明度を取得する
	 * @return　白明度
	 */
	public float getWhite() {
		return white;
	}

	/**
	 * 白明度を設定する
	 * @param white　白明度
	 */
	public void setWhite(float white) {
		this.white = white;
	}

	/**
	 * 白RGBを設定する
	 * @param red
	 * @param green
	 * @param blue
	 */
	public void setWhtieRGB(float[] maxRGB) {
		this.whiteRGB = maxRGB;
	}

	/**
	 * 黒明度を取得する
	 * @return　黒明度
	 */
	public float getBlack() {
		return black;
	}

	/**
	 * 黒明度を設定する
	 * @param black　黒明度
	 */
	public void setBlack(float black) {
		this.black = black;
	}

	/**
	 * 黒RGBを設定する
	 * @param red
	 * @param green
	 * @param blue
	 */
	public void setBlackRGB(float[] minRGB) {
		this.blackRGB = minRGB;
	}

	/**
	 * 目標明度を取得する
	 * @return　目標明度
	 */
	public float getTarget() {
		return target;
	}

	/**
	 * 目標明度を設定する
	 * @param target　目標明度
	 */
	public void setTarget(float target) {
		this.target = target;
	}

	/**
	 * 路面色相を取得する
	 * @return　路面色相
	 */
	public float getHue() {
		return hsv[0];
	}

	/**
	 * 路面彩度を取得する
	 * @return　路面彩度
	 */
	public float getSaturation() {
		return hsv[1];
	}

	/**
	 * 路面明度を取得する
	 * @return　路面明度
	 */
	public float getValue() {
		return hsv[2];
	}

	/**
	 * RGB値を取得する
	 * @return rgb (Red,Green,Blue)
	 */
	float[] getRGB() {
		return rgb;
	}

	/**
	 * RGBをHSVに変換する
	 */
	private void convertRGBtoHSV(float[] rgb) {
		// rgb（赤:Red、緑:Green、青:Blue）
		float r = rgb[0];
		float g = rgb[1];
		float b = rgb[2];

		// hsv（色相:Hue、彩度:Saturation、明度:Value）
		float h, s, v;

		// rgbの最大値
		float max = (r > g) ? r : g;
		if (b > max)
			max = b;

		// rgbの最小値
		float min = (r < g) ? r : g;
		if (b < min)
			min = b;

		// rgbからhsvへ変換
		if (max == min) {
			h = -1.0f; // 未定義
		} else {
			if (max == r) {
				h = (g - b) / (max - min) * 60.0f;
			} else if (max == g) {
				h = (b - r) / (max - min) * 60.0f + 120.0f;
			} else {
				h = (r - g) / (max - min) * 60.0f + 240.0f;
			}
			if (h < 0.0f)
				h = h + 360.0f;
			if (h > 360.0f)
				h = h - 360.0f;
		}
		if (max != 0.0f) {
			s = (max - min) / max;
		} else {
			s = -1.0f; // 未定義
		}
		v = max;

		hsv[0] = h;
		hsv[1] = s;
		hsv[2] = v;
	}

	public void setColorBorder(float borderRedToYellow, float borderYellowToGreen, float borderGreenToBlue,
			float borderBlueToRed) {
		measureCourseHSV = new MeasureCourseHSV(borderRedToYellow, borderYellowToGreen, borderGreenToBlue,
				borderBlueToRed);
		measureCourseHSL = new MeasureCourseHSL(borderRedToYellow, borderYellowToGreen, borderGreenToBlue,
				borderBlueToRed);
	}

	/**
	 * MeasureCourseHSV,MeasureCourseHSLクラスがMeasureCourseに存在しているか
	 * @param 両方あるならtrue, 片方でもないならfalse
	 */
	public boolean isNotNullHSVHSL() {
		return measureCourseHSV != null && measureCourseHSL != null;
	}

	/**
	 * RGBの係数をキャリブレーション結果から設定する
	 */
	public void setKRKGKB() {
		kr = 1.0f / (whiteRGB[0] - blackRGB[0]);
		kg = 1.0f / (whiteRGB[1] - blackRGB[1]);
		kb = 1.0f / (whiteRGB[2] - blackRGB[2]);
	}
}
