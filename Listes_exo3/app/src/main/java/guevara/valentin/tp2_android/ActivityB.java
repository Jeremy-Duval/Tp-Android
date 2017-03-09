package guevara.valentin.tp2_android;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.net.URI;
import java.util.HashMap;

public class ActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        final Intent intent = getIntent();
        HashMap<String,String> infos = (HashMap<String, String>) intent.getSerializableExtra("contactSelected");
        TextView nom = (TextView) findViewById(R.id.nom);
        TextView age = (TextView) findViewById(R.id.age);
        ImageView image = (ImageView) findViewById(R.id.imageView);
        TextView numero = (TextView) findViewById(R.id.numero);
        Button valid = (Button) findViewById(R.id.valider);

        nom.setText(infos.get("nom"));
        age.setText(infos.get("naissance"));
        numero.setText(infos.get("numero"));


        File file = new File(infos.get("image"));
        Uri uri = Uri.fromFile(file);
        image.setImageURI(uri);

        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
