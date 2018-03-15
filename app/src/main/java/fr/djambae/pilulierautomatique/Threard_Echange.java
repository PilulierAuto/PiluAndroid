package fr.djambae.pilulierautomatique;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


class Threard_Echange extends Thread {
    private final BluetoothSocket Socket;
    private final InputStream InStream;
    private final OutputStream OutStream;
    private byte[] Buffer;
    String TAG;
    Threard_Echange(BluetoothSocket Sckt, UUID UUID, String TAG) {
        Socket = Sckt;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        TAG = this.TAG;

        try {
            tmpIn = Socket.getInputStream();
        }
        catch (IOException e) {
            Log.e(TAG, "Une erreure un survenue lors de la creation de l'entré du flux", e);
        }
        try {
            tmpOut = Socket.getOutputStream();
        }
        catch (IOException e) {
            Log.e(TAG, "Une erreure un survenue lors de la creation de la sortie du flux", e);
        }
        InStream = tmpIn;
        OutStream = tmpOut;
    }
    void Lancer() {
        Buffer = new byte[1024];
        int NbrOct;

        while (true ) {
            try {
                NbrOct = InStream.read(Buffer);
            }
            catch(IOException e) {
                Log.d(TAG, "L'entré du flux était déconnecter", e);
                break;
            }
        }
    }
}
