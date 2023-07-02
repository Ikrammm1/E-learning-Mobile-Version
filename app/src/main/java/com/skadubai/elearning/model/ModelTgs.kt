package com.skadubai.elearning.model

class ModelTgs (
    val daftar_tugas : List<Daftar_tgs>

)
{
    data class Daftar_tgs(
        val id_tugas : String?,
        val nama_mapel : String?,
        val kelas : String?,
        val judul_tugas: String?,
        val deskripsi : String?,
        val mulai : String?,
        val waktu_mulai : String?,
        val hari_akhir : String?,
        val waktu_akhir : String?,
        val NISN : String?,
        val namafile : String?,
        val gambar : String?,
        val file_tugas: String?,
        val id_jawaban: String?,
        val nama_pengirim: String?,
        val jwb_text: String?,
        val file: String?,
        val submited: String?,
        val update: String?,
        val filejwb: String?,
        val sisawaktu: Int,
        val countwaktu : String?


    )
}
