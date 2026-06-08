package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.ExecuteLoginBL;
import model.UserLoginInfoDto;

//user_info_testテーブルからユーザーの入力と合致するデータを抽出、リダイレクトする
//リダイレクト先は　合致データあり：ShowAllData　合致データなし：Login

public class ExecuteLogin extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public ExecuteLogin() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		//レスポンス（出力データ）の文字コード設定
		response.setContentType("text/html;charset=UTF-8");
		//リクエスト（受信データ）の文字コード設定
		request.setCharacterEncoding("UTF-8");
		
		//セッションからユーザーデータ取得
		HttpSession session = request.getSession();
		UserLoginInfoDto userInfoOnS = (UserLoginInfoDto)session.getAttribute("LOGIN_INFO");
		
		//ログイン状態によって表示画面を振り分け
		//ログイン状態はセッションからユーザーデータを取得できたかどうかで判断
		
		//ログイン状態：アンケート入力画面に転送
		if(userInfoOnS != null) {
			
			//商品一覧ページへ遷移
			response.sendRedirect("ShowAllData");
			
		//未ログイン：ログイン処理の実施
		}else { 
			
			boolean successFlg = true; //ログイン成功フラグ
			
			if(!(checkPrmName(request.getParameter("USER_ID"))  &&
					checkPrmPassword(request.getParameter("USER_PASS")) )) {
				
				successFlg = false;
				
			}else {
			
				//リクエストパラメータからユーザー入力値を取得
				String userId = request.getParameter("USER_ID"); //USER_IDをリクエスト
				String passWord = request.getParameter("USER_PASS"); //PASSWORDをリクエスト
			
				//user_infoテーブルからユーザー入力値と合致するデータを抽出(UserInfoDto型)ない場合nullのDTOを取得
				ExecuteLoginBL logic = new ExecuteLoginBL();
				UserLoginInfoDto dto = logic.selectUserInfo(userId, passWord);
				
				//デバッグ用
				System.out.println("DEBUG: Input ID = " + userId);
				System.out.println("DEBUG: DB Result ID = " + dto.getUserId());
			
				//ユーザーデータの抽出成功/失敗に応じて表示させる画面の振り分け
				if(dto.getUserId() == null) {
				
					successFlg = false;
					
				}else {
				
					//DBからのデータをセッションにセット
					session.setAttribute("LOGIN_INFO", dto);
				}
			}
			
			//ログイン状態かどうかで画面振り分け
			if(successFlg) {
				
				//商品一覧ページへ遷移
				response.sendRedirect("ShowAllData");
				
			}else {
				
				response.sendRedirect("Login");
			}
			
		}
		
	}
	
	//名前の入力値チェック
	private boolean checkPrmName(String pr) {
		
		boolean chkResult = true;
		
		//入力値がNULLか空白でエラーに
		if(pr == null || pr.equals("")) {
			chkResult = false;
		}
		return chkResult;
	}
	
	//パスワードの入力値チェック
	private boolean checkPrmPassword(String pr) {
		
		boolean chkResult = true;
		
		//入力値がNULLか空白でエラーに
		if(pr == null || pr.equals("")) {
			chkResult = false;
		}
		return chkResult;
	}
}