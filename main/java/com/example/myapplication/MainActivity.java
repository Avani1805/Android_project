package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class MainActivity extends Activity implements OnClickListener, AdapterView.OnItemSelectedListener

{
    String[] select = { "Supervisor", "Surveyor"};
    String[] district = { "Pune", "Mumbai"};
    String[] mandal = { "Hello", "Bhai"};
    String[] name = { "Hema", "Harry"};
    String[] designation = { "Mr.", "Ms."};

    EditText User,Name,Marks;
    Button Insert,Delete,Update,View,ViewAll;
    SQLiteDatabase db;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getWindow().getDecorView().setBackgroundColor(Color.YELLOW);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,select);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        Spinner spin1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter aa1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,district);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(aa1);
        spin1.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter aa2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mandal);
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(aa2);
        spin2.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        Spinner spin3 = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter aa3 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,name);
        aa3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(aa3);
        spin3.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        Spinner spin4 = (Spinner) findViewById(R.id.spinner4);
        ArrayAdapter aa4 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,designation);
        aa3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin4.setAdapter(aa4);
        spin4.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        User=(EditText)findViewById(R.id.User);
        Name=(EditText)findViewById(R.id.Name);

        Insert=(Button)findViewById(R.id.submit);

        Insert.setOnClickListener(this);


    }
    public void onClick(View view)
    {
        // Inserting a record to the Student table

    }
    public void showMessage(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        User.setText("");
        Name.setText("");
        Marks.setText("");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}