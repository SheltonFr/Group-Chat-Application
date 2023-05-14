package dev.isutc.chatapplications.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.isutc.chatapplications.R;
import dev.isutc.chatapplications.models.Message;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {


    List<Message> messages = new ArrayList<>();
    private boolean isSender;

    public MessageAdapter() {}

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
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isSender) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.received_message_item, parent, false);
            return new SenderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sent_message_item, parent, false);
            return new ReceiverViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        if (holder instanceof SenderViewHolder) {
//            ((SenderViewHolder) holder).getMessageBody().setText(this.messages.get(position).getText());
            SenderViewHolder senderViewHolder = (SenderViewHolder) holder;
            senderViewHolder.messageBody.setText(message.getText());

        } else if (holder instanceof ReceiverViewHolder) {
//            ((SenderViewHolder) holder).getMessageBody().setText(this.messages.get(position).getText());
            ReceiverViewHolder receiverViewHolder = (ReceiverViewHolder) holder;
            receiverViewHolder.messageBody.setText(message.getText());

        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessage(Message message){
        this.messages.add(message);
        notifyDataSetChanged();
    }
    public void setChatMessages(List<Message> chatMessages){
        this.messages = chatMessages;
        notifyDataSetChanged();
    }

    public class SenderViewHolder extends MessageViewHolder {
        public CircleImageView profileImage;
        public TextView messageBody;

        public SenderViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.img_message_user);
            messageBody = itemView.findViewById(R.id.txt_msg);
        }

        public CircleImageView getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(CircleImageView profileImage) {
            this.profileImage = profileImage;
        }

        public TextView getMessageBody() {
            return messageBody;
        }

        public void setMessageBody(TextView messageBody) {
            this.messageBody = messageBody;
        }
    }

    public class ReceiverViewHolder extends MessageViewHolder {
        public CircleImageView profileImage;
        public TextView messageBody;

        public ReceiverViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.img_message_user);
            messageBody = itemView.findViewById(R.id.txt_msg);
        }

        public CircleImageView getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(CircleImageView profileImage) {
            this.profileImage = profileImage;
        }

        public TextView getMessageBody() {
            return messageBody;
        }

        public void setMessageBody(TextView messageBody) {
            this.messageBody = messageBody;
        }
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public MessageViewHolder(View itemView) {
            super(itemView);
        }
    }


}
