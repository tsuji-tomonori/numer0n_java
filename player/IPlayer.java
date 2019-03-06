/**
 *
 */
package player;

import logic.Numer0nEatBite;
import logic.Numer0nValue;

/**
 * @author TomonoriTsuji
 * @since 2019/02/21
 */
public interface IPlayer {
	/**
	 * データの前処理
	 * (自分の値の設定などを行う)
	 * @return 設定した自分のNumer0n値
	 */
	Numer0nValue preprocessing();
	/**
	 * コールする値の決定
	 * @param eb 前回の判定結果(初回はでたらめな値が入るため別途処理が必要)
	 * @return 決定したコール値
	 */
	Numer0nValue call(Numer0nEatBite eb);

	/**
	 * 相手の手に対して判定を行う
	 * @param nv 相手の手
	 * @return　判定結果
	 */
	Numer0nEatBite div(Numer0nValue nv);
	/**
	 * ユーザ名の取得
	 * @return　ユーザ名
	 */
	String getName();
	/**
	 * 答えの取得
	 * @return 答えの値
	 */
	Numer0nValue getAns();
}
