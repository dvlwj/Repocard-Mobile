package com.example.repocardandroid.utils

object ServerAddress {
    const val AppVersion = "1.0"
//    const val localhost = "192.168.5.122:8081"
    const val localhost = "192.168.5.122:8081"
    const val http = "http://"
//    private const val MainUrl = ""
    const val Login = "/login"
    const val LoginStudent = "/login/siswa"
    const val ChangePassword = "/changePassword"
    const val ChangePasswordSiswa = "/changePassword/student"
    const val ReadScoreBySubject = "/nilai/readBySubject"
    const val ReadScoreByStudent = "/nilai/readByStudent"
    const val ReadStudent = "/murid/read"
    const val UpdateScoreSubmit = "/nilai/update/submit"
//    const val Petugas = MainUrl+"petugas"
//    const val Absen = MainUrl+"absen"
//    const val StatusSiswa = MainUrl+"siswa/"
//    const val Kelas = MainUrl+"siswa/kelas"
//    const val TahunAjaran = MainUrl+"siswa/th-ajaran"
//    const val LaporanMakan = MainUrl+"report"
}