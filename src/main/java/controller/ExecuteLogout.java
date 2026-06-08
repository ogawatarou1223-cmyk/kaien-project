package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.UserLoginInfoDto;

//ExecuteLogoutクラス　ログアウトのHTML文を出力
public class ExecuteLogout extends HttpServlet{
	 private static final long serialVersionUID = 1L; 
	
	public ExecuteLogout() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException ,IOException{
				
		//セッションからユーザーデータを取得
		HttpSession session = request.getSession(false);
		UserLoginInfoDto UserInfoOnS = (UserLoginInfoDto)session.getAttribute("LOGIN_INFO");
		
		//ログイン状態によって表示画面を振り分け セッションからユーザーデータを取得できるかどうかで判断
		
		//ログイン済み：ログアウト処理を実施
		if(UserInfoOnS != null) {
			
			//ログアウトに伴いセッション情報を破棄
			session.invalidate();
			
			//Viewにフォワード
			RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/Logout.jsp");
			dispatch.forward(request, response);
			
		}else {
			
			//未ログイン：ログイン画面へ転送
			response.sendRedirect("Login");
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException ,IOException{
		doGet(request, response);
	}
}