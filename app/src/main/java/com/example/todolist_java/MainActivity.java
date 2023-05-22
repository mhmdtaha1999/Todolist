package com.example.todolist_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskDialog.AddnewTaskCallBack , TaskAdapter.TaskItemEventListener, TaskDialog_Edit.EditTaskCallback {
    private  SqliteHelper sqliteHelper;
    private TaskAdapter taskAdapter = new TaskAdapter(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = findViewById(R.id.rv_main_task);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(taskAdapter);
        EditText searchEdt = findViewById(R.id.edt_main_search);
        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    List<Task> tasks = sqliteHelper.searchInTasks(s.toString());
                }else {
                    List<Task> tasks = sqliteHelper.getTasks();
                    taskAdapter.setTasks(tasks);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sqliteHelper = new SqliteHelper(this);
        List<Task> tasks = sqliteHelper.getTasks();
        taskAdapter.addItems(tasks);
        View clearTaskButton = findViewById(R.id.iv_main_clearAllTasks);
        View addNewTaskFab = findViewById(R.id.fab_main_newTask);

        clearTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqliteHelper.clearAllTasks();
                taskAdapter.clearItems();
            }
        });
        addNewTaskFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDialog taskDialog = new TaskDialog();
                taskDialog.show(getSupportFragmentManager(),null);

            }
        });


    }

    @Override
    public void onNewTask(Task task) {
        long taskId = sqliteHelper.addTask(task);
        if(taskId != -1){
            task.setId(taskId);
            taskAdapter.addItem(task);
        }

    }

    @Override
    public void onItemLongPressed(Task task) {
        TaskDialog_Edit taskDialog_edit = new TaskDialog_Edit();
        Bundle bundle = new Bundle();
        bundle.putParcelable("task",task);
        taskDialog_edit.setArguments(bundle);
        taskDialog_edit.show(getSupportFragmentManager(),null);
    }

    @Override
    public void onItemCheckedChange(Task task) {
        sqliteHelper.updateTask(task);
    }


    @Override
    public void OnDeleteButtonClick(Task task) {
        int result = sqliteHelper.deleteTask(task);
        if(result>0){
            taskAdapter.deleteItem(task);
        }
    }

    @Override
    public void onEditTask(Task task) {
        int result = sqliteHelper.updateTask(task);
        if(result>0){
            taskAdapter.updateItem(task);
        }
    }
}