package com.efrei.dev1.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efrei.dev1.projet.model.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        TextView title = (TextView) findViewById(R.id.listTitle);
        Button update = (Button) findViewById(R.id.buttonUpdateList);

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));

        // Obtenir la liste de tâches
        Gson gson = new Gson();
        Type todolist = new TypeToken<List<Task>>() { }.getType();

        List<Task> tasks = gson.fromJson(intent.getStringExtra("tasks"), todolist);

        // S'il y a des tâches
        if(!(tasks == null || tasks.size() <= 0)) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayoutTasks);

            // Afficher les tâches
            for (Task task : tasks) {
                CheckBox checkbox = new CheckBox(this);
                checkbox.setText(task.getTask());
                checkbox.setChecked(task.isChecked());

                layout.addView(checkbox);
            }
        }

        // Lorsqu'on clique sur le bouton pour modifier
        // Renvoyer vers la page de creation avec les informations de la to do list
        update.setOnClickListener(view -> {
            Intent com = new Intent(getApplicationContext(), CreateActivity.class);

            com.putExtra("title", intent.getStringExtra("title"));
            com.putExtra("id", intent.getIntExtra("id", 0));
            com.putExtra("tasks", intent.getStringExtra("tasks"));
            com.putExtra("update", true);

            finish();
            startActivity(com);
        });

    }

    @Override
    public void onBackPressed() {
        // Retourner sur la page d'accueil
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        finish();
        startActivity(intent);
    }
}