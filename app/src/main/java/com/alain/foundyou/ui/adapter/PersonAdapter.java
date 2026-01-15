package com.alain.foundyou.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.alain.foundyou.R;
import com.alain.foundyou.data.network.model.Person;
import com.bumptech.glide.Glide;

public class PersonAdapter extends ListAdapter<Person, PersonAdapter.PersonViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(Person post);
    }

    private OnItemClickListener listener;


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public PersonAdapter() {
        super(DIFF_CALLBACK);
    }


    private static final DiffUtil.ItemCallback<Person> DIFF_CALLBACK = new DiffUtil.ItemCallback<Person>() {
        @Override
        public boolean areItemsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
            return java.util.Objects.equals(oldItem.getId(), newItem.getId());
        }
    };

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_person, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Person currentPerson = getItem(position);
        holder.bind(currentPerson, listener);
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        private final ImageView personAvatar;
        private final TextView personName;
        private final TextView personEmail;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            personAvatar = itemView.findViewById(R.id.image_person_avatar);
            personName = itemView.findViewById(R.id.text_person_name);
            personEmail = itemView.findViewById(R.id.text_person_email);
        }

        public void bind(Person person, OnItemClickListener listener) {
            personName.setText(person.getName().getFirst());
            personEmail.setText(person.getEmail());

            Glide.with(itemView.getContext())
                    .load(person.getPicture().getThumbnail())
                    .circleCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(personAvatar);
            itemView.setOnClickListener(v -> {
                if (listener != null && getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onItemClick(person);
                }
            });

        }
    }

}
