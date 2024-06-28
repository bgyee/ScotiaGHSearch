package com.interview.scotiaghsearch.ui.list

import android.graphics.drawable.Drawable
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.interview.scotiaghsearch.data.model.UserRepo
import com.interview.scotiaghsearch.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val viewModel: ListViewModel by viewModels()

    private var _binding: FragmentListBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repoListAdapter = RepoListAdapter { userRepo -> onItemClick(userRepo) }
        binding.repoListView.adapter = repoListAdapter

        viewModel.userInfo.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer

            Glide.with(requireContext())
                .load(it.avatarUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.userImage.startFadeInAnimation()
                        return false
                    }

                })
                .transition(DrawableTransitionOptions.withCrossFade(CROSS_FADE_ANIM_DURATION))
                .into(binding.userImage)

            binding.userName.startFadeInAnimation()
            binding.userName.text = it.name
        })

        viewModel.userRepoList.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer

            repoListAdapter.submitList(it)

            binding.repoListView.startFadeInAnimation()
        })

        binding.searchButton.setOnClickListener {
            val searchText = binding.searchEditText.text.toString()
            viewModel.getUserInfo(searchText)
            viewModel.getRepoList(searchText)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun onItemClick(userRepo: UserRepo) {
        findNavController().navigate(
            ListFragmentDirections.actionListFragmentToDetailsFragment(
                userRepo.name,
                viewModel.totalForkCount
            )
        )
    }

    private fun View.startFadeInAnimation() {
        apply {
            alpha = 0F
            translationY = FADE_IN_ANIM_DISTANCE

            animate().alpha(1F).translationY(0F).setDuration(FADE_IN_ANIM_DURATION)
        }
    }

    companion object {
        private const val CROSS_FADE_ANIM_DURATION = 1000
        private const val FADE_IN_ANIM_DURATION = 500L
        private const val FADE_IN_ANIM_DISTANCE = 40F
    }
}