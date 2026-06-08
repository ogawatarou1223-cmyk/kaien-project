package model;

import java.util.List;


//ShowAllDataの呼び出しビジネスロジック
public class ShowAllDataBL {

	//商品情報全件抽出・表示メソッド
	public List<ShowAllDataDto> executeSelectSurvey() {

		//-------------------------------------------
		//データベースへの接続を実施
		//-------------------------------------------

		//DAOクラスをインスタンス化＆対象のユーザーデータを登録するよう依頼
		ShowAllDataDao dao = new ShowAllDataDao();
		List<ShowAllDataDto> dtoList= dao.Select();

		return dtoList;
	}

}