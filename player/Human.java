/**
 *
 */
package player;

import java.util.Scanner;

import logic.Numer0nEatBite;
import logic.Numer0nValue;

/**
 * @author TomonoriTsuji
 * @since 2019/02/22
 */
public class Human implements IPlayer{

	private Numer0nValue nv;
	private String name;
	Scanner sc = new Scanner(System.in);

	public Human() {
		System.out.println("ユーザー名を入力してください:");
		this.name = sc.nextLine();
	}

	public Human(String name) {
		this.name = name;
	}

	@Override
	public Numer0nValue preprocessing() {
		// TODO 自動生成されたメソッド・スタブ
		this.nv = scanNumer0nValue("【"+ this.name+"】あなたの値を入力してください:");
		return this.nv;
	}

	@Override
	public Numer0nValue call(Numer0nEatBite eb) {
		// TODO 自動生成されたメソッド・スタブ
		return scanNumer0nValue("【"+this.name+"】Call値を入力してください:");
	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return this.name;
	}

	@Override
	public Numer0nValue getAns() {
		// TODO 自動生成されたメソッド・スタブ
		return this.nv;
	}

	private Numer0nValue scanNumer0nValue(String firstMessage) {
		boolean finFlag = false;
		Numer0nValue nvs = null;

		System.out.print(firstMessage);
		while(!finFlag) {
			try {
				nvs = new Numer0nValue(sc.next());
				if(nvs.check()) finFlag = true;
				else {
					// 重複がある or 桁数が少ない
					System.out.println("桁の値が重複しています");
					System.out.print("再度あなたの値を入力してください:");
				}
			}catch(IllegalArgumentException e) {
				// 入力ないように数値以外が含まれている場合
				System.out.println("桁数が不適切 or 数値以外が入力されています");
				System.out.print("再度あなたの値を入力してください:");
			}
		}
		return nvs;
	}

	@Override
	public Numer0nEatBite div(Numer0nValue nv) {
		// TODO 自動生成されたメソッド・スタブ
		return new Numer0nEatBite().div(nv, getAns());
	}

}
