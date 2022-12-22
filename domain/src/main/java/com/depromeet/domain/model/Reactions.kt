package com.depromeet.domain.model

data class Reactions(
    val my_reaction_info: MyReactionInfo,
    val reaction_count_infos: List<ReactionCountInfo>
)