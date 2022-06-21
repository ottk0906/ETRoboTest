package task;

import java.util.concurrent.CountDownLatch;

import body.control.Control;
import body.measure.Measure;
import game.Game;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

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

    /**
     * コンストラクタ
     * @param countDownLatch 同期化支援機能
     * @param measure 計測
     * @param game 競技
     * @param control 制御
     */
    public GameTask(CountDownLatch countDownLatch, Measure measure, Game game, Control control){
        this.countDownLatch = countDownLatch;
        this.measure = measure;
        this.game = game;
        this.control = control;
    }

    /**
     * 実行する
     */
    @Override
    public void run() {
        if(Button.ESCAPE.isDown() || game.isOver()){
            countDownLatch.countDown();
        }
        try {
        LCD.drawString("GT1", 0, 7);
        measure.update();
        }catch(Exception e){
        	LCD.drawString("GTe1", 0, 7);
        }
        try {
        LCD.drawString("GT2", 0, 7);
        game.run();
        }catch(Exception e){
        	LCD.drawString("GTe2", 0, 7);
        }
        try {
        LCD.drawString("GT3", 0, 7);
			control.run();
        }catch(Exception e) {
        	LCD.drawString("GTe3", 0, 7);
        }
    }

}
