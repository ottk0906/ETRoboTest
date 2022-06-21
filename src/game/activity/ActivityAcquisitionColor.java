package game.activity;

import body.Body;
import lejos.hardware.lcd.LCD;

/**
 * 色情報取得表示動作クラス
 * @author user212
 *
 */
public class ActivityAcquisitionColor extends Activity {

	/**
	 * コンストラクタ
	 */
	public ActivityAcquisitionColor() {

	}

	/**
	 * 仮限界値設定
	 */
	@Override
	public void entryAction() {
		try{
			float limitW = 0.1f;
		float limitB = 0.03f;
		float borderRtoY = 30.0f;
		float borderYtoG = 90.0f;
		float borderGtoB = 180.0f;
		float borderBtoR = 300.0f;
		Body.measure.setColorLimit(limitW, limitB, borderRtoY, borderYtoG, borderGtoB, borderBtoR);
		}catch(Exception e) {
			LCD.drawString("ACE", 0, 7);
		}
	}

	/**
	 * 継続動作を実行する
	 */
	@Override
	public void doActivity() {
		draw();
	}

	/**
     * LCDに描画する
     */
    private void draw() {
		LCD.clear();
		LCD.drawString("RGB", 0, 0);
		float[] rgb = Body.measure.getRGB();
		LCD.drawString(Float.toString(rgb[0]), 11, 0);
		LCD.drawString(Float.toString(rgb[1]),11,1);
		LCD.drawString(Float.toString(rgb[2]),11,2);
		LCD.drawString("H(HSV)", 0, 3);
		LCD.drawString(Float.toString(Body.measure.getHueHSV()), 11, 3);
		LCD.drawString("C(HSV)", 0, 4);
		LCD.drawString(String.valueOf(Body.measure.judgeColorHSV()), 11, 4);
		LCD.drawString("H(HSL)", 0, 5);
		LCD.drawString(Float.toString(Body.measure.getHueHSL()), 11, 5);
		LCD.drawString("C(HSL)", 0, 6);
		LCD.drawString(String.valueOf(Body.measure.judgeColorHSL()), 11, 6);

		//画面が狭いので一部ずつ表示するようにコメントアウト
		/*LCD.drawString("S(HSV)", 0, 1);
		LCD.drawString(Float.toString(Body.measure.getSaturationHSV()), 11, 1);
		LCD.drawString("V(HSV)", 0, 2);
		LCD.drawString(Float.toString(Body.measure.getValueHSV()), 11, 2);*/
		/*LCD.drawString("S(HSL)", 0, 5);
		LCD.drawString(Float.toString(Body.measure.getSaturationHSL()), 11, 5);
		LCD.drawString("L(HSL)", 0, 6);
		LCD.drawString(Float.toString(Body.measure.getLightnessHSL()), 11, 6);*/

    }

}
