package com.skadubai.elearning.API

import com.skadubai.elearning.PostJwb
import com.skadubai.elearning.UpdJwb
import com.skadubai.elearning.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


data class Jawaban (
    val idSoal: String,
    val jawaban: String
)

data class QuizRequest (
    val idQuiz: String?,
    val nisn: String?,
    val jawaban: List<Jawaban>
)

interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("post_username") username : String,
        @Field("post_password") password : String
    ) : Call<ResponseLogin>


    @FormUrlEncoded
    @POST("datasiswa.php")
    fun datasiswa(
        @Field("post_username") username: String
    ) : Call<ModelProfile>

    @FormUrlEncoded
    @POST("kelas_siswa.php")
    fun KelasSiswa(
        @Field("post_username") username: String
    ) : Call<ModelKlsSiswa>

    @FormUrlEncoded
    @POST("jumlah_klssiswa.php")
    fun JmlKelasSiswa(
        @Field("post_username") username: String
    ) : Call<ModelJmlkls>

    @FormUrlEncoded
    @POST("data_tugas.php")
    fun DaftarTugas(
        @Field("post_username") username: String?
    ) : Call<ModelTgs>

    @FormUrlEncoded
    @POST("data_jwbtgs.php")
    fun DaftarJawban(
        @Field("NISN") NISN: String?,
        @Field("id_tugas") id_tugas: String?
    ) : Call<ModelJwb>

    @FormUrlEncoded
    @POST("tugas_limit.php")
    fun TugasLimit(
        @Field("post_username") username: String
    ) : Call<ModelTgs>

    @FormUrlEncoded
    @POST("AktifitasKelas.php")
    fun AktifitasKls(
        @Field("id_kelas_mapel") id_kelas_mapel: String?
    ) : Call<ModelAct>


    @FormUrlEncoded
    @POST("upload_jwb.php")
    fun UploadJwb(
        @Field("nama_pengirim") nama_pengirim: String?,
        @Field("file_jwb") file_jwb: String?,
        @Field("NISN") NISN: String?,
        @Field("id_tugas") id_tugas: String?

    ) :Call<PostJwb>
    @FormUrlEncoded
    @POST("update_jwb.php")
    fun UpdateJwb(
        @Field("nama_pengirim") nama_pengirim: String?,
        @Field("file_baru") file_jwb: String?,
        @Field("NISN") NISN: String?,
        @Field("id_tugas") id_tugas: String?,
        @Field("file_asal") file_asal: String?

    ) :Call<UpdJwb>

    @FormUrlEncoded
    @POST("data_soalquiz.php")
    fun SoalQuiz(
        @Field("id_quiz") id_quiz: String?
    ) : Call<ModelSoal>

    @POST("jawab_quiz.php")
    fun jawabQuiz(
        @Body body: QuizRequest
    ) : Call<ResponseLogin>

    @FormUrlEncoded
    @POST("hasil_quiz.php")
    fun HasilQuiz(
        @Field("id_quiz") id_quiz: String?,
        @Field("NISN") NISN: String?
    ) : Call<ModelHasil>

    @FormUrlEncoded
    @POST("simpannilai.php")
    fun SimpanNilai(
        @Field("NISN") NISN: String?,
        @Field("id_quiz") id_quiz: String?,
        @Field("nilai_quiz") nilai_quiz: String?
    ) : Call<ModelNilai>

    @FormUrlEncoded
    @POST("cari_kelas.php")
    fun CariKelas(
        @Field("post_jurusan") post_jurusan: String?
    ) : Call<ModelCari>

    @FormUrlEncoded
    @POST("cari_byword.php")
    fun CariWord(
        @Field("post_word") post_word: String?
    ) : Call<ModelCari>

    @FormUrlEncoded
    @POST("MasukKelas.php")
    fun MasukKelas(
        @Field("NISN") NISN: String?,
        @Field("id_kelas_mapel") id_kelas_mapel: String?,
        @Field("token") token: String?,
        @Field("tokeninput") tokeninput: String?

    ) :Call<PostKelas>

    @FormUrlEncoded
    @POST("Cekklssiswa.php")
    fun CekklsSiswa(
        @Field("NISN") NISN: String?,
        @Field("id_kelas_mapel") id_kelas_mapel: String?

    ) :Call<ModelCekkls>

    @FormUrlEncoded
    @POST("keluar_kelas.php")
    fun KeluarKelas(
        @Field("NISN") NISN:String,
        @Field("id_kelas_mapel") id_kelas_mapel:String
    ): Call<ModelAct>

}