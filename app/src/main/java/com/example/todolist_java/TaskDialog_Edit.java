package com.example.todolist_java;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.EventListener;

public class TaskDialog_Edit extends DialogFragment {
    private EditTaskCallback callBack;
    private Task task;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callBack = (EditTaskCallback) context;
        task = getArguments().getParcelable("task");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_task_edit,null,false);

        final TextInputEditText editText = view.findViewById(R.id.et_dialogEdit_save);
        editText.setText(task.getTitle());
        final TextInputLayout inputLayout = view.findViewById(R.id.etl_dialogEdit_save);
        View savebtn = view.findViewById(R.id.btn_dialogEdit_save);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.length()>0){
                    task.setTitle(editText.getText().toString());;
                    callBack.onEditTask(task);
                    dismiss();
                }else{
                    inputLayout.setError("عنوان نباید خالی باشد.");
                }
            }
        });

        builder.setView(view);
        return builder.create();
    }

    public interface EditTaskCallback{
        void onEditTask(Task task);
    }
}
