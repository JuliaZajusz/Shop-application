package lab2zadanie;

import java.io.Serializable;
/**
 * Klasa bazowa kont uzytkownikow i wlascicieli.
 * <br>
 * @author Julia Zajusz
 * @version 22 pazdziernika 2016 r.
 */
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * identyfikator
	 */
	private String id;						//PESEL
	/**
	 * kod hasla
	 */
	private long   passwordCode;
	/**
	 * flaga
	 */
	private int flag;
	/**
	 * Konstruktor parametrowy klasy Account.
	 * @param id
	 * @param flag
	 */
	Account(String id, int flag){
		this.id = id;
		this.flag=flag;
		passwordCode = 0;
	}
	/**
	 * Metoda sprawdzajaca poprawnosc hasla.
	 * @param password
	 * @return Zwraca hashCode hasla
	 */
	public boolean checkPassword(String password) {
		if (password==null) return false;
		return password.hashCode()==passwordCode;
	}
	/**
	 * Metoda zmieniajaca haslo.
	 * Jako parametry przyjmuje stare haslo i nowe haslo.
	 * @param oldPassword
	 * @param newPassword
	 * @throws Exception
	 * wyjatek jest zglaszany, kiedy podane stare haslo jest niepoprawne
	 */
	
	public void setPassword(String oldPassword, String newPassword) throws Exception {
		if (!checkPassword(oldPassword)) throw new Exception("Haslo niepoprawne");
		passwordCode = newPassword.hashCode(); 
	}
	
	/**
	 * Metoda zwracajaca identyfikator
	 * @return id
	 */
	public String getId(){
		return id;
	}
	/**
	 * Metoda zwracajaca flage
	 * @return flag
	 */
	public int getFlag(){
		return flag;
	}

}
