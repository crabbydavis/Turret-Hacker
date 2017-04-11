package crabbman.turrethacker;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private Button startButton;
    private Button jumptoFrequencyButton;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private static final String DEVICE_NAME = "HB-01"; //change to the MAC address of the device

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BluetoothTask bluetoothTask = new BluetoothTask();
        bluetoothTask.execute();

        startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SimonActivity.class);
                startActivityForResult(intent, 1);
            }
        });

//        jumptoFrequencyButton = (Button) findViewById(R.id.jump_to_frequency_button);
//        jumptoFrequencyButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, PlayerFrequencyActivity.class);
//                startActivityForResult(intent, 1);
//            }
//        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        int result = data.getIntExtra("frequency", -1);
        Toast.makeText(this, "result: " + result, Toast.LENGTH_SHORT).show();
        sendCommand(result);
        BluetoothTask bluetoothTask = new BluetoothTask();
        bluetoothTask.execute();
    }

    class BluetoothTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... params) {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return bluetoothSetup();
        }

        protected void onPostExecute(String result){
            displayResult(result);
        }
    }

    private void displayResult(String result){
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    private String bluetoothSetup(){
        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            return "Device does not support Bluetooth";
            //Toast.makeText(getApplicationContext(),"Device does not support Bluetooth",Toast.LENGTH_SHORT).show();

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
            return "Please pair the device first";
            //Toast.makeText(getApplicationContext(),"Please pair the device first",Toast.LENGTH_SHORT).show();

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
                return "Could not connect with " + DEVICE_NAME;
                //Toast.makeText(getApplicationContext(), "Could not connect with" + DEVICE_NAME, Toast.LENGTH_SHORT).show();
            }
        };

        if (device == null){
            return "Device not connected";
            //Toast.makeText(this, "Device not connected", Toast.LENGTH_SHORT).show();
        }
        else{
            try {
                socket = device.createRfcommSocketToServiceRecord(device.getUuids()[0].getUuid());
                socket.connect();
            } catch (IOException e) {
                return "Could not open a connection";
                //Toast.makeText(this, "Could not open a connection", Toast.LENGTH_SHORT).show();
                //e.printStackTrace();
            }
        }
        return "Device connected";
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
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
