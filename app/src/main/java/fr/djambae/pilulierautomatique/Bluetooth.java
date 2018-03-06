package fr.djambae.pilulierautomatique;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import java.util.UUID;


public class Bluetooth extends AppCompatActivity {
    public static final String TAG = "App";
    private UUID UUID;
    String Nom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        Context context = getApplicationContext();
        Intent Int = getIntent();
        Nom = Int.getStringExtra("Nom");
        BluetoothAdapter Blue_Adapte = BluetoothAdapter.getDefaultAdapter(); // Objet support Bluetooth
        //Set<BluetoothDevice> AppareilAssoci = Blue_Adapte.getBondedDevices(); //Objet de stokage de la liste des pareille connecté

        if (Blue_Adapte == null) {// Verification de la présence du bluetooth

            CharSequence text = "Il n'y a pas bluetooth";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        } else {
            if(!Blue_Adapte.isEnabled()){
                CharSequence text = "Activer le Bluetooth !";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 0);

            }
        }
    }
}

