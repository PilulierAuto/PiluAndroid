package fr.djambae.pilulierautomatique;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
 String Nom ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent Intent;
        Intent = getIntent();
        if(Intent != null){
            Nom = Intent.getStringExtra("Nom");
        }
    }

    public void sendMessage(View View){
        EditText Texte = (EditText) findViewById(R.id.editText);
        String Message  = Texte.getText().toString();

        if(!Message.equals("Votre Nom") && !Message.equals("")){
            Intent intent = new Intent(this, Posologie.class);
            intent.putExtra("Nom", Message);
            startActivity(intent);
            Nom = Message;
        }else{
            CharSequence text = "Entrez votre nom";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }
    }

    public void Efface(View View) {
        EditText Texte = (EditText) findViewById(R.id.editText);
        String Message  = Texte.getText().toString();

        if (Message.equals("Votre Nom") || Message.equals("") || Message.equals(Nom)) {
            Texte.setText("");
        } else {
            sendMessage(Texte);
        }
    }


}
