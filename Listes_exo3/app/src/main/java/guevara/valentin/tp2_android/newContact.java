package guevara.valentin.tp2_android;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class newContact extends AppCompatActivity {
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "MainActivity";
    private String path;
    private Uri selectedImageUri;
    Calendar myCalendar = Calendar.getInstance();
    EditText dateNaiss;
    RadioButton masc,fem;
    EditText nom,prenom,numero;
    TextView textContact;
    ImageView imageContact;
    Button valid;

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };


    private void updateLabel() {

        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        dateNaiss.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        dateNaiss = (EditText) findViewById(R.id.editTextdateNaiss);
        masc = (RadioButton) findViewById(R.id.radioButton);
        fem = (RadioButton) findViewById(R.id.radioButton2);
        nom = (EditText) findViewById(R.id.editText2);
        prenom = (EditText) findViewById(R.id.editText3);
        numero = (EditText) findViewById(R.id.editText4);
        imageContact =  (ImageView) findViewById(R.id.imageContact);
        textContact = (TextView) findViewById(R.id.textContact);
        valid = (Button) findViewById(R.id.button);

        dateNaiss.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(newContact.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        imageContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECT_PICTURE);
            }
        });



        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((!masc.isChecked() && !fem.isChecked()) || nom.getText().toString().equals("") || prenom.getText().toString().equals("") || numero.getText().toString().equals("") || dateNaiss.getText().toString().equals("")){
                    //nom.setText(prenom.getText().toString());
                    new AlertDialog.Builder(newContact.this)
                            .setTitle("Remplir tous les champs")
                            .setMessage("Il est impossible de laisser des champs vides")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else{
                    HashMap<String,String> element = new HashMap<>();
                    element.put("nom", nom.getText().toString());
                    element.put("prenom", prenom.getText().toString());
                    element.put("numero", numero.getText().toString());

                    if(selectedImageUri != null) {
                        element.put("image", path);
                    }else{
                        element.put("image", null);
                    }

                    element.put("dateNaiss", dateNaiss.getText().toString());

                    if(masc.isChecked()){
                        element.put("sexe", masc.getText().toString());
                    }else{
                        element.put("sexe", fem.getText().toString());
                    }

                    Intent myIntent = new Intent(newContact.this, MainActivity.class);
                    myIntent.putExtra("contact", element);
                    setResult(RESULT_OK, myIntent);
                    finish();
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    path = getPathFromURI(selectedImageUri);
                    Log.i(TAG, "Image Path : " + path);
                    // Set the image in ImageView
                    imageContact.setImageURI(selectedImageUri);
                    textContact.setText("Chemin : " + path);
                    Toast.makeText(this.getApplicationContext(),"Image ajoutée avec succès", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}
