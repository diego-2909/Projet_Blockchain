package utils;

import java.time.LocalDateTime;

public class Date {
	int jour;
	int mois;
	int an;
	int heure;
	int min;
	int sec;
	
	public Date() {
		super();
		this.jour = LocalDateTime.now().getDayOfMonth();;
		this.mois = LocalDateTime.now().getDayOfMonth();
		this.an = LocalDateTime.now().getYear();
		this.heure = LocalDateTime.now().getHour();
		this.min = LocalDateTime.now().getMinute();
		this.sec = LocalDateTime.now().getSecond();
	}

	@Override
	public String toString() {
		return  jour + "/" + mois + "/" + an + "," + heure + ":" + min + ":"
				+ sec;
	}
	
	
	
	

}