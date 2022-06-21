package body.measure;

import lejos.hardware.sensor.EV3ColorSensor;;

public class MeasureCourseHSV extends MeasureCourseHue {

	/** hsv（色相:Hue、彩度:Saturation、明度:Value）*/
	private float hue, saturation, value;

	public MeasureCourseHSV(EV3ColorSensor colorSensor) {
		super(colorSensor);
		//仮設定
		hue = saturation = value = -2.0f;
	}


	/**
	 * 色相を取得する
	 * @return hue 色相
	 */
	float getHueHSV() {
		return hue;
	}

	/**
	 * 彩度を取得する
	 * @return saturation 彩度
	 */
	float getSaturationHSV() {
		return saturation;
	}

	/**
	 * 明度を取得する
	 * @return value 輝度
	 */
	float getValueHSV() {
		return value;
	}

	/**
	 * RGBをHSVに変換する
	 */
	void convertRGBTo(float[] rgb) {
		// rgb（赤:Red、緑:Green、青:Blue）
		float r = rgb[0];
		float g = rgb[1];
		float b = rgb[2];

		// rgbの最大値
		float max;
		// rgbの最小値
		float min;

		//maxとminを設定
		if (r > g) {
			if (r > b) {
				max = r;
				if (g > b) {
					min = b;
				} else {
					min = g;
				}
			} else {
				max = b;
				min = g;
			}
		} else {
			if (g > b) {
				max = g;
				if (r > b) {
					min = b;
				} else {
					min = r;
				}
			} else {
				max = b;
				min = r;
			}
		}
		// rgbからhsvへ変換
		if (max == min) {
			hue = -1.0f;
			if(max == 0) {
				saturation = -1.0f;
			}
		} else {
			/* h設定部分 */
			if (max == r) {
				hue = (g - b) / (max - min) * 60.0f;
			} else if (max == g) {
				hue = (b - r) / (max - min) * 60.0f + 120.0f;
			} else {
				hue = (r - g) / (max - min) * 60.0f + 240.0f;
			}
			if (hue < 0.0f) {
				hue = hue + 360.0f;
			}else if (hue > 360.0f) {
				hue = hue - 360.0f;
			}

			/* s設定部分 */
			saturation = (max - min) / max;
		}
		value = max;
	}


	/**
	 * 色をHSVで判定する
	 * @return 色
	 */
	Color judgeColorHSV() {

		if(value >= getLimitWhite()) {
			return Color.White;
		}else if(value <= getLimitBlack()) {
			return Color.Black;
		}
		return judgeColorHue();
	}
}
