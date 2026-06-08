package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//user_info_testテーブル用のDAO　ログイン処理で使用
public class UserLoginInfoDao{
	
	//データベースへの接続情報 Tomcatの環境変数として登録
	//JDBCドライバの相対パス
	final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	
	//接続先のデータベース
	final String JDBC_URL = System.getenv("DB_URL");
	
	//接続するユーザー名
	final String USER_NAME = System.getenv("DB_ID");
	
	//ユーザーパスワード
	final String USER_PASS = System.getenv("DB_PASS");
	
	//ここから下メソッド
	//doSelect user_info_testのテーブルからユーザー情報を抽出　引数はユーザーIDとパスワード　戻り値はDTOのデータ
	public UserLoginInfoDto doSelect(String inputUserId , String inputPassWord){
		
		//JDBCドライバのロード
		try {
			Class.forName(DRIVER_NAME);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		
		//SQL発行
		//JDBCの接続に使うオブジェクトの宣言
		Connection con = null; //Connection(DB接続情報)格納用変数
		PreparedStatement ps = null; //PreparedStatement（SQL発酵用オブジェ）格納用変数
		ResultSet rs = null; //ResultSet(SQL抽出結果)格納用変数
		
		//抽出データ（DTO型）格納用変数
		UserLoginInfoDto dto = new UserLoginInfoDto();
		
		try {
			
			//Connectionオブジェを取得し、接続を確立
			con = DriverManager.getConnection(JDBC_URL, USER_NAME, USER_PASS);
			
			//SQL文の送信と結果の取得
			
			//発行するSQL文の生成
			StringBuffer buf = new StringBuffer();
			
			buf.append(" SELECT ");
			buf.append("  USER_ID , ");
			buf.append("  FAMILY_NAME , ");
			buf.append("  LAST_NAME , ");
			buf.append("  USER_PASS , ");
			buf.append("  USER_EMAIL , ");
			buf.append("  USER_ROLE ");
			buf.append(" FROM ");
			buf.append("  USER_INFO_TEST ");
			buf.append(" WHERE ");
			buf.append("  USER_ID = ? AND "); //第1パラメータ
			buf.append("  USER_PASS = ? "); //第２パラメータ
		
			
			//PreparedmentStatementオブジェを生成＆発行するSQLをセット
			ps = con.prepareStatement(buf.toString());
			
			//パラメータをセット
			ps.setString(1, inputUserId); //第１パラメータ：入力されたユーザーID
			ps.setString(2, inputPassWord); //第２パラメータ：入力されたパスワード
			
			//SQLの送信と戻り値としてSQLの抽出結果を取得
			rs = ps.executeQuery();
			
			//ResultSetオブジェからユーザーデータを抽出
			if(rs.next()) {
				//ResultSetから１行分のレコードの情報をDTOに登録
				dto.setUserId( rs.getString("USER_ID"));
				dto.setFamilyName(rs.getString("FAMILY_NAME"));
				dto.setLastName( rs.getString("LAST_NAME"));
				dto.seteMail( rs.getString("USER_EMAIL"));
				dto.setPassWord(rs.getString("USER_PASS"));
				dto.setRole( rs.getInt("USER_ROLE"));
			}
		}catch(SQLException e){
			e.printStackTrace();
			
		}finally {
			
			//接続の解除
			
			//ResultSetオブジェの接続解除
			if(rs != null) {
				try {
					rs.close(); //接続解除
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			
			//PreparedStatementオブジェの接続解除
			if(ps != null) {
				try {
					ps.close(); //接続解除
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			
			//Connectionオブジェの接続解除
			if(con != null) {
				try {
					con.close(); //接続解除
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		return dto;
	}
}