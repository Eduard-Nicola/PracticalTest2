package ro.pub.cs.systems.eim.PracticalTestV2.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import ro.pub.cs.systems.eim.PracticalTestV2.general.Constants;
import ro.pub.cs.systems.eim.PracticalTestV2.general.Utilities;
import ro.pub.cs.systems.eim.PracticalTestV2.model.Information;

public class ClientThread extends Thread {

    private String address;
    private int port;
    private String ip;
    private TextView informationTextView;
    private ImageView imageView;

    private Socket socket;

    public ClientThread(String address, int port, String ip, TextView informationTextView, ImageView imageView) {
        this.address = address;
        this.port = port;
        this.ip = ip;
        this.informationTextView = informationTextView;
        this.imageView = imageView;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            if (socket == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
                return;
            }
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader == null || printWriter == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Buffered Reader / Print Writer are null!");
                return;
            }
            printWriter.println(ip);
            printWriter.flush();
            String information;
            while ((information = bufferedReader.readLine()) != null) {
                final String finalizedWeateherInformation = information;
                informationTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        informationTextView.setText(finalizedWeateherInformation);
                    }
                });
                String imageUrl = information.substring(information.lastIndexOf(",") + 2);
                Log.e(Constants.TAG, "Image URL is " + imageUrl);
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGetImage = new HttpGet(imageUrl);
                    HttpResponse httpResponse = httpClient.execute(httpGetImage);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    final Bitmap bitmap = BitmapFactory.decodeStream(httpEntity.getContent());
                    if (bitmap != null) {
                        imageView.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                } catch (Exception exception) {
                    Log.i(Constants.TAG, exception.getMessage());
                    if (Constants.DEBUG) {
                        exception.printStackTrace();
                    }
                }
            }
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
                    if (Constants.DEBUG) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }

}
