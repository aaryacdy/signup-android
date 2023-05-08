package np.edu.kathford.signupform;

/// @author Dinesh Maharjan
/// @email: dinesh.maharjan@fonepay.com
/// @created_at: 23/03/2023


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.math.BigDecimal;

import np.edu.kathford.signupform.model.Category;
import np.edu.kathford.signupform.retrofit.RetrofitService;
import np.edu.kathford.signupform.retrofit.api.CategoryApi;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private EditText fullNameEditText;
    private EditText addressEditText;
    private EditText mobileNumberEditText;

    private RadioGroup genderRadioGroup;

    private Button signupButton;

    private Spinner countrySpinner;
    private String TAG = SignupActivity.class.getCanonicalName();

    private String selectedGenderValue;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initViews();
        buttonClickListener();

        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton = (RadioButton) findViewById(checkedId);

                selectedGenderValue = radioButton.getText().toString().trim();

            }
        });

    }

    private void initViews() {
        fullNameEditText = findViewById(R.id.fullNameEditText);
        addressEditText = findViewById(R.id.addressEdiText);
        mobileNumberEditText = findViewById(R.id.mobileEditText);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        countrySpinner = findViewById(R.id.countrySpinner);
        signupButton = findViewById(R.id.singupButton);
    }


    private void buttonClickListener() {
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullNameEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();
                String mobileNumber = mobileNumberEditText.getText().toString().trim();

                String country = countrySpinner.getSelectedItem().toString();

                Log.i(TAG, country);

//                int checkedRadioButtonId =genderRadioGroup.getCheckedRadioButtonId();
//                RadioButton genderRadioButton =(RadioButton)findViewById(checkedRadioButtonId);
//                String genderValue =genderRadioButton.getText().toString().trim();

//                Log.i(TAG, selectedGenderValue);

                if (fullName != null && !fullName.isEmpty()) {
                    if (fullName.length() >= 3) {
                        if (address != null && !address.isEmpty()) {
                            if (mobileNumber != null && !mobileNumber.isEmpty()) {
//                                Toast.makeText(SignupActivity.this, "Form validation success", Toast.LENGTH_SHORT).show();

                                Log.i(TAG, fullName);

                                //let call the retrofit api here to save the data from third party
                                executeCategoryTest(fullName);

                            } else {
                                mobileNumberEditText.setError("Mobile number field is empty");
                            }
                        } else {
                            addressEditText.setError("Address field is empty.");
                        }

                    } else {
                        fullNameEditText.setError("Invalid full name");
                    }

                } else {
                    fullNameEditText.setError("Full name field is empty.");
                }

            }
        });
    }


    public void executeCategoryTest(String name) {
        RetrofitService retrofitService = new RetrofitService();

        System.out.println(" The Full name is : " + name);

        CategoryApi category = retrofitService.getRetrofit().create(CategoryApi.class);
//        Call call = (Call) category.saveOrUpdateCategory(name, price);
//        Call<ResponseBody> call = category.saveOrUpdateCategory(name, price);

        retrofit2.Call<ResponseBody> call = category.saveOrUpdateCategory(
                name
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("Success", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, "Category saved successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignupActivity.this, "Something is not right.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Success", t.getMessage());
                Toast.makeText(SignupActivity.this, "Failed to save data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
