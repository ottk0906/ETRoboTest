package log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import body.SelfPosition.SelfPosition;
import lejos.hardware.lcd.LCD;

/**
 * 自己位置推定ログクラス
 * @author 尾角 武俊
 */
public class LogSelfPosition {

	/** 自己位置推定 */
	SelfPosition selfPos;

    /** 自己位置推定ログデータリスト */
    private List<LogSelfPositionData> logList;

    /**
     * コンストラクタ
     */
    public LogSelfPosition(SelfPosition selfPos) {
    	this.selfPos = selfPos;
        logList = new ArrayList<LogSelfPositionData>();
    }

    /**
     * 実行する
     */
    public void run() {
    	draw();
    	add();
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
        LCD.drawString("X-Coord", 0, 6);
        LCD.drawString(String.valueOf(selfPos.getXCoord()) , 11, 6);
        LCD.drawString("Y-Coord", 0, 7);
        LCD.drawString(String.valueOf(selfPos.getYCoord()), 11, 7);
        LCD.drawString("Angle", 0, 8);
        LCD.drawString(String.valueOf(selfPos.getAccumAngle()), 11, 8);
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
