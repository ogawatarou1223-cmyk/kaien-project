package model;

/**----------------------------------------------------------------------*
 *■■■ItemUpdateBLクラス■■■
 *ItemUpdateの呼び出しビジネスロジック
 *----------------------------------------------------------------------**/
public class ItemUpdateBL {

	/**----------------------------------------------------------------------*
	 *■executeUpdateInfoメソッド
	 *概要　：対象の商品情報を更新する
	 *引数　：対象の商品情報（ItemUpdateDto型）
	 *戻り値：DB操作成功フラグ（true:成功/false:失敗）
	 *----------------------------------------------------------------------**/
	public boolean executeUpdateInfo(ItemUpdateDto dto) {

		boolean successInsert = true ;  //DB操作成功フラグ（true:成功/false:失敗）

		//-------------------------------------------
		//データベースへの接続を実施
		//-------------------------------------------

		//DAOクラスをインスタンス化＆対象のユーザーデータを登録するよう依頼
		ItemUpdateDao dao = new ItemUpdateDao();
		successInsert = dao.doUpdate(dto);

		return successInsert;
	}

}
