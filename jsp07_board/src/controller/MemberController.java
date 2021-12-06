package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dto.Member;
import service.MemberService;
import service.MemberServiceImpl;

@WebServlet("/member/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private MemberService mservice = new MemberServiceImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		System.out.println(uri);
		//context path 경로
		String path = request.getContextPath();
		
		if (uri.contains("add")) {
			//회원등록
			//saveDirectory : 파일을 저장할 경로(서버)
			//"D:\\ksy\\savedir"(windows표기방식) 와 같음
			//String saveDirectory = "D:/ksy/savedir";
			//web.xml에서 저장 경로 읽기
			String saveDirectory=getServletContext().getInitParameter("savedir");
			
			int size = 1024 * 1024 * 10;//10m :업로드파일사이즈 제한
			//new DefaultFileRenamePolicy() : 같은이름의 파일이 있을때 파일이름 변경
			MultipartRequest multi = new MultipartRequest(request,saveDirectory, size,"utf-8", new DefaultFileRenamePolicy() );
			//MultipartRequest 객체를 이용해서 데이터 가져옴
			String email = multi.getParameter("email");
			String passwd = multi.getParameter("passwd");
			String zipcode = multi.getParameter("zipcode");
			String addr = multi.getParameter("addr");
			String addrdetail = multi.getParameter("addrdetail");
			//실제저장된 파일이름가져오기
			String filename = multi.getFilesystemName("file");
			if (filename==null) filename = ""; //파일이 없을경우
			//객체 생성
			Member member = new Member();
			member.setEmail(email);
			member.setPasswd(passwd);
			member.setZipcode(zipcode);
			member.setAddr(addr);
			member.setAddrdetail(addrdetail);
			member.setFilename(filename);
			System.out.println(member);
			//저장
			String msg = mservice.insert(member);
			System.out.println(msg);
			
			//redirect방식, home.jsp
			//response.sendRedirect(path + "/views/home.jsp?msg=" + URLEncoder.encode(msg, "utf-8"));
			//자동로그인처리
			response.sendRedirect(path + "/member/login?email="+email+"&passwd="+passwd);
			
		}else if (uri.contains("login")) {
			//로그인
			String email = request.getParameter("email");
			String passwd = request.getParameter("passwd");
			System.out.println(email);
			System.out.println(passwd);
			
			Map<String, String> map = mservice.login(email, passwd);
			//rcode가 0이면 home.jsp이동
			//rcode가 0이 아니면 login.jsp이동
			String rcode = map.get("rcode");
			String msg = map.get("msg");
			if (rcode.equals("0")) { //로그인 성공
				//세션에 이메일 넣기
				HttpSession session = request.getSession();
				session.setAttribute("email", email);
				session.setMaxInactiveInterval(60*60*5);
				
				//쿠키에 이메일저장
				String idsave = request.getParameter("idsave");
				Cookie email_cookie = new Cookie("email", email);
				email_cookie.setPath(path); //쿠키의 사용범위 변경
				if (idsave ==null) {
					email_cookie.setMaxAge(0); //쿠키삭제
				}
				response.addCookie(email_cookie);
				
				response.sendRedirect(path + "/views/home.jsp?msg=" + URLEncoder.encode(msg, "utf-8"));				
			}else {
				response.sendRedirect(path + "/views/member/login.jsp?msg=" + URLEncoder.encode(msg, "utf-8"));
			}
	
		}else if (uri.contains("logout")) {
			//로그아웃
			HttpSession session = request.getSession();
			session.invalidate(); //모든 세션변수 삭제
			String msg = "로그아웃 되었습니다.";
			response.sendRedirect(path + "/views/home.jsp?msg=" + URLEncoder.encode(msg, "utf-8"));			
			
		}else if (uri.contains("myinfo")) {
			//내정보
			//이메일을 세션에서 가져오기
			HttpSession session = request.getSession();
			String email = (String)session.getAttribute("email");
			Member member = mservice.selectOne(email);
			System.out.println(member);
			//forward방식으로 myinfo.jsp이동
			request.setAttribute("member", member);
			request.getRequestDispatcher("/views/member/myinfo.jsp")
						.forward(request, response);
		}else if (uri.contains("modify")) {
			//수정
			//String saveDirectory = "D:/ksy/savedir";
			//web.xml에서 읽기
			String saveDirectory=getServletContext().getInitParameter("savedir");
			
			int size = 1024 * 1024 * 10;//10m :업로드파일사이즈 제한
			//new DefaultFileRenamePolicy() : 같은이름의 파일이 있을때 파일이름 변경
			MultipartRequest multi = new MultipartRequest(request,saveDirectory, size,"utf-8", new DefaultFileRenamePolicy() );
			//MultipartRequest 객체를 이용해서 데이터 가져옴
			String email = multi.getParameter("email");
			String passwd = multi.getParameter("passwd");
			String changepw = multi.getParameter("changepw");
			String zipcode = multi.getParameter("zipcode");
			String addr = multi.getParameter("addr");
			String addrdetail = multi.getParameter("addrdetail");
			String filename = multi.getParameter("filename"); //기존파일이름
			String filedel = multi.getParameter("filedel"); //이미지파일 삭제 여부
			System.out.println(filedel);
			
			//실제저장된 파일이름가져오기
			String newfilename = multi.getFilesystemName("file"); 
			//파일을 변경하겠다라는 의미
			if (newfilename!=null) 
				filename = newfilename;
			else if (filedel != null) //삭제체크가 되어있다면
				filename = "";
			
			//객체 생성
			Member member = new Member();
			member.setEmail(email);
			member.setPasswd(passwd);
			member.setZipcode(zipcode);
			member.setAddr(addr);
			member.setAddrdetail(addrdetail);
			member.setFilename(filename);
			System.out.println(member);
			
			String msg = mservice.update(member, changepw);
			
			//내정보 서블릿 호출
			response.sendRedirect(path + "/member/myinfo?msg="+ URLEncoder.encode(msg, "utf-8"));
			
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
