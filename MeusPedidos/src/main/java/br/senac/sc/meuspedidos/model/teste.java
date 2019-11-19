package br.senac.sc.meuspedidos.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class teste {
	public static void main(String[] args) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		
		System.out.println(dateFormat.format(date));
	}
}
