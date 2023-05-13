package dev.isutc.chatapplications.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.isutc.chatapplications.R;
import dev.isutc.chatapplications.models.User;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    List<User> users;
    private OnItemClickListener listener;

    public ContactsAdapter(List<User> users) {
        this.users = users;
    }

    //todo: TO BE REMOVED
    public ContactsAdapter(List<User> users, OnItemClickListener onItemClickListener) {
        this.users = users;
        this.listener = onItemClickListener;
    }

    //todo: TO BE REMOVED
    public interface OnItemClickListener {
        void onItemClick(User item);
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
//        holder.bind(users.get(position)); //todo: UNCOMMENT
        holder.bind(users.get(position), listener);//todo: DELETE
    }

    @Override
    public int getItemCount() {
        return users.size();
    }



    class ContactsViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView imageView;
        private TextView username;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.profile_image);
            username = itemView.findViewById(R.id.txt_username);

        }

        //todo: DELETE
        public void bind(User user, OnItemClickListener listener) {
            username.setText(user.getUsername());
            Picasso.get()
                    .load(user.getProfileUrl())
                    .into(imageView);
            username.setOnClickListener(view -> listener.onItemClick(user));
        }
        public void bind(User user) {
            username.setText(user.getUsername());
            Picasso.get()
                    .load(user.getProfileUrl())
                    .into(imageView);
        }

    }
}
