package com.veriqus.savoirvivre;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ArticleFragment extends Fragment {


    public ArticleFragment() {
        // Required empty public constructor
    }

    View rootView;
    static String ARTICLE_NAME;
    String articleName;
    String articleContent;
    HashMap<String, String> savedList;
    boolean isSaved;
    int position;
    List<String> quotesTitles;
    List<String> quotesContents;
    ImageView saveIcon;
    TextView articleContentTextView;
    TextView articleTitleText;
    ViewGroup swipeCard;
    View swipeLay;
    int dip16toPx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_article, container, false);
        setHasOptionsMenu(true);


        //pixel to dp conversion
        Resources r = getResources();
        dip16toPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, r.getDisplayMetrics());

        Bundle bundle = getArguments();
        String categoryID = bundle.getString("CATEGORY_ID");
        Log.i("CatID", categoryID);
        position = bundle.getInt("ARTICLE_POSITION");

        swipeLay = rootView.findViewById(R.id.swipeLay);
        swipeLay.setOnTouchListener(new OnSwipeTouchListener(getContext()){
            public void onSwipeRight() {
                //Toast.makeText(getContext(), "left", Toast.LENGTH_SHORT).show();
                if (position > 0) {
                    position--;
                } else {
                    position = quotesTitles.size() - 1;
                }
                loadArticle();
            }
            public void onSwipeLeft() {
                //Toast.makeText(getContext(), "right", Toast.LENGTH_SHORT).show();
                if (position < quotesTitles.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                loadArticle();
            }
        });
        swipeCard = (ViewGroup) rootView.findViewById(R.id.cardViewSwipe);
        swipeCard.setOnTouchListener(new OnSwipeTouchListener(getContext()){
            public void onSwipeRight() {
                //Toast.makeText(getContext(), "left", Toast.LENGTH_SHORT).show();
                if (position > 0) {
                    position--;
                } else {
                    position = quotesTitles.size() - 1;
                }
                loadArticle();
            }
            public void onSwipeLeft() {
                //Toast.makeText(getContext(), "right", Toast.LENGTH_SHORT).show();
                if (position < quotesTitles.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                loadArticle();
            }
        });

        if (categoryID.equals("SAVED")) {
            HashMap<String, String> savedList = ((MainActivity) getActivity()).loadMap();

            quotesTitles = new ArrayList<>();
            quotesContents = new ArrayList<>();

            for (String key : savedList.keySet()) {
                int num = +0;
                quotesContents.add(num, ((MainActivity) getActivity()).getArticleContentbyID(key));
                quotesTitles.add(num, ((MainActivity) getActivity()).getArticleTitlebyID(key));
            }
        } else {
            quotesTitles = ((MainActivity) getActivity()).getArticleList(categoryID, "title");
            quotesContents = ((MainActivity) getActivity()).getArticleList(categoryID, "content");
        }

        articleContentTextView = (TextView) rootView.findViewById(R.id.articleContent);
        articleTitleText = (TextView) rootView.findViewById(R.id.articleTitle);


        loadArticle();

        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });


        final ImageView share = (ImageView) rootView.findViewById(R.id.shareArticleButton);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIntent(articleName, articleContent);

            }
        });


        final ImageView previous = (ImageView) rootView.findViewById(R.id.previousArticleButton);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position > 0) {
                    position--;
                } else {
                    position = quotesTitles.size() - 1;
                }
                loadArticle();
            }
        });

        final ImageView next = (ImageView) rootView.findViewById(R.id.nextArticleButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < quotesTitles.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                loadArticle();
            }
        });


        return rootView;
    }

    private void shareIntent(String title, String content) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, title + "\n" + content);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void save(){
        if (!isSaved) {
            String ID = ((MainActivity) getActivity()).getArticleID(articleName);
            savedList.put(ID, ID);
            ((MainActivity) getActivity()).saveMap(savedList);
            saveIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_36dp));
        } else {
            saveIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_36dp));
            String ID = ((MainActivity) getActivity()).getArticleID(articleName);
            savedList.remove(ID);
            ((MainActivity) getActivity()).saveMap(savedList);
        }
        isSaved = savedList.containsKey(((MainActivity) getActivity()).getArticleID(articleName));
    }

    private void loadArticle() {
        articleName = quotesTitles.get(position);
        articleContent = quotesContents.get(position);
        articleTitleText.setText(articleName);

//        FOR HTML CONVERSION

//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            articleContent = Html.fromHtml(((MainActivity)getActivity()).getArticleContent(articleName),Html.FROM_HTML_MODE_LEGACY).toString();
//            Log.i(">","25");
//        } else {
//            articleContent = Html.fromHtml(((MainActivity)getActivity()).getArticleContent(articleName)).toString();
//            Log.i("<","21");
//        }

        articleContentTextView.setText(articleContent);

        ImageView imgPlace = (ImageView) rootView.findViewById(R.id.imgPlace);
        byte[] imgByteData = ((MainActivity) getActivity()).getImageByte(quotesTitles.get(position));
        int lenData = 0;
        if (!imgByteData.equals(null)) {
            lenData = imgByteData.length;
        }

        if (!imgByteData.equals(null) && lenData > 1) {
//            imgPlace.setPadding(dip16toPx, dip16toPx, dip16toPx, dip16toPx);
            Log.i("Image:", "shown");
            Glide.with(getActivity())
                    .load(imgByteData).asBitmap()
                    .into(imgPlace);
            imgPlace.setVisibility(View.VISIBLE);
        } else {
            imgPlace.setVisibility(View.GONE);
        }

        saveIcon = (ImageView) rootView.findViewById(R.id.saveArticleButton);
        savedList = ((MainActivity) getActivity()).loadMap();
        isSaved = savedList.containsKey(((MainActivity) getActivity()).getArticleID(articleName));

        if (!isSaved) {
            saveIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_36dp));
        } else {
            saveIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_36dp));
        }

    }
}