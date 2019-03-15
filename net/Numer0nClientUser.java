/**
 *
 */
package net;

import java.net.Socket;

/**
 * @author Tomonori Tsuji
 * @since 2019/03/07
 */
public class Numer0nClientUser {

	/** ソケット */
	private Socket socket;
	/** ユーザ名 */
	private String name;

	/**
	 * コンストラクタ
	 * 接続用
	 * @param socket 接続するソケット
	 */
	public Numer0nClientUser(Socket socket) {
		this.socket = socket;
		Numer0nServerTask process = new Numer0nServerTask(getSocket(),this);
		Thread thread = new Thread(process);
		thread.start();
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * socket getter
	 * @return socket
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * name getter
	 * @return name
	 */
	public String getName() {
		return name;
	}
}
