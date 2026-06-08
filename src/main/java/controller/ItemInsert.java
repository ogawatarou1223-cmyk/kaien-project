package controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import model.ItemInsertBL;
import model.ItemInsertDto;
import model.UserLoginInfoDto;

//マルチパートリクエストを許可するための設定 itemInsert.jspのmultipart/form-dataからの画像ファイル用
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024, // 1MB
	    maxFileSize = 1024 * 1024 * 5,    // 5MB
	    maxRequestSize = 1024 * 1024 * 10 // 10MB
	)



/**----------------------------------------------------------------------*
 *■■■ItemInsertクラス■■■
 *概要：サーブレット
 *詳細：HTML文書（新商品登録画面）を出力する。
 *----------------------------------------------------------------------**/
public class ItemInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ItemInsert() {
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
			RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/ItemInsert.jsp");
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
			
		boolean succesFlg = true;  //成功フラグ（true:成功/false:失敗）
		
		String itemImage = "NOIMAGE"; // 送信された画像フォルダのデフォルト値
		
		//画像処理
		try {
			Part filePart = request.getPart("ITEM_IMAGE");//ファイル取得
			String fileName = (filePart != null) ? filePart.getSubmittedFileName() : null;// 元のファイル名
        
			if (fileName != null && !fileName.isEmpty()) {
				String savePath = getServletContext().getRealPath("/img");//画像の保存ファイル
            // フォルダが存在しない場合に作成する処理を入れるとより安全
				File fileSaveDir = new File(savePath);
				if (!fileSaveDir.exists()) fileSaveDir.mkdir();
            
				filePart.write(savePath + File.separator + fileName);
				itemImage = fileName;
			} else {
				//ファイル選択がない場合隠しフィールド等を確認
				String exist = request.getParameter("EXISTING_IMAGE");
				
				if(exist != null && !exist.isEmpty()) {
					//それでも空ならNOIMAGE
					itemImage = exist;
				}
			}
        }catch(Exception e){
        	e.printStackTrace();
        }
			

		//パラメータのバリデーションチェック
		if( !(validatePrmName(request.getParameter(  "ITEM_ID"))       &&
			validatePrmName(request.getParameter("ITEM_NAME"))         &&
			validatePrmNum(request.getParameter( "PRICE"))             &&
			validatePrmName(request.getParameter("ITEM_CATEGORY"))     &&
			validatePrmName(request.getParameter("PUBLIC_STATUS"))     &&
			validatePrmName(request.getParameter("ITEM_DESCRIPTION"))  &&
			validatePrmName(request.getParameter("RELEASE_DATE"))               ) ) {

			System.out.println("DEBUG: バリデーションNG"); 
			//バリデーションNGの場合
			succesFlg = false ;

		}else {

		//バリデーションOKの場合

			//リクエストパラメータを取得
			String itemId          = request.getParameter("ITEM_ID"); //リクエスト：商品ID
			String itemName        = request.getParameter("ITEM_NAME");//リクエスト：商品名
			int    price           = Integer.parseInt( request.getParameter("PRICE")); //リクエスト：価格
			String itemCategory    = request.getParameter("ITEM_CATEGORY"); //リクエスト：商品カテゴリ
			String publicStatus    = request.getParameter("PUBLIC_STATUS");  //リクエスト：公開状況
			String itemDescription = request.getParameter("ITEM_DESCRIPTION"); //リクエスト：商品説明
			String releaseDate     = request.getParameter("RELEASE_DATE"); //リクエスト：発売日時
			
			// HTMLのdatetime-local形式を解析（Tが含まれる形式）
			LocalDateTime dateTime = LocalDateTime.parse(releaseDate);
		
			//アンケートデータ（SurveyDto型）の作成
			ItemInsertDto dto = new ItemInsertDto();
			dto.setItemId(itemId);
			dto.setItemName(itemName);
			dto.setPrice(price);
			dto.setItemCategory(itemCategory);
			dto.setPublicStatus(publicStatus);
			dto.setItemImage(itemImage);
			dto.setItemDescription(itemDescription);
			dto.setReleaseDate(dateTime);
			
			System.out.println("DEBUG: バリデーションOK、DB登録開始");
			//アンケートデータをDBに登録
			ItemInsertBL logic = new ItemInsertBL();
			succesFlg          = logic.executeInsertSurvey(dto);  //成功フラグ（true:成功/false:失敗）
			System.out.println("DEBUG: DB登録結果 = " + succesFlg); 
			
		}
		
		

		//成功/失敗に応じて表示させる画面を振り分ける
		if (succesFlg) {
	
			//成功した場合、回答完了画面（finish.html）を表示する
			response.sendRedirect("htmls/ItemInsert-finish.html");
	
		} else {
	
			//失敗した場合、エラー画面（error.html）を表示する
			response.sendRedirect("htmls/ItemInsert-error.html");
		}
			
	}

	/**----------------------------------------------------------------------*
	 *■■■validatePrmNameクラス■■■
	 *概要：バリデーションチェック
	 *詳細：入力値の検証を行う
	 *----------------------------------------------------------------------**/
	private boolean validatePrmName( String pr) {

		boolean validateResult = true ;

		//入力値がnullまたは空白の場合はエラーとする
		if( pr == null || pr.equals("") ) {
			validateResult = false ;
		}

		return validateResult ;
	}

	/**----------------------------------------------------------------------*
	 *■■■validatePrmNumクラス■■■
	 *概要：バリデーションチェック
	 *詳細：入力値の検証を行う
	 *----------------------------------------------------------------------**/
	private boolean validatePrmNum( String pr) {

		boolean validateResult = true ;

		//入力値がnullまたは正の数以外の場合はエラーとする
		if( pr == null || !( pr.matches("^[0-9]+$") )) {
			validateResult = false ;
		}

		return validateResult ;
	}

	

}