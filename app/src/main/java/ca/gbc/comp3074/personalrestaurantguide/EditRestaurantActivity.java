package ca.gbc.comp3074.personalrestaurantguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EditRestaurantActivity extends AppCompatActivity {

    private TextView editScreenTitle;
    private TextView editNameLabel;
    private EditText editNameInput;
    private TextView editAddressLabel;
    private EditText editAddressInput;
    private TextView editPhoneLabel;
    private EditText editPhoneInput;
    private TextView editDescriptionLabel;
    private EditText editDescriptionInput;
    private TextView editRatingLabel;
    private RatingBar editRatingBar;
    private TextView editTagsLabel;
    private CheckBox editCanadianTag;
    private CheckBox editItalianTag;
    private CheckBox editGreekTag;
    private CheckBox editJapaneseTag;
    private CheckBox editChineseTag;
    private CheckBox editIndianTag;
    private Button submitEditBtn;
    private SqliteDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);


        Intent i=getIntent();
        int id=i.getExtras().getInt("COLUMN_ID");
        String name=i.getExtras().getString("COLUMN_NAME");
        String address=i.getExtras().getString("COLUMN_ADDRESS");
        String phone=i.getExtras().getString("COLUMN_PHONE");
        String description=i.getExtras().getString("COLUMN_DESCRIPTION");
        String ratingBar=i.getExtras().getString("COLUMN_RATING");
        String tags=i.getExtras().getString("COLUMN_TAGS");

        mDatabase = new SqliteDatabase(this);

        submitEditBtn=(Button)findViewById(R.id.submitEditBtn);
        editScreenTitle=(TextView)findViewById(R.id.editScreenTitle);
        editNameLabel=(TextView)findViewById(R.id.editNameLabel);
        editNameInput=(EditText)findViewById(R.id.editNameInput);
        editAddressLabel=(TextView)findViewById(R.id.editAddressLabel);
        editAddressInput=(EditText)findViewById(R.id.editAddressInput);
        editPhoneLabel=(TextView)findViewById(R.id.editPhoneLabel);
        editPhoneInput=(EditText)findViewById(R.id.editPhoneInput);
        editDescriptionLabel=(TextView)findViewById(R.id.editDescriptionLabel);
        editDescriptionInput=(EditText)findViewById(R.id.editDescriptionInput);
        editRatingLabel=(TextView)findViewById(R.id.editRatingLabel);
        editRatingBar=(RatingBar)findViewById(R.id.editRatingBar);
        editTagsLabel=(TextView)findViewById(R.id.editTagsLabel);
        editCanadianTag=(CheckBox)findViewById(R.id.editCanadianTag);
        editItalianTag=(CheckBox)findViewById(R.id.editItalianTag);
        editGreekTag=(CheckBox)findViewById(R.id.editGreekTag);
        editJapaneseTag=(CheckBox)findViewById(R.id.editJapaneseTag);
        editChineseTag=(CheckBox)findViewById(R.id.editChineseTag);
        editIndianTag=(CheckBox)findViewById(R.id.editIndianTag);

        float numStars=Float.parseFloat(ratingBar);

        editNameInput.setText(name);
        editAddressInput.setText(address);
        editPhoneInput.setText(phone);
        editDescriptionInput.setText(description);
        editRatingBar.setRating(numStars);

        //check which tags are present in the entry and check them
        if(tags.indexOf("chinese")>-1){

            editChineseTag.setChecked(true);

        }
        if(tags.indexOf("canadian")>-1){

            editCanadianTag.setChecked(true);

        }
        if(tags.indexOf("indian")>-1){

            editIndianTag.setChecked(true);

        }
        if(tags.indexOf("japanese")>-1){

            editJapaneseTag.setChecked(true);

        }
        if(tags.indexOf("greek")>-1){

            editGreekTag.setChecked(true);

        }
        if(tags.indexOf("italian")>-1){

            editItalianTag.setChecked(true);

        }

        submitEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=editNameInput.getText().toString();
                String address=editAddressInput.getText().toString();
                String phone=editPhoneInput.getText().toString();
                String description=editDescriptionInput.getText().toString();
                float numStars = editRatingBar.getRating();
                String rating = Float.toString(numStars);
                String tags = "";
                if (editIndianTag.isChecked()) {
                    tags = tags + "," + editIndianTag.getText();
                }
                if (editChineseTag.isChecked()) {
                    tags = tags + "," + editChineseTag.getText();
                }
                if (editJapaneseTag.isChecked()) {
                    tags = tags + "," + editJapaneseTag.getText();
                }
                if (editGreekTag.isChecked()) {
                    tags = tags + "," + editGreekTag.getText();
                }
                if (editItalianTag.isChecked()) {
                    tags = tags + "," + editItalianTag.getText();
                }
                if (editCanadianTag.isChecked()) {
                    tags = tags + "," + editCanadianTag.getText();
                }


                mDatabase.updateEntries(new Entries(id,name,address,phone,description,rating,tags));

                Intent intent=new Intent(EditRestaurantActivity.this,RestaurantsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addRestaurant:
                Intent intent = new Intent(this, AddRestaurantActivity.class);
                this.startActivity(intent);
                break;
        }
        switch (item.getItemId()) {
            case R.id.about:
                Intent intent = new Intent(this, AboutActivity.class);
                this.startActivity(intent);
                break;
        }
        switch (item.getItemId()) {
            case R.id.restaurants:
                Intent intent = new Intent(this, RestaurantsActivity.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}