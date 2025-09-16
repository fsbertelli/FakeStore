package com.felipe.bertelli.fakestore.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.felipe.bertelli.fakestore.R;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.content.Intent;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText editLogin = findViewById(R.id.editTextLogin);
        EditText editSenha = findViewById(R.id.editTextSenha);
        Button btnEntrar = findViewById(R.id.buttonEntrar);


        btnEntrar.setOnClickListener(v -> new Thread(() -> {
            try {
                URL url = new URL("https://fakestoreapi.com/auth/login");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                JSONObject json = new JSONObject();
                json.put("username", editLogin.getText().toString());
                json.put("password", editSenha.getText().toString());

                OutputStream os = conn.getOutputStream();
                os.write(json.toString().getBytes());
                os.close();

                InputStream is = conn.getInputStream();
                byte[] buffer = new byte[1024];
                int len = is.read(buffer);
                is.close();

                String resposta = new String(buffer, 0, len);
                runOnUiThread(() -> {
                    Toast.makeText(this, resposta, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                });
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Erro", Toast.LENGTH_LONG).show());
            }
        }).start());
    }
}
