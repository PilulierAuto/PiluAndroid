package fr.djambae.pilulierautomatique;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
 String Nom ="";
 Fichier Fi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, NotificationService.class);
        intent.putExtra("Suppr",true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        AlarmManager Mm = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        PendingIntent PIntent = PendingIntent.getService(this,0,intent,0);
        long date = System.currentTimeMillis() + 30L;
        Mm.setExact(AlarmManager.RTC_WAKEUP,date,PIntent);

        Fi = new Fichier(this);
        Fi.Chargement();
        if(Fi.Charge) {
            String N = Fi.Lecture('N');
            if (!N.equals("")) Passage(Nom);
            }

    }

    public void sendMessage(View View){
        EditText Texte = (EditText) findViewById(R.id.editText);
        String Message  = Texte.getText().toString();

        if(!Message.equals("Votre Nom") && !Message.equals("")){
            Nom = Message;
            if(Inconue(Message)) {

                Fi.Ecrire(Nom, 'N', false);
                Passage(Nom);
            }else{
                Toast.makeText(this,"Votre nom est inconue",Toast.LENGTH_SHORT).show();
            }
        }else{
            CharSequence text = "Entrez votre nom";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }
    }
    boolean Inconue(String N){
        String[] Noms = getResources().getString(R.string.Nom).split(",");
        for (String T :Noms){
            Log.i("APP",T+"|"+N);
            if(N.equals(T)){
                return true;
            }
        }
        Log.i("APP","False");
        return false;

    }
    public void Efface(View View) {
        EditText Texte = findViewById(R.id.editText);
        String Message  = Texte.getText().toString();

        if (Message.equals("Votre Nom") || Message.equals("") || Message.equals(Nom)) {
            Texte.setText("");
        } else {
            sendMessage(Texte);
        }
    }

    private void Passage(String Nom){
        Intent intent = new Intent(this, Posologie.class);
        intent.putExtra("Nom", Nom);
        startActivity(intent);
    }

    public void  Press (View V){
//       Log.d("TAG" ,Fi.Lecture('N',0));
//        Log.d("TAG" ,Fi.Lecture());
//        TextView Tex = findViewById(R.id.C);
//        Tex.setText(Fi.Lecture('N',0));
        Fi.Suppr();
    }
}
