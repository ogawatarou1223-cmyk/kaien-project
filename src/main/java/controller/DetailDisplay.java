package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.DetailDisplayBL;
import model.ShowAllDataDto;
import model.UserLoginInfoDto;

//ShowAllData.java  概要:item_infoテーブルの全件データを表示させる

public class DetailDisplay extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DetailDisplay() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	 throws ServletException, IOException {

		//レスポンス（出力データ）の文字コードを設定
		response.setContentType("text/html;charset=UTF-8");
		
		//セッションからユーザー情報取得
		HttpSession session = request.getSession(); 
		UserLoginInfoDto userInfoOnS = (UserLoginInfoDto) session.getAttribute("LOGIN_INFO");
		
		//ログイン状態によって表示画面を振り分け  セッション情報にユーザー情報あるかどうかで判定
		if(userInfoOnS != null) {			
					
			// 一覧画面のリンクからitemIdを取得する
			    String itemId = request.getParameter("itemId");

			    DetailDisplayBL logic = new DetailDisplayBL();
			    // 1件分のデータを取得
			    ShowAllDataDto dto = logic.executeSelectDetail(itemId);
			    
			    // リクエストスコープに1件分のデータをセット
	            request.setAttribute("DETAIL_DISPLAY", dto);
		        request.setAttribute("USER_INFO", userInfoOnS);
			    
			    RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/DetailDisplay.jsp");
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