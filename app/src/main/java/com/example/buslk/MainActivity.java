package com.example.buslk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText BName, SPlace, StPlace, Time, Cost, BNumber;
    Button Add, Display, Update, Delete, Activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        BName = (EditText)findViewById(R.id.txtBName);
        SPlace = (EditText)findViewById(R.id.txtSPlace);
        StPlace = (EditText)findViewById(R.id.txtStPlace);
        Time = (EditText)findViewById(R.id.txtTime);
        Cost = (EditText)findViewById(R.id.txtCost);
        BNumber = (EditText)findViewById(R.id.txtBNumber);
        Add = (Button)findViewById(R.id.btnAdd);
        Display = (Button)findViewById(R.id.btnDisplay);
        Update = (Button) findViewById(R.id.btnUpdate);
        Delete = (Button) findViewById(R.id.btnDelete);
        Activity = (Button)findViewById(R.id.btnActivity);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
        Activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMoreDetails();
            }
        });

    }
    public void openMoreDetails(){
        Intent intent = new Intent(this, MoreDetails.class);
        startActivity(intent);
    }
    public void DeleteData() {
        Delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(BNumber.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void UpdateData(){
        Update.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(BNumber.getText().toString(),
                                BName.getText().toString(),
                                SPlace.getText().toString(),
                                StPlace.getText().toString(),
                                Time.getText().toString(),
                                Cost.getText().toString());
                        if(isUpdate == true)
                            Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void AddData(){
        Add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(BName.getText().toString(),
                                SPlace.getText().toString(),
                                StPlace.getText().toString(),
                                Time.getText().toString(),
                                Cost.getText().toString());
                        if(isInserted == true)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void viewAll(){
        Display.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                                Cursor res = myDb.getAllData();
                                if(res.getCount() == 0){
                                    //show message
                                    showMessage("Error ","Nothing found");
                                    return;
                                }
                                StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("BusNo :" + res.getString(0) + "\n");
                            buffer.append("BusName :" + res.getString(1) + "\n");
                            buffer.append("StartPlace :" + res.getString(2) + "\n");
                            buffer.append("StopPlace :" + res.getString(3) + "\n");
                            buffer.append("TotalTime :" + res.getString(4) + "\n");
                            buffer.append("TotalTicketPrice :" + res.getString(5) + "\n\n");
                        }

                        //show all data
                        showMessage("Data ",buffer.toString());
                    }
                }
        );
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
