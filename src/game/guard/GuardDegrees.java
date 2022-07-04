package game.guard;

import body.Body;

public class GuardDegrees extends Guard {

	private float targetDegrees; //目標回転角度

	/**
	 * コンストラクタ
	 * @param targetDegrees	目標角度
	 */
	public GuardDegrees(float targetDegrees) {
		//アームモータの目標角度を設定する
		this.targetDegrees = targetDegrees;
	}

	/**
	 * 回転停止を判定する
	 * @return	true : 回転停止 / false ： 回転中
	 */
	@Override
	public boolean judge() {
		//現在の走行体の角度を取得する
		float tmpDegrees = Body.measure.getDegrees();
		//目標角度の±0.1度を設定、要検討
		return (tmpDegrees <= targetDegrees + 0.10f && tmpDegrees >= targetDegrees - 0.10f);

//		if (tmpDegrees <= targetDegrees + 0.10f && tmpDegrees >= targetDegrees - 0.10f) {
//			return true;
//		} else {
//			return false;
//		}
	}
}
