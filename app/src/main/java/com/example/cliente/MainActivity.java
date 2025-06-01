package com.example.cliente;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNome;
    private EditText etTelefone;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); 

        
        etNome = findViewById(R.id.etNome);
        etTelefone = findViewById(R.id.etTelefone);
        btnCadastrar = findViewById(R.id.btnCadastrar);

    
        btnCadastrar.setOnClickListener(v -> {
            String nome = etNome.getText().toString().trim();
            String telefone = etTelefone.getText().toString().trim();

            if (nome.isEmpty() || telefone.isEmpty()) {
                Toast.makeText(MainActivity.this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                intent.putExtra("nome_cliente", nome);
                startActivity(intent);
                finish();
            }
        });
    }
}
