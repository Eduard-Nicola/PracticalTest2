package ro.pub.cs.systems.eim.PracticalTestV2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ro.pub.cs.systems.eim.PracticalTestV2.general.Constants;
import ro.pub.cs.systems.eim.PracticalTestV2.network.ClientThread;
import ro.pub.cs.systems.eim.PracticalTestV2.network.ServerThread;

public class PracticalTestV2MainActivity extends AppCompatActivity {

    private EditText serverPortEditText;
    private Button connectButton;

    private EditText clientAddressEditText;
    private EditText clientPortEditText;
    private EditText clientIpEditText;
    private Button getInformationButton;
    private TextView informationTextView;

    private ServerThread serverThread = null;
    private ClientThread clientThread = null;

    private ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();
    private class ConnectButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String serverPort = serverPortEditText.getText().toString();
            if (serverPort == null || serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            serverThread = new ServerThread(Integer.parseInt(serverPort));
            if (serverThread.getServerSocket() == null) {
                Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not create server thread!");
                return;
            }
            serverThread.start();
        }

    }

    private GetInformationButtonClickListener getInformationButtonClickListener = new GetInformationButtonClickListener();
    private class GetInformationButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String clientAddress = clientAddressEditText.getText().toString();
            String clientPort = clientPortEditText.getText().toString();
            if (clientAddress == null || clientAddress.isEmpty()
                    || clientPort == null || clientPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Client connection parameters should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (serverThread == null || !serverThread.isAlive()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                return;
            }
            String ip = clientIpEditText.getText().toString();
            if (ip == null || ip.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Parameters from client (city / information type) should be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            informationTextView.setText(Constants.EMPTY_STRING);

            clientThread = new ClientThread(
                    clientAddress, Integer.parseInt(clientPort), ip, informationTextView
            );
            clientThread.start();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practicaltestv2_main);

        serverPortEditText = findViewById(R.id.server_port_edit_text);
        connectButton = findViewById(R.id.connect_button);
        clientAddressEditText = findViewById(R.id.client_address_edit_text);
        clientPortEditText = findViewById(R.id.client_port_edit_text);
        clientIpEditText = findViewById(R.id.client_ip_edit_text);
        getInformationButton = findViewById(R.id.get_information_button);
        informationTextView = findViewById(R.id.information_text_view);

        connectButton.setOnClickListener(connectButtonClickListener);
        getInformationButton.setOnClickListener(getInformationButtonClickListener);
    }

    @Override
    protected void onDestroy() {
        Log.i(Constants.TAG, "[MAIN ACTIVITY] onDestroy() callback method has been invoked");
        if (serverThread != null) {
            serverThread.stopThread();
        }
        super.onDestroy();
    }
}
