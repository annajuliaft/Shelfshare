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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnedit = findViewById(R.id.btnedit);
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etNome = findViewById(R.id.etNome);
                String nome = etNome.getText().toString();
                if(nome.isEmpty()) {
                    Toast.makeText(EditarPerfilActivity.this, "O campo de nome está vazio", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText etEmail = findViewById(R.id.etEmail);
                String email = etEmail.getText().toString();
                if(email.isEmpty()) {
                    Toast.makeText(EditarPerfilActivity.this, "O campo de email está vazio", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText etSenha = findViewById(R.id.etSenha);
                String senha = etEmail.getText().toString();
                if(senha.isEmpty()) {
                    Toast.makeText(EditarPerfilActivity.this, "O campo de senha está vazio", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText etConfirmar = findViewById(R.id.etConfirmar);
                String confirmar = etEmail.getText().toString();
                if(confirmar.isEmpty()) {
                    Toast.makeText(EditarPerfilActivity.this, "O campo de confirmar senha está vazio", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent(EditarPerfilActivity.this, PerfilActivity.class);
                startActivity(i);
            }
        });

        FloatingActionButton btnUpload = findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchGalleryOrCameraIntent();
            }
        });



    }


    /**
     * Esse método exibe um pequeno menu de opções que permite que o usuário escolha de onde virá
     * a imagem do produto: câmera ou galeria.
     */
    private void dispatchGalleryOrCameraIntent() {

        // Primeiro, criamos o arquivo que irá guardar a imagem.
        File f = null;
        try {
            f = createImageFile();
        } catch (IOException e) {
            Toast.makeText(EditarPerfilActivity.this, "Não foi possível criar o arquivo", Toast.LENGTH_LONG).show();
            return;
        }

        // Se o arquivo foi criado com sucesso...
        if(f != null) {

            // setamos o endereço do arquivo criado dentro do ViewModel
            EditarPerfilActivityViewModel editarPerfilActivityViewModel = new ViewModelProvider(this).get(EditarPerfilActivityViewModel.class);
            editarPerfilActivityViewModel.setCurrentPhotoPath(f.getAbsolutePath());

            // Criamos e configuramos o INTENT que dispara a câmera
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri fUri = FileProvider.getUriForFile(EditarPerfilActivity.this, "anna.camila.madu.simon.shelfshare.fileprovider", f);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fUri);

            // Criamos e configuramos o INTENT que dispara a escolha de imagem via galeria
            Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            galleryIntent.setType("image/*");

            // Criamos o INTENT que gera o menu de escolha. Esse INTENT contém os dois INTENTS
            // anteriores e permite que o usuário esolha entre câmera e galeria de fotos.
            Intent chooserIntent = Intent.createChooser(galleryIntent, "Pegar imagem de...");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { cameraIntent });
            startActivityForResult(chooserIntent, RESULT_TAKE_PICTURE);
        }
        else {
            Toast.makeText(EditarPerfilActivity.this, "Não foi possível criar o arquivo", Toast.LENGTH_LONG).show();
            return;
        }

    }

    /**
     * Método que cria um arquivo vazio, onde será guardada a imagem escolhida. O arquivo é
     * criado dentro do espaço interno da app, no diretório PICTURES. O nome do arquivo usa a
     * data e hora do momento da criação do arquivo. Isso garante que sempre que esse método for
     * chamado, não haverá risco de sobrescrever o arquivo anterior.
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File f = File.createTempFile(imageFileName, ".jpg", storageDir);
        return f;
    }

    /**
     * Esse método é chamado depois que o usuário escolhe a foto
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_TAKE_PICTURE) {

            // Pegamos o endereço do arquivo vazio que foi criado para guardar a foto escolhida
            EditarPerfilActivityViewModel editarPerfilActivityViewModel = new ViewModelProvider(this).get(EditarPerfilActivityViewModel.class);
            String currentPhotoPath = editarPerfilActivityViewModel.getCurrentPhotoPath();

            // Se a foto foi efetivamente escolhida pelo usuário...
            if(resultCode == Activity.RESULT_OK) {
                ImageView imvPhoto = findViewById(R.id.imgperfil);

                // se o usuário escolheu a câmera, então quando esse método é chamado, a foto tirada
                // já está salva dentro do arquivo currentPhotoPath. Entretanto, se o usuário
                // escolheu uma foto da galeria, temos que obter o URI da foto escolhida:
                Uri selectedPhoto = data.getData();
                if(selectedPhoto != null) {
                    try {
                        // carregamos a foto escolhida em um bitmap
                        Bitmap bitmap = anna.camila.madu.simon.shelfshare.util.Util.getBitmap(this, selectedPhoto);
                        // salvamos o bitmao dentro do arquivo currentPhotoPath
                        anna.camila.madu.simon.shelfshare.util.Util.saveImage(bitmap, currentPhotoPath);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return;
                    }
                }

                // Carregamos a foto salva em currentPhotoPath com a escala correta e setamos no ImageView
                Bitmap bitmap = anna.camila.madu.simon.shelfshare.util.Util.getBitmap(currentPhotoPath, imvPhoto.getWidth(), imvPhoto.getHeight());
                imvPhoto.setImageBitmap(bitmap);
            }
            else {
                // Se a imagem não foi escolhida, deletamos o arquivo que foi criado para guardá-la
                File f = new File(currentPhotoPath);
                f.delete();
                editarPerfilActivityViewModel.setCurrentPhotoPath("");
            }
        }
    }

}