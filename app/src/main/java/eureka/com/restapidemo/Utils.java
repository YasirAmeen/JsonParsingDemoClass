package eureka.com.restapidemo;

import java.io.UnsupportedEncodingException;

/**
 * Created by hp on 7/22/2017.
 */

public class Utils {

    public static String getResponse(byte[] responseBody) {

        String str = null;
        String jresponse = null;
        try {


            if(responseBody == null) {

                return "null";
            }

            str = new String(responseBody,"UTF-8");
            jresponse = str.replaceAll("\\\\", "");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (NullPointerException ex) {

            //Toast.makeText(context, "Internet connection is required", Toast.LENGTH_SHORT).show();
        }

        return jresponse;

    }

}
