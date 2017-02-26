package lab2zadanie;

import java.io.Serializable;

/* 
 *  Klasa Commodity
 *  - obiekty to towary 
 *  
 *   Magazyn towarow
 
 *  Autor: Julia Zajusz
 *   Data: 22 pazdziernika 2016 r. 
 */

import java.io.Serializable;
/**
 * Klasa realizujaca obsluge produktow.
 * <br>
 * @author Julia Zajusz
 * @version 22 pazdziernika 2016 r.
 */

public class Commodity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * numer seryjny
	 */
	private String serialNumber;    //numer seryjny
	/**
	 * nazwa produktu
	 */
	private String name;
	/**
	 * cena jednostkowa
	 */
	private float price;
	/**
	 * ilosc produktu
	 */
	private int reserves;			//stan
	
/**
 * Konstruktor parametrowy klasy Commodity.
 * @param serialNumber
 * @param name
 * @param price
 * @param reserves
 */
	Commodity(String serialNumber,String name,float price, int reserves){
		this.serialNumber=serialNumber;
		this.name=name;
		this.price=price;
		this.reserves=reserves;
	}
	/**
	 * Metoda zwracajaca numer seryjny.
	 * @return serialNumber
	 */
	public String getSerialNumber(){
		return serialNumber;
	}
	/**
	 * Metoda nadajaca nazwe produktu.
	 * @param name
	 */
	public void setName(String name){
		this.name=name;
	}
	/**
	 * Metoda zwracajaca nazwe produktu.
	 * @return name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Metoda ustawiajaca cene produktu.
	 * @param price
	 */
	public void setPrice(float price){
		this.price=price;
	}
	/**
	 * Metoda zwracajaca cene produktu.
	 * @return price
	 */
	public float getPrice(){
		return price;
	}
	/**
	 * Metoda ustawiajaca stan towaru.
	 * @param reserves
	 */
	public void setReserves(int reserves){
		this.reserves=reserves;
	}
	/**
	 * Metoda zwracajaca stan towaru.
	 * @return reserves
	 */
	public int getReserves(){
		return reserves;
	}
	/**
	 * Metoda zwraca towar w postaci tekstowej.
	 *
	 * <p><b>Uwaga:</b> Metada jest wywolywana niejawnie,
	 *  jezli zachodzi potrzeba przedstawienia towaru
	 *  w postaci tekstowej.
	 *  @return towar w postaci tekstowej ("Numer seryjny:" numer seryjny "Cena:" cena "Ilosc:" ilosc towaru).
	 */
	public String toString(){
		java.text.DecimalFormat df=new java.text.DecimalFormat("0.00");
		String s="Numer seryjny: "+serialNumber+" Nazwa: "+name+" Cena: "+df.format(price)+" Ilosc: "+reserves;
		return s;
	}
	/**
	 * Metoda zwraca towar w postaci tekstowej.
	 *
	 * <p><b>Uwaga:</b> Metada jest wywolywana niejawnie,
	 *  jezli zachodzi potrzeba przedstawienia towaru
	 *  w postaci tekstowej.
	 *  @return towar w postaci tekstowej ("Numer seryjny:" numer seryjny "Nazwa:" nazwa).
	 */
	public String toStringBought(){
		String s="Numer seryjny: "+serialNumber+" Nazwa: "+name;
		return s;
	}
	
}
