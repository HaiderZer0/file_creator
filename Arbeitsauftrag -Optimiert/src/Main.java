import java.io.*;
import java.util.ArrayList;

public class Main {
    public static File file;
    public static void main(String[] args) {
        int eingabe;
        boolean stop = true;
        while(stop){
            eingabe = Hauptmenu();
            switch (eingabe) {
                case 1 -> CASE_1();
                case 2 -> CASE_2();
                case 3 -> CASE_3();
                case 4 -> CASE_4();
                case 5 -> stop = false;
                case 6 -> CASE_6();
            }
        }
    }
    public static int Hauptmenu(){

        System.out.println("********** H A U P T M E N Ü **********\n");
        System.out.println("1) NEUE SCHÜLERDATEI ANLEGEN");
        System.out.println("2) VORHANDENE SCHÜLERDATEI FÜLLEN");
        System.out.println("3) INHALT EINER SCHÜLERDATEI ANZEIGEN");
        System.out.println("4) PRO SCHÜLER EINEN UNTERORDNER ERSTELLEN");
        System.out.println("5) PROGRAMM BEENDEN");
        System.out.println("6) EINEN ZUFÄLLIGEN SCHÜLER AUSGEBEN\n");


        System.out.print("Ziffer eingeben: ");
        return Tastatur.intInput();
    }

    //NEUE SCHÜLERDATEI ANLEGEN
    public static void CASE_1 () {
        System.out.print("Geben Sie den Namen der Datei ein: ");
        String name = Tastatur.stringInput();
        file = new File(name + ".txt");
        if(file.exists()){
            System.out.println("Die Datei mit dem gewählten Namen existiert bereits");
        }else{
            try {
                new BufferedWriter(new FileWriter(file));
                System.out.println("Die folgende Datei wurde erstellt !");


            } catch (IOException e) {
                System.out.println("Einen Fehler ist aufgetreten !");
            }
        }
    }

    //VORHANDENE SCHÜLERDATEI FÜLLEN
    public static void CASE_2 () {
        System.out.print("Geben Sie den Namen der Datei ein: ");
        String name = Tastatur.stringInput();
        file = new File(name + ".txt");

        if(!file.exists()){
            System.out.println("Die Datei mit dem gewählten Namen existiert nicht");
        }
        else{
            try{
                BufferedWriter emptyfile = new BufferedWriter(new FileWriter( name + ".txt"));
                String nachname, vorname;
                while(true){
                    System.out.print("nachname (mit 0 aufhören): ");
                    nachname = Tastatur.stringInput();
                    if(nachname.equals("0")){ break;}
                    System.out.print("vorname: ");
                    vorname = Tastatur.stringInput();

                    emptyfile.write(nachname + " ," + vorname);
                    emptyfile.newLine();
                }
                emptyfile.close();
            }
            catch (IOException e) {
                System.out.println("Einen Fehler ist aufgetreten !");
            }
            System.out.println("Die gegebene Daten wurden gespeichert ! ");
        }
    }

    //INHALT EINER SCHÜLERDATEI ANZEIGEN
    public static void CASE_3 () {
        System.out.print("Geben Sie den Namen der Datei ein: ");
        String name = Tastatur.stringInput();
        file = new File(name + ".txt");

        if(!file.exists()){
            System.out.println("Die Datei mit dem gewählten Namen existiert nicht");
        }
        else{
            try{
                BufferedReader br = new BufferedReader(new FileReader(name + ".txt"));
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
                br.close();
            }
            catch (IOException e) {
                System.out.println("Einen Fehler ist aufgetreten !");
            }
        }
    }

    //PRO SCHÜLER EINEN UNTERORDNER ERSTELLEN
    public static void CASE_4 () {
        System.out.print("Geben Sie den Namen der Datei ein: ");
        String name = Tastatur.stringInput();
        file = new File(name + ".txt");

        if(!file.exists()){
            System.out.println("Die Datei mit dem gewählten Namen existiert nicht");
        }
        else{
            try {
                BufferedReader br = new BufferedReader(new FileReader(name + ".txt"));
                String line;
                while ((line = br.readLine()) != null) {
                    File ordner = new File(line.replace(" ,",""));
                    ordner.mkdir();
                }
                br.close();
                System.out.println("Alle Ordner wurden erstellt ! ");
            }
            catch (IOException e) {
                System.out.println("Einen Fehler ist aufgetreten !");
            }
        }
    }

    //EINEN ZUFÄLLIGEN SCHÜLER AUSGEBEN
    public static void CASE_6 () {
        ArrayList <String> namen = new ArrayList<>();
        ArrayList <Integer> anzahl = new ArrayList<>();

        //Eingabe des Dateinamens.
        System.out.print("Geben Sie den Namen der Datei ein: ");
        String name = Tastatur.stringInput();

        //Überprüfung der Existenz der Datei.
        file = new File(name + ".txt");
        if(!file.exists()){
            System.out.println("Die Datei mit dem gewählten Namen existiert nicht");
        }
        else{
            try{
                //Datei lesen
                BufferedReader br = new BufferedReader(new FileReader(name + ".txt"));
                String line;


                //Daten der Datei bzw. alle Namen in einem Array speichern.
                while ((line = br.readLine()) != null) {
                    namen.add(line.replace(" ,"," "));
                }
                br.close();


                //Überprüfung der Existenz der anderen Datei mit den bereits gespeicherten Daten.
                file = new File("Data" + name + ".txt");
                if(!file.exists()){

                    //Erzeugung der neuen Datei für Datenspeicherung, falls eine nicht existiert.
                    BufferedWriter emptyfile = new BufferedWriter(new FileWriter(file));
                    for(int i = 0; i < namen.size(); i++){
                        emptyfile.write("0");
                        emptyfile.newLine();
                    }
                    emptyfile.close();
                }


                /*Die Datei mit gespeicherten Daten lesen und Informationen in einem Array speichern
                  Anzahl enthält, wie viel mal ein Benutzer schon ausgewählt wurde. */
                br = new BufferedReader(new FileReader("Data" + name + ".txt"));
                while ((line = br.readLine()) != null) {
                    anzahl.add(Integer.parseInt(line));
                }


                //User, die bereits 2 Mal im Vergleich zu anderen Benutzern gewählt wurden, werden gesperrt.
                int randomPerson;
                int delta = 2;
                boolean run = true;

                do {
                    randomPerson = (int) (Math.random() * (namen.size()));
                    System.out.println(randomPerson + 1);
                    for (int i = 0; i < anzahl.size(); i++) {
                        //Verhinderung der Deltaberechnung mit der Zahl selbst.
                        if (i != randomPerson) {delta = anzahl.get(randomPerson) - anzahl.get(i);}
                        if (delta < 2) {
                            run = false;
                            break;
                        }
                    }
                }while (run);
                System.out.println("Zufällige Person ======>  " + namen.get(randomPerson));


                //Die Änderung der Daten im Array speichern.
                if(anzahl.size() != 0){
                    anzahl.add(randomPerson, anzahl.get(randomPerson) + 1);
                    anzahl.remove(randomPerson + 1);
                }


                //Die Änderung der Daten in der Datei schreiben.
                BufferedWriter bw = new BufferedWriter(new FileWriter("Data" + name + ".txt"));
                for (Integer integer : anzahl) {
                    bw.append(integer.toString());
                    bw.newLine();
                }


                bw.close();
                namen.clear();
                anzahl.clear();

            }
            catch (IOException e){
                System.out.println("Einen Fehler ist aufgetreten !");
            }
        }
    }
}