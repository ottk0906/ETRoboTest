package game.guard;

import body.Body;
import body.measure.MeasureCourseHue.Color;

public class GuardTouchOrColor extends Guard {
	/** 判定する色 */
	private Color color;

	public GuardTouchOrColor(Color color) {
		this.color = color;
	}

	/**
	 * 判定する
	 * @return 判定する色と一致するかボタンが押されたらtrue,それ以外false
	 */
	@Override
	public boolean judge() {
		return color == Body.measure.getColorHSL() || Body.measure.isUpped();
	}

}
