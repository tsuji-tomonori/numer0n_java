/**
 *
 */
package net;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author Tomonori Tsuji
 * @since 2019/03/07
 */
public class Numer0nServerTask implements Runnable {

	/** ソケット */
	private Socket socket;
	/** Numer0nServerクラスのインスタンス */
	private Numer0nServer application = Numer0nServer.getInstance();
	/** ユーザー情報 */
	private Numer0nClientUser user;

	/**
	 * コンストラクター
	 * @param socket 接続するソケット
	 */
	public Numer0nServerTask(Socket socket, Numer0nClientUser user) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.socket = socket;
		this.user = user;
	}

	/**
	 * バックグラウンド処理
	 * データを受信する
	 */
	@Override
	public void run() {
		// TODO 自動生成されたメソッド・スタブ
		try {
			// 接続されたソケットの入力ストリームを取得し, データ入力を連結
			InputStream is = this.socket.getInputStream();
			DataInputStream dis = new DataInputStream(is);

			// ソケットを閉じるまでループ
			while(!this.socket.isClosed()) {
				// データの受信
				String message = dis.readUTF();
				// メッセージの処理
				analyzeMessage(message);
			}
		}catch(IOException e) {
			// 切断
			this.application.removeUser(user);
			processingMessage(Protocol.exit,user.getName());
		}

	}

	private void analyzeMessage(String message) {
		String[] buff = message.split(",",2);
		String protocol = buff[0];
		String value;
		if(buff.length < 2){
			value = "";
		}
		else{
			value = buff[1];
		}
		processingMessage(Protocol.valueOf(protocol),value);
	}

	private void processingMessage(Protocol pr, String value) {
		switch(pr) {
		case name:
			user.setName(value);
			System.out.println("name:"+user.getName());
			application.createRoom(user);
			break;
		default:
			System.out.println("定義してません");
			break;
		}
	}


}
