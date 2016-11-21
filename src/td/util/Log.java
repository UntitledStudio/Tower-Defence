package td.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/y HH:mm:ss");
    //private static final List<String> total_output = new ArrayList<>();
    
    public static void info(String output) {
        String formatted = "[" + DATE_FORMAT.format(new Date()) + "] " + output;
        System.out.println(formatted);
    }
    
    public static void error(String output) {
        String formatted = "[" + DATE_FORMAT.format(new Date()) + "] " + output;
        System.err.println(formatted);
    }
    
    /*public static String getOutput() {
        String s = "";
        s = total_output.stream().map((ss) -> ss + "\n").reduce(s, String::concat);
        return s;
    }*/
    
    public static SimpleDateFormat getDateFormat() {
        return DATE_FORMAT;
    }
}
