package logic;

import java.util.Arrays;

/**
 * EatBiteの値に関するクラス
 * EatBiteの判定を行う
 * @author Tomonori Tsuji
 * @since 2019/02/20
 */
public class Numer0nEatBite {

	/** 桁数(今回は3で固定) */
	private final int DIGIT = 3;
	/** EatBiteを格納する配列 eatBite[0] = Eat, eatBite[1] = Bite */
	private int[] eatBite = new int[2];
	/** Numer0nの値を格納 */
	private Numer0nValue nv;
	/** Numer0n値を格納したかを表す */
	private boolean nvFlag = false;

	/**
	 * デフォルトコンストラクター
	 * eatBite 配列を初期化
	 */
	public Numer0nEatBite() {
		init();
	}

	/**
	 * コンストラクター
	 * 事前にNumer0nValue を持っている場合, それを保持する
	 * @param nv Numer0nの値
	 */
	public Numer0nEatBite(Numer0nValue nv) {
		init();
		setNv(nv);
	}

	/**
	 * eatBite の判定を行うメソッド
	 * @param nv1　判定するNumer0n値1つ目
	 * @param nv2  判定するNumer0n値2つ目
	 * @return 判定結果
	 */
	public Numer0nEatBite div(Numer0nValue nv1, Numer0nValue nv2) {
		divEatBite(nv1,nv2);
		return this;
	}

	/**
	 * eatBite の判定を行うメソッド
	 * コンストラクターにてすでにNumer0n値を設定していた場合にのみ使用可能
	 * @throws IllegalArgumentException あらかじめNumer0n値を設定していない場合
	 * @param nv1 判定するNumer0n値
	 * @return 判定結果
	 */
	public Numer0nEatBite div(Numer0nValue nv1)/* throws Exception*/{
		if(!nvFlag) throw new IllegalArgumentException("事前にNumer0n値が入力されていません");
		divEatBite(nv,nv1);
		return this;
	}

	/**
	 * ゲーム終了したかどうか判定するメソッド
	 * @return true:終了 false:終了でない
	 */
	public boolean finFlag() {
		if(this.eatBite[0] == DIGIT) return true;
		else return false;
	}

	/**
	 * eatBite のgetter
	 * @return eatBite値
	 */
	public int[] getValue() {
		return this.eatBite;
	}

	/**
	 * eatBiteの値をStringに変換したもの
	 */
	public String toString() {
		return Arrays.toString(eatBite);
	}

	/**
	 * Numer0nValue のsetter
	 * 値をセットしたときnvFlagをtrueにする
	 * @param nv Numer0nValue値
	 */
	private void setNv(Numer0nValue nv) {
		this.nv = nv;
		nvFlag = true;
	}

	/**
	 * eatBite の判定 (実処理)
	 * @param nv1　判定するNumer0n値1つ目
	 * @param nv2  判定するNumer0n値2つ目
	 * @return 判定結果
	 */
	private int[] divEatBite(Numer0nValue nv1, Numer0nValue nv2) {
		// 初期化(判定するため)
		init();
		for(int i = 0; i < DIGIT; i++) {
			for(int j = 0; j < DIGIT; j++) {
				/* 値が一致したとき */
				if(nv1.getValue()[i]==nv2.getValue()[j]) {
					// Eat
					if(i==j) this.eatBite[0] ++;
					// Bite
					else this.eatBite[1] ++;
				}
			}
		}
		return this.eatBite;
	}

	private void init() {
		Arrays.fill(this.eatBite, 0);
	}

}
