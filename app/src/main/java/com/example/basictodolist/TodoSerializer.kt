package com.example.basictodolist

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object TodoSerializer:Serializer<Todos>{
    override val defaultValue: Todos
        get() = Todos.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Todos {
       return try{
            Todos.parseFrom(input)
        }catch (e:InvalidProtocolBufferException){
            throw CorruptionException("Cannot read proto", e)
        }
    }

    override suspend fun writeTo(t: Todos, output: OutputStream) {
         t.writeTo(output)
    }

}



