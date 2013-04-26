package ${test_namespace}.utils;

import java.util.Random;

import org.joda.time.DateTime;

public abstract class TestUtils{

	private static final Random RANDOM = new Random();
	
     /**
      * <p>Creates a random string based on a variety of options, using
      * supplied source of randomness.</p>
      *
      * <p>If start and end are both <code>0</code>, start and end are set
      * to <code>' '</code> and <code>'z'</code>, the ASCII printable
      * characters, will be used, unless letters and numbers are both
      * <code>false</code>, in which case, start and end are set to
      * <code>0</code> and <code>Integer.MAX_VALUE</code>.
      *
      * <p>If set is not <code>null</code>, characters between start and
      * end are chosen.</p>
      *
      * <p>This method accepts a user-supplied {@link Random}
      * instance to use as a source of randomness. By seeding a single 
      * {@link Random} instance with a fixed seed and using it for each call,
      * the same random sequence of strings can be generated repeatedly
      * and predictably.</p>
      *
      * @param count  the length of random string to create
      * @param start  the position in set of chars to start at
      * @param end  the position in set of chars to end before
      * @param letters  only allow letters?
      * @param numbers  only allow numbers?
      * @param chars  the set of chars to choose randoms from.
      *  If <code>null</code>, then it will use the set of all chars.
      * @param random  a source of randomness.
      * @return the random string
      * @throws ArrayIndexOutOfBoundsException if there are not
      *  <code>(end - start) + 1</code> characters in the set array.
      * @throws IllegalArgumentException if <code>count</code> &lt; 0.
      * @since 2.0
      */
     public static String random(int count, int start, int end, boolean letters, boolean numbers,
                                 char[] chars, Random random) {
         if (count == 0) {
             return "";
         } else if (count < 0) {
             throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
         }
         if ((start == 0) && (end == 0)) {
             end = 'z' + 1;
             start = ' ';
             if (!letters && !numbers) {
                 start = 0;
                 end = Integer.MAX_VALUE;
             }
         }
 
         char[] buffer = new char[count];
         int gap = end - start;
 
         while (count-- != 0) {
             char ch;
             if (chars == null) {
                 ch = (char) (random.nextInt(gap) + start);
             } else {
                 ch = chars[random.nextInt(gap) + start];
             }
             if ((letters && Character.isLetter(ch))
                 || (numbers && Character.isDigit(ch))
                 || (!letters && !numbers)) 
             {
                 if (ch >= 56320 && ch <= 57343) {
                     if (count == 0) {
                         count++;
                     } else {
                         // low surrogate, insert high surrogate after putting it in
                         buffer[count] = ch;
                         count--;
                         buffer[count] = (char) (55296 + random.nextInt(128));
                     }
                 } else if (ch >= 55296 && ch <= 56191) {
                     if (count == 0) {
                         count++;
                     } else {
                         // high surrogate, insert low surrogate before putting it in
                         buffer[count] = (char) (56320 + random.nextInt(128));
                         count--;
                         buffer[count] = ch;
                     }
                 } else if (ch >= 56192 && ch <= 56319) {
                     // private high surrogate, no effing clue, so skip it
                     count++;
                 } else {
                     buffer[count] = ch;
                 }
             } else {
                 count++;
             }
         }
         return new String(buffer);
     }
     
     public static String generateRandomString(int length){
    	 return random(length, 0, 0, true, true, null, RANDOM);
     }
     
     public static int generateRandomInt(int min, int max){
    	 return (int)generateRandomDouble(min, max);
     }
     
     public static double generateRandomDouble(double min, double max){
    	 return Math.random()*(max-min)+min;
     }
     
     public static float generateRandomFloat(float min, float max){
    	 return (float)generateRandomDouble(min, max);
     }
     
     public static boolean generateRandomBool(){
    	 return (Math.random()>0.5);
     }
     
     public static DateTime generateRandomDateTime(){
    	 DateTime date = generateRandomDate();
    	 DateTime time = generateRandomTime();
    	 
    	 return new DateTime(
    			 		date.getYear(),
    			 		date.getMonthOfYear(),
    			 		date.getDayOfWeek(),
    			 		time.getHourOfDay(),
    			 		time.getMinuteOfHour());
     }
     
     public static DateTime generateRandomDate(){
    	 int year, month, day;
    	 
    	 year = (int)(Math.random()*200)+1900;
    	 month = (int)(Math.random()*11)+1;
    	 day = (int)(Math.random()*27)+1;
    	 
    	 return new DateTime(year, month, day, 0, 0);
     }
     
     public static DateTime generateRandomTime(){
    	 DateTime dt = new DateTime();
    	 int hours,minutes; 
    	 
    	 hours = (int)(Math.random()*23);
    	 minutes = (int)(Math.random()*59);
    	 
    	 return new DateTime(
		    			 dt.getYear(), 
		    			 dt.getMonthOfYear(),
		    			 dt.getDayOfMonth(), 
		    			 hours, 
		    			 minutes);
     }
}
