package game.activity;

import body.Body;

/**
 * PID走行クラス
 *
 */
public class ActivityArm extends ActivityRun {
	/**処理周期*/
	private float DELTA_T = 0.010f;
	/**偏差（前回の偏差、今回の偏差）*/
	private float kago[] = new float[2];
	/**平均偏差/秒*/
	private float integral;

	/**比例係数*/
	private final float KP;
	/**積分係数*/
	private final float KI;
	/**微分係数*/
	private final float KD;

	private float tagetDegrees;



	public ActivityArm(float forward, float turn, float targetDegrees, float KP, float KI, float KD) {
		super(forward, turn);

		this.tagetDegrees = targetDegrees;
		this.KP = KP;
		this.KI = KI;
		this.KD = KD;
	}

	/**
	 * 継続動作を実行する
	 * 路面明度と目標明度を比較して目標回転角速度を計算し、
	 * 目標速度と目標回転角速度を設定する
	 */
	@Override
	public void doActivity() {
		float p, i, d;
		// 路面明度を取得する
		float degrees = Body.measure.getDegrees();

		// 目標明度を取得する
		float targetDegrees = tagetDegrees;

		//操作量を計算する
		kago[0] = kago[1];
		kago[1] = targetDegrees - degrees;
		integral += (kago[1] + kago[0]) / 2.0f * DELTA_T;

		p = KP * kago[1];
		i = KI * integral;
		d = KD * (kago[1] - kago[0]) / DELTA_T;

		float armRotationSpeed = p + i + d;

		// 速度を設定する
		Body.control.setForward(forward);
		Body.control.setTurn(turn);
		Body.control.setArmRotationSpeed(armRotationSpeed);
	}
}