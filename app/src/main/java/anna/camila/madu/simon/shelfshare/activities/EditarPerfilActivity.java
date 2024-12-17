package anna.camila.madu.simon.shelfshare.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import anna.camila.madu.simon.shelfshare.R;
import anna.camila.madu.simon.shelfshare.model.EditarPerfilActivityViewModel;

public class EditarPerfilActivity extends AppCompatActivity {

    static int RESULT_TAKE_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_perfil);

        // Configuração de padding para acomodar os insets do sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configuração do botão de editar perfil
        Button btnedit = findViewById(R.id.btnedit);
        btnedit.setOnClickListener(v -> {
            EditText etNome = findViewById(R.id.etNome);
            String nome = etNome.getText().toString();
            if (nome.isEmpty()) {
                Toast.makeText(EditarPerfilActivity.this, "O campo de nome está vazio", Toast.LENGTH_LONG).show();
                return;
            }

            EditText etEmail = findViewById(R.id.etEmail);
            String email = etEmail.getText().toString();
            if (email.isEmpty()) {
                Toast.makeText(EditarPerfilActivity.this, "O campo de email está vazio", Toast.LENGTH_LONG).show();
                return;
            }

            EditText etSenha = findViewById(R.id.etSenha);
            String senha = etSenha.getText().toString();
            if (senha.isEmpty()) {
                Toast.makeText(EditarPerfilActivity.this, "O campo de senha está vazio", Toast.LENGTH_LONG).show();
                return;
            }

            EditText etConfirmar = findViewById(R.id.etConfirmar);
            String confirmar = etConfirmar.getText().toString();
            if (confirmar.isEmpty()) {
                Toast.makeText(EditarPerfilActivity.this, "O campo de confirmar senha está vazio", Toast.LENGTH_LONG).show();
                return;
            }

            Intent i = new Intent(EditarPerfilActivity.this, PerfilActivity.class);
            startActivity(i);
        });

        // Configuração do botão de upload de imagem
        Button btnUpload = findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(v -> dispatchGalleryOrCameraIntent());
    }

    /**
     * Método para abrir um menu de escolha entre câmera e galeria.
     */
    private void dispatchGalleryOrCameraIntent() {
        // Criar o arquivo que armazenará a imagem
        File f;
        try {
            f = createImageFile();
        } catch (IOException e) {
            Toast.makeText(EditarPerfilActivity.this, "Não foi possível criar o arquivo", Toast.LENGTH_LONG).show();
            return;
        }

        // Configuração dos intents para câmera e galeria
        if (f != null) {
            EditarPerfilActivityViewModel editarPerfilActivityViewModel =
                    new ViewModelProvider(EditarPerfilActivity.this).get(EditarPerfilActivityViewModel.class);
            editarPerfilActivityViewModel.setCurrentPhotoPath(f.getAbsolutePath());

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri fUri = FileProvider.getUriForFile(
                    EditarPerfilActivity.this,
                    "anna.camila.madu.simon.shelfshare.fileprovider",
                    f
            );
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fUri);

            Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            galleryIntent.setType("image/*");

            Intent chooserIntent = Intent.createChooser(galleryIntent, "Pegar imagem de...");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

            startActivityForResult(chooserIntent, RESULT_TAKE_PICTURE);
        }
    }

    /**
     * Método que cria um arquivo temporário para salvar a imagem.
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    /**
     * Método chamado após o usuário selecionar ou capturar a imagem.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_TAKE_PICTURE) {
            EditarPerfilActivityViewModel editarPerfilActivityViewModel =
                    new ViewModelProvider(EditarPerfilActivity.this).get(EditarPerfilActivityViewModel.class);
            String currentPhotoPath = editarPerfilActivityViewModel.getCurrentPhotoPath();

            if (resultCode == Activity.RESULT_OK) {
                ImageView imvPhoto = findViewById(R.id.imgperfil);

                Uri selectedPhoto = data != null ? data.getData() : null;
                if (selectedPhoto != null) {
                    try {
                        Bitmap bitmap = anna.camila.madu.simon.shelfshare.util.Util.getBitmap(this, selectedPhoto);
                        anna.camila.madu.simon.shelfshare.util.Util.saveImage(bitmap, currentPhotoPath);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return;
                    }
                }

                Bitmap bitmap = anna.camila.madu.simon.shelfshare.util.Util.getBitmap(
                        currentPhotoPath,
                        imvPhoto.getWidth(),
                        imvPhoto.getHeight()
                );
                imvPhoto.setImageBitmap(bitmap);
            } else {
                new File(currentPhotoPath).delete();
                editarPerfilActivityViewModel.setCurrentPhotoPath("");
            }
        }
    }
}
