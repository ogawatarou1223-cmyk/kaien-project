package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



/**----------------------------------------------------------------------*
 *■■■NewMemberRegestrationクラス■■■
 *概要：サーブレット
 *詳細：HTML文書（新規会員登録）を出力する。
 *----------------------------------------------------------------------**/
public class NewMemberRegestration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public NewMemberRegestration() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//レスポンス（出力データ）の文字コードを設定
		response.setContentType("text/html;charset=UTF-8");  //文字コードをUTF-8で設定
		
   
		//viewにフォワード
		RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/newMemberRegestration.jsp");
		dispatch.forward(request, response);
			
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
