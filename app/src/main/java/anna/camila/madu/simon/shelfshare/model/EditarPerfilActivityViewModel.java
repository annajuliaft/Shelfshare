package anna.camila.madu.simon.shelfshare.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class EditarPerfilActivityViewModel extends AndroidViewModel {

    // guardamos o local da foto escolhida pelo usuário
    String currentPhotoPath = "";

    public EditarPerfilActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public String getCurrentPhotoPath() {
        return currentPhotoPath;
    }

    public void setCurrentPhotoPath(String currentPhotoPath) {
        this.currentPhotoPath = currentPhotoPath;
    }
}
