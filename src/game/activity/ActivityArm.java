package game.activity;

import body.Body;

/**
 * アーム動作クラス
 * @author 駒井
 */
public class ActivityArm extends ActivityRun {
	/** 目標角度 */
	private float targetDegrees;
	/** アームモード*/
	private int armMode = 0;

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
	 * controlクラスに目標角度とアームモードを渡す
	 */
	@Override
	public void doActivity() {
		Body.control.setDegrees(targetDegrees);
		Body.control.setArmMode(armMode);
	}
}