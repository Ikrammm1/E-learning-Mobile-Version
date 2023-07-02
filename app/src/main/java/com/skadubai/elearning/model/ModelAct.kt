package com.skadubai.elearning.model

class ModelAct (
    val Aktifitas : List<Daftar_act>

)
{
    data class Daftar_act(
        val id_kelas_mapel : String,
        val id_act_kelas : String?,
        val topik: String?,
        val deskripsi : String?,
        val id_materi : String?,
        val judul : String?,
        val namamateri : String?,
        val dwmateri : String?,
        val id_quiz: String?,
        val nama_quiz: String?,
        val durasi_quiz: Int,
        val tgl_mulai: String?,
        val tgl_akhir: String?,
        val KKM: Int?,
        val status_quiz: String?,
        val tgl_buat_quiz: String?,
        val tgl_modify_quiz: String?,
        val mulai: String?,
        val waktu_mulai: String?,
        val selesai: String?,
        val waktu_selesai: String?,
        val tgl_modify: String?,
        val waktu_modify: String?,
        val sisawaktuquiz: Int,
        val countwaktuquiz: String?

    )

}