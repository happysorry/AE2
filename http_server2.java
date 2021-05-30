
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class http_server2 {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(2222), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        System.out.println("server started");
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {

            InputStream is = t.getRequestBody();
            InputStreamReader isReader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isReader);

            // code to read and print headers
            String headerLine = null;
            String result = "";
            while ((headerLine = br.readLine()) != null) {
                // System.out.println(headerLine);

                result += headerLine + "\n";
            }

            // System.out.println(result);//讀取一行字串資料
            System.out.println(t.getRemoteAddress());

            /**
             * dealing with post request
             */
            if ("POST".equals(t.getRequestMethod())) {
                System.out.println("POST");
                do_post(result);
            }
            /**
             * dealing with get request
             */
            if ("GET".equals(t.getRequestMethod())) {
                System.out.println("GET");
            }
            String response = "This is the response";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    /**
     * split out label ,content check whether content value is true or false if
     * false , put it into defected list container
     * 
     * @param result
     */
    public static void do_post(String result) {
        try {
            String[] sp;
            String[] sp1;
            sp = result.split("rn=\"");
            sp1 = sp[1].split("\">");
            String rn = sp1[0];
            System.out.println("rn " + rn);
            send_defect(rn);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void send_defect(String RFID) {
        try {
            String path = "http://192.168.99.100:777/~/mn-cse/mn-name/AE2/Robot_Arm_Status_Container";
            URL url = new URL(path);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setDoOutput(true);
            // http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("X-M2M-Origin", "admin:admin");
            http.setRequestProperty("Content-Type", "application/json;ty=4");
            try {
                http.setRequestMethod("POST");
                http.connect();
                DataOutputStream out = new DataOutputStream(http.getOutputStream());
                String request = "{\"m2m:cin\": {\"con\": \"true\", \"cnf\": \"application/xml\",\"lbl\":\"" + "label"
                        + "\",\"rn\":\"" + RFID + "\"}}";
                // '{"m2m:cin": {"con": "EXAMPLE_VALUE", "cnf": "text/plain:0"}}'
                out.write(request.toString().getBytes("UTF-8"));
                out.flush();
                out.close();
                int satus = http.getResponseCode();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (IOException e) {

        }
    }

}