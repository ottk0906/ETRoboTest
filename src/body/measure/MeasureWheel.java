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

    //---> Add 2022/06/20 T.Okado
    private int tachoCountL;		//左モータのタコメータのエンコード値
    private int tachoCountR;		//右モータのタコメータのエンコード値
    //<--- Add 2022/06/20 T.Okado

    /**
     * コンストラクタ
     * @param leftMotor 左モータ
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
        //---> Add 2022/06/20 T.Okado
        tachoCountL = leftMotor.getTachoCount() ;		//左モータのタコメータのエンコード値を更新する
        tachoCountR = rightMotor.getTachoCount() ;		//右モータのタコメータのエンコード値を更新する
        //<--- Add 2022/06/20 T.Okado
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

    //---> Add 2022/06/20 T.Okado
    public int getLeftTachoCount() {
		return tachoCountL;
    }
    public int getRightTachoCount() {
		return tachoCountR;
    }
    //<--- Add 2022/06/20 T.Okado


}
