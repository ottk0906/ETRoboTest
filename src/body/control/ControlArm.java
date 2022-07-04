package body.control;

import hardware.KamogawaRegulatedMotor;

/**
 * 車輪制御クラス
 *
 */
public class ControlArm {

	private KamogawaRegulatedMotor armMotor;

	private float targetDegrees;

	private float armRotationSpeed;

	/**処理周期*/
	private float DELTA_T = 0.010f;
	/**偏差（前回の偏差、今回の偏差）*/
	private float kago[] = new float[2];
	/**平均偏差/秒*/
	private float integral;

	/**比例係数*/
	private final float KP = 4.0f;
	/**積分係数*/
	private final float KI = 0.01f;
	/**微分係数*/
	private final float KD = 0.01f;

	/**
	 * コンストラクタ
	 * @param leftMotor 左モータ
	 * @param rightMotor　右モータ
	 */
	public ControlArm(KamogawaRegulatedMotor armMotor) {
		this.armMotor = armMotor;
	}

	/**
	 * 制御する
	 */
	public void run() {
		armMotor.setSpeed(armRotationSpeed);
		if (armRotationSpeed >= 0) {
			armMotor.forward();
		} else {
			armMotor.backward();
		}
	}

	/**
	 * 目標角度を設定し、calcArmRotationSpeed()を実行する
	 * @param targetDegrees 目標角度
	 */
	public void setDegrees(float targetDegrees) {
		this.targetDegrees = targetDegrees;
		calcArmRotationSpeed();
	}

	/**
	 * 目標角度からアームモータの回転速度を算出する
	 */
	public void calcArmRotationSpeed() {
		float p, i, d;
		// 現在の角度を取得する
		float tmpDegrees =armMotor.getPosition();

		//操作量を計算する
		kago[0] = kago[1];
		kago[1] = targetDegrees - tmpDegrees;
		integral += (kago[1] + kago[0]) / 2.0f * DELTA_T;

		p = KP * kago[1];
		i = KI * integral;
		d = KD * (kago[1] - kago[0]) / DELTA_T;

		this.armRotationSpeed = p + i + d;
	}

	/**
	 * アームモータの速度を渡す
	 * @return armRotationSpeed アームモータの速度
	 */
	public float getArmRotationSpeed() {
		return armRotationSpeed;
	}
}