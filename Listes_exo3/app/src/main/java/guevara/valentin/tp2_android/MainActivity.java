package guevara.valentin.tp2_android;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ListView contact;
    private HashMap<String, String> renseignement;
    private ArrayList<HashMap<String,String>> listItem;
    private SimpleAdapter contact_schedule;
    private static boolean first_Start = true;

    //We declare an Hashmap which is equivalent to one element
    private HashMap<String,String> element;

    Button b_contact_adding;


    protected void saveList() {
        SharedPreferences db= PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());

        SharedPreferences.Editor collection = db.edit();
        Gson gson = new Gson();
        String contact = gson.toJson(listItem);

        collection.putString("listItem", contact);

        collection.commit();
    }

    private void restoreList() {
        SharedPreferences db=PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());

        Gson gson = new Gson();
        String arrayListString = db.getString("listItem", null);
        Type type = new TypeToken<ArrayList<HashMap<String,String>>>() {}.getType();
        listItem = gson.fromJson(arrayListString, type);

        if(listItem == null){
            listItem = new ArrayList<HashMap<String, String>>();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        verifyStoragePermissions(MainActivity.this);

        b_contact_adding = (Button) findViewById(R.id.button);

        //fetch the listView created in the XML file Main
        contact = (ListView) findViewById(R.id.contact);

        //HashMap with all elements (Image,Description...) inside the list
        listItem = new ArrayList<>();


        //restore list saved
        restoreList();

        //creation of a SimpleAdapter
        contact_schedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.affichageitem,
                new String[]{"image", "nom", "numero"},
                new int[]{R.id.img, R.id.titre, R.id.description}

        );

        contact.setAdapter(contact_schedule);

        contact.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final HashMap<String, String> hashmapselected = (HashMap<String, String>) contact.getItemAtPosition(position);
                Resources res = getResources();
                BitmapDrawable icon = new BitmapDrawable(res, hashmapselected.get("image"));
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                if(hashmapselected.get("sexe").equals("M")){
                    alert.setTitle("Contact selectionné");
                }else{
                    alert.setTitle("Contact selectionnée");
                }
                alert.setIcon(icon);
                alert.setMessage("Vous avez choisi : " + hashmapselected.get("nom").toString() + "\n" + hashmapselected.get("numero").toString());
                alert.setPositiveButton("Appeler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", hashmapselected.get("numero"), null));
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("detail", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntentDetail = new Intent(MainActivity.this, ActivityB.class);
                        myIntentDetail.putExtra("contactSelected", hashmapselected);
                        startActivity(myIntentDetail);
                    }
                });
                alert.show();
            }
        });

        contact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int contactASupprimer = position;
                final HashMap<String, String> hashmapselected = (HashMap<String, String>) contact.getItemAtPosition(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Supprimer Contact");
                alert.setMessage(Html.fromHtml("Voulez-vous réellement supprimer le contact : <font color='#FF0000'><b>" + hashmapselected.get("nom").toString() + "</b></font>"));
                alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listItem.remove(contactASupprimer);
                        contact_schedule.notifyDataSetChanged();
                    }
                });
                alert.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ne rien faire
                    }
                });
                alert.show();
                return true;
            }
        });

        b_contact_adding.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, newContact.class);
                startActivityForResult(myIntent, 1);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            System.out.println("adding");
            if (resultCode == RESULT_OK) {
                renseignement = (HashMap<String, String>) data.getSerializableExtra("contact");
                element = new HashMap<>();
                element.put("nom", renseignement.get("nom") + " " + renseignement.get("prenom"));
                element.put("numero", renseignement.get("numero"));
                element.put("naissance", renseignement.get("dateNaiss"));

                if(renseignement.get("image") != null) {
                    element.put("image", renseignement.get("image"));
                }else{
                    System.out.println("image par défault");
                    element.put("image", String.valueOf(R.mipmap.ic_default_profile));
                }

                element.put("sexe", renseignement.get("sexe"));
                listItem.add(element);
                contact_schedule.notifyDataSetChanged(); //add the new contact
                System.out.println("ActivityResult");
            }
            if (resultCode == RESULT_CANCELED) {
            }
        }
    }

    @Override
    protected void onStop(){
        saveList(); //save the new list
        super.onStop();
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}
