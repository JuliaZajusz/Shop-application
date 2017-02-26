package lab2zadanie;

/* 
 *  Interfejs UserDialog
 *  
 *  Prosta biblioteka metod do realizacji
 *  dialogu z uzytkownikiem w prostych aplikacjach
 *  bez graficznego interfejsu uzytkownika.
 *  
 */

public interface UserDialog{
	
	 /** Komunikat o blednym formacie wprowadzonych danych. */
    static final String ERROR_MESSAGE =
          "Nieprawidlowe dane!\nSprobuj jeszcze raz.";

  
	public void printMessage(String message);
	
	public void printInfoMessage(String message);
	
	public void printErrorMessage(String message);
	
	public void clearConsole();
	
	public String enterString(String prompt);
	
	public char enterChar(String prompt);
	
	public int enterInt(String prompt);
	
	public float enterFloat(String prompt);
	
	public double enterDouble(String prompt);
	
}
