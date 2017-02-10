import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 
 */

/**
 * @author Meshva Mehta (z3463610)
 * COMP2911 ass1 15s1
 */
public class Room {
		//The room number
		public Integer roomNumber;
		//The roomSize (single, double or triple)
		public Integer roomSize;
		//The hotel this room is in
		public String inHotel;
		//An array of the id's of bookings made in this hotel
		ArrayList<Integer> id;
		//A HashMap calendar with key as the id of the bookings and the value an ArrayList of dates booked under the id
		HashMap<Integer, ArrayList<String>> calendar;
		
		/**
		 * Constructor for the class Room
		 * @param roomNumber the number of the room
		 * @param roomSize the size of the room (single, double or triple)
		 * @param inHotel the hotel in which this room is
		 */
		public Room (Integer roomNumber, Integer roomSize, String inHotel) {
			
			this.roomNumber = roomNumber;
			this.roomSize = roomSize;
			this.inHotel = inHotel;
			this.calendar = new HashMap<Integer, ArrayList<String>>();
			this.id = new ArrayList<Integer>();
			
		}
		
		/**
		 * Used for referencing and printing
		 * @return room number as an integer
		 */
		public int getRoomNumber() {
			return roomNumber;		
		}
		
		/**
		 * used for checking if room matches customer requirements
		 * @return rooms capacity as an integer
		 */
		public int getRoomSize() {
			return roomSize;
		}
		
		/**
		 * Method used mainly for referencing and prints
		 * @return the name of the hotel the room is in
		 */
		public String getHotelName() {
			return inHotel;
		}
		
		/**
		 * 
		 * @return The list of id's booked in this room
		 */
		public ArrayList<Integer> getID() {
			return this.id;
		}
		
		/**
		 * 
		 * @param month the month the booking is in
		 * @param date the date the booking wants to be made
		 * @param id the id under which the booking wants to be made
		 * @return True if the date and month is available (not within the calendar) and true if the date is within the calendar but the booking was made by the id given and returns false otherwise
		 */
		public boolean isAvailableOne(String month, Integer date, Integer id) {
			String wholeDate = convertWholeDate(month, date);
			boolean valid = isValidDate(wholeDate);
			
			//If the date is invalid then return false
			if (valid == false) { 
				return false; 
			}
			
			Collection<ArrayList<String>> dates = calendar.values();
			Iterator<ArrayList<String>> check =  dates.iterator();
			
			while (check.hasNext()) {
				ArrayList<String> current = check.next();
				
				if (current.contains(wholeDate)) {
					if(calendar.containsKey(id)) {
						ArrayList<String> temp = calendar.get(id);
						if (temp.equals(current)) {
							return true;
						}
					}
					return false;
				}
			}
			
			return true;
		}
		
		/**
		 * 
		 * @param month the month the booking is in
		 * @param date the date the booking wants to be made
		 * @param numberNights the booking wants to be made for
		 * @param id the id under which the booking wants to be made
		 * @return True if available for multiple nights false otherwise
		 * @throws ParseException if invalid date is found
		 */
		public boolean isAvailable(String month, Integer date, Integer numberNights, Integer id) throws ParseException {
			
			String wholeDate = convertWholeDate(month, date);
			String dateDummy = wholeDate;
			
			if (numberNights == 1) {
				return isAvailableOne(month, date, id);
			} else {
				int numberNightsCounter = numberNights;
				
				while (numberNightsCounter != 1) {
					String nextDate = getNextDate(dateDummy);
					
					String newDate = nextDate.substring(0, 3);
					String newMonthTwo = nextDate.substring(3, 6);
					String year = nextDate.substring(6, 10);
					
					if (year.equals("2016")) {
						System.out.println("Booking rejected");
						return false;
					}
					
					String convertedMonth = convertStringMonthTwo (newMonthTwo);
					int convertedDate = convertStringDate(newDate);
					
					if (!isAvailableOne(convertedMonth, convertedDate, id)) {
						return false;
					} else {
						dateDummy = nextDate;
					}
	
					numberNightsCounter--;
				}
			}
			
			return true;
		}
		
		/**
		 * 
		 * @param id the id we want to remove
		 * @return True if a booking can be removed under the given id
		 */
		public boolean isAvailableRemove(Integer id) {
	
				if (calendar.containsKey(id) == true) {
					return true;
				} else {
					return false;
				}
		}
		
		/**
		 * 
		 * @param month the month the booking is in
		 * @param date the date the booking wants to be made
		 * @param numberNights the booking wants to be made for
		 * @param id the id under which the booking wants to be made
		 * @return True if booking was made false otherwise
		 * @throws ParseException if invalid date is found
		 */
		public boolean bookRoom(String month, int date, int numberNights, Integer id) throws ParseException {
			//This is to assure that duplicate bookings are not made
			if (calendar.containsKey(id)) {
				return false;
			}
			
			if (isAvailable(month, date, numberNights, id) == false) {
				return false;		
			} else {
				String wholeDate = convertWholeDate(month, date);
				String duplicateDate = wholeDate;
				
				ArrayList<String> dates = new ArrayList<String>();
				dates.add(duplicateDate);
				
				for (int numNightsCounter = 0; numNightsCounter != (numberNights-1); numNightsCounter++) {
					String nextDate = getNextDate(duplicateDate);
					dates.add(nextDate);
					duplicateDate = nextDate;
				}
				
				calendar.put(id, dates);
				this.id.add(id);
			}
			return true;
		}
		
		/**
		 * 
		 * @param id the id we want to remove
		 * @return True if the booking was removed and false otherwise
		 */
		public boolean removeBooking(Integer id) {
			
			if (isAvailableRemove(id) == false) {
				return false;
			} else {
				calendar.remove(id);
			}
	
			return true;
		}
		
		/**
		 * Method used to book for multiple dates
		 * @param allDate the simple date format of the date
		 * @return the next calendar date
		 * @throws ParseException if input is invalid
		 */
		public String getNextDate(String allDate) throws ParseException {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			Calendar c = Calendar.getInstance();
			
			c.setTime(sdf.parse(allDate));
			
			c.add(Calendar.DATE, 1);
			allDate = sdf.format(c.getTime());
			
			return allDate;
		}
		
		/**
		 * Method used to check validity of input
		 * @param date a simple date format string
		 * @return if the given date is valid or not
		 */
		public boolean isValidDate(String date) {
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date testDate = null;
			
			try {
				testDate = sdf.parse(date);
			}
			
			catch (ParseException e) {
				String errorMessage = "invalid date provided!";
				System.out.println(errorMessage);
				return false;
			}
			
			if (!sdf.format(testDate).equals(date)) {
				String errorMessage = "invalid date provided!";
				System.out.println(errorMessage);
				return false;
			}
			
			return true;
		}
		
		/**
		 * Method outputs a whole date of the simple date format, used in other methods
		 * @param oldMonth is in form "Jan" - "Dec"
		 * @param oldDate is in an integer date format
		 * @return a combined simple date format of the whole date
		 */
		public String convertWholeDate(String oldMonth, int oldDate) {
			String month = convertMonth (oldMonth);
			String date = convertDate (oldDate);
			
			String finalDate = date + month + "2015";
			
			return finalDate;
		}
		
		/**
		 * Method used to get month in simple date format used in other methods
		 * @param month in format "Jan" - "Dec"
		 * @return simple date format of month i.e. "mm/"
		 */
		public String convertMonth (String month) {
			
			String monthNumber;
			
			if (month .equals("Jan")) {
				monthNumber = "01/";
			} else if (month .equals("Feb")) {
				monthNumber = "02/";
			} else if (month .equals("Mar")) {
				monthNumber = "03/";
			} else if (month .equals("Apr")) {
				monthNumber = "04/";
			} else if (month .equals("May")) {
				monthNumber = "05/";
			} else if (month .equals("Jun")) {
				monthNumber = "06/";
			} else if (month .equals("Jul")) {
				monthNumber = "07/";
			} else if (month .equals("Aug")) {
				monthNumber = "08/";
			} else if (month .equals("Sep")) {
				monthNumber = "09/";
			} else if (month .equals("Oct")) {
				monthNumber = "10/";
			} else if (month .equals("Nov")) {
				monthNumber = "11/";
			} else if (month .equals("Dec")) {
				monthNumber = "12/";
			} else {
				throw new IllegalArgumentException("Invalid month input");
			}
			
			return monthNumber;
		}
	
		/**
		 * Method used to get integer date into simple date format used in other methods
		 * @param date an integer date
		 * @return the string date format "xx/"
		 */
		public String convertDate (int date) {
			
			String newDate;
			
			if (date == 1) {
				newDate = "01/";
			} else if (date == 2) {
				newDate = "02/";
			} else if (date == 3) {
				newDate = "03/";
			} else if (date == 4) {
				newDate = "04/";
			} else if (date == 5) {
				newDate = "05/";
			} else if (date == 6) {
				newDate = "06/";
			} else if (date == 7) {
				newDate = "07/";
			} else if (date == 8) {
				newDate = "08/";
			} else if (date == 9) {
				newDate = "09/";
			} else if (date == 10) {
				newDate = "10/";
			} else if (date == 11) {
				newDate = "11/";
			} else if (date == 12) {
				newDate = "12/";
			} else if (date == 13) {
				newDate = "13/";
			} else if (date == 14) {
				newDate = "14/";
			} else if (date == 15) {
				newDate = "15/";
			} else if (date == 16) {
				newDate = "16/";
			} else if (date == 17) {
				newDate = "17/";
			} else if (date == 18) {
				newDate = "18/";
			} else if (date == 19) {
				newDate = "19/";
			} else if (date == 20) {
				newDate = "20/";
			} else if (date == 21) {
				newDate = "21/";
			} else if (date == 22) {
				newDate = "22/";
			} else if (date == 23) {
				newDate = "23/";
			} else if (date == 24) {
				newDate = "24/";
			} else if (date == 25) {
				newDate = "25/";
			} else if (date == 26) {
				newDate = "26/";
			} else if (date == 27) {
				newDate = "27/";
			} else if (date == 28) {
				newDate = "28/";
			} else if (date == 29) {
				newDate = "29/";
			} else if (date == 30) {
				newDate = "30/";
			} else if (date == 31) {
				newDate = "31/";
			} else {
				throw new IllegalArgumentException("Invalid date input");
			}
			
			return newDate;
		}
	
		/**
		 * 
		 * @param month the string month
		 * @return The integer equivalent of string month
		 */
		public int convertStringMonth (String month) {
			
			int monthNumber;
			
			if (month .equals("01/")) {
				monthNumber = 0;
			} else if (month .equals("02/")) {
				monthNumber = 1;
			} else if (month .equals("03/")) {
				monthNumber = 2;
			} else if (month .equals("04/")) {
				monthNumber = 3;
			} else if (month .equals("05/")) {
				monthNumber = 4;
			} else if (month .equals("06/")) {
				monthNumber = 5;
			} else if (month .equals("07/")) {
				monthNumber = 6;
			} else if (month .equals("08/")) {
				monthNumber = 7;
			} else if (month .equals("09/")) {
				monthNumber = 8;
			} else if (month .equals("10/")) {
				monthNumber = 9;
			} else if (month .equals("11/")) {
				monthNumber = 10;
			} else if (month .equals("12/")) {
				monthNumber = 11;
			} else {
				throw new IllegalArgumentException("Invalid month input");
			}
			
			return monthNumber;
		}
		
		/**
		 * Method used to take a substring from a complete date and change date to integers for calculations
		 * @param date takes in a date in format "xx/"
		 * @return the date as an integer
		 */
		public int convertStringDate (String date) {
			int newDate = 0;
			
			if (date .equals("01/")) {
				newDate = 1;
			} else if (date .equals("02/")) {
				newDate = 2;
			} else if (date .equals("03/")) {
				newDate = 3;
			} else if (date .equals("04/")) {
				newDate = 4;
			} else if (date .equals("05/")) {
				newDate = 5;
			} else if (date .equals("06/")) {
				newDate = 6;
			} else if (date .equals("07/")) {
				newDate = 7;
			} else if (date .equals("08/")) {
				newDate = 8;
			} else if (date .equals("09/")) {
				newDate = 9;
			} else if (date .equals("10/")) {
				newDate = 10;
			} else if (date .equals("11/")) {
				newDate = 11;
			} else if (date .equals("12/")) {
				newDate = 12;
			} else if (date .equals("13/")) {
				newDate = 13;
			} else if (date .equals("14/")) {
				newDate = 14;
			} else if (date .equals("15/")) {
				newDate = 15;
			} else if (date .equals("16/")) {
				newDate = 16;
			} else if (date .equals("17/")) {
				newDate = 17;
			} else if (date .equals("18/")) {
				newDate = 18;
			} else if (date .equals("19/")) {
				newDate = 19;
			} else if (date .equals("20/")) {
				newDate = 20;
			} else if (date .equals("21/")) {
				newDate = 21;
			} else if (date .equals("22/")) {
				newDate = 22;
			} else if (date .equals("23/")) {
				newDate = 23;
			} else if (date .equals("24/")) {
				newDate = 24;
			} else if (date .equals("25/")) {
				newDate = 25;
			} else if (date .equals("26/")) {
				newDate = 26;
			} else if (date .equals("27/")) {
				newDate = 27;
			} else if (date .equals("28/")) {
				newDate = 28;
			} else if (date .equals("29/")) {
				newDate = 29;
			} else if (date .equals("30/")) {
				newDate = 30;
			} else if (date .equals("31/")) {
				newDate = 31;
			} else {
				throw new IllegalArgumentException("Invalid date input");
			}
			
			return newDate;
		}
	
		/**
		 * Method mainly used for printing and conversions for calculations
		 * @param month takes in the format "xx/"
		 * @return the month as a String like "Jan" - "Dec"
		 */
		public String convertStringMonthTwo (String month) {
			
			String monthNumber;
			
			if (month .equals("01/")) {
				monthNumber = "Jan";
			} else if (month .equals("02/")) {
				monthNumber = "Feb";
			} else if (month .equals("03/")) {
				monthNumber = "Mar";
			} else if (month .equals("04/")) {
				monthNumber = "Apr";
			} else if (month .equals("05/")) {
				monthNumber = "May";
			} else if (month .equals("06/")) {
				monthNumber = "Jun";
			} else if (month .equals("07/")) {
				monthNumber = "Jul";
			} else if (month .equals("08/")) {
				monthNumber = "Aug";
			} else if (month .equals("09/")) {
				monthNumber = "Sep";
			} else if (month .equals("10/")) {
				monthNumber = "Oct";
			} else if (month .equals("11/")) {
				monthNumber = "Nov";
			} else if (month .equals("12/")) {
				monthNumber = "Dec";
			} else {
				throw new IllegalArgumentException("Invalid month input");
			}
			
			return monthNumber;
			
		}
		
		/**
		 * 
		 * @return The string of all the bookings in this room
		 */
		public String printBookings() {
			if (this.id == null) {
				return "";
			} else {
				
				ArrayList<Integer> jan = new ArrayList<Integer>();
				ArrayList<Integer> jan2 = new ArrayList<Integer>();
				
				ArrayList<Integer> feb = new ArrayList<Integer>();
				ArrayList<Integer> feb2 = new ArrayList<Integer>();
				
				ArrayList<Integer> mar = new ArrayList<Integer>();
				ArrayList<Integer> mar2 = new ArrayList<Integer>();
				
				ArrayList<Integer> apr = new ArrayList<Integer>();
				ArrayList<Integer> apr2 = new ArrayList<Integer>();
				
				ArrayList<Integer> may = new ArrayList<Integer>();
				ArrayList<Integer> may2 = new ArrayList<Integer>();
				
				ArrayList<Integer> jun = new ArrayList<Integer>();
				ArrayList<Integer> jun2 = new ArrayList<Integer>();
				
				ArrayList<Integer> jul = new ArrayList<Integer>();
				ArrayList<Integer> jul2 = new ArrayList<Integer>();
				
				ArrayList<Integer> aug = new ArrayList<Integer>();
				ArrayList<Integer> aug2 = new ArrayList<Integer>();
				
				ArrayList<Integer> sep = new ArrayList<Integer>();
				ArrayList<Integer> sep2 = new ArrayList<Integer>();
				
				ArrayList<Integer> oct = new ArrayList<Integer>();
				ArrayList<Integer> oct2 = new ArrayList<Integer>();
				
				ArrayList<Integer> nov = new ArrayList<Integer>();
				ArrayList<Integer> nov2 = new ArrayList<Integer>();
				
				ArrayList<Integer> dec = new ArrayList<Integer>();
				ArrayList<Integer> dec2 = new ArrayList<Integer>();
				
				String allDates = "";
				
				Collection<ArrayList<String>> dates = calendar.values();
				Iterator<ArrayList<String>> check =  dates.iterator();
				
				while (check.hasNext()) {
					ArrayList<String> current = check.next();
					
					int currentSize = current.size();
					String date = current.get(0);
					
					String newDate = date.substring(0, 3);
					String newMonthTwo = date.substring(3, 6);
					
					String convertedMonth = convertStringMonthTwo (newMonthTwo);
					int convertedDate = convertStringDate(newDate);
					
					if (convertedMonth == "Jan") {
						jan.add(convertedDate);
						Collections.sort(jan);
						
						int index = jan.indexOf(convertedDate);
						
						jan2.add(index, currentSize);
					} if (convertedMonth == "Feb") {
						feb.add(convertedDate);
						Collections.sort(feb);
						
						int index = feb.indexOf(convertedDate);
						
						feb2.add(index, currentSize);
					} if (convertedMonth == "Mar") {
						mar.add(convertedDate);
						Collections.sort(mar);
						
						int index = mar.indexOf(convertedDate);
						
						mar2.add(index, currentSize);
					} if (convertedMonth == "Apr") {
						apr.add(convertedDate);
						Collections.sort(apr);
						
						int index = apr.indexOf(convertedDate);
						
						apr2.add(index, currentSize);
					} if (convertedMonth == "May") {
						may.add(convertedDate);
						Collections.sort(may);
						
						int index = may.indexOf(convertedDate);
						
						may2.add(index, currentSize);
					} if (convertedMonth == "Jun") {
						jun.add(convertedDate);
						Collections.sort(jun);
						
						int index = jun.indexOf(convertedDate);
						
						jun2.add(index, currentSize);
					} if (convertedMonth == "Jul") {
						jul.add(convertedDate);
						Collections.sort(jul);
						
						int index = jul.indexOf(convertedDate);
						
						jul2.add(index, currentSize);
					} if (convertedMonth == "Aug") {
						aug.add(convertedDate);
						Collections.sort(aug);
						
						int index = aug.indexOf(convertedDate);
						
						aug2.add(index, currentSize);
					} if (convertedMonth == "Sep") {
						sep.add(convertedDate);
						Collections.sort(sep);
						
						int index = sep.indexOf(convertedDate);
						
						sep2.add(index, currentSize);
					} if (convertedMonth == "Oct") {
						oct.add(convertedDate);
						Collections.sort(oct);
						
						int index = oct.indexOf(convertedDate);
						
						oct2.add(index, currentSize);
					} if (convertedMonth == "Nov") {
						nov.add(convertedDate);
						Collections.sort(nov);
						
						int index = nov.indexOf(convertedDate);
						
						nov2.add(index, currentSize);
					} if (convertedMonth == "Dec") {
						dec.add(convertedDate);
						Collections.sort(dec);
						
						int index = dec.indexOf(convertedDate);
						
						dec2.add(index, currentSize);
					}					
				}
				
				
				allDates = getSortedBookings(jan, jan2, "Jan") + getSortedBookings(feb, feb2, "Feb") + getSortedBookings(mar, mar2, "Mar") + getSortedBookings(apr, apr2, "Apr")
						+ getSortedBookings(may, may2, "May") + getSortedBookings(jun, jun2, "Jun") + getSortedBookings(jul, jul2, "Jul") + getSortedBookings(aug, aug2, "Aug")
						+ getSortedBookings(sep, sep2, "Sep") + getSortedBookings(oct, oct2, "Oct") + getSortedBookings(nov, nov2, "Nov") + getSortedBookings(dec, dec2, "Dec");
				return allDates;
			}
		}
		
		/**
		 * 
		 * @param month an ArrayList of starting dates under the given month in which bookings were made
		 * @param month2 an ArrayList of the number of nights that the room was booked for (the indexes match the dates in @param month)
		 * @param monthName the name of the month that we are looking under
		 * @return A string of bookings for the given month in this room
		 */
		public String getSortedBookings(ArrayList<Integer> month, ArrayList<Integer> month2, String monthName) {
			if (month.size() != 0) {
				String allDates = "";
				for (int i = 0; i != month.size(); i++) {
					int date = month.get(i);
					int numNights = month2.get(i);
					allDates = allDates + " " + monthName  + " " + date + " " + numNights;
				}
				return allDates;
			}
			return "";
		}
		
}
