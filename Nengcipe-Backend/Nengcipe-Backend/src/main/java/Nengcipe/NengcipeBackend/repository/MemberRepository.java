package Nengcipe.NengcipeBackend.repository;

import Nengcipe.NengcipeBackend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    public Optional<Member> findByMemberId(String memberID);
}
