package body.control;

import hardware.KamogawaRegulatedMotor;

/**
 * 車輪制御クラス
 *
 */
public class ControlArm {

	private KamogawaRegulatedMotor armMotor;

	private float armRotationSpeed;

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

	public void setArmRotationSpeed(float armRotationSpeed) {
		this.armRotationSpeed = armRotationSpeed;

	}

}