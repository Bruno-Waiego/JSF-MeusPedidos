package br.senac.sc.meuspedidos.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesUtil {

	public static void addInfoMessages(String msg) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, null, msg);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public static void addErrorMessages(String msg) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msg);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public static void addWarningMessages(String msg) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, null, msg);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
