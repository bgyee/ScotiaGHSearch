package com.interview.scotiaghsearch.ui.details

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.interview.scotiaghsearch.R
import com.interview.scotiaghsearch.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()

    private var _binding: FragmentDetailsBinding? = null
    private val binding
        get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedRepo.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            with(binding) {
                repoNameView.text = it.name
                repoDescriptionView.text = getString(R.string.description, it.description)
                repoUpdatedAtView.text = getString(R.string.updated_at, it.updatedAt)
                repoStargazersCountView.text =
                    getString(R.string.stargazers_count, it.stargazersCount.toString())
                repoForksView.text = getString(R.string.forks, it.forks)
            }
        })

        viewModel.selectRepo(args.repoName)

        setTotalForksCount()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setTotalForksCount() {
        val totalForkPrefix = getString(R.string.total_fork_count)
        val totalForkValue = " ${args.totalForkCount}"

        val totalForkCountText = if (args.totalForkCount > STAR_BADGE_THRESHOLD) {
            val spanStringBuilder = SpannableStringBuilder()

            val totalForkPrefixSpan = SpannableString(totalForkPrefix)
            totalForkPrefixSpan.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.star_badge_color_1
                    )
                ),
                0,
                totalForkPrefixSpan.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spanStringBuilder.append(totalForkPrefixSpan)

            val totalForkValueSpan = SpannableString(totalForkValue)
            totalForkValueSpan.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.star_badge_color_2
                    )
                ),
                0,
                totalForkValueSpan.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spanStringBuilder.append(totalForkValueSpan)

            spanStringBuilder
        } else {
            totalForkPrefix + totalForkValue
        }

        binding.totalForksCountView.text = totalForkCountText
    }

    companion object {
        private const val STAR_BADGE_THRESHOLD = 5000
    }
}