package lab2zadanie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import lab2zadanie.Client;
import lab2zadanie.Commodity;
import lab2zadanie.JOptionPaneUserDialog;
import lab2zadanie.UserDialog;

/* 
 *  Program ShopApplication
 *  Wazne: kod dostepu dla admina: admin
 *  
 *  Autor: Julia Zajusz
 *   Data: 22 pazdziernika 2016 r.
 */
/**
 * Klasa obslugujaca aplikacje sklepu internetowego:

 *Program realizuje nastepujace funkcje:
 * <ul>
 *  <li> wykorzystywanie parametrow linii polecenia,</li>
 *  <li> porownywanie ciagow znakow za pomoca metody
 *       <code> String.equals </code>,</li>
 *  <li> wyswietlanie danych roznych typow w oknie konsoli, </li>
 *  <li> wyswietlanie komunikatow za pomoca okna dialogowego
 *       z biblioteki swing: <br>
 *       <code> JOptionPane.showMessageDialog</code>,</li>
 *  <li> obsluga sklepu internetowego, </li>
 *  <li> obsluga rejestracji nowych uzytkownikow, </li>
 *  <li> obsluga bezpiecznego logowania, </li>
 *  <li> obsluga zakupow, </li>
 *  <li> obsluga generowania faktur, </li>
 * </ul>
 *
 * @author Julia Zajusz
 * @version 22 pazdziernika 2016 r.
 */
public class ShopApplication {

	public static void main(String[] args) throws Exception{
		
		new ShopApplication();
		
	}

		private UserDialog UI = new JOptionPaneUserDialog();
	/**
	 * Wiadomosc powitalna	
	 */
		private static final String GREETING_MESSAGE =
				"Program symulujacy Sklep internetowy\n" +
				"Autor: Julia Zajusz\n" +
				"Data: 22 pazdziernika 2016 r.\n";
/**
 * Menu 1
 */
		private static final String EKRAN_LOGOWANIA = 
				"        LOGOWANIE                   \n" +
				"1 - Zarejestruj sie                 \n" +
				"2 - Zaloguj sie                     \n" + 
				"0 - Wyjscie                         \n";	
		/**
		 * Menu klienta
		 */
			
		private static final String ACCOUNT_MENU =
				"1 - Doladuj konto               \n" +
				"2 - Przegladaj produkty         \n" +
				"3 - Kup produkt                 \n" + 
				"4 - Wyswietl historie zakupow   \n" +
				"5 - Zmien haslo                 \n" +
				"6 - Zmien dane                  \n" +
				"7 - Usun konto                  \n" +
				"0 - Wyloguj sie                 \n";
		/**
		 * Menu wlasciciela
		 */
		private static final String ADMIN_MENU =
				"1 - Dodaj produkt               \n"+
				"2 - Przegladaj produkty         \n"+
				"3 - Lista klientow              \n"+
				"4 - Wystaw fakture              \n"+
				"5 - Usun konto klienta          \n"+
				"6 - Usun produkt                \n"+
				"7 - Zmien cene produktu         \n"+
				"8 - Sprawdz utarg               \n"+
				"9 - Wyswietl historie zakupow   \n"+
				"10- Zmien haslo                 \n"+
				"0 - Wyloguj sie                 \n";
				
		/**
		 * Plik do zapisu list klienow, wlascicieli i towarow
		 */
		private static final String DATA_FILE_NAME = "SKLEP.BIN";
		/**
		 * Plik do zapisu list zakupionych produktow i wybranych produkow klienta
		 */
		private static final String DATA_FILE_PURCHASE = "PURCHASE.BIN";
		
		private Shop sklep= new Shop();
		

		public ShopApplication() throws Exception {
			UI.printMessage(GREETING_MESSAGE);
			Owner owner=new Owner("123");
			owner.setPassword("", "123");
			
			try {
				sklep.loadShopFromFile(DATA_FILE_NAME);
				sklep.loadPurchasesFromFile(DATA_FILE_PURCHASE);
				UI.printMessage("Uzytkownicy i produkty zostaly wczytane z pliku " + DATA_FILE_NAME);
				UI.printMessage("Historia zakupow zostala wczytana z pliku " + DATA_FILE_PURCHASE);
			} catch (Exception e) {
				UI.printErrorMessage(e.getMessage());
			}
			
			while (true) {
				UI.clearConsole();

				try {
					switch (UI.enterInt(EKRAN_LOGOWANIA + "==>> ")) {
					
					case 1:
						createNewAccount();
						break;
					case 2:
						login();
						break;
					case 0:
						try {
							sklep.savePurchasesToFile(DATA_FILE_PURCHASE);
							sklep.saveShopToFile(DATA_FILE_NAME);
							UI.printMessage("\nDane sklepu zapisane do pliku " + DATA_FILE_NAME);
							UI.printMessage("\nHistoria sklepu zostala zapisana do pliku " + DATA_FILE_PURCHASE);
						} catch (Exception e) {
							UI.printErrorMessage(e.getMessage());
						}

						UI.printInfoMessage("\nProgram zakonczyl dzialanie!");
						System.exit(0);
					}  // end of switch(option)
				} catch (Exception e) {
					UI.printErrorMessage(e.getMessage());
				}
			}
		}
		/**
		 * Metoda wyswietlajaca male menu i wywolujaca metode createNewOwner lub createNewClient
		 */
		public void createNewAccount(){
			String answer;
			UI.printMessage("\nTWORZENIE NOWEGO KONTA\n");
			answer=UI.enterString("\n1.Utworz konto administratora\n2.Utworz konto uzytkownika\n");
			if(answer.equals("1")){
				createNewOwner();
			}else if(answer.equals("2")){
				createNewClient();
			}
		}
		
		
		/**
		 * Metoda tworzaca nowego wlasciciela i dodajaca go do listy wlascicieli.
		 */
		public  void createNewOwner() {	
			String newId;
			String newPassword;
			Owner newOwner;
			String AccessCode;
			UI.printMessage("\nTWORZENIE NOWEGO KONTA\n");
			while(true) {
				AccessCode=UI.enterString("Podaj kod dostepu:");
				if(AccessCode.equals("admin")){
				newId = UI.enterString("Podaj PESEL:");
				if (newId.equals("")) return;  // rezygnacja z tworzenia nowego konta
				newPassword = UI.enterString("Podaj haslo:");	
				try {
					newOwner = sklep.addOwner(newId);
					newOwner.setPassword("", newPassword);
				} catch (Exception e) {
					UI.printErrorMessage(e.getMessage());
					continue;
				}
				UI.printMessage("Konto zostalo utworzone");
				StringBuilder list = new StringBuilder("\nLISTA ADMINISTRATOROW\n");
				list.append(sklep.listOwners());		
				UI.printInfoMessage(list.toString());
				break;
				}else return;
			}
		}
		/**
		 * Metoda tworzaca nowego klienta i dodajaca go do listy klientow.
		 */
		public  void createNewClient() {	
			String newId;
			String newPassword;
			Client newClient;
			
			UI.printMessage("\nTWORZENIE NOWEGO KONTA\n");
			while(true) {
				newId = UI.enterString("Podaj PESEL:");
				if (newId.equals("")) return;  // rezygnacja z tworzenia nowego konta
				if (sklep.findClient(newId)!=null) {
					UI.printErrorMessage("Konto przypisane do tego numeru PESEL juz istnieje");
					continue;
				}
				
				newPassword = UI.enterString("Podaj haslo:");	
				try {
					newClient = sklep.addClient(newId);
					newClient.setPassword("", newPassword);
				} catch (Exception e) {
					UI.printErrorMessage(e.getMessage());
					continue;
				}
				UI.printMessage("Konto zostalo utworzone");
				break;
			}
			
		}
		
	
	
		
		
		/**
		 * Metoda obslugujaca menu wewnetrzne administratora.
		 */
		
		public void login() {
			String id, password="";
			Account account;
			Owner owner;
			Client client;
			int flag;

			UI.printMessage("\nLOGOWANIE\n");

			id = UI.enterString("Podaj PESEL:");
			password = UI.enterString("Podaj haslo:");
			if((account = sklep.findOwner(id,password))!=null){
				while(true){
					owner = sklep.findOwner(id,password);
					try {

						switch (UI.enterInt(ADMIN_MENU + "==>> ")) {
						case 1:
							addProduct();
							break;
						case 2:
							listAllProducts();
							break;
						case 3:
							listAllClients();
							break;
						case 4:
							writeInvoice();
							break;
						case 5:
							if (deleteClient() == false)
								break;
							client = null;
							break;
						case 6:
							if (deleteProduct() == false)
								break;
							break;
						case 7:
							changeProductPrice();
							break;
						case 8:
							checkTurnover();
							break;
						case 9:
							showClientPurchases();
							break;
						case 10:
							password = changePassword(owner,password);
							break;
						case 0:
							account = null;
							UI.printMessage("Zostales wylogowany");
							return;
								
							
						}
					} catch (Exception e) {
						UI.printErrorMessage(e.getMessage());
					}
				}
				
			}else if((client = sklep.findClient(id, password))!=null){
				loginClient(client,password);
			}else{
				UI.printErrorMessage("Bledne dane logowania");
				return;
			}
		
			}
		
		
		
		/**
		 * Metoda obslugujaca menu wewnetrzne klienta.
		 *Przyjmuj parametry klient i haslo.
		 * @param client
		 * @param password
		 */
		public void loginClient(Client client,String password) {
				UI.printMessage("\nLOGOWANIE DO KONTA\n");

				while (true) {
					UI.printMessage("\nJESTES ZALOGOWANY DO KONTA ");
					UI.printMessage("ID uzytkownika: " + client.getId());
					UI.printMessage("Nazwisko: " + client.getSurname());
					java.text.DecimalFormat df=new java.text.DecimalFormat("0.00");
					UI.printMessage("Saldo konta: " + df.format(client.getBalance()));

					try {

						switch (UI.enterInt(ACCOUNT_MENU + "==>> ")) {
						case 1:
							increaseBalance1(client);
							break;
						case 2:
							listAllProducts();
							break;
						case 3:
							pay1(client, password);
							break;
						case 4:
							showMyPurchases(client);
							break;
						case 5:
							password = changePassword(client, password);
							break;
						case 6:
							changeOwnerData(client, password);
							break;
						case 7:
							if (removeClient(client, password) == false)
								break;
							client = null;
							return;
						case 0:
							client = null;
							UI.printMessage("Zostales wylogowany");
							return;
						}
					} catch (Exception e) {
						UI.printErrorMessage(e.getMessage());
					}
				}
		}

		//FUNKCJE KLIENTA
		/**
		 * Metoda zwiekszajaca stan portfela klienta.
		 * @param client
		 * @throws Exception
		 */
		public void increaseBalance1(Client client)throws Exception {
				float amount;
				UI.printMessage("\nDOLADOWANIE KONTA");
				amount = UI.enterFloat("Podaj kwote: ");
				client.increaseBalance(amount);
		}
		
		/**
		 * Metoda wypisujaca liste produktow.
		 */
		public  void listAllProducts() {
			StringBuilder list = new StringBuilder("\nLISTA PRODUKTOW\n");
			list.append(sklep.listCommodities());		
			UI.printMessage(list.toString());
		}
		
		
		Purchase purchase = new Purchase();
		/**
		 * Metoda realizujaca zakup i platnosc.
		 * @param client
		 * @param password
		 * @throws Exception
		 */
		public void pay1(Client client,String password)throws Exception {
			listAllProducts();
			String serialNumber;
			int quantity;
			Commodity commodity;
			UI.printMessage("\nZAPLATA");
			serialNumber = UI.enterString("Podaj numer seryjny produktu: ");
			commodity=sklep.findCommodity(serialNumber);
			quantity = UI.enterInt("Podaj ilosc: ");
			client.pay(password, commodity, quantity);
			float price=commodity.getPrice();
			int tnr=sklep.setTransactionNr(purchase);
			tnr++;		
			purchase=sklep.addPurchaseToList(tnr, client, commodity, quantity, price);
			int reserves=commodity.getReserves();
			if (commodity.getPrice()*quantity<=client.getBalance()){
			if(reserves>=quantity)
			{
			sklep.increaseTurnover(commodity.getPrice()*quantity);
			}
			}
		}
		
		/** 
		 * Metoda wypisujaca liste zakupow.
		 * @param client
		 */
		public void showAllPurchases(Client client){
			UI.printMessage("\nHISTORIA ZAKUPOW\n");
			UI.printMessage(sklep.listPurchases());
		}
		
		/**
		 * Metoda wypisujaca liste zakupow klienta.
		 * @return client
		 */
		public Client showClientPurchases(){
			String id;
			Client client;
			listAllClients();
			while(true) {
			id=UI.enterString("Podaj id klienta, ktorego historie chcesz wyswietlic: ");
				if(id.equals("")){
					return null;
				}else{
					try{
					client=sklep.findClient(id);
					UI.printMessage("\nHISTORIA ZAKUPOW KLIENTA"+ client.toStringBought()+"\n");
					UI.printMessage(sklep.listClientPurchases(client));
					}catch(Exception e){
						UI.printErrorMessage(e.getMessage());
						continue;
					}
					return client;
				}
			}
		}
		/**
		 * Metoda wypisujaca liste zakupow klienta przyjmujaca za parametr klineta.
		 * Metoda przeznaczona do wyswietlania historii zakupow przez klienta.
		 * @param client
		 * @return client
		 */
		public Client showMyPurchases(Client client){
			while(true) {
					try{
					UI.printMessage("\nMOJA HISTORIA ZAKUPOW\n");
					UI.printMessage(sklep.listClientPurchases(client));
					}catch(Exception e){
						UI.printErrorMessage(e.getMessage());
						continue;
					}
					return client;
			}
		}
		/**
		 * Metoda odswiazajaca liste zakupow klienta
		 * @return
		 */
		public Client showClientPurchases1(){
			String id;
			Client client;
			listAllClients();
			while(true) {
			id=UI.enterString("Podaj id klienta, ktorego historie chcesz wyswietlic: ");			
				if(id.equals("")){
					return null;
				}client=sklep.findClient(id);
				
				return client;
			}	
		}
		/**
		 * Metoda zmieniajaca haslo.
		 * @param client
		 * @param password
		 * @return newPassword
		 */
		public  String changePassword(Client client, String password) throws Exception {
			String newPassword;
			
			UI.printMessage("\nZMIANA HASLA DO KONTA");
			newPassword = UI.enterString("Podaj nowe haslo: ");
			client.setPassword(password, newPassword);
			return newPassword;
		}
		/**
		 * Metoda zmieniajaca dane klienta.
		 * @param client
		 * @param password
		 */
		public  void changeOwnerData(Client client, String password){
			String newSurname;
			String newAdress;
			UI.printMessage("\nZMIANA DANYCH KLIENTA");
			newSurname = UI.enterString("Podaj nazwisko: ");
			newAdress = UI.enterString("Podaj adres: ");
			client.setSurname(newSurname);
			client.setAdress(newAdress);
		}
		/**
		 * Metoda usuwajaca klienta.
		 * @param client
		 * @param password
		 * @return
		 * @throws Exception
		 */
		public boolean removeClient(Client client, String password) throws Exception {
			String answer;
			UI.printMessage("\nUSUWANIE KONTA");
			answer = UI.enterString("Czy na pewno usunac to konto? (TAK/NIE)");
			if (!answer.equals("TAK")) {
				UI.printErrorMessage("\nKonto nie zostalo usuniete");
				return false;
			}

			sklep.removeClient(client);

			UI.printInfoMessage("\nKonto zostalo usuniete");
			return true;
		}
		
	
		

		
		
		
		//FUNKCJE ADMINA
		/**
		 * Metoda dodajaca produkt do listy produktow.
		 */
		public void addProduct(){
			
			String newSerialNumber;
			String newName;
			String tmp;
			int newReserves;
			float newPrice;
			Commodity newCommodity;
			
			UI.printMessage("\nDODAWANIE PRODUKTU\n");
			while(true) {
				newSerialNumber = UI.enterString("Podaj numer seryjny:");
				if (newSerialNumber.equals("")) return;  // rezygnacja z dodawania produktu
				if (sklep.findCommodity(newSerialNumber)!=null) {
					
					tmp=UI.enterString("Podaj ilosc towaru:");
					if (tmp.equals("")) return;  // rezygnacja z dodawania produktu
					newReserves = Integer.parseInt(tmp);
					if(newReserves<0){
						UI.printErrorMessage("Ilość nie moze byc ujemna");
						break;
					}
					Commodity commodity;
					commodity=sklep.findCommodity(newSerialNumber);
					int reserves=commodity.getReserves();
					commodity.setReserves(reserves+=newReserves);
					UI.printMessage("Ilosc produktu zostala zwiekszona");
					break;
				}
				
				newName = UI.enterString("Podaj nazwe produktu:");	
				tmp=UI.enterString("Podaj ilosc towaru:");
				if (tmp.equals("")) return;  // rezygnacja z dodawania produktu
				newReserves = Integer.parseInt(tmp);
				tmp=UI.enterString("Podaj cene jednostkowa:");
				if (tmp.equals("")) return;  // rezygnacja z dodawania produktu
				newPrice = Float.parseFloat(tmp);
				try {
					newCommodity = sklep.addCommodity(newSerialNumber,newName,newPrice, newReserves);
				} catch (Exception e) {
					UI.printErrorMessage(e.getMessage());
					continue;
				}
				UI.printMessage("Produkt zostal dodany do sklepu");
				break;
			}
			
		}
		
		/**
		 * Metoda wypisujaca liste klientow.
		 */
		public void listAllClients(){
			StringBuilder list = new StringBuilder("\nLISTA KLIENTOW\n");
			list.append(sklep.listClients());		
			UI.printMessage(list.toString());
		}
		
		/**
		 * Metoda generujaca fakture.
		 */
		public void writeInvoice(){
			Client client;
			
			client=showClientPurchases1();
			String s = sklep.listClientPurchases(client);
			List<Purchase> tmp = new ArrayList<Purchase>();
			while(true){
					UI.printMessage(s);
					String stnr;
					int tnr;
					
					StringBuilder list = new StringBuilder("\nFAKTURA "+ client.toStringBought());
					UI.printMessage(list.toString());
					for(Purchase i:tmp){
						UI.printMessage(i.toString());
					}
					try {
						PrintWriter zapis = new PrintWriter("Faktury.txt");
						zapis.println("Sklep internetowy UnderTheCounter.com\n"+ client.getSurname()+"\n"+ client.getAdress());
						zapis.println(list.toString());
						for(Purchase i:tmp){
						//	totalPrice+=(i.getPrice()*i.getQuantity());
							zapis.println(i.toString());
						}
					//	zapis.println("Lacznie do zaplaty: " + totalPrice);
						zapis.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					stnr=UI.enterString("Podaj nr transakcji, ktora chcesz dodac do faktury (aby zakonczyc wcisnij ENTER): ");
					
					if(stnr.equals("")){
						return;
					}else{
						tnr=Integer.parseInt(stnr);
						int flaga =0;
						for(Purchase purchase: tmp){
							if(tnr==purchase.getTransactionNr()){
								flaga = 1;
							} 
						}
						if(flaga == 0){
							Purchase tmp2 = sklep.findPurchase(client, tnr);
							tmp.add(tmp2);	
						}	
					}
			}
		}
		/**
		 * Metoda usuwajaca klienta
		 * @return
		 * @throws Exception
		 * wyjatek jest zglaszany jesli klient nie istnieje lub stan jego portfela jest rozny od 0
		 */
		public boolean deleteClient() throws Exception{
			listAllClients();
			
			UI.printMessage("\nUSUWANIE KONTA");
			Client client;
			String id;
			while(true){
			id=UI.enterString("Podaj id klienta, ktorego chcesz usunac:");
			if (id.equals("")){
				UI.printErrorMessage("\nId nie moze byc puste");
				return false;
			}
			
			if(sklep.findClient(id)==null){
				UI.printErrorMessage("Uzytkownik o podanym id nie istnieje!");
				return false;
			}
			client=sklep.findClient(id);
			
			String answer;
			answer = UI.enterString("Czy na pewno usunac to konto klienta "+client.getId() +" ? (TAK/NIE)");
			if (!answer.equals("TAK")) {
				UI.printErrorMessage("\nKonto nie zostalo usuniete");
				return false;
			}
			sklep.removeClient(client);
			UI.printInfoMessage("\nKonto zostalo usuniete");
			return true;
			}
		}
		/**
		 * Metoda zmieniajaca haslo wlasciciela
		 * @param owner
		 * @param password
		 * @return newPassword
		 * @throws Exception
		 */
		public  String changePassword(Owner owner,String password) throws Exception {
			String newPassword;
			
			UI.printMessage("\nZMIANA HASLA DO KONTA");
			newPassword = UI.enterString("Podaj nowe haslo: ");
			owner.setPassword(password, newPassword);
			return newPassword;
		}
		

/**
 * Metoda usuwajaca produkt z listy produktow.
 * @return
 * @throws Exception
 * wyjatek jest zglaszany jesli podany numer produktu jest pusty
 */
		
		public boolean deleteProduct() throws Exception{
			listAllProducts();
			UI.printMessage("\nUSUWANIE PRODUKTU");
			Commodity commodity;
			String serialNumber;
			while(true){
			serialNumber=UI.enterString("Podaj numer seryjny produktu, ktory chcesz usunac:");
			if (serialNumber.equals("")){
				UI.printErrorMessage("\nNumer seryjny nie moze byc pusty");
				return false;
			}
			
			if(sklep.findCommodity(serialNumber)==null){
				UI.printErrorMessage("Produkt o podanym numerze seryjnym nie istnieje!");
				return false;
			}
			commodity=sklep.findCommodity(serialNumber);
			
			String answer;
			
			answer = UI.enterString("Czy na pewno usunac produkt o numerze seryjnym "+commodity.getSerialNumber() +" ? (TAK/NIE)");
			if (!answer.equals("TAK")) {
				UI.printErrorMessage("\nProdukt nie zostal usuniety");
				return false;
			}
			sklep.removeCommodity(commodity);
			UI.printInfoMessage("\nProdukt zostal usuniety");
			return true;
			}
		}
		
		/**
		 * Metoda zmieniajaca cene produktu.
		 */
		public void changeProductPrice(){
			listAllProducts();
			UI.printMessage("\nZMIANA CENY PRODUKTU");
			Commodity commodity;
			String serialNumber;
			Float price;
			
			while(true){
				serialNumber=UI.enterString("Podaj numer seryjny produktu, ktorego cene chcesz zmienic:");
				if (serialNumber.equals("")){
					UI.printErrorMessage("\nNumer seryjny nie moze byc pusty");
					break;
				}
				
				if(sklep.findCommodity(serialNumber)==null){
					UI.printErrorMessage("Produkt o podanym numerze seryjnym nie istnieje!");
					break;
				}
				commodity=sklep.findCommodity(serialNumber);
				price=UI.enterFloat("Podaj nowa cene:");
				commodity.setPrice(price);
				break;
			}
		}
		
		/**
		 * Metoda sprawdzajaca utarg.
		 */
		public void checkTurnover(){
			UI.printMessage("Utarg: "+sklep.getTurnover());
		}
		
		
		
}
