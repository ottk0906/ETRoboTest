package game.activity;

import body.Body;

/**
 * 走行動作クラス
 *
 * @author 後藤 聡文
 *
 */
public class ActivityRun extends Activity {

	/** 目標速度(mm/秒) */
	protected float forward;
	/** 目標回転角速度(度/秒) */
	protected float turn;

	/**
	 * コンストラクタ
	 *
	 * @param forward 目標速度(mm/秒)
	 * @param turn 目標回転角速度(度/秒)
	 */
	public ActivityRun(float forward, float turn) {
		this.forward = forward;
		this.turn = turn;
	}

	/**
	 *目標速度(mm/秒)と目標回転角速度(度/秒)から
	 * 左右モータの角速度(度/秒)を計算して設定する
	 *
	 * @author 駒井
	 */
	public void calcSpeed() {
		turn = (float) Math.toRadians(turn);

		Body.control.setLeftRotationSpeed((float) Math.toDegrees((2.0f * forward - Body.TREAD * turn) / Body.DIAMETER));
		Body.control.setRightRotationSpeed((float) Math.toDegrees((2.0f * forward + Body.TREAD * turn) / Body.DIAMETER));
	}

	/**
	 * 継続動作を実行する 目標速度と目標回転角速度を設定する
	 */
	@Override
	public void doActivity() {
		calcSpeed();
	}

}
