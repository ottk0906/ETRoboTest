package body.measure;

import body.Body;

public class JudgeColor {
	/**赤色上限値,黄色下限値*/
	private final float BORDER_RED_TO_YELLOW;
	/**黄色上限値,緑色下限値*/
	private final float BORDER_YELLOW_TO_GREEN;
	/**緑色上限値,青色下限値*/
	private final float BORDER_GREEN_TO_BLUE;
	/**青色上限値,赤色下限値*/
	private final float BORDER_BLUE_TO_RED;

	/** judgeColorHSLで使用 saturation = 0.5f以下の時 */
	private final float SAT_050_BLACK_JUDGE_VALUE;
	private final float SAT_050_WHITE_JUDGE_VALUE;

	/** judgeColorHSLで使用 saturation = 1.0f以下の時 */
	private final float SAT_100_BLACK_JUDGE_VALUE;
	private final float SAT_100_WHITE_JUDGE_VALUE;

	/** judgeColorHSVで使用 白色の上限値*/
	private final float LIMIT_SATURATION_HSV;
	/** judgeColorHSVで使用 黒色の上限値 */
	private final float LIMIT_VALUE_HSV;

	/**
	 * コンストラクタ
	 */
	public JudgeColor(float BORDERREDTOYELLOW, float BORDERYELLOWTOGREEN, float BORDERGREENTOBLUE,
			float BORDERBLUETORED) {
		this.BORDER_RED_TO_YELLOW = BORDERREDTOYELLOW;
		this.BORDER_YELLOW_TO_GREEN = BORDERYELLOWTOGREEN;
		this.BORDER_GREEN_TO_BLUE = BORDERGREENTOBLUE;
		this.BORDER_BLUE_TO_RED = BORDERBLUETORED;
		this.LIMIT_SATURATION_HSV = 0.25f;
		this.LIMIT_VALUE_HSV = Body.measure.getTarget() * 3.0f / 5.0f;

		/* 以下HSVで使用しない定数 */
		this.SAT_050_BLACK_JUDGE_VALUE = 0;
		this.SAT_050_WHITE_JUDGE_VALUE = 0;
		this.SAT_100_BLACK_JUDGE_VALUE = 0;
		this.SAT_100_WHITE_JUDGE_VALUE = 0;

	}

	/**
	 * コンストラクタ
	 * @param measureCourseHSL
	 */
	public JudgeColor(float BORDERREDTOYELLOW, float BORDERYELLOWTOGREEN, float BORDERGREENTOBLUE,
			float BORDERBLUETORED, float white, float target) {
		this.BORDER_RED_TO_YELLOW = BORDERREDTOYELLOW;
		this.BORDER_YELLOW_TO_GREEN = BORDERYELLOWTOGREEN;
		this.BORDER_GREEN_TO_BLUE = BORDERGREENTOBLUE;
		this.BORDER_BLUE_TO_RED = BORDERBLUETORED;
		SAT_100_BLACK_JUDGE_VALUE = target - (white - target) * 35.0f / 50f;
		SAT_100_WHITE_JUDGE_VALUE = target + (white - target) * 35.0f / 50f;

		/** (limitWhite - averageLimitWhiteBlack)が0.035fになるような係数 */
		float correctionJudgeColorHSL = 0.35f / (SAT_100_WHITE_JUDGE_VALUE - target);

		SAT_050_BLACK_JUDGE_VALUE = target - 0.3f * correctionJudgeColorHSL;
		SAT_050_WHITE_JUDGE_VALUE = target + 0.3f * correctionJudgeColorHSL;

		/* 以下HSLで使用しない定数 */
		this.LIMIT_SATURATION_HSV = 0;
		this.LIMIT_VALUE_HSV = 0;
	}

	/**
	 * 色相：Hueの数値から色を判定する
	 * @return 赤、黄、緑、青のいずれか
	 */
	Color judgeColorHue(float hue) {
		if (hue > BORDER_BLUE_TO_RED || hue <= BORDER_RED_TO_YELLOW) {
			return Color.Red;
		} else if (hue <= BORDER_YELLOW_TO_GREEN) {
			return Color.Yellow;
		} else if (hue <= BORDER_GREEN_TO_BLUE) {
			return Color.Green;
		} else {
			return Color.Blue;
		}
	}

	/**
	 * 色をHSLで判定する
	 * @return　色
	 */
	public Color judgeColorHSL(float hue, float saturation, float lightness) {
		if (saturation <= 0.3f) {
			if (lightness > Body.measure.getTarget()) {
				return Color.White;
			} else {
				return Color.Black;
			}
		} else if (saturation <= 0.5f) {
			if (lightness < SAT_050_BLACK_JUDGE_VALUE) {
				return Color.Black;
			} else if (lightness > SAT_050_WHITE_JUDGE_VALUE) {
				return Color.White;
			} else {
				return judgeColorHue(hue);
			}
		} else {
			if (lightness < SAT_100_BLACK_JUDGE_VALUE) {
				return Color.Black;
			} else if (lightness > SAT_100_WHITE_JUDGE_VALUE) {
				return Color.White;
			} else {
				return judgeColorHue(hue);
			}
		}
	}

	/**
	 * 色をHSVで判定する
	 */
	public Color judgeColorHSV(float hue, float saturation, float value) {
		if (value <= LIMIT_VALUE_HSV) {
			return Color.Black;
		} else if (saturation < LIMIT_SATURATION_HSV) {
			return Color.White;
		} else {
			return judgeColorHue(hue);
		}
	}
}
