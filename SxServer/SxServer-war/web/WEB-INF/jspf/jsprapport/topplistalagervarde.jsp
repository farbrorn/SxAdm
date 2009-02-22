<%-- 
    Document   : lagervarde
    Created on : 2009-feb-22, 18:14:20
    Author     : ulf
--%>

<%@ page import="se.saljex.sxserver.SXUtil" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>

<%
Connection con = (Connection)request.getAttribute("con");
ResultSet rs;

final String OB_VARDE = "varde";
final String OB_TID = "tid";
final String OB_LAGERKOSTNAD = "kostnad";
final String checkedStr = "checked=\"checked\"";

int lagernr = 0;
int maxrader = 1000;
int sida = 1;
try {	maxrader = Integer.parseInt(request.getParameter("maxrader")); } catch (NumberFormatException e) {}
try {	sida = Integer.parseInt(request.getParameter("sida")); } catch (NumberFormatException e) {}
if (sida > 0) sida--; else sida=0; // Sida 1 = offest 0, dvs minska sidan med 1 f�r att f� r�tt offset

try {
	lagernr = Integer.parseInt(request.getParameter("lagernr"));
} catch (NumberFormatException e) {}

String orderForklaring;
String orderby = request.getParameter("orderby");

String sqlOrder;

if (OB_VARDE.equals(orderby)) {
	sqlOrder = "varde desc, l.ilager desc";
	orderForklaring = "Lagerv�rde";
} else if (OB_TID.equals(orderby)) {
	sqlOrder = "omstid desc, varde desc, l.ilager desc";
	orderForklaring = "Oms�ttningstid";
} else {
	sqlOrder = "lagerkost desc, varde desc, l.ilager desc";
	orderForklaring = "Lagerkostnad";
	orderby = OB_LAGERKOSTNAD;
}



String lagerNamn;

if (!"true".equals(request.getParameter("inputform"))) {

	String lagerWhere = "1=1";

	// Lageruppgifter
	rs = con.createStatement().executeQuery("select bnamn  from lagerid where lagernr=" + lagernr);
	if (!rs.next()) {
		out.print("Ogiltigt lager");
		return;
	}
	lagerWhere = "f1.lagernr = " + lagernr;
	lagerNamn = rs.getString(1);
	rs.close();

	PreparedStatement p = con.prepareStatement(
	" select  a.nummer, a.namn, l.ilager, round(l.ilager*a.inpris*(1-a.rab/100)) as varde, " +
	" round(case coalesce(f.sumlev,0) when 0 then 12 else l.ilager/coalesce(f.sumlev,0) end) as omstid,"+
	" round((case coalesce(f.sumlev,0) when 0 then 12 else l.ilager/coalesce(f.sumlev,0) end) * l.ilager*a.inpris*(1-a.rab/100)/12/10)  as lagerkost"+
	" from artikel a"+
	" join lager l on l.lagernr = " + lagernr +
	" and l.artnr=a.nummer and l.ilager <> 0"+
	" left outer join ( select f1.lagernr, f2.artnr, sum(f2.lev)/12 as sumlev "+
	"	from faktura2 f2 join faktura1 f1 on f1.faktnr = f2.faktnr"+
	"	where f1.datum > current_date - 365"+
	"	group by f1.lagernr, f2.artnr"+
	" ) f on f.lagernr = l.lagernr and f.artnr=l.artnr"+
	" order by " + sqlOrder +
	" limit " + maxrader +
	" offset " + maxrader*sida
			  );
	rs = p.executeQuery();

	%>
				<h1>Lagerv�rde <%= lagerNamn %></h1>
				Visar lagerstatistik.<br/>
				Statistiken �r baserat p� f�rs�ljning senaste �ret. D�r artikeln �r nyupplagd, eller uppgifter saknas r�knas med lageroms�ttning p� 12 m�n.<br/>
				Lagerkostnad anger lagerkostnaden f�r respektive artikel baserat p� lagerv�rde, oms�ttningstiden och en kostnad p� 10% inkl r�nta.<br/>
				<table>
					<tr><td>Sortering</td><td><%= orderForklaring %></td></tr>
				</table>
				<table id="doclist">
					<tr>
						<th class="tds10">ArtNR</th>
						<th class="tds30">Ben�mning</th>
						<th class="tdn12">Antal i lager</th>
						<th class="tdn12">Lagerv�rde</th>
						<th class="tdn12">Oms�ttningstid i m�nader</th>
						<th class="tdn12">Lagerkostnad</th>
						<th></th>
					</tr>
					<%
					boolean oddRow = true;
					while (rs.next()) {

						if (oddRow) out.print("<tr class=\"trdocodd\">"); else out.print("<tr class=\"trdoceven\">");
						oddRow = !oddRow;
						%>
						<td class="tds10"><%= SXUtil.toHtml(rs.getString(1)) %></td>
						<td class="tds30"><%= SXUtil.toHtml(rs.getString(2)) %></td>
						<td class="tdn12"><%= SXUtil.getFormatNumber(rs.getDouble(3),0) %></td>
						<td class="tdn12"><%= SXUtil.getFormatNumber(rs.getDouble(4),0) %></td>
						<td class="tdn12"><%= SXUtil.getFormatNumber(rs.getDouble(5),0) %></td>
						<td class="tdn12"><%= SXUtil.getFormatNumber(rs.getDouble(6),0) %></td>
					</tr>
						<%
					}
					%>
				</table>
<%
	} else {
	//Skiriv inputform
	String p = null;
	%>
<h1>Lagerv�rde</h1>
<form action="" method="get">
		<table><tr><td>Lager:</td>
		<td>
			<select name="lagernr" style="width: 200px;">
			<%
				ResultSet rsk = con.createStatement().executeQuery("SELECT lagernr, bnamn FROM lagerid");
				while( rsk.next() ) {
					out.print("<option value=\"" + rsk.getInt(1) + "\"");
					if (lagernr == (rsk.getInt(1))) out.print(" selected=\"selected\"");
					out.print(">" + SXUtil.toHtml(rsk.getString(2)) + "</option>");
				}
				%>
			</select> <br/>
		</td></tr>
		<tr>
			<td>Sortera</td>
			<td>
				<input type="radio" name="orderby" value="<%= OB_LAGERKOSTNAD %>" <% if (OB_LAGERKOSTNAD.equals(orderby)) out.print(checkedStr); %>/>Lagerkostnad<br/>
				<input type="radio" name="orderby" value="<%= OB_VARDE %>" <% if (OB_VARDE.equals(orderby)) out.print(checkedStr); %>/>Lagerv�rde<br/>
				<input type="radio" name="orderby" value="<%= OB_TID %>" <% if (OB_TID.equals(orderby)) out.print(checkedStr); %>/>Oms�ttningstid<br/>
			</td>
		</tr>
		<tr><td colspan="2">
		<%
			Enumeration e = request.getParameterNames();
			while (e.hasMoreElements()) {
				p = (String)e.nextElement();
				if (p!=null && !"inputform".equals(p)) {
					%><input type="hidden" name="<%= p %>" value="<%= request.getParameter(p) %>"/><%
				}
			}
	%>
   <input type="submit" value="Visa" />
	</td></tr></table>
</form>

<% } %>
