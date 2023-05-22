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

public class TaskDialog extends DialogFragment {
    private AddnewTaskCallBack callBack;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callBack = (AddnewTaskCallBack) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_task,null,false);

        TextInputEditText editText = view.findViewById(R.id.et_dialog_save);
        TextInputLayout inputLayout = view.findViewById(R.id.etl_dialog_save);
        View savebtn = view.findViewById(R.id.btn_dialog_save);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.length()>0){
                    Task task = new Task();
                    task.setTitle(editText.getText().toString());
                    task.setisCompleted(false);
                    callBack.onNewTask(task);
                    dismiss();
                }else{
                    inputLayout.setError("عنوان نباید خالی باشد.");
                }
            }
        });


        builder.setView(view);
        return builder.create();
    }

    public interface AddnewTaskCallBack{
        void onNewTask(Task task);
        void onItemLongPressed(Task task);

    }
}
