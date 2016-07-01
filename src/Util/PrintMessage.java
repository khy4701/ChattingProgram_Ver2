package Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PrintMessage {

	public static void printUserAndTimeOrder(String message)
	{
		String name = Thread.currentThread().getName();
        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		
		System.out.println("["+name + f.format(new Date()) + "] :" + message); 
	}
	
}
