package body.measure;

/**
 * 路面計測色相クラス
 * @author 原田　寛大
 */
public class MeasureCourseHue {
	/**色相：0.0f~360.0f*/
	private float hue;

	/**
	 * 列挙型色クラス
	 * 白、黒、赤、黄色、緑、青、その他
	 * @author 原田　寛大
	 *
	 */
	public enum Color{
		White,
		Black,
		Red,
		Yellow,
		Green,
		Blue,
		Other;
	}

	/** 色判定結果 黒,白,赤,黄,緑,青,その他 のいずれか */
	private Color color;

	/**赤色上限値,黄色下限値*/
	private final float BORDER_RED_TO_YELLOW;
	/**黄色上限値,緑色下限値*/
	private final float BORDER_YELLOW_TO_GREEN;
	/**緑色上限値,青色下限値*/
	private final float BORDER_GREEN_TO_BLUE;
	/**青色上限値,赤色下限値*/
	private final float BORDER_BLUE_TO_RED;

	/**
	 * コンストラクタ
	 */
	public MeasureCourseHue(float borderRedToYellow, float borderYellowToGreen, float borderGreenToBlue,
			float borderBlueToRed) {
		this.BORDER_RED_TO_YELLOW = borderRedToYellow;
		this.BORDER_YELLOW_TO_GREEN = borderYellowToGreen;
		this.BORDER_GREEN_TO_BLUE = borderGreenToBlue;
		this.BORDER_BLUE_TO_RED = borderBlueToRed;
	}

	/**
	 * 色相を設定する
	 */
	public void setHue(float hue) {
		if (hue >= 0.0f && hue <= 360.0f) {
			this.hue = hue;
		} else if (hue < 0.0f) {
			this.hue = hue + 360.0f;
		} else if (hue > 360.0f) {
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

}
