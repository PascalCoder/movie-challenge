package com.example.movieschallenge.presenter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieschallenge.R;
import com.example.movieschallenge.model.Movie;
import com.example.movieschallenge.model.MovieList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>
                                    implements Filterable {

    private MovieList dataSet;
    public List<Movie> filteredMovies;

    public CustomAdapter(MovieList dataSet){
        this.dataSet = dataSet;
        filteredMovies = new ArrayList<>(dataSet.movieList);
    }

    @NonNull
    @Override
    public CustomAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CustomViewHolder(LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.item_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.CustomViewHolder customViewHolder, int i) {
        customViewHolder.tvTitle.setText(dataSet.movieList.get(i).getTitle());
        customViewHolder.tvYear.setText(dataSet.movieList.get(i).getYear());
        Picasso.get().load(dataSet.movieList.get(i).getPoster())
                    .into(customViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return (dataSet != null ? dataSet.getMovieList().size() : 0);
    }

    @Override
    public Filter getFilter() {
        return movieFilter;
    }

    private Filter movieFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Movie> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(filteredMovies);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                //String genreFilterPattern = constraint.toString().toLowerCase().trim();

                for(Movie movie : filteredMovies){
                    if(movie.getTitle().toLowerCase().contains(filterPattern)
                        || movie.getGenre().toLowerCase().contains(filterPattern)){
                        filteredList.add(movie);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataSet.movieList.clear();
            dataSet.movieList.addAll((List)results.values);

            notifyDataSetChanged();
        }
    };

    class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle, tvYear;
        ImageView imageView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvYear = itemView.findViewById(R.id.tv_year);
            imageView = itemView.findViewById(R.id.iv_poster);

            tvYear.setOnClickListener(v -> {

            });
        }
    }
}
