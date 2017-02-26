package lab2zadanie;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lab2zadanie.Purchase;
import lab2zadanie.Client;
import lab2zadanie.Commodity;

/* 
 *  Klasa Shop
 *   
 *  
 *   Klasa realizuj¹ca obsluge sklepu
 
 *  Autor: Julia Zajusz
 *   Data: 22 pazdziernika 2016 r.
 */

/**
 * Klasa realizujaca obsluge sklepu.
 * <br>
 *
 *Klasa realizuje nastepujace zagadnienia:
 * <ul>
 *  <li> przechowuje liste klientow,</li>
 *  <li> przechowuje liste towarow, </li>
 *  <li> przechowuje liste zakupow,</li>
 *  <li> przechowuje liste zakupow klienta, </li>
 *  <li> przechowuje obrot sklepu, </li>
 *  <li> przechowuje liste zakupionych produktow do wygenerowania faktury, </li>
 *  <li> implementuje opcje przeszukiwania list, wypisywania list, dodawania do list,</li>
 *  <li> zapisuje i odczytuje listy z plikow,</li>
 * </ul>
 *
 * @author Julia Zajusz
 * @version 22 pazdziernika 2016 r.
 */
public class Shop implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** obrot sklepu.
	 * Dostêp do obrotu ma tylko konto wlasciciel.
	 */
	private static float turnover;
	/**
	 * Metoda zwraca obrot.
	 *  @return obrot sklepu
	 */
	public float getTurnover(){
		return turnover;
	}
	/**
	 * Metoda zwiekszajaca obrot po zakupie produktu.
	 * Metoda przyjmuje parametr value, ktory jest wartoscia zakupu.
	 * @param value
	 * wartosc zakupu
	 */
	public void increaseTurnover(Float value){
		this.turnover+=value;
	}
	
	/**
	 * Lista kont wlascicieli.
	 * <p><b>Uwaga:</b> Zeby utworzyc konto wlasciciela nalezy podac kod dostepu.
	 */
	private ArrayList<Owner> listOfOwners = new ArrayList<Owner>();
	/**
	 * Metoda dodaje wlascicela do listy wlasciceli.
	 * Przyjmuje parametr id, czyli identyfikator.
	 * @param id
	 * Metoda zwraca konto nowego wlasciciela
	 * @return newOwner
	 * @throws Exception
	 * wyjatek zglaszany, gdy nie podano identyfikatora, lub gdy podany identyfikator juz istnieje.
	 */
	public Owner addOwner(String id) throws Exception {
		if (id==null || id.equals("")) throw(new Exception("Identyfikator konta nie moze byc pusty"));
		if (findOwner(id)!=null) throw(new Exception("Uzytkownik juz istnieje"));
		Owner newOwner = new Owner(id);
		listOfOwners.add( newOwner );
		return newOwner;
	}
	/**
	 * Metoda wyszukuje wlasciciela w liscie wlascicieli.
	 * Przyjmuje parametr identyfikator.
	 * Jesli wyszukiwanie przebiegnie pomyslnie zwraca wlasciciela.
	 * @param id
	 * @return owner
	 */
	public Owner findOwner(String id) {
		for (Owner owner : listOfOwners)
			if (owner.getId().equals(id)) return owner;
		return null;
	}
	/**
	 * Metoda wyszukuje wlasciciela w liscie wlascicieli i weryfikuje haslo dostepu.
	 * Przyjmuje za parametr identyfikator i haslo.
	 * Jesli wyszukiwanie sie powiedzie oraz walidacja hasla przebiegnie pomyslnie zwraca wlascicela.
	 * @param id
	 * @param password
	 * @return owner
	 */
	public Owner findOwner(String id, String password) {
		Owner owner = findOwner(id);
		if (owner!=null){
			if (!owner.checkPassword(password)) owner = null;
		}
		return owner;
	}
	/**
	 * Metoda wyswietla liste wlascicieli.
	 * Zwraca liste wlascicieli w postaci tekstowej.
	 * @return Tekstowa postac listy wlascicieli.
	 */
	public String listOwners(){
		StringBuilder sb = new StringBuilder();
		int n = 0;
		for (Owner owner : listOfOwners){
			if (n++ != 0) sb.append("\n");		
			sb.append(owner.toString());
		}
		return sb.toString();
	}
	

	/**
	 * Lista kont klientow.
	 */
	
	private ArrayList<Client> listOfClients = new ArrayList<Client>();
	/**
	 * Metoda dodaje klienta do listy klientow.
	 * Przyjmuje parametr id, czyli identyfikator.
	 * @param id
	 * Metoda zwraca konto nowego klienta.
	 * @return newClient
	 * @throws Exception
	 * wyjatek zglaszany, gdy nie podano identyfikatora, lub gdy podany identyfikator juz istnieje.
	 */
	public Client addClient(String id) throws Exception {
		if (id==null || id.equals("")) throw(new Exception("Identyfikator konta nie moze byc pusty"));
		if (findClient(id)!=null) throw(new Exception("Uzytkownik juz istnieje"));
		Client newClient = new Client(id);
		listOfClients.add( newClient );
		return newClient;
	}
	/**
	 * Metoda usuwa podanego klienta z listy klientow.
	 * Przyjmuje za parametr klienta.
	 * @param client
	 * @throws Exception
	 * wyjatek jest zglaszany jesli podany uzytkownik nie istnieje lub posiada srodki na koncie.
	 */
	public void removeClient(Client client) throws Exception {
		if (client==null) throw(new Exception("Uzytkownik nie istnieje"));
		if (client.getBalance()!= 0) throw(new Exception("Uzytkownik posiada srodki na koncie! Nie mozna usunac konta"));
		listOfClients.remove(client);
	}
	/**
	 * Metoda wyszukuje klienta w liscie klientow.
	 * Przyjmuje parametr identyfikator.
	 * Jesli wyszukiwanie przebiegnie pomyslnie zwraca klienta.
	 * @param id
	 * @return client
	 */
	public Client findClient(String id) {
		for (Client client : listOfClients)
			if (client.getId().equals(id)) return client;
		return null;
	}
	/**
	 * Metoda wyszukuje clienta w liscie klientow i weryfikuje haslo dostepu.
	 * Przyjmuje za parametr identyfikator i haslo.
	 * Jesli wyszukiwanie sie powiedzie oraz walidacja hasla przebiegnie pomyslnie zwraca clienta.
	 * @param id
	 * @param password
	 * @return client
	 */
	public Client findClient(String id, String password) {
		Client client = findClient(id);
		if (client!=null){
			if (!client.checkPassword(password)) client = null;
		}
		return client;
	}
	/**
	 * Metoda wyswietla liste klientow.
	 * Zwraca liste klientow w postaci tekstowej.
	 * @return Tekstowa postac listy klientow.
	 */
	public String listClients(){
		StringBuilder sb = new StringBuilder();
		int n = 0;
		for (Client client : listOfClients){
			if (n++ != 0) sb.append("\n");		
			sb.append(client.toString());
		}
		return sb.toString();
	}
	
	
	/**
	 * Lista produktow.
	 */
	
	private ArrayList<Commodity> listOfCommodities = new ArrayList<Commodity>();
	/**
	 * Metoda dodaje produkt do listy produktow.
	 * Jako parametry przyjmuje numer seryjny, nazwe produktu, cene jednostkowa oraz ilosc towaru. 
	 * @param serialNumber
	 * numer seryjny
	 * @param name
	 * nazwa produktu
	 * @param price
	 * cena jednostkowa
	 * @param reserves
	 * ilosc towaru
	 * @return newCommodity
	 * zwraca nowy produkt
	 * @throws Exception
	 * wyjatek jest zglaszany jezeli nie podano numeru seryjnego, produkt o podanym numerze seryjnym jest juz na liscie, podana ilosc towaru jest liczba ujemna.
	 */
	public Commodity addCommodity(String serialNumber,String name,float price,int reserves) throws Exception {
		if (serialNumber==null || serialNumber.equals("")) throw(new Exception("Numer seryjny jest wymagany"));
		if (findCommodity(serialNumber)!=null) throw(new Exception("Produkt jest juz w bazie"));
		if (reserves<0) throw new Exception("Liczba produktow nie moze byc ujemna");
		Commodity newCommodity = new Commodity(serialNumber,name,price,reserves);
		listOfCommodities.add( newCommodity );
		return newCommodity;
	}
	/** 
	 * Metoda usuwajaca produkt z listy produktow.
	 * <p><b>Uwaga:</b> Nie mozna usunac produktu ze sklepu jesli ilosc towaru jest rozna od 0.
	 * @param commodity
	 * Przyjmuje jako parametr produkt.
	 * @throws Exception
	 * wyjatek jest zglaszany jesli podany produkt nie istnieje w bazie lub jesli ilosc towaru jest rozna od 0.
	 */
	public void removeCommodity(Commodity commodity) throws Exception {
		if (commodity==null) throw(new Exception("Nie ma takiego produktu"));
		if (commodity.getReserves()!= 0) throw(new Exception("Nie mozna usunac produktu z bazy, produkt jest na stanie"));
		listOfClients.remove(commodity);
	}
	/**
	 * Metoda wyszukuje produkt w liscie produktow.
	 * Przyjmuje parametr numer seryjny.
	 * Jesli wyszukiwanie przebiegnie pomyslnie zwraca produkt.
	 * @param serialNumber
	 * @return commodity
	 */
	public Commodity findCommodity(String serialNumber) {
		for (Commodity commodity : listOfCommodities)
			if (commodity.getSerialNumber().equals(serialNumber)) return commodity;
		return null;
	}
	
	/**
	 * Metoda wyswietla liste produktow.
	 * Zwraca liste produktow w postaci tekstowej.
	 * @return Tekstowa postac listy produktow.
	 */
	public String listCommodities(){
		StringBuilder sb = new StringBuilder();
		int n = 0;
		for (Commodity commodity : listOfCommodities){
			if (n++ != 0) sb.append("\n");		
			sb.append(commodity.toString());
		}
		return sb.toString();
	}
	

	
	/**
	 * Lista wszystkich zakupow.
	 */
	private ArrayList<Purchase> listOfPurchases = new ArrayList<Purchase>();
	/**
	 * Lista wszystkich zakupow klienta.
	 */
	private ArrayList<Purchase> listOfClientPurchases ;//= new ArrayList<Purchase>();
	/**
	 * Lista zakupow do wygenerowania faktury.
	 */
	private ArrayList<Purchase> listOfChoosenPurchases = new ArrayList<Purchase>();
	
	
	/**
	 * Metoda czyszczaca liste zakupow klienta.
	 */
	public void clearList(){
		listOfClientPurchases.clear();
	}
	/**
	 * Metoda dodajaca zakup do listy zakupow.
	 * Przyjmuje jako parametry numer transakcji, klienta dokonujacego zakupu, zakupiony produkt, ilosc zakupionego produktu oraz cene produktu.
	 * Dodaje zakup do listy i zwraca zakup.
	 * @param tnr
	 * numer transakcji
	 * @param client
	 * klient
	 * @param commodity
	 * produkt
	 * @param quantity
	 * zakupiona ilosc
	 * @param price
	 * cena
	 * @return newPurchase
	 */
	public Purchase addPurchaseToList(int tnr,Client client,Commodity commodity,int quantity,float price)  {
		Purchase newPurchase = new Purchase(tnr, client, commodity, quantity, price);
		listOfPurchases.add( newPurchase );
		return newPurchase;
	}
	/**
	 * Metoda wyszukuje zakup w liscie zakupow i dodaje go do listy zakupow klienta.
	 * Przyjmuje parametr klienta.
	 * @param client
	 */
	public void findPurchase(Client client) {
		listOfClientPurchases = new ArrayList<Purchase>();
		for (Purchase purchase : listOfPurchases){
			if (purchase.getClient()==client){
				Purchase tmp = new Purchase();
				tmp = purchase;
				listOfClientPurchases.add(tmp);
		
			}
		}
	}
	/**
	 * Metoda wyszukuje zakup na liscie zakupow.
	 * Jako parametr przyjmuje klienta oraz numer transakcji.
	 * Zwraca zakup.
	 * @param client
	 * @param transactionNr
	 * @return purchase
	 * zakup
	 */
	public Purchase findPurchase(Client client, int transactionNr) {
		for(Purchase purchase:listOfPurchases){
			if(purchase.getClient().toString().equals(client.toString())){
				if (purchase.getTransactionNr()==transactionNr){
					return purchase;
				}
			}
		}
		return null;
	}
	/**
	 * Metoda wypisuje liste zakupow.
	 * @return Zwraca tekstowa postac listy zakupow
	 */
	public String listPurchases(){
		StringBuilder sb = new StringBuilder();
		int n = 0;
		for (Purchase purchase : listOfPurchases){
			if (n++ != 0) sb.append("\n");		
			sb.append(purchase.toString());
		}
		return sb.toString();
	}

	/**
	 * Metoda wypisuje liste zakupow klienta.
	 * Jako parametr przyjmuje klienta.
	 * @param client
	 * @return Zwraca tekstowa postac listy zakupow
	 */
	public String listClientPurchases(Client client){
		StringBuilder sb = new StringBuilder();
		int n = 0;
		for (Purchase purchase : listOfPurchases){
			if(purchase.getClient().toString().equals(client.toString())){
				if (n++ != 0) sb.append("\n");		
				sb.append(purchase.toString());
			}
		}
		return sb.toString();
	}
	/**
	 * Metoda wypisuje liste wybranych zakupow klienta.
	 * Jako parametr przyjmuje klienta.
	 * @param client
	 * @return Zwraca tekstowa postac listy wybranych produktow klienta
	 */
	public String listChoosenPurchases(Client client){
		StringBuilder sb = new StringBuilder();
		int n = 0;
		for (Purchase purchase : listOfChoosenPurchases){
			if(purchase.getClient().toString().equals(client.toString())){
				if (n++ != 0) sb.append("\n");		
				sb.append(purchase.toString());
			}
		}
		return sb.toString();
	}

	/**
	 * Metoda pobierajaca numer transakcji z listy zakupow.
	 * @return Zwraca numer transakcji
	 */
	public int getTransactionNr(){
		int tnr=0;
		for(Purchase purchase: listOfPurchases){
			if(purchase.getTransactionNr()>tnr){
				tnr=purchase.getTransactionNr();
			}
		}

		return tnr;
	}
/**
 * Metoda przypisujaca numer transakcji kazdemu zakupowi.
 * Jako parametr przybiera zakup
 * @param purchase
 * @return Zwraca odpowiedni numer transakcji
 */
	public int setTransactionNr(Purchase purchase) throws Exception{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("PURCHASE.BIN"));
		ArrayList<Purchase> l1 = (ArrayList<Purchase>)in.readObject();
		ArrayList<Purchase> l3 = (ArrayList<Purchase>)in.readObject();
		int tmp=in.readInt();
		in.close();
		if(tmp<purchase.getTransactionNr()){
			tmp=purchase.getTransactionNr();
		}
		return tmp;
		
	}

	/**
	 * Metoda zapisujaca liste zakupow i liste wybranych zakupow klienta do pliku.
	 * Jako parametr przyjmuje nazwe pliku.
	 * @param fileName
	 */
	void savePurchasesToFile(String fileName) throws Exception {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
		out.writeObject(listOfPurchases);
		out.writeObject(listOfChoosenPurchases);
		int a=getTransactionNr();
		out.writeInt(a);
		out.close();
	}
	
	/**
	 * Metoda odczytujaca liste zakupow i liste wybranych zakupow klienta z pliku.
	 * Jako parametr przyjmuje nazwe pliku.
	 * @param fileName
	 */
	void loadPurchasesFromFile(String fileName) throws Exception {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
		listOfPurchases = (ArrayList<Purchase>)in.readObject();
		listOfChoosenPurchases = (ArrayList<Purchase>)in.readObject();
		int tmp=in.readInt();
		in.close();
		
	}
	

	
	/**
	 * Metoda zapisujaca liste wlascicieli, liste klientow i liste towarow do pliku.
	 * Jako parametr przyjmuje nazwe pliku.
	 * @param fileName
	 * @throws Exception
	 */
	
	void saveShopToFile(String fileName) throws Exception {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
		out.writeObject(listOfOwners);
		out.writeObject(listOfClients);
		out.writeObject(listOfCommodities);
		out.writeFloat(turnover);
		out.close();
	}
	/**
	 * Metoda odczytujaca liste wlascicieli, liste klientow i liste towarow z pliku.
	 * Jako parametr przyjmuje nazwe pliku.
	 * @param fileName
	 * @throws Exception
	 */
	
	void loadShopFromFile(String fileName) throws Exception {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
		listOfOwners = (ArrayList<Owner>)in.readObject();
		listOfClients = (ArrayList<Client>)in.readObject();
		listOfCommodities = (ArrayList<Commodity>)in.readObject();
		turnover=in.readFloat();
		in.close();
	}
	
	
}
