package com.efrei.dev1.projet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.efrei.dev1.projet.model.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button add = (Button) findViewById(R.id.buttonAddTaskCreate);
        Button save = (Button) findViewById(R.id.buttonSaveCreate);

        // Si on souhaite modifier une liste existante
        Intent intent = getIntent();
        if(intent.getBooleanExtra("update", false) == true) {

            // Obtenir la liste de tâches
            Gson gson = new Gson();
            Type todolist = new TypeToken<List<Task>>() { }.getType();

            List<Task> list_tasks = gson.fromJson(intent.getStringExtra("tasks"), todolist);

            // S'il y a des tâches
            if(!(list_tasks == null || list_tasks.size() <= 0)) {

                // Afficher le titre de la to do list
                EditText title = (EditText) findViewById(R.id.editTextTitleCreate);
                title.setText(intent.getStringExtra("title"));

                LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayoutTasksCreate);

                // Afficher les tâches
                for (Task task : list_tasks) {

                    LinearLayout task_layout = new LinearLayout(this);
                    task_layout.setOrientation(LinearLayout.HORIZONTAL);


                    // Checkbox coché ou non
                    CheckBox checkbox = new CheckBox(CreateActivity.this);
                    checkbox.setChecked(task.isChecked());
                    checkbox.setClickable(false);


                    // EditText avec le texte de la tâche pour pouvoir la modifier
                    EditText edit_task = new EditText(CreateActivity.this);
                    int width = (int)convertDPtoPX(255);
                    int height = (int)convertDPtoPX(48);
                    edit_task.setLayoutParams(new ViewGroup.LayoutParams(width, height));
                    edit_task.setText(task.getTask());


                    // Bouton de suppression
                    Button delete_task = new Button(CreateActivity.this);
                    int button_params = (int)convertDPtoPX(40);
                    delete_task.setLayoutParams(new ViewGroup.LayoutParams(button_params, button_params));
                    delete_task.setText("X");
                    delete_task.setTextColor(getResources().getColor(R.color.coral));
                    delete_task.setBackgroundTintList(getResources().getColorStateList(R.color.white));


                    task_layout.addView(checkbox);
                    task_layout.addView(edit_task);
                    task_layout.addView(delete_task);
                    layout.addView(task_layout);

                    // Supprimer la tâche si on clique sur le bouton de suppression
                    delete_task.setOnClickListener(view_delete -> layout.removeView(task_layout));

                }
            }
        }

        // Lorsqu'on clique sur le bouton +
        // Ajouter la tâche saisie dans la liste
        add.setOnClickListener(view_add -> {

            EditText task = (EditText) findViewById(R.id.editTextTaskCreate);
            String str_task = task.getText().toString();
            str_task = str_task.trim();

            // Si une tâche a été renseignée
            if (!str_task.matches("")) {

                // Ajouter la nouvelle tâche
                LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayoutTasksCreate);

                LinearLayout task_layout = new LinearLayout(this);
                task_layout.setOrientation(LinearLayout.HORIZONTAL);


                // Checkbox non cochable
                CheckBox checkbox = new CheckBox(CreateActivity.this);
                checkbox.setClickable(false);


                // EditText avec le texte de la tâche pour pouvoir la modifier
                EditText edit_task = new EditText(CreateActivity.this);
                int width = (int)convertDPtoPX(255);
                int height = (int)convertDPtoPX(48);
                edit_task.setLayoutParams(new ViewGroup.LayoutParams(width, height));
                edit_task.setText(str_task);


                // Bouton de suppression
                Button delete_task = new Button(CreateActivity.this);
                int button_params = (int)convertDPtoPX(40);
                delete_task.setLayoutParams(new ViewGroup.LayoutParams(button_params, button_params));
                delete_task.setText("X");
                delete_task.setTextColor(getResources().getColor(R.color.coral));
                delete_task.setBackgroundTintList(getResources().getColorStateList(R.color.white));


                task_layout.addView(checkbox);
                task_layout.addView(edit_task);
                task_layout.addView(delete_task);
                layout.addView(task_layout);


                // Supprimer la tâche si on clique sur le bouton de suppression
                delete_task.setOnClickListener(view_delete -> layout.removeView(task_layout));

                // Rénitialiser l'EditText
                task.setText("");
            }
        });

        // Lorsqu'on clique sur le bouton "Enregistrer"
        save.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        // Cette fonction devrait enregistrer dans une base de données
        // et retourner sur la page d'accueil
        // mais comme je n'ai pas de base de données
        // on retourne juste en arrière
        Intent com = getIntent();

        // Si on modifie une liste
        if(com.getBooleanExtra("update", false) == true) {
            // Retourner sur la page de la to do list
            Intent intent = new Intent(getApplicationContext(), ListActivity.class);

            intent.putExtra("title", com.getStringExtra("title"));
            intent.putExtra("id", com.getIntExtra("id", 0));
            intent.putExtra("tasks", com.getStringExtra("tasks"));

            finish();
            startActivity(intent);
        } else {
            // Retourner sur la page d'accueil
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            finish();
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        Intent com = getIntent();

        // Si on modifie une liste
        if(com.getBooleanExtra("update", false) == true) {

            // Retourner sur la page de la to do list
            Intent intent = new Intent(getApplicationContext(), ListActivity.class);

            intent.putExtra("title", com.getStringExtra("title"));
            intent.putExtra("id", com.getIntExtra("id", 0));
            intent.putExtra("tasks", com.getStringExtra("tasks"));

            finish();
            startActivity(intent);

        } else {

            // Retourner sur la page d'accueil
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            finish();
            startActivity(intent);

        }
    }

    /**
     * Convertir une valeur dp en px
     * @param dp
     * @return la valeur convertie en px
     */
    private float convertDPtoPX(int dp) {
        return dp * this.getResources().getDisplayMetrics().density;
    }
}