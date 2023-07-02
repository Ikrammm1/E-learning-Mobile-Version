package com.skadubai.elearning.model

class ModelSoal (
    val Soal : List<Daftar_soal>

)
{
    data class Daftar_soal(
        val id_act_kelas : String?,
        val id_quiz: String?,
        val id_soal: String,
        val soal: String?,
        val file_soal: String?,
        val jwb_a: String?,
        val file_a: String?,
        val jwb_b: String?,
        val file_b: String?,
        val jwb_c: String?,
        val file_c: String?,
        val jwb_d: String?,
        val file_d: String?,
        val jwb_e: String?,
        val file_e: String?,
        val bobot: String?,
        val dwsoal: String?,
        val dwfilea: String?,
        val dwfileb: String?,
        val dwfilec: String?,
        val dwfiled: String?,
        val dwfilee: String?

    )
}