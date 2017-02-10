import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 */

/**
 * @author Meshva Mehta (z3463610)
 * COMP2911 ass1 15s1
 */
public class HotelManager {
	
		//A list of hotels in this system
		ArrayList<Hotel> hotels;
		
		//A list of hotel names of the hotels in the system
		ArrayList<String> hotelNames;
		
		//HashMap bookings stores all the bookings made with id as key and hotel as to which hotel they are in
		HashMap<Integer, Hotel> bookings;
		
		/**
		 * Constructor for the hotel manager empty list of hotels, hotelNames and the bookings
		 */
		public HotelManager() {
			this.hotels = new ArrayList<Hotel> ();
			this.hotelNames = new ArrayList<String>();
			this.bookings = new HashMap<Integer, Hotel>();
		}

		/**
		 * 
		 * @param hotelName the name of the hotel that is required
		 * @return The Hotel with name hotelName
		 */
		public Hotel getHotel(String hotelName) {
			if (hotelNames.contains(hotelName)) {
				int hotelIndex = hotelNames.indexOf(hotelName);
				
				return hotels.get(hotelIndex);
			}
			return null;
		}
			
		/**
		 * 
		 * @param currentID the id to check if it exists or not
		 * @return True if id (i.e. a valid booking was made) exists and false otherwise
		 */
		public boolean getBooking(Integer currentID) {
			if (bookings.containsKey(currentID)) {
				return true;
			}
			return false;
		}
		
		/**
		 * Adds a hotel to the list of hotels if it is new or adds the room to the existing hotel
		 * @param hotelName adds a new hotel or adds to an existing hotel
		 * @param roomNumber a new room to add to the hotel
		 * @param roomSize the size/capacity of the room to be added
		 */
		public void addHotel(String hotelName, int roomNumber, int roomSize) {
			
			if (hotelNames.contains(hotelName) == true) {
				//just adding a new room to the hotel that already exists
				hotels.get(hotelNames.indexOf(hotelName)).setRoom(roomNumber, roomSize);
			} else {
				
				//Creating a new hotel adding it to the list of hotels and also adding it's name to the list of hotel names
				Hotel newHotel = new Hotel(hotelName);
				hotels.add(newHotel);
				hotelNames.add(hotelName);
				newHotel.setRoom(roomNumber, roomSize);
			}
		}
		
		/**
		 * Converts the string room capacity/size to an integer
		 * @param sizeString is the capacity of the room as a string i.e. single, double or triple
		 * @return the string capacity as an integer i.e. single = 1, double = 2 and triple = 3
		 */
		public int roomSizeConverter(String sizeString) {
	
			int size = 0;
			if (sizeString .equals("single")) {
				size = 1;
			} else if (sizeString .equals("double")) {
				size = 2;
			} else if (sizeString .equals("triple")) {
				size = 3;
			} else {
				System.out.println("incorrect room size");
			}
			
			return size;
		}
		
		/**
		 * Gets the first instance of an available room (in any hotel) that is available for booking with the specified attributes
		 * @param rSize is the room capacity that is required
		 * @param month the month (January - December) to book in the year
		 * @param date the date (1-31) of when to book in the month
		 * @param numberNights the customer wants to book for
		 * @return Room that is available in a list of hotels with specified requirements
		 * @throws ParseException if an incorrect date is found
		 */
		public Room getRequiredRoom(String rSize, String month, int date, int numberNights, Integer id) throws ParseException {
			//converts the string room size to an integer
			int rSizeInt = roomSizeConverter(rSize);
			
			//Checking for room in all available hotels
			for(int i = 0; i != hotels.size(); i++) {
				
				//Checking for all rooms in a hotel if it is the correct size and is available
				for (int j = 0; j!= hotels.get(i).rooms.size(); j++) {
					
					int checkSize = hotels.get(i).rooms.get(j).roomSize;
					
					//Checking if the room is of the correct size
					if(checkSize == rSizeInt) {
						
						//Checking if the room has a booking available for the given date
						if (hotels.get(i).rooms.get(j).isAvailable(month, date, numberNights, id) == true) {
							return hotels.get(i).rooms.get(j);
							
						}
					}
				}
			}
			
			//returns null if no room was found of the specification
			return null;
		}
				
		/**
		 * Checks if a multiple bookings of rooms are available in same hotel
		 * @param temp checks the hotel specified if a booking specified can be made
		 * @param month month to make the new booking
		 * @param date the date of the new booking
		 * @param numberNights number of nights the customer want to book
		 * @param numSingles number of single rooms they wish to book
		 * @param numDoubles number of double rooms they wish to book
		 * @param numTripples number of triple rooms they wish to book
		 * @return true if the booking of such specification can be made, false otherwise
		 * @throws ParseException for incorrect dates
		 */
		public boolean checkHotelRooms(Hotel temp, String month, int date, int numberNights, Integer id, int numSingles, int numDoubles, int numTripples) throws ParseException {
	
			if ((temp.getNumSinglesAvailable(month, date, numberNights, id) >= numSingles) && (temp.getNumDoublesAvailable(month, date, numberNights, id) >= numDoubles) && (temp.getNumTriplesAvailable(month, date, numberNights, id) >= numTripples)) {
				return true;
			}
			
			return false;
		}
		
		/**
		 * 
		 * @param month the month the booking is in
		 * @param date the date the booking starts
		 * @param numberNights the number of nights the booking needs to be made for
		 * @param id the id under which the booking needs to be made
		 * @param numSingles the number of single rooms required
		 * @param numDoubles the number of double rooms required
		 * @param numTriples the number of triple rooms required
		 * @return True if this booking is possible and false otherwise
		 * @throws ParseException if invalid date is found
		 */
		public boolean checkAllHotels(String month, int date, int numberNights, Integer id, int numSingles, int numDoubles, int numTriples) throws ParseException {
			for(int i = 0; i != hotels.size(); i++) {
				Hotel temp = hotels.get(i);
				boolean check = checkHotelRooms(temp, month, date, numberNights, id, numSingles, numDoubles, numTriples);
				
				if (check == true) {
					return true;
				}
			}
			
			return false;
		}
		
		/**
		 * This method checks if a room of specific requirement is available to book or not
		 * @param rSize is the room capacity that is required
		 * @param month the month (January - December) to book in the year
		 * @param date the date (1-31) of when to book in the month
		 * @param numberNights the customer wants to book for
		 * @return Room that is available in a list of hotels with specified requirements
		 * @throws ParseException if an incorrect date is found
		 */
		public boolean canBook(String rSize, String month, int date, int numberNights, Integer id) throws ParseException {
			Room newRoom = getRequiredRoom(rSize, month, date, numberNights, id);
			if (newRoom == null) {
				return false;
			} else {
				return true;
			}
			
		}
				
		/**
		 * This method finds the first available room in any hotel matching the requirements of the customer
		 * @param rSize is the room capacity that is required
		 * @param month the month (January - December) to book in the year
		 * @param date the date (1-31) of when to book in the month
		 * @param numberNights the customer wants to book for
		 * @return Room that is available in a list of hotels with specified requirements
		 * @throws ParseException if an incorrect date is found
		 */
		public Room book(String rSize, String month, int date, int numberNights, Integer id) throws ParseException {
			if (canBook(rSize, month, date, numberNights, id) == true) {
				Room newRoom = getRequiredRoom(rSize, month, date, numberNights, id);
				newRoom.bookRoom(month, date, numberNights, id);
				return newRoom;
			}
			return null;
		}
			
		/**
		 * 
		 * @param month the month the booking is in
		 * @param date the date the booking starts
		 * @param numberNights the number of nights the booking needs to be made for
		 * @param id the id under which the booking needs to be made
		 * @param numSingles the number of single rooms required
		 * @param numDoubles the number of double rooms required
		 * @param numTriples the number of triple rooms required
		 * @param bookingKind the kind of booking (i.e. new booking or change)
		 * @return True if booking made and false otherwise
		 * @throws ParseException if incorrect date found
		 */
		public boolean newBooking (String month, int date, int numberNights, Integer id, int numSingles, int numDoubles, int numTriples, String bookingKind) throws ParseException {
			
			for(int i = 0; i != hotels.size(); i++) {			
				Hotel toBookIn = hotels.get(i);
				
				if (checkHotelRooms(toBookIn, month, date, numberNights, id, numSingles, numDoubles, numTriples) == true) {
					System.out.print(bookingKind + id + " " + toBookIn.getHotelName());
					bookings.put(id, toBookIn);
					int singles = 0;
					int doubles = 0;
					int triples = 0;
					
					for (int a = 0; a != toBookIn.rooms.size(); a++) {
	
						Room checkRoom = toBookIn.rooms.get(a);
						if (checkRoom.getRoomSize() == 1 && checkRoom.isAvailable(month, date, numberNights, id) == true && numSingles != 0) {
							if (singles != numSingles) {
								checkRoom.bookRoom(month, date, numberNights, id);
								System.out.print(" " + checkRoom.getRoomNumber());
								singles = singles +1;
							}
	
						} else if (checkRoom.getRoomSize() == 2 && checkRoom.isAvailable(month, date, numberNights, id) == true && numDoubles != 0) {
							if (doubles != numDoubles) {
								checkRoom.bookRoom(month, date, numberNights, id);
								System.out.print(" " + checkRoom.getRoomNumber());
								doubles = doubles +1;
							}
	
						} else if (checkRoom.getRoomSize() == 3 && checkRoom.isAvailable(month, date, numberNights, id) == true && numTriples != 0) {
							if (triples != numTriples) {
								checkRoom.bookRoom(month, date, numberNights, id);
								System.out.print(" " + checkRoom.getRoomNumber());
								triples = triples +1;
							}					
						}					
					}
					System.out.println();
					return true;
					
				}
			}
			
			return false;
		}	
		
		/**
		 * 
		 * @param id The id of the booking that needs to be removed
		 * @return true if removed false otherwise
		 */
		public boolean removeBooking(Integer id) {
			if (!bookings.containsKey(id)) {
				return false;
			} else {
				Hotel temp = bookings.get(id);
				ArrayList<Room> toCheck = temp.getRooms();
				
				for (int i = 0; i != toCheck.size(); i++) {
					Room tempRoom = toCheck.get(i);
					if (tempRoom.getID().contains(id)) {
						tempRoom.removeBooking(id);
					}
				}
			}
			return true;
		}
		
		/**
		 * 
		 * @param idToCheck Is the id that we need to check is available
		 * @return true if the id is valid (i.e. a booking with that id is made) and false otherwise
		 */
		public boolean canRemove(Integer idToCheck) {
				if (bookings.containsKey(idToCheck)) {
					return true;
				}
				return false;
		}

}
	
