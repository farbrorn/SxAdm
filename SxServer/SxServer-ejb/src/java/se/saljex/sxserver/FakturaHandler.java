/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import java.util.ArrayList;
import java.util.Calendar;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import se.saljex.sxserver.tables.TableArtikel;
import se.saljex.sxserver.tables.TableArtstat;
import se.saljex.sxserver.tables.TableArtstatPK;
import se.saljex.sxserver.tables.TableBetjour;
import se.saljex.sxserver.tables.TableBetjourPK;
import se.saljex.sxserver.tables.TableBokord;
import se.saljex.sxserver.tables.TableBokordPK;
import se.saljex.sxserver.tables.TableBonus;
import se.saljex.sxserver.tables.TableBonusPK;
import se.saljex.sxserver.tables.TableBonusbet;
import se.saljex.sxserver.tables.TableBonusbetPK;
import se.saljex.sxserver.tables.TableFaktorut;
import se.saljex.sxserver.tables.TableFaktura1;
import se.saljex.sxserver.tables.TableFaktura2;
import se.saljex.sxserver.tables.TableFaktura2PK;
import se.saljex.sxserver.tables.TableFuppg;
import se.saljex.sxserver.tables.TableKund;
import se.saljex.sxserver.tables.TableKundres;
import se.saljex.sxserver.tables.TableKunstat;
import se.saljex.sxserver.tables.TableKunstatPK;
import se.saljex.sxserver.tables.TableLager;
import se.saljex.sxserver.tables.TableLagerPK;
import se.saljex.sxserver.tables.TableLagerhand;
import se.saljex.sxserver.tables.TableLev;
import se.saljex.sxserver.tables.TableLevstat;
import se.saljex.sxserver.tables.TableLevstatPK;
import se.saljex.sxserver.tables.TableOrder1;
import se.saljex.sxserver.tables.TableOrderhand;
import se.saljex.sxserver.tables.TableRanta;
import se.saljex.sxserver.tables.TableRantaPK;
import se.saljex.sxserver.tables.TableSaljare;
import se.saljex.sxserver.tables.TableSljstat;
import se.saljex.sxserver.tables.TableSljstatPK;
import se.saljex.sxserver.tables.TableStatistik;
import se.saljex.sxserver.tables.TableStatistikPK;
import se.saljex.sxserver.tables.TableStjarnrad;

/**
 *
 * @author ulf
 */
public class FakturaHandler {

	private EntityManager em;
	private OrderHandler orderHandler;
	private String anvandare = null;
	private TableFaktura1 fa1 = new TableFaktura1();
	private ArrayList<FaktRad> faktRadList = new ArrayList();

	public FakturaHandler(EntityManager e, String anvandare) {
		this.em = e;
		setAnvandare(anvandare);
	}

	public int faktureraOrder(int ordernr) throws SxOrderLastException{
		OrderHandler orh = new OrderHandler(em, ordernr, anvandare);
		prepareFaktura(orh);
		int faktnr  = persistFaktura();
		em.persist( new TableOrderhand(orh.getOrdernr(), anvandare, SXConstant.ORDERHAND_FAKTURERAD));
		orh.deleteOrder(false);
		return faktnr;
	}

	public void prepareFaktura(OrderHandler orh) {

		FaktRad faktRad;

		TableOrder1 or1 = orh.getTableOrder1();
		fa1.setAdr1(or1.getAdr1());
		fa1.setAdr2(or1.getAdr2());
		fa1.setAdr3(or1.getAdr3());
		fa1.setBonus(or1.getBonus());
		fa1.setDirektlevnr(or1.getDirektlevnr());
		fa1.setFaktor(or1.getFaktor());
		fa1.setFraktbolag(or1.getFraktbolag());
		fa1.setFraktfrigrans(or1.getFraktfrigrans());
		fa1.setFraktkundnr(or1.getFraktkundnr());
		fa1.setFraktkundnr(or1.getFraktkundnr());
		fa1.setKtid(or1.getKtid());
		fa1.setKundnr(or1.getKundnr());
		fa1.setLagernr(or1.getLagernr());
		fa1.setLevadr1(or1.getLevadr1());
		fa1.setLevadr2(or1.getLevadr2());
		fa1.setLevadr3(or1.getLevadr3());
		fa1.setMarke(or1.getMarke());
		fa1.setMoms(or1.getMoms());
		fa1.setNamn(or1.getNamn());
		fa1.setOrderdat(or1.getDatum());
		fa1.setReferens(or1.getReferens());
		fa1.setSaljare(or1.getSaljare());

		TableFaktura2 fa2;
		for (OrderHandlerRad rad : orh.getOrdreg()) {
			fa2 = new TableFaktura2();
			fa2.setArtnr(rad.artnr);
			fa2.setEnh(rad.enh);
			fa2.setKonto(rad.konto);
			fa2.setLev(rad.lev);
			fa2.setNamn(rad.namn);
			fa2.setNetto(rad.netto);
			fa2.setOrdernr(or1.getOrdernr());
			fa2.setPris(rad.pris);
			fa2.setPrisnr(rad.prisnr);
			fa2.setRab(rad.rab);
			fa2.setStjid(rad.stjid);
			fa2.setSumma(rad.summa);

			fa2.setText(null);

			//När vi hämtar från orderså ska det inte finnas någon ränterad
			fa2.setRantaproc(0);
			fa2.setRantafalldatum(null);
			fa2.setRantafakturanr(0);
			fa2.setRantabetaldatum(null);
			fa2.setRantabetalbelopp(0);

			//När vi hämtar från order så ska det inte finnas någon bonusrad
			fa2.setBonNr(0);

			faktRad = new FaktRad();
			faktRad.fa2 = fa2;


			/* TO-DO */
			//Här ska vi hämta ränta och bonusar. Inte implementerad ännu,
			//faktRad.bonusId=???		sätt här bonus id om det är en bonusrad som vi hämtat

			faktRadList.add(faktRad);
		}
	}

	//Nuvarande funktion klarar inte att ta fram ränta och bonusar. Något för framtiden kanske...
	public int persistFaktura()  {
		
		TableBokord bokord;
		TableBokordPK bokordPK;
		TableFuppg fup;
		TableLagerhand lah;
		TableLager lag;
		TableLagerPK lagPK;
		TableArtstat artstat;
		TableArtstatPK artstatPK;
		TableLev lev;
		TableArtikel art;
		TableLevstat levstat;
		TableLevstatPK levstatPK;
		TableKundres kundres;
		TableFaktorut faktorut;
		TableBetjour betjour;
		TableKund kund;
		TableKunstat kunstat;
		TableKunstatPK kunstatPK;
		TableSaljare saljare;
		TableSljstat sljstat;
		TableSljstatPK sljstatPK;
		TableStatistik statistik;
		TableStatistikPK statistikPK;
		TableBonus bonus;
		TableBonusPK bonusPK;
		TableBonusbet bonusbet;
		TableBonusbetPK bonusbetPK;
		TableRanta ranta;
		TableRantaPK rantaPK;
		
		
		Calendar calendar = Calendar.getInstance();
		
		kund = em.find(TableKund.class, fa1.getKundnr());
		if (kund==null) { throw new EntityNotFoundException("Kund saknas vid försök att spara faktura"); }
		double t_innetto = 0;
		double t_netto=0;
		fup = (TableFuppg)em.createNamedQuery("TableFuppg.findAll").getSingleResult();

		TableFaktura2 fa2;
		for (FaktRad faktRad : faktRadList) {
			fa2 = faktRad.fa2;
			t_innetto = t_innetto+ (fa2.getNetto()*fa2.getLev());
			t_netto = t_netto + fa2.getSumma();
		}

		if (fa1.getMoms() == 0)	fa1.setMomsproc(0);
		else if (fa1.getMoms() == 1) fa1.setMomsproc(fup.getMoms1());
		else if (fa1.getMoms() == 2) fa1.setMomsproc(fup.getMoms2());
		else if (fa1.getMoms() == 3) fa1.setMomsproc(fup.getMoms3());
		else throw new EntityNotFoundException("Otillåten moms: Moms nr " + fa1.getMoms());

		Integer faktnr;
		faktnr = (Integer)em.createNativeQuery("select max(faktnr) from faktura1").getSingleResult();
		faktnr++;
		java.util.Date datum = new java.util.Date();

		fa1.setFaktnr(faktnr);
		fa1.setDatum(datum);

		if (fup.getPantsattfakturor()!=0) fa1.setFaktor((short)0);	//!Ingen faktoring om vi pantsätter fakturan
		if (fa1.getBonus()!=(short)0) {
			fa1.setText1(fup.getBonText1());
			fa1.setText2(fup.getBonText2());
			fa1.setText3(fup.getBonText3());
			fa1.setText4(fup.getBonText4());
			fa1.setText5(fup.getBonText5());
		} else {
			fa1.setText1(fup.getEbText1());
			fa1.setText2(fup.getEbText2());
			fa1.setText3(fup.getEbText3());
			fa1.setText4(fup.getEbText4());
			fa1.setText5(fup.getEbText5());
		}
		if (fup.getTempText()!=0) {
			fa1.setText1(fup.getTempText1());
			fa1.setText2(fup.getTempText2());
			fa1.setText3(fup.getTempText3());
			fa1.setText4(fup.getTempText4());
			fa1.setText5(fup.getTempText5());
		}
		if (fa1.getFaktor()!=0) {
			fa1.setFaktortext1(fup.getFaktortext1());
			fa1.setFaktortext2(fup.getFaktortext2());
			fa1.setFaktortext3(fup.getFaktortext3());
		} else if (fup.getPantsattfakturor()!=0) {
			fa1.setFaktortext1(fup.getPantsatttext1());
			fa1.setFaktortext2(fup.getPantsatttext2());
			fa1.setFaktortext3(fup.getPantsatttext3());
		}
		fa1.setRanta(fup.getDroj());

		fa1.setTNetto(t_innetto);
		fa1.setTInnetto(t_netto);


		fa1.setTMoms(SXUtil.getRoundedDecimal(t_netto*fa1.getMomsproc()/100));
		fa1.setTOrut(SXUtil.getRoundedDecimal( Math.round(t_netto + fa1.getTMoms()) - (fa1.getTNetto()+fa1.getTMoms()) ));
		fa1.setTAttbetala(SXUtil.getRoundedDecimal(fa1.getTNetto() + fa1.getTOrut() + fa1.getTMoms()));

		fa1.setRantfakt((short)0);

		//Dessa fält nollas initialt och används inte vid skapandet av fakturan
		fa1.setInkassostatus(null);
		fa1.setInkassodatum(null);

		//Dessa fält börjar bli gamla och fyller ingen funktion längre
		fa1.setLevvillkor(null);
		fa1.setLevdat(null);
		fa1.setMottagarfrakt((short)0);

		em.persist(fa1);

		short rad=0;
		int cn;
		for (FaktRad faktRad : faktRadList) {
			fa2 = faktRad.fa2;
			rad++;
			fa2.setTableFaktura2PK(new TableFaktura2PK(faktnr, rad));
			em.persist(fa2);

		   //!*** Spara Artikel
			if (!SXUtil.isEmpty(fa2.getArtnr()) && !"*RÄNTA*".equals(fa2.getArtnr()) && !"*BONUS*".equals(fa2.getArtnr())) {
				datum = new java.util.Date();
				lah = new TableLagerhand(fa2.getArtnr(), fa1.getLagernr(), datum, datum);
				lah.setAnvandare(anvandare);
				lah.setHandelse("Fakturerad");
				lah.setForandring(-fa2.getLev());
				lah.setStjid(fa2.getStjid());
				lah.setOrdernr(fa2.getOrdernr());

				for (cn=0 ; cn < 20; cn++) {
					if (em.find(TableLagerhand.class, lah.getTableLagerhandPK()) == null) break;
					calendar.setTime(lah.getTableLagerhandPK().getTid());
					calendar.add(Calendar.SECOND, 1);
					lah.getTableLagerhandPK().setTid(calendar.getTime());
				}
				em.persist(lah);

				lagPK = new TableLagerPK(fa2.getArtnr(), fa1.getLagernr());
				lag = em.find(TableLager.class, lagPK);
				if (lag==null) {
					lag = new TableLager(lagPK);
					em.persist(lag);
				}
				lag.setIlager(lag.getIlager() - fa2.getLev());




				//*** Spara Artikelstatistik
				calendar.setTime(fa1.getDatum());
				artstatPK = new TableArtstatPK(fa2.getArtnr(), (short)calendar.get(Calendar.YEAR), (short)calendar.get(Calendar.MONTH));
				artstat = em.find(TableArtstat.class, artstatPK);
				if (artstat == null) {
					artstat = new TableArtstat(artstatPK);
					em.persist(artstat);
				}
				artstat.setSalda(artstat.getSalda() + fa2.getLev());
				artstat.setTbidrag(artstat.getTbidrag() + fa2.getSumma() - (fa2.getNetto()*fa2.getLev()));

				//Spara stjärnradinfo
				if (fa2.getStjid() != 0 && fa2.getArtnr().startsWith("*")) {
					TableStjarnrad stj;
					stj = em.find(TableStjarnrad.class, fa2.getStjid());
					if (stj != null) {
						stj.setFakturanr(fa1.getFaktnr());
					}
				}

				//*** Spara Levstatistik
				art = em.find(TableArtikel.class, fa2.getArtnr());
				if (art != null) {
					lev = em.find(TableLev.class, art.getLev());
					if (lev != null) {
						levstatPK = new TableLevstatPK(lev.getNummer(), (short)calendar.get(Calendar.YEAR), (short)calendar.get(Calendar.MONTH));
						levstat = em.find(null, levstatPK);
						if (levstat == null) {
							levstat = new TableLevstat(levstatPK);
							em.persist(levstat);
						}
						levstat.setFtot(levstat.getFtot() + fa2.getSumma());
						levstat.setFtbidrag(levstat.getFtbidrag() + fa2.getSumma() - (fa2.getNetto()*fa2.getLev()));


					}

				}


				if (!((Double)0.0).equals(fa2.getSumma())) {
					if (SXUtil.isEmpty(fa2.getKonto())) fa2.setKonto("3011");
					bokordPK = new TableBokordPK(fa2.getKonto(), fa1.getFaktnr(), "F", fa1.getDatum());
					bokord = em.find(TableBokord.class, bokordPK);
					if (bokord == null) {
						bokord = new TableBokord(bokordPK);
						em.persist(bokord);
					}
					bokord.setKundnr(fa1.getKundnr());
					calendar.setTime(fa1.getDatum());
					bokord.setAr((short)calendar.get(Calendar.YEAR));
					bokord.setMan((short)calendar.get(Calendar.MONTH));
					bokord.setNamn(fa1.getNamn());
					bokord.setSumma(bokord.getSumma() - SXUtil.getRoundedDecimal(fa2.getSumma()));
				}

				//Spara bonus
				if (fa2.getBonNr() != 0) {
					bonusbet=null;
					bonusPK = new TableBonusPK(cn, faktRad.bonusId);
					bonus = em.find(TableBonus.class, bonusPK);
					if (bonus == null) throw new EntityNotFoundException("Bonus på faktura " + fa2.getBonNr() + " id: " + faktRad.bonusId + " finns inte men försökte sparas.");
					bonusbetPK = new TableBonusbetPK(fa1.getKundnr(), fa1.getFaktnr(), (short)0);
					for (short x = 0; x < 200; x++) {
						bonusbetPK.setId(x);
						bonusbet = em.find(TableBonusbet.class, bonusbetPK);
						if (bonusbet == null) break;
					}
					if (bonusbet!=null)  throw new EntityExistsException("Kan inte spara bonus eftersom bonusen redan finns sparad i bonusbet. Kundnr " + fa1.getKundnr() + " faktnr " + fa1.getFaktnr() + " högsta testade id " + bonusbetPK.getId());
					bonusbet = new TableBonusbet(bonusbetPK);
					bonusbet.setUtdatum(fa1.getDatum());
					bonusbet.setUtfaktura(fa1.getFaktnr());
					bonusbet.setBonus(fa2.getSumma());
					em.persist(bonusbet);
					em.remove(bonus);

				}

				//Ränta
				if (fa2.getRantafakturanr() != 0) {
					rantaPK = new TableRantaPK(fa1.getKundnr(), fa1.getFaktnr(), fa2.getRantabetaldatum());
					ranta = em.find(TableRanta.class, rantaPK);
					if (ranta==null) throw new EntityNotFoundException("Räntepost för faktura " + fa1.getFaktnr() + " rantabetdat " + SXUtil.getFormatDate(fa2.getRantabetaldatum()) + " finns inte i tabell Ranta men hänvisas till i fakturan");
					em.remove(ranta);
				}

			}//Spara artikel
		} //for (TableFaktura2 fa2 : fa2List)


		bokordPK = null;
		if (fa1.getMoms() == 1) bokordPK = new TableBokordPK(fup.getMoms1k(), fa1.getFaktnr(), "F", fa1.getDatum());
		else if(fa1.getMoms() == 2) bokordPK = new TableBokordPK(fup.getMoms2k(), fa1.getFaktnr(), "F", fa1.getDatum());
		else if(fa1.getMoms() == 3) bokordPK = new TableBokordPK(fup.getMoms3k(), fa1.getFaktnr(), "F", fa1.getDatum());
		if (bokordPK != null) {
			bokord = new TableBokord(bokordPK);
			bokord.setSumma(-fa1.getTMoms());
			bokord.setKundnr(fa1.getKundnr());
			bokord.setNamn(fa1.getNamn());
			calendar.setTime(fa1.getDatum());
			bokord.setAr((short)calendar.get(Calendar.YEAR));
			bokord.setMan((short)calendar.get(Calendar.MONTH));
			em.persist(bokord);
		}

		bokord = new TableBokord(fup.getKundfk(), fa1.getFaktnr(), "F", fa1.getDatum());
		bokord.setSumma(fa1.getTAttbetala());
		bokord.setKundnr(fa1.getKundnr());
		bokord.setNamn(fa1.getNamn());
		calendar.setTime(fa1.getDatum());
		bokord.setAr((short)calendar.get(Calendar.YEAR));
		bokord.setMan((short)calendar.get(Calendar.MONTH));
		em.persist(bokord);

		bokord = new TableBokord(fup.getOrutk(), fa1.getFaktnr(), "F", fa1.getDatum());
		bokord.setSumma(fa1.getTOrut());
		bokord.setKundnr(fa1.getKundnr());
		bokord.setNamn(fa1.getNamn());
		calendar.setTime(fa1.getDatum());
		bokord.setAr((short)calendar.get(Calendar.YEAR));
		bokord.setMan((short)calendar.get(Calendar.MONTH));
		em.persist(bokord);

		if (!((Double)0.0).equals(fa1.getTAttbetala())) {
			kundres = new TableKundres();
			kundres.setFaktnr(fa1.getFaktnr());
			kundres.setDatum(fa1.getDatum());
			kundres.setTot(fa1.getTAttbetala());
			kundres.setNetto(fa1.getTNetto());
			if (!((Double)0.0).equals(fa1.getTNetto())) kundres.setMedelmomsproc(SXUtil.getRoundedDecimal(fa1.getTMoms() / fa1.getTNetto() * 100));
			else kundres.setMedelmomsproc(0.0);
			kundres.setKundnr(fa1.getKundnr());
			kundres.setNamn(fa1.getNamn());
			calendar.setTime(fa1.getDatum());
			calendar.add(Calendar.DAY_OF_MONTH, fa1.getKtid());
			kundres.setFalldat(calendar.getTime());
			kundres.setFaktor(fa1.getFaktor());
			kundres.setBonus(fa1.getBonus());
			kundres.setPantsatt(fup.getPantsattfakturor());
			em.persist(kundres);

			if (fa1.getFaktor() != 0) {
				faktorut = new TableFaktorut();
				faktorut.setFaktnr(fa1.getFaktnr());
				faktorut.setDatum(fa1.getDatum());
				faktorut.setTot(fa1.getTAttbetala());
				faktorut.setKundnr(fa1.getKundnr());
				faktorut.setNamn(fa1.getNamn());
				calendar.setTime(fa1.getDatum());
				calendar.add(Calendar.DAY_OF_MONTH, fa1.getKtid());
				faktorut.setFalldat(calendar.getTime());
				em.persist(faktorut);
			}
		} else { // Om dedt är en 0-faktura spara genast i betaljournalen så att fakturan blir avbokad
			betjour = new TableBetjour(fa1.getFaktnr(), 'K', 0, fa1.getDatum());
			betjour.setKundnr(fa1.getKundnr());
			betjour.setNamn(fa1.getNamn());
			betjour.setBet(0.0);
			betjour.setBetdat(fa1.getDatum());
			calendar.setTime(fa1.getDatum());
			betjour.setAr((short)calendar.get(Calendar.YEAR));
			betjour.setMan((short)calendar.get(Calendar.MONTH));
			betjour.setBonsumma(0.0);
			betjour.setPantsatt(fup.getPantsattfakturor());
			betjour.setBetsattkonto(fup.getPantsattfakturor());
			em.persist(betjour);
		}


		//**** Spara Kund
		kund.setObet(kund.getObet() + fa1.getTAttbetala());
		kund.setFaktdat(fa1.getDatum());
		kund.setTot(kund.getTot() + fa1.getTAttbetala());
		kund.setNtot(kund.getNtot() + fa1.getTNetto());
		kund.setNetto(kund.getNetto() + fa1.getTInnetto());
		kund.setTbidrag(kund.getTbidrag() + fa1.getTNetto() - fa1.getTInnetto());

		//**** Spara Kundstatistik
		kunstatPK = new TableKunstatPK(fa1.getKundnr(), (short)calendar.get(Calendar.YEAR), (short)calendar.get(Calendar.MONTH));
		kunstat = em.find(TableKunstat.class, kunstatPK);
		if (kunstat == null) {
			kunstat = new TableKunstat(kunstatPK);
			em.persist(kunstat);
		}
		kunstat.setTot(kunstat.getTot()+fa1.getTNetto());
		kunstat.setTbidrag(kunstat.getTbidrag() + fa1.getTNetto() - fa1.getTInnetto());


		//**** Spara Säljare
		if (!SXUtil.isEmpty(fa1.getSaljare())) {
			String saljareNamn;
			if (fa1.getSaljare().length() < 30) saljareNamn = fa1.getSaljare(); else saljareNamn = fa1.getSaljare().substring(0,30);
			saljare = em.find(TableSaljare.class, saljareNamn);
			if (saljare != null) {
				saljare.setTotalt(saljare.getTotalt() + fa1.getTNetto());
				saljare.setTbidrag(saljare.getTbidrag() +  fa1.getTNetto() - fa1.getTInnetto());
				calendar.setTime(fa1.getDatum());
				sljstatPK = new TableSljstatPK(saljareNamn, (short)calendar.get(Calendar.YEAR), (short)calendar.get(Calendar.MONTH));
				sljstat = em.find(TableSljstat.class, sljstatPK);
				if (sljstat == null) {
					sljstat = new TableSljstat(sljstatPK);
					em.persist(sljstat);
				}
				sljstat.setTotalt(sljstat.getTotalt() + fa1.getTNetto());
				sljstat.setTbidrag(sljstat.getTbidrag() + fa1.getTNetto() - fa1.getTInnetto());
			}
		}
		
		//Spara statistik
		calendar.setTime(fa1.getDatum());
		statistikPK = new TableStatistikPK((short)calendar.get(Calendar.YEAR), (short)calendar.get(Calendar.MONTH));
		statistik = em.find(TableStatistik.class, statistikPK);
		if (statistik == null) {
			statistik = new TableStatistik(statistikPK);
			em.persist(statistik);
		}
		statistik.setFakNetto(statistik.getFakNetto() + fa1.getTNetto());
		statistik.setFakMoms(statistik.getFakMoms() + fa1.getTMoms());
		statistik.setFakAttbetala(statistik.getFakAttbetala() + fa1.getTAttbetala());
		statistik.setFakInnetto(statistik.getFakInnetto() + fa1.getTInnetto());
		statistik.setFakAntal((short)(statistik.getFakAntal() + 1));

		em.flush();
		return fa1.getFaktnr();
	}

	public final void setAnvandare(String anvandare) {		this.anvandare = anvandare;	}
	public String getAnvandare() {return anvandare; }

	private class FaktRad {
		public TableFaktura2 fa2;
		public short bonusId = 0;
	}
}
