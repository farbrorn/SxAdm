<%-- 
    Document   : rapp-saljstatartgrupp
    Created on : 2009-mar-22, 18:24:38
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="se.saljex.sxlibrary.*" %>

<%
String jspName = "saljstatartgrupp";
Connection con = (Connection)request.getAttribute("con");
ResultSet rs;
Integer lagerNr = null;
try {
	lagerNr = Integer.parseInt(request.getParameter("lagernr"));
} catch (NumberFormatException e) {}
%>

 <script type="text/javascript">
$(document).ready(function() {
	$("input[@name=bsok]").click( function() {  loadsokreslocal(); return false;});
	$("select[@name=lagernr]").click( function() {  loadsokreslocal(); return false;});
	$("input[@name=lagertyp]").click( function() { loadsokreslocal();});
	loadsokreslocal();
});

function loadsokreslocal() {
	$("#divdoclist").load("rapp?" + $("#sokform").serialize(), function() {  } );
}

 </script>

<div id="divdocsok">

<form id="sokform" action="">
<input type="hidden" name="get" value="printjsprapport"/>
<input type="hidden" name="jsp" value="<%= jspName %>"/>
Lager:
<select name="lagernr">
	<option value="alla" <%= lagerNr==null ? "selected" : "" %>>Alla</option>
<%
rs = con.createStatement().executeQuery("select lagernr, bnamn from lagerid order by bnamn");
while (rs.next()) {
%>
<option value="<%= rs.getInt(1) %>" <%= lagerNr!=null && lagerNr.equals(rs.getInt(1)) ? "selected" : ""  %>><%= SXUtil.toHtml(rs.getString(2)) %></option>
<%
}
%>
</select>
<input type="radio" name="lagertyp" value="lager" <%= "lager".equals(request.getParameter("lagertyp")) || request.getParameter("lagertyp")==null  ? "checked" : "" %>>Endast lagerf�rs�ljning &nbsp;
<input type="radio" name="lagertyp" value="team" <%= "team".equals(request.getParameter("lagertyp")) ? "checked" : "" %>>Lager med teammedlemmar &nbsp;
<input type="submit" value="S�k" name="bsok"/>
<input type="hidden" name="kundnr" value="<%= SXUtil.toStr(request.getParameter("kundnr")) %>"
</form>
</div>

<div id="divdoclist"></div>

