
package game;

import body.Body;
import game.activity.ActivityCalibrationBlack;
import game.activity.ActivityCalibrationWhite;
import game.activity.ActivityRun;
import game.activity.ActivityTurn;
import game.guard.GuardTouch;
import game.guard.GuardTurnStop;

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
        StateCalibrationWhite.getInstance().add(new GuardTouch(), new ActivityCalibrationWhite());

        StateCalibrationBlack.getInstance().add(new GuardTouch(), new ActivityCalibrationBlack());

        StateWaitStart.getInstance().add(new GuardTouch(), new ActivityRun(0, 0));

        /*StateRun.getInstance().add(new GuardTouch(), new ActivityRunOnOff(100, 30.0f));
        StateRun.getInstance().add(new GuardTouch(), new ActivityRunOnOff(100, 60.0f));
        StateRun.getInstance().add(new GuardTouch(), new ActivityRunOnOff(100, 90.0f));
        StateRun.getInstance().add(new GuardTouch(), new ActivityRun(100, 90.0f));
        StateRun.getInstance().add(new GuardTouch(), new ActivityRun(100, -90.0f));*/

       //StateRun.getInstance().add(new GuardTouch(), new ActivityRun(0, 0));
//        StateRun.getInstance().add(new GuardTime(4000), new ActivityTurn(LR.RIGHT.getParam()));
//        StateRun.getInstance().add(new GuardTouch(), new ActivityRun(0, 0));
//        StateRun.getInstance().add(new GuardTime(4000), new ActivityTurn(LR.LEFT.getParam()));
//        StateRun.getInstance().add(new GuardTouch(), new ActivityRun(0, 0));
//        StateRun.getInstance().add(new GuardTime(4000), new ActivityTurn(LR.RIGHT.getParam()));
//        StateRun.getInstance().add(new GuardTouch(), new ActivityRun(0, 0));
//        StateRun.getInstance().add(new GuardTime(4000), new ActivityTurn(LR.LEFT.getParam()));
//        StateRun.getInstance().add(new GuardTouch(), new ActivityRun(0, 0));

      //---> Add 2022/06/29 T.Okado
        Body.selfPos.setTurnStartInfo(540.0);
        StateRun.getInstance().add(new GuardTurnStop(), new ActivityTurn(LR.RIGHT.getParam()));
        //<--- Add 2022/06/29 T.Okado

        StateEnd.getInstance().add(new GuardTouch(), new ActivityRun(0, 0));

        changeState(null, StateCalibrationWhite.getInstance());
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
                state.doActivity(this);
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

    //---> Add 2022/06/20 T.Okado
    /**
     * 現在の競技状態を取得する
     * @return 現在の競技状態
     */
    public State getStatus() {
    	return state;
    }
    //<--- Add 2022/06/20 T.Okado

    /**
     * オブジェクトの文字列表現を取得する
     */
    @Override
    public String toString() {
        return state.toString();
    }

}
