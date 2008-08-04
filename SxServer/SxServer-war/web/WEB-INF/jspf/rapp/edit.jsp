<%@ page import="se.saljex.sxserver.SXUtil" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.util.ArrayList" %>

<% 
SXSession sxSession = WebUtil.getSXSession(session);
String divInfo = (String)request.getAttribute("divinfo");
RappEdit r = (RappEdit)request.getAttribute("rappedit");
Integer rappSession = r.getRappSession();
//Integer rappSession = Integer.parseInt(request.getParameter("rappsession"));
//RappEdit r = sxSession.getArrRappEdit().get(rappSession);
if (divInfo == null) divInfo = "";
int cn;
RappEdit.RappColumn rc;
%>

<div <%= divInfo %>>
<h1>�ndra Rapport</h1>

<div id="rapphuvud">
<%= r.editHuvud() %>
<a href="?id=persist&rappsession=<%= rappSession %>">Spara</a>
</div>


<div id="rappcolumn">
<table><tr><th>Sortering</th><th>Kolumnrubrik</th><th>Sql-namn</th></tr>
<%
for (cn=0; cn<r.getArrColumn().size(); cn++) {
rc = r.getArrColumn().get(cn);
%>
<tr><td><%= rc.sortOrder %></td><td><%= SXUtil.toHtml(rc.label) %></td><td><%= SXUtil.toHtml(rc.sqlLabel) %></td><td><a href="?id=editcolumn&rappsession=<%= rappSession %>&pos=<%= cn %>">�ndra</a></td></tr>
<%
}
	
%>
</table>
<a href="?id=newcolumn&rappsession=<%= rappSession %>">Ny kolumn</a>
</div>



<div id="rappfilter">
<table><tr><th>Position i where</th><th>Namn</th><th>Javatype</th></tr>
<%
RappEdit.RappFilter rf;
for (cn=0; cn<r.getArrFilter().size(); cn++) {
rf = r.getArrFilter().get(cn);
%>
<tr><td><%= rf.wherepos %></td><td><%= SXUtil.toHtml(rf.label) %></td><td><%= SXUtil.toHtml(rf.javatype) %></td><td><a href="?id=editfilter&rappsession=<%= rappSession %>&pos=<%= cn %>">�ndra</a></td></tr>
<%
}	
%>
</table>
<a href="?id=newfilter&rappsession=<%= rappSession %>">Nytt filter</a>
</div>


<div id="rappsum">
<table><tr><th>Summeringskolumn</th><th>Nollst�llningskolumn</th><th>Typ</th></tr>
<%
RappEdit.RappSum rs;
for (cn=0; cn<r.getArrSum().size(); cn++) {
rs = r.getArrSum().get(cn);
%>
<tr><td><%= rs.sumcolumn %></td><td><%= rs.resetcolumn %></td><td><%= SXUtil.toHtml(rs.sumtype) %></td><td><a href="?id=editsum&rappsession=<%= rappSession %>&pos=<%= cn %>">�ndra</a></td></tr>
<%
}	
%>
</table>
<a href="?id=newsum&rappsession=<%= rappSession %>">Ny summa</a>
</div>


</div>