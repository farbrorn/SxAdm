<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.websupport.*" %>

<% 
SXSession sxSession = WebUtil.getSXSession(session);
String anv = "";
if (sxSession.getKundLoginNamn() != null) {
	anv = sxSession.getKundKontaktNamn() + ", " + sxSession.getKundnamn();
} else if (sxSession.getLevnr() != null) {
	anv = sxSession.getLevnamn();
} else if (sxSession.getIntraAnvandare() != null) {
	anv = sxSession.getIntraAnvandare();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
               "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<title>S�ljex AB</title>
		<link href="style.css" type="text/css" rel="stylesheet" />
		<meta content="S�ljex AB" name="description" />
		<meta content="s�ljex,logga in, kund" name="keywords" />
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
<script type="text/javascript" src="jquery.js"></script>          
	</head>
	<body>
		<div id="container">
			 <div id="siteheader">
				<ul>
				 <li><a href="/">S�ljex AB</a></li>
				 <li>
					<%
					if (sxSession.getInloggad()) {
					 %> <a href="login?action=logout">Logga ut</a>&nbsp;<span id="siteanvandare">Inloggad som: <%= anv %></span> <%
					}
					%>

				 </li>
				 <li><a href="kund">Kund</a></li>
				 <li>Leverant�r</li>
				 <li><a href="intra">Internt</a></li>
			  </ul>
				
			 </div>
