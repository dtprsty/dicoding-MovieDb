package prsty.id.favoritesmovie;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteAdapter extends CursorAdapter {

    @BindView(R.id.imgPoster)
    ImageView moviePoster;
    @BindView(R.id.txTitle)
    TextView txTitle;
    @BindView(R.id.txDate)
    TextView txDate;
    @BindView(R.id.cardView)
    CardView cardview;

    public FavoriteAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_row, viewGroup, false);
        return view;
    }


    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ButterKnife.bind(this, view);

        txTitle.setText(cursor.getString(cursor.getColumnIndex("title")));
        txDate.setText(cursor.getString(cursor.getColumnIndex("date")));
        Glide.with(context)
                .load(cursor.getString(cursor.getColumnIndex("poster")))
                .into(moviePoster);
    }

}
