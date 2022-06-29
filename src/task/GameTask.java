package task;

import java.util.concurrent.CountDownLatch;

import body.control.Control;
import body.measure.Measure;
import game.Game;
import game.SelfPosition.SelfPosition;
import lejos.hardware.Button;

/**
 * 競技タスククラス
 * @author 後藤　聡文
 *
 */
public class GameTask extends Thread {

    /** ほかのスレッドで実行中の操作セットが完了するまで、1つ以上のスレッドを待機可能にする同期化支援機能 */
    private CountDownLatch countDownLatch;

    /** 計測　*/
    Measure measure;
    /** 競技 */
    Game game;
    /** 制御 */
    Control control;
    //---> Add 2022/06/20 T.Okado
    /** 自己位置推定 */
    SelfPosition selfPos;
    //<--- Add 2022/06/20 T.Okado

    /**
     * コンストラクタ
     * @param countDownLatch 同期化支援機能
     * @param measure 計測
     * @param game 競技
     * @param control 制御
     */
    //---> Modify 2022/06/20 T.Okado
    //public GameTask(CountDownLatch countDownLatch, Measure measure, Game game, Control control){
    public GameTask(CountDownLatch countDownLatch, Measure measure, Game game, Control control, SelfPosition selfPos){
    //<--- Modify 2022/06/20 T.Okado
        this.countDownLatch = countDownLatch;
        this.measure = measure;
        this.game = game;
        this.control = control;
        //---> Add 2022/06/20 T.Okado
        this.selfPos = selfPos;
        //<--- Add 2022/06/20 T.Okado
    }

    /**
     * コンストラクタ
     * @param countDownLatch　同期化支援機能
     */
    public GameTask(CountDownLatch countDownLatch) {
    	this.countDownLatch = countDownLatch;
    }

    /**
     * 実行する
     */
    @Override
    public void run() {
        if(Button.ESCAPE.isDown() || game.isOver()){
            countDownLatch.countDown();
        }
		measure.update();
        game.run();
		control.run();
        //---> Add 2022/06/20 T.Okado
		//走行体を動作させた後に自己位置推定して、現在の座標位置を特定する
		selfPos.run();
        //<--- Add 2022/06/20 T.Okado
    }

}