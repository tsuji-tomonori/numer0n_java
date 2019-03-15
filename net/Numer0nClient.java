/**
 *
 */
package net;

import java.net.Socket;
import java.util.Scanner;

/**
 * @author Tomonori Tsuji
 * @since 2019/03/07
 */
public class Numer0nClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		// 情報の取得
		Scanner sc = new Scanner(System.in);
		System.out.print("名前を入力してください:");
		String name = sc.nextLine();
		System.out.print("IPアドレスを入力してください:");
		String server = sc.nextLine();
		System.out.print("ポート番号を入力してください");
		int portNumber = scanInt();
		Socket socket = null;
		Numer0nClientTask task = new Numer0nClientTask(socket,name,server,portNumber);
		task.start();
	}

	/**
	 * 整数値の取得
	 * @return 整数値
	 */
	private static int scanInt() {
		boolean finFlag = false;
		Scanner sc = new Scanner(System.in);
		int num = 0;
		while(!finFlag) {
			try {
				num = Integer.parseInt(sc.nextLine());
				finFlag = true;
			}catch(NumberFormatException e) {
				System.out.println("数値以外の文字が含まれています");
			}
		}
		return num;
	}

}
