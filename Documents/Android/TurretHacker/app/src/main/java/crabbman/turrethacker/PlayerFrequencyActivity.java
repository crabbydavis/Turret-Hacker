package crabbman.turrethacker;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Set;
import java.util.UUID;

/**
 * Created by crabbydavis on 3/28/17.
 */

public class PlayerFrequencyActivity extends AppCompatActivity {

    private Button playerFrequencyButton0;
    private Button playerFrequencyButton1;
    private Button playerFrequencyButton2;
    private Button playerFrequencyButton3;
    private Button playerFrequencyButton4;
    private Button playerFrequencyButton5;
    private Button playerFrequencyButton6;
    private Button playerFrequencyButton7;
    private Button playerFrequencyButton8;
    private Button playerFrequencyButton9;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private static final String DEVICE_NAME = "HB-01"; //change to the MAC address of the device
    public static int selectedNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_frequency);

        //bluetoothSetup();
        buttonSetup();
    }

    private void bluetoothSetup(){
        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {

            Toast.makeText(getApplicationContext(),"Device does not support Bluetooth",Toast.LENGTH_SHORT).show();

        }
        else{
            if(!bluetoothAdapter.isEnabled())

            {

                Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

                startActivityForResult(enableAdapter, 0);

            }
        }

        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

        if(bondedDevices.isEmpty()) {

            Toast.makeText(getApplicationContext(),"Please pair the device first",Toast.LENGTH_SHORT).show();

        } else {
            boolean found = false;
            for (BluetoothDevice iterator : bondedDevices) {

                if(iterator.getName().equals(DEVICE_NAME)) //Replace with iterator.getName() if comparing Device names.

                {

                    device=iterator; //device is an object of type BluetoothDevice

                    found=true;

                    break;

                }
            }
            if (!found){
                Toast.makeText(getApplicationContext(), "Could not connect with" + DEVICE_NAME, Toast.LENGTH_SHORT).show();
            }
        };

        if (device == null){
            Toast.makeText(this, "Device not connected", Toast.LENGTH_SHORT).show();
        }
        else{
            try {
                socket = device.createRfcommSocketToServiceRecord(device.getUuids()[0].getUuid());
                socket.connect();
            } catch (IOException e) {
                Toast.makeText(this, "Could not open a connection", Toast.LENGTH_SHORT).show();
                //e.printStackTrace();
            }
        }
    }

    private void buttonSetup(){
        playerFrequencyButton0 = (Button) findViewById(R.id.player_frequency_button_0);
        playerFrequencyButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(0);
            }
        });

        playerFrequencyButton1 = (Button) findViewById(R.id.player_frequency_button_1);
        playerFrequencyButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(1);
            }
        });

        playerFrequencyButton2 = (Button) findViewById(R.id.player_frequency_button_2);
        playerFrequencyButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(2);
            }
        });

        playerFrequencyButton3 = (Button) findViewById(R.id.player_frequency_button_3);
        playerFrequencyButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(3);
            }
        });

        playerFrequencyButton4 = (Button) findViewById(R.id.player_frequency_button_4);
        playerFrequencyButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(4);
            }
        });

        playerFrequencyButton5 = (Button) findViewById(R.id.player_frequency_button_5);
        playerFrequencyButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(5);
            }
        });

        playerFrequencyButton6 = (Button) findViewById(R.id.player_frequency_button_6);
        playerFrequencyButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(6);
            }
        });

        playerFrequencyButton7 = (Button) findViewById(R.id.player_frequency_button_7);
        playerFrequencyButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(7);
            }
        });

        playerFrequencyButton8 = (Button) findViewById(R.id.player_frequency_button_8);
        playerFrequencyButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(8);
            }
        });

        playerFrequencyButton9 = (Button) findViewById(R.id.player_frequency_button_9);
        playerFrequencyButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(9);
            }
        });
    }

    private void onButtonClick(int playerNumber){
        //sendCommand(playerNumber);
//        Intent intent = new Intent();
//        intent.putExtra("frequency", playerNumber);
//        setResult(1, intent);
        startMainActivity(playerNumber);
    }

    private void sendCommand(int playerNumber){
        if (device == null){
            Toast.makeText(this, "Device not connected", Toast.LENGTH_SHORT).show();
        }
        else{
            String playerCommand = "";
            switch (playerNumber){ //switch to determine the specific command
                case 0:
                    playerCommand = "0";
                    break;
                case 1:
                    playerCommand = "1";
                    break;
                case 2:
                    playerCommand = "2";
                    break;
                case 3:
                    playerCommand = "3";
                    break;
                case 4:
                    playerCommand = "4";
                    break;
                case 5:
                    playerCommand = "5";
                    break;
                case 6:
                    playerCommand = "6";
                    break;
                case 7:
                    playerCommand = "7";
                    break;
                case 8:
                    playerCommand = "8";
                    break;
                case 9:
                    playerCommand = "9";
                    break;
            }

            try {
                OutputStream outputStream = socket.getOutputStream(); //open an output stream
                outputStream.write((playerCommand).getBytes()); //write the command to the output stream
                //outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void startMainActivity(int result){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("frequency",result);
        setResult(1, intent);
        selectedNumber = result;
        finish();
        //startActivity(intent);
        //PlayerFrequencyActivity.onBackPress()
    }


}