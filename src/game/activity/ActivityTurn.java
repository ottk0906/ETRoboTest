package game.activity;

import body.Body;

/**
 *
 * @author 駒井
 *
 */
public class ActivityTurn extends Activity {
    enum PIVOT {
        LEFT, RIGHT
    };

    /** 回転角速度(度/秒) */
    private final float TURN = 90.0f;
    private PIVOT pivot;

    /**
     *
     * @param pivot  0なら左回転、１なら右回転
     */
    public ActivityTurn(int pivot) {
        if (pivot == 0) {
            this.pivot = PIVOT.LEFT;
        } else if (pivot == 1) {
            this.pivot = PIVOT.RIGHT;
        }
    }

    /**
     * 継続動作を実行する 目標速度と目標回転角速度を設定する
     */
    @Override
    public void doActivity() {
        // 軸足回転
        if (pivot == PIVOT.LEFT) {
            Body.control.setLeftRotationSpeed(0.0f);
            Body.control.setRightRotationSpeed(TURN);
        } else if (pivot == PIVOT.RIGHT) {
            Body.control.setLeftRotationSpeed(TURN);
            Body.control.setRightRotationSpeed(0.0f);
        }

    }
}
