package com.efrei.dev1.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efrei.dev1.projet.model.ToDoList;
import com.efrei.dev1.projet.model.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<ToDoList> toDoLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.textView);
        Button add = (Button) findViewById(R.id.buttonAddList);


        // Récupérer les listes de to do list
        try {
            toDoLists = readJSONFile(this);
        } catch(Exception e)  {
            e.printStackTrace();
        }


        // S'il n'y a pas de liste
        if(toDoLists == null || toDoLists.size() == 0) {

            textView.setVisibility(View.VISIBLE);

        // Sinon afficher les listes
        } else {
            LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);

            for (ToDoList toDoList : toDoLists) {
                Button btn = new Button(this);
                btn.setText(toDoList.getTitle());
                btn.setId(toDoList.getId());

                layout.addView(btn);

                // Si on clique sur une to do list
                // Renvoyer vers la page de la to do list (avec la liste des tâches)
                btn.setOnClickListener(view -> {
                    Task[] tasks = toDoList.getTasks();
                    StringBuilder str_tasks = new StringBuilder("[\n");
                    for(int i = 0; i < tasks.length; i++) {
                        str_tasks.append("{\n" + "\"_id\":").append(tasks[i].getId()).append(",\n")
                                 .append("\"task\": \"").append(tasks[i].getTask()).append("\",\n")
                                 .append("\"checked\":").append(tasks[i].isChecked()).append("\n")
                                 .append("}");

                        if(i < toDoLists.size()) {
                            str_tasks.append(",\n");
                        }
                        else {
                            str_tasks.append("\n");
                        }
                    }
                    str_tasks.append("]");

                    // Envoyer toutes les informations nécessaires
                    Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                    intent.putExtra("title", toDoList.getTitle());
                    intent.putExtra("id", toDoList.getId());
                    intent.putExtra("tasks", str_tasks.toString());

                    finish();
                    startActivity(intent);
                });
            }
        }

        // Lorsqu'on appuie sur le bouton "Ajouter une liste"
        add.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // Aller sur la page de création de liste
        Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
        finish();
        startActivity(intent);
    }


    /**
     * Lire le fichier JSON servant de DataBase
     * @param context
     * @return les informations du fichier
     * @throws JSONException
     */
    public static List<ToDoList> readJSONFile(Context context) throws JSONException {

        // Lire le fichier JSON
        String jsonText = getJSONFromAssets(context);

        // Obtenir les listes de to do list
        Gson gson = new Gson();
        Type todolist = new TypeToken<List<ToDoList>>() { }.getType();

        return gson.fromJson(jsonText, todolist);
    }

    /**
     * Convertir en string le fichier data.json
     * @param context
     * @return le fichier convertie en string
     */
    private static String getJSONFromAssets(Context context) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open("data.json");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }
}