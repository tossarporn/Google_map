package appewtc.masterung.bsrufriend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

public class ListFriend extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_friend);

        ListView listView = (ListView) findViewById(R.id.LivFriend);

        //create Listview
        try {
            GetUser getUser = new GetUser(ListFriend.this);
            MyContan myContan = new MyContan();
            getUser.execute(myContan.getUrlPHPString());
            String strJSON = getUser.get();
            JSONArray jsonArray = new JSONArray(strJSON);
            final String[] nameStrings = new String[jsonArray.length()];
            final String[] imageStrings = new String[jsonArray.length()];
            final String[] latStrings = new String[jsonArray.length()];
            final String[] lngStrings = new String[jsonArray.length()];

            for (int i=0;i<jsonArray.length();i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nameStrings[i] = jsonObject.getString("Name");
                imageStrings[i] = jsonObject.getString("Image");
                latStrings[i] = jsonObject.getString("Lat");
                lngStrings[i] = jsonObject.getString("Lng");

            }//For

            FriendAdapter friendAdapter = new FriendAdapter(ListFriend.this, imageStrings, nameStrings);
            listView.setAdapter(friendAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent intent = new Intent(ListFriend.this, DetailFriend.class);
                    intent.putExtra("Name", nameStrings[i]);
                    intent.putExtra("Image", imageStrings[i]);
                    intent.putExtra("Lat", latStrings[i]);
                    intent.putExtra("Lng", lngStrings[i]);
                    startActivity(intent);

                }
            });

        } catch (Exception e) {
            Log.d("17febV3", "e ListView ==>" + e.toString());

        }

    }//Main Method



}//Main Class
