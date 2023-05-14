package dev.isutc.chatapplications.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.isutc.chatapplications.R;
import dev.isutc.chatapplications.models.Message;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Message> messages;
    private boolean isSender;

    public MessageAdapter(List<Message> messages, boolean isSender) {
        this.messages = messages;
        this.isSender = isSender;
    }

    @Override
    public int getItemViewType(int position) {
        return isSender ? R.layout.sent_message_item : R.layout.received_message_item;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        if (isSender) {
            return new SenderViewHolder(view);
        } else {
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
//        holder.bind(users.get(position));
        if (holder instanceof SenderViewHolder) {
            SenderViewHolder senderViewHolder = (SenderViewHolder) holder;
            senderViewHolder.messageBody.setText(message.getText());

        } else if (holder instanceof ReceiverViewHolder) {
            ReceiverViewHolder receiverViewHolder = (ReceiverViewHolder) holder;
            receiverViewHolder.messageBody.setText(message.getText());

        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView profileImage;
        public TextView messageBody;

        public SenderViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.img_message_user);
            messageBody = itemView.findViewById(R.id.txt_msg);
        }
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView profileImage;
        public TextView messageBody;

        public ReceiverViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.img_message_user);
            messageBody = itemView.findViewById(R.id.txt_msg);
        }
    }


}
