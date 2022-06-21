package body.SelfPosition;

import body.Body;

/**
 * 自己位置推定計算処理クラス
 * 自己位置推定する際の計算処理を記載する
 * @author 尾角 武俊
 */
public class CalcSelfPosition {

	private double beforeX;		//移動前のX座標
	private double beforeY;		//移動前のY座標
	private double afterX;		//移動後のX座標（計算後の自己位置）
	private double afterY;		//移動後のY座標（計算後の自己位置）
	private double accumAngle;	//積算角度
	private double accumDistance;	//積算移動距離
	private double distanceL;		//左車輪の移動距離
	private double distanceR;		//右車輪の移動距離
	private double distance;		//走行体の移動距離
	private double angle;			//走行体の回転角度
	private double beforeAngleL;	//左車輪の回転角度過去値
	private double beforeAngleR;	//右車輪の回転角度過去値

    /**
     * コンストラクタ
     */
	public CalcSelfPosition() {
		//各変数値を初期化する
		//現在のX座標、Y座標は自己位置推定を開始する位置を「原点（0，0）」として積算する
		beforeX = 0.0;
		beforeY = 0.0;
		afterX = 0.0;
		afterY = 0.0;
		accumAngle = 0.0;
		accumDistance = 0.0;
		distanceL = 0.0;
		distanceR = 0.0;
		distance = 0.0;
		angle = 0.0;
		beforeAngleL = 0.0;
		beforeAngleR = 0.0;
	}

    /**
     * 更新する
     */
	public void update() {
		updateDistance();		//距離更新処理を呼び出す
		updateAngle();			//角度更新処理を呼び出す
		UpdatePositionCoord();	//座標更新処理を呼び出す
	}

    /**
     * 距離を更新する
     */
	private void updateDistance() {

		//左車輪の回転角度現在値を取得する
		double afterAngleL = Body.measure.getLeftAngleCount();
		//右車輪の回転角度現在値を取得する
		double afterAngleR = Body.measure.getRightAngleCount();

		//左車輪の移動距離を算出する（車輪の円周 / 360 * (回転角度現在値 - 回転角度過去値)）
		distanceL = (Body.CIRCLE / 360) * (afterAngleL - beforeAngleL);
		//右車輪の移動距離を算出する（車輪の円周 / 360 * (回転角度現在値 - 回転角度過去値)）
		distanceR = (Body.CIRCLE / 360) * (afterAngleR - beforeAngleR);

		//走行体に移動距離を算出する(左右の車輪の移動距離を足して2で割る)
		distance = (distanceL + distanceR) / 2.0f;

		//累積移動距離に現在移動距離を足しこむ
		accumDistance += distance;

		//回転角度過去値を更新する（回転角度現在地を回転角度過去値で更新する）
		beforeAngleL = afterAngleL;
		beforeAngleR = afterAngleR;
	}

    /**
     * 回転角度を更新する(右旋回が正転)
     */
	private void updateAngle() {

		//回転角度を算出する
		//(360 / (2 * 円周率 * 車体トレッド幅)) * (左進行距離 - 右進行距離)
		angle = (360 / (2 * Math.PI * Body.TREAD)) * ( getLeftDistnce() - getRightDistnce());

		//現在回転角度を累積回転角度に足しこむ
		accumAngle += angle;

	}

    /**
     * 現在座標を更新する
     */
	private void UpdatePositionCoord() {

		//移動後のX座標を算出する
		//移動前のX座標 + 移動距離 * COS(回転角度)
		afterX = beforeX + distance * Math.cos(angle);

		//移動後のY座標を算出する
		//移動前のY座標 + 移動距離 * SIN(回転角度)
		afterY = beforeY + distance * Math.sin(angle);

		//移動前の座標を移動後の座標で更新する
		beforeX = afterX;
		beforeY = afterY;

	}

    /**
     * 左車輪の移動距離を取得する
     * @return 右車輪の移動距離（mm）
     */
	public double getLeftDistnce() {
		return distanceL;
	}

	/**
     * 右車輪の移動距離を取得する
     * @return 右車輪の移動距離（mm）
     */
	public double getRightDistnce() {
		return distanceR;
	}

	/**
     * 走行体の移動距離を取得する
     * @return 走行体の移動距離（mm）
     */
	public double getDistnce() {
		return distance;
	}

	/**
     * 走行体の積算移動距離を取得する
     * @return 走行体の積算移動距離（mm）
     */
	public double getAccumDistance() {
		return accumDistance;
	}

	/**
     * 走行体の積算回転角度を取得する
     * @return 走行体の積算回転角度
     */
	public double getAccumAngle() {
		return accumAngle;
	}

	/**
     * 走行体の現在位置のX座標を取得する
     * @return 走行体の現在位置のX座標
     */
	public double getXCoord() {
		return afterX;
	}

	/**
     * 走行体の現在位置のY座標を取得する
     * @return 走行体の現在位置のY座標
     */
	public double getYCoord() {
		return afterY;
	}

}
