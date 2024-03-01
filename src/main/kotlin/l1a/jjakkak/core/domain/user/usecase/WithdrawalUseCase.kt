package l1a.jjakkak.core.domain.user.usecase

import l1a.jjakkak.core.domain.exception.ZzekakException
import l1a.jjakkak.core.domain.user.AuthenticationId
import l1a.jjakkak.core.domain.user.WithdrawalResult
import l1a.jjakkak.core.domain.user.message.WithdrawalMessage
import l1a.jjakkak.core.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.Instant

interface WithdrawalUseCase {
    fun widthdrawal(message: WithdrawalMessage): WithdrawalResult
}

@Service
internal class WithdrawalUseCaseImpl(
    val userRepo: UserRepository
): WithdrawalUseCase {
    override fun widthdrawal(message: WithdrawalMessage): WithdrawalResult {
        val (userId) = message
        val user = userRepo.findUserByUserIdAndIsRemoved(userId, false)
            ?: return WithdrawalResult(
                result = 'N',
        message = "탈퇴 처리에 실패했습니다. 확인되지 않는 회원입니다."
        )

        return userRepo.save(user.deleteUser()).let {
            WithdrawalResult(
                result = 'Y',
                message = "성공적으로 탈퇴처리 되었습니다."
            )
        }
    }


}