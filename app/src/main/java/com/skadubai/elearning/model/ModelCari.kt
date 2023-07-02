package com.skadubai.elearning.model

class ModelCari (
    val KelasMapel : List<DaftarKelas>

)
{
    data class DaftarKelas(
        val id_kelas_mapel : String,
        val code : String?,
        val NIP: String?,
        val nama_mapel : String?,
        val kelas : String?,
        val keterangan : String?,
        val nama_guru : String?,
        val gambar : String?,
        val token : String?,
        val dwgambar: String?

    )

}