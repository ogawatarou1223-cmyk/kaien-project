package model;

public class ExecuteLoginBL{
	
	//selectUserInfoメソッド　ユーザーIDとパスワードを引数にuser_infoテーブルからデータを抽出
	public UserLoginInfoDto selectUserInfo(String userId, String password) {
		
		//DAOクラスインスタンス化
	    UserLoginInfoDao dao = new UserLoginInfoDao();
		//user_infoテーブルからユーザーデータを抽出するよう依頼
		UserLoginInfoDto dto = dao.doSelect(userId, password);
		
		//抽出したデータを戻す
		return dto;
	}
}