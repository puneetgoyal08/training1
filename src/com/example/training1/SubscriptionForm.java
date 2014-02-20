package com.example.training1;

import java.io.IOException;

import com.example.training1.database.DatabaseAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SubscriptionForm extends Activity {

	private DatabaseAdapter dbAdapter;
	EditText name, address, phone, email;
	CheckBox guidelines;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subscription_form);
		dbAdapter = new DatabaseAdapter(getApplicationContext());

		try {
			dbAdapter.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}
		dbAdapter.openDataBase();

		Spinner profession = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.profession_array,
				android.R.layout.simple_spinner_dropdown_item);
		profession.setAdapter(adapter);
		Spinner gender = (Spinner) findViewById(R.id.Spinner01);
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
				this, R.array.gender_array,
				android.R.layout.simple_spinner_dropdown_item);
		gender.setAdapter(adapter1);

		setFonts();
		Button subscribe = (Button) findViewById(R.id.button1);

		subscribe.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				subscribeClicked();
				// TODO Auto-generated method stub
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		dbAdapter.close();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subscription_form, menu);
		return true;
	}

	public void subscribeClicked() {
		if (checkEntries()) {
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
			finish();
		}
	}

	public void setFonts() {
		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/data_gothic/jlsdatagothic_nc.otf");
		name = (EditText) findViewById(R.id.nameText);
		name.setTypeface(font);
		address = (EditText) findViewById(R.id.addressText);
		address.setTypeface(font);
		phone = (EditText) findViewById(R.id.phoneText);
		phone.setTypeface(font);
		email = (EditText) findViewById(R.id.emailText);
		email.setTypeface(font);
		guidelines = (CheckBox) findViewById(R.id.checkBox1);
	}

	public Boolean checkEntries() {
		String nameText = name.getText().toString();
		String addressText = address.getText().toString();
		String phoneText = phone.getText().toString();
		String emailText = email.getText().toString();
		Boolean userExists = dbAdapter.ifUserAlreadyExists(emailText);
		if (nameText.contentEquals("")) {
			name.setHintTextColor(Color.RED);
			name.setHint("Please enter your name");
			return false;
		} else if (addressText.contentEquals("")) {
			address.setHintTextColor(Color.RED);
			address.setHint("Please enter your address");
			return false;
		} else if (phoneText.length() != 10) {
			phone.setText("");
			phone.setHintTextColor(Color.RED);
			phone.setHint("Please enter your 10 digit phone number");
			return false;
		} else if (emailText.contentEquals("") || (!emailText.contains("@"))
				|| (!emailText.contains("."))) {
			email.setText("");
			email.setHintTextColor(Color.RED);
			email.setHint("Please enter a valid email id");
			return false;
		} else if (!guidelines.isChecked()) {
			Toast.makeText(getApplicationContext(),
					"Please read the guidelines and tick the checkbox",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (userExists) {
			Toast.makeText(getApplicationContext(),
					"User is already subscribed", Toast.LENGTH_SHORT).show();
			return false;
		}

		dbAdapter.createUser(nameText, emailText);
		return true;
	}
}
