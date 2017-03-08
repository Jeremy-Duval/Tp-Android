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
    private ListView contact;
    private ArrayList<HashMap<String,String>> listItem;
    Button b_contact_adding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = getIntent();
        HashMap<String,String> renseignement = (HashMap<String, String>) intent.getSerializableExtra("contact");

        b_contact_adding = (Button) findViewById(R.id.button);

        //fetch the listView created in the XML file Main
        contact = (ListView) findViewById(R.id.contact);

        //HashMap with all elements (Image,Description...) inside the list
        listItem = new ArrayList<>();

        //We declare an Hashmap which is equivalent to one element
        HashMap<String,String> element;


        //We have to make these operations a lot of times for creating another element
        element = new HashMap<>();
        element.put("Nom", "Velien Fanny");
        element.put("Numero", "0609098149");
        element.put("image", String.valueOf(R.mipmap.ic_default_profile));
        listItem.add(element);

        element = new HashMap<>();
        element.put("Nom", renseignement.get("nom") + " " + renseignement.get("prenom"));
        element.put("Numero", renseignement.get("numero"));
        element.put("image", String.valueOf(R.mipmap.ic_default_profile));
        listItem.add(element);

        //creation of a SimpleAdapter
        SimpleAdapter contact_schedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.affichageitem,
                new String[]{"image", "Nom", "Numero"},
                new int[]{R.id.img, R.id.titre, R.id.description}

        );

        contact.setAdapter(contact_schedule);

        contact.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> hashmapselected = (HashMap<String, String>) contact.getItemAtPosition(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Contact selected");
                alert.setIcon(Integer.parseInt(hashmapselected.get("image")));
                alert.setMessage("You have selected : " + hashmapselected.get("titre").toString() + " by " + hashmapselected.get("description").toString());
                alert.setPositiveButton("Got It", null);
                alert.show();
            }
        });

        b_contact_adding.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, newContact.class);
                startActivity(myIntent);
            }
        });


    }
}
