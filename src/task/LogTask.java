package task;

import lejos.hardware.lcd.LCD;
import log.Log;
import log.LogSelfPosition;
//<--- Add 2022/06/21 T.Okado

/**
 * ログタスククラス
 * @author
 *
 */
public class LogTask extends Thread {

    /** ログ */
    private Log log;

    //---> Add 2022/06/21 T.Okado
    /** 自己位置推定ログ */
    private LogSelfPosition logSelfPos;
    //<--- Add 2022/06/21 T.Okado


    /**
     * コンストラクタ
     * @param log   ログ
     */
    //---> Modify 2022/06/21 T.Okado
    //public LogTask(Log log){
    public LogTask(Log log,LogSelfPosition logSelfPos){
    //<--- Modify 2022/06/21 T.Okado
        this.log = log;
        //---> Add 2022/06/21 T.Okado
        this.logSelfPos = logSelfPos;
        //<--- Add 2022/06/21 T.Okado
    }

    /**
     * 実行する
     */
    @Override
    public void run() {
    	try {
        log.run();
        //---> Add 2022/06/21 T.Okado
        //自己位置推定ログ出力処理を実行する
        logSelfPos.run();
        //<--- Add 2022/06/21 T.Okado
    	}catch(Exception e) {
    		LCD.drawString("LTE", 5, 7);
    	}
    }

}
