package game.SelfPosition;

import game.Game;
import game.StateRun;

/**
 * 自己位置推定クラス
 * デザインパターンのFacadeパターンを採用
 * @author 尾角 武俊
 */
public class SelfPosition {

    /** 競技 */
    private Game game;
	/** 自己位置推定計算処理 */
	private CalcSelfPosition calcSelfPos;

    /**
     * コンストラクタ
     */
	public SelfPosition(Game game){
		this.game = game;
		calcSelfPos = new CalcSelfPosition();	//自己位置推定計算処理クラスのインスタンスを生成する
	}

    /**
     * 実施する
     */
	public void run() {
		//走行中の場合のみ、自己位置推定を実行する
        if (game.getStatus() instanceof StateRun) {
        	//自己位置更新処理を呼び出す
        	calcSelfPos.update();
		}
	}

	/**
     * 走行体の積算回転角度を取得する
     * @return 走行体の積算回転角度
     */
	public double getAccumAngle() {
		return calcSelfPos.getAccumAngle();
	}

	/**
     * 走行体の現在位置のX座標を取得する
     * @return 走行体の現在位置のX座標
     */
	public double getXCoord() {
		return calcSelfPos.getXCoord();
	}

	/**
     * 走行体の現在位置のY座標を取得する
     * @return 走行体の現在位置のY座標
     */
	public double getYCoord() {
		return calcSelfPos.getYCoord();
	}




}
