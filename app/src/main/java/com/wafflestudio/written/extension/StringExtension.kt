package com.wafflestudio.written.extension

class StringExtension {
    fun modifyCreatedAt(oriCreatedAt: String): String {
        return  oriCreatedAt.substring(0, 4) + "년 " +
                oriCreatedAt.substring(5, 7) + "월 " +
                oriCreatedAt.substring(8, 10) + "일 " +
                oriCreatedAt.substring(11, 13) + "시"
    }
}