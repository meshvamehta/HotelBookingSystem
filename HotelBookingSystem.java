import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author Meshva Mehta
 * COMP2911 ass1 15s1
 */
public class HotelBookingSystem {

	/**
	 * @param args
	 * @throws ParseException if invalid date is found
	 */
	public static void main(String[] args) throws ParseException {
		try {
			//New scanner to read from the file
			Scanner scan = new Scanner(new FileReader(args[0]));
			
			//Creating a new HotelManager that will deal with all the bookings and allocate valid commands
			HotelManager manager = new HotelManager();
			
			//A marker that scans through each word in the line and helps to allocate correct commands
			String marker;
		   
			//Keep reading while the file is not finished reading
		   while(scan.hasNextLine() != false){
			   
			   String input = scan.nextLine();
			   //Starting with Hotel implies input is reading the data about the hotels 
			   if(input.startsWith ("Hotel ")) {
		    		//Scanning in attributes
				   input = input.substring(6); 
		                            
				   int i = input.indexOf(" ");
				   String hotelName = input.substring(0, i);
		            
				   int j = input.indexOf(" ", i+1);
				   marker = input.substring(i+1, j);
				   int roomNumber = Integer.parseInt(marker);
		     
				   marker = input.substring(j+1, input.length());
				   int roomSize = Integer.parseInt(marker);        
				   manager.addHotel(hotelName, roomNumber, roomSize);
				   
				   //Starting with Book implies all data about hotels has been fed in, and now make a Booking
			   	} else if (input.startsWith("Booking ")) {
		    		//Scanning in elementary constant attributes
			   		input = input.substring(8);
			   		String[] arguments = input.split(" ");
			   		
			   		String idTemp = arguments[0];
			   		Integer id = Integer.parseInt(idTemp);
			   		
			   		//This makes sure that duplicate bookings are not made
			   		if (manager.getBooking(id) == true) {
			   			System.out.println("Booking rejected");
			   		} else {
			   		
				   		String month = arguments[1];
				   		
				   		String tempDate = arguments[2];
				   		int date = Integer.parseInt(tempDate);
				   		
				   		String tempNights = arguments[3];
				   		int numberNights = Integer.parseInt(tempNights);
				   		String rSize = arguments[4];
				   		
				   		//Number of single, double and triple rooms needed are stored in here.
				   		int numSingles = 0;
				   		int numDoubles = 0;
				   		int numTriples = 0;
				   		
				   		boolean check = false;
				   		
				   		String bookingKind = "Booking ";
	
				   		//Different conditions used in input (note we assume that rooms given in correct order i.e. single, double, triple)
				   		if(arguments.length == 6) {
					   		if (rSize.equals("single")) {
					   			String tempNumSingles = arguments[5];
					   			numSingles = Integer.parseInt(tempNumSingles);
						   		check = manager.newBooking(month, date, numberNights, id, numSingles, numDoubles, numTriples, bookingKind);
						   		if (check == false) {
						   			System.out.println("Booking rejected");
						   		}
					   			
					   		} else if (rSize.equals("double")) {
					   			String tempNumDoubles = arguments[5];
					   			numDoubles = Integer.parseInt(tempNumDoubles);
						   		check = manager.newBooking(month, date, numberNights, id, numSingles, numDoubles, numTriples, bookingKind);
						   		if (check == false) {
						   			System.out.println("Booking rejected");
						   		}
	
					   		} else if (rSize.equals("triple")) {
					   			String tempNumTriples = arguments[5];
					   			numTriples = Integer.parseInt(tempNumTriples);
						   		check = manager.newBooking(month, date, numberNights, id, numSingles, numDoubles, numTriples, bookingKind);
						   		if (check == false) {
						   			System.out.println("Booking rejected");
						   		}
					   			
					   		} else {
					   			System.out.println("Booking rejected");
					   		}
	
				   		} else if (arguments.length == 8) {
					   		if (rSize.equals("single")) {
					   			String tempNumSingles = arguments[5];
					   			numSingles = Integer.parseInt(tempNumSingles);
					   			
					   			String type2 = arguments[6];
					   			if (type2.equals("double")) {
					   				String tempNumDoubles = arguments[7];
						   			numDoubles = Integer.parseInt(tempNumDoubles);
							   		check = manager.newBooking(month, date, numberNights, id, numSingles, numDoubles, numTriples, bookingKind);
							   		if (check == false) {
							   			System.out.println("Booking rejected");
							   		}
					   			} else if (type2.endsWith("triple")) {
					   				String tempNumTriples = arguments[7];
						   			numTriples = Integer.parseInt(tempNumTriples);
							   		check = manager.newBooking(month, date, numberNights, id, numSingles, numDoubles, numTriples, bookingKind);
							   		if (check == false) {
							   			System.out.println("Booking rejected");
							   		}
					   			} else {
					   				System.out.println("Booking rejected");
					   			}
		
					   		} else if (rSize.equals("double")) {
					   			String tempNumDoubles = arguments[5];
					   			numDoubles = Integer.parseInt(tempNumDoubles);
					   			
					   			String type2 = arguments[6];
					   			if (type2.equals("triple")) {
					   				String tempNumTriples = arguments[7];
						   			numTriples = Integer.parseInt(tempNumTriples);
							   		check = manager.newBooking(month, date, numberNights, id, numSingles, numDoubles, numTriples, bookingKind);
							   		if (check == false) {
							   			System.out.println("Booking rejected");
							   		}
					   			} else {
					   				System.out.println("Booking rejected");
					   			}
					   			
					   		} else if (rSize.equals("triple")) {
					   			System.out.println("Booking rejected");				   			
					   		} else {
					   			System.out.println("Booking rejected");
					   		}
				   			
				   		} else if (arguments.length == 10) {
				   			
				   			if (!(arguments[4].equals("single"))) {
				   				System.out.println("Booking rejected");
				   			} else if (!(arguments[6].equals("double"))) {
				   				System.out.println("Booking rejected");
				   			} else if (!(arguments[8].equals("triple"))) {
				   				System.out.println("Booking rejected");
				   			} else {
					   			String tempNumSingles = arguments[5];
					   			numSingles = Integer.parseInt(tempNumSingles);
					   			
					   			String tempNumDoubles = arguments[7];
					   			numDoubles = Integer.parseInt(tempNumDoubles);
					   			
					   			String tempNumTriples = arguments[9];
					   			numTriples = Integer.parseInt(tempNumTriples);	
						   		check = manager.newBooking(month, date, numberNights, id, numSingles, numDoubles, numTriples, bookingKind);
						   		if (check == false) {
						   			System.out.println("Booking rejected");
						   		}
				   			}
				   		}
			   		}	
		    	} if(input.startsWith ("Change ")) {
		    		//Scanning in attributes
					input = input.substring(7); 
					String bookingKind = "Change ";
					
				   	String[] arguments = input.split(" ");
				   		
				   	String idTemp = arguments[0];
				   	Integer id = Integer.parseInt(idTemp);
				   	if (manager.canRemove(id) == false) {
				   		System.out.println("Change rejected");
				   	} else {
				   		String month = arguments[1];
				   		
				   		String tempDate = arguments[2];
				   		int date = Integer.parseInt(tempDate);
				   		
				   		String tempNights = arguments[3];
				   		int numberNights = Integer.parseInt(tempNights);
				   		
				   		//Get the first room type
				   		String rSize = arguments[4];
				   		
				   		//Number of single, double and triple rooms needed are stored in here.
				   		int numSingles = 0;
				   		int numDoubles = 0;
				   		int numTriples = 0;
				   		
				   		boolean check = false;

				   		//Different conditions used in input (note we assume that rooms given in correct order i.e. single, double, triple)
				   		if(arguments.length == 6) {
					   		if (rSize.equals("single")) {
					   			
					   			String tempNumSingles = arguments[5];
					   			numSingles = Integer.parseInt(tempNumSingles);
						   		check = manager.checkAllHotels(month, date, numberNights, id, numSingles, numDoubles, numTriples);
						   		if (check == false) {
						   			System.out.println("Change rejected");
						   		} else {
						   			manager.removeBooking(id);
						   			manager.newBooking(month, date, numberNights, id, numSingles, numDoubles, numTriples, bookingKind);
						   		}
					   			
					   		} else if (rSize.equals("double")) {
					   			String tempNumDoubles = arguments[5];
					   			numDoubles = Integer.parseInt(tempNumDoubles);
						   		check = manager.checkAllHotels(month, date, numberNights, id, numSingles, numDoubles, numTriples);
						   		if (check == false) {
						   			System.out.println("Change rejected");
						   		} else {
						   			manager.removeBooking(id);
						   			manager.newBooking(month, date, numberNights, id, numSingles, numDoubles, numTriples, bookingKind);
						   		}

					   		} else if (rSize.equals("triple")) {
					   			String tempNumTriples = arguments[5];
					   			numTriples = Integer.parseInt(tempNumTriples);
						   		check = manager.checkAllHotels(month, date, numberNights, id, numSingles, numDoubles, numTriples);
						   		if (check == false) {
						   			System.out.println("Change rejected");
						   		} else {
						   			manager.removeBooking(id);
						   			manager.newBooking(month, date, numberNights, id, numSingles, numDoubles, numTriples, bookingKind);
						   		}
					   			
					   		} else {
					   			System.out.println("Change rejected");
					   		}

				   		} else if (arguments.length == 8) {
					   		if (rSize.equals("single")) {
					   			String tempNumSingles = arguments[5];
					   			numSingles = Integer.parseInt(tempNumSingles);
					   			
					   			String type2 = arguments[6];
					   			if (type2.equals("double")) {
					   				String tempNumDoubles = arguments[7];
						   			numDoubles = Integer.parseInt(tempNumDoubles);
							   		check = manager.checkAllHotels(month, date, numberNights, id, numSingles, numDoubles, numTriples);
							   		if (check == false) {
							   			System.out.println("Change rejected");
							   		} else {
							   			manager.removeBooking(id);
							   			manager.newBooking(month, date, numberNights, id, numSingles, numDoubles, numTriples, bookingKind);
							   		}
					   			} else if (type2.endsWith("triple")) {
					   				String tempNumTriples = arguments[7];
						   			numTriples = Integer.parseInt(tempNumTriples);
						   			check = manager.checkAllHotels(month, date, numberNights, id, numSingles, numDoubles, numTriples);
							   		if (check == false) {
							   			System.out.println("Change rejected");
							   		} else {
							   			manager.removeBooking(id);
							   			manager.newBooking(month, date, numberNights, id, numSingles, numDoubles, numTriples, bookingKind);
							   		}
					   			} else {
					   				System.out.println("Change rejected");
					   			}
		
					   		} else if (rSize.equals("double")) {
					   			String tempNumDoubles = arguments[5];
					   			numDoubles = Integer.parseInt(tempNumDoubles);
					   			
					   			String type2 = arguments[6];
					   			if (type2.equals("triple")) {
					   				String tempNumTriples = arguments[7];
						   			numTriples = Integer.parseInt(tempNumTriples);
						   			check = manager.checkAllHotels(month, date, numberNights, id, numSingles, numDoubles, numTriples);
							   		if (check == false) {
							   			System.out.println("Change rejected");
							   		} else {
							   			manager.removeBooking(id);
							   			manager.newBooking(month, date, numberNights, id, numSingles, numDoubles, numTriples, bookingKind);
							   		}
					   			} else {
					   				System.out.println("Change rejected");
					   			}
					   			
					   		} else if (rSize.equals("triple")) {
					   			System.out.println("Change rejected");				   			
					   		} else {
					   			System.out.println("Booking rejected");
					   		}
				   			
				   		} else if (arguments.length == 10) {
				   			
				   			if (!(arguments[4].equals("single"))) {
				   				System.out.println("Change rejected");
				   			} else if (!(arguments[6].equals("double"))) {
				   				System.out.println("Change rejected");
				   			} else if (!(arguments[8].equals("triple"))) {
				   				System.out.println("Change rejected");
				   			} else {
					   			String tempNumSingles = arguments[5];
					   			numSingles = Integer.parseInt(tempNumSingles);
					   			
					   			String tempNumDoubles = arguments[7];
					   			numDoubles = Integer.parseInt(tempNumDoubles);
					   			
					   			String tempNumTriples = arguments[9];
					   			numTriples = Integer.parseInt(tempNumTriples);	
					   			check = manager.checkAllHotels(month, date, numberNights, id, numSingles, numDoubles, numTriples);
						   		if (check == false) {
						   			System.out.println("Change rejected");
						   		} else {
						   			manager.removeBooking(id);
						   			manager.newBooking(month, date, numberNights, id, numSingles, numDoubles, numTriples, bookingKind);
						   		}
				   			}
				   		}
					   	
				   	}	   
		    	} if(input.startsWith ("Cancel ")) {
					input = input.substring(7);
					
					String stringId = input.substring(0, input.length());
					Integer id = Integer.parseInt(stringId);
					
					boolean check = manager.removeBooking(id);
					if (check == true) {
						System.out.println("Cancel " + id);
					} else {
						System.out.println("Cancel rejected");
					}
	   
		    	} if (input.startsWith("Print ")) {
		    		input = input.substring(6);
					
					String hotelToPrint = input.substring(0, input.length());
					Hotel hotel = manager.getHotel(hotelToPrint);
					ArrayList<Room> rooms = hotel.getRooms();
					
					for (int i = 0; i != rooms.size(); i++) {
						System.out.println(hotelToPrint + " " + rooms.get(i).getRoomNumber() + rooms.get(i).printBookings());
					}
					//System.out.print(" ");
		    	}
		   }
		   //Close the scanner
		   scan.close();
		} catch (FileNotFoundException e) {
			   
		}
	}

}
