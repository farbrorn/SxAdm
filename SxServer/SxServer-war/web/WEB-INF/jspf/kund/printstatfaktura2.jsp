<%-- 
    Document   : printkundinfo
    Created on : 2008-jun-16, 19:42:28
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.util.List" %>
 <script type="text/javascript" src="sxdoclib.js"></script>
 <script type="text/javascript">
$(document).ready(function() {
		 $(".docgrupp").hide();
});
		 
 </script> 

<% 
SXSession sxSession = WebUtil.getSXSession(session);

PageListStatFaktura2 pl = (PageListStatFaktura2)request.getAttribute("pageliststatfaktura2");

String divInfo = (String)request.getAttribute("divinfo");
if (divInfo == null) divInfo = "";
%>
<div <%= divInfo %>>
<%
if (pl == null) { out.println("Inga data"); } else { %>
<table id="doclist">
	<tr>
		<th class="tdknapp"></th>
		<th class="tds15">Artikelnr</th>
		<th class="tds30">Ben�mning</th>
		<th class="tdn16">Belopp</th>
		<th class="tdn16">Antal</th>
		<th class="tdn16">K�ptillf�llen</th>
		 <th></th>
	</tr>
<%
int radcn = 0;
while ( pl.next() ) {
radcn++;
if (radcn % 2 > 0) { %> <tr id="tr<%= radcn %>" class="trdocodd"> <%} else { %><tr id="tr<%= radcn %>" class="trdoceven"> <%}

%>
<td class="tdknapp"><a href="JavaScript:showstatfaktura12(<%= radcn %>, '<%= pl.getFrdatStr() %>', '<%= pl.getTidatStr() %>', '<%= pl.getArtnr() %>')" name="a<%= radcn %>">Visa</a></td>
<td class="tds15"><%= pl.getArtnr() %></td>
<td class="tds30"><%= pl.getNamn() %></td>
<td class="tdn16"><%= SXUtil.getFormatNumber(pl.getSumma()) %></td>
<td class="tdn16"><%= SXUtil.getFormatNumber(pl.getAntalKopta(),0) %></td>
<td class="tdn16"><%= pl.getAntalKop() %></td>
<td></td>
</tr>
<tr><td colspan="7"><div id="f<%= radcn %>" class="docgrupp"></div</td></tr>	
<%
}
%>
</table>
<%
}
%>
<form name="data">
	 <input name="currentpage" value="<%= pl.getCurrentPage() %>" type="hidden" />
	 <input name="nextpage" value="<%= pl.getNextPage() %>" type="hidden" />
	 <input name="previouspage" value="<%= pl.getPreviousPage() %>" type="hidden" />
</form>
	
</div>