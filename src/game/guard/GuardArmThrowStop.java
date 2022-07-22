package game.guard;

import body.Body;

public class GuardArmThrowStop extends Guard {
	/**  目標角度 */
	private float targetDegrees;

	/**
	 * コンストラクタ
	 * @param targetDegrees	目標角度
	 */
	public GuardArmThrowStop(float targetDegrees) {
		//アームモータの目標角度を設定する
		this.targetDegrees = targetDegrees;
	}

	/**
	 * 回転停止を判定する
	 * @return	true : 回転停止 / false ： 回転中
	 */
	@Override
	public boolean judge() {
		//現在の走行体の角度を取得する
		float tmpDegrees = Body.measure.getDegrees();
		//目標角度に足してのマージンの設定l
		if (tmpDegrees >= targetDegrees) {
			Body.control.setArmMode(0);
			return true;
		}
		return false;
	}
}