/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

/**
 *
 * @author Ulf
 */
public interface HasFormUpdater<T> extends HasData2Form<T>{
	public T form2Data();
}
