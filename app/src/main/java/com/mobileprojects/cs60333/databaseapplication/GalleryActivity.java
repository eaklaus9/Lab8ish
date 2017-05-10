package com.mobileprojects.cs60333.databaseapplication;

import android.content.ContentValues;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bchaudhr on 4/5/2017.
 */

public class GalleryActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1;
    int book_id;
    GridView gridview;
    String timeStamp;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        dbHelper = new DBHelper(this);
        Intent i = getIntent();
        book_id = i.getExtras().getInt("id");

        gridview = (GridView) findViewById(R.id.gridview);
        populateGridView();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Send intent to ImageActivity
                Intent i = new Intent(getApplicationContext(), ImageActivity.class);
                // Pass image index
                i.putExtra("id", position);
                startActivity(i);
            }
        });
    }

    public void cameraClick(View v) {
         Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
         File storageDirectory = new File(getApplicationContext().getFilesDir(), "images");

         if(!storageDirectory.exists()) storageDirectory.mkdir();
         setTimeStamp();

         File imageFile = new File(storageDirectory, getPictureName());
         Uri pictureUri = FileProvider.getUriForFile(getApplicationContext(),
                getPackageName(),
                imageFile);
         System.out.println(pictureUri);
         cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
         cameraIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
         startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }


    private void setTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        timeStamp = sdf.format(new Date());
    }

    private String getPictureName() {
        String imageName = "BookImages" + timeStamp + ".jpg";
        return imageName;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //super.onActivityResult(requestCode, resultCode, intent);
        System.out.println("Hello 2222");
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                File imagePath = new File(getApplicationContext().getFilesDir(), "images");
                File imageFile = new File( imagePath, getPictureName());
                final Uri imageUri = FileProvider.getUriForFile(this, getPackageName(), imageFile);

                //File storageDirectory = getApplicationContext().getExternalFilesDir("images");
                //Uri imageUri = Uri.parse(storageDirectory + File.separator + getPictureName());
                ContentValues contentValues = new ContentValues();
                contentValues.put(dbHelper.COL_IMAGE, imageUri.toString());
                contentValues.put(dbHelper.COL_BOOK_ID, book_id);
                dbHelper.insertData("Image", contentValues);
                populateGridView();
            }
        }
    }


    private void populateGridView() {
        //Get all the book entries from the table Books
        String[] fields = dbHelper.getTableFields(dbHelper.TABLE_IMAGES);
        String where = " book_id = ?";
        String[] args = new String[]{Integer.toString(book_id)};
        Cursor cursor = dbHelper.getSelectEntries(dbHelper.TABLE_IMAGES, fields, where, args);

        //Get ids of all the widgets in the custom layout for the listview
//        String[] field_names = new String[] {dbHelper.COL_IMAGE, R.id};
//        int[] item_ids = new int[] {R.id.iv_game, R.id.tv_label};

        //Create the cursor that is going to feed information to the listview
        SimpleCursorAdapter imageCursor;

  //      imageCursor = new SimpleCursorAdapter( getBaseContext(),
  //              R.layout.image_layout, cursor, fields, item_ids, 0);
  //      gridview.setAdapter(imageCursor);
    }
}