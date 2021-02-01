package com.ar.contactUtils.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.ar.contactUtils.R;
import com.ar.contactUtils.databinding.ItemContactListBinding;
import com.ar.contactUtils.model.Contact;
import com.ar.contactUtils.utils.SharedPreference;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<Contact> contacts;
    private ItemContactListBinding binding;
    SharedPreference sharedPreference;

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Context context;

    public ContactAdapter(Context context) {
        this.context = context;
    }

    private onItemClickListener onItemClickListener;

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_contact_list,
                parent, false);
        sharedPreference = new SharedPreference();
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.setContact(contacts.get(position));
        if (checkContactItem(contacts.get(position))) {
            holder.binding.checkBox.setChecked(true);
        } else {
            holder.binding.checkBox.setChecked(false);

        }
    }

    @Override
    public int getItemCount() {
        return contacts != null ? contacts.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemContactListBinding binding;

        public ViewHolder(@NonNull ItemContactListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.checkBox.setOnClickListener(view -> {
                if(binding.checkBox.isChecked()) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onCheckClicked(contacts.get(getAdapterPosition()), getAdapterPosition(), true);
                    }
                }
                else{
                    if (onItemClickListener != null) {
                        onItemClickListener.onCheckClicked(contacts.get(getAdapterPosition()),getAdapterPosition(),false);
                    }
                }
            });


        }
    }

    public boolean checkContactItem(Contact checkContact) {
        boolean check = false;
        List<Contact> contacts = sharedPreference.getContacts(context);
        if (contacts != null) {
            for (Contact contact : contacts) {
                if (contact.equals(checkContact)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }


    public interface onItemClickListener {
        void onCheckClicked(Contact contact, int pos, boolean checkStatus);

    }
}
