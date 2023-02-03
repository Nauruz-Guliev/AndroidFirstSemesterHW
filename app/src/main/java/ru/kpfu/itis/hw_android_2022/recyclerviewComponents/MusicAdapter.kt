package ru.kpfu.itis.hw_android_2022.recyclerviewComponents

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.kpfu.itis.hw_android_2022.R
import ru.kpfu.itis.hw_android_2022.databinding.FirstViewTypeBinding
import ru.kpfu.itis.hw_android_2022.databinding.RvHeaderBinding
import ru.kpfu.itis.hw_android_2022.databinding.SecondViewTypeBinding
import ru.kpfu.itis.hw_android_2022.databinding.ThirdViewTypeBinding
import ru.kpfu.itis.hw_android_2022.models.UIModel
import ru.kpfu.itis.hw_android_2022.models.colorizeText

class MusicAdapter(
    val context: Context,
    private val musicArrayList: MutableList<UIModel>,
    private val glide: RequestManager,
    val onDeleteClick: ((RecyclerView.ViewHolder) -> Unit)?,
    val onFirstItemClicked: ((Int) -> Unit)?,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when(viewType){
            R.layout.first_view_type -> {
                FirstTypeViewHolder(
                    binding = FirstViewTypeBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            R.layout.second_view_type -> {
                SecondTypeViewHolder(
                    binding = SecondViewTypeBinding.inflate(
                        LayoutInflater.from(parent.context), parent,false
                    )
                )
            }
            R.layout.third_view_type -> {
                ThirdTypeViewHolder(
                    binding = ThirdViewTypeBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            else -> {
                HeaderViewHolder(
                    binding = RvHeaderBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = musicArrayList[position]
        when(holder) {
            is FirstTypeViewHolder -> holder.bindFirstTypeItem(item as UIModel.FirstTypeModel)
            is SecondTypeViewHolder -> holder.bindSecondTypeItem(item as UIModel.SecondTypeModel)
            is ThirdTypeViewHolder -> holder.bindThirdTypeItem(item as UIModel.ThirdTypeModel)
            is HeaderViewHolder -> holder.bindThirdTypeItem(item as UIModel.HeaderModel)
        }
    }

    override fun getItemViewType(position: Int) =
        when(musicArrayList[position]) {
            is UIModel.FirstTypeModel -> R.layout.first_view_type
            is UIModel.SecondTypeModel -> R.layout.second_view_type
            is UIModel.ThirdTypeModel -> R.layout.third_view_type
            is UIModel.HeaderModel -> R.layout.rv_header
        }

    override fun getItemCount(): Int = musicArrayList.size


    inner class FirstTypeViewHolder(private val binding: FirstViewTypeBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            with(binding) {
                rootLayout.setOnClickListener {
                    if (rootLayout.scrollX != 0) {
                        rootLayout.scrollTo(0, 0)
                    }
                    onFirstItemClicked?.invoke(adapterPosition)
                }
                tvSwipeToDelete.setOnClickListener {
                    onDeleteClick?.invoke(this@FirstTypeViewHolder)
                }
            }
        }

        fun bindFirstTypeItem(item: UIModel.FirstTypeModel) {
            with(binding) {
                tvPrimary.text = item.firstItem.primaryName
                tvSecondary.setText(
                    item.firstItem.secondaryName.colorizeText(
                        R.color.dark_grey,
                        R.color.grey,
                        item.firstItem.duration,
                        binding.root.resources
                    )
                )
                glide
                    .load(item.firstItem.imageUrl)
                    .into(shapeableImageView)
            }
        }
    }
    inner class SecondTypeViewHolder(private val binding: SecondViewTypeBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            with(binding) {
                rootLayout.setOnClickListener {
                    if (rootLayout.scrollX != 0) {
                        rootLayout.scrollTo(0, 0)
                    }
                }
                tvSwipeToDelete.setOnClickListener {
                    onDeleteClick?.invoke(this@SecondTypeViewHolder)
                }
            }
        }

        fun bindSecondTypeItem(item: UIModel.SecondTypeModel) {
            with(binding) {
                tvPrimary.text = item.secondItem.primaryName
                ivSecondType.setImageDrawable(
                    context.resources.getDrawable(
                        item.secondItem.imageDrawableId,
                        null
                    )
                )

            }
        }
    }
    inner class ThirdTypeViewHolder(private val binding: ThirdViewTypeBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            with(binding) {
                rootLayout.setOnClickListener {
                    if (rootLayout.scrollX != 0) {
                        rootLayout.scrollTo(0, 0)
                    }
                }
                tvSwipeToDelete.setOnClickListener {
                    onDeleteClick?.invoke(this@ThirdTypeViewHolder)
                }
            }
        }

        fun bindThirdTypeItem(item: UIModel.ThirdTypeModel) {
            with(binding) {
                with(item) {
                    tvPrimary.text = thirdItem.primaryName
                    tvSecondary.setText(
                        thirdItem.secondaryName.colorizeText(
                            R.color.dark_grey,
                            R.color.grey,
                            thirdItem.duration,
                            binding.root.resources
                        )
                    )
                    glide
                        .load(thirdItem.imageUrl)
                        .into(shapeableImageView)
                }
            }
        }
    }

    inner class HeaderViewHolder(private val binding: RvHeaderBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindThirdTypeItem(item: UIModel.HeaderModel){
            with(binding){
                tvHeader.text = item.headerText
            }
        }
    }


    fun setData(currentList: List<UIModel>) {
        val diffUtil = RVDiffUtil(musicArrayList, currentList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        musicArrayList.clear()
        musicArrayList.addAll(currentList)
        diffResults.dispatchUpdatesTo(this)
    }
}


