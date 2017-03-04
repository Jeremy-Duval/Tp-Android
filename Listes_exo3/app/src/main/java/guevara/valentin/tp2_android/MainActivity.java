package guevara.valentin.tp2_android;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ListView musique;
    private ArrayList<HashMap<String,String>> listItem;
    Button b_contact_adding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_contact_adding = (Button) findViewById(R.id.button);

        //fetch the listView created in the XML file Main
        musique = (ListView) findViewById(R.id.musique);

        //HashMap with all elements (Image,Description...) inside the list
        listItem = new ArrayList<>();

        //We declare an Hashmap which is equivalent to one element
        HashMap<String,String> element = new HashMap<>();

        //We have to make these operations a lot of times for creating another element
        element = new HashMap<>();
        element.put("titre", "Membre du G2S4 et développeur de choc !");
        element.put("description", "Valentin Guevara");
        element.put("image", String.valueOf(R.mipmap.ic_default_profile));
        listItem.add(element);

        element = new HashMap<>();
        element.put("titre", "Membre du G2S4 et développeur de choc !");
        element.put("description", "Jérémy Duval");
        element.put("image", String.valueOf(R.mipmap.ic_default_profile));
        listItem.add(element);

        element = new HashMap<>();
        element.put("titre", "Vache volante, idole de tout les peuples");
        element.put("description", "Marguerite Cow");
        element.put("image", String.valueOf(R.mipmap.ic_default_profile));
        listItem.add(element);

        element = new HashMap<>();
        element.put("titre", "Connue de tout les programmeur mais personne ne sait qui il est vraiment");
        element.put("description", "Toto TitiTataTutu");
        element.put("image", String.valueOf(R.mipmap.ic_default_profile));
        listItem.add(element);

        //creation of a SimpleAdapter
        SimpleAdapter musique_schedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.affichageitem,
                new String[]{"image", "titre", "description"},
                new int[]{R.id.img, R.id.titre, R.id.description}

        );

        musique.setAdapter(musique_schedule);

        musique.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> hashmapselected = (HashMap<String, String>) musique.getItemAtPosition(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Music selected");
                alert.setIcon(Integer.parseInt(hashmapselected.get("image")));
                alert.setMessage("You have selected : " + hashmapselected.get("titre").toString() + " by " + hashmapselected.get("description").toString());
                alert.setPositiveButton("Got It", null);
                alert.show();
            }
        });

        b_contact_adding.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ActivityA.class);
                startActivity(myIntent);
            }
        });


    }
}
