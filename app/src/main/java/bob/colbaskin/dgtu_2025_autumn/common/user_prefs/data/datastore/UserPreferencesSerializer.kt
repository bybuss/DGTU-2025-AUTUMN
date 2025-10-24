package bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import bob.colbaskin.dgtu_2025_autumn.datastore.UserPreferencesProto
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer : Serializer<UserPreferencesProto> {
    override val defaultValue: UserPreferencesProto = UserPreferencesProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserPreferencesProto {
        try {
            return UserPreferencesProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto", exception)
        }
    }

    override suspend fun writeTo(t: UserPreferencesProto, output: OutputStream) {
        t.writeTo(output)
    }
}
