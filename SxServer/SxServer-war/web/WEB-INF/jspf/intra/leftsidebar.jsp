<%-- 
    Document   : leftsidebar
    Created on : 2008-jun-16, 20:43:50
    Author     : ulf
--%>
<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxlibrary.SXConstant" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="se.saljex.sxserver.websupport.*" %>
<%@ page import="se.saljex.sxlibrary.SXSession" %>

<a href="?id=welcome">Startsida</a><p/>
<a href="rapp">Rapporter</a><p/>

<%
Connection con = (Connection)request.getAttribute("con");
SXSession sxSession = WebSupport.getSXSession(request.getSession());
Integer lagerNr = sxSession.getIntraAnvandareLagerNr();
String lagerNamn = null;

// Om vi beg�rt annat lager �n f�rvalt
try { lagerNr = Integer.parseInt(request.getParameter("lagernr")); } catch (NumberFormatException e) {}

ResultSet rs = con.createStatement().executeQuery("select lagernr, bnamn  from lagerid order by bnamn");
while (rs.next()) {
	out.print("<a href=\"?id=viewlager&lagernr="+ rs.getInt(1) + "\">" + rs.getString(2) + "</a><br/>");
	if (lagerNr.equals(rs.getInt(1))) lagerNamn = rs.getString(2);
}
rs.close();
%>

<p/>
<h1><%= lagerNamn %></h1>
<b>Rapporter</b><br/>
<a href="?id=rapp-filialforsaljning&lagernr=<%= lagerNr %>">F�rs�ljning</a><br/>
<a href="?id=rapp-filialstat1&lagernr=<%= lagerNr %>">Statistik</a><br/>
<a href="?id=rapp-saljstatartgrupp&lagernr=<%= lagerNr %>">Statistik artikelgrupp</a><br/>
<p/><b>Topplistor</b><br/>
<a href="?id=rapp-topplistaartikel&sokform=true&lagernr=<%= lagerNr %>">Artiklar</a><br/>
<a href="?id=rapp-topplistakund&sokform=true&lagernr=<%= lagerNr %>">Kunder</a><br/>
<a href="?id=rapp-topplistaartgrupp&sokform=true&lagernr=<%= lagerNr %>">Artikelgrupper</a><br/>
<a href="?id=rapp-topplistalagervarde&sokform=true&lagernr=<%= lagerNr %>">Lagerv�rde</a><br/>
<p/>
<h1><%= SXUtil.toHtml(sxSession.getIntraAnvandare()) %></h1>
<a href="?id=mk-kundlista">Kunder</a><br/>
<a href="?id=mk-statistik">Statistik</a><br/>
<a href="?id=mk-saljstatartgrupp">Statistik artikelgrupp</a><br/>
<% if (sxSession.isBehorighet(SXConstant.BEHORIGHET_STE_TEKNIK))  { %>
<br/><a href="ste">Stiebel Eltron</a><br/>

<%  } %>
