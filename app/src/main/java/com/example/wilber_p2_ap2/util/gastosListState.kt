package com.example.wilber_p2_ap2.util

import com.example.wilber_p2_ap2.data.remote.dto.gastosDto

data class gastosListState(
    val isLoading: Boolean = false,
    val Gastos: List<gastosDto> = emptyList(),
    val error: String = ""
)