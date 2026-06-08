package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//商品一覧用DAO　item_infoテーブルから商品情報を取得する
public class ShowAllDataDao{
	public List<ShowAllDataDto> Select() {
		
		//データベースへの接続情報
		//JDBCドライバの相対パス
		final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
		
		//接続先のデータベース
		final String JDBC_URL = System.getenv("DB_URL");
		
		//接続するユーザー名
		final String USER_NAME = System.getenv("DB_ID");
		
		//ユーザーパスワード
		final String USER_PASS = System.getenv("DB_PASS");
		
		//surveyテーブルのデータ全件抽出

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
		Connection        con = null ;   // Connection（DB接続情報）格納用変数
		PreparedStatement ps  = null ;   // PreparedStatement（SQL発行用オブジェクト）格納用変数
		ResultSet         rs  = null ;   // ResultSet（SQL抽出結果）格納用変数

		//抽出結果格納用DTOリスト
		List<ShowAllDataDto> dtoList = new ArrayList<ShowAllDataDto>();

		try {

			//-------------------------------------------
			//接続の確立（Connectionオブジェクトの取得）
			//-------------------------------------------
			con = DriverManager.getConnection(JDBC_URL, USER_NAME, USER_PASS);

			//-------------------------------------------
			//SQL文の送信 ＆ 結果の取得
			//-------------------------------------------

			//発行するSQL文の生成（SELECT）
			StringBuffer buf = new StringBuffer();
			buf.append(" SELECT               ");
			buf.append("  ITEM_ID ,           ");
			buf.append("  ITEM_NAME ,         ");
			buf.append("  PRICE ,             ");
			buf.append("  ITEM_CATEGORY ,     ");
			buf.append("  PUBLIC_STATUS ,     ");
			buf.append("  ITEM_IMAGE ,        ");
			buf.append("  ITEM_DESCRIPTION ,  ");
			buf.append("  RELEASE_DATE        ");
			buf.append(" FROM                 ");
			buf.append("  ITEM_INFO           ");
			buf.append(" ORDER BY             ");
			buf.append("  ITEM_ID DESC        ");

			ps = con.prepareStatement(buf.toString());
			rs = ps.executeQuery();

			//ResultSetオブジェクトからDTOリストに格納
			while (rs.next()) {
				ShowAllDataDto dto = new ShowAllDataDto();
				dto.setItemId(          rs.getString ( "ITEM_ID"          ) );
				dto.setItemName(        rs.getString ( "ITEM_NAME"        ) );
				dto.setPrice(           rs.getInt    ( "PRICE"            ) );
				dto.setItemCategory(    rs.getString ( "ITEM_CATEGORY"    ) );
				dto.setPublicStatus(    rs.getString ( "PUBLIC_STATUS"    ) );
				dto.setItemImage(       rs.getString ( "ITEM_IMAGE"       ) );
				dto.setItemDescription( rs.getString ( "ITEM_DESCRIPTION" ) );
				dto.setReleaseDate(     rs.getObject  ( "RELEASE_DATE",LocalDateTime.class ) );
				dtoList.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//-------------------------------------------
			//接続の解除
			//-------------------------------------------

			//ResultSetオブジェクトの接続解除
			if (rs != null) {    //接続が確認できている場合のみ実施
				try {
					rs.close();  //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

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

		//抽出結果を返す
		return dtoList;
	}
	
	//ItemDelete.jspの表示用
	public List<ShowAllDataDto> selectByIds(String[] ids) {
	    // 接続情報は既存のSelect()と同じものを使用してください
	    final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	    final String JDBC_URL = "jdbc:mysql://localhost/test_db?characterEncoding=UTF-8&serverTimezone=JST&useSSL=false";
	    final String USER_NAME = "test_user";
	    final String USER_PASS = "test_pass";

	    List<ShowAllDataDto> dtoList = new ArrayList<>();
	    
	    // 引数が空の場合は空のリストを返す
	    if (ids == null || ids.length == 0) return dtoList;

	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        Class.forName(DRIVER_NAME);
	        con = DriverManager.getConnection(JDBC_URL, USER_NAME, USER_PASS);

	        // SQL文の組み立て (WHERE ITEM_ID IN (?, ?, ?))
	        StringBuilder sql = new StringBuilder();
	        sql.append("SELECT ITEM_ID, ITEM_NAME, PRICE, ITEM_CATEGORY, PUBLIC_STATUS, ITEM_IMAGE, ITEM_DESCRIPTION, RELEASE_DATE ");
	        sql.append("FROM ITEM_INFO WHERE ITEM_ID IN (");
	        for (int i = 0; i < ids.length; i++) {
	            sql.append("?");
	            if (i < ids.length - 1) sql.append(",");
	        }
	        sql.append(")");

	        ps = con.prepareStatement(sql.toString());
	        
	        // パラメータのセット
	        for (int i = 0; i < ids.length; i++) {
	            ps.setString(i + 1, ids[i]);
	        }

	        rs = ps.executeQuery();

	        while (rs.next()) {
	            ShowAllDataDto dto = new ShowAllDataDto();
	            dto.setItemId(rs.getString("ITEM_ID"));
	            dto.setItemName(rs.getString("ITEM_NAME"));
	            dto.setPrice(rs.getInt("PRICE"));
	            dto.setItemCategory(rs.getString("ITEM_CATEGORY"));
	            dto.setPublicStatus(rs.getString("PUBLIC_STATUS"));
	            dto.setItemImage(rs.getString("ITEM_IMAGE"));
	            dto.setItemDescription(rs.getString("ITEM_DESCRIPTION"));
	            dto.setReleaseDate(rs.getObject("RELEASE_DATE", LocalDateTime.class));
	            dtoList.add(dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
			//-------------------------------------------
			//接続の解除
			//-------------------------------------------

			//ResultSetオブジェクトの接続解除
			if (rs != null) {    //接続が確認できている場合のみ実施
				try {
					rs.close();  //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

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
	    
	    return dtoList;
	}
	
	//DetailDisplay用のメソッド　選んだデータ一件を抽出
	public ShowAllDataDto selectId(String id) {
	    // 接続情報は既存のSelect()と同じものを使用してください
	    final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	    final String JDBC_URL = "jdbc:mysql://localhost/test_db?characterEncoding=UTF-8&serverTimezone=JST&useSSL=false";
	    final String USER_NAME = "test_user";
	    final String USER_PASS = "test_pass";
	    
	    ShowAllDataDto dtoResult = null;
	    
	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
        
        try {
	        Class.forName(DRIVER_NAME);
	        con = DriverManager.getConnection(JDBC_URL, USER_NAME, USER_PASS);

	        // SQL文の組み立て 
	        StringBuilder sql = new StringBuilder();
	        sql.append("SELECT ITEM_ID, ITEM_NAME, PRICE, ITEM_CATEGORY, PUBLIC_STATUS, ITEM_IMAGE, ITEM_DESCRIPTION, RELEASE_DATE ");
	        sql.append("FROM ITEM_INFO WHERE ITEM_ID = ? ");

	        ps = con.prepareStatement(sql.toString());
	        
	        // パラメータのセット
	        ps.setString(1 ,id);
	        
	        rs = ps.executeQuery();

	        if(rs.next()) {
	            dtoResult = new ShowAllDataDto();
	            dtoResult.setItemId(rs.getString("ITEM_ID"));
	            dtoResult.setItemName(rs.getString("ITEM_NAME"));
	            dtoResult.setPrice(rs.getInt("PRICE"));
	            dtoResult.setItemCategory(rs.getString("ITEM_CATEGORY"));
	            dtoResult.setPublicStatus(rs.getString("PUBLIC_STATUS"));
	            dtoResult.setItemImage(rs.getString("ITEM_IMAGE"));
	            dtoResult.setItemDescription(rs.getString("ITEM_DESCRIPTION"));
	            dtoResult.setReleaseDate(rs.getObject("RELEASE_DATE", LocalDateTime.class));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
			//-------------------------------------------
			//接続の解除
			//-------------------------------------------

			//ResultSetオブジェクトの接続解除
			if (rs != null) {    //接続が確認できている場合のみ実施
				try {
					rs.close();  //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

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

        
        return dtoResult;
	}
}
