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
import anna.camila.madu.simon.shelfshare.util.Config;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnEntrar = findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtUser = findViewById(R.id.edtUser);
                String user = edtUser.getText().toString();
                if(user.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "O campo de usuário está vazio", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText edtSenha = findViewById(R.id.edtSenha);
                String senha = edtSenha.getText().toString();
                if(senha.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "O campo de senha está vazio", Toast.LENGTH_LONG).show();
                    return;
                }

                Config.setLogin(LoginActivity.this, user);
                Config.setPassword(LoginActivity.this, senha);

                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
        Button btnCadastre = findViewById(R.id.btnCadastrar);
        btnCadastre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(i);
            }
        });
    }
}