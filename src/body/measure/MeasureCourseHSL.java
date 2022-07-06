package body.measure;

import body.Body;

/**
 * 路面計測HSLクラス
 * @author 原田　寛大
 */
public class MeasureCourseHSL extends MeasureCourseHue {
	/** sv（彩度:Saturation、明度:lightness）*/
	private float saturation, lightness;

	public MeasureCourseHSL() {
		//仮設定
		setHue(-3.0f);
		saturation = lightness = -3.0f;
	}

	/**
	 * 彩度を取得する
	 * @return
	 */
	public float getSatuation() {
		return saturation;
	}

	/**
	 * 明度を取得する
	 * @return
	 */
	public float getLightness() {
		return lightness;
	}

	/**
	 * 更新する
	 */
	public void update() {
		convertRGBTo(Body.measure.getRGB());
		judgeColor();
	}


	/**
	 * 色をHSLで判定する
	 * 色判定結果を設定する
	 */
	public void judgeColor() {
		if (getJudgeColor() != null) {
		setColor(getJudgeColor().judgeColorHSL(getHue(), saturation, lightness));
		}
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

		//色相設定用変数
		float hue;

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
				hue += 360.0f;
			} else if (hue > 360.0f) {
				hue -= 360.0f;
			}
		}
		setHue(hue);

		/* s設定部分*/
		//収束値:cnt
		cnt = (max + min) / 2.0f;
		if (cnt <= (1.0f / 2.0f)) {
			saturation = (cnt - min) / cnt;
		} else {
			saturation = (max - cnt) / (1.0f - cnt);
		}
		/*l設定部分*/
		lightness = (max + min) / 2;
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
				borderGreenToBlue, borderBlueToRed, Body.measure.getWhite(), Body.measure.getTarget()));
	}
}
