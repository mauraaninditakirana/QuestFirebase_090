package com.example.pertemuan14.repositori

import com.example.pertemuan14.modeldata.Siswa
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

interface RepositorySiswa {
    suspend fun getDataSiswa(): List<Siswa>
    suspend fun postDataSiswa(siswa: Siswa)
    suspend fun getSatuSiswa(id: Long): Siswa?
    suspend fun editSatuSiswa(id: Long, siswa: Siswa)
    suspend fun hapusSatuSiswa(id: Long)
}

class FirebaseRepositorySiswa : RepositorySiswa {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("siswa")

    override suspend fun getDataSiswa(): List<Siswa> {
        return try {
            collection.get().await().documents.map { doc ->
                Siswa(
                    id = doc.getLong("id")?.toLong() ?: 0,
                    nama = doc.getString("nama") ?: "",
                    alamat = doc.getString("alamat") ?: "",
                    telpon = doc.getString("telpon") ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun postDataSiswa(siswa: Siswa) {
        val docRef = if (siswa.id == 0L) collection.document() else collection.document(
            siswa.id.toString())
        val data = hashMapOf(
            "id" to (siswa.id.takeIf { it != 0L } ?: docRef.id.hashCode()),
            "nama" to siswa.nama,
            "alamat" to siswa.alamat,
            "telpon" to siswa.telpon
        )
        docRef.set(data).await()
    }

    override suspend fun getSatuSiswa(id: Long): Siswa? {
        return try {
            val query = collection.whereEqualTo("id", id).get().await()
            query.documents.firstOrNull()?.let { doc ->
                Siswa(
                    id = doc.getLong("id")?.toLong() ?: 0,
                    nama = doc.getString("nama") ?: "",
                    alamat = doc.getString("alamat") ?: "",
                    telpon = doc.getString("telpon") ?: ""
                )
            }
        } catch (e: Exception) {
            println("Gagal baca data siswa : ${e.message}")
            null
        }
    }

    override suspend fun editSatuSiswa(id: Long, siswa: Siswa) {
        val docQuery = collection.whereEqualTo("id", id).get().await()
        val docId = docQuery.documents.firstOrNull()?.id ?: return
        collection.document(docId).set(
            mapOf(
                "id" to siswa.id,
                "nama" to siswa.nama,
                "alamat" to siswa.alamat,
                "telpon" to siswa.telpon
            )
        ).await()
    }

    override suspend fun hapusSatuSiswa(id: Long) {
        val docQuery = collection.whereEqualTo("id", id).get().await()
        val docId = docQuery.documents.firstOrNull()?.id ?: return
        collection.document(docId).delete().await()
    }
}
