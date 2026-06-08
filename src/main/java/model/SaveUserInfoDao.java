package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**----------------------------------------------------------------------*
 *■■■SaveUserInfoDaoクラス■■■
 *新規ユーザー登録用のDAO　user_info_testに登録
 *----------------------------------------------------------------------**/
public class SaveUserInfoDao {
	//-------------------------------------------
	//データベースへの接続情報 Tomcatの環境変数として登録
	//-------------------------------------------

	//JDBCドライバの相対パス
	//※バージョンによって変わる可能性があります（MySQL5系の場合は「com.mysql.jdbc.Driver」）
	final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

	//接続先のデータベース
	final String JDBC_URL    = System.getenv("DB_URL");

	//接続するユーザー名
	final String USER_ID     = System.getenv("DB_ID");

	//接続するユーザーのパスワード
	final String USER_PASS   = System.getenv("DB_PASS");


	//----------------------------------------------------------------
	//メソッド
	//----------------------------------------------------------------

	/**----------------------------------------------------------------------*
	 *■doInsertメソッド
	 *概要　：user_info_testテーブルに対象のアンケートデータを挿入する
	 *引数　：対象のユーザーデータ（SaveUserInfoDto型）
	 *戻り値：実行結果（真：成功、偽：例外発生）
	 *----------------------------------------------------------------------**/
	public boolean doInsert(SaveUserInfoDto dto) {

		//-------------------------------------------
		//JDBCドライバのロード
		//-------------------------------------------
		try {
			Class.forName(DRIVER_NAME);       //JDBCドライバをロード＆接続先として指定
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		//-------------------------------------------
		//SQL発行
		//-------------------------------------------

		//JDBCの接続に使用するオブジェクトを宣言
		//※finallyブロックでも扱うためtryブロック内で宣言してはいけないことに注意
		Connection        con = null ;   // Connection（DB接続情報）格納用変数
		PreparedStatement ps  = null ;   // PreparedStatement（SQL発行用オブジェクト）格納用変数

		//実行結果（真：成功、偽：例外発生）格納用変数
		//※最終的にreturnするため、tryブロック内で宣言してはいけないことに注意
		boolean isSuccess = true ;

		try {

			//-------------------------------------------
			//接続の確立（Connectionオブジェクトの取得）
			//-------------------------------------------
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);

			//-------------------------------------------
			//トランザクションの開始
			//-------------------------------------------
			//オートコミットをオフにする（トランザクション開始）
			con.setAutoCommit(false);

			//-------------------------------------------
			//SQL文の送信 ＆ 結果の取得
			//-------------------------------------------

			//発行するSQL文の生成（INSERT）
			StringBuffer buf = new StringBuffer();
			buf.append("INSERT INTO USER_INFO_TEST (  ");
			buf.append("  USER_ID ,           ");
			buf.append("  FAMILY_NAME ,       ");
			buf.append("  LAST_NAME ,         ");
			buf.append("  USER_PASS ,         ");
			buf.append("  USER_EMAIL  )       ");
			buf.append("  VALUES              ");
			buf.append(" ( ?,                 ");
			buf.append("   ?,                 ");
			buf.append("   ?,                 ");
			buf.append("   ?,                 ");
			buf.append("   ?  )               ");

			//PreparedStatementオブジェクトを生成＆発行するSQLをセット
			ps = con.prepareStatement(buf.toString());

			//パラメータをセット
			ps.setString( 1, dto.getUserId() ); //第1パラメータ：更新データ（ユーザーID）
			ps.setString( 2, dto.getFName() ); //第2パラメータ：更新データ（苗字）
			ps.setString( 3, dto.getLName() ); //第3パラメータ：更新データ（名前）
			ps.setString( 4, dto.getPassWord() ); //第4パラメータ：更新データ（パスワード）
			ps.setString( 5, dto.geteMail() ); //第5パラメータ：更新データ（メールアドレス）
			
			//SQL文の実行
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

			//実行結果を例外発生として更新
			isSuccess = false ;

		} finally {
			//-------------------------------------------
			//トランザクションの終了
			//-------------------------------------------
			if(isSuccess){
				//明示的にコミットを実施
				try {
					con.commit();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}else{
				//明示的にロールバックを実施
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			//-------------------------------------------
			//接続の解除
			//-------------------------------------------

			//PreparedStatementオブジェクトの接続解除
			if (ps != null) {    //接続が確認できている場合のみ実施
				try {
					ps.close();  //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			//Connectionオブジェクトの接続解除
			if (con != null) {    //接続が確認できている場合のみ実施
				try {
					con.close();  //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

		//実行結果を返す
		return isSuccess;
	}
}
