package task;

import lejos.hardware.lcd.LCD;
import log.Log;

/**
 * ログタスククラス
 * @author 後藤　聡文
 *
 */
public class LogTask extends Thread {

    /** ログ */
    private Log log;

    /**
     * コンストラクタ
     * @param log   ログ
     */
    public LogTask(Log log){
        this.log = log;
    }

    /**
     * 実行する
     */
    @Override
    public void run() {
    	LCD.drawString("LT1", 5, 7);
        log.run();
        LCD.drawString("LT2", 5, 7);
    }

}
