package controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import model.ItemUpdateBL;
import model.ItemUpdateDto;
import model.ShowAllDataBL;
import model.ShowAllDataDto;
import model.UserLoginInfoDto;

@MultipartConfig

/**ItemUpdate
 * データ更新画面（HTML)を表示
 * **/
public class ItemUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ItemUpdate() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//レスポンス（出力データ）の文字コードを設定
		response.setContentType("text/html;charset=UTF-8");  //文字コードをUTF-8で設定
		
		//セッションから情報を取得
		HttpSession session = request.getSession();
		UserLoginInfoDto InfoOnS = (UserLoginInfoDto)session.getAttribute("LOGIN_INFO");
		
        //ログイン状態によって画面を振り分け セッション情報にユーザー情報あるかどうかで判定
		if(InfoOnS != null) {
			
			//ログイン済み：アンケート入力画面に転送
			//viewにフォワード
			RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/itemUpdate.jsp");
			dispatch.forward(request, response);

			
		}else {
			//ログインしてないのでログイン画面へ
			response.sendRedirect("Login");
		
		}
		
	}
	
	
	//requestデータをDBに登録
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//レスポンス（出力データ）の文字コードを設定
				response.setContentType("text/html;charset=UTF-8");     //文字コードをUTF-8で設定
				//リクエスト（受信データ）の文字コードを設定
				request.setCharacterEncoding("UTF-8");                  //文字コードをUTF-8で設定
					
				boolean succesFlg = true;  //成功フラグ（true:成功/false:失敗）
				//送信されてきたデータの形式を確認する
			    String contentType = request.getContentType();
			    
			 // ファイルが含まれていない（通常の画面遷移など）の場合
			    if (contentType == null || !contentType.startsWith("multipart/form-data")) {
			        // 既存の「画面表示用ロジック」を実行
			        ShowAllDataBL bl = new ShowAllDataBL();
			        List<ShowAllDataDto> list = bl.executeSelectSurvey();
			        
			        if (list != null) {
			            for (ShowAllDataDto dto : list) {
			                if (dto.getReleaseDate() != null) {
			                    // 秒とナノ秒を0にクリアした日時に更新
			                    dto.setReleaseDate(dto.getReleaseDate().withSecond(0).withNano(0));
			                }
			            }
			        }
			        
			        request.setAttribute("SHOW_ALL_DATA", list);
			        
			        request.getRequestDispatcher("/WEB-INF/view/itemUpdate.jsp").forward(request, response);
			        return; // ここで処理を終了させる
			    }
				
				Part filePart = request.getPart("ITEM_IMAGE");//ファイル取得
				String fileName = filePart.getSubmittedFileName(); // 元のファイル名
		        String itemImage; // DBに保存する用の変数
		        
		        if (fileName != null && !fileName.isEmpty()) {
		            // 実行環境の「img」フォルダのパスを自動取得
		            String savePath = getServletContext().getRealPath("/img");
		            
		            File fileSaveDir = new File(savePath);
		            if (!fileSaveDir.exists()) {
		                fileSaveDir.mkdirs();
		            }
		            
		            // 書き込み実行
		            filePart.write(savePath + File.separator + fileName);
		            System.out.println("実際の保存先: " + savePath + File.separator + fileName);
		            itemImage = fileName;
		        }else {
		            //新しいファイルが選択されていない場合は、隠しフィールドから既存のファイル名を取得する
		            itemImage = request.getParameter("EXISTING_IMAGE");
		        }

				//リクエストパラメータを取得
				String itemId          = request.getParameter("ITEM_ID");  //hiddenで贈った値 商品ID
				String itemName        = request.getParameter("ITEM_NAME");//リクエスト：商品名
				String priceStr        = request.getParameter("PRICE");//リクエスト：価格
				String itemCategory    = request.getParameter("ITEM_CATEGORY"); //リクエスト：商品カテゴリ
				String publicStatus    = request.getParameter("PUBLIC_STATUS"); //リクエスト：公開状況
				String itemDescription = request.getParameter("ITEM_DESCRIPTION");//リクエスト：商品説明
				String releaseDateStr  = request.getParameter("RELEASE_DATE");//リクエスト：発売日時
				
				if (itemName == null) {
					
					ShowAllDataBL bl = new ShowAllDataBL();
					List<ShowAllDataDto> list  = bl.executeSelectSurvey();//リストから抽出
					
					request.setAttribute("SHOW_ALL_DATA", list);//リストにセット
					
					// 更新画面を表示（フォワード）
			        request.getRequestDispatcher("/WEB-INF/view/itemUpdate.jsp").forward(request, response);
					
				}else {
					//バグを洗い出しやすくするため個別にバリデーションチェック
					if( (!validateChk(itemId))) {
						response.sendRedirect("htmls/errorUpdate.html");
						System.out.println("IDバリデーションチェックNG");						
						return;
					}
					
					if( (!validateChk(itemName))) {
						response.sendRedirect("htmls/errorUpdate.html");
						System.out.println("nameバリデーションチェックNG");						
						return;
					}
					
					if( (!validateChk(priceStr))) {
						response.sendRedirect("htmls/errorUpdate.html");
						System.out.println("priceバリデーションチェックNG");						
						return;
					}
					
					if( (!validateChk(itemCategory))) {
						response.sendRedirect("htmls/errorUpdate.html");
						System.out.println("categoryバリデーションチェックNG");						
						return;
					}
					
					if( (!validateChk(publicStatus))) {
						response.sendRedirect("htmls/errorUpdate.html");
						System.out.println("statusバリデーションチェックNG");						
						return;
					}
					
					if( (!validateChk(itemDescription))) {
						response.sendRedirect("htmls/errorUpdate.html");
						System.out.println("説明バリデーションチェックNG");						
						return;
					}
					
					if( (!validateChk(releaseDateStr))) {
						response.sendRedirect("htmls/errorUpdate.html");
						System.out.println("dateバリデーションチェックNG");						
						return;
					}
					
					ItemUpdateDto dto = new ItemUpdateDto();
					
					//型変換処理
					int price = Integer.parseInt(priceStr);
					
					// HTMLのdatetime-local形式を解析（Tが含まれる形式）
					String dateStr = releaseDateStr;
					LocalDateTime releaseDate = null;
					try {
					    // 秒が含まれない場合にも対応できるようにパースする
					    if (dateStr.length() == 16) { // yyyy-MM-ddTHH:mm の場合
					        dateStr += ":00";
					    }
					    releaseDate = LocalDateTime.parse(dateStr);
					    dto.setReleaseDate(releaseDate);
					} catch (Exception e) {
					    System.out.println("日付解析エラー: " + releaseDateStr);
					    succesFlg = false;
					}
					       
				
					//アンケートデータ（SurveyDto型）の作成
					dto.setItemId(itemId);
					dto.setItemName(itemName);
					dto.setPrice(price);
					dto.setItemCategory(itemCategory);
					dto.setPublicStatus(publicStatus);
					dto.setItemImage(itemImage);
					dto.setItemDescription(itemDescription);
					dto.setReleaseDate(releaseDate);


					//アンケートデータをDBに登録
					ItemUpdateBL logic = new ItemUpdateBL();
					succesFlg          = logic.executeUpdateInfo(dto);  //成功フラグ（true:成功/false:失敗）
						
	
					//成功/失敗に応じて表示させる画面を振り分ける
					if (succesFlg) {
	
						//成功した場合、回答完了画面を表示する itemUpdate.jspに戻らせる
						response.sendRedirect("htmls/finishUpdate.html");
	
					} else {
	
						//失敗した場合、エラー画面を表示する
						response.sendRedirect("htmls/errorUpdate.html");
						System.out.println("送信失敗");
					}
				}
			}
	
	//空欄かどうかチェック
	boolean validateChk (String chk) {
		
		boolean chkValidate = true;
		
		if ( chk == null || chk.isEmpty() ) {
			chkValidate = false;
		}
		return chkValidate;
	}
}
