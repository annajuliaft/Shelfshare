package anna.camila.madu.simon.shelfshare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import anna.camila.madu.simon.shelfshare.R;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnCadastrar = findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextNome= findViewById(R.id.editTextNome);
                String nome = editTextNome.getText().toString();
                if(nome.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "O campo de nome está vazio", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText editTextEmail = findViewById(R.id.editTextEmail);
                String email = editTextEmail.getText().toString();
                if(email.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "O campo de e-mail está vazio", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText editTextDate = findViewById(R.id.editTextDate);
                String date = editTextDate.getText().toString();
                if(date.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "O campo de data está vazio", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText editTextTel = findViewById(R.id.editTextTel);
                String tel = editTextTel.getText().toString();
                if(tel.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "O campo de telefone está vazio", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText editTextSenha = findViewById(R.id.editTextSenha);
                String senha = editTextSenha.getText().toString();
                if(senha.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "O campo de senha está vazio", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText editTextConfSenha = findViewById(R.id.editTextConfSenha);
                String confsenha = editTextConfSenha.getText().toString();
                if(confsenha.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "O campo de confirmar senha está vazio", Toast.LENGTH_LONG).show();
                    return;
                }


                Intent i = new Intent(CadastroActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }
}