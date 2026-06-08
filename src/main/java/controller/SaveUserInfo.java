package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.SaveUserInfoBL;
import model.SaveUserInfoDto;



/**----------------------------------------------------------------------*
 *■■■SaveUserInfoクラス■■■
 *概要：サーブレット
 *詳細：リクエストを「user_info_test」テーブルに登録し、画面遷移する。
 *　　　＜遷移先＞登録成功：回答完了画面（finish.html）／登録失敗：エラー画面（error.html）
 *----------------------------------------------------------------------**/
public class SaveUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SaveUserInfo() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//レスポンス（出力データ）の文字コードを設定
		response.setContentType("text/html;charset=UTF-8");     //文字コードをUTF-8で設定
		//リクエスト（受信データ）の文字コードを設定
		request.setCharacterEncoding("UTF-8");                  //文字コードをUTF-8で設定
		
			//ログイン状態　登録処理と結果画面への遷移実行
			
		boolean succesFlg = true;  //成功フラグ（true:成功/false:失敗）

		//リクエストパラメータを取得
		String userId   = request.getParameter("USER_ID");//リクエスト：ユーザーID  
		String FName    = request.getParameter("FAMILY_NAME");//リクエスト：苗字
		String LName    = request.getParameter("LAST_NAME");//リクエスト：名前
		String password = request.getParameter("USER_PASS");//リクエスト：パスワード
		String eMail    = request.getParameter("USER_EMAIL");//リクエスト：メールアドレス
		
		//バリデーションチェック
		if( (!(validateChk(userId))) || (!(validateChk(FName))) || (!(validateChk(LName))) || (!(validateChk(password))) || (!(validateChk(eMail))) ) {
			response.sendRedirect("htmls/error.html");
			return;
		}
		

		//リクエストのデータ（SaveUserDto型）の作成
		SaveUserInfoDto dto = new SaveUserInfoDto();
		dto.setUserId( userId );
		dto.setFName( FName );
		dto.setLName( LName );
		dto.seteMail( eMail );
		dto.setPassWord( password );


		//アンケートデータをDBに登録
		SaveUserInfoBL logic = new SaveUserInfoBL();
		succesFlg          = logic.executeInsertInfo(dto);  //成功フラグ（true:成功/false:失敗）
			

		//成功/失敗に応じて表示させる画面を振り分ける
		if (succesFlg) {

			//成功した場合、回答完了画面（finish.html）を表示する
			response.sendRedirect("htmls/finish.html");

		} else {

			//失敗した場合、エラー画面（error.html）を表示する
			response.sendRedirect("htmls/error.html");
		}
	}
	
	//nullかどうかバリデーションチェック
	boolean validateChk (String chk) {
		
		boolean chkValidate = true;
		
		if ( chk == "" || chk.trim() .isEmpty()) {
			chkValidate = false;
		}
		return chkValidate;
	}


}