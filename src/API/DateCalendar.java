package API;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DateCalendar 
{

	/**
	 * Retourne la durée entre une date et celle d'aujourd'hui
	 * 
	 * @param d : date format --> yyyy/MM/dd  HH:mm:ss
	 * @return
	 */
	public int duration_date(String d)  
	{
	    String dateToday=null;
	    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
	    Date date = new Date();
	    dateToday = dateFormat.format(date);//date systeme
	    String NBjour = "";	 
	    long nbDaysFirstDate=0;
	    long nbDaysSecondDate=0;
	 
	 
	    Date d1;
		try {
			d1 = dateFormat.parse(dateToday);
		
	    Date d2=dateFormat.parse(d);
	    long DayInMillisecond=24*60*60*1000;
	    nbDaysFirstDate = d1.getTime()/DayInMillisecond;
	    nbDaysSecondDate = d2.getTime()/DayInMillisecond;
	    NBjour = Long.toString(nbDaysFirstDate-nbDaysSecondDate);
	   
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   return Integer.parseInt(NBjour);     
	}
}
