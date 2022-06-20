package body.control;

/**
 * 制御クラス
 * デザインパターンのFacadeパターンを採用
 * @author 後藤　聡文
 *
 */
public class Control {
	/** 車輪制御 */
	ControlWheel controlWheel;

	/**
	 * コンストラクタ
	 * @param controlWheel 車輪制御
	 */
	public Control(ControlWheel controlWheel) {
		this.controlWheel = controlWheel;
	}

	/**
	 * 制御する
	 *
	 */
	public void run() {
		controlWheel.run();
	}

	public void setLeftRotationSpeed(float leftRotationSpeed) {
		controlWheel.setLeftRotationSpeed(leftRotationSpeed);
	}

	public void setRightRotationSpeed(float rightRotationSpeed) {
		controlWheel.setRightRotationSpeed(rightRotationSpeed);
	}

}
