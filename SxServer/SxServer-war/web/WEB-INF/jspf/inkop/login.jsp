<%-- 
    Document   : printkundinfo
    Created on : 2008-jun-16, 19:42:28
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.util.List" %>
<%@ page import="se.saljex.sxserver.websupport.*" %>
<%@ page import="se.saljex.sxlibrary.SXSession" %>
<%@ page import="se.saljex.sxlibrary.*" %>


<% 
SXSession sxSession = WebSupport.getSXSession(session);

se.saljex.sxserver.web.inkop.LoginForm l = (se.saljex.sxserver.web.inkop.LoginForm)request.getAttribute("loginform");
%>
<h1>Logga in</h1>

<% if (l.isLoginError()) { %>
<div id="errortext">Felaktigt best�llningsnummer/s�kerhetskod, Var v�nlig f�rs�k igen.<p/>Incorrect p/o number/safety code. Please try again.<p/>
</div>
<% } %>
<% if (l.isSecurityError()) { %>
<div id="errortext">F�r m�nga felaktiga inloggningsf�rs�k. Var v�nlig kontakta oss.<p/>Too many incorrect logins. Please contact us.<p/>
</div>
<% } %>

<form action="?id=2" method="get">
<table>
<tr>
	<td>Best�llningsnummer<br/>Purchase order number</td>
	<td><input type="text" name="bnr" value="<%= SXUtil.toHtml(l.getBnr()) %>"></td>
</tr>
<tr>
	<td>S�kerhetskod<br/>Safety code</td>
	<td><input type="text" name="skd"></td>
</tr>
<tr>
	<td colspan="2"><input type="submit" value="OK" ></td>
</tr>
</table>
</form>
