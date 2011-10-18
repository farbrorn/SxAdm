/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import java.util.Date;

/**
 *
 * @author Ulf
 */
public class FormDateTextBox extends DateTextBox implements FormWidgetInterface<Date> {
	private FormWidgetGetSet formWidgetGetSet;

	public FormDateTextBox(FormWidgetGetSet formWidgetGetSet) {
		super();
		this.formWidgetGetSet = formWidgetGetSet;
	}

	@Override
	public FormWidgetGetSet getFormWidgetGetSet() {return formWidgetGetSet; }

	@Override
	public void setSQLTableValue(Date value) {
		setValue(value);
	}

	@Override
	public Date getSQLTableValue() {
		return getValue();
	}

}