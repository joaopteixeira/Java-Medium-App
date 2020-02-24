package JV.DB.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public interface DateUtil {
	
	
	static String timeago(String d){
		 String valor = "0";
         int qual=-1;
         String te = "";
        Date date = null;
        Date datenow = Calendar.getInstance().getTime();
        try {
            date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(d);
            long time = datenow.getTime()-date.getTime();

           

            String hh = new SimpleDateFormat("HH").format(time);
            String mm = new SimpleDateFormat("mm").format(time);
            String ss = new SimpleDateFormat("ss").format(time);



            if(hh.compareTo("00")==0){
                if(mm.compareTo("00")==0){
                    qual = 3;
                    valor = ss;
                }else{
                    qual = 2;
                    valor=mm;
                }


            }else{
                qual=1;
                valor=hh;
            }

           

            switch (qual){
                case 1:te="hora";
                    break;
                case 2:te="minutos";
                    break;
                case 3:te="segundos";
                    break;
            }

            if(valor.charAt(0)=='0'){
                return valor.replace(String.valueOf(valor.charAt(0)),"")+" "+te+" atrás";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        


        return valor+" "+te+" atrás";
    }

}
