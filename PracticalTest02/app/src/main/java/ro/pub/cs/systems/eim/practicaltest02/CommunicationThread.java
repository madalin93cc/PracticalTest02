package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by colez on 16/05/2016.
 */
public class CommunicationThread extends Thread {
    Socket socket;

    public CommunicationThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        if (socket != null) {
            try {
                BufferedReader br = Utilities.getReader(socket);
                PrintWriter pw = Utilities.getWriter(socket);
//                TODO all business
//                POST example
//                HttpClient httpClient = new DefaultHttpClient();
//                HttpPost httpPost = new HttpPost(Constants.WEB_SERVICE_ADDRESS);
//                List<NameValuePair> params = new ArrayList<NameValuePair>();
//                params.add(new BasicNameValuePair(Constants.QUERY_ATTRIBUTE, city));
//                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
//                httpPost.setEntity(urlEncodedFormEntity);
//                ResponseHandler<String> responseHandler = new BasicResponseHandler();
//                String pageSourceCode = httpClient.execute(httpPost, responseHandler);

//                GET example
//                HttpClient httpClient = new DefaultHttpClient();
//                HttpGet httpGet = new HttpGet("http://www.server.com");
//                HttpResponse httpGetResponse = httpClient.execute(httpGet);
//                ResponseHandler<String> responseHandler = new BasicResponseHandler();
//                String content = httpClient.execute(httpGet, responseHandler);
//                sau
//                HttpEntity httpGetEntity = httpGetResponse.getEntity();


            } catch (IOException e) {
                Log.e(Constants.TAG, "An exception has occurred: " + e.getMessage());
            }
        }
    }
}
