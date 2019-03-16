/**
 *
 */
package net;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

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
	/** ゲーム中の部屋の動的配列 */
	private ArrayList<Numer0nRoom> roomList = new ArrayList<>();
	/** 部屋作成に使用する変数 部屋を作成する人数だけ人が集まったときtrue */
	private boolean roomFlag = false;
	/** 部屋に参加待ちのユーザー */
	private Numer0nClientUser waitUser;

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
		roomList = new ArrayList<Numer0nRoom>();
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
				sendMessage(user,Protocol.name,"接続完了","");
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
	 * 部屋の追加
	 * @param room 追加する部屋
	 */
	public void addRoomList(Numer0nRoom room) {
		if(roomList.contains(room)) return;
		roomList.add(room);
		System.out.println("room add:"+room.getRoomID());
	}

	public Numer0nRoom getRoomList(String roomID) {
		for(int i = 0; i < roomListSize(); i++) {
			if(getRoomList(i).getRoomID().equals(roomID))
				return getRoomList(i);
		}
		throw new IllegalArgumentException("部屋が見つかりません");
	}

	/**
	 * 部屋情報の getter
	 * @param i 配列番号
	 * @return 部屋情報
	 */
	public Numer0nRoom getRoomList(int i) {
		return roomList.get(i);
	}

	/**
	 * 作成された部屋の数を返すメソッド
	 * @return 作成された部屋の数
	 */
	public int roomListSize() {
		return roomList.size();
	}

	/**
	 * 指定した部屋を削除するメソッド
	 * @param room 削除する部屋情報
	 */
	public void removeRoom(Numer0nRoom room) {
		roomList.remove(room);
	}

	/**
	 * 部屋を建てる
	 * @param user 部屋に入るユーザー情報
	 * @return 部屋を建てたかどうか true:部屋を建てた false:部屋を建てなかった
	 */
	public boolean createRoom(Numer0nClientUser user) {
		// 待機ユーザーがいないとき
		// もう１人部屋に入るまで待つ
		if(!this.roomFlag) {
			System.out.println("待機中:"+user.getName());
			this.waitUser = user;
			this.roomFlag = true;
			return false;
		}
		// 待機ユーザーがいるとき
		// 部屋を建てる
		else {
			// ユニークなIDを作成
			UUID uuid = UUID.randomUUID();
			// 部屋情報登録
			Numer0nRoom room = new Numer0nRoom(uuid.toString(),waitUser,user);
			addRoomList(room);
			// 参加者にゲーム開始通知
			sendMessage(waitUser,Protocol.reqPreProcessing,user.getName(),room.getRoomID());
			sendMessage(user,Protocol.reqPreProcessing,waitUser.getName(),room.getRoomID());
			System.out.println("部屋を建てました:"+room.getRoomID());
			this.roomFlag = false;
			return true;
		}
	}

	public void sendMessageToRoom(Numer0nRoom room, Protocol pr, String value, String id) {
		sendMessage(room.getFirst(),pr,value,id);
		sendMessage(room.getLate(),pr,value,id);
	}

	/**
	 * メッセージを送信
	 * @param user 送信相手のNumer0nClientUser変数
	 * @param message 送信内容
	 */
	public void sendMessage(Numer0nClientUser user, Protocol pr, String value, String id) {
		try {
			// 接続されたソケットの出力ストリームを取得し, データ出力ストリームを連結
			OutputStream os = user.getSocket().getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);

			// 送信
			String message = pr + "," + value + "," + id;
			dos.writeUTF(message);
			dos.flush();
			System.out.println("<("+user.getName()+")"+pr+" : "+value+" : "+id);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
