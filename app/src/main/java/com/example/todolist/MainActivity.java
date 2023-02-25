package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton buttonAddNote;
    private NotesAdapter notesAdapter;
    private RecyclerView recyclerViewNotes;
    private NoteDatabase noteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteDatabase = NoteDatabase.getInstance(getApplication());

        initViews();
        notesAdapter = new NotesAdapter();
        notesAdapter.setOnNoteClickListener(new NotesAdapter.onNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                /*database.remove(note.getId());
                showNotes();*/
            }
        });
        recyclerViewNotes.setAdapter(notesAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Note note = notesAdapter.getNotes().get(position);
                noteDatabase.notesDao().remove(note.getId());
                showNotes();
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddNoteActivity.newIntent(MainActivity.this);
                startActivity(intent);

            }
        });
    }
    private void initViews(){
        buttonAddNote = findViewById(R.id.buttonAddNote);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showNotes();
    }
    public void showNotes(){
        notesAdapter.setNotes(noteDatabase.notesDao().getNotes());
    }
}