package com.example.justwyne.test_json;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


////        TextView output = (TextView) findViewById(R.id.textView1);
//        String strJson=loadJSONFromAsset();
//        String data = "";
//        try {
//            JSONObject jsonRootObject = new JSONObject(strJson);
//
//            //Get the instance of JSONArray that contains JSONObjects
//            JSONArray jsonArray = jsonRootObject.optJSONArray("product_list");
//
//            //Iterate the jsonArray and print the info of JSONObjects
//            for(int i=0; i < jsonArray.length(); i++){
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                String id = jsonObject.optString("id").toString();
//                String name = jsonObject.optString("name").toString();
//                String price = jsonObject.optString("price").toString();
//
//                data += "ID" + id + " \n Name= " + name + " \n price = "+ price +" \n ";
//            }
////            output.setText(data);
//            Log.i("data",data);
//        } catch (JSONException e) {e.printStackTrace();}
//
//    }
//
//    public String loadJSONFromAsset() {
//        String json = "";
//
//        InputStream in = getResources().openRawResource(R.raw.product);
//
//        Scanner scan = new Scanner(in);
//        while (scan.hasNext()){
//            json += scan.nextLine();
//        }
//        scan.close();
//
//        return json;

        final LinearLayout lm = (LinearLayout) findViewById(R.id.layoutMain);

        // create the layout params that will be used to define how your
        // button will be displayed
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //Create four
        for(int j=0;j<=4;j++)
        {
            // Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);

            // Create TextView
            TextView product = new TextView(this);
            product.setText(" Product"+j+"    ");
            ll.addView(product);

            // Create TextView
            TextView price = new TextView(this);
            price.setText("  $"+j+"     ");
            ll.addView(price);

            // Create Button
            final Button btn = new Button(this);
            // Give button an ID
            btn.setId(j+1);
            btn.setText("Add To Cart");
            // set the layoutParams on the button
            btn.setLayoutParams(params);

            final int index = j;
            // Set click listener for button
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Log.i("TAG", "index :" + index);

                    Toast.makeText(getApplicationContext(),
                            "Clicked Button Index :" + index,
                            Toast.LENGTH_LONG).show();

                }
            });

            //Add button to LinearLayout
            ll.addView(btn);
            //Add button to LinearLayout defined in XML
            lm.addView(ll);
        }

    }
}
