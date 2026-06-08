package model;

/**----------------------------------------------------------------------*
 *■■■SaveUserInfoBLクラス■■■
 *SaveUserInfoの呼び出しビジネスロジック
 *----------------------------------------------------------------------**/
public class SaveUserInfoBL {

	/**----------------------------------------------------------------------*
	 *■executeInsertInfoメソッド
	 *概要　：対象のユーザーデータを登録する
	 *引数　：対象のユーザーデータ（SaveUserInfoDto型）
	 *戻り値：DB操作成功フラグ（true:成功/false:失敗）
	 *----------------------------------------------------------------------**/
	public boolean executeInsertInfo(SaveUserInfoDto dto) {

		boolean successInsert = true ;  //DB操作成功フラグ（true:成功/false:失敗）

		//-------------------------------------------
		//データベースへの接続を実施
		//-------------------------------------------

		//DAOクラスをインスタンス化＆対象のユーザーデータを登録するよう依頼
		SaveUserInfoDao dao = new SaveUserInfoDao();
		successInsert = dao.doInsert(dto);

		return successInsert;
	}

}
