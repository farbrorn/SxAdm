<%-- 
    Document   : leftsidebar
    Created on : 2009-feb-09, 11:44:05
    Author     : ulf
--%>

<%
//String intraUser = (String)request.getAttribute("intrauser");
String divInfo = (String)request.getAttribute("divinfo");
if (divInfo == null) divInfo = "";
%>
<div <%= divInfo %>>
<a href="?id=status">Serverstatus</a><br/>
<a href="?id=updateartikel">Uppdatera Artikel p� web</a><br/>
<a href="?id=updateartikeltrad">Uppdatera Artikeltr�d p� web</a><br/>
<a href="?id=updatelagersaldon">Uppdatera lagersaldon lokalt</a><br/>
<a href="?id=inlagg">Hantera inl�gg</a><br/>

</div>