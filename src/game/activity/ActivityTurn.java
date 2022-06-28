package game.activity;

import body.Body;

/**
 *
 * @author 駒井
 *
 */
public class ActivityTurn extends Activity {


    /** 回転角速度(度/秒) */
    private final float TURN = 90.0f;
    private boolean param;

    /**
     *
     * @param pivot  0なら左回転、１なら右回転
     */
    public ActivityTurn(boolean param) {
            this.param = param;
    }

    /**
     * 継続動作を実行する 目標速度と目標回転角速度を設定する
     */
    @Override
    public void doActivity() {
        // 軸足回転
        if (param) {
            Body.control.setLeftRotationSpeed(0.0f);
            Body.control.setRightRotationSpeed(2*((Body.TREAD * TURN) / (Body.DIAMETER)));
        } else {
            Body.control.setLeftRotationSpeed(2*((Body.TREAD * TURN) / (Body.DIAMETER)));
            Body.control.setRightRotationSpeed(0.0f);
        }

    }
}
