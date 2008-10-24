<%-- 
    Document   : printkundinfo
    Created on : 2008-jun-16, 19:42:28
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.util.List" %>
<% 
SXSession sxSession = WebUtil.getSXSession(session);

se.saljex.sxserver.web.inkop.BestForm b = (se.saljex.sxserver.web.inkop.BestForm)request.getAttribute("bestform");
String errstr;
String errinfo;
%>
<div>
	<form action="?id=2" method="post">
<h1></h1>
<% if (b.isParseError()) { %>
<div id="errortext">Fel i formul�ret. Var v�nlig korrigera markerade f�lt.<br/>Error in form. Please correct marked fields
<%= SXUtil.toHtml(b.getParseText()) %>
</div>
<% } %>

<% if (b.isSaveError()) { %>
<div id="errortext">Ov�ntat fel vid bearbetning av data. Var v�nlig f�rs�k igen.<br/>Error processing data. Please retry.
<%= SXUtil.toHtml(b.getSaveText()) %>
</div>
<% } %>

<div id="infotext">
<% if (b.isSavedOK()) { %>
Sparad OK!<br/>Saved OK!<p/>
<% } %>
<% if (b.isMottagen()) { %> 
Best�llningen �r kvitterad. Vi uppskattar om Ni har m�jlighet att ange leveransdatum i rutan nedan. Om n�gon artikelrad har avvikande leveransdatum kan det anges f�r varje rad.<p/>
Receiption of order is confirmed. We would appreciate it if you also confirm date of delivery in the input field below. If one or more lines have a different delivery dates, dates can be given for each line.<p/>
<table width="100%">
	<tr>
	<td class="tddocheadrubrik" colspan="2">Leveransdatum. Anges i formen ����-mm-dd.<br/>Date of delivery. Please enter as yyyy-mm-dd.</td>
	 <% if (SXUtil.toStr(b.getFormBekrdatErr()).isEmpty()) { errstr=""; errinfo=""; } else {errstr="error"; errinfo=SXUtil.toHtml(b.getFormBekrdatErr());} %>
	<td class="tds30"><input class="tdinput<%= errstr %>" type="text" size="10" name="<%= b.getNameBekrdat() %>" value="<%= SXUtil.toHtml(b.getFormBekrdat()) %>">
		<%= errinfo %>
	</td>
	<td><input type="submit" name="skicka" value="S�nd bekr�ftelse"></td>
 </tr>
</table>
<% } else { %>
Best�llningen �r inte kvitterad av mottagaren.<p/>
Order is not confirmed by receiver.
<% }%>	 
</div>

<table id="dochead">
<tr><th colspan="4">Ink�psorder</th></tr>
<tr>
	<td class="tddocheadrubrik">Best�llningsnummer</td>
	<td class="tds30"><%= b.be1.getBestnr() %></td>
	<td class="tddocheadrubrik">Datum</td>
	<td class="tddatum"><%= b.be1.getDatum() %></td>
</tr>
<tr>
	<td class="tddocheadrubrik">V�r referens</td>
	<td class="tds30"><%= SXUtil.toHtml(b.be1.getVarRef()) %></td>
	<td class="tddocheadrubrik">Er referens</td>
	<td class="tds30"><%= SXUtil.toHtml(b.be1.getErRef()) %></td>
</tr>
<tr>
	<td class="tddocheadrubrik">Leveransadress</td>
	<td class="tds30"><%= SXUtil.toHtml(b.be1.getLevadr0()) %></td>
	<td class="tddocheadrubrik"></td>
	<td class="tds30"><%  %></td>
</tr>
<tr>
	<td class="tddocheadrubrik">&nbsp;</td>
	<td class="tds30"><%= SXUtil.toHtml(b.be1.getLevadr1()) %><br/></td>
	<td class="tddocheadrubrik"></td>
	<td class="tds30"><%  %></td>
</tr>
<tr>
	<td>&nbsp;</td>
	<td class="tds30"><%= SXUtil.toHtml(b.be1.getLevadr2()) %></td>
	<td></td>
	<td class="tds30"><%  %></td>
</tr>
<tr>
	<td>&nbsp;</td>
	<td class="tds30"><%= SXUtil.toHtml(b.be1.getLevadr3()) %></td>
	<td></td>
	<td class="tds30"><% %></td>
</tr>
<tr>
	<td class="tddocheadrubrik">Leveransdatum</td>
	<td class="tds30"><%= SXUtil.toHtml(b.be1.getLeverans()) %></td>
	<td class="tddocheadrubrik">M�rke</td>
	<td class="tds30"><%= SXUtil.toHtml(b.be1.getMarke()) %></td>
</tr>
<tr>
	<td class="tddocheadrubrik">M�rke</td>
	<td colspan="3"><%= SXUtil.toHtml(b.be1.getMarke()) %></td>
</tr>
<tr>
	<td class="tddocheadrubrik">Meddelande</td>
	<td colspan="3"><%= SXUtil.toHtml(b.be1.getMeddelande()) %></td>
</tr>
<tr>
</tr>
</table>

<table id="doc">
<tr>
<th class="tds15">Art.nr.</th>
<th class="tds15">Ert art.nr</th>
<th class="tds30">Ben�mning</th>
<th class="tdn12">Antal</th>
<th class="tds3">Enh</th>
<th class="tdn12">Pris</th>
<th class="tdn4">Rab</th>
<th class="tds10">Leveransdatum</th>
<th></th>
</tr>
<% for (se.saljex.sxserver.web.inkop.BestFormRad r : b.rader) {%>
	 <% if (SXUtil.toStr(r.formBekrdatErr).isEmpty()) { errstr=""; errinfo=""; } else {errstr="error"; errinfo=SXUtil.toHtml(r.formBekrdatErr);} %>
<tr>
	<td class="tds15"><%= SXUtil.toHtml(r.artnr) %></td>
	<td class="tds15"><%= SXUtil.toHtml(r.bartnr) %></td>
	<td class="tds30"><%= SXUtil.toHtml(r.artnamn) %></td>
	<td class="tdn12"><%= r.best %></td>
	<td class="tds3"><%= SXUtil.toHtml(r.enh) %></td>
	<td class="tdn12"><%= SXUtil.getFormatNumber(r.pris) %></td>
	<td class="tdb4"><%= SXUtil.getFormatNumber(r.rab) %></td>
	<td class="tds10"><input class="tdinput<%= errstr %>" type="text" size="10" name="<%= r.getNameBekrdat() %>" value="<%= SXUtil.toHtml(r.formBekrdat)%>">
		<%= errinfo %>
	 </td>
</tr>
<% }%>   
</table>
</form>
</div>