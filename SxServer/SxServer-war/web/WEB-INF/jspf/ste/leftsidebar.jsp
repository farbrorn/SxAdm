<%@ page import="se.saljex.sxserver.SXUtil" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>

<%
Connection con = (Connection)request.getAttribute("con");
SXSession sxSession = WebUtil.getSXSession(request.getSession());
%>
<b>Stiebel Eltron</b>
<p/>
<a href="?id=welcome">Startsida</a><p/>
<p/>
<b>Produkter</b><br/>
<a href="?id=produkt">Lista</a><br/>
<a href="?id=produkt&action=new">L�gg till</a><br/>
<p/>
<b>STE Artikelnummer</b><br/>
<a href="?id=pumpartnr">Lista</a><br/>
<a href="?id=pumpartnr&action=new">L�gg till</a><br/>
