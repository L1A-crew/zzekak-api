package l1a.jjakkak.api.config.exception

import org.springframework.http.HttpStatus

enum class ExceptionEnum(
    val code: String,
    val rStatus: HttpStatus,
    val message: String
) {
    // 유저 관련 Erorr => U-...
    ILLEGAL_TOKEN(" U-001", HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다."),

    NO_EXIST_USER("U-002", HttpStatus.UNAUTHORIZED, "존재하지 않는 사용자 입니다."),

    // server관련 Erorr => S-...
    SERVER_ERROR("S-001", HttpStatus.INTERNAL_SERVER_ERROR, "처리 중 오류가 발생했습니다."),
}
