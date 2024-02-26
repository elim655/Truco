package Truco.com;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import okhttp3.ResponseBody;



import Truco.com.R;
import Truco.com.api.ApiClientFactory;
import Truco.com.api.LoginApi;
import Truco.com.model.Login;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button signupButton;
    private LoginApi loginApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize views and API client
        usernameEditText = findViewById(R.id.signupUsername);
        passwordEditText = findViewById(R.id.signupPassword);
        signupButton = findViewById(R.id.buttonSignup);
        loginApi = ApiClientFactory.getLoginApi();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get entered username and password
                String enteredUsername = usernameEditText.getText().toString().trim();
                String enteredPassword = passwordEditText.getText().toString().trim();

                // Validate input
                if (validateInput(enteredUsername, enteredPassword)) {
                    // Create a Login object with user credentials
                    Login newUser = new Login();
                    newUser.setUsername(enteredUsername);
                    newUser.setPassword(enteredPassword);

                    // Make the API call for signup
                    sendSignupRequest(newUser);
                } else {
                    // Show error message
                    Toast.makeText(SignupActivity.this, "Please fill out all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInput(String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
    }

    private void sendSignupRequest(Login newUser) {
        Call<ResponseBody> call = loginApi.PostSignupByPath(newUser.getUsername(), newUser.getPassword());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    // If the response is not successful or the body is null, handle as an error
                    if (!response.isSuccessful() || response.body() == null) {
                        throw new IOException("Unexpected response: " + response);
                    }

                    // This will give you the complete response as a String.
                    String rawResponse = response.body().string();
                    Log.d("SignupActivity", "Response: " + rawResponse);

                    // Now, handle your response as needed. For instance:
                    if ("User added".equals(rawResponse.trim())) {
                        Toast.makeText(SignupActivity.this, "Signed up successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, Main.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignupActivity.this, rawResponse, Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(SignupActivity.this, "Error reading response!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }}
