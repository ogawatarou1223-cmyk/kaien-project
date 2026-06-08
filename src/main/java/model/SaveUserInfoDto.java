package model;

/**----------------------------------------------------------------------*
 *■■■SaveUserInfoDtoクラス■■■
 *User_Info_testテーブル用のDto　SaveUserInfoDaoで使用
 *----------------------------------------------------------------------**/
public class SaveUserInfoDto {

	//----------------------------------------------------------------
	//フィールド
	//----------------------------------------------------------------
	private String userId;   //ゆーざーID
	private String FName;   //苗字
	private String LName;   //名前
	private String eMail;   //メール
	private String passWord;   //パスワード
	
	//----------------------------------------------------------------
	//getter/setter
	//----------------------------------------------------------------

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFName() {
		return FName;
	}
	public void setFName(String fName) {
		FName = fName;
	}
	public String getLName() {
		return LName;
	}
	public void setLName(String lName) {
		LName = lName;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
}
