package fr.djambae.pilulierautomatique;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


public class Bluetooth extends AppCompatActivity {
    public static final String TAG = "App";
    public static final String Adresse = "20:14:12:03:07:82";
    private UUID UUId;
    Boolean Co = false;
    String Nom;
    Threard_Echange EchangeTh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BluetoothDevice Hc;
        BluetoothAdapter Blue_Adapte = BluetoothAdapter.getDefaultAdapter(); // Objet support Bluetooth
        Set<BluetoothDevice> AppareilAssoci = Blue_Adapte.getBondedDevices(); //Objet de stokage de la liste des pareille connecté
        Thread_Connection ConnectionTh;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        Context context = getApplicationContext();
        Intent Int = getIntent();
        UUId = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        if (Blue_Adapte.isDiscovering()) {
            Blue_Adapte.cancelDiscovery();
        }
        Nom = Int.getStringExtra("Nom");

        Verif_Blue(Blue_Adapte,this );
        Activation(Blue_Adapte,this);
        Toast(this,"Connexion au pilulier" );

        if(AppareilAssoci.size() > 0 ){
            for ( BluetoothDevice Appareil : AppareilAssoci) { //Recherche du serveur bluetooth

                if (Appareil.getName().equals("HC-06")) {
                    Hc = Appareil;
                    break;
                }
            }
            Log.d(TAG,"Création du Thread de Co");
            ConnectionTh = new Thread_Connection(Hc);
            Toast(this,"Connecté !!!");
        }else{
            Log.d(TAG,"Aucun appareil associer !");
        }

        EchangeTh.Ecriture("Bonjour");
    }

    void Verif_Blue(BluetoothAdapter Bl, Context context ){
        if (Bl == null) {// Verification de la présence du bluetooth
            CharSequence text = "Il n'y a pas bluetooth";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }
    void Activation(BluetoothAdapter Bl,Context context){
        if (!Bl.isEnabled()) {
        CharSequence text = "Activer le Bluetooth !";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, 0);
    }
}
    void Connection(BluetoothSocket Blue_Socket) {
        EchangeTh = new Threard_Echange(Blue_Socket);
    }

    void Toast(Context context,String St){
        CharSequence text = St;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private class Thread_Connection extends Thread {
        private final BluetoothSocket Socket;
        private final BluetoothDevice Appa;

        Thread_Connection(BluetoothDevice Appareil) {

            BluetoothSocket tmp = null;
            Appa = Appareil;
            try {
                Log.d(TAG,"Teantative de co !");
                tmp = Appareil.createRfcommSocketToServiceRecord(UUId);
            }
            catch(IOException e) {
                Log.e(TAG, "Erreur lors de creation du Socket", e);
            }
            Socket = tmp;
            run();
        }

        public void run () {
            Log.d(TAG,"RUN !");
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

        void Annuler() {
            try {
                Socket.close();
            }
            catch(IOException e) {
                Log.e(TAG, "On ne peut fermer le client Socket", e);
            }
        }
    }

    private class Threard_Echange extends Thread {
        private final BluetoothSocket Socket;
        private final InputStream InStream;
        private final OutputStream OutStream;
        private byte[] Buffer;

        Threard_Echange(BluetoothSocket Sckt) {
            Socket = Sckt;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

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

        void Ecriture(String Msg) {
            byte[] Oct = new byte[Msg.length()];
            for(int i = 0; i<Oct.length;i++){
                Oct[i] = byte(Msg.charAt(i));
            }
            try {
                OutStream.write();
            }
            catch(IOException e) {
                Log.e(TAG, " Une errueur est surbenue lors de l'envoie du message", e);
            }
        }
        void Annuler() {
            try {
                Socket.close();
            }
            catch(IOException e) {
                Log.e(TAG, "On ne peut fermer le client Socket");
            }
        }
    }
}