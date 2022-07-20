package body.measure;

import body.Body;

/**
 * 路面計測HSLクラス
 * @author 原田　寛大
 */
public class MeasureCourseHSL extends MeasureCourseHue {
	/** sv（彩度:Saturation、明度:lightness）*/
	private float saturation, lightness;

	/** judgeColorHSLで使用 saturation = 0.5f以下の時 */
	private final float SAT_050_BLACK_JUDGE_VALUE;
	private final float SAT_050_WHITE_JUDGE_VALUE;

	/** judgeColorHSLで使用 saturation = 1.0f以下の時 */
	private final float SAT_100_BLACK_JUDGE_VALUE;
	private final float SAT_100_WHITE_JUDGE_VALUE;

	public MeasureCourseHSL(float borderRedToYellow, float borderYellowToGreen, float borderGreenToBlue,
			float borderBlueToRed) {
		super(borderRedToYellow, borderYellowToGreen, borderGreenToBlue, borderBlueToRed);

		float target = Body.measure.getTarget();
		float white = Body.measure.getWhite();
		SAT_100_BLACK_JUDGE_VALUE = target - (white - target) * 35.0f / 50f;
		SAT_100_WHITE_JUDGE_VALUE = target + (white - target) * 35.0f / 50f;

		/** (limitWhite - averageLimitWhiteBlack)が0.035fになるような係数 */
		float correctionJudgeColorHSL = 0.35f / (SAT_100_WHITE_JUDGE_VALUE - target);

		SAT_050_BLACK_JUDGE_VALUE = target - 0.3f * correctionJudgeColorHSL;
		SAT_050_WHITE_JUDGE_VALUE = target + 0.3f * correctionJudgeColorHSL;

	}

	/**
	 * 彩度を取得する
	 * @return
	 */
	public float getSaturation() {
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
		if (saturation <= 0.3f) {
			if (lightness < Body.measure.getTarget()) {
				setColor(Color.Black);
			} else {
				setColor(Color.White);
			}
		} else if (saturation <= 0.5f) {
			if (lightness < SAT_050_BLACK_JUDGE_VALUE) {
				setColor(Color.Black);
			} else if (lightness > SAT_050_WHITE_JUDGE_VALUE) {
				setColor(Color.White);
			} else {
				setColor(judgeColorHue(getHue()));
			}
		} else {
			if (lightness < SAT_100_BLACK_JUDGE_VALUE) {
				setColor(Color.Black);
			} else if (lightness > SAT_100_WHITE_JUDGE_VALUE) {
				setColor(Color.White);
			} else {
				setColor(judgeColorHue(getHue()));
			}
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
}
