package anna.camila.madu.simon.shelfshare.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import anna.camila.madu.simon.shelfshare.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class CadastrarLivroActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imageCadastroLivro;
    private EditText editTextTitulo, editTextAutor, editTextSinopse;
    private Spinner generoSelect;
    private FloatingActionButton floatingActionButton3;
    private Button btnCadastre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_livro);

        imageCadastroLivro = findViewById(R.id.imgperfil);
        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextAutor = findViewById(R.id.editTextAutor);
        editTextSinopse = findViewById(R.id.editTextSinopse);
        generoSelect = findViewById(R.id.generoselect);
        floatingActionButton3 = findViewById(R.id.floatingActionButton4);
        btnCadastre = findViewById(R.id.btnCadastre);


        floatingActionButton3.setOnClickListener(v -> openGallery());


        String[] generos = {"Ficção", "Fantasia", "Romance", "Terror", "Suspense", "Aventura", "Comédia", "Drama", "História", "Biografia", "Ciência", "Poesia"};
        ArrayAdapter<String> generoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, generos);
        generoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        generoSelect.setAdapter(generoAdapter);


        btnCadastre.setOnClickListener(v -> {
            if (validateFields()) {

                Intent intent = new Intent(CadastrarLivroActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }


    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imageCadastroLivro.setImageBitmap(bitmap); // Coloca a imagem no ImageView
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateFields() {
        if (TextUtils.isEmpty(editTextTitulo.getText().toString())) {
            Toast.makeText(this, "Preencha o título", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(editTextAutor.getText().toString())) {
            Toast.makeText(this, "Preencha o autor", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(editTextSinopse.getText().toString())) {
            Toast.makeText(this, "Preencha a sinopse", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
