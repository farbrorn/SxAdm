/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import se.saljex.SxShop.client.rpcobject.SokResultKlase;
import se.saljex.SxShop.client.rpcobject.ArtSidaKlaseArtikel;
import se.saljex.SxShop.client.rpcobject.ArtSidaKlase;
import se.saljex.SxShop.client.rpcobject.ArtSida;
import se.saljex.SxShop.client.rpcobject.SokResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;

/**
 *
 * @author ulf
 */
public class ArtikelMainPanel extends VerticalPanel implements KopKnappCallback{

	private NumberFormat numberFormat = NumberFormat.getFormat("0.00");
	private NumberFormat numberFormatInt = NumberFormat.getFormat("0");
	private ArtikelVarukorg varukorg=null;

	private ArtikelPanel artikelPanel;
	VantaDialogBox vantaDialogBox = new VantaDialogBox(); //Modal dialog
	KopDialogBox kopDialogBox;

	public ArtikelMainPanel(ArtikelPanel artikelPanel) {
		addStyleName("sx-artikelmainpanel");
		this.artikelPanel = artikelPanel;
		kopDialogBox=new KopDialogBox(artikelPanel);
	}
/*
	public void setVarukorg(ArtikelVarukorg varukorg) {
		this.varukorg=varukorg;
	}
	public void showVarukorg() {
		if (varukorg!=null) varukorg.setVisible(true);
	}
	public void hideVarukorg() {
		if (varukorg!=null) varukorg.setVisible(false);
	}
	public boolean isVarukorgVisible() {
		if (varukorg!=null) return varukorg.isVisible(); else return false;
	}
	private void addVarukorgIfVisible() {
		if (isVarukorgVisible()) add(varukorg);
	}
*/
	public void fillError(String err) {
		clear();
//		addVarukorgIfVisible();
		add(new Label(err));
	}

	public void fillWidget (Widget w) {
		clear();
//		addVarukorgIfVisible();
		add(w);
	}

	public void fill(final SokResult sokResult) {
		clear();
//		addVarukorgIfVisible();
		add(new Label("Sökresultat för " + sokResult.sokStr));
		for (SokResultKlase sk : sokResult.sokResultKlasar) {
			add(new Label(sk.plats));
			printKlase(sk.artSidaKlase);
		}
		if (sokResult.merRaderFinns) {
			Anchor a=new Anchor("Fler rader finns i sökningen. Klicka här för att visa alla.");
			a.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
						artikelPanel.getService().getSokResult(sokResult.sokStr, 100, artikelPanel.callbackSok);
			}
			});
			add(a);
		}
	}

	public void fill(ArtSida artSida) {
		fill(artSida, null);
	}
	public void fill(ArtSida artSida, Integer filterKlasid) {
			clear();
//			addVarukorgIfVisible();

			String namn;
			Grid klaseTextGrid;

			final String GRPRUBRIK = "sx-huvudrubrik";
			final String KLASERUBRIK = "sx-klaserubrik";
			Label l;

			l = new Label(artSida.rubrik);
			l.addStyleName(GRPRUBRIK);
			add(l);

			l = new Label(artSida.text);
			add(l);

			l = new Label(artSida.infourl);
			add(l);

			for (ArtSidaKlase ask : artSida.klasar) {
				if (filterKlasid==null || filterKlasid.equals(ask.klasid)) {
					printKlase(ask);
/*					FlexTable ft;
					ft = new FlexTable();
					FlexTable.FlexCellFormatter cellFormatter = ft.getFlexCellFormatter();
					FlexTable.RowFormatter rowFormatter=ft.getRowFormatter();
					//ft.setWidth("100%");
					ft.setCellPadding(1);
					ft.setCellSpacing(0);
					ft.setWidget(0, 0, new Label("Art.nr"));
					ft.setWidget(0, 1, new Label("Benämning"));
					ft.setWidget(0, 2, new Label("Pris"));
					ft.setWidget(0, 3, new Label("Enh"));
					ft.setWidget(0, 4, new Label("Mängdpris"));
					ft.setWidget(0, 5, new Label("Mängd"));
					ft.setWidget(0, 6, new Label("Rab"));
					ft.setWidget(0, 7, new Label(" "));
					rowFormatter.addStyleName(0, "sx-tablerubrik");
					cellFormatter.addStyleName(0, 0, "sx-tb-artnr");
					cellFormatter.addStyleName(0, 1, "sx-tb-benamning");
					cellFormatter.addStyleName(0, 2, "sx-tb-pris");
					cellFormatter.addStyleName(0, 3, "sx-tb-enhet");
					cellFormatter.addStyleName(0, 4, "sx-tb-pris");
					cellFormatter.addStyleName(0, 5, "sx-tb-mangd");
					cellFormatter.addStyleName(0, 6, "sx-tb-rab");
					cellFormatter.addStyleName(0, 7, "sx-tb-kop");

					int rowCn=1;

					l = new Label(ask.rubrik);
					l.addStyleName(KLASERUBRIK);
					add(l);
					l = new Label(ask.text);
					add(l);
					l = new Label(ask.infourl);
					add(l);
					for (ArtSidaKlaseArtikel aska : ask.artiklar) {
						ft.setWidget(rowCn, 0,new Label(aska.nummer));
						if (aska.katnamn==null || aska.katnamn.isEmpty()) namn=aska.namn; else namn=aska.katnamn;
						ft.setWidget(rowCn, 1,new Label(namn));
						ft.setWidget(rowCn, 2,new Label(numberFormat.format(aska.utpris)));
						ft.setWidget(rowCn, 3, new Label(aska.enhet));
						if (aska.staf_pris1.equals(0.0)) {
							ft.setHTML(rowCn, 4,"");
							ft.setHTML(rowCn, 5,"");
						} else {
							ft.setWidget(rowCn, 4,new Label(numberFormat.format(aska.staf_pris1)));
							ft.setWidget(rowCn, 5,new Label(numberFormatInt.format(aska.staf_antal1)+ " " + aska.enhet));
						}
						ft.setWidget(rowCn, 6, new Label(aska.rabkod));
						cellFormatter.addStyleName(rowCn, 2, "sx-tb-pris");
						cellFormatter.addStyleName(rowCn, 4, "sx-tb-pris");
						KopKnapp kopKnapp = new KopKnapp(aska,this);
						ft.setWidget(rowCn, 7,kopKnapp );
						if (rowCn%2 > 0 ) rowFormatter.addStyleName(rowCn, "sx-highlite");
						rowCn++;

					}
					add(ft); */
				}
			}
	}


public void printKlase(ArtSidaKlase ask) {
					String namn;

					final String GRPRUBRIK = "sx-huvudrubrik";
					final String KLASERUBRIK = "sx-klaserubrik";
					Label l;
					FlexTable ft;
					ft = new FlexTable();
					FlexTable.FlexCellFormatter cellFormatter = ft.getFlexCellFormatter();
					FlexTable.RowFormatter rowFormatter=ft.getRowFormatter();
					//ft.setWidth("100%");
					ft.setCellPadding(1);
					ft.setCellSpacing(0);
					ft.setWidget(0, 0, new Label("Art.nr"));
					ft.setWidget(0, 1, new Label("Benämning"));
					ft.setWidget(0, 2, new Label("Pris"));
					ft.setWidget(0, 3, new Label("Enh"));
					ft.setWidget(0, 4, new Label("Mängdpris"));
					ft.setWidget(0, 5, new Label("Mängd"));
					ft.setWidget(0, 6, new Label("Rab"));
					ft.setWidget(0, 7, new Label(" "));
					rowFormatter.addStyleName(0, "sx-tablerubrik");
					cellFormatter.addStyleName(0, 0, "sx-tb-artnr");
					cellFormatter.addStyleName(0, 1, "sx-tb-benamning");
					cellFormatter.addStyleName(0, 2, "sx-tb-pris");
					cellFormatter.addStyleName(0, 3, "sx-tb-enhet");
					cellFormatter.addStyleName(0, 4, "sx-tb-pris");
					cellFormatter.addStyleName(0, 5, "sx-tb-mangd");
					cellFormatter.addStyleName(0, 6, "sx-tb-rab");
					cellFormatter.addStyleName(0, 7, "sx-tb-kop");

					int rowCn=1;

					l = new Label(ask.rubrik);
					l.addStyleName(KLASERUBRIK);
					add(l);
					l = new Label(ask.text);
					add(l);
					l = new Label(ask.infourl);
					add(l);
					for (ArtSidaKlaseArtikel aska : ask.artiklar) {
						ft.setWidget(rowCn, 0,new Label(aska.nummer));
						if (aska.katnamn==null || aska.katnamn.isEmpty()) namn=aska.namn; else namn=aska.katnamn;
						ft.setWidget(rowCn, 1,new Label(namn));
						ft.setWidget(rowCn, 2,new Label(numberFormat.format(aska.utpris)));
						ft.setWidget(rowCn, 3, new Label(aska.enhet));
						if (aska.staf_pris1.equals(0.0)) {
							ft.setHTML(rowCn, 4,"");
							ft.setHTML(rowCn, 5,"");
						} else {
							ft.setWidget(rowCn, 4,new Label(numberFormat.format(aska.staf_pris1)));
							ft.setWidget(rowCn, 5,new Label(numberFormatInt.format(aska.staf_antal1)+ " " + aska.enhet));
						}
						ft.setWidget(rowCn, 6, new Label(aska.rabkod));
						cellFormatter.addStyleName(rowCn, 2, "sx-tb-pris");
						cellFormatter.addStyleName(rowCn, 4, "sx-tb-pris");
						KopKnapp kopKnapp = new KopKnapp(aska,this);
						ft.setWidget(rowCn, 7,kopKnapp );
						if (rowCn%2 > 0 ) rowFormatter.addStyleName(rowCn, "sx-highlite");
						rowCn++;

					}
					add(ft);

}


public void kopKnappGetArtikelCallback(final ArtSidaKlaseArtikel artikel) {
	//Anropas när en köpknapp trycks.
	kopDialogBox.setArtikel(artikel);
	kopDialogBox.center();
}




public class KopDialogBox extends DialogBox {
		ArtSidaKlaseArtikel artikel=null;
		Label artikelText= new Label();
		final TextBox antal = new TextBox();
		Label minsaljpack= new Label();
		Label forpack= new Label();
		Label utpris= new Label();
		Label staf_pris1= new Label();
		Label prisdatum= new Label();
		Label rab= new Label();
		Label errortext= new Label();
		Grid grid = new Grid(7, 2);
		Button btnOk = new Button("OK", new ClickHandler() {
			public void onClick(ClickEvent event) {
				kopDialogBoxHandleOK();
			}
			});
		Button btnAvbryt = new Button("Avbryt", new ClickHandler() {
			public void onClick(ClickEvent event) {
				kopDialogBox.hide();
			}
			});

	public KopDialogBox(ArtikelPanel artikelPanel) {
		super(false,true);

		this.setText("Köp produkt");
		VerticalPanel vp = new VerticalPanel();
		HorizontalPanel hp = new HorizontalPanel();

		errortext.addStyleName("sx-feltext");
		vp.add(errortext);
		vp.add(artikelText);
		hp.add(new Label("Antal: "));
		hp.add(antal);
		vp.add(hp);

		grid.setText(0, 0, "Förpackning");
		grid.setWidget(0, 1, forpack);
		grid.setText(1, 0, "Minsta odelbara enhet");
		grid.setWidget(1, 1, minsaljpack);
		grid.setText(2, 0, "Pris");
		grid.setWidget(2, 1, utpris);
		grid.setText(3, 0, "Mängdpris");
		grid.setWidget(3, 1, staf_pris1);
		grid.setText(4, 0, "Prisdatum");
		grid.setWidget(4, 1, prisdatum);
		grid.setText(5, 0, "Rabattgrupp");
		grid.setWidget(5, 1, rab);
		vp.add(grid);
		hp = new HorizontalPanel();
		hp.add(btnAvbryt);
		hp.add(btnOk);
		vp.add(hp);

		add(vp);
//		center();

	}



	public void setArtikel(ArtSidaKlaseArtikel artikel) {
		this.artikel=artikel;
		artikelText.setText(artikel.nummer + " " + artikel.namn);
		antal.setValue(numberFormat.format(artikel.forpack));
		forpack.setText(numberFormat.format(artikel.forpack)+ " " + artikel.enhet);
		minsaljpack.setText(numberFormat.format(artikel.minsaljpack) + " " + artikel.enhet);
		utpris.setText(numberFormat.format(artikel.utpris) + "/" + artikel.enhet);
		if (!((Double)0.0).equals(artikel.staf_pris1)) {
			staf_pris1.setText(numberFormat.format(artikel.staf_pris1) + "/" + artikel.enhet + " vid " + numberFormatInt.format(artikel.staf_antal1) + " " + artikel.enhet);
		} else {
			staf_pris1.setText("");
		}
		prisdatum.setText(DateTimeFormat.getMediumDateFormat().format(artikel.prisdatum ));
		rab.setText(artikel.rabkod + artikel.kod1!=null || artikel.kod1.isEmpty() ? " - " + artikel.kod1 : "");
		errortext.setText("");
	}

	public void setErrorText(String err) {
		errortext.setText(err);
	}

	private void kopDialogBoxHandleOK() {
		setErrorText("");
		Double antalDouble=null;
		try {
			antalDouble = new Double(antal.getValue());
		} catch (NumberFormatException e) { }
		if (antalDouble!=null && antalDouble.compareTo(0.0)>0) {
			if (artikel.minsaljpack!=null && artikel.minsaljpack > 0 && antalDouble % artikel.minsaljpack >0){
				setErrorText("Antalet går ej jämt upp i minsta odelbara förpackning. Kontrollera antal och enhet.");
			} else {
				vantaDialogBox.show();
				artikelPanel.getService().addVaruKorg(artikel.nummer, antalDouble, callbackVaruKorg );
			}
		} else {
			setErrorText("Felaktigt antal.");
		}

	}

	final AsyncCallback callbackVaruKorg = new AsyncCallback() {
		public void onSuccess(Object result) {
			artikelPanel.updateVarukorg((ArrayList)result);
			vantaDialogBox.hide();
			kopDialogBox.hide();
		}

		public void onFailure(Throwable caught) {
			vantaDialogBox.hide();
			setErrorText("Fel: " + caught.toString());
		}
	};


	}


}

