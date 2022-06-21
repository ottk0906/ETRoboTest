package log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.StateRun;
import game.SelfPosition.SelfPosition;
import lejos.hardware.lcd.LCD;

/**
 * 自己位置推定ログクラス
 * @author 尾角 武俊
 */
public class LogSelfPosition {

	/** 競技 */
	private Game game;

	/** 自己位置推定 */
	private SelfPosition selfPos;

    /** 自己位置推定ログデータリスト */
    private List<LogSelfPositionData> logList;

    /**
     * コンストラクタ
     */
    public LogSelfPosition(Game game, SelfPosition selfPos) {
    	this.game = game;
    	this.selfPos = selfPos;
        logList = new ArrayList<LogSelfPositionData>();
    }

    /**
     * 実行する
     */
    public void run() {
		//走行中の場合のみ、自己位置推定ログ出力処理を実行する
        if (game.getStatus() instanceof StateRun) {
	    	draw();
	    	add();
	    	write();
        }
    }

    /**
     * 追加する
     */
    private void add() {
    	//自己位置推定ログデータクラスのインスタンスを生成し、ログリストに追加する
        logList.add(
        		new LogSelfPositionData(
        			selfPos.getXCoord(), selfPos.getYCoord(), selfPos.getAccumAngle()
        		)
    		);
    }

    /**
     * 画面出力する
     */
    private void draw() {
        LCD.drawString("X-Coord", 0, 5);
        LCD.drawString(String.valueOf(selfPos.getXCoord()) , 11, 5);
        LCD.drawString("Y-Coord", 0, 6);
        LCD.drawString(String.valueOf(selfPos.getYCoord()), 11, 6);
        LCD.drawString("Angle", 0, 7);
        LCD.drawString(String.valueOf(selfPos.getAccumAngle()), 11, 7);
    }

    /**
     * ログを出力する
     */
    public void write() {
        try {
            StringBuilder sb = new StringBuilder();

            for (LogSelfPositionData data : logList) {
                sb.append(data.toString());
            }

            File file = new File("logSelfPosition.csv");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sb.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
