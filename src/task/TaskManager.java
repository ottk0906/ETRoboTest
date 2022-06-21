package task;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import body.Body;
import game.Game;
import game.SelfPosition.SelfPosition;
import lejos.hardware.lcd.LCD;
import log.Log;
//---> Add 2022/06/21 T.Okado
import log.LogSelfPosition;
//<--- Add 2022/06/21 T.Okado

/**
 * タスク管理クラス
 * @author 後藤　聡文
 *
 */
public class TaskManager {

    // 競技タスク
    private GameTask gameTask;

    //---> Modify 2022/06/20 T.Okado
    //Game game;
    private Game game;
	//<--- Modify 2022/06/20 T.Okado

    // ログタスク
    private LogTask logTask;

    //---> Modify 2022/06/20 T.Okado
    //Log log;
    private Log log;
	//<--- Modify 2022/06/20 T.Okado

	//---> Add 2022/06/20 T.Okado
    // 自己位置推定クラス
    private SelfPosition selfPos;
    // 自己位置推定ログクラス
    private LogSelfPosition logSelfPos;
	//<--- Add 2022/06/20 T.Okado


    // スケジューラ
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> futureGame;
    private ScheduledFuture<?> futureLog;
    private CountDownLatch countDownLatch;

    /**
     * コンストラクタ
     */
    public TaskManager(){
        // タスク初期化 開始
        LCD.drawString("Initialize", 0, 0);

        // スケジューラ生成
        scheduler = Executors.newScheduledThreadPool(2);
        countDownLatch = new CountDownLatch(1);

        // タスク生成
        game = new Game();
        log = new Log(game);

		// ---> Add 2022/06/20 T.Okado
		// 自己位置推定のインスタンス生成
		selfPos = new SelfPosition(game);
		// 自己位置推定ログのインスタンス生成
		logSelfPos = new LogSelfPosition(game, selfPos);
		//<--- Add 2022/06/20 T.Okado

        //---> Modify 2022/06/20 T.Okado
        //gameTask = new GameTask(countDownLatch, Body.measure, game, Body.control);
        gameTask = new GameTask(countDownLatch, Body.measure, game, Body.control, selfPos);
        //<--- Modify 2022/06/20 T.Okado

        gameTask.setPriority(Thread.MAX_PRIORITY);

        //---> Modify 2022/06/21 T.Okado
        //logTask = new LogTask(log);
        logTask = new LogTask(log, logSelfPos);
        //<--- Modify 2022/06/21 T.Okado

        logTask.setPriority(Thread.NORM_PRIORITY);

        // 初期化完了
        Beep.ring();
    }

    /**
     * タスクのスケジューリング
     */
    public void schedule(){
        futureGame = scheduler.scheduleAtFixedRate(gameTask, 0, 10, TimeUnit.MILLISECONDS);
        futureLog = scheduler.scheduleAtFixedRate(logTask, 0, 500, TimeUnit.MILLISECONDS);
    }

    /**
     * 競技タスクが終了するまで待つ
     */
    public void await(){
        try{
            countDownLatch.await();
        }catch(InterruptedException e){

        }
    }

    /**
     * タスクの実行の取り消しとスケジューラのシャットダウン
     */
    public void shutdown(){
        if(futureLog != null){
            futureLog.cancel(true);
        }
        if(futureGame != null){
            futureGame.cancel(true);
        }
        scheduler.shutdownNow();
        log.write();
    }
}
