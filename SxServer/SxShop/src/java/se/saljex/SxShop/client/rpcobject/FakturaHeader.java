/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

/**
 *
 * @author ulf
 */
public class FakturaHeader implements IsSerializable{

	public int faktnr;
	public java.sql.Date datum=null;
	public java.sql.Date fallDatum=null;
	public double t_attbetala;
	public ArrayList<FakturaHeaderOrderMarke> orderMarken = new ArrayList();
	public FakturaHeader() {}

}
