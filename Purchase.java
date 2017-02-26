package lab2zadanie;

import java.io.Serializable;

/* 
 *  Klasa Purchase
 *  - historia zakupow
 *  
 *  Autor: Julia Zajusz
 *   Data: 22 pazdziernika 2016 r.
 */
/**
 * Klasa obslugujaca historie zakupow .
 * <br>
 * @author Julia Zajusz
 * @version 22 pazdziernika 2016 r.
 */
public class Purchase implements Serializable {
	private static final long serialVersionUID = 1L;
/**
 * numer transakcji
 */
	private int transactionNr;
	/**
	 * klient
	 */
	private Client client;
	/**
	 * produkt
	 */
	private Commodity commodity;
	/**
	 * ilosc
	 */
	private int quantity;
	/**
	 * cena
	 */
	private float price;
	

	/**
	 * Konstruktor bezparametrowy klasy Purchase
	 */
	Purchase(){

	}
	/**
	 * Konstruktor parametrowy klasy Purchase.
	 * @param tnr
	 * @param client
	 * @param commodity
	 * @param quantity
	 * @param price
	 */
	Purchase(int tnr,Client client,Commodity commodity, int quantity, float price){
		this.transactionNr = tnr;
		this.client=client;
		this.commodity=commodity;
		this.quantity=quantity;
		this.price=price;
	}
	/**
	 * Metoda zwracajaca klienta.
	 * @return client
	 */
	public Client getClient(){
		return this.client;
	}

	/**
	 * Metoda zwracajaca numer transakcji.
	 * @return transactionNr
	 */
	public  int getTransactionNr(){
		return transactionNr;
	}
	/**
	 * Metoda zwracajaca cene produktu.
	 * @return price
	 */
	public float getPrice(){
		return this.price;
	}
	/**
	 * Metoda zwracajaca zakupiona ilosc towaru.
	 * @return quantity
	 */
	public int getQuantity(){
		return this.quantity;
	}

		/**
		 * Metoda ustawiajaca numer transakcji.
		 * @param tNr
		 */
	 public void setTransactionNr(int tNr){
		this.transactionNr = tNr;
	}
		/**
		 * Metoda zwraca zakup w postaci tekstowej.
		 *
		 * <p><b>Uwaga:</b> Metada jest wywolywana niejawnie,
		 *  jezli zachodzi potrzeba przedstawienia zakupu
		 *  w postaci tekstowej.
		 *  @return zakup w postaci tekstowej ("Nr transakcji:" numer transakcji klient produkt ilosc zakupionego towaru cena "Total:" kwota do zaplaty).
		 */
	public String toString(){
		return String.format("Nr transakcji "+transactionNr+" "+client.toStringBought()+" "+commodity.toStringBought()+" "+quantity+" "+price+" Total: "+quantity*price);
	}

}
