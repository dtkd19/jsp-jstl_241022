package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.MemberVO;
import handler.FileRemoveHandler;
import net.coobird.thumbnailator.Thumbnails;
import service.MemberService;
import service.MemberServiceImpl;


@WebServlet("/mem/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	//동기방식 : requestDispatcher : request에 대한 응답 데이터를 jsp(html) 화면으로 전송하는 역할
	private RequestDispatcher rdp;
	// 목적지 주소
	private String destPage;
	private int isOk;
	
	// service 
	private MemberService msv; 
	// 파일 경로
	private String savePath;
	
	
    public MemberController() {
    	msv = new MemberServiceImpl();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 처리
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		// 요청주소 추출
		String uri = request.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/")+1);
		log.info(">>> path > {} " , path);
		
		switch (path) {
		case "join":
			destPage="/member/join.jsp";		
			break;
		case "register":
			
			try {
				// jsp에서 온 파라미터 받기
				// member 객체 생성 후 service에게 등록 요청
//				String id = request.getParameter("id");
//				String pwd = request.getParameter("pwd");
//				String email = request.getParameter("email");
//				String phone = request.getParameter("phone");
//				
//				MemberVO mvo = new MemberVO(id, pwd, email, phone);
//				log.info(">>>> mvo register 객체 >>>> {}", mvo);
				
				savePath = getServletContext().getRealPath("/_fileUpload");
				log.info(">>>> savePath > {}" , savePath);
				
				File fileDir = new File(savePath);
				DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
				fileItemFactory.setSizeThreshold(1024*1024*3);
				fileItemFactory.setRepository(fileDir);
				
				MemberVO mvo = new MemberVO();
				
				ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
				List<FileItem> itemList = fileUpload.parseRequest(request);
				
				for(FileItem item : itemList) {
					log.info(" >>>> FileItem >> {} ", item.toString());
					switch (item.getFieldName()) {
					case "id":
						mvo.setId(item.getString("UTF-8"));
						break;
					case "pwd":
						mvo.setPwd(item.getString("UTF-8"));
						break;
					case "email":
						mvo.setEmail(item.getString("UTF-8"));
						break;
					case "phone":
						mvo.setPhone(item.getString("UTF-8"));
						break;
					case "imageFile":
						if(item.getSize() > 0 ) {
							String fileName = item.getName();
						
							fileName = System.currentTimeMillis()+"_"+fileName;
								
							File uploadFilePath = new File(fileDir+File.separator+fileName);
							log.info(">>> uploadFilePath > {}" , uploadFilePath.toString());
							
							// 저장
							try {
								item.write(uploadFilePath); // 객체를 디스크에 쓰기
								mvo.setImageFile(fileName);// bvo에 저장할 값 (DB에 들어가는 값 )
																		
								Thumbnails.of(uploadFilePath).size(75, 75).toFile(new File(fileDir+File.separator+"_th_"+fileName));
															
							} catch (Exception e) {
								log.info(">>> file writer on disk error");
								e.printStackTrace();
							}
							
						}
						
						break;
				
					}
					
				}
											
				int isOk = msv.register(mvo);
				
				log.info(">>>> mvo register >>>>" + (isOk > 0 ? "성공" : "실패"));
				
				destPage = "/index.jsp";
				
			} catch (Exception e) {
				log.info("join error");
				e.printStackTrace();
			}
			
			break;
		case "login":
			try {
				// 로그인 : id, pwd 파라미터로 받아서 DB에 해당아이디가 있는지 확인, pwd가 일치하는지 확인
				// 정보를 가져와서 session 객체에 저장
				// session : 모든 jsp에 공유되는 객체 / 브라우저가 종료되면 삭제 
				// ${변수명}
				String id = request.getParameter("id");
				String pwd = request.getParameter("pwd");
				
				MemberVO mvo = new MemberVO(id, pwd);
				
				MemberVO loginMvo = msv.login(mvo);
				// 로그인 정보가 잘못되면 loginMvo는 null
				log.info(">>> loginMvo > {}", loginMvo);
				
				// session 에 저장 
				
				if(loginMvo != null) {
					// 로그인이 성공했다면...
					// 세션에 저장 
					HttpSession ses = request.getSession();
					ses.setAttribute("ses", loginMvo);
					// 로그인 유지시간 초단위로 설정 10분
					ses.setMaxInactiveInterval(10*60);
					
					
				} else {
					// 로그인이 실패했다면... index.jsp로 메시지전송
					request.setAttribute("msg_login", -1);
				}
				destPage = "/index.jsp";
				
			} catch (Exception e) {
				log.info("login error");
				e.printStackTrace();
			}
			break;
		case "logout":
			try {
				// session에 값이 있다면 해당 세션을 끊어라.
				HttpSession ses = request.getSession();
				MemberVO mvo = (MemberVO) ses.getAttribute("ses");
				log.info(">>> ses 에서 추출한 mvo 객체  > {}" , mvo );
				
				// lastlogin update
				isOk = msv.lastLogin(mvo.getId());
				log.info(">>> lastLogin update >> {}" , (isOk>0 ? "성공" : "실패"));
				ses.invalidate(); // 세션 무효화 (세션끊기)
				destPage= "/index.jsp";
				
			
			} catch (Exception e) {
				log.info("logout error");
				e.printStackTrace();
			}
				
			break;
		case "list":
			try {				
				List<MemberVO> list = msv.getList();
				log.info(">>>> userList >>> {} ", list);
				request.setAttribute("userList", list);
				
				destPage = "/member/list.jsp";
			
			} catch (Exception e) {
				log.info("userList error");
				e.printStackTrace();
			}
			break;
		case "modify":
			try {
				HttpSession ses = request.getSession();
				MemberVO mvo = (MemberVO) ses.getAttribute("ses");
				log.info(">>> modify ses 에서 추출한 mvo 객체  > {}" , mvo );				
				request.setAttribute("mvo", mvo);
				
				destPage = "/member/modify.jsp";
								
			} catch (Exception e) {
				log.info("modify error");
				e.printStackTrace();
			}
			break;
		case "info":
			try {
//				String id = request.getParameter("id");
//				String pwd = request.getParameter("pwd");
//				String email = request.getParameter("email");
//				String phone = request.getParameter("phone");
//				MemberVO mvo = new MemberVO(id, pwd, email, phone);
								
								
				savePath = getServletContext().getRealPath("_fileUpload");
				
				File fileDir = new File(savePath);
				
				DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
				fileItemFactory.setSizeThreshold(1024*1024*3); // 3MB 정도 설정
				fileItemFactory.setRepository(fileDir);
				
				MemberVO mvo = new MemberVO();
				
				ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
				List<FileItem> itemList = fileUpload.parseRequest(request);
				
				String old_file = null;
				
				for(FileItem item : itemList) {
					log.info(" >>>> FileItem >> {} ", item.toString());
					switch (item.getFieldName()) {
					case "id":
						mvo.setId(item.getString("UTF-8"));
						break;
					case "pwd":
						mvo.setPwd(item.getString("UTF-8"));
						break;
					case "email":
						mvo.setEmail(item.getString("UTF-8"));
						break;
					case "phone":
						mvo.setPhone(item.getString("UTF-8"));
						break;
					case "imageFile":
						old_file = item.getString("UTF-8");
						break;
					case "newFile":
						if(item.getSize() > 0) {
							if(old_file != null) {
								FileRemoveHandler fileHandler = new FileRemoveHandler();
								isOk = fileHandler.deleteFile(savePath, old_file);	
							}
							// 새로운 파일 등록 작업
							String fileName = System.currentTimeMillis()+"_"+item.getName();
							
							File uploadFilePath = new File(fileDir+File.separator+fileName);
							
							try {
								item.write(uploadFilePath);
								mvo.setImageFile(fileName);
								
								Thumbnails.of(uploadFilePath).size(75, 75).toFile(new File(fileDir+File.separator+"_th_"+fileName));
								
							} catch (Exception e) {
								log.info("File writer update error!");
								e.printStackTrace();
							}			
					} else {
						mvo.setImageFile(old_file);
					}
						
						
					}
				}
				
			
								
				int isOk = msv.modify(mvo);
				log.info(" >>> userInfo Modify "+ (isOk > 0 ? "성공" : "실패"));
		
				if(isOk > 0 ) {
					MemberVO mvo2 = msv.getDetail(mvo.getId());
					HttpSession ses = request.getSession();
					ses.setAttribute("ses", mvo2);					
				} else {
					request.setAttribute("msg_modify", -1);
				}
				
				destPage = "/index.jsp";
				
				
			} catch (Exception e) {
				log.info("info error");
				e.printStackTrace();
			}
			break;
		case "delete":
			try {
				String id = request.getParameter("id");
				int isOk = msv.delete(id);
				
				log.info(">>>> userInfo delete >>> " + (isOk > 0 ? "성공" : "실패"));
				
				HttpSession ses = request.getSession();
				MemberVO mvo = (MemberVO) ses.getAttribute("ses");
				ses.invalidate();
				
				destPage = "/index.jsp";		
				
			} catch (Exception e) {
				log.info(" userDelete error");
				e.printStackTrace();
			}
			break;
		case "myboard":
			try {
				HttpSession ses = request.getSession();
				MemberVO mvo = (MemberVO) ses.getAttribute("ses");				
				String id = mvo.getId();
				
				log.info(">>> id >>>>>> {} ", id);
				
				List<BoardVO> list = msv.getBoard(id);
				
				log.info(">>>> BoardList >>>> {}", list);
				
				request.setAttribute("boardList", list);
				
				destPage = "/member/mybrd.jsp";
				
			} catch (Exception e) {
				log.info(" myBoard error");
				e.printStackTrace();
			}		
			break;
		}
			
		
		// rdp 전송	
		rdp = request.getRequestDispatcher(destPage);
		rdp.forward(request, response);
		
		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// service 에서 처리
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// service 에서 처리
		service(request, response);
	}

}
