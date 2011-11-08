/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.rpcobject.Artikel;

/**
 *
 * @author Ulf
 */
public class ArtikelInfoSheetWidget extends InfoSheetWidget<Artikel>{

		ArtikelFormWidget formWidget;

	public ArtikelInfoSheetWidget()  {
		super();
	}
	public ArtikelInfoSheetWidget(PageLoad<Artikel> pageLoad)  {
		super(pageLoad);
	}

	public ArtikelFormWidget getForm() { return formWidget; }

	protected void setup() {
	//	OffertListaWidget offertListWidget = new OffertListaWidget(false);
	//	OrderListaWidget orderListWidget = new OrderListaWidget(false);
		OrderViewListWidget orderViewListWidget = new OrderViewListWidget();

		formWidget = new ArtikelFormWidget(pageLoad);
		addData2FormWidget(formWidget, formWidget, "Artikelinfo");
		addData2FormWidget(orderViewListWidget, orderViewListWidget, "Order");


//		addData2FormWidget(orderListWidget, orderListWidget, "Order");
//		addData2FormWidget(offertListWidget, offertListWidget, "Offerter");

	}

}
