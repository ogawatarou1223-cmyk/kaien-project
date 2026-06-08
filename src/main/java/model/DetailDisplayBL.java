package model;

//DetailDisplayの呼び出しビジネスロジック
public class DetailDisplayBL {

	//商品情報1件抽出・表示メソッド
	public ShowAllDataDto executeSelectDetail(String itemId) {

		//-------------------------------------------
		//データベースへの接続を実施
		//-------------------------------------------

		//DAOクラスをインスタンス化＆対象のユーザーデータを登録するよう依頼
		ShowAllDataDao dao = new ShowAllDataDao();
		ShowAllDataDto dto= dao.selectId(itemId); //一件抽出のメソッド後日用意

		return dto;
	}

}