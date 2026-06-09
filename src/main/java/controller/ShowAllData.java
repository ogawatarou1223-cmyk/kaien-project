package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.ShowAllDataBL;
import model.ShowAllDataDto;
import model.UserLoginInfoDto;

//ShowAllData.java  概要:item_infoテーブルの全件データを表示させる

public class ShowAllData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShowAllData() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	 throws ServletException, IOException {

		//レスポンス（出力データ）の文字コードを設定
		response.setContentType("text/html;charset=UTF-8");
		
		//セッションからユーザー情報取得
		HttpSession session              = request.getSession(); 
		UserLoginInfoDto userInfoOnS = (UserLoginInfoDto) session.getAttribute("LOGIN_INFO");
		
		//ログイン状態によって表示画面を振り分け  セッション情報にユーザー情報あるかどうかで判定
		if(userInfoOnS != null) {			

			//「item_info」テーブルのデータを全件抽出
			List<ShowAllDataDto> list  = new ArrayList<ShowAllDataDto>();
			
			ShowAllDataBL logic = new ShowAllDataBL();
			
			list = logic.executeSelectSurvey();
			
			//商品一覧リストをリクエストスコープにセット
			request.setAttribute("SHOW_ALL_DATA", list);
			
			//管理者権限判定のためにリクエストスコープにユーザー情報をセット
			request.setAttribute("USER_INFO", userInfoOnS);
			
			//viewにフォワード
			RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/ShowAllData.jsp");
			dispatch.forward(request, response);
		
		}else {
			//ログインしてないのでログイン画面へ
			response.sendRedirect("Login");
		
		}
	}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	 throws ServletException, IOException {
		doGet(request, response);
	}
	
}