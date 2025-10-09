package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/*
* 트랜잭션 - @Transactional AOP
* 여전히 남아있던 트랜잭션 관련 코드를 트랜잭션 AOP로 없앰
* */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV3_3 {

  private final MemberRepositoryV3 memberRepository;

  @Transactional
  public void accountTransfer(String fromId, String toId, int money) throws Exception {
    bizLogic(fromId, toId, money);
  }

  private void bizLogic(String fromId, String toId, int money) throws Exception {
    Member fromMember = memberRepository.findById(fromId);
    Member toMember = memberRepository.findById(toId);

    memberRepository.update(fromId, fromMember.getMoney() - money);
    validate(toMember);
    memberRepository.update(toId, toMember.getMoney() + money);
  }

  private void validate(Member member) {
    if (member.getMemberId().equals("ex")) {
      throw new IllegalStateException("이체중 예외 발생");
    }
  }

}
