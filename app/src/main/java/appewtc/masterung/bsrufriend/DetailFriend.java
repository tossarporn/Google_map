package appewtc.masterung.bsrufriend;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailFriend extends AppCompatActivity {

    //Explicit
    private TextView nameTextView, latTextView, lngTextView;
    private ImageView imageView;
    private Button backButton, callButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_friend);

        //Bind Widget
        nameTextView = (TextView) findViewById(R.id.textView3);
        latTextView = (TextView) findViewById(R.id.textView4);
        lngTextView = (TextView) findViewById(R.id.textView5);
        imageView = (ImageView) findViewById(R.id.imageView11);
        backButton = (Button) findViewById(R.id.button6);
        callButton = (Button) findViewById(R.id.button5);

        //Show View
        nameTextView.setText(getIntent().getStringExtra("Name"));
        latTextView.setText("lat=" + getIntent().getStringExtra("Lat"));
        lngTextView.setText("lng" + getIntent().getStringExtra("Lng"));
        Picasso.with(DetailFriend.this).load(getIntent().getStringExtra("Image")).into(imageView);

        //Button Controller
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:=12345"));
                if (ActivityCompat.checkSelfPermission(DetailFriend.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);

            }
        });

    }//Main Method
}//Main Class
