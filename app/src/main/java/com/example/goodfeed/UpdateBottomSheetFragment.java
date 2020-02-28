package com.example.goodfeed;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.goodfeed.firebase.User;
import com.example.goodfeed.firebase.UserPost;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateBottomSheetFragment extends BottomSheetDialogFragment {

    TextView tv_userId;
    EditText et_postImage;
    EditText et_postTitle;
    Button btn_addPostButton;
    private Context context;
    private User data;
    private boolean isUpdating = false;

    public UpdateBottomSheetFragment(Context context) {
        this.context = context;
    }

    public UpdateBottomSheetFragment(Context context, User data) {
        this.context = context;
        this.data = data;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_post, container, false);
        init(view);
        tv_userId.setText(GloabalData.USER_ID);

        if (data != null) {
            isUpdating = true;
            et_postImage.setText(data.getUserPost().imageUrl);
            et_postImage.setFocusable(false);
            et_postImage.setBackground(ContextCompat.getDrawable(context, R.drawable.disabled_bg));
            et_postImage.setTextColor(ContextCompat.getColor(context, R.color.disabled));
            btn_addPostButton.setText("Update");
            et_postTitle.setText(data.getUserPost().getText());
        }
        btn_addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((validate(et_postImage.getText().toString().trim()) || et_postImage.getText().toString().trim().isEmpty()) && !et_postTitle.getText().toString().trim().isEmpty()) {
                    if (isUpdating) {
                        data.getUserPost().setText(et_postTitle.getText().toString().trim());
                        ((PostsActivity) context).updatePost(data);
                    } else {
                        ((PostsActivity) context).addPost(new UserPost(et_postTitle.getText().toString().trim(), et_postImage.getText().toString().trim()));
                    }
                    dismiss();
                } else {
                    if (et_postTitle.getText().toString().trim().isEmpty()) {
                        Toast.makeText(context, "Add title first", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Add valid Image URL", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    public boolean validate(final String image) {
        Pattern pattern = Pattern.compile(GloabalData.IMAGE_REGEX);
        Matcher matcher = pattern.matcher(image);
        return matcher.matches();
    }

    private void init(View view) {
        btn_addPostButton = view.findViewById(R.id.addPostButton);
        tv_userId = view.findViewById(R.id.userIdView);
        et_postImage = view.findViewById(R.id.post_image);
        et_postTitle = view.findViewById(R.id.post_title);
    }
}
