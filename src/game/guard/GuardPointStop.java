package game.guard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import body.Body;

/**
 * 座標指定走行の停止条件
 * @author 尾角 武俊
 */
public class GuardPointStop extends Guard {

	private double targetX;		//移動先のX座標
	private double targetY;		//移動先のY座標
	private double marginDist;	//走行停止判定のマージン（mm）

    /**
     * コンストラクタ
     * @param targetX			移動先のX座標
     * @param targetY			移動先のY座標
     * @param marginDistance	走行停止判定のマージン（mm）
     */
	public GuardPointStop(double targetX, double targetY, double marginDist) {
		this.targetX = targetX;
		this.targetY = targetY;
		this.marginDist = marginDist;

	    //---> Add 2022/07/05 T.Okado Debug用
        try {
	        //ログファイルが既に存在する場合は削除する
	        File file = new File("GuardPointStop.csv");
	        if (file.exists()) file.delete();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
        //<--- Add 2022/07/05 T.Okado

	}

    /**
     * 座標指定走行停止を判定する
     * @return	True : 走行停止 / False ： 走行中
     */
	@Override
	public boolean judge() {

		//現在の自己位置を取得する
		double XCoord = Body.selfPos.getXCoord();
		double YCoord = Body.selfPos.getYCoord();

		//「移動先の座標」と「現在の自己位置」との距離を算出する
		double tmpX = targetX - XCoord;
		double tmpY = targetY - YCoord;
		double tmpDist = Math.sqrt(Math.pow(tmpX,2) + Math.pow(tmpY,2));

	    //---> Add 2022/07/05 T.Okado Debug用
		write(XCoord,YCoord,tmpX,tmpY,tmpDist);
	    //<--- Add 2022/07/05 T.Okado

		//「移動先の座標」と「現在の自己位置」との距離が「走行停止判定マージン」以下の場合に停止と判定する
		if(tmpDist <= marginDist) {
			return true;
		} else {
			return false;
		}
	}

    //---> Add 2022/07/05 T.Okado Debug用
	public void write(double XCoord, double YCoord, double tmpX, double tmpY,double tmpDist ) {
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(XCoord).append(",");
            sb.append(YCoord).append(",");
            sb.append(tmpX).append(",");
            sb.append(tmpY).append(",");
            sb.append(tmpDist).append("\r\n");

        	File file = new File("GuardPointStop.csv");
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
