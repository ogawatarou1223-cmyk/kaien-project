package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

/**----------------------------------------------------------------------*
 *■■■SurveyDaoクラス■■■
 *概要：DAO（「survey」テーブル）
 *----------------------------------------------------------------------**/
public class ItemInsertDao {
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
	 *概要　：「survey」テーブルに対象のアンケートデータを挿入する
	 *引数　：対象のアンケートデータ（SurveyDto型）
	 *戻り値：実行結果（真：成功、偽：例外発生）
	 *----------------------------------------------------------------------**/
	public boolean doInsert(ItemInsertDto dto) {

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
			buf.append(" INSERT INTO ITEM_INFO (  ");
			buf.append("   ITEM_ID,            ");
			buf.append("   ITEM_NAME,          ");
			buf.append("   PRICE,              ");
			buf.append("   ITEM_CATEGORY,      ");
			buf.append("   PUBLIC_STATUS,      ");
			buf.append("   ITEM_IMAGE,         ");
			buf.append("   ITEM_DESCRIPTION,   ");
			buf.append("   RELEASE_DATE    )   ");
			buf.append("   VALUES              ");
			buf.append("  ( ?,                 ");
			buf.append("    ?,                 ");
			buf.append("    ?,                 ");
			buf.append("    ?,                 ");
			buf.append("    ?,                 ");
			buf.append("    ?,                 ");
			buf.append("    ?,                 ");
			buf.append("    ?                  ");
			buf.append(" )                     ");

			//PreparedStatementオブジェクトを生成＆発行するSQLをセット
			ps = con.prepareStatement(buf.toString());

			//パラメータをセット
			ps.setString( 1, dto.getItemId() ); //第1パラメータ：登録データ（ID）
			ps.setString( 2, dto.getItemName() ); //第2パラメータ：更新データ（商品名）
			ps.setInt(    3, dto.getPrice() ); //第3パラメータ：更新データ（価格）
			ps.setString( 4, dto.getItemCategory() ); //第4パラメータ：更新データ（商品カテゴリ）
			ps.setString( 5, dto.getPublicStatus() ); //第5パラメータ：更新データ（公開状況）
			ps.setString( 6, dto.getItemImage() ); //第6パラメータ：更新データ（商品画像）
			ps.setString( 7, dto.getItemDescription() ); //第7パラメータ：更新データ（商品説明）
			if (dto.getReleaseDate() != null) {
			    ps.setTimestamp(8, Timestamp.valueOf(dto.getReleaseDate()));
			} else {
			    ps.setNull(8, Types.TIMESTAMP);
			} //第8パラメータ：更新データ（発売日時）

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