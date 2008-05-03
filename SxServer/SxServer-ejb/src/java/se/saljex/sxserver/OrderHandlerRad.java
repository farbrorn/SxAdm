/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import java.util.Date;

/**
 *
 * @author ulf
 */
public class OrderHandlerRad {
	public Integer ordernr = 0;
	public Short pos = 0;
	public Short prisnr = 0;
	public Short dellev = 0;
	public String artnr = new String();
	public String namn = new String();
	public String levnr = new String(); 
	public Double best = 0.0;
	public Double rab = 0.0;
	public Double lev = 0.0;
	public String text = new String();
	public Double pris = 0.0;
	public Double summa = 0.0;
	public String konto = new String();
	public Double netto = 0.0;
	public String enh = new String();
	public Date levdat = new Date();
	public Date utskrivendatum = new Date();
	public Date utskriventid = new Date();
	public Integer stjid = 0;
	
	public void setAll(TableOrder2 o) {
		ordernr = o.tableOrder2PK.getOrdernr();
		pos = o.tableOrder2PK.getPos();
		prisnr = o.getPrisnr();
		dellev = o.getDellev();
		artnr = o.getArtnr();
		namn = o.getNamn();
		levnr = o.getLevnr();
		best = o.getBest();
		rab = o.getRab();
		lev = o.getLev();
		text = o.getText();
		pris = o.getPris();
		summa = o.getSumma();
		konto = o.getKonto();
		netto = o.getNetto();
		enh = o.getEnh();
		levdat = o.getLevdat();
		utskrivendatum = o.getUtskrivendatum();
		utskriventid = o.getUtskriventid();
		stjid = o.getStjid();
	}
	
	//Returnera raden som ett TabelOrder2 objekt.
	public TableOrder2 getOrder2() {
		TableOrder2 o = new TableOrder2();

		o.tableOrder2PK = new TableOrder2PK(ordernr,pos);
		o.setPrisnr(prisnr);
		o.setDellev(dellev);
		o.setArtnr(artnr);
		o.setNamn(namn);
		o.setLevnr(levnr);
		o.setBest(best);
		o.setRab(rab);
		o.setLev(lev);
		o.setText(text);
		o.setPris(pris);
		o.setSumma(summa);
		o.setKonto(konto);
		o.setNetto(netto);
		o.setEnh(enh);
		o.setLevdat(levdat);
		o.setUtskrivendatum(utskrivendatum);
		o.setUtskriventid(utskriventid);
		o.setStjid(stjid);
		
		return o;
	}
}
