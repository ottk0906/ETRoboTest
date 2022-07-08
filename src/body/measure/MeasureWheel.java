package body.measure;

import hardware.KamogawaRegulatedMotor;

/**
 * 車輪計測クラス
 * @author 後藤　聡文
 *
 */
public class MeasureWheel {
    /** 左モータ */
    private KamogawaRegulatedMotor leftMotor;
    /** 右モータ */
    private KamogawaRegulatedMotor rightMotor;

    /** 左モータの角速度(度/秒) */
    private float leftRotationSpeed;
    /** 右モータの角速度(度/秒) */
    private float rightRotationSpeed;

    private float anglePosL;		//左モータの現在位置の角度
    private float anglePosR;		//右モータの現在位置の角度

    /**
     * コンストラクタ
     * @param leftMotor  左モータ
     * @param rightMotor 右モータ
     */
    public MeasureWheel(KamogawaRegulatedMotor leftMotor, KamogawaRegulatedMotor rightMotor){
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
    }

    /**
     * 更新する
     */
    public void update() {
        leftRotationSpeed = leftMotor.getRotationSpeed();
        rightRotationSpeed = rightMotor.getRotationSpeed();
        anglePosL = leftMotor.getPosition() ;	//左モータの現在位置の角度
        anglePosR = rightMotor.getPosition() ;	//右モータの現在位置の角度
    }

    /**
     * 左モータの角速度(度/秒)を取得する
     * @return leftRotationSpeed 左モータの角速度(度/秒)
     */
    public float getLeftRotationSpeed() {
        return leftRotationSpeed;
    }

    /**
     * 右モータの角速度(度/秒)を取得する
     * @return rightRotationSpeed 右モータの角速度(度/秒)
     */
    public float getRightRotationSpeed() {
        return rightRotationSpeed;
    }

    /**
     * 左モータの現在位置の角度を取得する
     * @return 左モータの現在位置の角度
     */
    public float getLeftAnglePosition() {
		return anglePosL;
    }

	/**
	 * 右モータの現在位置の角度を取得する
	 * @return 右モータの現在位置の角度
	 */
    public float getRightAnglePosition() {
		return anglePosR;
    }

}
