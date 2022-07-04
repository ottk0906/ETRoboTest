package game.guard;

import body.Body;

public class GuardDegrees extends Guard {

	private float lowerTargetDegrees; //下限目標角度
	private float upperTargetDegrees; //上限目標角度

	/**
	 * コンストラクタ
	 * @param targetDegrees	目標角度
	 */
	public GuardDegrees(float lowerTargetDegrees, float upperTargetDegrees) {
		//アームモータの目標角度を設定する
		this.lowerTargetDegrees = lowerTargetDegrees;
		this.upperTargetDegrees = upperTargetDegrees;
	}

	/**
	 * 回転停止を判定する
	 * @return	true : 回転停止 / false ： 回転中
	 */
	@Override
	public boolean judge() {
		//現在の走行体の角度を取得する
		float tmpDegrees = Body.measure.getDegrees();
		//目標角度の下限と上限の設定
		return (tmpDegrees >= lowerTargetDegrees && tmpDegrees <= upperTargetDegrees);
	}
}
