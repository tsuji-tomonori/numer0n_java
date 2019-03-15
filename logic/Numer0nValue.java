package logic;

import java.util.Arrays;
import java.util.Random;

/**
 * Numer0nの値に関するクラス
 * 自動作成・チェックが可能
 * @author Tomonori Tsuji
 * @since 2019/02/20
 */
public class Numer0nValue {

	/** 桁数(今回は3で固定) */
	private final int DIGIT = 3;
	/** Numer0n にて使用する値 */
	private int[] value = new int[DIGIT];

	/**
	 * デフォルトコンストラクター
	 * 自動でNumer0nの値を作成し保持する
	 * この値は重複がないように, かつランダムに作成される
	 */
	public Numer0nValue() {
		int[] value = create(); // Numer0n値の作成
		// 配列のコピー
		System.arraycopy(value, 0, this.value, 0, value.length);
	}

	/**
	 * コンストラクター
	 * 指定したNumer0nの値を保持する
	 * この値については特にチェックはしない
	 * (チェックする場合はcheckメソッドを別途呼ぶこと)
	 * @param value Numer0nの値
	 */
	public Numer0nValue(int... value) {
		// 桁数チェック
		if(value.length != DIGIT) throw new IllegalArgumentException("桁数が不適切です");
		// 配列のコピー
		System.arraycopy(value, 0, this.value, 0, value.length);
	}

	public Numer0nValue(String value) {
		// 桁数チェック
		if(value.length() != DIGIT) throw new IllegalArgumentException("桁数が不適切です");
		for(int i = 0; i < value.length();i++) {
			this.value[i] = Integer.parseInt(""+value.charAt(i));
		}
		// 値のチェック
		if(!check()) throw new IllegalArgumentException("桁の値が重複しています");
	}

	/**
	 * Numer0nに適した値かチェックする
	 * 重複のチェック
	 * @return true:OK, false:NG
	 */
	public boolean check() {
		// 同じものをチェックしないようにずらす
		for(int i = 0; i < value.length; i++) {
			for(int j = 0; j < i; j++) {
				// 一致したとき = 重複がある場合
				if(value[i]==value[j]) return false;
			}
		}
		// 一回も一致しなかった = 重複がない
		return true;
	}

	/**
	 * 新しくNumer0nの値を作成する
	 * @return
	 */
	private int[] create() {

		/* 宣言等 */
		Random rnd = new Random(); // Randomクラスの生成(シード指定なし)
		boolean finFlag = false; // 終了フラグ(while文にて使用)
		boolean[] numFlag = new boolean[10]; // 重複しないように値を管理
		Arrays.fill(numFlag, true); // numFlag をすべて true で初期化
		int[] createValue = new int[DIGIT]; // 作成した値を保存する配列
		int count = 0; // 桁管理用カウンタ

		/* 値の作成 */
		while(!finFlag) { // finFlag が true になるまで(最後の桁の値が決まるまで)ループ
			int rand = rnd.nextInt(10); // 乱数により値を返す
			if(numFlag[rand]) { // 乱数の値が他の桁と重複していないとき
				createValue[count] = rand; // その値を作成する値(countで指定した値の桁)とする
				numFlag[rand] = false; // その値を今後使わないように設定
				count ++; // カウントを進める(桁が1つ下がる)
				// 指定された桁数まで数を作成したとき終了フラグを立てる
				if (count == DIGIT) finFlag = true;
			}
		}
		return createValue;
	}

	/**
	 * Numer0n値のgetter
	 * @return Numer0n値
	 */
	public int[] getValue() {
		return value;
	}

	/**
	 * Numer0n値をStringに変換したもの
	 * @return Numer0n値をString に変換したもの
	 */
	public String toString() {
		return Arrays.toString(value);
	}
}
