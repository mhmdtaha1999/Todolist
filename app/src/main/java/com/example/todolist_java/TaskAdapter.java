package com.example.todolist_java;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> tasks = new ArrayList<>();
    private TaskItemEventListener eventListener;

    public TaskAdapter(TaskItemEventListener eventListener){

        this.eventListener = eventListener;
    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TaskViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        taskViewHolder.bindTask(tasks.get(i));
    }

    public void addItem(Task task){
        tasks.add(0,task);
        notifyItemInserted(0);
    }

    public void deleteItem(Task task){
        for(int i = 0 ;i<tasks.size();i++){
            if(tasks.get(i).getId() == task.getId()){
                tasks.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    public void updateItem(Task task){
        for (int i = 0 ; i < tasks.size();i++){
            if(task.getId() == tasks.get(i).getId()){
                tasks.set(i,task);
                notifyItemChanged(i);
            }
        }
    }

    public void setTasks(List<Task> tasks){
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public void clearItems(){
        this.tasks.clear();
        notifyDataSetChanged();
    }

    public void addItems(List<Task> tasks){
        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tasks.size() ;
    }


    public  class TaskViewHolder extends  RecyclerView.ViewHolder{
        private CheckBox checkBox;
        private View deleteButton;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox_task);
            deleteButton = itemView.findViewById(R.id.img_task_delete);
        }

        public void bindTask(Task task){
            checkBox.setOnCheckedChangeListener(null);
            checkBox.setChecked(task.getisCompleted());
            checkBox.setText(task.getTitle());
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventListener.OnDeleteButtonClick(task);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    eventListener.onItemLongPressed(task);
                    return false;
                }
            });

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    task.setisCompleted(isChecked);
                    eventListener.onItemCheckedChange(task);
                }
            });
        }
    }

    public interface TaskItemEventListener{
        void OnDeleteButtonClick(Task task);
        void onItemLongPressed(Task task);
        void onItemCheckedChange(Task task);
    }
}
