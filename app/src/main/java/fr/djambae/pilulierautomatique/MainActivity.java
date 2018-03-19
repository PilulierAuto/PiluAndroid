package fr.djambae.pilulierautomatique;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View View){
        EditText Texte = (EditText) findViewById(R.id.editText);
        String Message  = Texte.getText().toString();

        if(Message!="Votre Nom" && Message.equals!=""){
            Intent intent = new Intent(this, Posologie.class);
            intent.putExtra("Nom", Message);
            startActivity(intent);
        }
    }

    public void Efface(View View) {
        if (Message = "Votre Nom" && Message.equals = "") {
            EditText Texte = (EditText) findViewById(R.id.editText);
            Texte.setText("");
        } else {
            EditText Texte = (EditText) findViewById(R.id.editText);
            sendMessage(Texte);
        }
    }


}
