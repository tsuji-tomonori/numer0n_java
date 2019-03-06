/**
 *
 */
package logic;

import player.Human;
import player.IPlayer;

/**
 * @author Tomonori Tsuji
 * @since 2019/02/20
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		IPlayer a = new Human();
		IPlayer b = new Human();

		game(a,b);
		System.out.println("FIN");
	}

	private static void game(IPlayer a, IPlayer b) {
		// 宣言等
		IPlayer[] player = {a,b};
		boolean finFlag = false;
		Numer0nValue nv = new Numer0nValue();
		Numer0nEatBite[] eb = new Numer0nEatBite[2];
		for(int i = 0; i < player.length;i++) eb[i] = new Numer0nEatBite();

		// 前処理
		for(int i = 0; i < player.length;i++) player[i].preprocessing();

		// ゲーム主処理
		while(!finFlag) {
			for(int i = 0; i < player.length; i++) {
				// Call
				nv = player[i].call(eb[i]);
				// 判定(判定は相手プレイヤーに行ってもらう)
				eb[i] = player[(player.length-1)-i].div(nv);
				// 表示
				System.out.println("判定 " + nv.toString()+ " => " + eb[i].toString());
				// ゲーム終了条件の確認
				if(eb[i].finFlag()) {
					finFlag = true;
					break;
				}
			}
		}
	}

}
