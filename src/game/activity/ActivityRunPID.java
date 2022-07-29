package game.activity;

import body.Body;

/**
 * PID走行クラス
 *@author 原田　寛大
 */
public class ActivityRunPID extends ActivityRun {
	/** 処理周期 */
	private float DELTA_T = 0.010f;
	/** 偏差(前回の偏差、今回の偏差) */
	private float kago[] = new float[2];
	/** 平均偏差/秒 */
	private float integral;

	/** 比例係数 */
	private final float KP;
	/** 積分係数 */
	private final float KI;
	/** 微分係数 */
	private final float KD;

	/** ラインエッジ 1=左、-1=右 */
	private final float lineEdge;

	/**
	 * コンストラクタ
	 * @param forward 前進角速度
	 * @param KP 比例係数
	 * @param KI 積分係数
	 * @param KD 微分係数
	 * @param lineEdge ラインエッジ 1=左、-1=右
	 */
	public ActivityRunPID(float forward, float KP, float KI, float KD, float lineEdge) {
		super(forward, 0.0f);

		this.KP = KP;
		this.KI = KI;
		this.KD = KD;
		this.lineEdge = lineEdge;
	}

	/**
	 * 継続動作を実行する
	 * 路面明度と目標明度を比較して目標回転角速度を計算し、
	 * 目標速度と目標回転角速度を設定する
	 */
	@Override
	public void doActivity() {
		float p, i, d;
		// 路面明度を取得する
		float lightness = Body.measure.getLightnessHSL();

		// 目標明度を取得する
		float target = Body.measure.getTarget();

		//操作量を計算する
		kago[0] = kago[1];
		kago[1] = target - lightness;
		integral += (kago[1] + kago[0]) / 2.0f * DELTA_T;

		p = KP * kago[1];
		i = KI * integral;
		d = KD * (kago[1] - kago[0]) / DELTA_T;

		turn = (p + i + d) * lineEdge;

		// 速度を設定する
		Body.control.setForward(forward);
		Body.control.setTurn(turn);
	}
}