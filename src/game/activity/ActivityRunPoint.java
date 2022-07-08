package game.activity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import body.Body;

/**
 * 座標指定走行クラス
 * @author 尾角 武俊
 *
 */
public class ActivityRunPoint extends ActivityRun{

	private double targetX;		//移動先のX座標
	private double targetY;		//移動先のY座標
	private double marginAngle;	//回転角度のマージン（360°単位）
	//private boolean AngleSetFlg;	//走行会の回転動作実施済みフラグ

    /**
     * コンストラクタ
     * @param forward		目標速度(mm/秒)
     * @param turn 		目標回転角速度(度/秒)
     * @param targetX		移動先のX座標
     * @param targetY		移動先のY座標
     * @param marginAngle	回転角度のマージン（360°単位）
     */
	public ActivityRunPoint(float forward, float turn, double targetX, double targetY, double marginAngle ) {
		super(forward, turn);
		this.targetX = targetX;
		this.targetY = targetY;
		this.marginAngle = marginAngle;
		//this.AngleSetFlg = false;

	    //---> Add 2022/07/05 T.Okado Debug用
        try {
	        //ログファイルが既に存在する場合は削除する
	        File file = new File("ActivityRunPoint.csv");
	        if (file.exists()) file.delete();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
        //<--- Add 2022/07/05 T.Okado

	}

	@Override
	public void doActivity() {

		float tmpForward;
		float tmpTurn;

		//現在の自己位置を取得する
		double XCoord = Body.selfPos.getXCoord();
		double YCoord = Body.selfPos.getYCoord();

		//「現在の座標」から「移動先の座標」までの角度（ラジアン）を算出する
		double targetRad = Math.atan2((targetY - YCoord), (targetX - XCoord));
		//ラジアンを角度に変換する
		double targetAngle = targetRad * (180 / Math.PI);
		//360°単位に補正する
		targetAngle = targetAngle % 360;
		//マイナスの角度(逆回転)の場合、正回転の値に補正する
		if(targetAngle < 0) targetAngle = targetAngle + 360;
		//現在の走行体の回転角度を取得する
		double tmpAngle = Body.selfPos.getAfterRadianToAngle();
		//「現在の座標から移動先の座標までの角度」と「現在の走行体の角度」の差異を算出する
		double tmpAngleDiff = Math.abs(targetAngle - tmpAngle);
		//回転角度が近い方の角度に補正する
		if (tmpAngleDiff > 180) tmpAngleDiff = 360 - tmpAngleDiff;

		//「現在の座標から移動先の座標までの角度」と「現在の走行体の角度」の差異がマージン値より大きい場合
		if (tmpAngleDiff > marginAngle) {  //&& !AngleSetFlg) {
			//走行体を回転させる
			tmpForward = 0.0f;
			//近い方にの角度に回転するように回転方向を調整する
			if (tmpAngle <= 180) {
				if(tmpAngle <= targetAngle && targetAngle <= tmpAngle +180 ) {
					//左に回転する
					tmpTurn = turn;
				} else {
					//右に回転する
					tmpTurn = turn * -1;
				}
			} else {
				if(tmpAngle -180 <= targetAngle && targetAngle <= tmpAngle ) {
					//右に回転する
					tmpTurn = turn * -1;
				} else {
					//左に回転する
					tmpTurn = turn;
				}
			}
		} else {
			//回転走行により走行体の進行方向が決定した後は、走行体を直進させる
			//AngleSetFlg = true;
			//走行体を直進走行させる
			tmpForward = forward;
			tmpTurn = 0.0f;
		}
	    //---> Add 2022/07/05 T.Okado Debug用
		write(XCoord,YCoord,targetAngle,tmpAngle,tmpForward,tmpTurn);
	    //<--- Add 2022/07/05 T.Okado

		//目標速度を設定する
        Body.control.setForward(tmpForward);
        //目標回転角速度を設定する
        Body.control.setTurn(tmpTurn);

	}

    //---> Add 2022/07/05 T.Okado Debug用
	public void write(double XCoord, double YCoord, double targetAngle, double tmpAngle,double tmpForward,double tmpTurn ) {
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(XCoord).append(",");
            sb.append(YCoord).append(",");
            sb.append(targetAngle).append(",");
            sb.append(tmpAngle).append(",");
            sb.append(tmpForward).append(",");
            sb.append(tmpTurn).append("\r\n");

        	File file = new File("ActivityRunPoint.csv");
            FileWriter fw = new FileWriter(file, true);	//アペンドモードで書き込む
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sb.toString());
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //<--- Add 2022/07/05 T.Okado


}
