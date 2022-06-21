package body.measure;

import lejos.hardware.sensor.EV3ColorSensor;

public class MeasureCourseHSL extends MeasureCourseHue {
	/** hsv（色相:Hue、彩度:Saturation、明度:lightness）*/
	private float hue, saturation, lightness;

	public MeasureCourseHSL(EV3ColorSensor colorsensor) {
		super(colorsensor);
		//仮設定
		super.setHue(-4.0f);
		saturation = lightness =  -4.0f;
	}

	/**
	 * HSLクラスの色相を取得する
	 * @return
	 */
	public float getHueHSL() {
		return hue;
	}

	/**
	 * HSLクラスの彩度を取得する
	 * @return
	 */
	public float getSatuationHSL() {
		return saturation;
	}

	/**
	 * HSLクラスの明度を取得する
	 * @return
	 */
	public float getLightnessHSL() {
		return lightness;
	}

	/**
	 * RGBからHSLに変換する
	 * HSLを設定する
	 * @param rgb
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

		//収束値
		float cnt;

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
				hue+= 360.0f;
			} else if (hue > 360.0f) {
				hue -= 360.0f;
			}
		}

		/* s設定部分*/
		cnt = (max + min) / 2.0f;
		if (cnt >= 255.0f / 2.0f) {//１２８　１２７となっているがfloatでどうなるか
			saturation = (cnt - min) / cnt;
		}else {
			saturation = (max - cnt) / (255 - cnt);
		}
		/*l設定部分*/
		lightness = (max + min) / 2;
	}

	/**
	 * 色をHSLで判定する
	 * @return　色
	 */
	Color judgeColorHSL() {
		if (lightness >= getLimitWhite()) {
			return Color.White;
		} else if (lightness <= getLimitBlack()) {
			return Color.Black;
		}
		return judgeColorHue();
	}
}
