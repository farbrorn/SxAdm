/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author ulf
 */
public class FakturaRow implements IsSerializable {
	public int pos;
	public String artnr=null;
	public String namn=null;
	public String text=null;
	public double lev;
	public String enh=null;
	public double pris;
	public double rab;
	public double summa;
	public int ordernr;
	public FakturaRow(){}

}
