package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.ShowAllDataDao;
import model.ShowAllDataDto;
import model.UserLoginInfoDto;

/*ItemDelete
 * データ消去画面（HTML)を表示
 */
public class ItemDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ItemDelete() {
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
		
		String[] selectedIds = request.getParameterValues("selectedData");

		//パラメータのバリデーションチェック
		if(selectedIds == null || selectedIds.length == 0 )  {

			response.sendRedirect("ShowAllData");
			System.out.println("DEBUG: バリデーションNG"); 
			return;
			//バリデーションNGの場合

		}
		
		ShowAllDataDao dao = new ShowAllDataDao();
	    List<ShowAllDataDto> deleteList = dao.selectByIds(selectedIds);
		
		request.setAttribute("DELETE_LIST", deleteList);
		
		//jspファイルへフォワード
		RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/itemDelete.jsp");
		dispatch.forward(request, response);
	}

}