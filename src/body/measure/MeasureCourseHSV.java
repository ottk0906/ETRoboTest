package body.measure;

import body.Body;

public class MeasureCourseHSV extends MeasureCourseHue {
	/** sv（彩度:Saturation、明度:Value）*/
	private float saturation, value;

	/**
	 * コンストラクタ
	 */
	public MeasureCourseHSV() {
		//仮設定
		setHue(-2.0f);
		saturation = value = -2.0f;
	}

	/**
	 * 彩度を取得する
	 * @return saturation 彩度
	 */
	float getSaturation() {
		return saturation;
	}

	/**
	 * 明度を取得する
	 * @return value 輝度
	 */
	float getValue() {
		return value;
	}

	public void update() {
		convertRGBTo(Body.measure.getRGB());
		judgeColor();
	}

	/**
	 * 色判定結果を設定する
	 */
	public void judgeColor() {
		setColor(judgeColor.judgeColorHSV(getHue(), saturation, value));
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

		//色相設定用変数
		float hue;

		// rgbからhsvへ変換
		if (max == min) {
			hue = -1.0f;
			if (max == 0) {
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
			} else if (hue > 360.0f) {
				hue = hue - 360.0f;
			}
			setHue(hue);

			/* s設定部分 */
			saturation = (max - min) / max;
		}
		value = max;
	}

	/**
	 * 色を判定するクラスの生成
	 * 色を判別するときの上限下限値を設定する
	 * @param borderRedToYellow		赤色上限値、黄色下限値
	 * @param borderYellowToGreen	黄色上限値、緑色下限値
	 * @param borderGreenToBlue		緑色上限値、青色下限値
	 * @param borderBlueToRed		青色上限値、赤色下限値
	 */
	void setColorBorder(float borderRedToYellow, float borderYellowToGreen, float borderGreenToBlue,
			float borderBlueToRed) {
		setJudgeColor(new JudgeColor(borderRedToYellow, borderYellowToGreen,
				borderGreenToBlue, borderBlueToRed));
	}
}
