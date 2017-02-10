import java.text.ParseException;
import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Meshva Mehta
 * COMP2911 ass1 15s1
 */
public class Hotel {
		//The name of the hotel
		String hotelName;
		//The list of rooms within this hotel
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		/**
		 * Constructor builds a new hotel under the input name
		 * @param hotelName the name of a hotel
		 */
		public Hotel(String hotelName) {
			this.hotelName = hotelName;
	
		}
		
		/**
		 *  gets the name of the hotel
		 * @return the name of a given hotel;
		 */
		public String getHotelName() {
			return hotelName;
		}
		
		/**
		 * Sets a new room when given a room number and size
		 * @param roomNumber takes in a room number
		 * @param roomSize the size of the room they want to book
		 */
		public void setRoom(int roomNumber, int roomSize) {
			Room newRoom = new Room(roomNumber, roomSize, this.hotelName);
			if (rooms.contains(newRoom) == false) {
				rooms.add(newRoom);
			} else {
				//System.out.println("Booking rejected");
			}
		}
		
		/**
		 * Returns a room by inputing its room number within a hotel
		 * @param roomNumber a specified room
		 * @return the specified room
		 */
		public Room getRoom(int roomNumber) {
			int index = 0;
			
			while (index != rooms.size()) {
				//checking if the room numbers are the same, if yes return the room
				int roomNum = rooms.get(index).getRoomNumber();
				if (roomNum == roomNumber) {
					return rooms.get(index);
				}
				
				index++;
			}
			//room has not been found, return null
			return null;
		}
		
		/**
		 * 
		 * @return The list of rooms in the hotel
		 */
		public ArrayList<Room> getRooms() {
			return rooms;
		}
	
		/**
		 * This method checks a hotel the amount of single rooms free on a given date
		 * @param month the month that is required to book
		 * @param date the date that is required to book
		 * @param numberNights the number of nights the booking is for
		 * @return returns the number of free single rooms
		 * @throws ParseException for incorrect data entered
		 */
		public int getNumSinglesAvailable(String month, int date, int numberNights, Integer id) throws ParseException {
			int total = 0;
			for (int i = 0; i != rooms.size(); i++) {
				if (rooms.get(i).getRoomSize() == 1) {
					Room temp = rooms.get(i);
					if (temp.isAvailable(month, date, numberNights, id) == true) {
						total = total + 1;
					}
				}
			}
			
			return total;
		}
		
		/**
		 * This method checks a hotel the amount of double rooms free on a given date
		 * @param month the month that is required to book
		 * @param date the date that is required to book
		 * @param numberNights the number of nights the booking is for
		 * @return returns the number of free double rooms
		 * @throws ParseException for incorrect data entered
		 */
		public int getNumDoublesAvailable(String month, int date, int numberNights, Integer id) throws ParseException {
			int total = 0;
			for (int i = 0; i != rooms.size(); i++) {
				if (rooms.get(i).getRoomSize() == 2) {
					Room temp = rooms.get(i);
					if (temp.isAvailable(month, date, numberNights, id) == true) {
						total = total + 1;
					}
				}
			}
			
			return total;
		}
		
		/**
		 * This method checks a hotel for the amount of triple rooms free on a given date
		 * @param month the month that is required to book
		 * @param date the date that is required to book
		 * @param numberNights the number of nights the booking is for
		 * @return returns the number of free triple rooms
		 * @throws ParseException for incorrect data entered
		 */
		public int getNumTriplesAvailable(String month, int date, int numberNights, Integer id) throws ParseException {
			int total = 0;
			for (int i = 0; i != rooms.size(); i++) {
				if (rooms.get(i).getRoomSize() == 3) {
					Room temp = rooms.get(i);
					if (temp.isAvailable(month, date, numberNights, id) == true) {
						total = total + 1;
					}
				}
			}
			
			return total;
		}
		
}
