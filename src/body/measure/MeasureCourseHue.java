package body.measure;

import lejos.hardware.sensor.EV3ColorSensor;

public abstract class MeasureCourseHue extends MeasureCourse {
	/**色相：0.0f~360.0f*/
	private float hue;

	/**白色下限値*/
	private float limitWhite;
	/**黒色上限値*/
	private float limitBlack;
	/**赤色上限下限値*/
	private float[] limitRed;
	/**黄色上限下限値*/
	private float[] limitYellow;
	/**緑色上限下限値*/
	private float[] limitGreen;
	/**青色上限下限値*/
	private float[] limitBlue;

	/**
	 * コンストラクタ
	 * @param colorSensor カラーセンサ
	 */
	public MeasureCourseHue(EV3ColorSensor colorSensor) {
		super(colorSensor);
		limitRed = new float[2];
		limitYellow = new float[2];
		limitGreen = new float[2];
		limitBlue = new float[2];
	}

	/**
	 * 色相を取得する
	 * @param hue 色相
	 */
	public void setHue(float hue) {
		this.hue = hue;
	}

	/**
	 * 白色下限値を取得する
	 * @return limitWhite 白色下限値
	 */
	public float getLimitWhite() {
		return limitWhite;
	}

	/**
	 * 黒色上限値を取得する
	 * @return	limitBlack 黒色上限値
	 */
	public float getLimitBlack() {
		return limitBlack;
	}

	/**
	 * 赤色上限下限値を取得する
	 * @return limitRed[0] 赤色下限値, limitRed[1] 赤色上限値
	 */
	public float[] getLimitRed() {
		return limitRed;
	}

	/**
	 * 黄色上限下限値を取得する
	 * @return	limitYellow[0] 黄色下限値, limitYellow[1] 黄色上限値
	 */
	public float[] getLimitYellow() {
		return limitYellow;
	}

	/**
	 * 緑色上限下限値を取得する
	 * @return	limitGreen[0] 緑色下限値, limitGreen[1] 緑色上限値
	 */
	public float[] getLimitGreen() {
		return limitGreen;
	}

	/**
	 * 青色上限下限値を取得する
	 * @return	limitBlue[0] 青色下限値, limitBlue[1] 青色上限値
	 */
	public float[] getLimitBlue() {
		return limitBlue;
	}

	/**
	 * 色を判別するときの上限下限値を設定する
	 * @param borderWhiteToColor	白色下限値
	 * @param borderBlackToColor	黒色上限値
	 * @param borderRedToYellow		赤色上限値、黄色下限値
	 * @param borderYellowToGreen	黄色上限値、緑色下限値
	 * @param borderGreenToBlue		緑色上限値、青色下限値
	 * @param borderBlueToRed		青色上限値、赤色下限値
	 */
	void setColorLimit(float borderWhiteToColor, float borderBlackToColor,
						float borderRedToYellow, float borderYellowToGreen,
						float borderGreenToBlue, float borderBlueToRed){
		limitWhite = borderWhiteToColor;
		limitBlack = borderBlackToColor;
		limitRed[1] = limitYellow[0] = borderRedToYellow;
		limitYellow[1] = limitGreen[0] = borderYellowToGreen;
		limitGreen[1] = limitBlue[0] = borderGreenToBlue;
		limitBlue[1] = limitRed[0] = borderBlueToRed;
	}


	void adjustColorLimit(float adjust) {
		limitWhite += adjust;
		limitBlack += adjust;
		limitRed[0] += adjust;
		limitRed[1] += adjust;
		limitYellow[0] += adjust;
		limitYellow[1] += adjust;
		limitGreen[0] += adjust;
		limitGreen[1] += adjust;
		limitBlue[0] += adjust;
		limitBlue[1] += adjust;
	}

	/**
	 * 色相：Hueの数値から色を判定する
	 * @return 赤、黄、緑、青のいずれか
	 */
	Color judgeColorHue() {
		if(hue > limitRed[0] || hue < limitRed[1]) {
			return Color.Red;
		}else if(hue < limitYellow[1]) {
			return Color.Yellow;
		}else if(hue < limitGreen[1]) {
			return Color.Green;
		}else {
			return Color.Blue;
		}
	}
}
