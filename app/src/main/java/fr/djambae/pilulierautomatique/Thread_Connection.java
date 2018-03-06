package fr.djambae.pilulierautomatique;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;


class Thread_Connection extends Thread {
    String TAG;
    private final BluetoothSocket Socket;
    private final BluetoothDevice Appa;
    BluetoothAdapter Blue_Adapte = BluetoothAdapter.getDefaultAdapter(); // Objet support Bluetooth
    Set<BluetoothDevice> AppareilAssoci = Blue_Adapte.getBondedDevices(); //Objet de stokage de la liste des pareille connecté


    Thread_Connection(BluetoothDevice Appareil,UUID UUID, String TAG) {
        TAG = this.TAG;
        BluetoothSocket tmp = null;
        Appa = Appareil;
        try {
            tmp = Appa.createRfcommSocketToServiceRecord(UUID);
        }
        catch(IOException e) {
            Log.e(TAG, "Erreur lors de creation du Socket", e);
        }
        Socket = tmp;
    }

    void Lancer() {
        Blue_Adapte.cancelDiscovery();
        try {
            Socket.connect();
        }
        catch(IOException connectExecption) {
            try {
                Socket.close();
            }
            catch(IOException closeException) {
                Log.e(TAG, "On ne peut fermer le client Socket", closeException);
            }
            return;
        }
        Connection(Socket);
    }

    private void Connection(BluetoothSocket socket) {
    }

    void Annuler() {
        try {
            Socket.close();
        }
        catch(IOException e) {
            Log.e(TAG, "On ne peut fermer le client Socket", e);
        }
    }
}
