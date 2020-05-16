package ro.pub.cs.systems.eim.PracticalTestV2.network;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import ro.pub.cs.systems.eim.PracticalTestV2.general.Constants;
import ro.pub.cs.systems.eim.PracticalTestV2.general.Utilities;

public class ClientThread extends Thread {

    private String address;
    private int port;
    private String ip;
    private TextView informationTextView;

    private Socket socket;

    public ClientThread(String address, int port, String ip, TextView informationTextView) {
        this.address = address;
        this.port = port;
        this.ip = ip;
        this.informationTextView = informationTextView;
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
