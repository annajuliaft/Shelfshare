package anna.camila.madu.simon.shelfshare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import anna.camila.madu.simon.shelfshare.R;
import anna.camila.madu.simon.shelfshare.adapter.BookAdapter;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_perfil);

        // Configurar a View para EdgeToEdge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // RecyclerView configuração
        RecyclerView recyclerView = findViewById(R.id.recycler_view_books);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dados fake
        List<String> fakeBooks = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            fakeBooks.add("Livro " + i);
        }

        // Configurar o Adapter
        BookAdapter adapter = new BookAdapter(fakeBooks);
        recyclerView.setAdapter(adapter);
    }
    Button add_book_button = findViewById(R.id.add_book_button);
        add_book_button.setOnClickListener(new View.OnClickListener() {
        Intent i = new Intent(add_book_button.this, CadastrarLivroActivity.class);
        startActivity(i);
    }
    Button add_book_button = findViewById(R.id.add_book_button);
        add_book_button.setOnClickListener(new View.OnClickListener() {
        Intent i = new Intent(add_book_button.this, EditarPerfilActivity.class);
        startActivity(i);
    }
}
