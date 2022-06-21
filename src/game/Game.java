package game;

import game.activity.ActivityAcquisitionColor;
import game.activity.ActivityRun;
import game.guard.GuardTouch;
import lejos.hardware.lcd.LCD;

/**
 * 競技クラス
 * @author 後藤 聡文
 *
 */
public class Game {
    /** タスク呼出回数 */
    private int count = 0;

    /** 競技状態 */
    private State state;

    /** 競技が終了したか */
    private boolean isOver = false;

    /**
     * コンストラクタ
     */
    public Game() {
        StateWaitStart.getInstance().add(new GuardTouch(), new ActivityRun(0, 0));

		StateAcquisitionColor.getInstance().add(new GuardTouch(), new ActivityAcquisitionColor());

        StateEnd.getInstance().add(new GuardTouch(), new ActivityRun(0, 0));

        changeState(null, StateWaitStart.getInstance());
    }

    /**
     * 実施する
     */
    public void run() {
        if(isOver == false){
            count++;
            if (state instanceof StateEnd) {
                isOver = true;
            } else {
                try {
                	state.doActivity(this);
                }catch(Exception e) {
                	LCD.drawString("Ge1", 0, 7);
                }
            }
        }
    }

    /**
     * タスク呼出回数を取得する
     * @return タスク呼出回数
     */
    public int getCount() {
        return count;
    }

    /**
     * 競技状態を遷移する
     * @param oldState 前状態
     * @param newState　後状態
     */
    public void changeState(State oldState, State newState) {
        this.state = newState;

        if(oldState != null){
            oldState.exitAction();
        }
        if(newState != null){
            newState.entryAction();;
        }
    }

    /**
     * 競技が終了したか
     * @return 競技が終了した場合はtrue
     */
    public boolean isOver() {
        return isOver;
    }

    /**
     * オブジェクトの文字列表現を取得する
     */
    @Override
    public String toString() {
        return state.toString();
    }

}
