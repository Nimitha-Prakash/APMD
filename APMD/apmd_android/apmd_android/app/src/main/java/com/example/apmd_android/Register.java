package com.example.apmd_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONObject;

public class Register extends Activity  implements JsonResponse
{
	Button b1;
	RadioButton r1,r2;
	EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
	String url="";
	String fname,lname,hname,gen,place,pin,phone,email,uname,pass;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		e1=(EditText)findViewById(R.id.editTextfname);
		e2=(EditText)findViewById(R.id.editTextlname);
		e3=(EditText)findViewById(R.id.editTextplace);

		e5=(EditText)findViewById(R.id.editTextphone);
		e6=(EditText)findViewById(R.id.editTextemail);


		e7=(EditText)findViewById(R.id.editTextuname);
		e8=(EditText)findViewById(R.id.editTextpass);
//		e9=(EditText)findViewById(R.id.editTexthname);



		
		b1=(Button)findViewById(R.id.button1);
		b1.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub	


				fname=e1.getText().toString();
				lname=e2.getText().toString();
				place=e3.getText().toString();
//				pin=e4.getText().toString();
				phone=e5.getText().toString();
				email=e6.getText().toString();
				uname=e7.getText().toString();
				pass=e8.getText().toString();
//				hname=e9.getText().toString();

				if(fname.equalsIgnoreCase(""))
				{
					e1.setError("Enter First Name");
					e1.setFocusable(true);
				}
				else if(lname.equalsIgnoreCase(""))
				{
					e2.setError("Enter Last Name");
					e2.setFocusable(true);
				}
				else if(place.equalsIgnoreCase(""))
				{
					e3.setError("Enter Place");
					e3.setFocusable(true);
				}else if(phone.equalsIgnoreCase(""))
				{
					e5.setError("Enter Phone");
					e5.setFocusable(true);
				}else if(email.equalsIgnoreCase(""))
				{
					e6.setError("Enter Email");
					e6.setFocusable(true);
				}
				else if(!Patterns.PHONE.matcher(phone).matches())
				{
					e5.setError("Enter Valid Number !");
					e5.setFocusable(true);
				}
				else if(phone.length()!=10)
				{
					e5.setError("Enter Valid Phone Number");
					e5.setFocusable(true);
				}
				else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
				{
					e6.setError("Enter Valid Email !");
					e6.setFocusable(true);
				}
				else if(uname.equalsIgnoreCase(""))
				{
					e7.setError("Enter Username");
					e7.setFocusable(true);
				}
				else if(pass.equalsIgnoreCase(""))
				{
					e8.setError("Enter Password");
					e8.setFocusable(true);
				}

				else
				{

					JsonReq jr= new JsonReq();
					jr.json_response=(JsonResponse) Register.this;
					String q="/registerstud?fname=" + fname+"&lname="+lname+"&place="+place+"&phone="+phone+"&email="+email+"&uname="+uname+"&pass="+pass;
					jr.execute(q);

				}
			}
		});
	}

	@Override
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("register"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					Toast.makeText(getApplicationContext(), "Submitted succesfully..", Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(),Login.class));
//					Intent b=new Intent(getApplicationContext(), Login.class);
//					startActivity(b);

				}
				else
				{
					Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
		}
	}


}
