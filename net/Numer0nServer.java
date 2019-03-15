/**
 *
 */
package net;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Tomonori Tsuji
 * @since 2019/03/07
 */
public class Numer0nServer {

	/** このクラスのインスタンス */
	private static Numer0nServer instance;
	/** サーバソケット */
	private ServerSocket server;
	/** ゲームに参加している全ユーザーの動的配列 */
	private ArrayList<Numer0nClientUser> userList = new ArrayList<>();

	/**
	 * メインメソッド
	 * @param args[0] port number
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("start server port number is " + args[0]);
		Numer0nServer application = Numer0nServer.getInstance();
		application.start(Integer.parseInt(args[0]));
	}

	/**
	 * Numer0nServer のインスタンスのgetter
	 * シングルトン設計
	 * @return Numer0nServer のインスタンス
	 */
	public static Numer0nServer getInstance() {
		if(instance == null) instance = new Numer0nServer();
		return instance;
	}

	/**
	 * コンストラクター
	 */
	private Numer0nServer() {
		userList = new ArrayList<Numer0nClientUser>();
	}

	/**
	 * 新しいクライアントを接続する
	 * @param port　ポート番号
	 */
	private void start(int port) {
		try {
			server = new ServerSocket(port);
			while(!server.isClosed()) {
				// 新しいクライアントの接続を待つ
				Socket client = server.accept();
				// 新しいクライアントが接続したらユーザーオブジェクトを作成する
				Numer0nClientUser user = new Numer0nClientUser(client);
				addUser(user);
				sendMessage(user,Protocol.name,"接続完了");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ユーザの追加
	 * @param user 追加するユーザ
	 */
	public void addUser(Numer0nClientUser user) {
		if(userList.contains(user)) return;
		userList.add(user);
	}

	/**
	 * サーバに接続しているユーザのgettrr
	 * @param i 配列番号
	 * @return ユーザ情報
	 */
	public Numer0nClientUser getUserList(int i) {
		return userList.get(i);
	}

	/**
	 * ユーザリストの要素数を返すメソッド
	 * @return 要素数
	 */
	public int userListSize() {
		return userList.size();
	}

	/**
	 * ユーザの削除
	 * @param user 削除するユーザ
	 */
	public void removeUser(Numer0nClientUser user) {
		userList.remove(user);
	}

	/**
	 * メッセージを送信
	 * @param user 送信相手のNumer0nClientUser変数
	 * @param message 送信内容
	 */
	public void sendMessage(Numer0nClientUser user, Protocol pr, String value) {
		try {
			// 接続されたソケットの出力ストリームを取得し, データ出力ストリームを連結
			OutputStream os = user.getSocket().getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);

			// 送信
			String message = pr + "," + value;
			dos.writeUTF(message);
			dos.flush();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
