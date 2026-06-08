package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.ItemDeleteBL;
import model.ItemDeleteDto;
import model.UserLoginInfoDto;

/*ExecuteDelete
 * データ消去画面（HTML)を表示
 */
public class ExecuteDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteDelete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//レスポンス（出力データ）の文字コードを設定
		response.setContentType("text/html;charset=UTF-8");  //文字コードをUTF-8で設定
		
		//セッションから情報を取得
		HttpSession session = request.getSession();
		UserLoginInfoDto InfoOnS = (UserLoginInfoDto)session.getAttribute("LOGIN_INFO");
		
        //ログイン状態によって画面を振り分け
		if(InfoOnS != null) {
			//ログイン済み：アンケート入力画面に転送
			//viewにフォワード
			RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/itemDelete.jsp");
			dispatch.forward(request, response);

			
		}else {
			//ログインしてないのでログイン画面へ
			response.sendRedirect("Login");
		}
		
	}
	
	
	//リクエストデータをDBに登録
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//レスポンス（出力データ）の文字コードを設定
		response.setContentType("text/html;charset=UTF-8");     //文字コードをUTF-8で設定
		//リクエスト（受信データ）の文字コードを設定
		request.setCharacterEncoding("UTF-8");                  //文字コードをUTF-8で設定
		
		String[] selectedIds = request.getParameterValues("deleteIds");
		ItemDeleteBL logic = new ItemDeleteBL();
		boolean success = true;
		
		//結果によって表示画面を振り分け
		if(selectedIds != null) {
			for(String id : selectedIds) {
				ItemDeleteDto dto = new ItemDeleteDto();
				dto.setItemId(id);
				
				if(!logic.executeDeleteSurvey(dto)) {
					success = false;
					break;
				}
				
			}
		}
		
		//削除成功：削除終了画面へ
		if (success) {
			response.sendRedirect("htmls/ItemDelete-finish.html");
		//削除失敗：エラー画面へ
		}else {
			response.sendRedirect("htmls/ItemDelete-error.html");
		}

	}
}
