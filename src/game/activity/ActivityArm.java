package game.activity;

import body.Body;

/**
 * アーム動作クラス
 * @author 駒井
 *
 */
public class ActivityArm extends ActivityRun {
	private float targetDegrees;

//	public ActivityArm(float forward, float turn, float targetDegrees) {
//		super(forward, turn);
//
//		this.targetDegrees = targetDegrees;
//	}

	/**
	 * コンストラクタ
	 * @param targetDegrees 目標角度
	 */
	public ActivityArm(float targetDegrees) {
		super(0, 0);
		this.targetDegrees = targetDegrees;
	}

	/**
	 * 継続動作を実行する
	 * controlクラスに目標角度を渡す
	 */
	@Override
	public void doActivity() {

		// 速度を設定する
//		Body.control.setForward(forward);
//		Body.control.setTurn(turn);
		Body.control.setDegrees(targetDegrees);

	}
}