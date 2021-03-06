/**
 *
 */
package net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import logic.Numer0nEatBite;
import logic.Numer0nValue;
import player.Human;
import player.IPlayer;

/**
 * @author Tomonori Tsuji
 * @since 2019/03/10
 */
public class Numer0nClientTask{

	/** ソケット */
	private Socket socket;
	/** IPアドレス */
	private String ipAddress;
	/** ポート番号 */
	private int portNumber;
	/** ユーザ名 */
	private String name;
	/** 部屋ID */
	private String id;
	/***/
	private IPlayer player;
	/** EatBite */
	private Numer0nEatBite eb = new Numer0nEatBite();

	public Numer0nClientTask(Socket socket, String name, String ipAddress, int portNumber) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.socket = socket;
		this.ipAddress = ipAddress;
		this.portNumber = portNumber;
		this.name = name;
	}

	protected void start() {
		// TODO 自動生成されたメソッド・スタブ
		String server = this.ipAddress;
		try{
			// ソケットを作成
			socket = new Socket();
			// 指定されたホスト名（IPアドレス）とポート番号でサーバに接続する
			socket.connect(new InetSocketAddress(server, this.portNumber));
			System.out.println("接続開始");
			while(!socket.isClosed()){
				//受信
				receiveMessage();
			}
		}catch(Exception e){
			System.out.println("接続に失敗しました");
		}
	}

	/**メッセージ受信*/
	public void receiveMessage(){
		// 接続されたソケットの入力ストリームを取得し，データ入力ストリームを連結
		InputStream is = null;
		String message = "";
		try {
			is = this.socket.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			message =  dis.readUTF();
			analyzeMessage(message);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			//e.printStackTrace();
			//終了
		}
	}

	private void analyzeMessage(String message) {
		String[] buff = message.split(",",3);
		String protocol = buff[0];
		String value = buff[1];
		String id;
		if(buff.length < 3){
			id = "";
		}
		else{
			id = buff[2];
		}
		processingMessage(Protocol.valueOf(protocol),value,id);
	}

	private void processingMessage(Protocol pr, String value, String id) {
		System.out.println(">"+pr+" : "+value+" : "+id);
		switch(pr) {
			case name:
				sendMessage(Protocol.name,this.name,"");
				System.out.println("サーバに接続しました");
				break;
			case reqPreProcessing:
				this.id = id;
				System.out.println("部屋ID" + this.id);
				this.player = new Human(this.name);
				this.player.preprocessing();
				sendMessage(Protocol.resPreProcessing,"",this.id);
				System.out.println("対戦相手:"+value);
				System.out.println("準備中...");
				break;
			case start:
				System.out.println("game start");
				break;
			case reqCall:
				sendMessage(Protocol.resCall,this.player.call(this.eb).toValue(),this.id);
				break;
			case reqDiv:
				sendMessage(Protocol.resDiv,player.div(new Numer0nValue(value)).toValue(),this.id);
				break;
			case info:
				System.out.println("info:"+value);
				break;
			case fin:
				System.out.println("fin");
				break;
			default:
				System.out.println("定義してません");
				break;
		}

	}

	/**
	 * メッセージ送信
	 * @param message 送信内容
	 * */
	public void sendMessage(Protocol pr, String value, String id){
		// 接続されたソケットの出力ストリームを取得し，データ出力ストリームを連結
		OutputStream os = null;
		try {
			os = this.socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			// データの送信（ソケットへ書き出す）
			String message = pr + "," + value + "," + id;
			dos.writeUTF(message);
			dos.flush();
			System.out.println("<"+pr+" : "+value+" : "+id);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			//e.printStackTrace();
			//サーバーが落ちている
			try {
				close(1);
			} catch (IOException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
		}

	}

	/**サーバーから切断*/
	public void close(int flag) throws IOException{
		this.socket.close();
		switch(flag){
			case 0:
				System.out.println(">サーバから切断しました\n");
				break;
			case 1:
				System.out.println(">サーバから切断されました\n");
				break;
			default:
				System.out.println(">error:ChatClientTaskのcloseメソッド引数が不適切\n");
				break;
		}

	}



}
