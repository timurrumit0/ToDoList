package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {
    private EditText editTextNote;
    private RadioButton radioButtonLow;
    private RadioButton radioButtonMedium;
    private RadioButton radioButtonHigh;
    private Button buttonSave;
    private NoteDatabase noteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        noteDatabase = NoteDatabase.getInstance(getApplication());
        initViews();
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }
    private void initViews(){
        editTextNote=findViewById(R.id.editTextNote);
        radioButtonLow = findViewById(R.id.radioButtonLow);
        radioButtonMedium = findViewById(R.id.radioButtonMedium);
        radioButtonHigh = findViewById(R.id.radioButtonHigh);
        buttonSave =findViewById(R.id.buttonSave);
    }
    private void saveNote(){
        String text = editTextNote.getText().toString().trim();
        int priority=getPriority();

        Note note = new Note(0, text, priority);
        noteDatabase.notesDao().addNote(note);
        finish();
    }
    private int getPriority() {
        int priority = 2;
        if (radioButtonLow.isChecked()) {
            priority = 0;
        } else if (radioButtonMedium.isChecked()) {
            priority = 1;
        }
        return priority;
    }
    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, AddNoteActivity.class);
        return intent;
    }
}
