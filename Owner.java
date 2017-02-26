package lab2zadanie;

/* 
 *  Klasa Owner
 *  - obiekt to sprzedawca
 *  
 *  Autor: Julia Zajusz
 *   Data: 22 pazdziernika 2016 r.
 */
/**
 * Klasa kont wlascicieli, dziedziczaca po klasie Account .
 * <br>
 * @author Julia Zajusz
 * @version 22 pazdziernika 2016 r.
 */

public class Owner extends Account {
	/**
	 * Kontruktor parametrowy klasy Owner.
	 * @param id
	 */
	Owner(String id){
		super(id,1);
	}

}
