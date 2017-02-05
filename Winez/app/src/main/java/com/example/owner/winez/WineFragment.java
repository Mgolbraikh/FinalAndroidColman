package com.example.owner.winez;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.test.mock.MockContentProvider;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.owner.winez.Model.Comment;
import com.example.owner.winez.Model.Model;
import com.example.owner.winez.Model.Wine;
import com.example.owner.winez.Utils.Consts;
import com.example.owner.winez.Utils.WinezDB;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WineFragment extends Fragment {
    Wine wine;
    List<Comment> comments;
    CommentsAdapter mAdapter;

    public WineFragment() {
        // Required empty public constructor
    }
    
    // // TODO: 04-Feb-17 need to add the comment to wines and save and load from db
    // TODO: 04-Feb-17 need to add STAR and all its attributes


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle WineIdBundle = getArguments();
        // Inflate the layout for this fragment
        final View view = inflater.inflate (R.layout.fragment_wine, container, false);
        final ListView commentsList = (ListView)view.findViewById(R.id.wine_comment_list);
        mAdapter = new CommentsAdapter();
        comments = new ArrayList<>();
        commentsList.setAdapter(mAdapter);
        Model.getInstance().getWine(WineIdBundle.getString(Consts.WINE_BUNDLE_ID), new WinezDB.GetOnCompleteResult<Wine>() {
            @Override
            public void onResult(Wine data) {
                wine = data;
                Model.getInstance().getCommentsForWine(wine.getUid(), new WinezDB.GetOnCompleteResult<List<Comment>>() {
                    @Override
                    public void onResult(List<Comment> data) {
                        comments = data;
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancel(String err) {

                    }
                });

                TextView tvTitle = (TextView) view.findViewById(R.id.wine_wine_title);
                tvTitle.setText(wine.getName());
                EditText edPrice = (EditText) view.findViewById(R.id.wine_price);
                edPrice.setText(Double.toString(wine.getPrice()));
                EditText edType = (EditText) view.findViewById(R.id.wine_type);
                edType.setText(wine.getType());
                EditText edYear = (EditText) view.findViewById(R.id.wine_vintage_year);
                edYear.setText(wine.getVintage());

            }

            @Override
            public void onCancel(String err) {

            }
        });

        ((EditText) view.findViewById(R.id.wine_new_comment)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if((actionId == EditorInfo.IME_ACTION_DONE) ||
                        (actionId == EditorInfo.IME_ACTION_NEXT)) {

                    // Creatin comment
                    Comment toAdd = new Comment(wine.getUid(),
                            Model.getInstance().getCurrentUser().getUid(),
                            textView.getText().toString(),
                            Model.getInstance().getCurrentUser().getName());
                    // Saving to remote
                    Model.getInstance().saveComment(toAdd);

                    // Adding to list
                    comments.add(toAdd);
                    mAdapter.notifyDataSetChanged();

                    return true; // consume.
                    }
                return false;
                }
        });

        return  view;
    }

    
    class CommentsAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return comments.size();
        }

        @Override
        public Object getItem(int i) {
            return comments.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null)
            {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.row_comment_list,null);
            }

            // Check if this is not the first and the user wishes to enter data
                Comment currComent = (Comment) this.getItem(i);
                TextView tvName = (TextView) view.findViewById(R.id.row_comment_name);
                TextView tvText = (TextView) view.findViewById(R.id.row_comment_text);

                tvName.setText(currComent.getUserName());
                tvText.setText(currComent.getText());

            return view;
        }
    }
}
