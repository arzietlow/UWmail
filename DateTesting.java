import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;

public class DateTesting {
  public static void main(String[] args) throws ParseException {
    Date date1;
    DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
    DateFormat df2 = new SimpleDateFormat("MMM dd");
  //  dateStr = "Wed, 12 Sep 2015 08:22:32 -0500";
    String param = "Fri, 18 Sep 2015 10:22:51 -0500";
    try {
     date1 = df.parse(param);
     Date date = date1;
     System.out.println(date.toString());
     System.out.println(date);
    } catch (ParseException e) {
      System.out.println("Caught an error parsing the date!");
    }
  } 
}