
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class AE2{
    public static void main(String[]args){
        String mn = discovery_mn();
        String mn1 = "http://127.0.0.1:666/~/mn-cse/mn-name/AE1";
        create_AE(mn,"AE2","AE2");
        mn += "/mn-name/AE2";
        create_container(mn,"Robot_Arm_Status_Container");
        create_container(mn,"Control_Command_Container");
        create_container(mn1,"defected_list_container");
        sub_ae(mn1,"defected_list_container");
        // create_container(mn1,"Defected_Product_list");
        sub_ae(mn,"Robot_Arm_Status_Container");
    }

    public void AE2(){

    }
    /**
     * find mn2 ip nad port
     */
    public static String discovery_mn(){
        String mn = "";
        mn = "http://140.116.247.73:8282/~/mn-cse";
        return mn;
    }

    public static void create_AE(String target, String ae_name, String poa) {
        URL url;
        try {
            url = new URL(target);
            HttpURLConnection http;
            try {
                http = (HttpURLConnection) url.openConnection();
                http.setDoOutput(true);
                http.setRequestProperty("X-M2M-Origin", "admin:admin");
                http.setRequestProperty("Content-Type", "application/json;ty=2");
                http.setRequestMethod("POST");
                http.connect();
                DataOutputStream out = new DataOutputStream(http.getOutputStream());
                
                

                //'{"m2m:ae": {"rn": "EXAMPLE_APP_NAME", "api": "placeholder", "rr": "TRUE"}}'
                
                String request = "{\"m2m:ae\": {\"rn\": \""+ae_name+"\", \"api\": \""+ae_name+"\", \"rr\": \"FALSE\",\"poa\": \""+poa+"\"}}";
                out.write(request.toString().getBytes("UTF-8")); 
                out.flush();
                out.close();
                System.out.println(http.getResponseCode());
                 
                

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    public static void create_container(String target,String cnt_name){
        URL url;
        try {
            url = new URL(target);
            HttpURLConnection http;
            try {
                http = (HttpURLConnection) url.openConnection();
                http.setDoOutput(true);
                http.setRequestProperty("X-M2M-Origin", "admin:admin");
                http.setRequestProperty("Content-Type", "application/json;ty=3");
                http.setRequestMethod("POST");
                http.connect();
                DataOutputStream out = new DataOutputStream(http.getOutputStream());
                

                
                //'{"m2m:cnt": {"rn": "EXAMPLE_CONTAINER_NAME"}}'
                String request = "{\"m2m:cnt\": {\"rn\": \""+cnt_name+"\", \"rr\": \"FALSE\"}}";
                out.write(request.toString().getBytes("UTF-8")); 
                out.flush();
                out.close();
                System.out.println(http.getResponseCode());
                 
                

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    
    }


    public static String sub_ae(String target , String cnt_name){
        try {
            target = target + "/" + cnt_name;
            System.out.println(target);
            URL url = new URL(target);
            try {
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setDoOutput(true);
                http.setRequestProperty("X-M2M-Origin", "admin:admin");
                http.setRequestProperty("Content-Type", "application/xml;ty=23");
                http.setRequestMethod("POST");
                http.connect();
                DataOutputStream out = new DataOutputStream(http.getOutputStream());
                /**
                 * <m2m:sub xmlns:m2m="http://www.onem2m.org/xml/protocols">
                        <nu>mn-name/test_ipe</nu>
                        <nct>2</nct>
                    </m2m:sub>
                 * 
                 */
                //   sub string { "m2m:sub": {"rn": "sub3","nu": "Clight_ae1","nct": 2 }}

                // String request = "{\"m2m:sub\": {\"rn\":\"sub\",\"nu\":\"http://mnae1:4444\"}}";
                // String request = "{ \"m2m:sub\": {\"rn\": \"sub\",\"nu\": \"http://127.0.0.1:8000\",\"nct\": 2 }}";
                String request = "<m2m:sub xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\"SUB\"><nu>http://192.168.99.101:2222/test</nu><nct>2</nct></m2m:sub>";
                out.write(request.toString().getBytes("UTF-8")); 
                out.flush();
                out.close();
                System.out.println(http.getResponseCode());
                System.out.println(http.getErrorStream());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
        
    }
}