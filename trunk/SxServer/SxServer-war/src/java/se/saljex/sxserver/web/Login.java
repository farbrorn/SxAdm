/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.io.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;
import se.saljex.sxserver.*;

/**
 *
 * @author ulf
 */
public class Login extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
	@EJB
	private LocalWebSupportLocal LocalWebSupportBean;
	@Resource(name = "sxadm")
	private DataSource sxadm;
	private Connection con = null;
	private String action = null;
	private String loginType = null;
	private HttpSession session = null;
	private SXSession sxSession = null;
	private LoginFormData f = null;
	private PreparedStatement st = null;
	private RequestDispatcher dispatcher;
	private HttpServletRequest request;
	private HttpServletResponse response;
	protected void processRequest(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException {
		request = req;
		response = res;
//		response.setContentType("text/html;charset=UTF-8");
		try {
			con = sxadm.getConnection();
		} catch (SQLException se) {
			SXUtil.log(se.toString()); se.printStackTrace();
			throw new ServletException("Fel vid kommunikation med databasen.");
		}
		String refPage = request.getParameter("refpage");
		if (refPage == null) { refPage = "kund"; }


		try {
			session = request.getSession();
			sxSession = WebUtil.getSXSession(session);

			f = new LoginFormData();

			f.anvandare = request.getParameter("anvandare");
			action = request.getParameter("action");
			loginType = request.getParameter("logintype");
			if (action == null) { action = "login"; }
			if (loginType == null) { loginType = "kund"; }

			if (action.equals("logout")) {
					session.invalidate();
					dispatcher = request.getRequestDispatcher("WEB-INF/jspf/login/logoutpage.jsp");
					dispatcher.forward(request, response);
			} else {		//Default action = login
				if (loginType.equals("kund")) {
					loginKund();
				} else if (loginType.equals("lev")) {

				} else if (loginType.equals("intra")) {
					loginIntra();
				}
				if (sxSession.getInloggad()) {
					response.setStatus(response.SC_MOVED_TEMPORARILY);
					response.setHeader("Location", refPage);
				} else {
					dispatcher = request.getRequestDispatcher("WEB-INF/jspf/login/loginpage.jsp");
					dispatcher.forward(request, response);
				}
			}

		} catch (SQLException e) {
			SXUtil.log(e.toString()); e.printStackTrace();
			throw new ServletException("Fel vid kommunikation med databasen.");
		} finally {
			try { con.close();} catch (Exception e ){}
			try { st.close();} catch (Exception e ){}
		}
	} 

	private void setFelaktigAnvandare() {
		f.anvandareErr = "Felaktig användare/lösenord";
		request.setAttribute("loginformdata", f);
		SXUtil.log("Felaktig inloggning Användare " + f.anvandare + " fråån ip " + request.getRemoteAddr());
	}

	private boolean loginKund() throws SQLException {
		if (f.anvandare != null) {
			st = con.prepareStatement("select k.nummer, k.namn, kk.namn, kk.kontaktid, kl.loginnamn from kund k, kundkontakt kk, kundlogin kl where k.nummer = kk.kundnr and kk.kontaktid = kl.kontaktid and kl.loginnamn=? and kl.loginlosen=?");
			st.setString(1, f.anvandare);
			st.setString(2, request.getParameter("losen"));
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				sxSession.setInloggad(true);
				sxSession.setKundnr(rs.getString(1));
				sxSession.setKundnamn(rs.getString(2));
				sxSession.setKundKontaktNamn(rs.getString(3));
				sxSession.setKundKontaktId(rs.getInt(4));
				sxSession.setKundLoginNamn(rs.getString(5));
				return true;
			} else {
				// LoginError
				setFelaktigAnvandare();
				return false;
			}
		}
		return false;
	}
	private boolean loginIntra() throws SQLException {
		if (f.anvandare != null) {
			st = con.prepareStatement("select forkortning, namn, a.behorighet from saljare s, anvbehorighet a where a.anvandare = s.namn " +
							" and s.forkortning=? and s.losen=?");
			st.setString(1, f.anvandare);
			st.setString(2, request.getParameter("losen"));
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				if (SXConstant.BEHORIGHET_INTRA_SUPERUSER.equals(rs.getString(3))
						|| SXConstant.BEHORIGHET_INTRA_SUPERUSER.equals(rs.getString(3))
						|| SXConstant.BEHORIGHET_INTRA_ADMIN.equals(rs.getString(3))	  ) {
					sxSession.setInloggad(true);
					sxSession.setIntrauser(true);
					sxSession.setIntraAnvandare(rs.getString(1));
					sxSession.setIntraAnvandareKort(rs.getString(2));
				}
				if (SXConstant.BEHORIGHET_INTRA_SUPERUSER.equals(rs.getString(3)))	sxSession.setSuperuser(true);
				if (SXConstant.BEHORIGHET_INTRA_ADMIN.equals(rs.getString(3)))			sxSession.setAdminuser(true);
			}
			if (sxSession.getInloggad()) {
				return true;
			} else {
				// LoginError
				sxSession.setSuperuser(false);
				sxSession.setAdminuser(false);
				setFelaktigAnvandare();
				return false;
			}
		}
		return false;
	}
	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
    * Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
    * Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
    * Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
