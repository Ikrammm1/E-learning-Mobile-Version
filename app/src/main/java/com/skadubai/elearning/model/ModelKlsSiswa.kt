package com.skadubai.elearning.model

class ModelKlsSiswa (
    val Kelas_Siswa : List<KelasSiswa>

)
{
    data class KelasSiswa(
        val id_kelas_siswa : String?,
        val id_kelas_mapel : String?,
        val NIP : String?,
        val NISN : String?=null,
        val nama_siswa: String?,
        val jurusan: String?,
        val nama_mapel : String?,
        val kelas : String?,
        val keterangan : String?,
        val gambar_kls : String?,
        val nama_guru : String?,
        val semester : String?
    )
}