package body.measure;

public abstract class MeasureCourseHue {
	/**色相：0.0f~360.0f*/
	private float hue;

	/** 色判定結果 */
	private Color color;

	/** 色判定クラス */
	JudgeColor judgeColor;

	/**
	 * コンストラクタ
	 */
	public MeasureCourseHue() {
	}

	/**
	 * 色相を設定する
	 */
	public void setHue(float hue) {
		if(hue >= 0.0f&&hue<=360.0f) {
			this.hue = hue;
		}else if(hue < 0.0f) {
			this.hue = hue + 360.0f;
		}else if(hue > 360.0f){
			this.hue = hue - 360.0f;
		}
	}

	/**
	 * 色相を取得する
	 * @return hue : 色相
	 */
	public float getHue() {
		return hue;
	}

	/**
	 * 色判定結果を設定する
	 * @param color :白、黒、赤、黄、緑、青のいずれか
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * 色判定結果を取得する
	 * @return color :白、黒、赤、黄、緑、青のいずれか
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * 色判定クラスを設定
	 * @param judgeColor
	 */
	public void setJudgeColor(JudgeColor judgeColor) {
		this.judgeColor = judgeColor;
	}
}