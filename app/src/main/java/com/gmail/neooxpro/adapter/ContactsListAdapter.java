package com.gmail.neooxpro.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.neooxpro.R;
import com.gmail.neooxpro.model.Contact;

import java.util.ArrayList;

public class ContactsListAdapter extends ListAdapter<Contact, ContactsListAdapter.ContactsViewHolder> {

    private ItemClickListener itemClickListener;

    public ContactsListAdapter() {
        super(DIFF_CALLBACK);

    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_small_info, parent, false);
        return new ContactsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public void submitItems(ArrayList<Contact> contacts) {
        submitList(contacts);

    }


    public static final DiffUtil.ItemCallback<Contact> DIFF_CALLBACK = new DiffUtil.ItemCallback<Contact>() {
        @Override
        public boolean areItemsTheSame(@NonNull Contact oldContact, @NonNull Contact newContact) {
            return oldContact.getId().equals(newContact.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Contact oldContact, @NonNull Contact newContact) {
            return oldContact.getName().equals(newContact.getName())
                    && oldContact.getPhone().equals(newContact.getPhone());
        }
    };

    class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView name;
        private final TextView contactPhone;

        ContactsViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.contactName);
            contactPhone = itemView.findViewById(R.id.contactPhone);
            itemView.setOnClickListener(this);
        }

        void bind(final Contact contact) {
            name.setText(contact.getName());
            contactPhone.setText(contact.getPhone());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (itemClickListener != null && position != RecyclerView.NO_POSITION) {
                Contact contact = getItem(position);
                itemClickListener.onItemClicked(contact.getId());
            }
        }
    }

    public interface ItemClickListener {
        void onItemClicked(String contactId);
    }

    public void setOnClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }
}
