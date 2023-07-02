package com.skadubai.elearning.model



    data class ModelHasil(
        val id_quiz: String?,
        val id_soal: String,
        val id_jawaban: String?,
        val jwb: String?,
        val kunci_jwb: String?,
        val total_soal: Int,
        val total_benar: Int,
        val total_salah: Int,
        val nilai: Int

    )

