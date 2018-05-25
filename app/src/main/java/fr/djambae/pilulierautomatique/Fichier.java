package fr.djambae.pilulierautomatique;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class Fichier {
    FileInputStream In;
    FileOutputStream Out;
    Context Ctx;
    boolean Charge = false;
    private static String TAG  = "Fichier";
    Fichier (Context C){
        Ctx = C;
        TAG += hashCode();
    }

    public void Chargement(){
        if (Ds_List()){
            Fichier();
            Charge = true;
        }else {
            Cr_Fichier();
            Charge = false;
        }

    }
    public boolean Ecrire(String St, Character ID,boolean Efface){
        Log.i(TAG,"Ecriure");
        boolean Raj = true;
        if(!Efface) {
            String Prece = Lecture(),ChTest = "0";
            int i = 0;
            while (!ChTest.equals("")) {
                Raj = true;
                ChTest = Lecture(ID,i);
                i++;
                if(ChTest.equals("")) {
                    Raj = false;
                }
            }
            if(!Raj){
                return false;
            }
            St = Prece + ID + St + ";";

        }else{
            St =ID + St + ";";
        }
        Log.i(TAG,"ST : "+St);
        try {
            In = Ctx.openFileInput("Fichier");
            Out = Ctx.openFileOutput("Fichier",Ctx.MODE_PRIVATE);
            int Fin = In.available()-1;
            if (Fin<0)Fin = 0;
            byte[] Buff;
            Buff = St.getBytes();
            Out.write(Buff, Fin, Buff.length);
            Log.i(TAG,St +" A été enregistré en byte soit : "+Buff);
            return true;
           }catch (Exception e ){
               e.printStackTrace();
               return false;
           }
    }
    public  String Lecture(){
        Log.i(TAG,"Une lecture de fichier a été demandé");
        try {
            In = Ctx.openFileInput("Fichier");
        byte[] Buff  = new byte[In.available()];
        In.read(Buff);
            Log.i(TAG,new String(Buff));
        return  new String(Buff);
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
//    private String Lecture(String St ,int Index,int Taille){
//        String St_ = "";
//        for(int i = Index;i-Index<Taille;i++){
//            St_+=St.charAt(i);
//        }
//        Log.d(TAG, "Lecture ID "+St_);
//        return St_;
//    }

    public String Lecture (char ID,int Index){
        try {
            In = Ctx.openFileInput("Fichier");
            byte[] Buff = new byte[In.available()];
            In.read(Buff);
            int Temp=0;
            String St;
            St = new String(Buff);
            String St_ = "";
            boolean Eng = false;
            for (int i = 0; i < St.length(); i++) {
                if (';' ==St.charAt(i)) {
                    Eng = false;
                }
                if (Eng ) {
                    St_ += St.charAt(i);
                } else if (ID == St.charAt(i)) {
                        if(Index == Temp) {
                            Eng = true;
                        }
                       Temp++;
                    }
                }

            Log.i(TAG,St_+" A été lis de "+St);
            return St_;
        }catch (Exception e ){
            e.printStackTrace();
            return "";
        }
    }
    public String Lecture (char ID){
        Log.i(TAG,"Lecture");
        try {
            In = Ctx.openFileInput("Fichier");
            byte[] Buff = new byte[In.available()];
            In.read(Buff);
            String St;
            St = new String(Buff);
            String St_= "";
            boolean Eng = false;
            for (int i = 0;i<St.length();i++){
                if (Eng){
                    St_+=St.charAt(i);
                }else{
                    if(ID == St.charAt(i)){
                    Eng = true;
                    }
                }
                if (i+1<St.length()){
                        if (";".equals(St.charAt(i+1))){
                    Eng = false;
                }
                }
            }
            Log.i(TAG,St_+" A été lis de "+St);
            return St_;
        }catch (Exception e ){
            e.printStackTrace();
            return "";
        }
    }
    public void Close() {
        try{
        In.close();
        Out.close();
    }catch (Exception e){
            e.printStackTrace();
            Log.i("APP","Les flux n'ont pas put etre fermer");
        }

    }
    private boolean Ds_List(){
        String[] Liste = Ctx.fileList();
        for(String L:Liste) {
            Log.i(TAG, "Nom de fichier : "+L);
            if (L.equals("Fichier")) {
                return true;
            }
        }
        return false;
    }
    private void Cr_Fichier(){
        Log.i(TAG,"Cr fichier");
        try {
            FileOutputStream Out = Ctx.openFileOutput("Fichier",Ctx.MODE_PRIVATE);
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "Le fichier n'a pas pue etre crée");
        }
    }
    private  void Fichier(){
        Log.i(TAG,"Fichier ?");
        try{
            FileInputStream In = Ctx.openFileInput("Fichier");
            }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  void Suppr(){
        try{
            File Fich = new File(Ctx.getFilesDir(),"Fichier");
            Fich.delete();
        }catch (Exception e ){
            e.printStackTrace();
        }

    }
}
