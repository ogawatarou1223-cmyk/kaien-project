package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.UserLoginInfoDto;



//ログイン用のHTMLを出力

public class Login extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public Login() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		response.setContentType("text/html;charset=UTF-8");//文字コードをUTF-8に設定
		
		//セッションからログイン情報を取得
		HttpSession session = request.getSession();
		UserLoginInfoDto UserInfoOnS = (UserLoginInfoDto)session.getAttribute("LOGIN_INFO");
		
		//ログイン状態によって表示画面を振り分け
		if(UserInfoOnS != null) {
			//ログイン済み：商品一覧画面に転送
			response.sendRedirect("ShowAllData");
			
		}else {
			//viewにフォワード　
			RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/Login.jsp");
			dispatch.forward(request, response);
		}
	}
	
	protected void doPost (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
	
}