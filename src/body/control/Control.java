package body.control;

import body.Body;

/**
 * 制御クラス
 * デザインパターンのFacadeパターンを採用
 * @author 後藤　聡文
 *
 */
public class Control {
    /** 車輪制御 */
    ControlWheel controlWheel;

	/**  アーム制御 */
	ControlArm controlArm;

    /** 目標速度(mm/秒) */
    private float forward;
    /** 目標回転角速度(度/秒) */
    private float turn;

    private float armRotationSpeed;

	/**
	 * コンストラクタ
	 * @param controlWheel 車輪制御
	 */
	public Control(ControlWheel controlWheel, ControlArm controlArm) {
		this.controlWheel = controlWheel;
		this.controlArm = controlArm;
	}

    /**
     * 制御する
     * 目標速度(mm/秒)と目標回転角速度(度/秒)から
     * 左右モータの角速度(度/秒)を計算して設定する
     */
    public void run(){
        turn = (float)Math.toRadians(turn);

        float leftRotationSpeed = (float) Math.toDegrees((2.0f * forward - Body.TREAD * turn) / Body.DIAMETER);
        float rightRotationSpeed = (float) Math.toDegrees((2.0f * forward + Body.TREAD * turn) / Body.DIAMETER);

        controlWheel.setLeftRotationSpeed(leftRotationSpeed);
        controlWheel.setRightRotationSpeed(rightRotationSpeed);
        controlArm.setArmRotationSpeed(this.armRotationSpeed);
        controlWheel.run();
        controlArm.run();
    }

    /**
     * 目標速度(mm/秒)を設定する
     * @param forward 目標速度(mm/秒)
     */
    public void setForward(float forward) {
        this.forward = forward;
    }

    /**
     * 目標回転角速度(度/秒)を設定する
     * @param turn 目標回転角速度(度/秒)
     */
    public void setTurn(float turn) {
        this.turn = turn;
    }

	/**
	 * 目標回転角速度(度/秒)を設定する
	 * @param armRotationSpeed 目標回転角速度
	 */
	public void setArmRotationSpeed(float armRotationSpeed) {
		this.armRotationSpeed = armRotationSpeed;

	}
}
