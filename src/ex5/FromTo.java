package ex5;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Class that stores 2 string that represents duration
 * @author netanel & guy
 *
 */
public class FromTo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String from;
	private String to;
	
	public FromTo(String from, String to) {
		this.from = from;
		this.to = to;
	}

	/**
	 * Calculates how many days are between 2 dates
	 * @return days between 2 dates
	 * @throws ParseException
	 */
	public int dateDiff() throws ParseException {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
	    Date firstDate = sdf.parse(from);
	    Date secondDate = sdf.parse(to);

	    long diffInMillies = secondDate.getTime() - firstDate.getTime();

	    return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * Calculates how many minutes are between 2 times
	 * @return minutes between 2 times
	 * @throws ParseException
	 */
	public int timeDiff() throws ParseException {
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
	    Date firstDate = sdf.parse(from);
	    Date secondDate = sdf.parse(to);

	    long diffInMillies = secondDate.getTime() - firstDate.getTime();

	    return (int) TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
	
	
}
