package com.globalspace.miljonsales.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.globalspace.miljonsales.model.Login_Response;
import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.retrofit.ApiUtils;
import com.globalspace.miljonsales.utils.Internet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by aravind on 10/7/18.
 */

public class RegisterActivity extends Activity implements OnItemClickListener {
	
	
	
	// UI elements
	static EditText txtshopName;
	static EditText txtEmail;
	static AutoCompleteTextView txtCity;
	static EditText txtState;
	static TextView tvBasicInfo;
	
	static EditText txtlandmark, et_gstnumber;
	static EditText txtpin_no;
	static EditText txtownername;
	static EditText txtMobileno;
	static EditText txtDrug_licence_no;
	static EditText createpassword, etaltMobNumber, etconfirmpassword;
	static String Shop_name;
	static String email;
	static String mobileno;
	static String City;
	static String State;
	static String pin_no;
	static String Drug_licence;
	static String Owner_name;
	static String Createpassword;
	static String altMobNumber;
	static String landmark;
	static String Gstin;
	static String confirmpassword = "";
	public static String urlAll = "http://13.127.182.214/zomed_new_tmp/SeedApp/";
	static String serverUrl = urlAll + "zipcode.php";
	String function_name;
	private String complete_pin = "";
	private Boolean area;
	
	static String Flag;
	
	
	String item = "";
	SharedPreferences pref;
	
	// Register button
	Context context;
	
	ScrollView mainScroll;
	String emailPattern;
	String RecognitionFlag = "";
	String IdentifierFlag;
	SharedPreferences.Editor edit;
	
	String regId = "";
	String strlatitude, strlongitude;
	String Zip_ID, password, otpflag, IMEI, session_id = "";
	
	Button btnRegister ;
	private TextInputLayout inputLayoutshopName;
	private TextInputLayout inputLayoutownerName;
	private TextInputLayout inputLayoutmobNum;
	private TextInputLayout inputLayoutaltmobnum;
	private TextInputLayout inputLayoutemail;
	private TextInputLayout inputLayoutstate;
	private TextInputLayout inputLayoutcity;
	private TextInputLayout inputLayoutpin;
	private TextInputLayout inputLayoutcreate;
	private TextInputLayout inputLayoutconfirm;
	private TextInputLayout inputLayoutdruglicnum;
	private TextInputLayout inputlayoutlandmark;
	private TextInputLayout inputlayoutgstnumber;
	public static final String GSTINFORMAT_REGEX = "[0-9]{2}[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9A-Za-z]{1}[Z]{1}[0-9a-zA-Z]{1}";
	public static final String GSTN_CODEPOINT_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private ImageView backbtn;
	
	
	private static boolean isValidEmail(String email) {
		return !TextUtils.isEmpty(email)
				&& android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().setStatusBarColor(this.getResources().getColor(R.color.my_statusbar_color));
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		context = this;
		backbtn = (ImageView) findViewById(R.id.backBtn);
		
		backbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
		pref = context.getSharedPreferences(getResources().getString(R.string.app_name), context.MODE_PRIVATE);
		edit = pref.edit();
		
		setComponents();
		
		if (getIntent().getExtras() != null) {
			Intent i = getIntent();
			Flag = getIntent().getStringExtra("Identifierflag");
			IdentifierFlag = i.getStringExtra("Identifierflag");
		}
		strlatitude = "0";
		strlongitude = "0";
		IMEI = "null";
		regId = "null";
		Zip_ID = pref.getString("zip_id", "");

		if (IdentifierFlag.equals("1")) {
			// i.putExtra("Flag", "1");
			Flag = "1";
			etaltMobNumber.setVisibility(View.VISIBLE);
			
			
		} else if (IdentifierFlag.equals("4")) {
			// i.putExtra("Flag", "4");
			Flag = "4";
			etaltMobNumber.setVisibility(View.GONE);
			
		} else if (IdentifierFlag.equals("6")) {
			// i.putExtra("Flag", "5");
			Flag = "5";
			
		}else if (IdentifierFlag.equals("8")){
			Flag = "4";
			etaltMobNumber.setVisibility(View.GONE);
			txtshopName.setText(getIntent().getExtras().getString("ChemistName"));
			txtEmail.setText(getIntent().getExtras().getString("ChemistEmail"));
			txtMobileno.setText(getIntent().getExtras().getString("ChemistMobile"));
		}
		
		btnRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				//region Get data from EditText
				final View v = view;
				if (Internet.checkConnection(context)) {
					
					
					if (!validateshopname()) {
						//inputLayoutshopName.setError(getString(R.string.err_msg_shopname));
						
					} else if (!validateownername()) {
					
					
					} else if (!validategstnum()) {

//						inputLayoutownerName
//								.setError(getString(R.string.err_msg_ownername));
					} else if (!validatemobno()) {
					
					
					} else if (!validatealtmobno()) {


//									inputLayoutmobNum.setError(getString(R.string.err_msg_mob));
					
					} else if (!validateEmail()) {
//						inputLayoutemail.setError(getString(R.string.err_msg_email));
					
					} else if (!validatelandmark()) {
//							inputlayoutlandmark
//					.setError(getString(R.string.err_msg_Landmark));
					
					} else if (!validatepin()) {
//						inputLayoutpin.setError(getString(R.string.err_msg_pin));
					
					} else if (!validatecity()) {
//						inputLayoutcity.setError(getString(R.string.err_msg_city));
					
					} else if (!validatestate()) {
//						inputLayoutstate.setError(getString(R.string.err_msg_State));
					
					} else if (!validatepassword()) {
//						inputLayoutcreate.setError(getString(R.string.err_msg_Password));
					
					} else if (!validateconfirmpassword()) {
//						inputLayoutconfirm
//								.setError(getString(R.string.err_msg_confirmpassword));
					} else if (!validatedruglicnum()) {
//						inputLayoutdruglicnum
//								.setError(getString(R.string.err_msg_druglicnum));
					} else {
						
						Shop_name = txtshopName.getText().toString();
						
						email = txtEmail.getText().toString();
						mobileno = txtMobileno.getText().toString();
						
						City = txtCity.getText().toString();
						State = txtState.getText().toString();
						pin_no = txtpin_no.getText().toString();
						
						Drug_licence = txtDrug_licence_no.getText().toString();
						Owner_name = txtownername.getText().toString();
						Createpassword = createpassword.getText().toString();
						confirmpassword = etconfirmpassword.getText().toString();
						landmark = txtlandmark.getText().toString();
						//  Gstin = et_gstnumber.getText().toString();
						
						if (Flag.equals("1")) {
							altMobNumber = etaltMobNumber.getText().toString();
							Gstin = et_gstnumber.getText().toString();
						} else {
							
							altMobNumber = "null";
							Gstin = "0";
						}
						
						
						Drug_licence = txtDrug_licence_no.getText().toString();
						Owner_name = txtownername.getText().toString();
						Shop_name = txtshopName.getText().toString();
						
						email = txtEmail.getText().toString();
						mobileno = txtMobileno.getText().toString();
						
						landmark = txtlandmark.getText().toString();
						City = txtCity.getText().toString();
						pin_no = txtpin_no.getText().toString();
						State = txtState.getText().toString();
						
						// Createpassword = createpassword.getText().toString();
						// altMobNumber = etaltMobNumber.getText().toString();
						//  confirmpassword = etconfirmpassword.getText().toString();
						
						Gstin = et_gstnumber.getText().toString();
						
						function_name = "userRegistration";
						//   String flag = "chemist";
						Zip_ID = pref.getString("zip_id", "");
						session_id = pref.getString("employee_id", "");
						
						otpflag = "true";
						
						registerChemist(Drug_licence, Owner_name, Shop_name,
								email, mobileno, landmark, City, pin_no, State, Gstin,
								function_name, Flag, Createpassword, otpflag, session_id,
								regId,IMEI, strlatitude, strlongitude, altMobNumber, Zip_ID);
					}
					
					
				} else {
					Toast.makeText(context, "No Internet Connection",
							Toast.LENGTH_SHORT).show();
					
				}
				
				//endregion
			}
			
		});
		
		
		//region set Focus Listener
		txtshopName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean b) {
				if (b) {
					
					SpannableStringBuilder builder1 = setStarToLabel("Enter Shop Name");
					txtshopName.setHint(builder1);
					
				} else {
					
					//txtshopName.setHint("");
					
					SpannableStringBuilder builder1 = removeStarToLabel("Enter Shop Name");
					txtshopName.setHint(builder1);
					//txtshopName.setHintTextColor(Color.parseColor("#757575"));
				}
				
			}
		});
		
		txtownername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean b) {
				if (b) {
					
					SpannableStringBuilder builder1 = setStarToLabel("Owner Name");
					txtownername.setHint(builder1);
					
				} else {
					txtownername.setHint("");
					
					
					SpannableStringBuilder builder1 = removeStarToLabel("Owner Name");
					txtownername.setHint(builder1);
					txtownername.setHintTextColor(Color.parseColor("#757575"));
				}
				
			}
		});
		
		
		txtMobileno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean b) {
				if (b) {
					
					SpannableStringBuilder builder1 = setStarToLabel("Mobile Number");
					txtMobileno.setHint(builder1);
					
				} else {
					
					SpannableStringBuilder builder1 = removeStarToLabel("Mobile Number");
					txtMobileno.setHint(builder1);
				}
				
			}
		});
		
		
		txtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean b) {
				if (b) {
					
					SpannableStringBuilder builder1 = setStarToLabel("Email ID");
					txtEmail.setHint(builder1);
					
				} else {
					
					SpannableStringBuilder builder1 = removeStarToLabel("Email ID");
					txtEmail.setHint(builder1);
				}
				
			}
		});
		
		
		txtlandmark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean b) {
				if (b) {
					
					SpannableStringBuilder builder1 = setStarToLabel("Street/Landmark/Locality");
					txtlandmark.setHint(builder1);
					
				} else {
					
					SpannableStringBuilder builder1 = removeStarToLabel("Street/Landmark/Locality");
					txtlandmark.setHint(builder1);
				}
				
			}
		});
		
		txtpin_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean b) {
				if (b) {
					
					SpannableStringBuilder builder1 = setStarToLabel("Pin Code");
					txtpin_no.setHint(builder1);
					
				} else {
					
					SpannableStringBuilder builder1 = removeStarToLabel("Pin Code");
					txtpin_no.setHint(builder1);
				}
				
			}
		});
		
		
		txtCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean b) {
				if (b) {
					
					SpannableStringBuilder builder1 = setStarToLabel("Town/City");
					txtCity.setHint(builder1);
					
				} else {
					
					SpannableStringBuilder builder1 = removeStarToLabel("Town/City");
					txtCity.setHint(builder1);
				}
				
			}
		});
		txtState.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean b) {
				if (b) {
					
					SpannableStringBuilder builder1 = setStarToLabel("State");
					txtState.setHint(builder1);
					
				} else {
					
					SpannableStringBuilder builder1 = removeStarToLabel("State");
					txtState.setHint(builder1);
				}
				
			}
		});
		
		createpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean b) {
				if (b) {
					
					SpannableStringBuilder builder1 = setStarToLabel("Create Password");
					createpassword.setHint(builder1);
					
				} else {
					
					SpannableStringBuilder builder1 = removeStarToLabel("Create Password");
					createpassword.setHint(builder1);
				}
				
			}
		});
		
		etconfirmpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean b) {
				if (b) {
					
					SpannableStringBuilder builder1 = setStarToLabel("Confirm Password");
					etconfirmpassword.setHint(builder1);
					
				} else {
					
					SpannableStringBuilder builder1 = removeStarToLabel("Confirm Password");
					etconfirmpassword.setHint(builder1);
				}
				
			}
		});
		
		
		txtDrug_licence_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean b) {
				if (b) {
					
					SpannableStringBuilder builder1 = setStarToLabel("Drug License Number");
					txtDrug_licence_no.setHint(builder1);
					
				} else {
					
					SpannableStringBuilder builder1 = removeStarToLabel("Drug License Number");
					txtDrug_licence_no.setHint(builder1);
				}
				
			}
		});
		
		et_gstnumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean b) {
				if (b) {
					
					SpannableStringBuilder builder1 = setStarToLabel("GST Number");
					et_gstnumber.setHint(builder1);
					
				} else {
					
					SpannableStringBuilder builder1 = removeStarToLabel("GST Number");
					et_gstnumber.setHint(builder1);
				}
				
			}
		});
		//endregion
		
		
		txtCity.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
			                        int position, long id) {
				// TODO Auto-generated method stub
				item = (String) parent.getItemAtPosition(position);
				String s1[] = item.split(",");
				
				for (int i = 0; i < s1.length; i++) {
					txtCity.setText("");
					txtState.setText("");
					if (s1.length == 2) {
						txtCity.setText(s1[0].toString().trim());
						txtState.setText(s1[s1.length - 2].toString().trim());
						
					} else if (s1.length == 3) {
						txtCity.setText(s1[0].toString().trim());
						txtState.setText(s1[s1.length - 2].toString().trim());
					} else if (s1.length == 4) {
						txtCity.setText(s1[0].toString().trim() + ","
								+ s1[1].toString().trim());
						txtState.setText(s1[s1.length - 2].toString().trim());
					} else if (s1.length == 5) {
						txtCity.setText(s1[1].toString().trim() + ","
								+ s1[2].toString().trim());
						txtState.setText(s1[s1.length - 2].toString().trim());
					} else if (s1.length == 6 || s1.length == 7) {
						txtCity.setText(s1[s1.length - 3].toString().trim());
						txtState.setText(s1[s1.length - 2].toString().trim());
					}
					
				}
				
			}
		});
		
	}
	
	private SpannableStringBuilder setStarToLabel(String text) {
		String simple = text;
		String colored = "*";
		SpannableStringBuilder builder = new SpannableStringBuilder();
		builder.append(simple);
		int start = builder.length();
		builder.append(colored);
		int end = builder.length();
		builder.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return builder;
	}
	
	private SpannableStringBuilder removeStarToLabel(String text) {
		String simple = text;
		String colored = "*";
		SpannableStringBuilder builder = new SpannableStringBuilder();
		builder.append(simple);
		
		return builder;
	}
	
	private void setComponents(){
		inputLayoutshopName = (TextInputLayout) findViewById(R.id.input_layout_shopname);
		inputLayoutownerName = (TextInputLayout) findViewById(R.id.input_layout_ownername);
		inputLayoutmobNum = (TextInputLayout) findViewById(R.id.input_layout_mobnum);
		inputLayoutaltmobnum = (TextInputLayout) findViewById(R.id.input_layout_altmobno);
		inputLayoutemail = (TextInputLayout) findViewById(R.id.input_layout_email);
		inputLayoutcity = (TextInputLayout) findViewById(R.id.input_layout_city);
		inputLayoutstate = (TextInputLayout) findViewById(R.id.input_layout_state);
		inputLayoutpin = (TextInputLayout) findViewById(R.id.input_layout_pin);
		inputLayoutcreate = (TextInputLayout) findViewById(R.id.input_layout_password);
		inputLayoutconfirm = (TextInputLayout) findViewById(R.id.input_layout_confirmpassword);
		inputLayoutdruglicnum = (TextInputLayout) findViewById(R.id.input_layout_druglicnum);
		inputlayoutlandmark = (TextInputLayout) findViewById(R.id.input_layout_landmark);
		inputlayoutgstnumber = (TextInputLayout) findViewById(R.id.input_layout_gstnumber);
		
		
		txtshopName = (EditText) findViewById(R.id.etShopName);
		txtEmail = (EditText) findViewById(R.id.etEmailID);
		txtMobileno = (EditText) findViewById(R.id.etMobNumber);
		txtownername = (EditText) findViewById(R.id.etOwnerName);
		txtpin_no = (EditText) findViewById(R.id.etPin);
		txtDrug_licence_no = (EditText) findViewById(R.id.etDrugLicenceNo);
		txtCity = (AutoCompleteTextView) findViewById(R.id.etcity);
		txtState = (EditText) findViewById(R.id.etState);
		createpassword = (EditText) findViewById(R.id.etCreatepassword);
		etaltMobNumber = (EditText) findViewById(R.id.etaltMobNumber);
		txtlandmark = (EditText) findViewById(R.id.etLandmark);
		et_gstnumber = (EditText) findViewById(R.id.etGstno);
		btnRegister = (Button) findViewById(R.id.buttonNextRegister);
		//btnRegister = (LiveButton) findViewById(R.id.buttonNextRegister);
		//	btnRegister = (Button) findViewById(R.id.buttonNextRegister);
		etconfirmpassword = (EditText) findViewById(R.id.etconfirmpassword);
		
		//	tvMedInName = (TextView) findViewById(R.id.tvMedInName);
		//tvBasicInfo = (TextView) findViewById(R.id.tvBasicInfo);
		
		emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
		
		
		//region Set Hint and text change listener
		txtCity.addTextChangedListener(new MyTextWatcher(txtCity));
		txtshopName.addTextChangedListener(new MyTextWatcher(txtshopName));
		txtEmail.addTextChangedListener(new MyTextWatcher(txtEmail));
		txtMobileno.addTextChangedListener(new MyTextWatcher(txtMobileno));
		txtownername.addTextChangedListener(new MyTextWatcher(txtownername));
		txtpin_no.addTextChangedListener(new MyTextWatcher(txtpin_no));
		txtDrug_licence_no.addTextChangedListener(new MyTextWatcher(
				txtDrug_licence_no));
		txtState.addTextChangedListener(new MyTextWatcher(txtState));
		createpassword
				.addTextChangedListener(new MyTextWatcher(createpassword));
		etaltMobNumber
				.addTextChangedListener(new MyTextWatcher(etaltMobNumber));
		etconfirmpassword.addTextChangedListener(new MyTextWatcher(
				etconfirmpassword));
		et_gstnumber.addTextChangedListener(new MyTextWatcher(
				et_gstnumber));

       /* txtshopName.setTypeface(tf1);
        txtEmail.setTypeface(tf1);
        txtMobileno.setTypeface(tf1);
        txtownername.setTypeface(tf1);
        txtpin_no.setTypeface(tf1);
        txtDrug_licence_no.setTypeface(tf1);
        txtCity.setTypeface(tf1);
        txtState.setTypeface(tf1);
        createpassword.setTypeface(tf1);
        etaltMobNumber.setTypeface(tf1);
        etconfirmpassword.setTypeface(tf1);
        txtlandmark.setTypeface(tf1);
        et_gstnumber.setTypeface(tf1);

        // tvMedInName.setTypeface(tf1);
        //tvMedInName.setTypeface(tf1);
        tvBasicInfo.setTypeface(tf1);
        btnRegister.setTypeface(tf1);*/
		
		
		SpannableStringBuilder builder1 = setStarToLabel("Enter Shop Name");
		txtshopName.setHint(builder1);
		SpannableStringBuilder builder2 = setStarToLabel("Owner Name");
		txtownername.setHint(builder2);
		SpannableStringBuilder builder3 = setStarToLabel("Mobile Number");
		txtMobileno.setHint(builder3);
		SpannableStringBuilder builder4 = setStarToLabel("Email ID");
		txtEmail.setHint(builder4);
		SpannableStringBuilder builder5 = setStarToLabel("Street/Landmark/Locality");
		txtlandmark.setHint(builder5);
		
		SpannableStringBuilder builder6 = setStarToLabel("Pin Code");
		txtpin_no.setHint(builder6);
		SpannableStringBuilder builder7 = setStarToLabel("Town/City");
		txtCity.setHint(builder7);
		SpannableStringBuilder builder8 = setStarToLabel("State");
		txtState.setHint(builder8);
		SpannableStringBuilder builder9 = setStarToLabel("Create Password");
		createpassword.setHint(builder9);
		
		SpannableStringBuilder builder10 = setStarToLabel("Confirm Password");
		etconfirmpassword.setHint(builder10);
		SpannableStringBuilder builder11 = setStarToLabel("Drug License Number");
		txtDrug_licence_no.setHint(builder11);
		SpannableStringBuilder builder12 = setStarToLabel("Covered Pin Area ex- 400710,400720,400709");
		etaltMobNumber.setHint(builder12);
		SpannableStringBuilder builder13= setStarToLabel("GST Number");
		et_gstnumber.setHint(builder13);
		//tvMedInName.setTextColor(Color.parseColor("#ffffff"));
		
		// Click event on Register button
		
		//endregion
		
		mainScroll = (ScrollView) findViewById(R.id.scrollView1);
		
	}
	
	private boolean validateshopname() {
		if (txtshopName.getText().toString().trim().isEmpty()) {
			inputLayoutshopName.setError(getString(R.string.err_msg_shopname));
			requestFocus(txtshopName);
			return false;
		} else {
			inputLayoutshopName.setErrorEnabled(false);
			return true;
			
		}
		
	}
	
	private boolean validateownername() {
		if (txtownername.getText().toString().trim().isEmpty()) {
			inputLayoutownerName
					.setError(getString(R.string.err_msg_ownername));
			return false;
		} else {
			inputLayoutownerName.setErrorEnabled(false);
			return true;
			
		}
		
	}
	
	private boolean validatelandmark() {
		if (txtlandmark.getText().toString().trim().isEmpty()) {
			inputlayoutlandmark
					.setError(getString(R.string.err_msg_Landmark));
			requestFocus(txtownername);
			return false;
		} else {
			inputlayoutlandmark.setErrorEnabled(false);
			return true;
			
		}
		
	}
	
	private boolean validatecity() {
		if (txtCity.getText().toString().trim().isEmpty()) {
			inputLayoutcity.setError(getString(R.string.err_msg_city));
			requestFocus(txtCity);
			return false;
		} else {
			inputLayoutcity.setErrorEnabled(false);
			return true;
			
		}
		
	}
	
	private boolean validatestate() {
		if (txtState.getText().toString().trim().isEmpty()) {
			inputLayoutstate.setError(getString(R.string.err_msg_State));
			requestFocus(txtState);
			return false;
		} else {
			inputLayoutstate.setErrorEnabled(false);
			return true;
			
		}
		
	}
	
	private boolean validatedruglicnum() {
		if (txtDrug_licence_no.getText().toString().trim().isEmpty()) {
			inputLayoutdruglicnum
					.setError(getString(R.string.err_msg_druglicnum));
			requestFocus(txtDrug_licence_no);
			return false;
		} else {
			inputLayoutdruglicnum.setErrorEnabled(false);
			return true;
			
		}
		
	}
	
	private boolean validategstnum() {
		if (et_gstnumber.getText().toString().trim().isEmpty()) {
			
			if (Flag.equals("1")) {
				inputlayoutgstnumber
						.setError(getString(R.string.err_msg_gstnum));
				requestFocus(et_gstnumber);
				return false;
				
			} else {
				inputlayoutgstnumber.setErrorEnabled(false);
				return true;
			}
			
		} else {
			inputlayoutgstnumber.setErrorEnabled(false);
			return true;
			
		}
		
	}
	
	private boolean validateconfirmpassword() {
		
		if (!createpassword.getText().toString()
				.equals(etconfirmpassword.getText().toString())) {
			inputLayoutconfirm
					.setError(getString(R.string.err_msg_confirmpassword));
			requestFocus(etconfirmpassword);
			return false;
			
		} else {
			inputLayoutconfirm.setError("Password Match");
			return true;
			
		}
		
	}
	
	private boolean validateEmail() {
		String email = txtEmail.getText().toString().trim();
		
		if (email.isEmpty() || !isValidEmail(email)) {
			inputLayoutemail.setError(getString(R.string.err_msg_email));
			requestFocus(txtEmail);
			return false;
		} else {
			inputLayoutemail.setErrorEnabled(false);
			return true;
		}
		
	}
	
	private boolean validatepassword() {
		if (createpassword.getText().toString().trim().isEmpty()) {
			inputLayoutcreate.setError(getString(R.string.err_msg_Password));
			requestFocus(createpassword);
			return false;
		} else {
			inputLayoutcreate.setErrorEnabled(false);
			return true;
			
		}
		
	}
	
	private boolean validatemobno() {
		if (!(txtMobileno.length() >= 10)) {
			inputLayoutmobNum.setError(getString(R.string.err_msg_mob));
			requestFocus(txtMobileno);
			return false;
		} else {
			inputLayoutmobNum.setErrorEnabled(false);
			return true;
			
		}
		
	}
	
	private boolean validatealtmobno() {
		
		
		
		if ((etaltMobNumber.getText().toString().trim().isEmpty())) {
			
			if (Flag.equals("1")) {
				
				inputLayoutaltmobnum.setError(getString(R.string.err_msg_Altmob));
				requestFocus(etaltMobNumber);
				return false;
			}else {
				
				inputLayoutaltmobnum.setErrorEnabled(false);
				
				return true; }
			
			
		} else {
			
			String covered_pinarea = etaltMobNumber.getText().toString();
			String arr[] = covered_pinarea.split(",");
			complete_pin = arr[arr.length - 1];
			String remainder = covered_pinarea.substring(covered_pinarea.lastIndexOf(",") + 1);
			
			
			if (!(complete_pin.length() >= 6) || complete_pin.equals("")) {
				area = false;
				inputLayoutaltmobnum.setError(getString(R.string.err_msg_Altmob));
				requestFocus(etaltMobNumber);
				return false;
			} else {
				if (area == false && complete_pin.length() % 6 == 0) {
					area = true;
					if (remainder.isEmpty()) {
						etaltMobNumber.append("");
						if (covered_pinarea.length() > 0)
							etaltMobNumber.getText().delete(covered_pinarea.length() - 1, covered_pinarea.length());
					} else
						etaltMobNumber.append(",");
					
				} else if (complete_pin.length() > 6) {
					covered_pinarea = covered_pinarea.substring(0, covered_pinarea.length() - 1) + ","
							+ covered_pinarea.substring(covered_pinarea.length() - 1, covered_pinarea.length());
					etaltMobNumber.setText("");
					etaltMobNumber.setText(covered_pinarea);
					etaltMobNumber.setSelection(etaltMobNumber.getText().toString().length());
				}
				
				inputLayoutaltmobnum.setErrorEnabled(false);
				
				return true;
				
			}
			
		}}
	
	private boolean validatepin() {
		if (!(txtpin_no.length() >= 6) || txtpin_no.equals("")) {
			inputLayoutpin.setError(getString(R.string.err_msg_pin));
			
			requestFocus(txtpin_no);
			
			return false;
		}
		
		else {
			pin_no = txtpin_no.getText().toString();
			
			
			new LongOperation().execute(serverUrl, pin_no);
			
			inputLayoutpin.setErrorEnabled(false);
			return true;
			
		}
		
	}
	
	private void requestFocus(View view) {
		if (view.requestFocus()) {
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// unregisterReceiver(SyncServiceCompleatedBroadcastReceiver);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
	                        long id) {
		// TODO Auto-generated method stub
		
	}
	
	private class MyTextWatcher implements TextWatcher {
		
		private View view;
		int len = 0;
		
		private MyTextWatcher(View view) {
			this.view = view;
		}
		
		public void beforeTextChanged(CharSequence charSequence, int i, int i1,
		                              int i2) {
		}
		
		public void onTextChanged(CharSequence s, int i, int i1,
		                          int i2) {
			len = s.length();
			
		}
		
		
		public void afterTextChanged(Editable editable) {
			switch (view.getId()) {
				case R.id.etShopName:
					validateshopname();
					break;
				case R.id.etOwnerName:
					validateownername();
					break;
				case R.id.etMobNumber:
					validatemobno();
					break;
				case R.id.etaltMobNumber:
					validatealtmobno();
					break;
				case R.id.etEmailID:
					validateEmail();
					break;
				
				case R.id.etLandmark:
					validateshopname();
					break;
				case R.id.etPin:
					validatepin();
					break;
				
				case R.id.etcity:
					validatecity();
					break;
				case R.id.etState:
					validatestate();
					break;
				case R.id.etCreatepassword:
					validatepassword();
					break;
				
				case R.id.etconfirmpassword:
					validateconfirmpassword();
					break;
				case R.id.etDrugLicenceNo:
					validatedruglicnum();
					break;
				case R.id.etGstno:
					validategstnum();
					break;
			}
		}
	}
	
	private class LongOperation extends AsyncTask<String, Void, String> {
		
		String data = "";
		int sizeData = 0;
		private String Error = null;
		
		protected void onPreExecute() {
			// NOTE: You can call UI Element here.
			
			// Start Progress Dialog (Message)
			
		}
		
		@Override
		protected String doInBackground(String... params) {
			BufferedReader reader = null;
			String Content = "";
			JSONObject jsonResponse;
			URL url = null;
			
			
			try {
				
				
				url = new URL(params[0]);
				
				if (!params[1].equals(""))
					data += "&" + URLEncoder.encode("pin_code", "UTF-8")
							+ "=" + params[1].toString();
				
				
				// Send POST data request
				
				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter(
						conn.getOutputStream());
				wr.write(data);
				wr.flush();
				
				// Get the server response
				
				reader = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = null;
				
				// Read Server Response
				while ((line = reader.readLine()) != null) {
					// Append server response in string
					sb.append(line + "\n");
				}
				
				Content = sb.toString();
				
				try {
					jsonResponse = new JSONObject(Content);
					
					String Status = jsonResponse.getString("STATUS");

//					String message = jsonResponse.getString("errormsg");
					
					
					if (Status.equalsIgnoreCase("1")) {
						final String pin_city = jsonResponse.getString("CITY_NAME");
						final String pin_state = jsonResponse.getString("STATE_NAME");
						final String zip_id = jsonResponse.getString("ZIPID");
						
						
						edit.putString("pin_city", pin_city);
						edit.putString("pin_state", pin_state);
						edit.putString("zip_id", zip_id);
						
						
						edit.commit();
						
						
						((Activity) context).runOnUiThread(new Runnable() {
							public void run() {
								//	progress.dismiss();
								txtCity.setText(pin_city);
								txtState.setText(pin_state);
								
								
							}
							
						});
						
						
					} else {
						((Activity) context).runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(context, "Invalid Pin Code!", Toast.LENGTH_LONG).show();
								//  txtpin_no.setText("");
								
							}
							
						});
					}
					// }
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (Exception ex) {
				Error = ex.getMessage();
			} finally {
				try {
					
					reader.close();
				} catch (Exception ex) {
				}
			}
			
			/*****************************************************/
			return Content;
			
			
		}
		
		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
		}
	}
	
	
	private static boolean validGSTIN(String gstin) throws Exception {
		boolean isValidFormat = false;
		if (checkPattern(gstin, GSTINFORMAT_REGEX)) {
			isValidFormat = verifyCheckDigit(gstin);
		}
		return isValidFormat;
		
	}
	
	private static boolean verifyCheckDigit(String gstinWCheckDigit) throws Exception {
		Boolean isCDValid = false;
		String newGstninWCheckDigit = getGSTINWithCheckDigit(
				gstinWCheckDigit.substring(0, gstinWCheckDigit.length() - 1));
		
		if (gstinWCheckDigit.trim().equals(newGstninWCheckDigit)) {
			isCDValid = true;
		}
		return isCDValid;
	}
	
	public static boolean checkPattern(String inputval, String regxpatrn) {
		boolean result = false;
		if ((inputval.trim()).matches(regxpatrn)) {
			result = true;
		}
		return result;
	}
	
	public static String getGSTINWithCheckDigit(String gstinWOCheckDigit) throws Exception {
		int factor = 2;
		int sum = 0;
		int checkCodePoint = 0;
		char[] cpChars;
		char[] inputChars;
		
		try {
			if (gstinWOCheckDigit == null) {
				throw new Exception("GSTIN supplied for checkdigit calculation is null");
			}
			cpChars = GSTN_CODEPOINT_CHARS.toCharArray();
			inputChars = gstinWOCheckDigit.trim().toUpperCase().toCharArray();
			
			int mod = cpChars.length;
			for (int i = inputChars.length - 1; i >= 0; i--) {
				int codePoint = -1;
				for (int j = 0; j < cpChars.length; j++) {
					if (cpChars[j] == inputChars[i]) {
						codePoint = j;
					}
				}
				int digit = factor * codePoint;
				factor = (factor == 2) ? 1 : 2;
				digit = (digit / mod) + (digit % mod);
				sum += digit;
			}
			checkCodePoint = (mod - (sum % mod)) % mod;
			return gstinWOCheckDigit + cpChars[checkCodePoint];
		} finally {
			inputChars = null;
			cpChars = null;
		}
		
	}
	
	
	
	private void registerChemist(String drug_licence, String owner_name,
	                             String shop_name, String email, String mobileno,
	                             String landmark, String city, String pin_no,
	                             String state, String gstin, String function_name,
	                             String flag, String createpassword, String otpflag,
	                             String session_id, String regId, String imei,
	                             String strlatitude, String strlongitude, String altMobNumber,
	                             String zip_id) {
		
		
		ApiUtils.getInstance().setregistration(drug_licence, owner_name, shop_name,
				email, mobileno, landmark, city, pin_no, state, gstin,
				function_name, flag,
				createpassword, otpflag, session_id, regId, imei, strlatitude, strlongitude, altMobNumber,
				zip_id,pref.getString(getString(R.string.employee_id),""), new ApiUtils.ILoginCallback() {
					@Override
					public void onSuccessLogin(Login_Response response) {
						if (response != null) {
							if (response.getStatus().toString().equals("401")) {
								Toast.makeText(context, response.getMessage(),
										Toast.LENGTH_LONG).show();
							} else {
								if (response.getEmpStatus().contains("1")) {
								/*	String str_newchemist = response.getName().toString();
									String chem_id = response.getEmployeeId().toString();
									
									if (response.getFlag().contains("4")) {
										try {
											FragmentChemistList.chemistData.add(new ChemistInfo(str_newchemist, chem_id));
										} catch (Exception e) {
											e.printStackTrace();
										}
									} else if (response.getFlag().contains("1")) {
										try {
											FragmentStockistList.stockistData.add(new STOCKIESTINFO(str_newchemist, chem_id));
										} catch (Exception e) {
											e.printStackTrace();
										}
									}*/
									Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
									RegisterActivity.this.finish();
									/*
									final Dialog Sucess_dialog = new Dialog(context);
									// Include dialog.xml file
									Sucess_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
									Sucess_dialog.setContentView(R.layout.popup_success_layout);
									
									TextView tvmessage = (TextView) Sucess_dialog.findViewById(R.id.tvmessage);
									tvmessage.setText(response.getMessage().toString());
									TextView tv_ok = (TextView) Sucess_dialog.findViewById(R.id.tv_yes);
									Sucess_dialog.show();
									
									tv_ok.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											
											Sucess_dialog.dismiss();
											Intent i = new Intent(context, Dashboard.class);
											//i.putExtra("Chemist_detail", (Serializable) Chemist_fragment.Chemist_Information);
											//i.putExtra("Stockist_detail", (Serializable) Stockist_fragment.stockiestinfos);
											i.putParcelableArrayListExtra("Chemist_detail",
													(ArrayList<? extends Parcelable>) FragmentChemistList.chemistData);
											
											i.putParcelableArrayListExtra("Stockist_detail",
													(ArrayList<? extends Parcelable>) FragmentStockistList.stockistData);
											startActivity(i);
											finish();
											
										}
									});
									*/
								} else {
									Toast.makeText(context, response.getMessage().toString(),
											Toast.LENGTH_LONG).show();
								}
								
								
							}
						}
					}
					
					@Override
					public void onFailure(String failureMessage) {
						Toast.makeText(context, "Error Occurred ! ", Toast.LENGTH_LONG).show();
					}
				});
		
		
	}
	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		RegisterActivity.this.finish();
	}
}



