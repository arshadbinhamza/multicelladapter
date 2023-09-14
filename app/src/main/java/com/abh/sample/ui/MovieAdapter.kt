package com.abh.sample.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abh.sample.databinding.MovieLayoutBinding
import com.abh.sample.databinding.MovieLayoutThreeBinding
import com.abh.sample.databinding.MovieLayoutTwoBinding
import com.abh.sample.models.ResultItems
import com.bumptech.glide.Glide

class MovieAdapter :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_1 = 0
        const val TYPE_2 = 1
        const val TYPE_3 = 2
        // Add more types as needed
    }

    private var movieList = ArrayList<ResultItems>()

    fun setMovieList(movieList: List<ResultItems>) {
        this.movieList = ArrayList(movieList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_1 -> Type1ViewHolder(MovieLayoutBinding.inflate( LayoutInflater.from(parent.context ), parent, false))
            TYPE_2 -> Type2ViewHolder(MovieLayoutTwoBinding.inflate( LayoutInflater.from(parent.context ), parent, false))
            TYPE_3 -> Type3ViewHolder(MovieLayoutThreeBinding.inflate( LayoutInflater.from(parent.context ), parent, false))
            else -> Type1ViewHolder(MovieLayoutBinding.inflate( LayoutInflater.from(parent.context ), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = movieList[position]
        when (holder.itemViewType) {
            TYPE_1 -> {
                val type1ViewHolder = holder as Type1ViewHolder
                Glide.with(holder.itemView)
                    .load("https://image.tmdb.org/t/p/w500"+movieList[position].poster_path)
                    .into(type1ViewHolder.binding.movieImage)
                type1ViewHolder.binding.movieName.text = movieList[position].title

                holder.itemView.setOnClickListener(View.OnClickListener {
                    Log.d("sdfsfs","dsfsfsf")
                })
            }
            TYPE_2 -> {
                val type2ViewHolder = holder as Type2ViewHolder
              /*  Glide.with(holder.itemView)
                    .load("https://image.tmdb.org/t/p/w500"+movieList[position].poster_path)
                    .into(type2ViewHolder.binding.movieImage)*/
                type2ViewHolder.binding.movieName.text = movieList[position].title
            }
            else -> {
                val type3ViewHolder = holder as Type3ViewHolder
                Glide.with(holder.itemView)
                    .load("https://image.tmdb.org/t/p/w500"+movieList[position].poster_path)
                    .into(type3ViewHolder.binding.movieImage)
                type3ViewHolder.binding.movieName.text = movieList[position].title
            }
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun getItemViewType(position: Int): Int {
//        return movieList[position].type//Anthoyi modelinu anusarichu ivide type kodukkuka
        return position%3
    }

    class Type1ViewHolder(val binding : MovieLayoutBinding) : RecyclerView.ViewHolder(binding.root)  {
    }

    class Type2ViewHolder(val binding : MovieLayoutTwoBinding) : RecyclerView.ViewHolder(binding.root)  {
    }

    class Type3ViewHolder(val binding : MovieLayoutThreeBinding) : RecyclerView.ViewHolder(binding.root)  {
    }



}