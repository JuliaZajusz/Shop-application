package lab2zadanie;

/* 
 *  Klasa Client
 *  - obiekty to klienci
 *  
 *   baza klientow
 
 *  Autor: Julia Zajusz
 *   Data: 22 pazdziernika 2016 r.
 */
/**
 * Klasa kont klientow, dziedziczaca po klasie Account .
 * <br>
 * @author Julia Zajusz
 * @version 22 pazdziernika 2016 r.
 */
public class Client extends Account {
	/**
	 * Nazwisko
	 */
	private String surname;
	/**
	 * Adres
	 */
	private String adress;
	/**
	 * Stan portfela
	 */
	private float balance;					//stan portfela
	/**
	 * Konstruktor parametrowy klasy Client.
	 * Konto tworzone jest ze stanem portfela rownym 0.
	 * @param id
	 */
	Client(String id){
		super(id,2);
		balance = 0;
	}
	
	/**
	 * Metoda ustawiajaca nazwisko
	 * @param surname
	 */
	public void setSurname(String surname){
		this.surname=surname;
	}
	/**
	 * Metoda zwracajaca nazwisko.
	 * @return surname
	 */
	public String getSurname(){
		return surname;
	}
	/**
	 * Metoda ustawiajaca adres.
	 * @param adress
	 */
	public void setAdress(String adress){
		this.adress=adress;
	}
	/**
	 * Metoda zwracajaca adres.
	 * @return adress
	 */
	public String getAdress(){
		return adress;
	}
	/**
	 * Metoda zwracajaca stan portfela.
	 * @return balance
	 */
	public double getBalance(){
		return balance;
	}
	/**
	 * Metoda zwiekszajaca stan portfela.
	 * Jako parametry przyjmuje kwote wplaty.
	 * @param amount
	 * @throws Exception
	 * wyjatek jest zglaszany jesli podana kwota wplaty jest ujemna.
	 */
	public void increaseBalance(double amount) throws Exception {
		if (amount<0) throw new Exception("Wplata nie moze byc ujemna");
		balance += amount;
	}
	/**
	 * Metoda obslugujaca zakup produktu, zaplate, oraz pomniejszenie stanu towaru w sklepie.
	 * Przyjmuje za parametry haslo klienta, produkt i ilosc kupowanego towaru.
	 * @param password
	 * @param commodity
	 * @param quantity
	 * @throws Exception
	 * wyjatek jest zwracany jezeli haslo jest niepoprawne, klient ni eposiada wystarczajacej ilosci srodkow na koncie lub nie ma wstarczajacej ilosci produktu w sklepie.
	 */
	public void pay(String password, Commodity commodity,int quantity) throws Exception {

		int tmp=commodity.getReserves();
		if (!checkPassword(password)) throw new Exception("Haslo niepoprawne");
		if (commodity.getPrice()*quantity>balance) throw new Exception("Brak srodkow na koncie");
		if(tmp<quantity)throw new Exception("Niewystarczajaca ilosc produktu w sklepie");
		{
			balance -= (commodity.getPrice()*quantity);
			commodity.setReserves(tmp-=quantity);
		}
	}
	/**
	 * Metoda zwraca postac klienta w postaci tekstowej.
	 *
	 * <p><b>Uwaga:</b> Metada jest wywolywana niejawnie,
	 *  jezli zachodzi potrzeba przedstawienia klienta
	 *  w postaci tekstowej.
	 *  @return klient w postaci tekstowej (identyfikator":" nazwisko).
	 */
	public String toString(){
		String a=super.getId();
		return String.format("   %s: %s ", a, surname);
	}
	/**
	 * Metoda zwraca postac klienta w postaci tekstowej.
	 *
	 * <p><b>Uwaga:</b> Metada jest wywolywana niejawnie,
	 *  jezli zachodzi potrzeba przedstawienia klienta
	 *  w postaci tekstowej.
	 *  @return klient w postaci tekstowej ("Klient:" identyfikator).
	 */
	public String toStringBought(){
		String a=super.getId();
		return String.format("Klient: %s", a);
	}
	
}
